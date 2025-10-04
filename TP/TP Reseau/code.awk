BEGIN {
    FS = " "; # Délimiteur : espace
    output_file = "resultats_tcp_flux2.txt"; # Fichier de sortie
    simulation_duration = 5; # Temps total de simulation en secondes
    print "Début de l'analyse des performances TCP..." > output_file;
    print "-----------------------------------------" >> output_file;
}

#Comptage des paquets reçus avec nœud émetteur == 0
$1 == "r" && $3 == 3 && $4 == 4 && $5 == "tcp"  && $2>0 && $2 <=5{

    received++;          # Incrémente le compteur des paquets reçus
    flux_size += $6;     # Ajoute la taille du paquet au débit total
    last_value = $11;    # Met à jour la valeur de la colonne $10
}

END {
    # Vérification que des données valides ont été trouvées
    if (received == 0) {
        print "Erreur : aucun paquet reçu avec nœud émetteur 0." >> output_file;
        exit;
    }

    # Calcul du débit moyen
    throughput = (last_value * 8*1040) / 5; # Débit moyen en bits/s

    # Résultats
    print "Résultats de l'analyse des performances TCP :" >> output_file;
    print "  Nombre de paquets reçus (nœud émetteur 0) :", last_value >> output_file;
    print "  Débit moyen :", throughput, "bits/s" >> output_file;
    print "Durée totale de simulation :", simulation_duration, "secondes." >> output_file;
    print "-----------------------------------------" >> output_file;
    print "Analyse terminée. Résultats enregistrés dans", output_file;
}