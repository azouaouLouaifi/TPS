const {ajouter_sec}=require("../model/model_secteur_sauvegarde")
const {ajouter_sec_per}=require("../model/model_secteur_sauvegarde")
const {verifChamps_sec}=require("../fonction_GI/fonction_upd")

const {consulter_sec_per}=require("../model/model_secteur_sauvegarde")
const {consulterAll}=require("../model/model_secteur_sauvegarde")
const {consulter_sec}=require("../model/model_secteur_sauvegarde")
const {miseajour_sec}=require("../model/model_secteur_sauvegarde")
const {supprimer_sec}=require("../model/model_secteur_sauvegarde")
const {supprimer_sec_per}=require("../model/model_secteur_sauvegarde")

exports.ajout_sec= async (req, res) => {
    try {
      console.log(req.body.data.periodes)
      const { codeW, nomS, classement, periodes } = req.body.data;
      await ajouter_sec({ codeW, nomS, classement })
      const secteurs = await consulterAll()
      const secteur = secteurs.filter((sec)=> (sec.nomS === nomS && sec.codeW === codeW ))[0]
      if(periodes && secteur) for(i=0; i< periodes.length; i++){await ajouter_sec_per({id_sec: secteur.id_sec, id_per: periodes[i].id_per}) } 
      //res.redirect('/secteurs_sauvegardes');
      res.status(200).json({ codeW, nomS, classement }) 
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_sec= async (req, res) => {
    try {
      const secteurs = await consulterAll()
     
      for(let i=0;i<secteurs.length;i++){
        let periodes_sec=await consulter_sec_per(secteurs[i].id_sec)
        
        let tableau_periode=periodes_sec.map((obj) => obj.nomp).join(', ')

        secteurs[i].periodes=tableau_periode
      }
  
      res.json( secteurs)
      //res.render("affichage_secteurs",{secteurs:secteurs});
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_sec=async (req, res) => {
    try {
        const secteur= await consulter_sec(req.params.id)
        if (secteur.length<=0) return res.send('Secteur not found');
        let periodes_sec=await consulter_sec_per(secteur.id_sec)
        
        //res.render("affichage_secteurs",{secteurs:secteur});
        console.log(secteur)
        res.json( secteur, periodes_sec)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  
 
  exports.msj_sec= async (req, res) => { 
    try {           
      const { codeW, nomS, classement, id_sec } = req.body.data;

      const data= await consulter_sec(id_sec)
      /*const resulta=verifChamps_sec({ codeW, nomS, classement },data)
      const codeW1=resulta.codeW
      const nom1=resulta.nom
      const classement1=resulta.classement*/

      await miseajour_sec( { codeW, nomS, classement, id_sec })
      
    res.json({ codeW, nomS, classement })
     
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_sec=async (req, res) => {
    try {
      console.log('mamimomo',req)
      await supprimer_sec(req.params.id)
      const message=`Le secteur avec id: ${req.params.id} a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error mami');
    }
  }


  exports.ajout_sec_per=async (req, res) => {
    try {
      
      const {id_sec,id_per}= req.body.data
      console.log('add',  {id_sec,id_per})
      await ajouter_sec_per({id_sec,id_per})
      const message=`Lajout reussie`
      res.json(message)
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  



  exports.supprime_sec_per=async (req, res) => {
    try {
      const {id_sec,id_per}= req.body
      console.log('delete',  {id_sec,id_per})
      await supprimer_sec_per({id_sec,id_per})
      const message=` a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }