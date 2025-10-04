import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import profil from "./assets/anonyme_profil.jpg";
import "./Informations.css";

export default function Profil() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [showMdp, setShowMdp] = useState(false);

    useEffect(() => {
      const storedUser = localStorage.getItem("user");
      const storedToken = localStorage.getItem("token");

      if (!storedToken) {
        navigate("/");
        return;
      }
      if (storedUser) {
        
        const parsedUser = JSON.parse(storedUser);
        setUser(parsedUser[0]); // 👈 on récupère l'objet dans le tableau
        console.log(parsedUser[0]);
      }
    }, []);

  const goToPageAccueil = () => {
      navigate('/');   
  };

  const goToPagePrincipale = () => {
    navigate('/Principale');   
  };

  const goToPageHistorique = () => {
    navigate('/Historique');   
  };

  const goToPageGestion = () => {
    navigate('/Gestion');   
  };

  const goToPageStatistique = () => {
    navigate('/Statistique');   
  }; 

  const toggleMdpVisibility = () => {
    setShowMdp(!showMdp);
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="left-column-info">
        <div className="profil">
            <img src={profil} alt="anonyme_profil" width={100} height={100} className="mx-auto mb-4 w-24 h-24 " />
            <p>
            {user ? user.nom : "Nom"} {user ? user.prenom : "Prenom"}
            </p>
        </div>
        <div className="buttonsMenu">
            <p><button className="buttonDonneesActuelle">Profil</button></p>
            <p><button onClick={goToPageHistorique} className="buttonDonnees">Historique</button></p>
            <p><button onClick={goToPageStatistique} className="buttonDonnees">Statistique</button></p>
            {user ? (user.role == "admin" ? <p><button onClick={goToPageGestion} className="buttonDonnees">Gestion</button></p> : ""): ""}
        </div>
        <div className="buttonDeco">
            <p><button onClick={goToPageAccueil}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition duration-300">
            Déconnexion
            </button></p>
            <p><button onClick={goToPageAccueil}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition duration-300">
            Supprimer le compte
            </button></p>
        </div>
      </div>
      
      <div className="mainInformations">
          <div className="nomCategorie">Profil</div>
          <div className="informations">
          Nom : {user ? user.nom : "Nom"} <br /><br />
          Prenom : {user ? user.prenom : "Prenom"} <br /><br />
          Email : {user ? user.email : "email"} <br /><br />
          Mot de Passe : {showMdp ? user.mdp + " " : " ******** "}
          <button onClick={toggleMdpVisibility} style={{ fontSize: '0.8em', padding: '5px 10px' }}>
            {showMdp ? "Masquer le mot de passe" : "Afficher le mot de passe"}
          </button><br /><br />
          Role : {user ? user.role : "inconnu"} <br /><br />
          Date de creation : {user ? user.date : "inconnu"}
        </div>
      </div>
      <button className="quitButton" onClick={goToPagePrincipale} >
          X
        </button>
    </div>
  );
}