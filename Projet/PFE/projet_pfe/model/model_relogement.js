const {query}=require('../helper.js')


exports.ajouter_rel=async({id_bat,date }) => {
    const sql = `INSERT INTO relogement (id_bat,date ) VALUES
     ('${id_bat}','${date}');`
    await query(sql);
}

exports.consulterAll_rel =async ()=> {
    const sql = 'SELECT * FROM relogement;';
    const relogements = query(sql);
    return relogements
}  


exports.consulter_rel=async({date}) =>{ 

    sql = `SELECT * FROM relogement WHERE date= "${date}";`;
    const relogement =  query(sql);
    
    return relogement

}
    
    exports.supprimer_rel=async({id_bat,date})=>{
    const sql = `delete from relogement WHERE id_bat= "${id_bat}" and date= "${date}"`;
    await query(sql);
}