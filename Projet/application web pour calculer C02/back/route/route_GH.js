const routes_GH = require('express').Router();
const {
  ajouterHistorique, qauntiteCo2Mois, qauntiteCo2Annee, historiqueUserMois, historiqueUserMoisT
} = require('../controleur/controleur_historique');
const { authorize } = require('../helper');

routes_GH.post('/gH/ajouterHistorique', authorize(['admin', 'user']), ajouterHistorique);
routes_GH.post('/gH/qauntiteCo2Mois', authorize(['admin', 'user']), qauntiteCo2Mois);
routes_GH.post('/gH/qauntiteCo2Annee', authorize(['admin', 'user']), qauntiteCo2Annee);
routes_GH.post('/gH/historiqueUserMois', authorize(['admin', 'user']), historiqueUserMois);
routes_GH.post('/gH/historiqueUserMoisT', authorize(['admin', 'user']), historiqueUserMoisT);

module.exports = routes_GH;
