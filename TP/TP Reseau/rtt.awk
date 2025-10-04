BEGIN {
    t1 = 0.0;               # Temps de début
    t2 = 5.0;               # Temps de fin
    observation = 1;        # Numéro d'observation
    print "#Observation RTT_Flux1 RTT_Flux2" > "rtt_data.txt";
}

{
    event = $1;              # Type d'événement: +, -, r, d, etc.
    time = $2;               # Temps de l'événement
    ptype = $5;              # Type de paquet (tcp, ack, etc.)
    flow_id = $8;            # Identifiant du flux
    seq_num = $10;           # Numéro de séquence du paquet

    # Calcul du RTT pour les flux 1 et 2
    if (event == "r" && ptype == "ack") {
        if (flow_id == "0" && seq_num in send_time1) {
            rtt1 = time - send_time1[seq_num];
            print observation, rtt1, "NaN" >> "rtt_data.txt";
            delete send_time1[seq_num];
        } else if (flow_id == "1" && seq_num in send_time2) {
            rtt2 = time - send_time2[seq_num];
            print observation, "NaN", rtt2 >> "rtt_data.txt";
            delete send_time2[seq_num];
        }
        observation++;
    }

    # Enregistrement des paquets envoyés
    if (event == "+" && ptype == "tcp") {
        if (flow_id == "0") send_time1[seq_num] = time;
        if (flow_id == "1") send_time2[seq_num] = time;
    }
}
