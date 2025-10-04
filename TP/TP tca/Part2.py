#!/usr/bin/env python3
# coalition_market.py  – stats & matplotlib
# --------------------------------------------------------------------
import threading, queue, time, random, string, json
from dataclasses import dataclass
from typing import Dict, List, Optional
from collections import deque
from itertools import combinations
from functools import lru_cache
import matplotlib.pyplot as plt           # ← NEW


# ───────────────────────── utilitaires ──────────────────────────────
PRINT_LOCK = threading.Lock()
def log(msg: str):
    with PRINT_LOCK:
        print(msg)

# ─────────────────────────── messages ───────────────────────────────
@dataclass
class Message:
    sender: str
    receiver: str
    mtype: str
    data: Dict

# ───────────────────────── environnement ────────────────────────────
class Environment:
    def __init__(self):
        self.agents: Dict[str, "Agent"] = {}
    def register(self, a: "Agent"):
        self.agents[a.name] = a
    def send(self, m: Message):
        if m.receiver == "*":
            for ag in self.agents.values():
                if ag.name != m.sender:
                    ag.inbox.put(m)
        else:
            self.agents[m.receiver].inbox.put(m)

# ────────────────────────── base agent ──────────────────────────────
class Agent(threading.Thread):
    def __init__(self, name: str, env: Environment):
        super().__init__(daemon=True)
        self.name = name
        self.env  = env
        self.inbox: "queue.Queue[Message]" = queue.Queue()
    def send(self, to: str, mtype: str, data: Dict):
        self.env.send(Message(self.name, to, mtype, data))
    def broadcast(self, targets: List[str], mtype: str, data: Dict):
        for tgt in targets:
            if tgt != self.name:
                self.env.send(Message(self.name, tgt, mtype, data))

# ─────────────────────────── domaine ────────────────────────────────
@dataclass
class Ticket:
    tid: str; depart: str; arriver: str; date: str
    prix_min: float; prix_max: float

# ─────────────────────────── supplier ───────────────────────────────
class Supplier(Agent):
    COLLECT_SEC = 1.0
    def __init__(self, name: str, env: Environment, tickets: List[Ticket]):
        super().__init__(name, env)
        self.tickets = {t.tid: t for t in tickets}
        self.requests: Dict[str, List[str]] = {}
        self.sales: List[tuple[str,int,float]] = []   # (tid, qty, total_price)

    # ---------- collecte ----------
    def collect_requests(self):
        deadline = time.time() + self.COLLECT_SEC
        while time.time() < deadline:
            try:
                m: Message = self.inbox.get(timeout=.1)
            except queue.Empty:
                continue
            if m.mtype != "REQ":
                continue
            dep, arr, dat = m.data["depart"], m.data["arriver"], m.data["date"]
            for t in self.tickets.values():
                if (t.depart, t.arriver, t.date) == (dep, arr, dat):
                    self.requests.setdefault(t.tid, []).append(m.sender)
                    break

    # ---------- enchère ----------
    def run_auction(self, tid: str, buyers: List[str]):
        t = self.tickets[tid]
        price = min(t.prix_min * self.env.agents[b].qty for b in buyers)
        active = deque(buyers); winner=None
        log(f"\n[{self.name}]  Enchère {tid} – départ {price} € – {', '.join(active)}")
        while len(active) > 1:
            bidder = active[0]
            self.send(bidder,"A_CALL",{"tid":tid,"price":price,"seller":self.name})
            while True:
                msg: Message = self.inbox.get()
                if msg.data.get("tid")!=tid or msg.mtype not in ("A_BID","A_WD"):
                    continue
                break
            if msg.mtype=="A_WD":
                active.popleft(); log(f"    {bidder} se retire ({self.name})"); continue
            bid=msg.data["bid"]
            if bid>price:
                price=bid; winner=bidder; log(f"    {bidder} -> {price} € ({self.name})")
            active.rotate(-1)
        winner = winner or (active[0] if active else None)
        if winner:
            qty = self.env.agents[winner].qty
            self.sales.append((tid, qty, price))
            log(f"[{self.name}]  {winner} remporte {tid} à {price} €")
            self.broadcast(list(self.env.agents.keys()),"A_WIN",
                 {"tid":tid,"winner":winner,"price":price,"seller":self.name})
        else:
            self.broadcast(list(self.env.agents.keys()),"A_ABORT",
                 {"tid":tid,"seller":self.name})

    # ---------- négociation ----------
    def run_negotiation(self, tid: str, buyer: str):
        t = self.tickets[tid]; qty=self.env.agents[buyer].qty
        max_total, min_total = t.prix_max*qty, t.prix_min*qty
        log(f"\n[{self.name}]  Négociation {tid} (×{qty}) avec {buyer}")
        self.send(buyer,"N_START",{"tid":tid,"price":max_total,"seller":self.name})
        for _ in range(3):
            msg: Message = self.inbox.get()
            if msg.mtype!="N_OFFER" or msg.data["tid"]!=tid: continue
            offer=msg.data["offer"]
            ok = offer>=min_total and random.choice([True,False])
            log(f"    offre {offer} € – {'OK' if ok else 'rej'} ({self.name})")
            if ok:
                self.sales.append((tid, qty, offer))
                self.send(buyer,"N_OK",{"tid":tid,"price":offer,"seller":self.name}); return
            self.send(buyer,"N_REJ",{"tid":tid,"seller":self.name})
        self.send(buyer,"N_FAIL",{"tid":tid,"seller":self.name})

    # ---------- boucle ----------
    def run(self):
        self.collect_requests()
        for tid, buyers in self.requests.items():
            if len(buyers)>1: self.run_auction(tid,buyers)
            elif buyers:      self.run_negotiation(tid,buyers[0])
        self.broadcast(list(self.env.agents.keys()),"END",{})

