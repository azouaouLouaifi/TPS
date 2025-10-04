const {query}=require('../helper.js')


exports.ajouter_fon=async({ancienNom,historique,id_equ}) => {

    const sql = `INSERT INTO fontaine (ancienNom,historique,id_equ) 
    VALUES ('${ancienNom}','${historique}','${id_equ}');`
     await query(sql)
}

exports.consulterAll_fon =async ({id_sec,id_Un,id_il})=> {
    const sql = `SELECT *
    FROM fontaine b
    JOIN equipement e ON b.id_equ = e.id_equ 
    WHERE e.id_il IN (
      SELECT id_il 
      FROM ilot  
      WHERE id_il = '${id_il}' 
      AND id_Un IN (
        SELECT id_Un 
        FROM unite 
        WHERE id_sec = ${id_sec}
        AND id_Un = '${id_Un}'
      )
    );`;
    const fontaines = query(sql);
    return  fontaines
}  


exports.consulter_fon=async(id) =>{ 

    sql = `SELECT * FROM fontaine f, equipement e where (f.id_equ = e.id_equ and f.id_equ = "${id}");`;
    const  fontaine  =  query(sql);
    
    return fontaine 

}

exports.miseajour_fon=async({ancienNom,historique, id_equ})=>{
    const sql = `UPDATE fontaine  SET ancienNom = '${ancienNom}',historique = '${historique}' WHERE id_equ= "${id_equ}"`;
      await query(sql);
    }

    
    exports.supprimer_fon=async(id)=>{
    const sql = `delete from fontaine WHERE id_fon= "${id}"`;
    await query(sql);
}