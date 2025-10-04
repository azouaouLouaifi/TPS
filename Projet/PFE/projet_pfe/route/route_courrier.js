const routes_courrier = require('express').Router()

const {courrierA_getAll, courrierA_insert, courrierA_remove, courrierA_update} = require('../controleur_courrier/controleur_courrierA')
const {courrierD_getAll, courrierD_insert, courrierD_remove, courrierD_update} = require('../controleur_courrier/controleur_courrierD')


const {authorize}=require('../helper')

routes_courrier.get('/courrierA', authorize(["Secrétaire", "Directrice"]),courrierA_getAll)
//routes_courrier.get('/courrierA/select/:id', authorize(["Ingénieur"]),courrierA_selectID)
routes_courrier.post('/courrierA/add', authorize(["Secrétaire", "Directrice"]),courrierA_insert)
routes_courrier.delete('/courrierA/remove/:id_courrierA', authorize(["Secrétaire", "Directrice"]),courrierA_remove)
routes_courrier.put('/courrierA/update', authorize(["Secrétaire", "Directrice"]),courrierA_update)

routes_courrier.get('/courrierD', authorize(["Secrétaire", "Directrice"]),courrierD_getAll)
//routes_courrier.get('/courrierA/select/:id', authorize(["Ingénieur"]),courrierA_selectID)
routes_courrier.post('/courrierD/add', authorize(["Secrétaire", "Directrice"]),courrierD_insert)
routes_courrier.delete('/courrierD/remove/:id_courrierD', authorize(["Secrétaire", "Directrice"]),courrierD_remove)
routes_courrier.put('/courrierD/update', authorize(["Secrétaire", "Directrice"]),courrierD_update)



module.exports = routes_courrier