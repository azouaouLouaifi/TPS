const routes_GE =require('express').Router()
//import controleur enquete nature proprietaire
const {ajout_enq_np}=require("../controleur_GE/controleur_enq_np")
const {consultAll_enq_np}=require("../controleur_GE/controleur_enq_np")
const {consultId_enq_np}=require("../controleur_GE/controleur_enq_np")
const {msj_enq_np}=require("../controleur_GE/controleur_enq_np")
const {supprime_enq_np}=require("../controleur_GE/controleur_enq_np")

//import fonction autorisation accede a une route
const {authorize}=require('../helper')




// controleur enquete nature proprietaire

routes_GE.get('/gE/enquetes_natures',authorize(["Ingénieur", "Directrice"]),consultAll_enq_np)
routes_GE.get('/gE/enquete_nature',authorize(["Ingénieur", "Directrice"]),consultId_enq_np)
routes_GE.post('/gE/add_enquete_nature',authorize(["Chef de département", "Directrice"]),ajout_enq_np)
routes_GE.put('/gE/upd_enquete_nature',authorize(["Chef de département", "Directrice"]),msj_enq_np)
routes_GE.delete('/gE/del_enquete_nature',authorize(["Chef de département", "Directrice"]),supprime_enq_np)


//import controleur enquete conservation proprietaire


const {ajout_enq_cons}=require("../controleur_GE/controleur_enq_cons")
const {consultAll_enq_cons}=require("../controleur_GE/controleur_enq_cons")
const {ajout_enq_all_cons}=require("../controleur_GE/controleur_enq_cons")
const {consultId_enq_cons}=require("../controleur_GE/controleur_enq_cons")
const {msj_enq_cons}=require("../controleur_GE/controleur_enq_cons")
const {supprime_enq_cons}=require("../controleur_GE/controleur_enq_cons")



// controleur enquete conservation 

routes_GE.get('/gE/enquetes_conservations',authorize(["Ingénieur", "Directrice"]),consultAll_enq_cons)
routes_GE.get('/gE/enquete_conservation',authorize(["Ingénieur", "Directrice"]),consultId_enq_cons)
routes_GE.post('/gE/add_enquete_conservation',authorize(["Chef de département", "Directrice"]),ajout_enq_cons)
routes_GE.post('/gE/add_enquetes_conservations',authorize(["Chef de département", "Directrice"]),ajout_enq_all_cons)
routes_GE.put('/gE/upd_enquete_conservation',authorize(["Chef de département", "Directrice"]),msj_enq_cons)
routes_GE.delete('/gE/del_enquete_conservation/:id_equ/:jour/:mois/:anne/:realisateur',authorize(["Chef de département", "Directrice"]),supprime_enq_cons)


 


module.exports=routes_GE
