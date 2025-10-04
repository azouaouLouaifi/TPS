<!-- LTeX: language=fr -->
# Rendu du Mini Projet "Y Microblogging"

**Votre travail devra être rendu sous forme d’un projet déposé sur la Forge Lyon 1, les dates limites sont dans le fichier README.md de ce projet.**

Le mini-projet noté est le fil rouge de tous les TPs. Vous commencez à
travailler sur la base de code au [TP1](TP1-java/), et vous ajoutez
des fonctionnalités tout en améliorant la qualité du code dans la
suite.

Les consignes ci-dessous sont à respecter **impérativement** pour le rendu.

## Fraude

On vous demande un travail original et non une copie. Vous n'êtes pas autorisés
à fournir votre code à d'autres équipes ni à utiliser le code d'autres équipes
(y compris le code écrit les années passées, y compris par vous-même si vous
êtes redoublant). Vous n'êtes pas non plus autorisés à utiliser le code généré
par des IA génératives (ChatGPT, Copilot, etc.). Si vous êtes en difficulté,
demandez de l'aide à vos enseignants, mais n'utilisez pas vos difficultés comme
excuse pour frauder.

## Rapport

Votre rendu inclura un rapport, au format PDF (consignes pour le rendu
ci-dessous), en français, qui doit comprendre obligatoirement, en plus d'une
présentation globale du projet (rapide : ne répétez pas l'énoncé) :

### Une section « design patterns » [BAREME: 6]

  Cette section donne une motivation des choix
  d’architecture (et des patterns choisis), et leur explication en s’aidant de
  diagrammes appropriés et adaptés au degré de précision et au type
  d’explication. Donc des diagrammes de classe, mais pas que cela, et pas de
  plats de spaghettis générés automatiquement représentant tout le code.
  
### Une section « éthique » [BAREME: 4]

  Cette section devra discuter de la problématique des
  plateformes de microblogging et plus généralement des réseaux sociaux. Quelles
  sont les parties prenantes ? Quels sont les enjeux ? Quel impact ce genre
  d'outils a sur la société ?

  Nous avons implémenté un système de scoring, analogue aux systèmes de
  recommandations qui sont implémentés dans la plupart des réseaux sociaux pour
  proposer du contenu « pertinent » à l'utilisateur. Quels sont les avantages,
  et quels sont les risques de ces systèmes de recommandation ?

  Quelles sont les mesures, légales et techniques, pour limiter
  ou éliminer les risques ? Lesquels sont mis en œuvre dans la réalité ? En
  avez-vous mis en place dans votre TP, si oui, lesquelles (il s'agit d'un petit
  projet scolaire, on ne vous demande pas une application vraiment sécurisée,
  mais vous devriez être capable de discuter des limites de votre
  implémentation. Vous pouvez aussi mettre en place des mesures simplistes et
  discuter de ce qu'il faudrait faire dans une vraie application) ?

  L'objectif n'est pas de donner un avis subjectif (la question « les réseaux
  sociaux sont ils une bonne chose ? » est hors sujet ici), mais de présenter
  les questions importantes et les éléments objectifs de réponse autour de la
  question des réseaux sociaux et des systèmes de recommandation de contenu.
  Appuyez-vous autant que possible sur des articles existants, en citant vos
  sources. Il s'agit donc avant tout d'un travail de bibliographie de votre
  part. Attention, un travail de bibliographie n'est pas un exercice de
  copier-coller : on attend de vous un travail de synthèse. Vous pouvez
  copier-coller des sources externes uniquement dans le cadre du [droit de
  courte citation](https://fr.wikipedia.org/wiki/Droit_de_courte_citation)
  (portions de textes courtes, bien délimitées par exemple par des guillemets,
  mention de la source, ...). Il est interdit, pas seulement dans le cadre d'un
  travail scolaire, de laisser entendre que vous êtes l'auteur d'un texte que
  vous n'avez pas écrit vous-même. On rappelle que l'utilisation d'IA générative
  est interdite sur ce TP.

  Pour vous aider, voici quelques références intéressantes sur le sujet :

  * [Bulle de filtres](https://fr.wikipedia.org/wiki/Bulle_de_filtres), concept développé par Eli Parisier.

  * [Économie de l'attention](https://fr.wikipedia.org/wiki/%C3%89conomie_de_l%27attention) sur Wikipedia.

  * [Twitter supprime une grande partie de ses capacités de modération](https://www.lemonde.fr/pixels/article/2022/11/14/twitter-supprime-une-grande-partie-de-ses-capacites-de-moderation_6149773_4408996.html) dans la rubrique « Pixels » du journal « Le Monde ».

  * [My Video Went Viral. Here's Why](https://www.youtube.com/watch?v=fHsa9DqmId8) sur la chaîne Youtube Veritasium, qui analyse l'effet de l'algorithme de recommandation de Youtube sur le succès d'une vidéo.

  * [« NazTok » : un rapport révèle comment des groupes néonazis utilisent l’algorithme de TikTok à leur avantage](https://www.lemonde.fr/pixels/article/2024/07/29/naztok-un-rapport-revele-comment-des-groupes-neonazis-utilisent-l-algorithme-de-tiktok-a-son-avantage_6261156_4408996.html?lmd_medium=al&lmd_campaign=envoye-par-appli&lmd_creation=android&lmd_source=default) dans la rubrique « Pixels » du journal « Le Monde ».

  * En amont des systèmes de recommandation, la création de continu calculée pour répondre aux demandes : [How Netflix Used Data to Create House of Cards: A Revolutionary Approach to Content Creation](https://medium.com/@danial.a/how-netflix-used-data-to-create-house-of-cards-a-revolutionary-approach-to-content-creation-b9a114630ddc)

  La liste n'est bien entendu pas exhaustive. Pensez à vos enseignants qui
  liront des dizaines de rapports, surprenez-nous, apprenez-nous des choses ! Si
  votre relecteur se dit « Ah tiens, je ne savais pas » ou « Ah tiens, je n'y
  avais pas pensé » en lisant votre rapport, vous avez atteint l'objectif !
  
### Une section « tests » [BAREME: 1]

  Vous décrirez les tests manuels que vous avez
  réalisés. Vos tests automatiques (le code Java des tests et les commentaires
  associés) devraient se suffire à eux-mêmes, il n'est pas nécessaire de les
  re-documenter dans le rapport (sauf si vous avez fait des choses
  extraordinaires qui méritent une documentation externe).

## Qualité du code

### Style

Assurez-vous que votre programme respecte toujours le style imposé
(`mvn test`, qui doit lancer checkstyle).

Bien sûr, respecter le style doit se faire en corrigeant (si besoin)
votre code, mais *pas* en modifiant le fichier de configuration de
checkstyle.

### Design-pattern

Assurez-vous d'avoir appliqué toutes les consignes du
[TP 3](TP3-patterns/).

### Tests et intégration continue

Vérifiez que l'intégration continue mise en place au
[TP 2](TP2-outils/) fonctionne toujours.

Les tests automatisés tels que décrits au [TP 4](TP4-tests/) doivent
être lancés automatiquement par `mvn test`, et doivent tous passer
avec succès.

### Portabilité

Clonez, compilez et exécutez votre code **sur une machine vierge**
(c'est-à-dire sur laquelle vous n'avez installé aucune dépendance, ni
configuré le compte utilisateur de façon particulière). Une grande
partie du barème est liée à l'exécution de votre travail. Il est
important que nous arrivions à l'exécuter **directement**. "Ça marche
chez moi" n'est pas une excuse et une démo *a posteriori* ne permet
pas de remonter une note de TP.

## Projet Forge et TOMUSS

Les projets seront rendus en binômes. La date limite est indiquée sur
la page d'accueil du cours.

**Ajoutez les utilisateurs @matthieu.moy, @LIONEL.MEDINI, avec le niveau de privilège
"reporter" (ou plus, mais *pas* "guest") à votre projet sur la forge**

Dans la feuille TOMUSS M1 Informatique, indiquez l'URL de votre projet dans la case URL_Projet_MIF01 (l'URL doit ressembler à
`https://forge.univ-lyon1.fr/<login>/mif01`). Il faut impérativement
**que la commande `git clone <url>` fonctionne, et que cette commande récupère la dernière version de votre projet.**
Autrement dit la branche par défaut doit contenir la dernière version du projet si vous avez
plusieurs branches. Tous les membres du binôme doivent entrer **exactement** la même URL (au caractère près).

Pensez à remplir dès à présent TOMUSS indiquant votre URL.
Le dépôt ne sera relevé qu’après la date de rendu.

Votre dépôt sur la Forge devra contenir :

- un répertoire `microblog/` (le répertoire doit impérativement avoir exactement ce nom)
- un fichier maven (`microblog/pom.xml`) pour le build du projet
- les sources (fichiers Java)
- le rapport en PDF (6 pages maximum, format libre. La limitation de pages est indicative, si vous avez vraiment besoin de plus vous pouvez dépasser un peu, mais restez raisonnables et concis), dans un fichier qui doit impérativement s'appeler `rapport.pdf` à la racine du dépôt Git.

Vous pouvez laisser les autres fichiers et répertoires.

## Barème indicatif (le barème sera ramené à 20), à utiliser comme checklist pour vérifier que vous avez tout fait

<!--
git grep -h '\[BAREME: [^]]\]' | sed 's/^#*\s*//' | sed 's/^\(.*\)\[BAREME: \([^]]*\)\]\(.*\)$/| \2 | \1\3 |/'
-->

| Points | Critère |
|--------|---------|
| Malus si retard | Rendu avant la deadline |
| Malus jusqu'à -5 | Malus éventuel pour non-respect des consignes (en plus de la note automatique) |
| malus -3 si absent | Présence des bons fichiers (rapport.pdf, microblog/pom.xml, .gitlab-ci.yml) |
| 1 | Le projet est compilable (mvn compile) |
| 1 | Le programme se lance correctement (mvn exec:java) |
| 1 | Fonctionnalité : Affichage des messages en bookmark en premier  |
| 1 | Fonctionnalité : Stockage de la date de publication  |
| 1 | Fichiers ignorés avec .gitignore  |
| 1 | Au moins une issue fermée dans GitLab  |
| 1 | Les tests passent (mvn test -Dcheckstyle.skip)  |
| 2 | Absence d'erreur avec checkstyle  |
| 1 | Au moins une merge-request fermée sur GitLab  |
| 3 | Pattern Modèle-Vue-Contrôleur  |
| 1 | Flexibilité du modèle MVC : deux vues correctement synchronisées  |
| 1 | Principes GRASP bien respectés  |
| 5 | Design-patterns (création, structure, SOLID, ...) : au moins 3 autres patterns que MVC ou GRASP  |
| 1 | Fonctionnalité : bonus de score pour les messages récents  |
| 1 | Fonctionnalité : autre règle de scoring des messages  |
| 1 | Fonctionnalité : seuil de score pour afficher les messages  |
| 2 | Fonctionnalité : choix de la stratégie de calcul de score et d'affichage  |
| 1 | Fonctionnalité : une troisième stratégie de scoring et d'affichage  |
| 2 | Fonctionnalité : configuration initiale (utilisateurs et messages prédéfinis)  |
| 1 | Fonctionnalité : Modification de l'interface pour les messages (bouton pour supprimer)  |
| 3 | Autres extensions  |
| 3 | Tests automatiques |
| 6 | Rapport : Une section « design patterns »  |
| 4 | Rapport : Une section « éthique »  |
| 1 | Rapport : Une section « tests »  |
| 3 | Rapport : qualité globale des explications |
| Malus jusqu'à 5 | Rapport : malus éventuel pour mauvaise forme et orthographe (0 si la forme est OK, pas de note positive) |
| Bonus jusqu'à +3 | Bonus éventuel pour choses en plus (pas de note négative) |
