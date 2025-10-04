BEGIN {
    pkt_sent = 0;           # Nombre total de paquets envoyés
    pkt_received = 0;       # Nombre de paquets reçus
    pkt_dropped = 0;        # Nombre de paquets perdus
    t1 = 0.0;               # Temps de début
    t2 = 5.0;               # Temps de fin
}

{
    event = $1;              # Type d'événement: +, -, r, d, etc.
    time = $2;               # Temps de l'événement
    from_node = $3;          # Nœud source
    to_node = $4;            # Nœud destination
    ptype = $5;              # Type de paquet (tcp, ack, etc.)
    psize = $6;              # Taille du paquet
    flow_id = $8;            # Identifiant du flux
    seq_num = $10;           # Numéro de séquence du paquet

    # Traiter les paquets dans l'intervalle de temps [t1, t2]
    if (time >= t1 && time <= t2) {

        # Comptage des paquets envoyés (événement "+")
        if (event == "+" && ptype == "tcp") {
            pkt_sent++;
            print "DEBUG: Paquet envoyé - seq_num =", seq_num, "time =", time;
        }

        # Comptage des paquets reçus (événement "r")
        if (event == "r" && ptype == "tcp") {
            pkt_received++;
            print "DEBUG: Paquet reçu - seq_num =", seq_num, "time =", time;
        }

        # Comptage des paquets perdus (événement "d")
        if (event == "d" && ptype == "tcp") {
            pkt_dropped++;
            print "DEBUG: Paquet perdu - seq_num =", seq_num, "time =", time;
        }
    }
}

END {
    # Calcul du débit moyen
    if (pkt_sent > 0) {
        debit_mbps = (pkt_received * 8 * psize) / ((t2 - t1) * 1e6);  # En Mbps
        printf("Débit moyen : %.2f Mbps\n", debit_mbps);
    } else {
        print("Aucun paquet envoyé.");
    }

    # Calcul du taux de pertes
    if (pkt_sent > 0) {
        taux_pertes = (pkt_dropped / pkt_sent) * 100;
        printf("Taux de pertes : %.2f%%\n", taux_pertes);
    } else {
        print("Aucun paquet perdu.");
    }
}
