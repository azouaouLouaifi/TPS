const {query}=require('../helper.js')
exports.ajouter_sec=async({ codeW, nomS, classement }) => {
    const sql = `INSERT INTO Secteur_Sauvegarde (codeW, nomS, classement) VALUES ('${codeW}', '${nomS}', '${classement}');`;
    await query(sql);
}

exports.consulterAll =async ()=> {
    //const sql = 'SELECT * FROM Secteur_Sauvegarde s,Secteur_Sauvegarde_periode ps,periode p where s.id_sec=ps.id_sec and ps.id_per=p.id_per;';
    const sql = 'SELECT * FROM Secteur_Sauvegarde ;';
    //const sql = 'SELECT * FROM Secteur_Sauvegarde s,Secteur_Sauvegarde_periode ps,periode p where s.id_sec=ps.id_sec and ps.id_per=p.id_per group by s.id_sec;';
    
    const secteurs = query(sql);

    return secteurs
}  
exports.consulter_sec=async(id) =>{ 

    sql = `SELECT * FROM Secteur_Sauvegarde s where s.id_sec=${id}  ;`
    const secteur =  query(sql);

    
    return secteur

}
exports.consulter_sec_per =async (id)=> {
    const sql = `SELECT nomp,description FROM Secteur_Sauvegarde_periode ps,periode p where  ps.id_sec="${id}" and ps.id_per=p.id_per;`;

    
    const secteurs = query(sql);
    return secteurs
}

exports.miseajour_sec=async( { codeW, nomS, classement, id_sec })=>{
    const sql = `UPDATE Secteur_Sauvegarde SET codeW = '${codeW}', nomS = '${nomS}', classement = '${classement}' WHERE id_sec = ${id_sec}`;
      await query(sql);

    }

    
    exports.supprimer_sec=async(id)=>{
    const sql = `delete from Secteur_Sauvegarde WHERE id_sec = ${id}`;
    await query(sql);
}


exports.ajouter_sec_per=async({id_sec,id_per}) => {
    const sql = `INSERT INTO Secteur_Sauvegarde_periode (id_sec,id_per)
     VALUES ('${id_sec}', '${id_per}');`;
    await query(sql);
}
 

exports.supprimer_sec_per=async({id_sec,id_per})=>{
    const sql = `delete from Secteur_Sauvegarde_periode WHERE 
    id_sec = ${id_sec} and id_per = ${id_per}`;
    await query(sql);
}




