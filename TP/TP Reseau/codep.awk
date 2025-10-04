BEGIN {
    FS = " "; # Délimiteur : espace
    output_file = "resultats_tcp_pert1.txt"; # Fichier de sortie
    simulation_duration = 5; # Temps total de simulation en secondes
    print "Début de l'analyse des performances TCP..." > output_file;
    print "-----------------------------------------" >> output_file;
}

$1 == "r" && $3 == 0 && $5 == "tcp"  && $2>0 && $2<10{

    received++;          # Incrémente le compteur des paquets reçus
    flux_size += $6;     # Ajoute la taille du paquet au débit total
}

#Comptage des paquets reçus avec nœud émetteur == 0
$1 == "-" && $3 == 0 && $5 == "tcp"  && $2>0 && $2<5{

    envoyer++;          # Incrémente le compteur des paquets reçus
    flux_size += $6;     # Ajoute la taille du paquet au débit total
}

#Comptage des paquets reçus avec nœud émetteur == 0
$1 == "d" && $3 == 0 && $5 == "tcp"  && $2>0 && $2<5{

    perdue++;          # Incrémente le compteur des paquets reçus
    flux_size += $6;     # Ajoute la taille du paquet au débit total
}

END {
    # Vérification que des données valides ont été trouvées
    if (received == 0) {
        print "Erreur : aucun paquet reçu avec nœud émetteur 0." >> output_file;
        exit;
    }

    # Calcul du débit moyen
    throughput = (received  / envoyer)*100; # Débit moyen en bits/s

    # Résultats
   
    print "  taux de perte :", throughput >> output_file;
    print "Durée totale de simulation :", simulation_duration, "secondes." >> output_file;
    print "-----------------------------------------" >> output_file;

    print "Analyse terminée. Résultats enregistrés dans", output_file;
}