const routes_GI =require('express').Router()
//import fonction autorisation accede a une route
const {authorize}=require('../helper')

//import controleur secteur sauvergarde consultAll_sec
const {ajout_sec}=require("../controleur/controleur_secteur_sauvegarde")
const {ajout_sec_per}=require("../controleur/controleur_secteur_sauvegarde")
const {consultAll_sec}=require("../controleur/controleur_secteur_sauvegarde")
const {consultId_sec}=require("../controleur/controleur_secteur_sauvegarde")
const {msj_sec}=require("../controleur/controleur_secteur_sauvegarde")
const {supprime_sec}=require("../controleur/controleur_secteur_sauvegarde")
const {supprime_sec_per}=require("../controleur/controleur_secteur_sauvegarde")
const {consulter_sec_per}=require("../model/model_secteur_sauvegarde")
//import controleur periode

const {ajout_per}=require("../controleur/controleur_periode")
const {consultAll_per}=require("../controleur/controleur_periode")
const {consultSecteur_per}=require("../controleur/controleur_periode")
const {consultId_per}=require("../controleur/controleur_periode")
const {msj_per}=require("../controleur/controleur_periode")
const {supprime_per}=require("../controleur/controleur_periode")

//import controleur unite
const {ajout_un}=require("../controleur/controleur_unite")
const {consultAll_un}=require("../controleur/controleur_unite")
const {consultId_un}=require("../controleur/controleur_unite")
const {msj_un}=require("../controleur/controleur_unite")
const {supprime_un}=require("../controleur/controleur_unite")
const {consult_un_sec}=require("../controleur/controleur_unite")

//import controleur ilot
const {ajout_il}=require("../controleur/controleur_ilot")
const {consultAll_il}=require("../controleur/controleur_ilot")
const {consultId_il}=require("../controleur/controleur_ilot")
const {consult_il_sec}=require("../controleur/controleur_ilot")
const {msj_il}=require("../controleur/controleur_ilot")
const {supprime_il}=require("../controleur/controleur_ilot")

//import controleur non equipement
const {ajout_non_equ}=require("../controleur/controleur_non_equipement")
const {consultAll_non_equ, consultAll_non_equ_sec}=require("../controleur/controleur_non_equipement")
const {consultId_non_equ}=require("../controleur/controleur_non_equipement")
const {msj_non_equ}=require("../controleur/controleur_non_equipement")
const {supprime_non_equ}=require("../controleur/controleur_non_equipement")

//import controleur  equipement
const {ajout_equ}=require("../controleur/controleur_equipement")
const {consultAll_equ}=require("../controleur/controleur_equipement")
const {consultAllTotale_equ}=require("../controleur/controleur_equipement")
const {consultId_equ, consultAll_secteur_equ, consultAll_secteurDunEquipement_equ}=require("../controleur/controleur_equipement")
const {msj_equ}=require("../controleur/controleur_equipement")
const {supprime_equ}=require("../controleur/controleur_equipement")


//import controleur  batisse
const { consultAll_bat } = require('../controleur/controleur_batisse')
const {consultId_bat}=require("../controleur/controleur_batisse")
const {msj_bat}=require("../controleur/controleur_batisse")
const {supprime_bat}=require("../controleur/controleur_batisse")

//import controleur  fontaine
const { consultAll_fon} = require('../controleur/controleur_fontaine')
const {consultId_fon}=require("../controleur/controleur_fontaine")
const {msj_fon}=require("../controleur/controleur_fontaine")
const {supprime_fon}=require("../controleur/controleur_fontaine")

//import controleur  magasin
const {ajout_mag}=require("../controleur/controleur_magasin")
const { consultAll_mag} = require('../controleur/controleur_magasin')
const {consultId_mag}=require("../controleur/controleur_magasin")
const {consultId_mag_equ}=require("../controleur/controleur_magasin")
const {consultSomme_Nbr}=require("../controleur/controleur_magasin")
const {msj_mag}=require("../controleur/controleur_magasin")
const {supprime_mag}=require("../controleur/controleur_magasin")

//import controleur  relogement
const {ajout_rel}=require("../controleur/controleur_relogement")
const {consultAll_rel}=require("../controleur/controleur_relogement")
const {consultId_rel}=require("../controleur/controleur_relogement")
const {supprime_rel}=require("../controleur/controleur_relogement")


