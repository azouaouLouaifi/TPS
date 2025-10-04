const {query}=require('../helper.js')


exports.ajouter_non_equ=async({ id_n_equ,nomNE,adresse,historique,type,id_il}) => {

    const sql = `INSERT INTO non_equipement (id_n_equ,nomNE,adresse,historique,type,id_il) VALUES ('${id_n_equ}','${nomNE}','${adresse}','${historique}','${type}','${id_il}');`;
     await query(sql);
}

/*exports.consulterAll_non_equ =async ()=> {
    const sql = 'SELECT * FROM non_equipement;';
    const n_equipements = query(sql);
    return  n_equipements
}  */

exports.consulterAll_non_equ =async ({id_sec,id_Un,id_il})=> {
    const sql = `SELECT id_n_equ, i.id_il, nomNE, adresse, historique, type, nomI 
                FROM non_equipement ne,ilot i where ne.id_il=i.id_il and ne.id_il='${id_il}' 
                and id_Un in 
                (select id_Un from unite where id_Un='${id_Un}' and id_sec='${id_sec}') ;`;
    const n_equipements = query(sql);
    return  n_equipements
}

exports.consulterAll_non_equ_sec =async (id_sec)=> {
    const sql = `SELECT id_n_equ, nomNE FROM non_equipement ne,ilot i , unite u where ne.id_il=i.id_il and i.id_Un= u.id_Un and u.id_sec = ${id_sec} ;`;
    const n_equipements = query(sql);
    return  n_equipements
}


exports.consulter_non_equ=async(id) =>{ 

    sql = `SELECT * FROM non_equipement WHERE id_n_equ = "${id}";`;
    const  n_equipement =  query(sql);
    
    return  n_equipement

}

exports.miseajour_non_equ=async({id_n_equ, nomNE, adresse, historique, type})=>{
    const sql = `UPDATE non_equipement SET nomNE = '${nomNE}',adresse = '${adresse}',historique = '${historique}',type = '${type}' WHERE id_n_equ= "${id_n_equ}"`;
      await query(sql);

    }

    
    exports.supprimer_non_equ=async(id)=>{
    const sql = `delete from non_equipement WHERE id_n_equ= "${id}"`;
    await query(sql);
}