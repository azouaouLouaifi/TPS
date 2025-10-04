const {verifChamps_fon}=require("../fonction_GI/fonction_upd")
const {ajouter_fon}=require("../model/model_fontaine")
const {consulterAll_fon}=require("../model/model_fontaine")
const {consulter_fon}=require("../model/model_fontaine")
const {miseajour_fon}=require("../model/model_fontaine")
const {supprimer_fon}=require("../model/model_fontaine")

// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")


exports.ajout_fon= async (req, res) => {
    try{
      try {
      const {ancienNom,historique,id_equ} = req.body.data;
      await ajouter_fon({ancienNom,historique,id_equ})

      // const token = req.headers.authorization;
      //const envoyeur=getUserId(token)
      //const description =`${envoyeur} a ajouter une fontaine  de id:${id_fon}`
       
      // let userName=await rechercher_username('Directrice')
      // userName=userName[0].userName
      //await ajouter_notif({userName,description,envoyeur})

      res.json({ ancienNom,historique,id_equ})
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send(' fontaine deja existant')
      
      console.error(err);
      res.status(500).send(err);
     } 
    }
    catch(err){
      console.log(err)
    }
  }

  exports.consultAll_fon= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      const id_il= req.params.id_il
      const fontaines = await consulterAll_fon({id_sec,id_Un,id_il})
      
      res.json( fontaines)
      //res.render("affichage_secteurs",{secteurs:secteurs});
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
   exports.consultId_fon=async (req, res) => {
    try {
        const fontaine= await consulter_fon(req.params.id)
        if (fontaine.length<=0) return res.send(' fontaine not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
        console.log('fontaine',fontaine[0])
       
        res.json( fontaine[0])
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_fon= async (req, res) => {
    try {           
      const {ancienNom,historique, id_equ} = req.body.data;
      /*const data= await consulter_fon(req.params.id)
      const resulta=verifChamps_fon({ancienNom,historique},data)
      const ancienNom1=resulta.ancienNom
      const historique1=resulta.historique*/
     
      await miseajour_fon({ancienNom,historique, id_equ})

      //const token = req.headers.authorization;
      //const envoyeur=getUserId(token)
      //const description =`${envoyeur} a modifier la fontaine de id:${id_equ}`
       
       //let userName=await rechercher_username('Directrice')
      // userName=userName[0].userName
      //await ajouter_notif({userName,description,envoyeur})
      
      //res.json('modifier')
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_fon=async (req, res) => {
    try {
      await supprimer_fon(req.params.id)
       const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} asupprimer la fontaine  de id:${req.params.id}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      const message=`La fontaine avec id: ${req.params.id} a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
 
      res.status(500).send('Server Error');
    }
  }