class Buyer(Agent):
    def __init__(self, name: str, env: Environment,
                 budget: float, depart: str, arriver: str, date: str,
                 qty: int = 1):
        super().__init__(name, env)
        self.budget  = budget
        self.qty     = qty
        self.depart  = depart
        self.arriver = arriver
        self.date    = date
        self.tid_chosen: Optional[str] = None
        self.pending, self.engaged = set(), False
        self.last_offer = None

    # offre aléatoire (total) valide
    def rand_bid(self, current: float) -> Optional[float]:
        lo = current + 1
        return None if lo > self.budget else round(random.uniform(lo, self.budget), 2)

    def run(self):
        # requête
        self.send("*", "REQ",
                  {"depart": self.depart, "arriver": self.arriver, "date": self.date})
        total_sup = sum(isinstance(a, Supplier) for a in self.env.agents.values())
        remaining_end, had_resp = total_sup, False
        while True:
            msg: Message = self.inbox.get()
            if self.tid_chosen is None and msg.mtype in ("A_CALL", "N_START"):
                self.tid_chosen = msg.data["tid"]

            if msg.mtype == "END":
                remaining_end -= 1; self.pending.discard(msg.sender)
                if remaining_end == 0:
                    if not had_resp:
                        log(f"[{self.name}] Aucun fournisseur nʼa le trajet "
                            f"{self.depart}->{self.arriver} le {self.date}")
                    break
                continue
            if msg.data.get("tid") and msg.data["tid"] != self.tid_chosen:
                continue
            if self.tid_chosen is None:
                continue

            # --- enchère ---
            if msg.mtype == "A_CALL":
                had_resp = True; self.engaged = True
                s = msg.data["seller"]; self.pending.add(s)
                bid = self.rand_bid(msg.data["price"])
                if bid is None:
                    self.send(s, "A_WD", {"tid": self.tid_chosen})
                else:
                    self.send(s, "A_BID", {"tid": self.tid_chosen, "bid": bid})

            elif msg.mtype in ("A_WIN", "A_ABORT"):
                had_resp = True; self.pending.discard(msg.data["seller"])
                if self.engaged and not self.pending: break

            # --- négociation ---
            elif msg.mtype == "N_START":
                had_resp = True; self.engaged = True
                s = msg.data["seller"]; self.pending.add(s)
                self.last_offer = round(random.uniform(0.45, 0.75) * self.budget, 2)
                self.send(s, "N_OFFER",
                          {"tid": self.tid_chosen, "offer": self.last_offer})

            elif msg.mtype == "N_REJ":
                s = msg.data["seller"]
                room = max(0, self.budget - self.last_offer)
                inc  = round(random.uniform(1, 0.5 * room), 2)
                self.last_offer = min(self.budget, self.last_offer + inc)
                self.send(s, "N_OFFER",
                          {"tid": self.tid_chosen, "offer": self.last_offer})

            elif msg.mtype in ("N_OK", "N_FAIL"):
                had_resp = True; self.pending.discard(msg.data["seller"])
                if self.engaged and not self.pending: break

