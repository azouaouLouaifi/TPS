const express = require('express');
const cors = require('cors');
const cookieParser = require('cookie-parser');

const app =express();
const port = 5173 ;

const allowCredentialsMiddleware = (req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
 
  next();
};

// Utilisation du middleware pour toutes les routes de l'application
app.use(allowCredentialsMiddleware);
app.use(cookieParser());

// Configuration CORS
app.use(cors({
  origin: ['http://localhost:5173', 'http://192.168.75.60:5173'], // localhost + IP VM
  credentials: true
}));



require('dotenv').config()
const bodyparser =require('body-parser')
const routes_GC= require('./route/route_GC');
const routes_GT = require('./route/route_GT');
const routes_GH = require('./route/route_GH');
app.use(bodyparser.json())
 .use(bodyparser.urlencoded({extended:true})) 
 .use(routes_GC) 
 .use(routes_GT)
 .use(routes_GH)
app.listen(port, '0.0.0.0', () =>
  console.log(`notre application Node est demarre sur http://localhost:${port}`));