const con = require('./connexion')

exports.query = function query(sql) {
  return new Promise((resolve, reject) => {
    con.query(sql, (err, result) => {
      if (err) reject(err);
      resolve(result);
    });
  });
};


const jwt = require('jsonwebtoken');
 
  


exports.authorize = (allowedRoles) => (req, res, next) => {
  // Récupérer le token dans les en-têtes
  const token = req.headers.auth_token;
  console.log(token);
  if (!token) {
    return res.status(401).json({ message: 'Le jeton d\'authentification est manquant.' });
  }
  try {
    // Vérification et décodage du token
    const decoded = jwt.verify(token, 'wanu'); // Remplacez 'wanu' par votre clé secrète (idéalement dans une variable d'environnement)

    // Extraction du rôle depuis le token
    const { role } = decoded;
    console.log({ role });
    // Vérification que le rôle correspond à l'un des rôles autorisés

    if (!allowedRoles.includes(role)) {
      return res.status(403).json({ message: 'Vous n\'êtes pas autorisé à accéder à cette ressource.' });
    }

    // Ajout des informations du token à la requête pour une utilisation ultérieure
    req.user = decoded;
    next();
  } catch (err) {
    return res.status(401).json({ message: 'Le jeton d\'authentification est invalide.' });
  }
};
