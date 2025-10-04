const {verifChamps_un}=require("../fonction_GI/fonction_upd")


const {ajouter_un}=require("../model/model_unite")
const {consulterAll_un}=require("../model/model_unite")
const {consulter_un}=require("../model/model_unite")
const {miseajour_un}=require("../model/model_unite")
const {supprimer_un}=require("../model/model_unite")
const {consulter_sec}=require("../model/model_secteur_sauvegarde")
const {consulter_un_sec}=require("../model/model_unite")

// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")  
const{rechercher_username}=require("../model_GC/model_compte")



exports.ajout_un= async (req, res) => {
    try {
      const {id_Un, nomU, descr, id_sec} = req.body.data;
      console.log({id_Un, nomU, descr, id_sec})
      /*const secteur= await consulter_sec(id_sec)
      if (secteur.length<=0) return res.send('Secteur not found');*/

      await ajouter_un({ id_Un, nomU, descr, id_sec })
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur=getUserId(token)
        const description =`${envoyeur} a ajouter l'unite de id:${id_Un} nom:${nomU}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      
 
      res.json({ id_Un,nomU,descr,id_sec })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('unite deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.consultAll_un= async (req, res) => {
    try {
      const unites = await consulterAll_un()
      res.json( unites)
      //res.render("affichage_secteurs",{secteurs:secteurs});
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_un=async (req, res) => {
    try {
        const unite= await consulter_un(req.params.id)
        if (unite.length<=0) return res.send('Unite not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json( unite)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  
exports.consult_un_sec=async (req, res) => {
    try {
        //const {id_sec}=req.body
        console.log(req.params.id)
        const unites= await consulter_un_sec(req.params.id)
        console.log(unites)
       // console.log(unites)
        //if (unite.length<=0) return res.send('Unite not found');
        res.json( unites)

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_un= async (req, res) => {
    try {           
      const {nomU,descr, id_Un, id_sec, nomS } = req.body.data;
      await miseajour_un( {nomU,descr, id_Un, id_sec })
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier l'unite de id:${req.params.id} dans le secteur ${nomS}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json({nomU,descr})
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_un=async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      await supprimer_un({id_sec, id_Un})
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer l'unite de id: ${id_Un} dans le secteur id: ${id_sec}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json('supp')
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }