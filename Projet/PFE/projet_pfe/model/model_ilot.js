const {query}=require('../helper.js')


exports.ajouter_il=async({id_il,nomI,id_Un }) => {
    const sql = `INSERT INTO ilot (id_il,nomI,id_Un) VALUES ('${id_il}','${nomI}','${id_Un}');`;
    await query(sql);
}

exports.consulterAll_il =async ()=> {
    const sql = 'SELECT * FROM ilot i, unite u, Secteur_Sauvegarde s where i.id_Un = u.id_Un and  u.id_sec= s.id_sec ;';
    const ilots = query(sql);
    return ilots
}  


exports.consulter_il=async(id) =>{ 

    sql = `SELECT * FROM ilot WHERE id_il = "${id}";`;
    const ilot =  query(sql);
    
    return ilot

}

exports.consulter_il_sec=async({id_sec, id_Un}) =>{ 

    sql = `SELECT id_il,nomI FROM ilot WHERE id_sec = "${id_sec}" and id_Un = "${id_Un}";`;
    const ilots =  query(sql);

    return ilots

}

exports.miseajour_il=async({nomI, id_il })=>{
    const sql = `UPDATE ilot SET nomI    = '${nomI}' WHERE id_il= "${id_il}"`;
      await query(sql);

    }

    
    exports.supprimer_il=async(id)=>{
    const sql = `delete from ilot WHERE id_il= "${id}"`;
    await query(sql);
}