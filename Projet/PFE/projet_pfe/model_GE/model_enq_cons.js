const {query}=require('../helper.js')

exports.ajouter_enq_cons=async({id_equ, prescription,date, realisateur, observation ,etat}) => {
    const sql = `INSERT INTO enquete_cons (id_equ, prescription, date,realisateur
        , observation ,etat) VALUES
         ('${id_equ}','${prescription}','${date}',
         '${realisateur}','${observation}','${etat}');`;
    await query(sql);
}

exports.consulterAll_enq_cons =async ()=> {
    const sql = 'SELECT * FROM enquete_cons;';
    const enquete_conss = query(sql);
    return enquete_conss
}  


exports.consulter_enq_cons=async({id_equ,date,realisateur}) =>{ 

    sql = `SELECT * FROM enquete_cons WHERE id_equ = "${id_equ}" and 
    date = '${date}' and realisateur = '${realisateur}';`;
    const enquete_cons =  query(sql);
    
    return enquete_cons

}

exports.miseajour_enq_cons=async({id_equ,prescription,date,realisateur, observation ,etat})=>{
    const sql = `UPDATE enquete_cons SET prescription = "${prescription}"
    ,observation = "${observation}",
    etat ='${etat}' WHERE id_equ='${id_equ}' and 
    date ='${date}' and realisateur ='${realisateur}';`
      await query(sql);

    }

    exports.nubretat=async({id_sec,date,realisateur})=>{
        const sql = `select count(id_equ) from unite u,ilot i,enquipement e ,enquete_cons en where u.id_sec= "${id_sec}" 
        and i.id_un=u.id_il and 
        ,observation = "${observation}",
        etat ='${etat}' WHERE id_equ='${id_equ}' and 
        date ='${date}' and realisateur ='${realisateur}';`
          await query(sql);
    
        }
    

    

    
    exports.supprimer_enq_cons=async({id_equ,date,realisateur})=>{
    const sql = `delete from enquete_cons WHERE id_equ= "${id_equ}"and 
    date = '${date}' and realisateur = '${realisateur}'`;
    await query(sql);
}