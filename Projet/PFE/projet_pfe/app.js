const express =require('express')
const cors = require('cors');
const cookieParser = require('cookie-parser');
const app =express()
const port = 8080 

const allowCredentialsMiddleware = (req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
 
  next();
};

// Utilisation du middleware pour toutes les routes de l'application
app.use(allowCredentialsMiddleware);
app.use(cookieParser());
app.use(cors({ origin: 'http://localhost:3000', credentials: true }));




require('dotenv').config()
const bodyparser =require('body-parser')
const routes_GI=require('./route/route_GI')
const routes_GE=require('./route/route_GE')
const routes_GR=require('./route/route_GR')
const routes_GC=require('./route/route_GC')
const routes_courrier = require('./route/route_courrier')
const projet= require('./route/route_projet')
app.use(bodyparser.json())
.use(bodyparser.urlencoded({extended:true}))
.use(routes_GE)
.use(routes_GI)
.use(routes_GR) 
.use(routes_GC) 
.use(routes_courrier)
.use(projet)

/*
const forever = require('forever-monitor');
const con = require('./connection')

// Surveiller l'application avec forever
const child = new (forever.Monitor)(__filename, {
  max: 10,
  silent: true,
  args: []
});

child.on('exit', function () {
  console.log('Le serveur s\'est arrêté. Redémarrage en cours...');
});

child.start();

// Ajouter un gestionnaire pour redémarrer la connexion à la base de données si nécessaire
con.on('error', function (err) {
  console.error('Erreur de connexion à la base de données :', err);
  console.log('Tentative de reconnexion dans 5 secondes...');
  setTimeout(function () {
    con.connect(function (err) {
      if (err) {
        console.error('Reconnexion à la base de données échouée :', err);
        return;
      }
      console.log('Reconnexion à la base de données réussie.');
    });
  }, 5000);
});

 

app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
  next();
});*/
app.listen(port,()=>console.log(`notre application Node est demarre sur http://localhost:${port}`))
