const {ajouter_mag}=require("../model/model_magasin")
const {consulterAll_mag}=require("../model/model_magasin")
const {consulter_mag}=require("../model/model_magasin")
const {miseajour_mag}=require("../model/model_magasin")
const {supprimer_mag}=require("../model/model_magasin")
const {countNbr_magasin}=require("../model/model_magasin")
const { consulter_equ } = require('../model/model_equipement')

// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

exports.ajout_mag= async (req, res) => {
    try {
      const {id_equ,type_activite,nbr} = req.body.data;
      console.log('cc',{id_equ,type_activite,nbr})
      //const equipement= await consulter_equ(id_equ)
      //if (equipement.length<=0) return res.send('equipement not fomagd'); 

      await ajouter_mag({ id_equ,type_activite,nbr })
      /*const token = req.headers.authorization;
      const envoyeur=getUserId(token) 
      const description =`${envoyeur} a ajouter un local l'equipement  de id:${id_equ} Type:${type_activite} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName  
      await ajouter_notif({userName,description,envoyeur})*/ 
      res.json({ id_equ,type_activite,nbr })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('magasin deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    } 
  }

  exports.consultAll_mag= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      const id_il= req.params.id_il
      const id_equ= req.params.id_equ

      const magasins = await consulterAll_mag({id_sec,id_Un,id_il, id_equ})
      console.log('magasin: ',magasins)
      res.json(magasins)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  
  exports.consultSomme_Nbr= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      const id_il= req.params.id_il
      const id_equ= req.params.id_equ

      const magasin = await countNbr_magasin({id_sec,id_Un,id_il, id_equ})
      console.log('magasin: ',magasin)
      res.json(magasin)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_mag=async (req, res) => {
    try {
        const {id_equ}=req.body
        const magasin= await consulter_mag({id_equ})
        if (magasin.length<=0) return res.send('magasin not fomagd');
       
        res.json(magasin)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  


  exports.consultId_mag_equ=async (req, res) => {
    try {
        const {id_equ,type_activite}=req.body
        const magasin= await consulter_mag_equ({id_equ,type_activite})
        if (magasin.length<=0) return res.send('magasin not fomagd');
       
        res.json( magasin)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.msj_mag= async (req, res) => {
    try {           
      const {id_equ,type_activite,nbr} = req.body.data;

      await miseajour_mag({id_equ,type_activite,nbr})
      /*const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier le local l'equipement  de id:${id_equ} Type:${type_activite} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})*/  
      
    res.json({id_equ,type_activite,nbr})
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_mag=async (req, res) => {
    try {
        //const {id_equ,type_activite} = req.body.data;
        const id_equ= req.params.id_equ
        const type_activite= req.params.type_activite
        console.log({id_equ, type_activite})

      await supprimer_mag({id_equ,type_activite})
      /*const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer le local l'equipement  de id:${id_equ} Type:${type_activite} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})*/
      res.json('supprime') 
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }