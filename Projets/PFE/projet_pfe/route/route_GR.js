const routes_GR =require('express').Router()
//import controleur dossier reclamation
const {ajout_dos_rec}=require("../controleur_GR/controleur_dos_rec")
const {consultAll_dos_rec}=require("../controleur_GR/controleur_dos_rec")
const {consultId_dos_rec}=require("../controleur_GR/controleur_dos_rec")
const {msj_dos_rec}=require("../controleur_GR/controleur_dos_rec")
const {supprime_dos_rec}=require("../controleur_GR/controleur_dos_rec")

//import fonction autorisation accede a une route
const {authorize}=require('../helper')

// route controleur dossier reclamation

routes_GR.get('/gR/dossier_reclamations',authorize(["Cellule écoute","User"]),consultAll_dos_rec,)
routes_GR.get('/gR/dossier_reclamation',authorize(["Cellule écoute","User"]),consultId_dos_rec)
routes_GR.post('/gR/add_dossier_reclamation',authorize(["Cellule écoute"]),ajout_dos_rec)
routes_GR.put('/gR/upd_dossier_reclamation',authorize(["Cellule écoute"]),msj_dos_rec)
routes_GR.delete('/gR/del_dossier_reclamation',authorize(["Cellule écoute"]),supprime_dos_rec)


//import controleur dossier Anss

const {ajout_dos_Anss}=require("../controleur_GR/controleur_dos_Anss")
const {consultAll_dos_Anss}=require("../controleur_GR/controleur_dos_Anss")
const {consultId_dos_Anss}=require("../controleur_GR/controleur_dos_Anss")
const {msj_dos_Anss}=require("../controleur_GR/controleur_dos_Anss")
const {supprime_dos_Anss}=require("../controleur_GR/controleur_dos_Anss")



// route controleur dossier Anss

routes_GR.get('/gR/dossiers_Anss',authorize(["Cellule écoute","User"]),consultAll_dos_Anss)
routes_GR.get('/gR/dossier_Anss',authorize(["Cellule écoute","User"]),consultId_dos_Anss)
routes_GR.post('/gR/add_dossier_Anss',authorize(["Cellule écoute"]),ajout_dos_Anss)
routes_GR.put('/gR/upd_dossier_Anss',authorize(["Cellule écoute"]),msj_dos_Anss)
routes_GR.delete('/gR/del_dossier_Anss',authorize(["Cellule écoute"]),supprime_dos_Anss)



module.exports=routes_GR