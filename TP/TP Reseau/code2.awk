BEGIN {
    FS = " ";                           # Délimiteur : espace
    output_file = "resultats_cbr.txt";  # Fichier de sortie
    simulation_duration = 5;            # Durée totale de simulation en secondes
    received = 0;                       # Compteur des paquets reçus
    flux_size = 0;                      # Taille totale des paquets reçus (en octets)

    print "Début de l'analyse des performances CBR..." > output_file;
    print "-----------------------------------------" >> output_file;
}

# Comptage des paquets CBR reçus (événements "r" avec type "cbr")
$1 == "r" && $4 == "MAC" && $7 == "cbr" && $2 > 0 && $2 <= simulation_duration {
    received++;                         # Incrémente le compteur des paquets reçus
    flux_size += $6;                    # Ajoute la taille du paquet à la somme totale
}
#r 0.877897682 _1_ MAC  --- 73 cbr 1520 [13a 1 0 800] ------- [0:0 1:0 30 1] [73] 1 0

END {
    # Vérification que des données valides ont été trouvées
    if (received == 0) {
        print "Erreur : aucun paquet CBR reçu pendant la simulation." >> output_file;
        exit;
    }

    # Calcul du débit moyen
    throughput = (flux_size * 8) / simulation_duration;  # Débit moyen en bits/s

    # Résultats
    print "Résultats de l'analyse des performances CBR :" >> output_file;
    print "  Nombre de paquets reçus :", received >> output_file;
    print "  Taille totale des données reçues :", flux_size, "octets" >> output_file;
    print "  Débit moyen :", throughput / 1e6, "Mbps" >> output_file;
    print "Durée totale de simulation :", simulation_duration, "secondes." >> output_file;
    print "-----------------------------------------" >> output_file;

    print "Analyse terminée. Résultats enregistrés dans", output_file;
}
