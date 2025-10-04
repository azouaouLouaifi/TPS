const {ajouter_dos_Anss}=require("../model_GR/model_dos_Anss")
const {consulterAll_dos_Anss}=require("../model_GR/model_dos_Anss")
const {consulter_dos_Anss}=require("../model_GR/model_dos_Anss")
const {miseajour_dos_Anss}=require("../model_GR/model_dos_Anss")
const {supprimer_dos_Anss}=require("../model_GR/model_dos_Anss")
const {verifChamps_dos_rec}=require("../fonction_GR/fonction_upd")
// import pour sys notif

const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")


exports.ajout_dos_Anss= async (req, res) => {
    try {
      const { id_res, date,des} = req.body;
     
      await ajouter_dos_Anss({ id_res, date,des})
       const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter un dossier  Anss de id:${id_res} date:${date} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      
      res.json({  id_res, date,des})
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('dos_Anss deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_dos_Anss= async (req, res) => {
    try {
      const dossier_As = await consulterAll_dos_Anss()
      res.json(dossier_As)
    } catch (err) {
      console.error(err);
      res.status(500).json('Server Error');
    }
  }
   exports.consultId_dos_Anss=async (req, res) => {
    try {
        const { id_res, date}=req.body

        const dossier_A= await consulter_dos_Anss({ id_res, date})
        if (dossier_A.length<=0) return res.send('dos_Anss not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json( dossier_A)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_dos_Anss= async (req, res) => {
    try {           
      const { id_res, date,des } = req.body;
      const data= await consulter_dos_Anss({ id_res, date})
      const resulta= verifChamps_dos_rec({des},data)
      const des1=resulta.des
     
      
    await miseajour_dos_Anss({ id_res, date,des1})
     const token = req.headers.authorization;
    const envoyeur=getUserId(token)
    const description =`${envoyeur} a modifier  un dossier  Anss de id:${id_res} date:${date}  `
     
     let userName=await rechercher_username('Directrice')
     userName=userName[0].userName
    await ajouter_notif({userName,description,envoyeur})
      res.json({ id_res, date,des1})

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_dos_Anss=async (req, res) => {
    try {
      const { id_res, date}=req.body
      await supprimer_dos_Anss({ id_res, date})
       const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer un dossier Anss de id:${id_res} date:${date} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json('supprimer')
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }