const {query}=require('../helper.js')


exports.ajouter_bat=async({nbr_menage,nbr_personne,nom_pro,prenom_pro,participation_restauration
    ,plan_d_attaque,liberer_lieux,id_equ}) => {

    const sql = `INSERT INTO batisse (nbr_menage,nbr_personne,nom_pro,prenom_pro,
        participation_restauration,plan_d_attaque,liberer_lieux,id_equ) 
    VALUES ('${nbr_menage}','${nbr_personne}','${nom_pro}','${prenom_pro}',
    '${participation_restauration}','${plan_d_attaque}','${liberer_lieux}','${id_equ}');`
     await query(sql)
}

exports.consulterAll_bat =async ({id_sec,id_Un,id_il})=> {
    const sql = `SELECT *
    FROM batisse b
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
     
    const batisses = query(sql);
    return  batisses
}  


exports.consulter_bat=async(id) =>{ 

    sql = `SELECT * FROM batisse b,equipement e WHERE b.id_equ = "${id}" and b.id_equ=e.id_equ ;`;
    const  batisse =  query(sql);
    
    return batisse

}

exports.miseajour_bat=async( {nbr_menage,nbr_personne,nom_pro,prenom_pro,participation_restauration,plan_d_attaque,
    liberer_lieux,id_equ})=>{
    const sql = `UPDATE batisse SET nbr_menage = '${nbr_menage}',  nbr_personne = '${nbr_personne}',
                nom_pro = '${nom_pro}',prenom_pro = '${prenom_pro}',
                participation_restauration = '${participation_restauration}',
                plan_d_attaque = '${plan_d_attaque}', liberer_lieux='${liberer_lieux}' WHERE id_equ= "${id_equ}";`;
                console.log('sql',sql)
      await query(sql);
    } 
 
    
    exports.supprimer_bat=async(id)=>{
    const sql = `delete from batisse WHERE id_equ= "${id}"`;
    await query(sql);
}