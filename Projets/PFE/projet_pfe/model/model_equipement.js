const {query}=require('../helper.js')


exports.ajouter_equ=async({ id_equ,nomE,fonctionE,topologie,statut,adresse,type,id_il}) => {

    const sql = `INSERT INTO equipement (id_equ,nomE,fonctionE,topologie,statut,adresse,type,id_il) VALUES ('${id_equ}','${nomE}','${fonctionE}','${topologie}','${statut}','${adresse}','${type}','${id_il}');`;
     await query(sql); 
}

exports.consultergetAll_equ =async ()=> {
    const sql = 'SELECT * FROM equipement;';
    const equipements = query(sql);
    return  equipements
} 

exports.consulterAll_equ =async ({id_sec,id_Un,id_il})=> {

    const sql = `SELECT *FROM equipement ne,ilot i where ne.id_il=i.id_il and ne.id_il='${id_il}' 
     and id_Un in 
    (select id_Un from unite where id_Un='${id_Un}' and id_sec='${id_sec}') ;`;
    const equipements = query(sql);
    return  equipements
}

exports.consulterAll_secteur_equ =async (id_sec)=> {

    const sql = `SELECT *FROM equipement ne,ilot i where ne.id_il=i.id_il 
     and id_Un in 
    (select id_Un from unite where id_sec='${id_sec}') ;`;
    const equipements = query(sql);
    return  equipements
} 

exports.consulterAll_secteur_d_equipement_equ =async (id_equ)=> {

    const sql = `SELECT *
    FROM Secteur_Sauvegarde ss, unite u
    WHERE ss.id_sec = u.id_sec
      AND u.id_Un IN (
        SELECT id_Un
        FROM ilot
        WHERE id_il IN (
          SELECT id_il
          FROM equipement
          WHERE id_equ = '${id_equ}'
        )
      );`;
    const equipements = query(sql);
    return  equipements
} 

exports.consulterAllTotale_equ =async ()=> {

    const sql = `SELECT id_equ FROM equipement  ;`;
    const equipements = query(sql);
    return  equipements
}


exports.consulter_equ=async(id) =>{ 

    sql = `SELECT * FROM equipement WHERE id_equ = "${id}";`;
    const  equipement =  query(sql);
    
    return equipement

}

exports.miseajour_equ=async({id_equ,nomE,fonctionE,topologie,statut,adresse,})=>{
    const sql = `UPDATE equipement SET nomE = '${nomE}',fonctionE = '${fonctionE}',topologie = '${topologie}', statut = '${statut}',adresse = '${adresse}' WHERE id_equ= "${id_equ}"`;
      await query(sql);

    }

    
    exports.supprimer_equ=async(id)=>{
    const sql = `delete from equipement WHERE id_equ= "${id}"`;
    await query(sql);
}