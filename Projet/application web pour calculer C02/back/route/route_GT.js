const routes_GT = require('express').Router();
const {
  ajouterT, supprimerT, modiferConstante, getConstante, getAllTransports
} = require('../controleur/controleur_transport');
const { authorize } = require('../helper');

routes_GT.post('/gT/ajouterT', authorize(['admin']), ajouterT);
routes_GT.delete('/gT/supprimerT', authorize(['admin']), supprimerT);
routes_GT.put('/gT/modifierT', authorize(['admin']), modiferConstante);
routes_GT.post('/gT/getConstante', authorize(['admin', 'user']), getConstante);
routes_GT.get('/gT/getAllTransports', authorize(['admin', 'user']), getAllTransports);

module.exports = routes_GT;
