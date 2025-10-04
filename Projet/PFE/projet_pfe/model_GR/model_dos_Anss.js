const {query}=require('../helper.js')

exports.ajouter_dos_Anss=async({ id_res,date, des}) => {
    const sql = `INSERT INTO dossier_Anss ( id_res, date,description)
     VALUES
         ('${id_res}','${date}',
         '${des}');`;
    await query(sql);
}

exports.consulterAll_dos_Anss =async ()=> {
    const sql = 'SELECT * FROM dossier_Anss;';
    const dossier_Ansss = query(sql);
    return dossier_Ansss
}  


exports.consulter_dos_Anss=async({id_res,date}) =>{ 

    sql = `SELECT * FROM dossier_Anss WHERE  
    date = '${date}' and id_res = '${id_res}';`;
    const dossier_Anss =  query(sql);
    
    return dossier_Anss

}

exports.miseajour_dos_Anss=async({id_res,date,des1})=>{
    const sql = `UPDATE dossier_Anss SET description = '${des1}' WHERE 
    date ='${date}' and id_res ='${id_res}';`
      await query(sql);

    }

    

    
    exports.supprimer_dos_Anss=async({id_res,date})=>{
    const sql = `delete from dossier_Anss WHERE 
    date = '${date}' and id_res = '${id_res}'`;
    await query(sql);
}