const {query}=require('../helper.js')


exports.ajouter_mag=async({id_equ,type_activite,nbr}) => {
    const sql = `INSERT INTO magasin (id_equ,type_activite,nbr) VALUES
     ('${id_equ}','${type_activite}','${nbr}');`;
    await query(sql);
}

exports.consulterAll_mag =async ({id_sec,id_Un,id_il, id_equ})=> {
    console.log('{id_sec,id_Un,id_il, id_equ}',{id_sec,id_Un,id_il, id_equ})
    const sql = `SELECT *
    FROM magasin
    WHERE id_equ ='${id_equ}'
    and    
    id_equ in( 
        SELECT id_equ from equipement WHERE id_il IN (
            SELECT id_il 
            FROM ilot  
            WHERE id_il = '${id_il}' 
            AND id_Un IN (
                SELECT id_Un 
                FROM unite 
                WHERE id_sec = ${id_sec}
                AND id_Un = '${id_Un}'
            )
        )
    );`;
    const magasins = await query(sql);
    console.log('magasin: ',magasins)
    return magasins
}  

exports.countNbr_magasin =async ({id_sec,id_Un,id_il, id_equ})=> {
    const sql = `SELECT sum(nbr) somme
    FROM magasin
    WHERE id_equ ='${id_equ}'
    and 
    id_equ in(
        SELECT id_equ from equipement WHERE id_il IN (
            SELECT id_il 
            FROM ilot  
            WHERE id_il = '${id_il}' 
            AND id_Un IN (
                SELECT id_Un 
                FROM unite 
                WHERE id_sec = ${id_sec}
                AND id_Un = '${id_Un}'
            )
        )
    );`;
    const magasins = query(sql);
    //console.log('magasin: ',magasins)
    return magasins
} 


exports.consulter_mag=async({id_equ}) =>{ 

    sql = `SELECT * FROM magasin WHERE id_equ= "${id_equ}";`;
    const magasin =  query(sql);
    
    return magasin

}

exports.consulter_mag_equ=async({id_equ,type_activite}) =>{ 

    sql = `SELECT * FROM magasin WHERE id_equ= "${id_equ}" 
    and type_activite="${type_activite}";`;
    const magasin =  query(sql);
    
    return magasin

}

exports.miseajour_mag=async({ id_equ,type_activite,nbr})=>{
    const sql = `UPDATE magasin SET nbr = '${nbr}'
     WHERE id_equ= "${id_equ}" and type_activite= "${type_activite}";`;
      await query(sql);

    }

    
    exports.supprimer_mag=async({ id_equ,type_activite})=>{
    const sql = `delete from magasin WHERE id_equ= "${id_equ}"
    and type_activite= "${type_activite}"`;
    await query(sql);
}