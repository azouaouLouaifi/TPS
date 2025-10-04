
import threading, queue, time, random, json
from dataclasses import dataclass
from typing import Dict, List, Optional
from collections import deque, defaultdict

PRINT_LOCK = threading.Lock()
def log(msg: str):
    with PRINT_LOCK:
        print(msg)


@dataclass
class Message:
    sender: str
    receiver: str      
    mtype: str
    data: Dict

class Environment:
    def __init__(self):
        self.agents: Dict[str,"Agent"] = {}
    def register(self, a:"Agent"):
        self.agents[a.name] = a
    def send(self, m:Message):
        if m.receiver == "*":
            for ag in self.agents.values():
                if ag.name != m.sender:
                    ag.inbox.put(m)
        else:
            self.agents[m.receiver].inbox.put(m)


class Agent(threading.Thread):
    def __init__(self, name:str, env:Environment):
        super().__init__(daemon=True)
        self.name = name; self.env = env
        self.inbox: "queue.Queue[Message]" = queue.Queue()
    def send(self,to,mtype,data): self.env.send(Message(self.name,to,mtype,data))
    def broadcast(self,targets,mtype,data):
        for t in targets:
            if t != self.name:
                self.env.send(Message(self.name,t,mtype,data))


@dataclass
class Ticket:
    tid:str; depart:str; arriver:str; date:str
    prix_min:float; prix_max:float


class Supplier(Agent):
    COLLECT_SEC = 1.0
    def __init__(self,name,env,tickets:List[Ticket]):
        super().__init__(name,env)
        self.tickets = {t.tid:t for t in tickets}
        self.requests: Dict[str,List[str]] = {}
        self.sales: List[tuple[str,int,float]] = []  

  
    def collect_requests(self):
        deadline = time.time()+self.COLLECT_SEC
        while time.time()<deadline:
            try:
                m:Message = self.inbox.get(timeout=.1)
            except queue.Empty:
                continue
            if m.mtype!="REQ": continue
            dep,arr,dat = m.data["depart"],m.data["arriver"],m.data["date"]
            for t in self.tickets.values():
                if (t.depart,t.arriver,t.date)==(dep,arr,dat):
                    self.requests.setdefault(t.tid,[]).append(m.sender)
                    break

  
    def run_auction(self,tid:str,buyers:List[str]):
        t=self.tickets[tid]; price=t.prix_min
        active=deque(buyers); winner=None
        log(f"\n[{self.name}]  Enchère {tid} – départ {price} € – {', '.join(active)}")
        while len(active)>1:
            bdr=active[0]
            self.send(bdr,"A_CALL",{"tid":tid,"price":price,"seller":self.name})
            while True:
                m:Message=self.inbox.get()
                if m.data.get("tid")!=tid or m.mtype not in ("A_BID","A_WD"):
                    continue
                break
            if m.mtype=="A_WD":
                active.popleft(); log(f"    {bdr} se retire ({self.name})"); continue
            bid=m.data["bid"]
            if bid>price:
                price=bid; winner=bdr
                log(f"    {bdr} -> {price} € ({self.name})")
            active.rotate(-1)
        winner = winner or (active[0] if active else None)
        if winner:
            self.sales.append((tid,1,price))
            log(f"[{self.name}]  {winner} remporte {tid} à {price} €")
            self.broadcast(list(self.env.agents),"A_WIN",
                            {"tid":tid,"winner":winner,"price":price,"seller":self.name})
        else:
            self.broadcast(list(self.env.agents),"A_ABORT",{"tid":tid,"seller":self.name})

 
    def run_negotiation(self,tid:str,buyer:str):
        t=self.tickets[tid]
        log(f"\n[{self.name}]  Négociation {tid} avec {buyer}")
        self.send(buyer,"N_START",{"tid":tid,"price":t.prix_max,"seller":self.name})
        for _ in range(3):
            m:Message=self.inbox.get()
            if m.mtype!="N_OFFER" or m.data["tid"]!=tid: continue
            offer=m.data["offer"]
            ok = offer>=t.prix_min and random.choice([True,False])
            log(f"    offre {offer} € – {'OK' if ok else 'rej'} ({self.name})")
            if ok:
                self.sales.append((tid,1,offer))
                self.send(buyer,"N_OK",{"tid":tid,"price":offer,"seller":self.name}); return
            self.send(buyer,"N_REJ",{"tid":tid,"seller":self.name})
        self.send(buyer,"N_FAIL",{"tid":tid,"seller":self.name})

    
    def run(self):
        self.collect_requests()
        for tid,lst in self.requests.items():
            if len(lst)>1: self.run_auction(tid,lst)
            else:          self.run_negotiation(tid,lst[0])
        self.broadcast(list(self.env.agents),"END",{})


