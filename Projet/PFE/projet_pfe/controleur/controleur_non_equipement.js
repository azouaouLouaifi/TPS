const {verifChamps_non_equ}=require("../fonction_GI/fonction_upd")
//const {testeserveur}=require('../helper.js')
const {ajouter_non_equ}=require("../model/model_non_equipement")
const {consulterAll_non_equ}=require("../model/model_non_equipement")
const {consulterAll_non_equ_sec}=require("../model/model_non_equipement")
const {consulter_non_equ}=require("../model/model_non_equipement")
const {miseajour_non_equ}=require("../model/model_non_equipement")
const {supprimer_non_equ}=require("../model/model_non_equipement")
//import constler id ilot
const {consulter_il}=require("../model/model_ilot")


// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

exports.ajout_non_equ= async (req, res) => {
    try{
      try {
      const {id_n_equ,nomNE,adresse,historique,type,id_il} = req.body.data;
      console.log({id_n_equ,nomNE,adresse,historique,type,id_il} )

      //const ilot= await consulter_il(id_il)
      //if (ilot.length<=0) return res.send('Ilot not found');
      await ajouter_non_equ({id_n_equ,nomNE,adresse,historique,type,id_il})
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter un non equipement de id:${id_n_equ} nom:${nomNE}  type:${type}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json({ id_n_equ,nomNE,adresse,historique,type,id_il })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('non equipement deja existant')
      
      console.error(err); 
      res.status(500).send(err);
     } 
    }
    catch(err){
      
      console.log(err)
    }
  }

  exports.consultAll_non_equ_sec= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const n_equipements = await consulterAll_non_equ_sec(id_sec)
      res.json (n_equipements)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.consultAll_non_equ= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      const id_il= req.params.id_il
      console.log('info iiiiii ', id_sec,id_Un,id_il)
      const n_equipements = await consulterAll_non_equ({id_sec,id_Un,id_il})
      console.log('resulta recherch', n_equipements)
      res.json (n_equipements)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_non_equ=async (req, res) => {
    try {
        const n_equipement= await consulter_non_equ(req.params.id)
        if (n_equipement.length<=0) return res.send('NON equipement not found');
       
     
        res.json( n_equipement)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_non_equ= async (req, res) => {
    try {           
      const {id_n_equ, nomNE, adresse, historique, type} = req.body.data;
     // const data= await consulter_non_equ(req.params.id)
      //const resulta=verifChamps_non_equ({nomNE,adresse,historique,type})
      //const nom1=resulta.nom
      //const historique1=resulta.historique
      //const adresse1=resulta.adresse
     // const type1=resulta.type
      


      await miseajour_non_equ({id_n_equ, nomNE, adresse, historique, type})
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier le non equipement de id:${id_n_equ} nom:${nomNE}  type:${type}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      
      res.json({id_n_equ, nomNE, adresse, historique, type})
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_non_equ=async (req, res) => {
    try {
      await supprimer_non_equ(req.params.id)
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer un non equipement de id:${req.params.id} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json('supprime')
    } catch (err) {
      console.error(err);
 
      res.status(500).send('Server Error');
    }
  }   