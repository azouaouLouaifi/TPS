exports.verifChamps_enq_np = ({prescription, observation, nature}, data) => {
    if (prescription === undefined) prescription = data[0].prescription;
    if (observation === undefined) observation = data[0].observation;
    if (nature === undefined) nature = data[0].nature;
    const resulta = { prescription, observation, nature };
    return resulta;
  };


  exports.verifChamps_enq_cons = ({prescription, observation, etat}, data) => {
    if (prescription === undefined) prescription = data[0].prescription;
    if (observation === undefined) observation = data[0].observation;
    if (etat === undefined) etat = data[0].etat;
    const resulta = { prescription, observation, etat };
    return resulta;
  };