class Buyer(Agent):
    def __init__(self,name,env,budget,depart,arriver,date):
        super().__init__(name,env)
        self.budget=budget; self.depart=depart; self.arriver=arriver; self.date=date
        self.tid_chosen:Optional[str]=None
        self.pending=set(); self.engaged=False; self.last_offer=None
    def rand_bid(self,current):       # retourne None si dépasse le budget
        lo=current+1
        return None if lo>self.budget else round(random.uniform(lo,self.budget),2)

    def run(self):
        self.send("*","REQ",{"depart":self.depart,"arriver":self.arriver,"date":self.date})
        tot_sup=sum(isinstance(a,Supplier) for a in self.env.agents.values())
        remaining_end,had_resp=tot_sup,False
        while True:
            msg:Message=self.inbox.get()
            if self.tid_chosen is None and msg.mtype in ("A_CALL","N_START"):
                self.tid_chosen=msg.data["tid"]
            if msg.mtype=="END":
                remaining_end-=1; self.pending.discard(msg.sender)
                if remaining_end==0 and not had_resp:
                    log(f"[{self.name}] Aucun fournisseur nʼa {self.depart}->{self.arriver} {self.date}")
                    break
                if remaining_end==0: break
                continue
            if msg.data.get("tid") and msg.data["tid"]!=self.tid_chosen:
                continue
            if self.tid_chosen is None: continue

            
            if msg.mtype=="A_CALL":
                had_resp=True; self.engaged=True
                s=msg.data["seller"]; self.pending.add(s)
                bid=self.rand_bid(msg.data["price"])
                if bid is None: self.send(s,"A_WD",{"tid":self.tid_chosen})
                else:           self.send(s,"A_BID",{"tid":self.tid_chosen,"bid":bid})
            elif msg.mtype in ("A_WIN","A_ABORT"):
                self.pending.discard(msg.data["seller"])
                if self.engaged and not self.pending: break

           
            elif msg.mtype=="N_START":
                had_resp=True; self.engaged=True
                s=msg.data["seller"]; self.pending.add(s)
                self.last_offer=round(random.uniform(.45,.75)*self.budget,2)
                self.send(s,"N_OFFER",{"tid":self.tid_chosen,"offer":self.last_offer})
            elif msg.mtype=="N_REJ":
                s=msg.data["seller"]
                room=max(0,self.budget-self.last_offer)
                inc=round(random.uniform(1,.5*room),2)
                self.last_offer=min(self.budget,self.last_offer+inc)
                self.send(s,"N_OFFER",{"tid":self.tid_chosen,"offer":self.last_offer})
            elif msg.mtype in ("N_OK","N_FAIL"):
                self.pending.discard(msg.data["seller"])
                if self.engaged and not self.pending: break


import matplotlib.pyplot as plt
from matplotlib.cm import ScalarMappable
from matplotlib.colors import Normalize
import pandas as pd


import pandas as pd
import matplotlib.pyplot as plt
from collections import defaultdict
# ----------------------------------------------------------------


def stats_and_plots(suppliers: List[Supplier]) -> None:
   
    names, sold, rev  = [], [], []
    per_tic: dict[str, dict] = defaultdict(lambda: {"qty": 0, "tot": 0.0})

   
    for s in suppliers:
        names.append(s.name)
        sold.append(len(s.sales))               
        rev.append(sum(p for *_, p in s.sales)) 

        for tid, q, tot in s.sales:            
            per_tic[tid]["qty"] += q
            per_tic[tid]["tot"] += tot

   
    df = (pd.DataFrame.from_dict(per_tic, orient="index")
            .reset_index()
            .rename(columns={"index": "Ticket", "qty": "Qté", "tot": "Montant"})
            .sort_values("Qté", ascending=False))
    df["PU"] = (df["Montant"] / df["Qté"]).round(2)

    df[["Ticket", "PU", "Montant"]].to_csv("pu_par_ticket2.csv",
                                           index=False, float_format="%.2f")
    log("[Sauvegardé] pu_par_ticket.csv")

    
    plt.figure(figsize=(10, 4))
    plt.bar(names, sold, color="steelblue")
    plt.title("Billets vendus par fournisseur")
    plt.ylabel("Nombre de transactions")
    plt.xticks(rotation=45, ha="right")
    plt.tight_layout(); plt.show()

    plt.figure(figsize=(10, 4))
    plt.bar(names, rev, color="seagreen")
    plt.title("Chiffre d’affaires (€) par fournisseur")
    plt.ylabel("€")
    plt.xticks(rotation=45, ha="right")
    plt.tight_layout(); plt.show()

    
    fig, ax = plt.subplots(figsize=(12, 0.35*len(df) + 1))
    ax.barh(df["Ticket"], df["Qté"], color="tab:orange")
    ax.invert_yaxis()                     
    ax.set_xlabel("Quantité vendue")
    ax.set_title("Quantité & Montant total par billet")

    for i, (q, tot) in df[["Qté", "Montant"]].iterrows():
        ax.text(q + 0.1, i, f"Tot {tot:.2f} €", va="center", fontsize=8)

    plt.tight_layout()
    plt.show()



def load_scenario(path:str)->dict:
    with open(path,"r") as f: return json.load(f)

def build_world_from_json(env:Environment,d:dict)->List[Agent]:
    agents=[]
    for s in d["suppliers"]:
        tickets=[Ticket(**t) for t in s["tickets"]]
        agents.append(Supplier(s["name"],env,tickets))
    for b in d["buyers"]:
        agents.append(Buyer(b["name"],env,b["budget"],
                            b["depart"],b["arriver"],b["date"]))
    for ag in agents: env.register(ag)
    return agents


def main(path_json:str|None=None):
    env=Environment()
    if path_json:
        agents=build_world_from_json(env,load_scenario(path_json))
    else:
        agents=build_world(env)           
    suppliers=[a for a in agents if isinstance(a,Supplier)]
    log("\n=== Catalogue ===")
    for s in suppliers:
        log(f"{s.name}:")
        for t in s.tickets.values():
            log(f"  • {t.tid}  {t.depart}->{t.arriver} {t.date} [{t.prix_min}…{t.prix_max}] €")

   
    for ag in agents: ag.start()
    for a in agents:
        if isinstance(a,Buyer): a.join()  

    stats_and_plots(suppliers)
    log("\n=== Fin de la simulation ===")


if __name__=="__main__":
    random.seed(42)
    main("large_scenario.json")  
