const {getAllDB, insertDB, removeBD, updateDB}= require('../model_courrier/model_courrierA')
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

//******************** controle de consultation ********************//
exports.courrierA_getAll = async (req, res)=> {
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

exports.courrierA_insert = async(req, res)=> {
    const {id_courrierA, date_d, date_a, num_courrier, expediteur, objet, archive, destinataire} = req.body.data
    //console.log(req.body)
    try{
        await insertDB({id_courrierA, date_d, date_a, num_courrier, expediteur, objet, archive, destinataire})
        
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        let description =`${envoyeur} a ajouter un nouveau courrier arriver  l'id:${id_courrierA} et l'expediteur: ${expediteur}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= destinataire
        description =`Vous avez un nouveau courrier arrivé de l'id:${id_courrierA} et l'expediteur: ${expediteur}` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json('loperation et bien')
    }catch(err){
        console.log(err)
        res.status(500).json(err)
    }   
}

/*
const info_confirm = async(info)=>{
    try{ 
        const exist = await selectIdDB(info[0])
        if(exist.etat === true || exist.result.length !== 0) return false
        return true
    }catch(err){
        res.status(500).send(err).json()
    }
}*/

//****************** la suppressions par id ******************//
exports.courrierA_remove = async(req, res)=> {
    const info = req.params.id_courrierA
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

exports.courrierA_update = async(req, res)=>{
    const {id_courrierA, date_d, date_a, num_courrier, expediteur, objet, archive, destinataire} = req.body.data
    console.log(req.body.data)
    try{
        await updateDB({id_courrierA, date_d, date_a, num_courrier, expediteur, objet, archive, destinataire})
        
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        let description =`${envoyeur} a modifier le courrier arriver de l'id:${id_courrierA} et l'expediteur: ${expediteur}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= destinataire
        description =`a modifier le courrier arrivé de l'id:${id_courrierA} et l'expediteur: ${expediteur}` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json()
                             
    }catch(err){
        console.log(err)
        res.status(500)
   } 
}




//module.exports= {getAll, insert, remove, selectID, update};