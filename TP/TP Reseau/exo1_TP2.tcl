# ===========================
# 1) Initialisation du simulateur
# ===========================
set ns [new Simulator]

# Pour générer un fichier de trace classique
set tracefile [open out.tr w]
$ns trace-all $tracefile

# (Optionnel) Fichier de trace au format NAM pour visualisation
set namfile [open out.nam w]
$ns namtrace-all $namfile

# Procédure de fin de simulation
proc finish {} {
    global ns tracefile namfile
    $ns flush-trace
    close $tracefile
    close $namfile
    exit 0
}

# ===========================
# 2) Création des nœuds
# ===========================
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]

# ===========================
# 3) Création des liens
# ===========================
# Tous les liens (sauf n3-n4) : 1 Mbps, 10 ms, DropTail
# Lien n3-n4 : 1 Mbps, 50 ms, DropTail
# La taille de file est de 50 par défaut, sauf sur (n2-n3) où l'on fixe 10.

# Lien n0 <-> n2
$ns duplex-link $n0 $n2 1Mb 10ms DropTail
$ns queue-limit $n0 $n2 50

# Lien n2 <-> n1
$ns duplex-link $n2 $n1 1Mb 10ms DropTail
$ns queue-limit $n2 $n1 50

# Lien n2 <-> n3 (file d’attente = 10)
$ns duplex-link $n2 $n3 1Mb 10ms DropTail
$ns queue-limit $n2 $n3 10

# Lien n3 <-> n4 (50 ms)
$ns duplex-link $n3 $n4 1Mb 50ms DropTail
$ns queue-limit $n3 $n4 50

# (Facultatif) Si vous avez besoin d’un lien direct n0 <-> n3 ou n1 <-> n4
# (en fonction de la figure exacte), ajoutez-les ici :
# $ns duplex-link $n0 $n3 1Mb 10ms DropTail
# $ns queue-limit $n0 $n3 50
# ...

# ===========================
# 4) Configuration des agents et applications TCP
# ===========================
# -- Flot 1 : TCP depuis n0 -> n3 ----------------

# Création agent TCP
set tcp1 [new Agent/TCP]
$tcp1 set packetSize_ 1000  ;# 1 ko ~ 1000 octets
$ns attach-agent $n0 $tcp1

# Puits (TCPSink)
set sink1 [new Agent/TCPSink]
$ns attach-agent $n3 $sink1

# Connexion (côté TCP -> côté TCPSink)
$ns connect $tcp1 $sink1

# Application FTP sur tcp1
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1

# -- Flot 2 : TCP depuis n1 -> n4 ----------------

set tcp2 [new Agent/TCP]
$tcp2 set packetSize_ 1000  ;# 1 ko
$ns attach-agent $n1 $tcp2

set sink2 [new Agent/TCPSink]
$ns attach-agent $n4 $sink2

$ns connect $tcp2 $sink2

set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2

# ===========================
# 5) Programmation du démarrage / arrêt des flux
# ===========================
# Les deux flux démarrent à t=0.0
$ns at 0.0 "$ftp1 start"
$ns at 0.0 "$ftp2 start"

# Flot 1 s’arrête à t=5.0
$ns at 5.0 "$ftp1 stop"

# Flot 2 s’arrête à t=10.0
$ns at 10.0 "$ftp2 stop"

# Fin de la simulation à t=12.0
$ns at 12.0 "finish"

# Lancement de la simulation
$ns run
