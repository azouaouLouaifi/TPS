const mysql = require('mysql');
require('dotenv').config();

const con = mysql.createPool({
    connectionLimit: 10, // ajustable
    host:process.env.HOST,
    user:process.env.USER,
    password:process.env.PASSWORD,
    database:process.env.DATABASE,

});

con.getConnection((err, connection) => {
    if (err) {
      console.error("Erreur lors de la connexion à la BDD:", err);
    } else {
      console.log("Connexion à la BDD réussie !");
      connection.release(); // important
    }
});

module.exports = con ;