const { parse, format } = require('date-fns');
const { utcToZonedTime } = require('date-fns-tz');
const { fr } = require('date-fns/locale');
 
 const jwt = require('jsonwebtoken');

 const {creer}=require("../model_GC/model_compte")
 const {ajouterRole}=require("../model_GC/model_compte")
 const {removeRole}=require("../model_GC/model_compte")
 const {constlerRoles}=require("../model_GC/model_compte")
 //const {connecter}=require("../model_GC/model_compte")
 const {recherchercompte}=require("../model_GC/model_compte")
 const {rechercher_role}=require("../model_GC/model_compte")
 const {consulter_cpt}=require("../model_GC/model_compte")
 const {consulter_notification}=require("../model_GC/model_compte")
 const {consulter_notification_date}=require("../model_GC/model_compte")
 const {supprimer}=require("../model_GC/model_compte")
 const {voir}=require("../model_GC/model_compte")
 const {transformObjectToArray}=require("../fonction_GC/fonction_util")

 const{getUserId}=require("../sys_notification/fonction_notif")

 exports.creation= async (req, res) => { 
    try{ 
    const {userName, password, role } = req.body.data;
      console.log('create user:', {userName, password, role })
    await creer({ userName, password, role })
    res.json({ userName, password, role })
 

    }
    catch (err) {
        if(err.code==='ER_DUP_ENTRY') res.send('user deja existant')
        console.error(err);
        res.status(500).send('Server Error');
      }
 }

 exports.ajoutRole= async (req, res) => {
    try{
    const val = req.body.data;
    const roles= await constlerRoles( val.userName);
    console.log(val, roles);
    let deleteCSS= false
    let deleteCD= false
    let addCSS= val.Chef_secteur_sauvegarde
    let addCD= val.Chef_de_departement

    roles.map((role)=> {
      if(role.role === 'Chef secteur sauvegardé' && !val.Chef_secteur_sauvegarde) deleteCSS= true;
        else if((role.role === 'Chef secteur sauvegardé' && val.Chef_secteur_sauvegarde) ) addCSS= false;
      if(role.role === 'Chef de département' && !val.Chef_de_departement) deleteCD= true;
       else if(role.role === 'Chef de département' && val.Chef_de_departement)  addCD= false
    })
    if(deleteCD) await removeRole(val.userName, 'Chef de département' );  
    if(deleteCSS)  await removeRole(val.userName, 'Chef secteur sauvegardé' );
    if(addCSS)await ajouterRole( val.userName, 'Chef secteur sauvegardé' )
    if(addCD) await ajouterRole( val.userName, 'Chef de département' )

    //await ajouterRole({ userName, role })
    res.json(val)
 

    }
    catch (err) {
        if(err.code==='ER_DUP_ENTRY') res.send('user deja existant')
        console.error(err);
        res.status(500).send('Server Error');
      }
 }

 exports.consultation_roles= async (req, res) => {

    try{
      const token = JSON.parse(req.cookies.auth_token);
      const userName =getUserId(token)
      const roles=await rechercher_role({ userName})
      return res.status(200).json({roles, userName});
    }
    catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
      }
 }

  

 exports.connexion= async (req, res) => {
  console.log('cc')
     try{
    const {userName,password } = req.body;
    const compte=await recherchercompte({userName,password })

   
    if (compte.length > 0) {
        const user = compte[0];
        const roles=await rechercher_role({ userName}) 
        
        const rolesArr=transformObjectToArray(roles)
 
       
        const token = jwt.sign({ userId: user.userName,  rolesArr }, 'wanu');
        return res.status(200).json({ token});
       
      }
      return res.status(401).json({ message: 'Nom d\'utilisateur ou mot de passe invalide.' });
    }
    catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
      }
 }
 


exports.consultation_cpt= async (req, res) => {
    try{
    //console.log(req.cookies.authToken)
    const comptes=await consulter_cpt()
    if(comptes) {
      for(let i=0; i< comptes.length; i++){
        const roles= await constlerRoles( comptes[i].userName);
        comptes[i].roles = roles;
        comptes[i].role= roles.filter((r)=> (r.role === 'Secrétaire' || r.role === 'Ingénieur'))[0]?.role
      }
    }
    res.json(comptes)
    }
    catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
      }
 }

 


 exports.suppression= async (req, res) => {
    try{
    const  userName  = req.params.userName;
    await supprimer( userName )
    res.json({ userName})


    }
    catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
      }
 }


 exports.consultation_notification= async (req, res) => {
  try{
     const token = JSON.parse(req.cookies.auth_token);

    const decoded = jwt.verify(token, 'wanu');
    const notifications=await consulter_notification(decoded.userId)
    const not= []
    if(notifications){
      const options = {
        day: 'numeric',
        month: 'long',
        year: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric',
      };
      notifications.map((row)=>{
        //console.log(row.date);

        const date = new Date(row.date);

        const formattedDate = date.toLocaleDateString('fr-FR', options);
        row.dateFR= formattedDate
        not.push(row)
        
    })
    }
    res.json(not)
  }
  catch (err) {
      console.error(err);
      res.status(500).send('Server Error');
    }
}


exports.vu=async(req,res)=>{
// a revoir
try{
  const {date, userName}=req.body.data
  const token = JSON.parse(req.cookies.auth_token);
  const decoded = jwt.verify(token, 'wanu');
  const envoyeur=decoded.userId


  await voir({date, userName, envoyeur})
  res.json("modifier")    
}
catch (err) {
  console.error(err);
  res.status(500).send('Server Error');
}
}

exports.vu_all=async(req,res)=>{
  // a revoir
  try{
    const liste=req.body.data
    const token = JSON.parse(req.cookies.auth_token);
    const decoded = jwt.verify(token, 'wanu');
    const envoyeur=decoded.userId
  
    for(let i=0; i< liste.length; i++)
      await voir({date: liste[i].date, userName: liste[i].userName, envoyeur})
    
    res.json("modifier")    
  }
  catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  } 
  }