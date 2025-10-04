// controleur pour les transport
// a utiliser apres la gestion des accées
const {
  ajouterT, supprimerT, modiferConstante, getConstante, getAllTransports
} = require('../model/model_transport');

// ajout d'un transport
exports.ajouterT = async (req, res) => {
  try {
    const { nomT, constante } = req.body;
    await ajouterT({ nomT, constante });
    res.json({ nomT, constante });
  } catch (err) {
    if (err.code === 'ER_DUP_ENTRY') res.send('transport deja existant');
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// supprimer transport
exports.supprimerT = async (req, res) => {
  try {
    const { nomT } = req.body;
    await supprimerT({ nomT });
    res.json({ nomT });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// mdofier une constante
exports.modiferConstante = async (req, res) => {
  try {
    const { nomT, constante } = req.body;
    await modiferConstante({ nomT, constante });
    res.json({ nomT, constante });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// get constante
exports.getConstante = async (req, res) => {
  try {
    const { nomT } = req.body;
    const constante = await getConstante({ nomT });
    return res.status(200).json({ constante });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};

// getalltransport
exports.getAllTransports = async (req, res) => {
  try {
    const transports = await getAllTransports();
    return res.status(200).json({ transports });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
};
