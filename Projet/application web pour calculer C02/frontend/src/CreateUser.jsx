import { useState } from "react";
import { useNavigate } from "react-router-dom";
import './CreateUser.css'; // Import the CSS file

const CreateUser = () => {
  const [formData, setFormData] = useState({
    nom: "",
    prenom: "",
    email: "",
    mdp: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Handle input changes
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
  
    console.log("Form data to send:", JSON.stringify(formData, null, 2));
  
    try {
      const response = await fetch("http://192.168.75.60/api/gC/creation", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData), // <-- Send raw form data
      });
  
      const result = await response.json();
  
      if (!response.ok) {
        throw new Error(result.message || "Erreur lors de l'inscription");
      }
  
      console.log("Inscription réussie:", result);

      navigate("/");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };
  

  return (
    <div className="container">
      <div className="form-container">
        <h2>Créer un compte</h2>
        
        {error && <p className="error-message">{error}</p>}
        
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label>Nom</label>
            <input
              type="text"
              name="nom"
              value={formData.nom}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-4">
            <label>Prénom</label>
            <input
              type="text"
              name="prenom"
              value={formData.prenom}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-4">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="mb-6">
            <label>Mot de passe</label>
            <input
              type="passsword"
              name="mdp"
              value={formData.mdp}
              onChange={handleChange}
              required
            />
          </div>

          <button
            type="submit"
            disabled={loading}
          >
            {loading ? "Chargement..." : "S'inscrire"}
          </button>
        </form>

        <p className="form-link">
          Vous avez déjà un compte ?{" "}
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              navigate("/");
            }}
          >
            Connectez-vous
          </a>
        </p>
      </div>
    </div>
  );
};

export default CreateUser;

