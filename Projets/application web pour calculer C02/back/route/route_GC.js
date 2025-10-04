const routes_GC = require('express').Router();
const {
  creation, connexion, nbrUser, supprimerU, Users
} = require('../controleur/controleur_compte');
const { authorize } = require('../helper');

routes_GC.post('/gC/creation', creation);
routes_GC.post('/gC/connexion', connexion);
routes_GC.get('/gC/nbrUser', authorize(['admin']), nbrUser);
routes_GC.get('/gC/Users', authorize(['admin']), Users);
routes_GC.delete('/gC/supprimerU', authorize(['admin', 'user']), supprimerU);

module.exports = routes_GC;
