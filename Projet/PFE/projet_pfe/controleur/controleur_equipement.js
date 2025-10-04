const {verifChamps_equ}=require("../fonction_GI/fonction_upd")
const {ajouter_equ}=require("../model/model_equipement")
const {consulterAll_equ}=require("../model/model_equipement")
const {consulterAllTotale_equ, consulterAll_secteur_equ, consulterAll_secteur_d_equipement_equ}=require("../model/model_equipement")
const {consulterAll_enq_cons, }=require("../model_GE/model_enq_cons")
const {consulter_equ}=require("../model/model_equipement")
const {miseajour_equ}=require("../model/model_equipement")
const {supprimer_equ}=require("../model/model_equipement")

//import constler id ilot
const {consulter_il}=require("../model/model_ilot")

// import controleur ajout pour batisse et fontaine
const{ajout_bat}=require("./controleur_batisse")
const { ajout_fon } = require('./controleur_fontaine.js')
const{msj_bat}=require("./controleur_batisse")
const { msj_fon } = require('./controleur_fontaine.js')

//import { ajout_bat } from "./controleur_batisse"

 
// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")


exports.ajout_equ= async (req, res) => {
    try{
      try {
      const {id_equ,nomE,fonctionE,topologie,statut,adresse,type,id_il} = req.body.data;
        
      const ilot= await consulter_il(id_il)
      if (ilot.length<=0) return res.send('Ilot not found');
      await ajouter_equ({id_equ,nomE,fonctionE,topologie,statut,adresse,type,id_il})

      if(type==="batisse")  await ajout_bat(req,res)
      if(type==="fontaine") await ajout_fon(req,res)
     
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter un  equipement de id:${id_equ} nom:${nomE}  type:${type}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      
      res.json({ id_equ,nomE,fonctionE,topologie,statut,adresse,type,id_il })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send(' equipement deja existant')
      
      console.error(err);
      res.status(500).send(err);
     } 
    }
    catch(err){
      
      console.log(err)
    }
  }

  /*exports.consultAll_equ= async (req, res) => {
    try {
      const equipements = await consulterAll_equ()
      const message = `LISTE DES  equipement`
      res.json (equipements)
      //res.render("affichage_secteurs",{secteurs:secteurs});
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }*/

  exports.consultAll_equ= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      const id_il= req.params.id_il
      const equipements = await consulterAll_equ({id_sec,id_Un,id_il})
      //console.log('equip', equipements)
      res.json (equipements)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.consultAll_secteurDunEquipement_equ= async (req, res) => {
    try {
      const id_equ= req.params.id_equ
      
      const equipements = await consulterAll_secteur_d_equipement_equ(id_equ)
      console.log('equip', equipements)
      res.json (equipements)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.consultAll_secteur_equ= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const equipements = await consulterAll_secteur_equ(id_sec)
      res.json (equipements)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.consultAllTotale_equ= async (req, res) => {
    try {
      const equipements = await consulterAllTotale_equ()
      const equipement_id= equipements.map((obj)=>(obj.id_equ))

      const equipements_enq = await consulterAll_enq_cons()
      res.json ({equipement_id, equipements_enq})
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_equ=async (req, res) => {
    try {
        const equipement= await consulter_equ(req.params.id)
        if (equipement.length<=0) return res.send(' equipement not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json(equipement)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_equ= async (req, res) => {
    try {           
      const {id_equ,nomE,fonctionE,topologie,statut,adresse,type} = req.body.data;
      
      
      await miseajour_equ({id_equ,nomE,fonctionE,topologie,statut,adresse})
      if(type==="batisse")  await msj_bat(req,res)
      if(type==="fontaine") await msj_fon(req,res)

      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier un  equipement de id:${id_equ} nom:${nomE}  type:${type}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json('ajouter')
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_equ=async (req, res) => {
    try {
      await supprimer_equ(req.params.id)
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer un  equipement de id:${req.params.id} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json('supprimer')
    } catch (err) {
      console.error(err);
 
      res.status(500).send('Server Error');
    }
  }