# ──────────────────────────── Coalitions (DP) ──────────────────────
def value_of_coalition(group: List[Buyer]) -> float:
    """Exemple : économie 5 % du budget total par membre supplémentaire."""
    k = len(group)
    if k == 1:
        return 0.0
    base = sum(b.budget for b in group)
    return 0.05 * (k-1) * base

def coalition_dp(buyers: List[Buyer]) -> List[List[Buyer]]:
    n = len(buyers); id2b = dict(enumerate(buyers))
    @lru_cache(maxsize=None)
    def f(mask: int):
        if mask == 0:
            return 0.0, ()
        best_val, best_part = -1e18, ()
        sub = mask
        while sub:
            grp = [id2b[i] for i in range(n) if sub & (1 << i)]
            val = value_of_coalition(grp)
            rest_val, rest_part = f(mask ^ sub)
            if val + rest_val > best_val:
                best_val = val + rest_val; best_part = (sub,) + rest_part
            sub = (sub-1) & mask
        return best_val, best_part
    _, parts = f((1 << n) - 1)
    return [[id2b[i] for i in range(n) if m & (1 << i)] for m in parts]

class SuperBuyer(Buyer):
    def __init__(self, members: List[Buyer]):
        name   = "Coal[" + ",".join(b.name for b in members) + "]"
        env    = members[0].env
        budget = sum(b.budget for b in members)
        qty    = len(members)
        super().__init__(name, env, budget,
                         members[0].depart, members[0].arriver,
                         members[0].date, qty=qty)
        self.members = members

def build_super_buyers(all_buyers: List[Buyer]) -> List[Buyer]:
    supers: List[Buyer] = []
    groups: Dict[tuple, List[Buyer]] = {}
    for b in all_buyers:
        key = (b.depart, b.arriver, b.date)
        groups.setdefault(key, []).append(b)
    for buyers in groups.values():
        if len(buyers) == 1:
            supers.append(buyers[0]); continue
        for coal in coalition_dp(buyers):
            supers.append(SuperBuyer(coal))
    return supers


import json

def load_scenario(path: str) -> dict:
    with open(path, "r") as f:
        return json.load(f)

def build_world_from_json(env: Environment, data: dict) -> List[Agent]:
    suppliers, buyers = [], []

    
    for s in data["suppliers"]:
        tickets = [Ticket(**t) for t in s["tickets"]]
        suppliers.append(Supplier(s["name"], env, tickets))

    
    for b in data["buyers"]:
        buyers.append(
            Buyer(b["name"], env, b["budget"],
                  b["depart"], b["arriver"], b["date"])
        )

    for ag in suppliers + buyers:
        env.register(ag)
    return suppliers + buyers



import matplotlib.pyplot as plt
from matplotlib.cm import ScalarMappable
from matplotlib.colors import Normalize
from collections import defaultdict
import pandas as pd

