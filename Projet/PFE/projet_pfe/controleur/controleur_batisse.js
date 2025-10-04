const {verifChamps_bat}=require("../fonction_GI/fonction_upd")
const {ajouter_bat}=require("../model/model_batisse")
const {consulterAll_bat}=require("../model/model_batisse")
const {consulter_bat}=require("../model/model_batisse")
const {miseajour_bat}=require("../model/model_batisse")
const {supprimer_bat}=require("../model/model_batisse")


// import pour sys notif
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")




exports.ajout_bat= async (req, res) => {
    try{
      try {
      const {nbr_menage,nbr_personne,nom_pro,prenom_pro,participation_restauration,plan_d_attaque,
        liberer_lieux,id_equ} = req.body.data;
        console.log(req.body.data)
      await ajouter_bat({nbr_menage ,nbr_personne,nom_pro,prenom_pro,participation_restauration,
        plan_d_attaque,liberer_lieux,id_equ})

        const token = JSON.parse(req.cookies.auth_token);

      const envoyeur=getUserId(token)
      const description =`${envoyeur} a ajouter une batisse  de id:${id_equ} `
       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})


      res.json({nbr_menage,nbr_personne,nom_pro,prenom_pro,participation_restauration,plan_d_attaque,
        liberer_lieux,id_equ})
    } catch (err) {
      if(err.code==='ER_DUP_ENTRY') res.send(' batisse deja existant')
      
      console.error(err);
      res.status(500).send(err);
     } 
    }
    catch(err){
      console.log(err)
    }
}

  exports.consultAll_bat= async (req, res) => {
    try {
      const id_sec= req.params.id_sec
      const id_Un= req.params.id_Un
      const id_il= req.params.id_il
      const batisses = await consulterAll_bat({id_sec,id_Un,id_il})
     
      res.json( batisses)
      //res.render("affichage_secteurs",{secteurs:secteurs});
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }

   exports.consultId_bat=async (req, res) => {
    try {
        const batisse= await consulter_bat(req.params.id)
        console.log(batisse)
        if (batisse.length<=0) return res.send(' batisse not found');
        //res.render("affichage_secteurs",{secteurs:secteur});
       
        res.json( batisse[0])
        
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }
    

  exports.msj_bat= async (req, res) => {
    try {    
      const {nbr_menage,nbr_personne,nom_pro,prenom_pro,participation_restauration,plan_d_attaque,
        liberer_lieux,id_equ} = req.body.data;
      
      await miseajour_bat( {nbr_menage,nbr_personne,nom_pro,prenom_pro,participation_restauration,plan_d_attaque,
        liberer_lieux,id_equ})
      /*  const token = JSON.parse(req.cookies.auth_token);

        const envoyeur=getUserId(token)
        const description =`${envoyeur} a modifier la batisse de id:${id_equ} }`
         
         let userName=await rechercher_username('Directrice')
         userName=userName[0].userName
        await ajouter_notif({userName,description,envoyeur})*/
      //res.json('modifier')
      
    } catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
  }


  exports.supprime_bat=async (req, res) => {
    try {
      await supprimer_bat(req.params.id)
      const token = req.headers.authorization;

      const envoyeur=getUserId(token)
      const description =`${envoyeur} a supprimer la batisse de id:${req.params.id}`

       
       let userName=await rechercher_username('Directrice')
       userName=userName[0].userName
      await ajouter_notif({userName,description,envoyeur})
      const message=`La batisse avec id: ${req.params.id} a etait supprimer`
      res.json(message)
    } catch (err) {
      console.error(err);
 
      res.status(500).send('Server Error');
    }
  }