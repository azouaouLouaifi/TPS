const {ajouter_il}=require("../model/model_ilot")
const {consulterAll_il}=require("../model/model_ilot")
const {consulter_il}=require("../model/model_ilot")
const {miseajour_il}=require("../model/model_ilot")
const {supprimer_il}=require("../model/model_ilot")
const {consulter_il_sec}=require("../model/model_ilot")
//import constler id unite
const {consulter_un}=require("../model/model_unite")

// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

exports.ajout_il= async (req, res) => {
    try {
      const {id_il,nomI,id_Un} = req.body.data;
      console.log({id_il,nomI,id_Un})
      const unite= await consulter_un(id_Un)
      if (unite.length<=0) return res.send('Unite not found');

      await ajouter_il({ id_il,nomI,id_Un})
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter un ilot de id:${id_il} nom:${nomI} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      
      //res.redirect('/secteurs_sauvegardes');
      
      res.json({ id_il,nomI,id_Un })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('ilot deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_il= async (req, res) => {
    try {
      const ilots = await consulterAll_il()
      res.json(ilots)
    } catch (err) {
      console.error(err);
      res.status(500).json('Server Error');
    }
  }
   exports.consultId_il=async (req, res) => {
    try {
        const ilot= await consulter_il(req.params.id)
        if (ilot.length<=0) return res.send('Ilot not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        const message = `l'Ilot de id:${req.params.id}`
        res.json(ilot)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  
exports.consult_il_sec=async (req, res) => {
    try {

      const {id_sec}=req.body
        const ilots= await consulter_il_sec({id_sec})
        if (ilots.length<=0) return res.send('Ilot not found');

        res.json(ilots)

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_il= async (req, res) => {
    try {           
      const {nomI, id_il } = req.body.data;
      await miseajour_il({nomI, id_il })
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier l'ilot de id:${req.params.id}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json({nomI, id_il })
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_il=async (req, res) => {
    try {
      await supprimer_il(req.params.id)
      const token = JSON.parse(req.cookies.auth_token); 
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer l'ilot de id:${req.params.id}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      const message=`L'ilot avec id: ${req.params.id} a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }