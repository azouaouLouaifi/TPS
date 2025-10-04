import { useState } from "react";
import { useNavigate } from "react-router-dom";
import feuille2 from "./assets/feuille2.png";

const LoginPage = () => {
  const [formData, setFormData] = useState({
    email: "",
    mdp: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      const response = await fetch("http://192.168.75.60/api/gC/connexion", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      const result = await response.json();

      if (!response.ok) {
        throw new Error(result.message || "Erreur de connexion");
      }

      if (!result.token) {
        throw new Error("Token manquant dans la réponse du serveur.");
      }

      localStorage.setItem("token", result.token);
      localStorage.setItem("user", JSON.stringify(result.user));
      console.log("Connexion réussie, token :", result.token);

      navigate("/Principale");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <div className="form-container">
        <div className="logo-container">
          <img src={feuille2} alt="Logo" className="logo"/>
        </div>

        <h2>Connexion</h2>

        {error && <p className="error-message">{error}</p>}

        <form onSubmit={handleSubmit}>
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
              type="password"
              name="mdp"
              value={formData.mdp}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "Connexion..." : "Se connecter"}
          </button>
        </form>

        <p className="form-link">
          Vous n'avez pas de compte ?{" "}
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              navigate("/CreateUser");
            }}
          >
            Créez-en un ici
          </a>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
