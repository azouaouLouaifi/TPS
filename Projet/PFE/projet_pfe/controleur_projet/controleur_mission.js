const {getAll_missionDB, selectId_missionDB, insert_missionDB, remove_missionBD, update_missionDB}= require('../modele_projet/modele_mission')
const {selectIdProjetDB}= require('../modele_projet/modele_projet')

const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")

//******************** controle de consultation ********************//
exports.getAll_mission = async (req, res)=> {
    try{
        const result = await getAll_missionDB()
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
exports.insert_mission = async(req, res)=> {
    const {id_mission, id_projet, nom_mission} = req.body.data
    console.log({id_mission, id_projet, nom_mission})

    try{
        //const mission = await selectIdProjetDB (req.body.data.id_projet.id_projet)
       // console.log('mission',mission)
        await insert_missionDB({id_mission, id_projet, nom_mission})
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        
        let description =`${envoyeur}, à ajouter  la mission.  l'id:${id_mission} et le nom: ${nom_mission} du projet ${id_projet.id_projet}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= id_projet.ingenieur
        description =`${envoyeur},Vous avez une nouvelle mission  l'id:${id_mission} et le nom: ${nom_mission} du projet ${id_projet.id_projet}` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json('oop')

    }catch(err){
        console.log(err)
        res.status(500).json(err)
    }    
}

//****************** la suppressions par id ******************//
exports.remove_mission = async(req, res)=> {
    const id_mission = req.params.id_mission
    const id_projet = req.params.id_projet
    try{
        const mission = await selectId_missionDB (id_projet, id_mission)
        await remove_missionBD(id_projet, id_mission)
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        
        let description =`${envoyeur}, à supprimer la mission  l'id:${id_mission} et le nom: ${mission[0]?.nom_mission} du projet ${id_projet}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= mission[0]?.ingenieur
        description =`${envoyeur}, à supprimer votre mission  l'id:${id_mission} et le nom: ${mission[0]?.nom_mission} du projet ${id_projet}` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json('opp')

                         
    }catch(err){
        console.log(err)
        res.status(500).json(err)
   }
}



//******************** la modification ********************//

exports.update_mission = async(req, res)=>{
    const {id_mission, id_projet, nom_mission, id_missionA} = req.body.data
    try{
        await update_missionDB({id_mission, id_projet, nom_mission, id_missionA})
        res.status(200).json('oop') 
    }catch(err){
        console.log(err)
        res.status(500)
   } 
}
