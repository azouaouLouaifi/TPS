const {query}=require('../helper.js')

//******************** requete de consultation ********************//
exports.getAllDB = async()=>{
    const sql = 'select * from courrier_arrivee'
    return query(sql)
}


//******************** requete d'ajoute ********************//
exports.insertDB= async({id_courrierA, date_d, date_a, num_courrier, expediteur, objet, archive, destinataire})=>{
    const sql = `insert into courrier_arrivee values (${id_courrierA}, "${date_d}", "${date_a}", ${num_courrier}, "${expediteur}",
         "${destinataire}", "${objet}", "${archive}" );`
    return  query(sql)
}

//****************** les requtes de suppressions par id ******************//
exports.removeBD = async(id)=>{
    const sql = `delete from courrier_arrivee where id_courrierA = ${id};`
    return  query(sql)
}

//******************** requete de la modification ********************//
exports.updateDB = async ({id_courrierA, date_d, date_a, num_courrier, expediteur, objet, archive, destinataire}) =>{
    const sql = `UPDATE courrier_arrivee
                SET expediteur= "${expediteur}",  objet = "${objet}",  archive = "${archive}"
                WHERE id_courrierA = "${id_courrierA}";`
    
    return query(sql)
}

