import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios";
import profil from "./assets/anonyme_profil.jpg";
import {
  LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer
} from 'recharts';
import './Informations.css';

export default function Statistique() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [co2Annee, setCo2Annee] = useState(null);
  const [co2Mois, setCo2Mois] = useState(null);
  const [mois, setMois] = useState((new Date().getMonth() + 1).toString().padStart(2, '0'));
  const [annee, setAnnee] = useState(new Date().getFullYear().toString());
  const [graphData, setGraphData] = useState([]);

  const goToPageAccueil = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    navigate('/');
  };

  const goToPagePrincipale = () => navigate('/Principale');
  const goToPageProfil = () => navigate('/Profil');
  const goToPageHistorique = () => navigate('/Historique');
  const goToPageGestion = () => navigate('/Gestion');

  const fetchStatistiques = async () => {
  const storedToken = localStorage.getItem("token");
  if (!user || !storedToken) return;

  try {
    console.log(" Début fetchStatistiques pour :", user.email, mois, annee);

    const resAnnee = await axios.post("http://192.168.75.60/api/gH/qauntiteCo2Annee", {
      email: user.email, annee
    }, {
      headers: { auth_token: storedToken }
    });
    console.log(" Résultat CO2 Année :", resAnnee.data);
    setCo2Annee({
      quantite: resAnnee.data.qte?.[0]?.["sum(co2)"] ?? 0
    });

    const resMois = await axios.post("http://192.168.75.60/api/gH/qauntiteCo2Mois", {
      email: user.email, mois, annee
    }, {
      headers: { auth_token: storedToken }
    });
    console.log(" Résultat CO2 Mois :", resMois.data);
    setCo2Mois({
      quantite: resMois.data.qte?.[0]?.["sum(co2)"] ?? 0
    });

    const moisPromises = Array.from({ length: 12 }, (_, i) => {
      const m = (i + 1).toString().padStart(2, '0');
      return axios.post("http://192.168.75.60/api/gH/qauntiteCo2Mois", {
        email: user.email, mois: m, annee
      }, {
        headers: { auth_token: storedToken }
      }).then(res => {
        console.log(` Mois ${m} => `, res.data);
        return {
          mois: m, quantite: res.data.qte?.[0]?.["sum(co2)"] ?? 0
        };
      }).catch(err => {
        console.warn(` Erreur Mois ${m}`, err);
        return {
          mois: m, quantite: 0
        };
      });
    });

    const results = await Promise.all(moisPromises);
    console.log(" Données pour le graphique :", results);
    setGraphData(results);

  } catch (error) {
    console.error("Erreur récupération statistiques CO2 :", error);
  }
};

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const storedToken = localStorage.getItem("token");
    if (!storedToken) navigate("/");
    else if (storedUser) setUser(JSON.parse(storedUser)[0]);
  }, []);

  useEffect(() => {
    if (user) fetchStatistiques();
  }, [user, mois, annee]);

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="left-column-info">
        <div className="profil">
          <img src={profil} alt="profil" width={100} height={100} className="profilImage" />
          <p>{user?.nom ?? "Nom"} {user?.prenom ?? "Prenom"}</p>
        </div>
        <div className="buttonsMenu">
          <p><button onClick={goToPageProfil} className="buttonDonnees">Profil</button></p>
          <p><button onClick={goToPageHistorique} className="buttonDonnees">Historique</button></p>
          <p><button className="buttonDonneesActuelle">Statistique</button></p>
          {user ? (user.role == "admin" ? <p><button onClick={goToPageGestion} className="buttonDonnees">Gestion</button></p> : ""): ""}
        </div>
        <div className="buttonDeco">
          <p><button onClick={goToPageAccueil} className="actionButton">Déconnexion</button></p>
          <p><button onClick={goToPageAccueil} className="actionButton">Supprimer le compte</button></p>
        </div>
      </div>

      <div className="mainInformations">
        <div className="nomCategorie"> Statistiques CO2</div>
        <div className="informations-Statistique">
          <div className="filters">
            <select value={mois} onChange={(e) => setMois(e.target.value)}>
              {Array.from({ length: 12 }, (_, i) => (i + 1).toString().padStart(2, '0')).map(m => (
                <option key={m} value={m}>{m}</option>
              ))}
            </select>
            <select value={annee} onChange={(e) => setAnnee(e.target.value)}>
              {Array.from({ length: 5 }, (_, i) => (new Date().getFullYear() + i).toString()).map(a => (
                <option key={a} value={a}>{a}</option>
              ))}
            </select>
          </div>
          <div className="co2-stats">
            <div>
              Quantité CO2 pour l'année {annee} :
              {co2Annee ? `${co2Annee.quantite} g` : "Chargement"}
            </div>
            <div>
              Quantité CO2 pour le mois {mois}/{annee} :
              {co2Mois ? `${co2Mois.quantite} g` : "Chargement"}
            </div>
          </div>
          <h4>Émissions mensuelles en {annee}</h4>
        <div className="graph">
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={graphData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="mois" />
              <YAxis unit=" g" />
              <Tooltip />
              <Line type="monotone" dataKey="quantite" stroke="#10b981" strokeWidth={3} dot={{ r: 4 }} activeDot={{ r: 6 }} /> 
            </LineChart>
          </ResponsiveContainer>
        </div>
        </div>  
    </div>
    <button onClick={goToPagePrincipale} className="quitButton">X</button>
    </div>
  );
}
