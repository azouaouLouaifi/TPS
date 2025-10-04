const {verifChamps_per}=require("../fonction_GI/fonction_upd")

const {ajouter_per}=require("../model/model_periode")
const {consulterAll_per}=require("../model/model_periode")
const {consulter_Secteurs_periode}=require("../model/model_periode")
const {consulter_per}=require("../model/model_periode")
const {miseajour_per}=require("../model/model_periode")
const {supprimer_per}=require("../model/model_periode")

// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")
exports.ajout_per= async (req, res) => {
    try {
      const {nomP,description } = req.body.data;
      console.log( {nomP,description } )
      await ajouter_per({ nomP,description })
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
     const desc =`${envoyeur} a ajouter une periode de nom:${nomP} description:${description}`
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description: desc,envoyeur})
      res.json({ nomP,description })
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_per= async (req, res) => {
    try {
      const periodes = await consulterAll_per()
      res.json( periodes)
      //res.render("affichage_secteurs",{secteurs:secteurs});
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.consultSecteur_per= async (req, res) => {
    try {
      const periodes = await consulter_Secteurs_periode(req.params.id)
      res.json( periodes)
      
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_per=async (req, res) => {
    try {
        const periode= await consulter_per(req.params.id)
        if (periode.length<=0) return res.send('Secteur not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        const message = `la periode de id:${req.params.id}`
        res.json( periode)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_per= async (req, res) => {
    try {           
      const { nomP,des } = req.body;
      const data= await consulter_per(req.params.id)
      const resulta=verifChamps_per({nomP,des},data)
      const nomP1=resulta.nomP
      const des1=resulta.des
      await miseajour_per({ nomP1,des1 },req.params.id)
      const token = JSON.parse(req.cookies.auth_token);

      const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier la periode de Nom::${nomP}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      
      res.json({ nomP1,des1 })
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_per=async (req, res) => {
    try {
      await supprimer_per(req.params.id)
      const token = JSON.parse(req.cookies.auth_token);

      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer la  periode   
         de id:${req.params.id} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      const message=`La periode avec id: ${req.params.id} a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
