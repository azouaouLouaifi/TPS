const {query}=require('../helper.js')

//******************** requete de consultation ********************//
exports.getAllDB = async()=>{
    const sql = 'select * from courrier_depart'
    return query(sql)
}


//******************** requete d'ajoute ********************//
exports.insertDB= async({id_courrierD, date_d, num_courrier, expediteur, objet, archive, destinataire})=>{
    const sql = `insert into courrier_depart values (${id_courrierD}, "${date_d}",  ${num_courrier}, "${expediteur}", "${objet}", "${archive}" );`
    return  query(sql)
}

//****************** les requtes de suppressions par id ******************//
exports.removeBD = async(id)=>{
    const sql = `delete from courrier_depart where id_courrierD = ${id};`
    return  query(sql)
}

//******************** requete de la modification ********************//
exports.updateDB = async ({id_courrierD, expediteur, objet, archive}) =>{
    const sql = `UPDATE courrier_depart
                SET expediteur= "${expediteur}",  objet = "${objet}",  archive = "${archive}"
                WHERE id_courrierD = "${id_courrierD}";`
    
    return query(sql)
}

