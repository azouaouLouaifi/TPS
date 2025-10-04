
const con =require('./connection')
exports.query=function query(sql) {
    return new Promise((resolve, reject) => {
      con.query(sql,(err, result) => {
       //if (err) reject({etat:true,err});
        if (err) reject(err);
        //resolve({etat:false,result});
        resolve(result);
      })
})
//.then(({etat,result})=>{return {etat,result} })
//.catch(({etat,result})=>{return {etat,result} })
  }

  const jwt = require('jsonwebtoken');
const {constlercompte}=require("./model_GC/model_compte")


  exports.authorize = (roles) => {
    return async (req, res, next) => {
      const token1 = req.cookies 
      const token = JSON.parse(req.cookies.auth_token);
                console.log(token)
      if (!token) { 
        return res.status(401).json({ message: 'Le jeton d\'authentification est manquant.' });
      }
      try {
        const decoded = jwt.verify(token, 'wanu');
        const compte = await constlercompte(decoded.userId);
        if (compte.length <= 0) {
          return res.status(401).json({ message: 'Le compte n\'existe pas.' });
        }
        if (!decoded.rolesArr.some(role => roles.includes(role))) {
          return res.status(403).json({ message: 'Vous n\'êtes pas autorisé à accéder à cette ressource.' });
        }
        req.user = decoded;
        next();
      } catch (err) {
        return res.status(401).json({ message: 'Le jeton d\'authentification est invalide.' });
      }
    };
  };



