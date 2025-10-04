
exports.verifChamps_equ=({nom,fonctionE,topologie,statut,adresse,type},data) => {
    
    if(nom===undefined) nom= data[0].nom
    if(topologie === undefined) topologie=data[0].topologie
    if(fonctionE === undefined) fonctionE=data[0].fonctionE
    if(topologie === undefined) topologie=data[0].topologie
    if(statut === undefined) statut=data[0].statut
    if(adresse === undefined) adresse=data[0].adresse
    if(type === undefined) type=data[0].type
    
    const resulta={nom,fonctionE,topologie,statut,adresse,type}
    return resulta

}


exports.verifChamps_non_equ=({nom,adresse,historique,type},data) => {
    
    if(nom===undefined) nom= data[0].nom
    if(historique===undefined) historique= data[0].historique
    if(adresse === undefined) adresse=data[0].adresse
    if(type === undefined) type=data[0].type
    
    const resulta={nom,adresse,historique,type}
    return resulta

}

exports.verifChamps_un=({ nom,des },data) => {
    
    if(nom===undefined) nom= data[0].nom
    if(des===undefined) des= data[0].des
    const resulta={ nom,des }
    return resulta

}

exports.verifChamps_per=({ nomP,des },data) => {
    
    if(nomP===undefined) nomP= data[0].nomP
    if(des===undefined) des= data[0].des
    const resulta={ nomP,des }
    return resulta

}
// --
exports.verifChamps_sec=({ codeW, nom, classement },data) => {
    
    if(nom===undefined) nom= data[0].nom
    if(codeW===undefined) codeW= data[0].codeW
    if(classement===undefined) classement= data[0].classement
    const resulta={ codeW, nom, classement }
    return resulta

}
exports.verifChamps_bat=({ nbr_p,nom_pro,prenom,participation_restauration,plan_d_attaque,libere_lieux},data) => {
    
    if(nbr_p===undefined) nbr_p= data[0].nbr_p
    if(nom_pro===undefined) nom_pro= data[0].nom_pro
    if(prenom===undefined) prenom= data[0].prenom
    if(participation_restauration===undefined) participation_restauration= data[0].participation_restauration
    if(plan_d_attaque===undefined) plan_d_attaque= data[0].plan_d_attaque
    if(libere_lieux===undefined) libere_lieux= data[0].libere_lieux
    const resulta={ nbr_p,nom_pro,prenom,participation_restauration,
        plan_d_attaque,libere_lieux }
    return resulta

}

exports.verifChamps_fon=({ ancienNom,historique},data) => {
    
    if(ancienNom===undefined) ancienNom= data[0].ancienNom
    if(historique===undefined) historique= data[0].historique
    const resulta={ ancienNom,historique }
    return resulta

}

