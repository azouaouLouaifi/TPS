// controleur pour les users

// a utiliser apres la gestion des accées
const jwt = require('jsonwebtoken');
const {
  creer, connexion, nbrUser, supprimerU, Users
} = require('../model/model_compte');

// creation d'un compte
exports.creation = async (req, res) => {
  try {
    const {
      nom, prenom, mdp, email
    } = req.body;
    await creer({
      nom, prenom, mdp, email
    });
    res.json({
      nom, prenom, mdp, email
    });
  } catch (err) {
    if (err.code === 'ER_DUP_ENTRY') res.send('user deja existant');
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// rest a ajouter les token
exports.connexion = async (req, res) => {
  try {
    const { email, mdp } = req.body;
    console.log('user recherche user', { email, mdp });
    const user = await connexion({ email, mdp });
    if (user.length > 0) {
      console.log(user[0].role);
      const token = jwt.sign({ role: user[0].role }, 'wanu', { expiresIn: '1h' });
      return res.status(200).json({ token, user });
    }
    return res.status(401).json({ message: 'Nom d\'utilisateur ou mot de passe invalide.' });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// consulter le nombre d'utilisateurs
exports.nbrUser = async (req, res) => {
  try {
    const users = await nbrUser();
    return res.status(200).json({ users });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// supprimer compte
exports.supprimerU = async (req, res) => {
  try {
    const { nom, prenom } = req.body;
    await supprimerU({ nom, prenom });
    res.json({ nom, prenom });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};

exports.Users = async (req, res) => {
  try {
    const users = await Users();
    return res.status(200).json({ users });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};
