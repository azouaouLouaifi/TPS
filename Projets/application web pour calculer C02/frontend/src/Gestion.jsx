import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import profil from "./assets/anonyme_profil.jpg";
import "./Informations.css";

export default function Profil() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [showMdp, setShowMdp] = useState(false);
  const [transports, setTransports] = useState([]);
  const [users, setUsers] = useState([]);
  const [modifications, setModifications] = useState({});
  const [newTransport, setNewTransport] = useState({ nomT: "", constante: "" });

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    const storedToken = localStorage.getItem("token");

    if (!storedToken) {
      navigate("/");
      return;
    }

    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      const currentUser = parsedUser[0];
      setUser(currentUser);

      if (currentUser.role === "admin") {
        fetch("http://192.168.75.60/api/gT/getAllTransports", {
          headers: {
            auth_token: storedToken,
          },
        })
          .then((res) => res.json())
          .then((data) => {
            console.log("Transports récupérés:", data.transports);
            setTransports(data.transports || []);
          })
          .catch((err) => console.error("Erreur lors du chargement des transports :", err));

        fetch("http://192.168.75.60/api/gC/users", {
          headers: {
            auth_token: storedToken,
          },
        })
          .then((res) => res.json())
          .then((data) => {
            console.log("Utilisateurs récupérés:", data.users);
            setUsers(data.users || []);
          })
          .catch((err) => console.error("Erreur lors du chargement des utilisateurs :", err));
      }
    }
  }, [navigate]);

  const goToPageAccueil = () => navigate("/");
  const goToPagePrincipale = () => navigate("/Principale");
  const goToPageHistorique = () => navigate("/Historique");
  const goToPageProfil = () => navigate("/Profil");
  const goToPageStatistique = () => navigate("/Statistique");
  const toggleMdpVisibility = () => setShowMdp(!showMdp);

  const handleConstanteChange = (nomT, newValue) => {
    setModifications((prev) => ({ ...prev, [nomT]: newValue }));
  };

  const handleModifierConstante = async (nomT) => {
    const constante = modifications[nomT];
    if (!constante) return;

    const storedToken = localStorage.getItem("token");

    try {
      const response = await fetch("http://192.168.75.60/api/gT/modifierT", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          auth_token: storedToken,
        },
        body: JSON.stringify({ nomT, constante }),
      });

      if (!response.ok) {
        alert("Erreur lors de la modification.");
      } else {
        alert("Constante mise à jour.");
        const data = await fetch("http://192.168.75.60/api/gT/getAllTransports", {
          headers: {
            auth_token: storedToken,
          },
        }).then((res) => res.json());
        setTransports(data.transports || []);
      }
    } catch (err) {
      console.error(err);
      alert("Erreur serveur");
    }
  };

  const handleAddTransport = async () => {
    const storedToken = localStorage.getItem("token");
    const { nomT, constante } = newTransport;
    if (!nomT || !constante) return alert("Veuillez remplir tous les champs.");

    try {
      const response = await fetch("http://192.168.75.60/api/gT/ajouterT", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          auth_token: storedToken,
        },
        body: JSON.stringify({ nomT, constante }),
      });

      if (!response.ok) {
        const text = await response.text();
        alert("Erreur lors de l'ajout: " + text);
      } else {
        alert("Transport ajouté avec succès.");
        setNewTransport({ nomT: "", constante: "" });
        const data = await fetch("http://192.168.75.60/api/gT/getAllTransports", {
          headers: { auth_token: storedToken },
        }).then((res) => res.json());
        setTransports(data.transports || []);
      }
    } catch (err) {
      console.error(err);
      alert("Erreur serveur");
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 p-4">
      <div className="left-column-info">
        <div className="profil">
          <img src={profil} alt="anonyme_profil" width={100} height={100} className="mx-auto mb-4 w-24 h-24" />
          <p>
            {user?.nom || "Nom"} {user?.prenom || "Prenom"}
          </p>
        </div>
        <div className="buttonsMenu">
          <p><button onClick={goToPageProfil} className="buttonDonnees">Profil</button></p>
          <p><button onClick={goToPageHistorique} className="buttonDonnees">Historique</button></p>
          <p><button onClick={goToPageStatistique} className="buttonDonnees">Statistique</button></p>
          {user?.role === "admin" && (
            <p><button className="buttonDonneesActuelle">Gestion</button></p>
          )}
        </div>
        <div className="buttonDeco">
          <p>
            <button onClick={goToPageAccueil}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition duration-300">
              Déconnexion
            </button>
          </p>
          <p>
            <button onClick={goToPageAccueil}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg transition duration-300">
              Supprimer le compte
            </button>
          </p>
        </div>
      </div>

      <div className="mainInformations">
        <div className="nomCategorie">Gestion</div>
        <div className="informations">
          {user?.role === "admin" ? (
            <div className="flex flex-row gap-8">
              {/* Bloc gauche : transports */}
              <div className="w-1/2">
                <h2 className="font-bold text-lg mb-2">Liste des transports</h2>
                <table className="w-full border text-sm mb-4">
                  <thead>
                    <tr className="bg-gray-200">
                      <th className="p-2">Nom</th>
                      <th className="p-2">Constante</th>
                      <th className="p-2">Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {transports.map((t) => (
                      <tr key={t.nomT} className="border-t">
                        <td className="p-2">{t.nomt}</td>
                        <td className="p-2">
                          <input
                            type="number"
                            step="any"
                            defaultValue={t.constante}
                            onChange={(e) => handleConstanteChange(t.nomt, e.target.value)}
                            className="border px-2 py-1 rounded w-full"
                          />
                        </td>
                        <td className="p-2">
                          <button
                            onClick={() => handleModifierConstante(t.nomt)}
                            className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                          >
                            Modifier
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                <div>
                  <input
                    type="text"
                    placeholder="Nom du transport"
                    value={newTransport.nomT}
                    onChange={(e) => setNewTransport({ ...newTransport, nomT: e.target.value })}
                    className="border px-2 py-1 rounded mb-2 w-full"
                  />
                  <input
                    type="number"
                    placeholder="Constante"
                    step="any"
                    value={newTransport.constante}
                    onChange={(e) => setNewTransport({ ...newTransport, constante: e.target.value })}
                    className="border px-2 py-1 rounded mb-2 w-full"
                  />
                  <button
                    onClick={handleAddTransport}
                    className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 w-full"
                  >
                    Ajouter
                  </button>
                </div>
              </div>

              <div className="w-1/2">
                <h2 className="font-bold text-lg mb-4">Utilisateurs</h2>
                <ul className="space-y-2">
                  {users.map((u, idx) => (
                    <li key={idx} className="bg-white p-2 rounded shadow text-sm">
                      {u.nom} {u.prenom} — {u.email} - {u.role}

                    </li>
                    
                  ))}
                </ul>
              </div>
            </div>
          ) : (
            <p className="text-red-500">Accès réservé à l’administrateur.</p>
          )}
        </div>
      </div>

      <button className="quitButton" onClick={goToPagePrincipale}>X</button>
    </div>
  );
}
