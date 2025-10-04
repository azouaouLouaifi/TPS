const {query}=require('../helper.js')


exports.ajouter_un=async({id_Un, nomU, descr, id_sec}) => {
    const sql = `INSERT INTO unite (id_Un,nomU,descr,id_sec) VALUES ('${id_Un}','${nomU}','${descr}','${id_sec}');`;
    await query(sql);
}

exports.consulterAll_un =async ()=> {
    const sql = 'SELECT * FROM unite u, secteur_sauvegarde s where u.id_sec = s.id_sec;';
    const unites = query(sql);
    return unites
}  


exports.consulter_un=async(id) =>{ 

    sql = `SELECT * FROM unite WHERE id_Un = "${id}";`;
    const unite =  query(sql);
    
    return unite 

}

exports.consulter_un_sec=async(id) =>{ 
    sql = `SELECT id_Un,nomU FROM unite WHERE  id_sec= "${id}";`;
    const unites =  query(sql);

    return unites
     
}

exports.miseajour_un=async({ nomU,descr, id_Un, id_sec})=>{
    const sql = `UPDATE unite SET nomU = '${nomU}', descr = '${descr}' WHERE id_Un= "${id_Un}" and id_sec = ${id_sec}`;
      await query(sql);

    }

    
    exports.supprimer_un=async({id_sec, id_Un})=>{
    const sql = `delete from unite WHERE id_Un= "${id_Un}" and id_sec=${id_sec};`;
    await query(sql);
}   