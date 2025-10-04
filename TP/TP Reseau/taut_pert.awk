BEGIN {
    FS = " "; # Délimiteur : espace
    output_file = "resultats_tcp_tauxdepert2.txt"; # Fichier de sortie
    simulation_duration = 5; # Temps total de simulation en secondes
    print "Début de l'analyse des performances TCP..." > output_file;
    print "-----------------------------------------" >> output_file;
    received = 0; # Initialisation du compteur de paquets reçus
    deleted = 0;  # Initialisation du compteur de paquets perdus
}

# Comptage des paquets émis avec les conditions spécifiées
$1 == "-" && $3 == 1 && $4 == 2 && $5 == "tcp" && $2 > 0 && $2 <= 5 && $10 == 4.0 && $9 == 1.0 {
    received++;          # Incrémente le compteur des paquets reçus
    flux_size += $6;     # Ajoute la taille du paquet au débit total
    last_value = $11;    # Met à jour la valeur de la colonne $11
}

# Comptage des paquets perdus avec les mêmes conditions
$1 == "d" && $10 == 4.0 && $9 == 1.0 && $5 == "tcp" && $2 > 0 && $2 <= 5 {
    deleted++;           # Incrémente le compteur des paquets perdus
}

END {
    # Vérification que des données valides ont été trouvées
    if (received == 0) {
        print "Erreur : aucun paquet émis correspondant aux conditions spécifiées." >> output_file;
        exit;
    }

    # Calcul du taux de perte
    loss_rate = (deleted / received) * 100; # Taux de perte en pourcentage

    # Résultats
    print "Résultats de l'analyse des performances TCP :" >> output_file;
    print "  Nombre de paquets émis :", received >> output_file;
    print "  Nombre de paquets perdus :", deleted >> output_file;
    print "  Taux de perte :", loss_rate, "%" >> output_file;
    print "Durée totale de simulation :", simulation_duration, "secondes." >> output_file;
    print "-----------------------------------------" >> output_file;
    print "Analyse terminée. Résultats enregistrés dans", output_file;
}
