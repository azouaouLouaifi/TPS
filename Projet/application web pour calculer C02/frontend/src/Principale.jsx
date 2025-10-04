import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router";
import './Principale.css';

import profil from "./assets/anonyme_profil.jpg";
import IconeVelo from "./assets/iconeVelo.png";
import IconeMarche from "./assets/iconeMarche.png";
import IconeVoiture from "./assets/iconeVoiture.png";

import './Map.css';
import {Map, View, Feature} from 'ol';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { LineString } from 'ol/geom';
import { Stroke, Style } from 'ol/style';

// Liste des images disponibles dans le dossier assets
const profileImages = [
  { id: 'imageMarche', src: IconeMarche },
  { id: 'imageVelo', src: IconeVelo },
  { id: 'imageVoiture', src: IconeVoiture }
];

// Liste des options de transport
const transportOptions = [
  { id: 'foot-walking', label: 'Pied', icon: IconeMarche, num: 0},
  { id: 'cycling-regular', label: 'Vélo', icon: IconeVelo, num: 1},
  { id: 'driving-car', label: 'Voiture', icon: IconeVoiture, num: 2},
];

export default function Principale() {
  const [user, setUser] = useState(null);
  const [showRightColumn, setShowRightColumn] = useState(false);
  const [departure, setDeparture] = useState('');
  const [arrival, setArrival] = useState('');
  const [selectedTransport, setSelectedTransport] = useState('');
  const [showImageSelection, setShowImageSelection] = useState(false);
  const [map, setMap] = useState(null);
  const [routeLayers, setRouteLayers] = useState([]);
  const [showInfo, setShowInfo] = useState(false);
  const [dataAvailableTransport, setDataAvailableTransport] = useState([]);
  const [distanceTrajet, setDistance] = useState([]);
  const [durationTrajet, setDuration] = useState([]);
  const [carbonneTrajet, setCarbonne] = useState([]);
  const [routeDraw, setRouteDraw] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const mapInstance = new Map({
      target: 'map-OSM',
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
      ],
      view: new View({
        center: [0, 0],
        zoom: 2,
      }),
    });

    setMap(mapInstance);
    getDataTransports();
    const storedUser = localStorage.getItem("user");

    if (storedUser) {
      
      const parsedUser = JSON.parse(storedUser);
      setUser(parsedUser[0]);
    }

    return () => {
      mapInstance.setTarget(null);
    };
  }, []);

  const changeSelectTransport = (id) => {
    if (selectedTransport === id) {
      setSelectedTransport('');
    } else {
      setSelectedTransport(id);
    }
  };

  const handleImageSelection = (imageSrc) => {
    setProfileImage(imageSrc);
    setShowImageSelection(false);
  };

  const goToPageAccueil = () => {
      navigate('/');   
  };

  const goToPageHistorique = () => {
    navigate('/Historique');
  };

  const goToPageProfil = () => {
    navigate('/Profil');
  };

  const goToPageGestion = () => {
    navigate('/Gestion');   
  };

  const goToPageStatistique = () => {
    navigate('/Statistique');   
  }; 

  const geocodeAddress = async (address, apiKey) => {
    const url = `https://api.openrouteservice.org/geocode/search?api_key=${apiKey}&text=${encodeURIComponent(address)}&size=1`;
    const response = await fetch(url);
    const data = await response.json();
    if (data.features && data.features.length > 0) {
      return data.features[0].geometry.coordinates;
    } else {
      throw new Error(`Adresse non trouvée : ${address}`);
    }
  };

  // Récupère tous les transports disponible de la base de donnée
  const getDataTransports = async () => {
    try {
      const response = await fetch("http://192.168.75.60/api/gT/getAllTransports", {
        method: "GET",
        headers: { "Content-Type": "application/json" ,
          "auth_token": localStorage.getItem("token")},
      });
      const data = await response.json();
      setDataAvailableTransport(data); 
    } catch (err){
      setError(err.message);
    }
  };

  const calculateRoute = async () => {
    const apiKey = '5b3ce3597851110001cf6248cebe8d125e6d41328b88a60e2a8ea56c'; // clé OpenRouteService

    if (!departure || !arrival) {
      alert("Veuillez remplir les champs Départ et Arrivée.");
      return;
    }

    const startCoords = await geocodeAddress(departure, apiKey);
    const endCoords = await geocodeAddress(arrival, apiKey);

    const allRoutes = [];

    try {
      for (const transport of dataAvailableTransport.transports) {
        const url = `https://api.openrouteservice.org/v2/directions/${transport.nomt}/geojson`;
        const responseAPI = await fetch(url, {
          method: 'POST',
          headers: {
            'Authorization': apiKey,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ coordinates: [startCoords, endCoords] })
        });
        const data = await responseAPI.json();
        allRoutes.push(data);

        const distanceInMeters = Math.round(data.features[0].properties.summary.distance);
        setDistance(prec=> ({...prec, [transport.nomt]: distanceInMeters}));
    
        const durationInSeconde = Math.round(data.features[0].properties.summary.duration);
        setDuration(prec=> ({...prec, [transport.nomt]: durationInSeconde}));

        const carbonneParMetre = transport.constante * (distanceInMeters / 100);
        setCarbonne(prec=> ({...prec, [transport.nomt]: (carbonneParMetre).toFixed(2)}));

        const route = new Feature({
          geometry: new LineString(data.features[0].geometry.coordinates).transform('EPSG:4326', 'EPSG:3857')
        });
        setRouteDraw(prec=> ({...prec, [transport.nomt]: route}))
      }

      return allRoutes;

    } catch (error) {
      console.error("Erreur d'itinéraire :", error);
      alert("Impossible de calculer l'itinéraire. Vérifiez vos adresses.");
    }
  };

  const findRoute = (transport) => {
    const route = routeDraw[transport];

    route.setStyle(new Style({
      stroke: new Stroke({
        color: '#ff0000',
        width: 3
      })
    }));

    const vectorSource = new VectorSource({ features: [route] });
    const newRouteLayer = new VectorLayer({ source: vectorSource });

    map.addLayer(newRouteLayer);
    setRouteLayers(prev => [...prev, newRouteLayer]);
    map.getView().fit(vectorSource.getExtent(), { padding: [50, 50, 50, 50] });
  };

  // applique le changement de chemin sur la map
  useEffect(() => {
    if (!showInfo || !map) return;

    routeLayers.forEach(layer => {
      map.removeLayer(layer);
    });
    setRouteLayers([]);


    if (selectedTransport && routeDraw[selectedTransport]) {
      findRoute(selectedTransport);

    } else if (!selectedTransport && selectedTransport === '') {
      for (const transport of dataAvailableTransport.transports) {
        findRoute(transport.nomt);
      }
    }
    
  }, [showInfo, selectedTransport, routeDraw, map]); // savoir si la variable a changé

  // Sauvegarde Trajet
  const [] = useState({
    email: "",
    duree: 0,
    co2: 0,
    distance: 0,
    villeD: "",
    villeA: "",
    transports: [],
  });

  const sauvegardeTrajet = async () => {
    const transport = transportOptions.find(t => t.id === selectedTransport);

    const updatedFormData = {
      email: user.email,
      duree: durationTrajet[selectedTransport],
      co2: carbonneTrajet[selectedTransport],
      distance: distanceTrajet[selectedTransport],
      villeD: departure,
      villeA: arrival,
      transports: transport ? transport.num : -1,
    };

    console.log("Form data to send:", JSON.stringify(updatedFormData, null, 2));
  
    try {
      const response = await fetch("http://192.168.75.60/api/gH/ajouterHistorique", {
        method: "POST",
        headers: { "Content-Type": "application/json",
                  "auth_token": localStorage.getItem("token") },
        body: JSON.stringify(updatedFormData), // <-- Send raw form data
      });
  
      const result = await response.json();
  
      if (!response.ok) {
        throw new Error(result.message || "Erreur lors de l'ajout du trajet");
      }
  
      console.log("Trajet ajouté:", result);

    } catch (err) {
      setError(err.message);
    }
  };
  
  return (
    <div className="three-column-container">
      <div className="left-column">
        <h2>Trajet</h2>

        <div className="travel-input-section">
          <label htmlFor="departure" className="travel-label">Départ</label>
          <input
            type="text"
            id="departure"
            className="travel-input"
            value={departure}
            onChange={(e) => setDeparture(e.target.value)}
            placeholder="Entrez votre point de départ"
          />

          <label htmlFor="arrival" className="travel-label">Arrivée</label>
          <input
            type="text"
            id="arrival"
            className="travel-input"
            value={arrival}
            onChange={(e) => setArrival(e.target.value)}
            placeholder="Entrez votre destination"
          />

          <button onClick={async () => {await calculateRoute(); setShowInfo(true);}} className="history-button">
            Calculer l'itinéraire
          </button>

          <div className="transport-icons">
            {transportOptions.map((transport) => (
              <img
                key={transport.id}
                src={transport.icon}
                alt={transport.label}
                className={`transport-icon-button ${selectedTransport === transport.id ? 'selected' : ''}`}
                onClick={() => changeSelectTransport(transport.id)}
              />
            ))}
          </div>

        </div>
        <div>
        {showInfo && (
          <>
          <br />
            {!selectedTransport ? (
              <>
                {dataAvailableTransport.transports.map(transport => (
                  <div key={transport.idt}>
                    {transportOptions.at(transport.idt).label} : {distanceTrajet[transport.nomt] <= 1000 ? `${distanceTrajet[transport.nomt].toFixed(0)} m - ` : `${(distanceTrajet[transport.nomt] / 1000).toFixed(2)} km - `}
                    {durationTrajet[transport.nomt] / 60 < 60 ? `${(durationTrajet[transport.nomt] / 60).toFixed(2)} min` : durationTrajet[transport.nomt] / 3600 < 24
                      ? `${(durationTrajet[transport.nomt] / 3600).toFixed(2)} heures - ` : `${(durationTrajet[transport.nomt] / 86400).toFixed(0)} jours - `}
                    {`${carbonneTrajet[transport.nomt]} g/km Co2`}
                  </div>
                ))}
              </>
            ) : (
              <>
                {distanceTrajet[selectedTransport] <= 1000 ? `${distanceTrajet[selectedTransport].toFixed(0)} m - ` : `${(distanceTrajet[selectedTransport] / 1000).toFixed(2)} km - `}
                {durationTrajet[selectedTransport] / 60 < 60 ? `${(durationTrajet[selectedTransport] / 60).toFixed(2)} min - ` : durationTrajet[selectedTransport] / 3600 < 24
                      ? `${(durationTrajet[selectedTransport] / 3600).toFixed(2)} heures - ` : `${(durationTrajet[selectedTransport] / 86400).toFixed(0)} jours - `}
                {`${carbonneTrajet[selectedTransport]} Co2`}    
                
                <button onClick={ () => {sauvegardeTrajet()}} className="history-button">
                  Choisir ce trajet
                </button>          
              </>
            )}
          </>
        )}
        </div>
      </div>

      {/*Map*/}
      <div className="main-column">
        <h1>Carte</h1>
        <div id="map-OSM" ></div>
      </div>

      <div className={`right-column-container ${showRightColumn ? 'visible' : ''}`}>
        <button
          className={`toggle-button ${showRightColumn ? 'side-button' : 'center-button'}`}
          onClick={() => setShowRightColumn(!showRightColumn)}
        >
          {'<='}
        </button>

        {showRightColumn && (
          <div className="right-column">
            <div className="profile-section">
              <div className="profile-name">
                <p>
                <img src={profil} alt="anonyme_profil" width={100} height={100} className="mx-auto mb-4 w-24 h-24 " />
                {user ? user.nom : "Nom"} {user ? user.prenom : "Prenom"}
                </p>
              </div>
              <div className="buttonsMenu">
                <p><button onClick={goToPageProfil} className="buttonDonnees">Profil</button></p>
                <p><button onClick={goToPageHistorique} className="buttonDonnees">Historique</button></p>
                <p><button onClick={goToPageStatistique} className="buttonDonnees">Statistique</button></p>
                {user.role == "admin" ? <p><button onClick={goToPageGestion} className="buttonDonnees">Gestion</button></p> : ""}
              </div>
              <div className="buttonDeco">
                <button onClick={goToPageAccueil} >Déconnexion</button>
              </div>

              {/* Affichage des images */}
              {showImageSelection && (
                <div className="profile-image-selection">
                  <h4>Choisissez une image :</h4>
                  {profileImages.map((image) => (
                    <img
                      key={image.id}
                      src={image.src}
                      alt={image.id}
                      className="profile-image-thumbnail"
                      onClick={() => handleImageSelection(image.src)} // Mise à jour de l'image de profil
                    />
                  ))}
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
    
  );
}
