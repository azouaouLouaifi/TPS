const  {query}=require("../helper")

exports.ajouter_notif=async({userName,description,envoyeur})=>{
  userName=userName.replace(/\s/g, "")
  const now = new Date().toISOString();
  const sql=`insert into notification_${userName}  (userName,date,description) 
    values ("${envoyeur}","${now}","${description}")`
    await query(sql)

  }
  const jwt = require('jsonwebtoken');

  exports.getUserId=(token)=>{
    const decoded = jwt.verify(token, 'wanu');
    return decoded.userId

  }