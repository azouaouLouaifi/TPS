const {ajouter_dos_rec}=require("../model_GR/model_dos_rec")
const {consulterAll_dos_rec}=require("../model_GR/model_dos_rec")
const {consulter_dos_rec}=require("../model_GR/model_dos_rec")
const {miseajour_dos_rec}=require("../model_GR/model_dos_rec")
const {supprimer_dos_rec}=require("../model_GR/model_dos_rec")
const {verifChamps_dos_rec}=require("../fonction_GR/fonction_upd")
// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

exports.ajout_dos_rec= async (req, res) => {
    try {
      const { id_equ, date,des} = req.body;
     
      await ajouter_dos_rec({ id_equ, date,des})
      const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter un dossier reclamation de id_equ:${id_equ} date:${date} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
     
      res.json({  id_equ, date,des})
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('dos_rec deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_dos_rec= async (req, res) => {
    try {
      const dossier_rs = await consulterAll_dos_rec()
      res.json(dossier_rs)
    } catch (err) {
      console.error(err);
      res.status(500).json('Server Error');
    }
  }
   exports.consultId_dos_rec=async (req, res) => {
    try {
        const { id_equ, date}=req.body

        const dossier_r= await consulter_dos_rec({ id_equ, date})
        if (dossier_r.length<=0) return res.send('dos_rec not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json( dossier_r)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_dos_rec= async (req, res) => {
    try {           
      const { id_equ, date,des } = req.body;
      const data= await consulter_dos_rec({ id_equ, date})
      const resulta= verifChamps_dos_rec({des},data)
      const des1=resulta.des
     
      
    await miseajour_dos_rec({ id_equ, date,des1})

    await ajouter_dos_rec({ id_equ, date,des})
    const token = req.headers.authorization;
    const envoyeur=getUserId(token)
    const description =`${envoyeur} a modifier un dossier reclamation de id_equ:${id_equ} date:${date} `
     
     let userName=await rechercher_username('Directrice')
     userName=userName[0].userName
    await ajouter_notif({userName,description,envoyeur})
      res.json({ id_equ, date,des1})

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_dos_rec=async (req, res) => {
    try {
      const { id_equ, date}=req.body
      await supprimer_dos_rec({ id_equ, date})
      await ajouter_dos_rec({ id_equ, date,des})
      const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer un dossier reclamation de id_equ:${id_equ} date:${date} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json('supprimer')
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }