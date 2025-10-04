const {query}=require('../helper.js')

/*exports.creer=async({ userName, password, role }) => {
   
    const sql = `INSERT INTO compte (userName, password) VALUES
         ('${userName}','${password}');`;
    await query(sql);

    const sql2= `INSERT INTO role (userName, role) VALUES
    ('${userName}','${role}');`;
    await query(sql2);
    }*/


    //avec notif 
exports.creer=async({ userName, password, role }) => {
   
    const sql = `INSERT INTO compte (userName, password) VALUES
         ('${userName}','${password}');`;
    await query(sql);

    const sql2= `INSERT INTO role (userName, role) VALUES
    ('${userName}','${role}');`; 
    await query(sql2);
    userName=userName.replace(/\s/g, "")
    const sql3= `create table notification_${userName} (
        
        userName varchar(50) references compte,
        date varchar(30)   ,
        description varchar(300),
        vue boolean default false, 
        primary key( date)

    )`;
    await query(sql3);  
 
}


/*exports.ajouterRole=async({ userName, role }) => {
    const sql= `INSERT INTO role (userName, role) VALUES
    ('${userName}','${role}');`;
    await query(sql);

}*/
// avec notif
exports.ajouterRole=async( userName, role ) => {
    const sql= `INSERT INTO role (userName, role) VALUES
    ('${userName}','${role}');`;

    await query(sql);
     userName=userName.replace(/\s/g, "")
     const now = new Date().toISOString();
     
     console.log('now', now)

    const sql2= `INSERT INTO notification_${userName} (userName, date, description, vue) VALUES
    ('Directrice','${now}','La Directrice vous a attribuer un nouveau role:${role}', ${0});`;
    
    await query(sql2);

}

exports.removeRole=async( userName, role ) => {
    const sql= `DELETE FROM role  WHERE  (userName='${userName}' and role='${role}');`;

    await query(sql);
     userName=userName.replace(/\s/g, "") 
     const now = new Date().toISOString();

    const sql2= `INSERT INTO notification_${userName} (userName, date,description) VALUES
    ('Directrice',"${now}",'La Directrice vous a supprimer le role:${role}');`;
    await query(sql2); 
    

}


exports.recherchercompte=async({userName,password }) => {
    const sql= `select * from compte where
    userName="${userName}" and password="${password}"`;
    const compte= query(sql);
    return compte
}       


exports.rechercher_role=async({ userName}) => {
    const sql= `select role from role where
     userName='${userName}' `;
    const roles= query(sql);
    return roles

}


exports.consulter_cpt=async() => {
    const sql= `select * from compte  `;
    const comptes= query(sql);
    return comptes

}
// suprimer un compte
exports.supprimer=async( userName) => {
    const sql= `delete from compte where  userName="${userName}" `;
    console.log(sql)
    query(sql);
 
    userName=userName.replace(/\s/g, "")
    console.log(userName)

    const sql2= `drop table notification_${userName}`; 
    console.log(sql2)
    query(sql2); 

}


exports.constlercompte=async(userName )=>{
    const sql= `select * from compte where
    userName="${userName}"`;
    const compte= query(sql);
    return compte

}

exports.constlerRoles=async(userName )=>{
    const sql= `select role from role  where
    userName="${userName}"`;
    const roles= query(sql);
    return roles

}


exports.consulter_notification=async(userName) => {
    userName=userName.replace(/\s/g, "")
    const sql= `select * from notification_${userName} ORDER BY vue DESC, date ASC    ; `;
    const notifications= query(sql);
    return notifications

}

exports.consulter_notification_date=async({userName, date}) => {
    tableName=userName.replace(/\s/g, "")
    const sql= `select * from notification_${tableName} where userName='${userName}'and date='${date}'; `;
    const notification= query(sql);
    return notification

}

// recherche les utilisateure avec le role passer
exports.rechercher_username=async(role)=>{
    const sql= `select userName from role where role="${role}"`;
    const userNames= query(sql);
    return userNames

}




// modifier vu d'une notif
exports.voir=async({userName,date, envoyeur})=>{
    tableName=envoyeur.replace(/\s/g, "")

    const sql= `update notification_${tableName} 
    set vue= ${1} where date= '${date}' and userName="${userName}"`;
    
    console.log(sql)
    await query(sql)

}