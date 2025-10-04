import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../LoginPage";
import Historique from "../Historique"
import Principale from "../Principale"
import CreateUser from "../CreateUser";
import Profil from "../Profil";
import Statistique from "../Statistique";
import Gestion from "../Gestion"

export default createBrowserRouter([
    {
        path: '/', 
        element: <LoginPage />,
    },
    {
        path: '/Historique',
        element:<Historique />
    },
    {
        path: '/Principale',
        element:<Principale />
    },
    {
        path: '/CreateUser',
        element:<CreateUser />
    },
    {
        path: '/Profil',
        element:<Profil />
    },
    {
        path: '/Statistique',
        element:<Statistique />
    },
    {
        path: '/Gestion',
        element:<Gestion />
    }

]);