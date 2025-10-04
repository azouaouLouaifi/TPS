const {
  ajouterHistorique, qauntiteCo2Mois, qauntiteCo2Annee, historiqueUserMois, historiqueUserMoisT
} = require('../model/model_historique');

exports.ajouterHistorique = async (req, res) => {
  try {
    // ici transports un tableau ou bien une variable
    const {
      email, duree, co2, distance, villeD, villeA, transports
    } = req.body;
    await ajouterHistorique({
      email, duree, co2, distance, villeD, villeA, transports
    });
    res.json({
      email, duree, co2, distance, villeD, villeA, transports
    });
  } catch (err) {
    if (err.code === 'ER_DUP_ENTRY') res.send('transport deja existant');
    console.error(err);
    res.status(500).send('Server Error');
  }
};

exports.qauntiteCo2Mois = async (req, res) => {
  try {
    const { email, mois, annee } = req.body;
    const qte = await qauntiteCo2Mois({ email, mois, annee });
    return res.status(200).json({ qte });
  } catch (err) {
    res.status(500).send('Server Error');
  }
};

exports.qauntiteCo2Annee = async (req, res) => {
  try {
    const { email, annee } = req.body;
    const qte = await qauntiteCo2Annee({ email, annee });
    return res.status(200).json({ qte });
  } catch (err) {
    res.status(500).send('Server Error');
  }
};

exports.historiqueUserMois = async (req, res) => {
  try {
    const { email, mois, annee } = req.body;
    const historique = await historiqueUserMois({ email, mois, annee });
    return res.status(200).json({ historique });
  } catch (err) {
    res.status(500).send('Server Error');
  }
};

exports.historiqueUserMoisT = async (req, res) => {
  try {
    const { email, mois, annee } = req.body;
    const historique = await historiqueUserMoisT({ email, mois, annee });
    return res.status(200).json({ historique });
  } catch (err) {
    res.status(500).send('Server Error');
  }
};