def print_stats_and_plots(suppliers: List[Supplier]) -> None:
   
    
    names, sold_cnt, revenue = [], [], []
    per_ticket: dict[str, dict] = defaultdict(lambda: {"qty": 0, "tot": 0.0})

    log("\n=== Statistiques détaillées ===")
    for s in suppliers:
        sold = sum(q for _, q, _ in s.sales)
        rev  = sum(p for *_, p in s.sales)
        names.append(s.name)
        sold_cnt.append(sold)
        revenue.append(rev)

        for tid, qty, tot in s.sales:
            pu = tot / qty
            log(f"   • {tid:<5}  qty={qty:<2}  PU={pu:>6.2f} €  total={tot:>7.2f} €")
            per_ticket[tid]["qty"] += qty
            per_ticket[tid]["tot"] += tot

    
    df = (pd.DataFrame.from_dict(per_ticket, orient="index")
            .reset_index()
            .rename(columns={"index": "Ticket", "qty": "Qté", "tot": "Montant"}))
    df["PU"] = (df["Montant"] / df["Qté"]).round(2)
    df = df.sort_values("Qté", ascending=False)


    df_simple = df[["Ticket", "PU", "Montant"]]
    df_simple.to_csv("pu_par_ticket.csv", index=False, float_format="%.2f")
    log(f"[Sauvegardé] pu_par_ticket.csv ({len(df_simple)} lignes)")

    
    plt.figure(figsize=(10, 4))
    plt.bar(names, sold_cnt, color="#1f77b4")
    plt.title("Billets vendus par fournisseur")
    plt.ylabel("Qté"); plt.xticks(rotation=45, ha="right")
    plt.tight_layout(); plt.show()

    plt.figure(figsize=(10, 4))
    plt.bar(names, revenue, color="#ff7f0e")
    plt.title("Recettes par fournisseur")
    plt.ylabel("€"); plt.xticks(rotation=45, ha="right")
    plt.tight_layout(); plt.show()

    
    fig, ax = plt.subplots(figsize=(12, 0.35*len(df) + 1))
    cmap  = plt.get_cmap("viridis")
    norm  = Normalize(df["PU"].min(), df["PU"].max())
    colors = cmap(norm(df["PU"]))

    ax.barh(df["Ticket"], df["Qté"], color=colors)
    ax.invert_yaxis()
    ax.set_xlabel("Quantité vendue")
    ax.set_title("Quantité et prix unitaire moyen par billet")

    sm = ScalarMappable(cmap=cmap, norm=norm)
    sm.set_array([])
    fig.colorbar(sm, ax=ax, orientation="vertical", label="PU moyen (€)")

    
    for i, (qty, pu, tot) in df[["Qté", "PU", "Montant"]].iterrows():
        ax.text(qty + 0.15, i, f"PU {pu:.2f}€ | Tot {tot:.2f}€",
                va="center", fontsize=8)

    plt.tight_layout()
    plt.show()


def main_manual():
    data = load_scenario("large_scenario.json")
    env  = Environment()
    agents = build_world_from_json(env, data)
    suppliers = [a for a in agents if isinstance(a, Supplier)]
    buyers_raw = [a for a in agents if isinstance(a, Buyer)]

    buyers_final = build_super_buyers(buyers_raw)
    env.agents.clear()
    for ag in suppliers + buyers_final: env.register(ag)

    log("\n=== Catalogue ===")
    for s in suppliers:
        log(f"{s.name}:")
        for t in s.tickets.values():
            log(f"  • {t.tid}  {t.depart}->{t.arriver} {t.date}"
                f"  [{t.prix_min}…{t.prix_max}] €")

    for ag in suppliers + buyers_final: ag.start()
    for b in buyers_final: b.join()
    log("\n=== Fin de la simulation ===")

    
    print_stats_and_plots(suppliers)

if __name__ == "__main__":
    random.seed(42)
    main_manual()
