const {getAllDB, insertDB, removeBD, updateDB}= require('../model_courrier/model_courrierD')
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

//******************** controle de consultation ********************//
exports.courrierD_getAll = async (req, res)=> {
    try{
        const result = await getAllDB()
        res.status(200).json(result)
    }catch(err){
        res.status(500).json(err) 
    }
     
}

//******************** consultation par id ********************//
/*const selectID = async (req, res)=>{
    const id = req.params.id 
    try{
        const result = await selectIdDB([id])
        if(result.etat === false) res.status(200).send(result).json()
            else res.status(500).send(result).json()
    }catch(err){
        res.status(500).send(err).json()
    }
}*/

//******************** ajouter ********************//

exports.courrierD_insert = async(req, res)=> {
    const {id_courrierD, date_d,  num_courrier, expediteur, objet, archive} = req.body.data
    //console.log(req.body)
    try{
        await insertDB({id_courrierD, date_d, num_courrier, expediteur, objet, archive})
        
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        let description =`${envoyeur} a ajouter un nouveau courrier depart  l'id:${id_courrierD} et l'expéditeur: ${expediteur}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= expediteur
        description =`Votre courrier est enregistré de l'id:${id_courrierD} ` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json('loperation et bien')
    }catch(err){
        console.log(err)
        res.status(500).json(err)
    }   
}


//****************** la suppressions par id ******************//
exports.courrierD_remove = async(req, res)=> {
    const info = req.params.id_courrierD
    try{
        await removeBD(info)
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)

        let description =`${envoyeur} a supprimer le courrier de  l'id:${info} `
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        
        res.status(200).json('opp')

    }catch(err){
        console.log(err)
        res.status(500).json(err)
   } 
    
}



//******************** la modification ********************//

exports.courrierD_update = async(req, res)=>{
    const {id_courrierD, date_d, num_courrier, expediteur, objet, archive} = req.body.data
    console.log(req.body.data)
    try{
        await updateDB({id_courrierD, date_d, num_courrier, expediteur, objet, archive})
        
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        let description =`${envoyeur} a modifier le courrier de départ de l'id:${id_courrierD} et l'expediteur: ${expediteur}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= expediteur
        description =`a modifier le courrier départ de l'id:${id_courrierD} et l'expéditeur: ${expediteur}` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json()
                             
    }catch(err){
        console.log(err)
        res.status(500)
   } 
}




//module.exports= {getAll, insert, remove, selectID, update};