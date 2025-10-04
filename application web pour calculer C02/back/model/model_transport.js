// model pour la gestion des transports

const { query } = require('../helper.js');

// ajouter un transport
exports.ajouterT = async ({ nomT, constante }) => {
  const sql = `INSERT INTO transport ( nomT, constante) VALUES
         ('${nomT}',${constante});`;
  await query(sql);
};

// supprimer un transport
exports.supprimerT = async ({ nomT }) => {
  const sql = `delete from transport where nomT = '${nomT}'; `;
  await query(sql);
};

// retourner la constante pour un transport donner
exports.getConstante = async ({ nomT }) => {
  const sql = `select constante from transport where nomT = '${nomT}'; `;
  const constante = await query(sql);
  return constante;
};

// modifier la constante
exports.modiferConstante = async ({ nomT, constante }) => {
  const sql = `Update transport set constante = '${constante}'   where nomt = '${nomT}' ; `;
  await query(sql);
};

// retourner la constante pour un transport donner
exports.getTdT = async ({ nomT }) => {
  const sql = `select idT from transport where nomT = '${nomT}'; `;
  const idT = await query(sql);
  return idT;
};

// ajouter pour le front
exports.getAllTransports = async () => {
  const sql = 'select idt, nomt, constante from transport; ';
  const transports = await query(sql);
  return transports;
};
