BEGIN {
   # Nombre de paquets perdus
    pkt_sent_ = 0;          # Nombre total de paquets envoyés
    pkt_tcp_sent_flot0 = 0; 
    pkt_sent_flot1 = 0;          # Nombre total de paquets envoyés
    pkt_tcp_sent_flot1 = 0;     # Nombre de paquets tcp envoyés
    somme_rtt0 = 0;         # Somme des RTT pour les paquets envoyés
    nb_rtt0 = 0; 
    somme_rtt1 = 0;         # Somme des RTT pour les paquets envoyés
    nb_rtt1 = 0;           # Nombre de paquets ayant un RTT calculable
    t1 = 0.0;              # Temps de début (ex : 1.0s)
    t2 = 5.0;   
             # Temps de fin (ex : 4.0s)
   
 
}

{
    event = $1;            # Type d'événement (ex : "r", "d", "+", etc.)
    time = $2;             # Temps de l'événement
    id_noeud = $3;           # Source (nœud 2)
    couche = $4;           # Destination (nœud 2)
    flag = $5;             # Type de paquet (ex : TCP, UDP, etc.)
    type = $7;             # Taille du paquet
    taille = $8;           # Identifiant du flux
    add_mac_dst = $9;           # Source (nœud 3)
    add_mac_src = $10;          # Destination (nœud 3)
    
    # Calcul du taux de pertes et des paquets envoyés pour l'intervalle [t1, t2]


    if (t1 <= time && time <= t2) {
      
        if(event == "s" && type == "cbr" && id_noeud=="_0_"){
            pkt_sent_++;       # Un paquet a été envoyé

        }
    }  
         

        
      
   

   

}

END {
   

    # Affichage des résultats
  
     if (pkt_sent_ > 0) {
        printf(pkt_sent_);
        printf("Le débit moyen  vaut %g Mbps\n", (pkt_sent_ * 550.0 * 8) / ((t2 - t1) * 1e6));  # Débit moyen en Mbps
     }
     
}