const {ajouter_enq_np}=require("../model_GE/model__enq_np")
const {consulterAll_enq_np}=require("../model_GE/model__enq_np")
const {consulter_enq_np}=require("../model_GE/model__enq_np")
const {miseajour_enq_np}=require("../model_GE/model__enq_np")
const {supprimer_enq_np}=require("../model_GE/model__enq_np")

const {verifChamps_enq_np}=require("../fonction_GE/fonction_upd")
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")


exports.ajout_enq_np= async (req, res) => {
    try {
      const {id_bat, prescription, date,realisateur, observation ,nature} = req.body;
     
      await ajouter_enq_np({id_bat, prescription, date, realisateur, observation ,nature})
       const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter une nouvelle enquete de nature pour la batisse de id:${id_bat}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})

      res.json({ id_bat,  prescription, date, realisateur, observation ,nature })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('enq_np deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  exports.consultAll_enq_np= async (req, res) => {
    try {
      const enquete_Ns = await consulterAll_enq_np()
      res.json(enquete_Ns)
    } catch (err) {
      console.error(err);
      res.status(500).json('Server Error');
    }
  }
   exports.consultId_enq_np=async (req, res) => {
    try {
        const {id_bat,date,realisateur}=req.body

        const enquete_N= await consulter_enq_np({id_bat,date,realisateur})
        if (enquete_N.length<=0) return res.send('enq_np not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json( enquete_N)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
  

  exports.msj_enq_np= async (req, res) => {
    try {           
      const {id_bat, prescription, date, realisateur, observation ,nature } = req.body;
      const data= await consulter_enq_np({id_bat,date,realisateur})
      const resulta= verifChamps_enq_np({prescription,observation,nature},data)
      const prescription1=resulta.prescription
      const  observation1=resulta.observation
      const nature1=resulta.nature

      
    await miseajour_enq_np({id_bat,prescription1,date,realisateur, observation1 ,nature1})
     const token = req.headers.authorization;
    const envoyeur=getUserId(token)
    const description =`${envoyeur} a modifier enquete de nature pour la batisse de id:${id_bat} Date:${date} realiser:${realisateur}`
     
     let userName=await rechercher_username('Directrice')
     userName=userName[0].userName
    await ajouter_notif({userName,description,envoyeur}) 
    res.json({id_bat, prescription1,date, realisateur, observation1 ,nature1})

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_enq_np=async (req, res) => {
    try {
      const {id_bat,date,realisateur}=req.body
      await supprimer_enq_np({id_bat,date,realisateur})
       const token = req.headers.authorization;
      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer enquete de nature pour la batisse de id:${id_bat} Date:${date} realiser:${realisateur}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})

      res.json('supprimer')
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }