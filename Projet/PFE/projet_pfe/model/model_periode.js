const {query}=require('../helper.js')
exports.ajouter_per=async({nomP,description}) => {
    const sql = `INSERT INTO Periode (nomP,description) VALUES ('${nomP}','${description}');`;
    await query(sql);
}

exports.consulterAll_per =async ()=> {
    const sql = 'SELECT * FROM Periode;';
    const periodes = query(sql);
    return periodes
}  


exports.consulter_per=async(id) =>{ 

    sql = `SELECT * FROM periode WHERE id_per = ${id};`;
    const periode =  query(sql);
    
    return periode

}

exports.consulter_Secteurs_periode=async(id) =>{ 

    sql = `SELECT ssp.id_per, s.id_sec, s.nomS FROM secteur_sauvegarde s, secteur_sauvegarde_periode ssp WHERE ssp.id_per = ${id} and s.id_sec = ssp.id_sec; `;
    const periodes =  query(sql);
    
    return periodes

}

exports.miseajour_per=async({ nomP1,des1 },id)=>{
    const sql = `UPDATE periode SET nomP = '${nomP1}', description = '${des1}' WHERE id_per= ${id}`;
      await query(sql);

    }

    
    exports.supprimer_per=async(id)=>{
    const sql = `delete from periode WHERE id_per= ${id}`;
    await query(sql);
}