const {getAll_projetDB, selectIdProjetDB,remove_equipementBD, insert_projetDB, insert_equipementDB, getAll_ID_projetDB, remove_projetBD, update_projetDB}= require('../modele_projet/modele_projet')
const{ajouter_notif}=require("../sys_notification/fonction_notif")
const{getUserId}=require("../sys_notification/fonction_notif")
const{rechercher_username}=require("../model_GC/model_compte")
//******************** controle de consultation ********************//
exports.getAll_projet = async (req, res)=> {
    try{
        const result = await getAll_projetDB()

        for(let i=0; i< result.length; i++){
            const equipements = await selectIdProjetDB(result[i].id_projet)
            let equ= ''
            for(let j=0; j< equipements.length; j++){
                if(equ)
                equ= equ.concat(", ", equipements[j].id_equ)
                else equ= equipements[j].id_equ
            }
            result[i].equipements= equ
        }
        //console.log(result)
        res.status(200).json(result); 
    }catch(err){
        console.log(err)
        res.status(500).json(err)
    }
     
}


//******************** ajouter ********************//
exports.insert_projet = async(req, res)=> {
    try{
        const {id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur} = req.body.data
        await insert_projetDB({id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur})
        const equipements = req.body.data?.equipement
        
        if(equipements){
            for(let i=0; i< equipements.length; i++){
                console.log(id_projet , equipements[i].id_equ)
                await insert_equipementDB(id_projet , equipements[i].id_equ)
            }
        }
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        let description =`${envoyeur} a ajouter un nouveau projet  l'id:${id_projet} et le nom: ${nom_projet}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= ingenieur
        description =`${envoyeur}, à vous envoyé un nouveau projet.  l'id:${id_projet} et le nom: ${nom_projet}` 
        await ajouter_notif({userName,description,envoyeur})

        
       res.status(200).json('oop')
                   
    }catch(err){
        console.log(err)
        res.status(500).json(err)
    }    
}

//****************** la suppressions par id ******************//
exports.remove_projet = async(req, res)=> {
   
    try{
        const id = req.params.id_projet
        const projet= await getAll_ID_projetDB(id)
        await remove_projetBD(id)

         
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)

        let description =`${envoyeur}, à supprimer le projet  l'id:${id} et le nom: ${projet[0]?.nom_projet}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        userName= projet[0]?.ingenieur
        description =`${envoyeur}, à supprimer votre projet  l'id:${id} et le nom: ${projet[0]?.nom_projet}` 
        await ajouter_notif({userName,description,envoyeur})

        res.status(200).json('oop')
    }catch(err){
        console.log(err)
        res.status(500).json(err)
   } 
    
}



//******************** la modification ********************//

exports.update_projet = async(req, res)=>{
    
    try{
        const {id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur, equipements} = req.body.data
        console.log({id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur, equipements})
        const projet= await getAll_ID_projetDB(id_projet)

       
        const equipement = await selectIdProjetDB(id_projet)

        console.log('debut', equipement, equipements)
     
            for(let i=0; i< equipement.length; i++){
                let bol = true
                for(let j=0; j < equipements.length; j++){
                    if(equipement[i].id_equ === equipements[j].id_equ){
                        
                        bol= false
                        break;
                    }else{
                        bol= true
                    }
                }
                if(bol){      
                    await remove_equipementBD(id_projet, equipement[i].id_equ)
                }
            }
        
        if(equipements.length> 0){
            let bol= true
            for(let i=0; i< equipements.length; i++){
                for(let j=0; j< equipement.length; j++){
                    if(equipements[i].id_equ === equipement[j].id_equ){
                        bol= false
                        break;
                    }else{
                        bol= true
                    }

                }
                if(bol){     
                    await insert_equipementDB(id_projet , equipements[i].id_equ) 
                    //equipements.pop(equipement[i])
                }   
            }
        }

        await update_projetDB({id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur})
        
        const token = JSON.parse(req.cookies.auth_token);
        const envoyeur= getUserId(token)
        let description =`${envoyeur}  à fait des modification dans le projet  l'id:${id_projet} et le nom: ${nom_projet}`
        let userName = await rechercher_username('Directrice')
        userName = userName[0].userName
        await ajouter_notif({userName,description,envoyeur})
        if(ingenieur === projet[0].ingenieur){
            userName= ingenieur
            description =`${envoyeur}, à fait des modification dans votre projet de id:${id_projet} ` 
            await ajouter_notif({userName,description,envoyeur})
        }else{
            userName= ingenieur
            description =`${envoyeur}, à vous envoyé un nouveau projet.  l'id:${id_projet} et le nom: ${nom_projet}` 
            await ajouter_notif({userName,description,envoyeur})
            userName= projet[0].ingenieur
            description =`${envoyeur}, à supprimer votre projet  l'id:${id_projet} et le nom: ${nom_projet}` 
            await ajouter_notif({userName,description,envoyeur})

        }
        res.status(200).json('oop')  
    }catch(err){
        console.log(err)
        res.status(500)
   } 
}