//Periode
routes_GI.get('/gI/periodes',authorize(["Ingénieur", "Directrice"]),consultAll_per)
routes_GI.get('/gI/periode/:id',authorize(["Ingénieur", "Directrice"]),consultId_per)
routes_GI.get('/gI/secteursPeriode/:id',authorize(["Ingénieur", "Directrice"]),consultSecteur_per)
routes_GI.post('/gI/add_periode',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_per)
routes_GI.put('/gI/upd_periode/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_per)
routes_GI.delete('/gI/del_periode/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_per)

//SECTEUR SAUVGARDE
routes_GI.get('/gI/secteurs_sauvegardes',authorize(["Ingénieur", "Directrice"]),consultAll_sec)
routes_GI.get('/gI/secteur_sauvegarde/:id',authorize(["Ingénieur", "Directrice"]),consultId_sec)
routes_GI.post('/gI/add_secteur',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_sec)
routes_GI.post('/gI/add_secteur_periode',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_sec_per)
routes_GI.put('/gI/upd_secteur',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_sec)
routes_GI.delete('/gI/del_secteur/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_sec)
routes_GI.delete('/gI/del_secteur_periode',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_sec_per) 


// Unite
routes_GI.get('/gI/unites',authorize(["Ingénieur", "Directrice"]),consultAll_un)
routes_GI.get('/gI/unite/:id',authorize(["Ingénieur", "Directrice"]),consultId_un)
routes_GI.get('/gI/unites_sec/:id',authorize(["Ingénieur", "Directrice"]),consult_un_sec)
routes_GI.post('/gI/add_unite',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_un)
routes_GI.put('/gI/upd_unite',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_un)
routes_GI.delete('/gI/del_unite/:id_Un/:id_sec',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_un)


// ilot
routes_GI.get('/gI/ilots',authorize(["Ingénieur", "Directrice"]),consultAll_il)
routes_GI.get('/gI/ilots_sec/:id',authorize(["Ingénieur", "Directrice"]),consult_il_sec)
routes_GI.get('/gI/ilot/:id',authorize(["Ingénieur", "Directrice"]),consultId_il)
routes_GI.post('/gI/add_ilot',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_il)
routes_GI.put('/gI/upd_ilot',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_il)
routes_GI.delete('/gI/del_ilot/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_il) 
 

//non equipement
routes_GI.get('/gI/non_equs/:id_sec/:id_Un/:id_il',authorize(["Ingénieur", "Directrice"]),consultAll_non_equ)
routes_GI.get('/gI/non_equ/:id_sec',authorize(["Ingénieur", "Directrice"]),consultAll_non_equ_sec)
routes_GI.get('/gI/non_equ/:id',authorize(["Ingénieur", "Directrice"]),consultId_non_equ)
routes_GI.post('/gI/add_non_equ',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_non_equ)
routes_GI.put('/gI/upd_non_equ',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_non_equ)
routes_GI.delete('/gI/del_non_equ/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_non_equ)


// equipement
routes_GI.get('/gI/equs/:id_sec/:id_Un/:id_il',authorize(["Ingénieur", "Directrice"]),consultAll_equ)
routes_GI.get('/gI/equs/:id_sec',authorize(["Ingénieur", "Directrice"]),consultAll_secteur_equ)
routes_GI.get('/gI/secteur/equi/:id_equ',authorize(["Ingénieur", "Directrice"]),consultAll_secteurDunEquipement_equ)
routes_GI.get('/gI/equs',authorize(["Ingénieur", "Directrice"]),consultAllTotale_equ)
routes_GI.get('/gI/equ/:id',authorize(["Ingénieur", "Directrice"]),consultId_equ)
routes_GI.post('/gI/add_equ',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_equ)
routes_GI.put('/gI/upd_equ',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_equ)
routes_GI.delete('/gI/del_equ/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_equ)


// batisse
routes_GI.get('/gI/batisses/:id_sec/:id_Un/:id_il',authorize(["Ingénieur", "Directrice"]),consultAll_bat)
routes_GI.get('/gI/batisse/:id',authorize(["Ingénieur", "Directrice"]),consultId_bat)
//routes_GI.post('/gI/add_bat',ajout_bat)
routes_GI.put('/gI/upd_batisse/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_bat)
routes_GI.delete('/gI/del_batisse/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_bat)

// fontaine
routes_GI.get('/gI/fontaines/:id_sec/:id_Un/:id_il',authorize(["Ingénieur", "Directrice"]),consultAll_fon)
routes_GI.get('/gI/fontaine/:id',authorize(["Ingénieur", "Directrice"]),consultId_fon)
routes_GI.put('/gI/upd_fontaine/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_fon)
routes_GI.delete('/gI/del_fontaine/:id',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_fon)

// magasin 
routes_GI.post('/gI/add_magasin',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_mag)
routes_GI.get('/gI/magasins/:id_sec/:id_Un/:id_il/:id_equ',authorize(["Ingénieur", "Directrice"]),consultAll_mag)
routes_GI.get('/gI/nbr_magasin/:id_sec/:id_Un/:id_il/:id_equ',authorize(["Ingénieur", "Directrice"]),consultSomme_Nbr)
routes_GI.get('/gI/magasins_equ',authorize(["Ingénieur", "Directrice"]),consultId_mag)
routes_GI.get('/gI/magasin_mag_equ',authorize(["Ingénieur", "Directrice"]),consultId_mag_equ)
routes_GI.put('/gI/upd_magasin',authorize(["Chef secteur sauvegardé", "Directrice"]),msj_mag)
routes_GI.delete('/gI/del_magasin/:id_equ/:type_activite',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_mag)


// relogement
routes_GI.post('/gI/add_relogement',authorize(["Chef secteur sauvegardé", "Directrice"]),ajout_rel)
routes_GI.get('/gI/relogements',authorize(["Ingénieur", "Directrice"]),consultAll_rel)
routes_GI.delete('/gI/del_relogement',authorize(["Chef secteur sauvegardé", "Directrice"]),supprime_rel)
routes_GI.get('/gI/relogement',authorize(["Ingénieur", "Directrice"]),consultId_rel)







module.exports=routes_GI
