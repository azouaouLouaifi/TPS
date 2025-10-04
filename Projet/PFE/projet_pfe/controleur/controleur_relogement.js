const {ajouter_rel}=require("../model/model_relogement")
const {consulterAll_rel}=require("../model/model_relogement")
const {consulter_rel}=require("../model/model_relogement")
const {supprimer_rel}=require("../model/model_relogement")

// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")



exports.ajout_rel= async (req, res) => {
    try {
      const {id_bat,date } = req.body;
      await ajouter_rel({ id_bat,date })
      
     const token = req.headers.authorization;

      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter une relogement de id:${id_bat} date:${date}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
    } catch (err) {
    if(err==='ER_DUP_ENTRY') res.send(' relogement deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_rel= async (req, res) => {
    try {
      const relogements = await consulterAll_rel()
      res.json( relogements)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
   exports.consultId_rel=async (req, res) => {
    try {
        const {date}=req.body
        const relogement= await consulter_rel(date)
        if (relogement.length<=0) return res.send('relogement not found');
        res.json( relogement)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

 


  exports.supprime_rel=async (req, res) => {
    try {

        const {id_bat,date } = req.body;
      await supprimer_rel({ id_bat,date })
     const token = req.headers.authorization;

      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer le rolegement de id:${id_bat} date :${date}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      const message=`La relogement a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }