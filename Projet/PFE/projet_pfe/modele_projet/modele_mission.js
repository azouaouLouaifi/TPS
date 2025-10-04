const {query}=require('../helper.js')

//******************** requete de consultation ********************//
exports.getAll_missionDB = async()=>{
    const sql = 'select m.id_mission, m.nom_mission, m.id_projet, p.nom_projet, p.ingenieur  from mission m ,  projet p where  p.id_projet = m.id_projet'
    return query(sql)
}

//******************** requete de consultation par id ********************//
exports.selectId_missionDB = async(id_projet, id_mission )=>{
    const sql = `select m.id_mission, m.nom_mission, m.id_projet, p.nom_projet, p.ingenieur  
                from mission m ,  projet p 
                where p.id_projet= m.id_projet and p.id_projet = "${id_projet}" and  m.id_mission = ${id_mission};`
    return query(sql)
}
/*
const selectIdNomDB = async(id)=>{
    const sql = `select * from nom_mission where id = ?`
    return query(sql)
}*/

//******************** requete d'ajoute ********************//
exports.insert_missionDB= async({id_mission, id_projet, nom_mission})=>{
    const sql = `insert into mission values (${id_mission}, "${id_projet.id_projet}", "${nom_mission}")`
    return  query(sql)
}


//****************** les requtes de suppressions par id ******************//
exports.remove_missionBD = async(id_projet, id_mission)=>{
    const sql = `delete from mission where id_projet = "${id_projet}" and  id_mission = ${id_mission};`
    return  query(sql)
}

//******************** requete de la modification ********************//
exports.update_missionDB = async ({id_mission, id_projet, nom_mission, id_missionA}) =>{
    const sql = `UPDATE mission
                SET id_mission = ${id_mission} ,  nom_mission = "${nom_mission}" 
                WHERE id_projet = "${id_projet}" and id_mission= ${id_missionA} ;`

    await query(sql)
}

