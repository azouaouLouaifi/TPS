import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import axios from "axios";
import profil from "./assets/anonyme_profil.jpg";
import "./Informations.css";

export default function Historique() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [historique, setHistorique] = useState([]);
  const [mois, setMois] = useState(new Date().getMonth() + 1);
  const [annee, setAnnee] = useState(new Date().getFullYear());
  const [transportFiltre, setTransportFiltre] = useState("tous");
  const [transportsDispo, setTransportsDispo] = useState([]);
  const [affichePlusRecent, setAffichePlusRecent] = useState("Plus récent");

  const goToPageAccueil = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    navigate('/');
  };

  const goToPagePrincipale = () => navigate('/Principale');
  const goToPageProfil = () => navigate('/Profil');
  const goToPageStatistique = () => navigate('/Statistique');
  const goToPageGestion = () => navigate('/Gestion');

  const handleDeleteAccount = async () => {
    if (!user) return;
    const storedToken = localStorage.getItem("token");

    const confirmDelete = window.confirm("Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.");
    if (!confirmDelete) return;

    try {
      const response = await fetch('http://192.168.75.60/api/gC/supprimerU', {
        method: 'DELETE',
        headers: {
          auth_token: storedToken,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          nom: user.nom,
          prenom: user.prenom,
        }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Erreur lors de la suppression du compte");
      }

      alert("Votre compte a été supprimé avec succès.");
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      navigate('/');
    } catch (error) {
      console.error("Erreur suppression :", error);
      alert("Échec de la suppression du compte : " + error.message);
    }
  };

  const fetchHistorique = async () => {
    const storedToken = localStorage.getItem("token");
    if (!user || !storedToken) return;

    try {
      const response = await axios.post(
        "http://192.168.75.60/api/gH/historiqueUserMoisT",
        {
          email: user.email,
          mois,
          annee,
        },
        {
          headers: {
            auth_token: storedToken,
          },
        }
      );

      const data = response.data.historique || [];
      setHistorique(data);

      const transportsSet = new Set();
      data.forEach(trajet => {
        if (trajet.transports) {
          trajet.transports.split(", ").forEach(t => transportsSet.add(t));
        }
      });
      setTransportsDispo(["tous", ...Array.from(transportsSet)]);
    } catch (error) {
      console.error("Erreur lors de la récupération de l'historique :", error);
    }
  };

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const storedToken = localStorage.getItem("token");

    if (!storedToken) {
      navigate("/");
      return;
    }

    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser[0]);
    }
  }, []);

  useEffect(() => {
    if (user) {
      fetchHistorique();
    }
  }, [user, mois, annee]);

  const OrdreListe = () => {
    setHistorique([...historique].reverse());
  };

  const getDureeFormattee = (duree) => {
    if (duree / 60 < 60) {
      return `${duree.toFixed(2)} min`;
    } else if (duree / 3600 < 24) {
      return `${(duree / 3600).toFixed(2)} heures`;
    } else {
      return `${(duree / 86400).toFixed(0)} jours`;
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
      {/* Menu latéral */}
      <div className="left-column-info">
        <div className="profil">
          <img src={profil} alt="anonyme_profil" width={100} height={100} className="mx-auto mb-4 w-24 h-24" />
          <p>{user?.nom || "Nom"} {user?.prenom || "Prenom"}</p>
        </div>
        <div className="buttonsMenu">
          <p><button onClick={goToPageProfil} className="buttonDonnees">Profil</button></p>
          <p><button className="buttonDonneesActuelle">Historique</button></p>
          <p><button onClick={goToPageStatistique} className="buttonDonnees">Statistique</button></p>
          {user?.role === "admin" && (
            <p><button onClick={goToPageGestion} className="buttonDonnees">Gestion</button></p>
          )}
        </div>
        <div className="buttonDeco">
          <p><button onClick={goToPageAccueil} className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition duration-300">
            Déconnexion</button></p>
          <p><button onClick={handleDeleteAccount} className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition duration-300">
            Supprimer le compte</button></p>
        </div>
      </div>

      {/* Contenu principal */}
      <div className="mainInformations">
        <div className="nomCategorie">Historique des trajets</div>
        <div className="informations-historique">

          {/* Filtres */}
          <div className="filters">
            <select value={mois} onChange={(e) => setMois(Number(e.target.value))}>
              {Array.from({ length: 12 }, (_, i) => i + 1).map(m => (
                <option key={m} value={m}>{m.toString().padStart(2, '0')}</option>
              ))}
            </select>

            <select value={annee} onChange={(e) => setAnnee(Number(e.target.value))}>
              {Array.from({ length: 5 }, (_, i) => new Date().getFullYear() - i).map(a => (
                <option key={a} value={a}>{a}</option>
              ))}
            </select>

            <select value={transportFiltre} onChange={(e) => setTransportFiltre(e.target.value)}>
              {transportsDispo.map((t) => (
                <option key={t} value={t}>{t}</option>
              ))}
            </select>

            <select value={affichePlusRecent} onChange={(e) => { setAffichePlusRecent(e.target.value); OrdreListe(); }}>
              <option value="Plus récent">Plus récent</option>
              <option value="Plus ancien">Plus ancien</option>
            </select>
          </div>

          {/* Historique trajets */}
          <div className="trajets">
            {historique
              .filter(trajet => transportFiltre === "tous" || trajet.transports?.includes(transportFiltre))
              .map((trajet) => {
                const key = `${trajet.date}-${trajet.villeD}-${trajet.villeA}`;
                const dureeAffichee = getDureeFormattee(trajet.duree);
                const distanceAffichee = trajet.distance <= 1000
                  ? `${trajet.distance.toFixed(0)} m`
                  : `${(trajet.distance / 1000).toFixed(2)} km`;

                return (
                  <div key={key} className="trajet-item">
                    <p><strong>Date :</strong> {new Date(trajet.date).toLocaleString()}</p>
                    <p><strong>De :</strong> {trajet.villeD} → <strong>À :</strong> {trajet.villeA}</p>
                    <p><strong>Durée :</strong> {dureeAffichee}</p>
                    <p><strong>Distance :</strong> {distanceAffichee}</p>
                    <p><strong>CO2 :</strong> {trajet.co2} g/km</p>
                    <p><strong>Transports :</strong> {trajet.transports || "N/A"}</p>
                  </div>
                );
              })}
          </div>
        </div>
      </div>
      <button onClick={goToPagePrincipale} className="quitButton">X</button>
    </div>
  );
}
