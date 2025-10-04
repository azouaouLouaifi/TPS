// model pour la gestion des users

const { format } = require('date-fns');
const { query } = require('../helper.js');

// creation d'un compte
exports.creer = async ({
  nom, prenom, mdp, email
}) => {
  const dateCourante = format(new Date(), 'yyyy-MM-dd HH:mm:ss');
  const sql = `INSERT INTO compte (nom, prenom, mdp, email, date, role) VALUES
         ('${nom}','${prenom}','${mdp}','${email}','${dateCourante}','user');`;
  await query(sql);
};

// connexion verifie si user exist
exports.connexion = async ({ email, mdp }) => {
  const sql = `select * from compte where mdp = '${mdp}' and email = '${email}'; `;
  const user = await query(sql);
  return user;
};

// retour le nombre d'utilisateur inscrit dans la platforme
exports.nbrUser = async () => {
  const sql = 'select count(*) from compte; ';
  const nbrUser = await query(sql);
  return nbrUser;
};

// supprimer un compte
exports.supprimerU = async ({ nom, prenom }) => {
  const sql = `delete from compte where nom = '${nom}' and prenom = '${prenom}'; `;
  await query(sql);
};

exports.Users = async () => {
  const sql = 'select nom, prenom, email, date, role from compte; ';
  const nbrUser = await query(sql);
  return nbrUser;
};
