# Guide de gestion de la VM pour le projet

## 1. Connexion à la VM
Pour vous connecter à la VM, suivez ces étapes :

1. **Télécharger la clé sur Tomuss** (au format `.pem`).
2. **Ouvrir une invite de commande**.
3. **Accéder au dossier où vous avez téléchargé la clé**.
4. Utilisez la commande suivante pour vous connecter à la VM :
    ```bash
    ssh -i {nom_de_la_clé} {votre_identifiant_lyon_pxxxxxxx}@192.168.75.60
    ```

## 2. Infrastructure du projet sur la VM
Le projet est composé de plusieurs services, chacun fonctionnant sur des ports différents. Voici un récapitulatif :

- **MySQL** : Base de données, fonctionne sur le port **3306**.
- **React** : Frontend, généré à partir du build CI GitLab.
- **Node/Express** : Backend, fonctionne sur le port **5173**.
- **Nginx** : Utilisé pour afficher le frontend (React) sur le port **80**.

### Emplacement des fichiers du projet
Tout le projet se trouve dans le dossier `/opt/app/`, avec des sous-dossiers pour chaque partie du projet :
- `/opt/app/database` : Base de données (MySQL).
- `/opt/app/frontend` : Frontend (React).
- `/opt/app/backend` : Backend (Node/Express).

## 3. Gérer le projet Node avec PM2
PM2 est utilisé pour gérer les processus Node. Voici les commandes utiles pour gérer l'application :

- **Voir les logs** :
    ```bash
    pm2 logs
    ```

- **Démarrer le serveur Node** (dans le dossier backend) :
    ```bash
    cd /opt/app/backend/
    pm2 start ecosystem.config.js
    ```

- **Redémarrer le serveur Express** (après une mise à jour du code backend) :
    ```bash
    pm2 restart trajet-api
    ```

## 4. Gérer Nginx
Nginx est utilisé pour afficher le frontend React. Si vous modifiez la configuration Nginx, vous devrez redémarrer Nginx :

- **Redémarrer Nginx** (après modification de la configuration) :
    ```bash
    sudo systemctl reload nginx
    ```

- **Fichier de configuration Nginx du projet** :  
    Le fichier de configuration est situé ici :
    ```bash
    /etc/nginx/sites-available/react-app.conf
    ```

## 5. Gérer React
Le build de l'application React est effectué automatiquement par la CI GitLab. Le code React est minimal et contient principalement les fichiers de configuration. L'application construite se trouve dans le répertoire `/opt/app/frontend/dist`.

## 6. Gérer la base de données MySQL
Si vous avez besoin de redémarrer MySQL ou d'interagir avec la base de données, voici les commandes utiles :

- **Redémarrer MySQL** :
    ```bash
    sudo systemctl restart mysql
    ```

- **Se connecter à MySQL** (en tant que root) :
    ```bash
    sudo mysql -u root
    ```

- **Accéder à la base de données OR_DB** :
    ```sql
    USE OR_DB;
    ```

- **Exécuter des requêtes SQL classiques** pour administrer la base de données.

## 7. Autres informations utiles
- Les services sont répartis sur différents ports pour éviter les conflits.
- Assurez-vous que tous les services nécessaires sont démarrés en cas de problèmes ou pour tester le projet.
- Si vous avez des questions supplémentaires ou si quelque chose ne fonctionne pas, n'hésitez pas à demander.

---

Ce guide vous permettra de gérer efficacement les différents services de la VM pour ce projet. Les ports sont modifiables pour le projet en local.
