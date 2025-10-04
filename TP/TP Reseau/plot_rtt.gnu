# Commentaire : Script Gnuplot pour tracer le RTT des flux 1 et 2

# Définir le fichier de données
set datafile separator " "  # Les colonnes sont séparées par des espaces
set key outside             # Légende en dehors du graphe

# Définir les axes
set xlabel "Numéro d'observation"
set ylabel "RTT (secondes)"
set title "Évolution du RTT pour les flux 1 et 2"

# Tracer les courbes
plot "rtt_data.txt" using 1:2 title "Flux 1" with linespoints ls 1, \
     "rtt_data.txt" using 1:3 title "Flux 2" with linespoints ls 2

# Sauvegarder le graphe en PNG
set term png
set output "rtt_plot.png"
replot
