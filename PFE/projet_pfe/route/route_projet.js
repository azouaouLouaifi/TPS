const projet = require('express').Router()

const {authorize}=require('../helper')

const {getAll_projet, insert_projet, remove_projet, update_projet} = require('../controleur_projet/controleur_projet')
const {getAll_mission, insert_mission, remove_mission, update_mission} = require('../controleur_projet/controleur_mission')

projet.get('/projet',authorize(["Ingénieur", "Directrice"]), getAll_projet)
//projet.get('/projet/select/:id', selectID)
projet.post('/projet/add',authorize(["Chef de département", "Directrice"]), insert_projet)
projet.delete('/projet/remove/:id_projet',authorize(["Chef de département", "Directrice"]), remove_projet)
projet.put('/projet/update',authorize(["Chef de département", "Directrice"]), update_projet)

projet.get('/mission',authorize(["Ingénieur", "Directrice"]), getAll_mission)
//projet.get('/mission/select/:id', selectID_mission)
projet.post('/mission/add',authorize(["Chef de département", "Directrice"]), insert_mission)
projet.delete('/mission/remove/:id_mission/:id_projet',authorize(["Chef de département", "Directrice"]), remove_mission)
projet.put('/mission/update',authorize(["Chef de département", "Directrice"]), update_mission)


module.exports = projet