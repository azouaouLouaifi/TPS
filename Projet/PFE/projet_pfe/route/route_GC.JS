const routes_GC =require('express').Router()
const {creation}=require('../controleur_GC/controleur_compte')
const {ajoutRole}=require('../controleur_GC/controleur_compte')
const {connexion}=require('../controleur_GC/controleur_compte')
const {consultation_cpt}=require('../controleur_GC/controleur_compte')
const {suppression}=require('../controleur_GC/controleur_compte')
const {consultation_notification, consultation_roles}=require('../controleur_GC/controleur_compte')
const {vu}=require('../controleur_GC/controleur_compte')
const {vu_all}=require('../controleur_GC/controleur_compte')

//import fonction autorisation accede a une route
const {authorize}=require('../helper')

routes_GC.post("/gC/creation",authorize(["Directrice"]),creation)
//routes_GC.post("/gC/creation",creation)
routes_GC.post("/gC/ajoutRole",authorize(["Directrice"]),ajoutRole)
routes_GC.post("/gC/marque_vu_notif",vu)
routes_GC.post("/gC/marque_vu_notif_all",vu_all)
routes_GC.get("/gC/consultation_cpt",   authorize(["Directrice", "Secrétaire", "Chef de département"]),consultation_cpt)
routes_GC.get("/gC/consultation_notif",consultation_notification)
routes_GC.get("/gC/roles",consultation_roles)
routes_GC.post("/gC/login",connexion)
routes_GC.delete("/gC/del_cpt/:userName",authorize(["Directrice"]),suppression)



module.exports=routes_GC
