const mysql= require('mysql')
require('dotenv').config();
var con =mysql.createConnection({
    host:process.env.HOST,
    user:process.env.USER,
    password:process.env.PASSWORD,
    database:process.env.DATABASE,

})
con.connect(err =>{
    try{if(err) throw err
    console.log("database is connected succefully")}
    catch(err){
 
        console.log(err,'wanu')
    }
})

module.exports =con 