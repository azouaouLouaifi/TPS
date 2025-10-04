const {query}=require('../helper.js')

//******************** requete de consultation ********************//
exports.getAll_projetDB = async()=>{  
    const sql = 'select * from projet'
    return   query(sql)   
}
//******************** requete de consultation ********************//
exports.getAll_ID_projetDB = async(id)=>{  
    const sql = `select * from projet where id_projet = "${id}";`
    return   query(sql)   
}

//******************** requete d'ajoute ********************//
exports.insert_projetDB= async({id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur})=>{
    const sql = `insert into projet values ("${id_projet}", "${nom_projet}", "${ingenieur}", "${maitre_ouvrage}", "${maitre_ouvre}",
                "${architecte_qualife}", "${entreprise}", "${etat}", "${type}");`
    return query(sql)  
}

//****************** les requtes de suppressions par id ******************//
exports.remove_projetBD = async(id)=>{
        const sql = `delete from projet where id_projet = "${id}";`
        return query(sql)
}

//******************** requete de la modification ********************//
exports.update_projetDB = async ({id_projet, nom_projet, maitre_ouvrage, maitre_ouvre, architecte_qualife, entreprise, etat, type, ingenieur}) =>{ 
    const sql = `UPDATE projet
                SET  maitre_ouvrage = "${maitre_ouvrage}",  maitre_ouvre = "${maitre_ouvre}",  architecte_qualife = "${architecte_qualife}",  
                entreprise = "${entreprise}",  etat = "${etat}",  type = "${type}", ingenieur= "${ingenieur}"
                WHERE id_projet = "${id_projet}";`
    return  query(sql)
}

exports.getAllDB = async()=>{
    const sql = 'select * from projet_equipement'
    return requete_select(sql)
}

//******************** requete de consultation par id projet ********************//
exports.selectIdProjetDB = async(id)=>{
    const sql = `select * from projet_equipement where id_projet = "${id}"`
    return query(sql)
}

//******************** requete de consultation par id proejt ********************//
const selectIdEquipementDB = async(data)=>{
    const sql = `select * from projet_equipement where id_equ= "${id_equ}" `
    return query(sql)
}

//******************** requete de consultation par id proejt ********************//
exports.selectIdEquipementProjetDB = async(id_projet, id_equ)=>{
    const sql = `select * from projet_equipement where id_projet= "${id_projet}" and id_equ= "${id_equ}" `
    return query(sql)
}



//******************** requete d'ajoute ********************//
exports.insert_equipementDB= async(id_projet, id_equ)=>{
    const sql = `insert into projet_equipement (id_projet, id_equ) values ( "${id_projet}", "${id_equ}")`
    return  query(sql)
}


//****************** les requtes de suppressions par id_projet and id_equipement ******************//
exports.remove_equipementBD = async(id_proejt, id_equ)=>{
    console.log('supprime', id_proejt, id_equ)
    const sql = `delete from projet_equipement where id_projet="${id_proejt}" and id_equ="${id_equ}";`
    return  query(sql)
}