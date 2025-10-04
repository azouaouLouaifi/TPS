/*---------CREATION UTILISATEUR-----------*/

alter session set "_ORACLE_SCRIPT"=true;
CREATE USER TP4user IDENTIFIED BY mdp;
grant all privileges to TP4user;
revoke ALL privileges from TP4user;
grant select on vu to Public;
revoke select on vu from Public;
Grant select,insert,UPDATE,Delete on SEANCE to TP4user3;
drop user TP4user3;


/*---------CREATION TABLE-----------*/
create table unite  (
   code_Unite           INTEGER                     not null,
   libelle              VARCHAR2(40),
   nbr_heure            INTEGER,
    matricule_ens        INTEGER                    not null,
   constraint PK_unite primary key (code_Unite),
 constraint fk_unite_enseignant foreign key (matricule_ens) references enseignant (matricule_ens)
);

/*---------CREATION insdex-----------*/

CREATE INDEX PROJETER_FK ON SEANCE ( TITRE ASC);
CREATE INDEX PROJETER_FK ON SEANCE ( TITRE deSC);


/*---------MODIFICATION SUR LES TABLE-----------*/

ALTER TABLE AIME MODIFY( TITRE VARCHAR(40));


/*---------AJOUTER ET SUPPRIMER UNE CONTRAINTE  SUR LES TABLE-----------*/

 ALTER TABLE SEANCE ADD CONSTRAINT VERIFY_HEURE_DEBUT CHECK (
HEURE_DEBUT > 13 );
ALTER TABLE SEANCE ADD CONSTRAINT VERIFY_NOM_SALLE CHECK
(NOM_SALLE IN( 'IBN ZAIDOUN' , 'IBN KHALDOUN' ));
ALTER TABLE SEANCE DROP CONSTRAINT VERIFY_NOM_SALLE ;


/*---------AJOUTER ET SUPPREIMER UN ATTRIBUT SUR LES TABLE-----------*/

ALTER TABLE AIME ADD( PRENOM_AMATEUR VARCHAR(30));
 ALTER TABLE AIME DROP COLUMN PRENOM_AMATEUR ;

 /*---------INSERTION SUR LES TABLE-----------*/
INSERT INTO SEANCE VALUES ('Perfect World','Beta','19:00','VO');
COMMIT;

 /*---------MISE A JOUR ET SUPPRESSION DES VALEUR -----------*/


UPDATE film SET duree = duree+60 where titre = 'Waterworld';
UPDATE SEANCE set VERSION ='VO' ;
Delete from SEANCE where HEURE_DEBUT='17:00';