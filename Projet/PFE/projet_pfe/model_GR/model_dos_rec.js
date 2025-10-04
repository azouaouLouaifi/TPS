const {query}=require('../helper.js')

exports.ajouter_dos_rec=async({ id_equ,date, des}) => {
    const sql = `INSERT INTO dossier_rec ( id_equ, date,description)
     VALUES
         ('${id_equ}','${date}',
         '${des}');`;
    await query(sql);
}

exports.consulterAll_dos_rec =async ()=> {
    const sql = 'SELECT * FROM dossier_rec;';
    const dossier_recs = query(sql);
    return dossier_recs
}  


exports.consulter_dos_rec=async({id_equ,date}) =>{ 

    sql = `SELECT * FROM dossier_rec WHERE 
    date = '${date}' and id_equ = '${id_equ}';`;
    const dossier_rec =  query(sql);
    
    return dossier_rec

}

exports.miseajour_dos_rec=async({id_equ,date,des1})=>{
    const sql = `UPDATE dossier_rec SET description = '${des1}' WHERE 
    date ='${date}' and id_equ ='${id_equ}';`
      await query(sql);

    }

    

    
    exports.supprimer_dos_rec=async({id_equ,date})=>{
    const sql = `delete from dossier_rec WHERE 
    date = '${date}' and id_equ = '${id_equ}'`;
    await query(sql);
}