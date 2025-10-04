# API Documentation (fetch requests)

> **Tous les endpoints** nécessitent un token dans les headers sauf pour la **création de compte** et la **connexion**.

### Headers communs (sauf pour création/connexion) :
```json
headers: {
  "Content-Type": "application/json",
  "auth_token": "XXXXXXXXXXXX"
}
```

---
> Exemple de quoi mettre dans les **endpoints**, à **adapter** dans les requêtes.
---

## Compte

### Connexion  
**POST**  
`http://192.168.75.60/api/gC/connexion`

```json
{
  "email": "John.Doe@example.com",
  "mdp": "0"
}
```

---

### Création de compte  
**POST**  
`http://192.168.75.60/api/gC/creation`

```json
{
  "nom": "silverhand",
  "prenom": "jonnny",
  "email": "jonnny.silverhand@example.com",
  "mdp": "votreMotDePasse"
}
```

---

### Nombre d'utilisateurs  
**GET**  
`http://192.168.75.60/api/gC/nbrUser`

---

### Supprimer un utilisateur  
**DELETE**  
`http://192.168.75.60/api/gC/supprimerU`

```json
{
  "nom": "silverhand",
  "prenom": "jonnny"
}
```

---

## Historique

### Historique par mois (tableau)  
**POST**  
`http://192.168.75.60/api/gH/historiqueUserMoisT`

```json
{
  "email": "azwaw0gmail.com",
  "mois": "04",
  "annee": "2025"
}
```

---

### Ajouter un historique (la date se remplit seule)  
**POST**  
`http://192.168.75.60/api/gH/ajouterHistorique`

```json
{
  "email": "azwaw0gmail.com",
  "date": "",
  "duree": "20000.0",
  "co2": "1200.0",
  "distance": "400.5",
  "villeD": "Lyon",
  "villeA": "Paris",
  "transports": "1"
}
```

---

### Quantité CO2 annuelle  
**POST**  
`http://192.168.75.60/api/gH/qauntiteCo2Annee`

```json
{
  "email": "azwaw0gmail.com",
  "annee": "2025"
}
```

---

### Quantité CO2 mensuelle  
**POST**  
`http://192.168.75.60/api/gH/qauntiteCo2Mois`

```json
{
  "email": "azwaw0gmail.com",
  "mois": "04",
  "annee": "2025"
}
```

---

### Historique par mois (détails)  
**POST**  
`http://192.168.75.60/api/gH/historiqueUserMois`

```json
{
  "email": "azwaw0gmail.com",
  "mois": "04",
  "annee": "2025"
}
```

---

## Transports

### Ajouter un transport  
**POST**  
`http://192.168.75.60/api/gT/ajouterT`

```json
{
  "nomT": "driving-car",
  "constante": "10"
}
```

---

### Supprimer un transport  
**DELETE**  
`http://192.168.75.60/api/gT/supprimerT`

```json
{
  "nomT": "TrainCharbon"
}
```

---

### Récupérer une constante d'un transport 
**POST**  
`http://192.168.75.60/api/gT/getConstante`

```json
{
  "nomT": "TrainCharbon"
}
```

---

### Modifier la constante d'un transport  
**PUT**  
`http://192.168.75.60/api/gT/modifierT`

```json
{
  "nomT": "TrainCharbon",
  "constante": "1400"
}
```

---

### Récupérer tous les transports  
**GET**  
`http://192.168.75.60/api/gT/getAllTransports`
