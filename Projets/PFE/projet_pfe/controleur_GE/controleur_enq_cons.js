const {ajouter_enq_cons}=require("../model_GE/model_enq_cons")
const {consulterAll_enq_cons}=require("../model_GE/model_enq_cons")
const {consulter_enq_cons}=require("../model_GE/model_enq_cons")
const {miseajour_enq_cons}=require("../model_GE/model_enq_cons")
const {supprimer_enq_cons}=require("../model_GE/model_enq_cons")
const {verifChamps_enq_cons}=require("../fonction_GE/fonction_upd")
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

/*
exports.ajout_enq_cons= async (req, res) => {
    try {
      const {id_equ, prescription, date,realisateur, observation ,etat} = req.body.data;
      await ajouter_enq_cons({id_equ, prescription, date, realisateur, observation ,etat})
      
      
      
      //res.redirect('/secteurs_sauvegardes');
      res.json({ id_equ,  prescription, date, realisateur, observation ,etat })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('enq_cons deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }*/


  
  
  
/*
  exports.msj_enq_cons= async (req, res) => {
    try {           
      const {id_equ, prescription, date, realisateur, observation ,etat } = req.body.data;
      const data= await consulter_enq_cons({id_equ,date,realisateur})
      const resulta= verifChamps_enq_cons({prescription,observation,etat},data)
      const prescription1=resulta.prescription
      const  observation1=resulta.observation
      const etat1=resulta.etat

      
    await miseajour_enq_cons({id_equ,prescription1,date,realisateur, observation1 ,etat1})
      res.json({id_equ, prescription1,date, realisateur, observation1 ,etat1})

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_enq_cons=async (req, res) => {
    try {
      const {id_equ,date,realisateur}=req.body.data
      await supprimer_enq_cons({id_equ,date,realisateur})
      res.json('supprimer')
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }*/



  exports.ajout_enq_cons= async (req, res) => {
    try {
      const {id_equ, prescription, date,realisateur, observation ,etat} = req.body.data;
    
      await ajouter_enq_cons({id_equ, prescription, date, realisateur, observation ,etat})
      const token = JSON.parse(req.cookies.auth_token);
       const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter une nouvelle enquete de concervation pour la l'equipement de l'id:${id_equ}`
       
       let userName = await rechercher_username('Directrice')
       userName = userName[0].userName
      await ajouter_notif({userName,description,envoyeur})


      res.json({ id_equ,  prescription, date, realisateur, observation ,etat })
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('enq_cons deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.ajout_enq_all_cons= async (req, res) => {
    try {
      const enquete = req.body.data;
      

      if(enquete){
        await enquete.map((obj)=>{
          
          if(obj){
            const id_equ= obj[0]
            const prescription= (obj[4])? obj[4]: ''
            const date= obj[2] 
            const realisateur= obj[1]
            const observation= (obj[5])? obj[5]: ''
            const etat= obj[3]
            ajouter_enq_cons({id_equ, prescription, date, realisateur, observation,etat}) 
            
          }
        })
      }
      const token = JSON.parse(req.cookies.auth_token);
      const envoyeur=getUserId(token)
      let chaine= ''
      enquete.map((obj)=>{
        if(obj){
          chaine=`${chaine}-${obj[0]}`
        }
      })
      const description =`${envoyeur} a ajouter une suit des  enquetes de concervation pour la l'equipement de les ids:${chaine}`
            
      let userName = await rechercher_username('Directrice')
      userName = userName[0].userName
     
      await ajouter_notif({userName,description,envoyeur})


      res.json('l\'operation terminer')
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send('enq_cons deja existant')
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

  exports.msj_enq_cons= async (req, res) => {
    try {           
      const {id_equ, prescription, date, realisateur, observation ,etat } = req.body.data;
      //const data= await consulter_enq_cons({id_equ,date,realisateur})
      //const resulta= verifChamps_enq_cons({prescription,observation,etat},data)
      //const prescription1=resulta.prescription
      //const  observation1=resulta.observation
      //const etat1=resulta.etat
      await miseajour_enq_cons({id_equ,prescription,date,realisateur, observation ,etat})
      const token = JSON.parse(req.cookies.auth_token);
       const envoyeur=getUserId(token)
      const description =`${envoyeur} a modifier enquete de concervation pour la batisse de id:${id_equ} Date:${date} realiser:${realisateur}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      res.json({id_equ, prescription,date, realisateur, observation ,etat})

    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }



  exports.supprime_enq_cons=async (req, res) => {
    try {
      const id_equ = req.params.id_equ
      const realisateur = req.params.realisateur
      const date = `${req.params.jour}/${req.params.mois}/${req.params.anne}`
      await supprimer_enq_cons({id_equ,date,realisateur})
      const token = JSON.parse(req.cookies.auth_token);
       const envoyeur=getUserId(token)
      const description =`${envoyeur} a suprimer enquete de concervation pour la batisse de id:${id_equ} Date:${date} realiser:${realisateur}`
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})

      res.json('supprimer')
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }



  exports.consultAll_enq_cons= async (req, res) => {
    try {
      const enquete_cs = await consulterAll_enq_cons()
      res.json(enquete_cs)
    } catch (err) {
      console.error(err);
      res.status(500).json('Server Error');
    }
  }
   exports.consultId_enq_cons=async (req, res) => {
    try {
        const {id_equ,date,realisateur}=req.body.data

        const enquete_c= await consulter_enq_cons({id_equ,date,realisateur})
        if (enquete_c.length<=0) return res.send('enq_cons not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json( enquete_c)
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }