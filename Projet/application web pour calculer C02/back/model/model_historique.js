const { format } = require('date-fns');
const { query } = require('../helper.js');

// ajouter un historique
exports.ajouterHistorique = async ({
  email, duree, co2, distance, villeD, villeA, transports
}) => {
  const dateCourante = format(new Date(), 'yyyy-MM-dd HH:mm:ss');
  const sql = `INSERT INTO historique (email,date , duree, co2, distance ,villeD, villeA) VALUES
         ('${email}','${dateCourante}','${duree}','${co2}','${distance}','${villeD}','${villeA}');`;
  await query(sql);
  // ajout mode de transports
  if (Array.isArray(transports)) {
    for (const idt of transports) {
      const sql2 = `
            INSERT INTO hmt
              (email, date, idt)
            VALUES
              ('${email}', '${dateCourante}', '${idt}');
          `;
      await query(sql2);
    }
  } else {
    const sql2 = `
          INSERT INTO hmt
            (email, date, idt)
          VALUES
            ('${email}', '${dateCourante}', '${transports}');
        `;
    await query(sql2);
  }
};

// quantité de co2 pour un mois donner
exports.qauntiteCo2Mois = async ({ email, mois, annee }) => {
  const sql = `select sum(co2) from historique where email = '${email}' and  YEAR(date)  = '${annee}'
      AND MONTH(date) = '${mois}';`;
  const co2 = await query(sql);
  return co2;
};

// quantité de co2 pour une année donner
exports.qauntiteCo2Annee = async ({ email, annee }) => {
  const sql = `select sum(co2) from historique where email = '${email}' AND YEAR(date)  = '${annee}';`;
  const co2 = await query(sql);
  return co2;
};

// historique pour un mois donner sans moyens de transports
exports.historiqueUserMois = async ({ email, mois, annee }) => {
  const sql = `select date , duree, co2, distance ,villeD, villeA from historique where email = '${email}' and  YEAR(date)  = '${annee}'
      AND MONTH(date) = '${mois}';`;
  const historique = await query(sql);
  return historique;
};

// historique pour un mois donner avec moyens de transports
exports.historiqueUserMoisT = async ({ email, mois, annee }) => {
  const sql = `select  h.email ,h.date , h.duree, h.co2, h.distance ,h.villeD, h.villeA , GROUP_CONCAT(DISTINCT t.nomT ORDER BY t.nomT SEPARATOR ', ') AS transports
 from historique h, hmt hm, transport t where hm.email = '${email}' 
    and hm.email = h.email
     and  YEAR(hm.date)  = '${annee}'
      AND MONTH(hm.date) = '${mois}'
      and hm.date=h.date
      and hm.idt=t.idt
      group by h.email ,h.date , h.duree, h.co2, h.distance ,h.villeD, h.villeA 
      ;`;
  const historique = await query(sql);
  return historique;
};

// ajouter a la demande du front
