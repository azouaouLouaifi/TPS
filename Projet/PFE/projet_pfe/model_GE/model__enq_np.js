const {query}=require('../helper.js')

exports.ajouter_enq_np=async({id_bat, prescription,date, realisateur, observation ,nature}) => {
    const sql = `INSERT INTO enquete_np (id_bat, prescription, date,realisateur
        , observation ,nature) VALUES
         ('${id_bat}','${prescription}','${date}',
         '${realisateur}','${observation}','${nature}');`;
    await query(sql);
}

exports.consulterAll_enq_np =async ()=> {
    const sql = 'SELECT * FROM enquete_np;';
    const enquete_nps = query(sql);
    return enquete_nps
}  


exports.consulter_enq_np=async({id_bat,date,realisateur}) =>{ 

    sql = `SELECT * FROM enquete_np WHERE id_bat = "${id_bat}" and 
    date = '${date}' and realisateur = '${realisateur}';`;
    const enquete_np =  query(sql);
    
    return enquete_np

}

exports.miseajour_enq_np=async({id_bat,prescription1,date,realisateur, observation1 ,nature1})=>{
    const sql = `UPDATE enquete_np SET prescription = '${prescription1}'
    ,observation ='${observation1}',
    nature ='${nature1}' WHERE id_bat='${id_bat}' and 
    date ='${date}' and realisateur ='${realisateur}';`
      await query(sql);

    }

    
    exports.supprimer_enq_np=async({id_bat,date,realisateur})=>{
    const sql = `delete from enquete_np WHERE id_bat= "${id_bat}"and 
    date = '${date}' and realisateur = '${realisateur}'`;
    await query(sql);
}