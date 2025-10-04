const pool = require('../db-config')

const requete_non_select = async(sql, data)=>{
    return new Promise((resolve, reject) =>{
        pool.query(sql, data,(erreur, result)=>{
        if(!erreur)
            resolve({etat: false, result})
        else
            reject({etat: true, erreur})
        })
    })
    .then((e)=>{ return e }) 
    .catch((e)=>{ return e })
}

const requete_select = async(sql)=>{
    return new Promise((resolve, reject) =>{
        pool.query(sql, (erreur, result)=>{
        if(!erreur)
            resolve({etat: false, result})
        else
            reject({etat: true, erreur})
        })
    })
    .then((e)=>{ return e }) 
    .catch((e)=>{ return e })
}

module.exports={requete_select, requete_non_select}