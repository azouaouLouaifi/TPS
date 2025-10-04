*==============================================================*/
/* Database name:  TP  BD_CINEMA                                         */
/*==============================================================*/


drop index AIMER_FK
/


drop index JOUER_FK
/


drop index PRODUIR_FK
/


drop index PROJETER_FK
/


drop index VOIR_FK
/


drop table AIME cascade constraints
/


drop table VU cascade constraints
/


drop table SEANCE cascade constraints
/


drop table PRODUIT cascade constraints
/


drop table JOUE cascade constraints
/


drop table FILM cascade constraints
/


/*==============================================================*/
/* Table: FILM                                                  */
/*==============================================================*/


create table FILM  (
   TITRE                VARCHAR2(40)                     not null,
   DUREE                INTEGER,
   NATIONALITE          VARCHAR2(30),
   NOM_REALISATEUR   VARCHAR2(30),
   constraint PK_FILM primary key (TITRE)
)
/


/*==============================================================*/
/* Table: JOUE                                                  */
/*==============================================================*/


create table JOUE  (
   NOM_ACTEUR           VARCHAR2(30)                     not null,
   TITRE                VARCHAR2(40)                     not null,
   constraint PK_JOUE primary key (NOM_ACTEUR, TITRE),
   constraint FK_JOUE_JOUER_FILM foreign key (TITRE)
         references FILM (TITRE)
)
/


/*==============================================================*/
/* Index: JOUER_FK                                              */
/*==============================================================*/
create  index JOUER_FK on JOUE (
   TITRE ASC
)
/
alter session set "_ORACLE_SCRIPT"=true;


/*==============================================================*/
/* Table: PRODUIT                                               */
/*==============================================================*/


create table PRODUIT  (
   NOM_PRODUCTEUR       VARCHAR2(30)                     not null,
   TITRE                VARCHAR2(40)                     not null,
   constraint PK_PRODUIT primary key (NOM_PRODUCTEUR, TITRE),
   constraint FK_PRODUIT_PRODUIR_FILM foreign key (TITRE)
         references FILM (TITRE)
)
/


/*==============================================================*/
/* Index: PRODUIR_FK                                            */
/*==============================================================*/
create  index PRODUIR_FK on PRODUIT (
   TITRE ASC
)
/


CREATE TABLE SEANCE (
TITRE VARCHAR2(40) NOT NULL,
NOM_SALLE VARCHAR2(30) NOT NULL,
HEURE_DEBUT INTEGER NOT NULL,
VERSION VARCHAR2(10),
CONSTRAINT PK_SEANCE PRIMARY KEY (TITRE, NOM_SALLE, HEURE_DEBUT),
CONSTRAINT FK_SEANCE_PROJETER_FILM FOREIGN KEY (TITRE)
REFERENCES FILM (TITRE)
);


CREATE TABLE VU (
NOM_SPECTATEUR VARCHAR2(30) NOT NULL,
TITRE VARCHAR2(40) NOT NULL,
CONSTRAINT PK_VU PRIMARY KEY (NOM_SPECTATEUR, TITRE),
CONSTRAINT FK_VU_VOIR_FILM FOREIGN KEY (TITRE)
REFERENCES FILM (TITRE)
);

CREATE TABLE AIME (
NOM_AMATEUR VARCHAR2(30) ,
TITRE VARCHAR2(40)
);


 CREATE INDEX PROJETER_FK ON SEANCE ( TITRE ASC);
 CREATE INDEX VOIR_FK ON VU ( TITRE DESC);
 CREATE INDEX AIMER_FK ON AIME ( TITRE ASC);

ALTER TABLE AIME MODIFY( TITRE VARCHAR(40));
 ALTER TABLE SEANCE ADD CONSTRAINT VERIFY_HEURE_DEBUT CHECK (
HEURE_DEBUT > 13 );


 ALTER TABLE SEANCE ADD CONSTRAINT VERIFY_NOM_SALLE CHECK
(NOM_SALLE IN( 'IBN ZAIDOUN' , 'IBN KHALDOUN' ));
 ALTER TABLE SEANCE MODIFY VERSION DEFAULT 'VO';
ALTER TABLE AIME ADD CONSTRAINT VERIFY_NOM_AMATEUR CHECK
(NOM_AMATEUR LIKE '%R');
ALTER TABLE SEANCE DROP CONSTRAINT VERIFY_HEURE_DEBUT ;
ALTER TABLE SEANCE DROP CONSTRAINT VERIFY_NOM_SALLE ;
ALTER TABLE SEANCE MODIFY VERSION DEFAULT NULL ;
ALTER TABLE AIME DROP CONSTRAINT VERIFY_NOM_AMATEUR ;
 ALTER TABLE SEANCE MODIFY HEURE_DEBUT VARCHAR2(6) ;
ALTER TABLE AIME ADD( PRENOM_AMATEUR VARCHAR(30));
 ALTER TABLE AIME DROP COLUMN PRENOM_AMATEUR ;
 ALTER TABLE SEANCE MODIFY HEURE_DEBUT NULL;
 ALTER TABLE SEANCE MODIFY HEURE_DEBUT NOT NULL;
ALTER TABLE AIME ADD CONSTRAINT PK_AIME PRIMARY KEY(
NOM_AMATEUR,TITRE);
ALTER TABLE AIME ADD CONSTRAINT FK_AIME FOREIGN KEY(TITRE)
REFERENCES FILM(TITRE);
ALTER TABLE VU DROP CONSTRAINT FK_VU_VOIR_FILM;
ALTER TABLE VU DROP CONSTRAINT PK_VU;
 ALTER TABLE VU ADD (CONSTRAINT PK_VU PRIMARY KEY
(TITRE,NOM_SPECTATEUR), CONSTRAINT FK_VU_VOIR_FILM FOREIGN KEY
(TITRE)REFERENCES FILM (TITRE));


/*-------------------TP3---------------*/

INSERT INTO SEANCE VALUES ('Perfect World','Beta','19:00','VO');
INSERT INTO PRODUIT VALUES ('Adam Moore','ET');
INSERT into joue VALUES('Kevin Costner','Waterworld');
iNSERT INTO PRODUIT VALUES ('Kevin Costner', 'Waterworld');
INSERT INTO SEANCE VALUES('Waterworld', 'Alpha', '15:00', 'VF');
INSERT INTO SEANCE VALUES('Waterworld', 'Alpha', '17:00', 'VO');
INSERT INTO SEANCE VALUES('Waterworld', 'Beta', '15:00', 'VF');
insert into vu VALUES('Kevin Costner', 'Waterworld');
INSERT INTO Film VALUES ('Waterworld', 250, 'US', 'Rickley Moore');
UPDATE film SET duree = duree+60 where titre = 'Waterworld';
UPDATE SEANCE set VERSION ='VO' ;
Delete from SEANCE where HEURE_DEBUT='17:00';
Delete from film where titre='Waterworld';


/*-------------------TP4---------------*/
CREATE USER TP4user IDENTIFIED BY mdp;
grant all privileges to TP4user;
select * from azwaw.film;
revoke all privileges from TP4user;
 
alter session set "_ORACLE_SCRIPT"=true;

CREATE USER TP4user2 IDENTIFIED BY TP42;
grant select on SEANCE to TP4user2;
grant insert on vu to TP4user2;
grant create session to TP4user2;
revoke all privileges from TP4user2;
alter session set "_ORACLE_SCRIPT"=true;
drop USER TP4user2;
grant select on vu to Public;
revoke select on vu from Public;


alter session set "_ORACLE_SCRIPT"=true;
create user TP4user3 IDENTIFIED by TP43;

grant select,insert,UPDATE,Delete on SEANCE to TP4user3;
grant select,insert,UPDATE,Delete on Film to TP4user3;
grant select,insert,UPDATE,Delete on PRODUIT to TP4user3;
grant select,insert,UPDATE,Delete on vu to TP4user3;
grant select,insert,UPDATE,Delete on AIME to TP4user3;
grant select,insert,UPDATE,Delete on joue to TP4user3;

revoke all privileges from TP4user3;

drop user TP4user3;


/*-------------------TP5---------------*/

select titre from film where titre LIKE'%World%';
select titre from film where DUREE <60;
select titre from film where DUREE >140;


select NOM_REALISATEUR from film where titre='Water world';
select NOM_SALLE from SEANCE s where s.VERSION ='VO'and  s.titre in (select titre
 from joue j where J.NOM_ACTEUR='Kevin Costner');
select titre from film where NOM_REALISATEUR in (select NOM_REALISATEUR from film where titre='Waterworld');
select titre from film where NOM_REALISATEUR='Spielberg' MINUS (select titre from vu);
select NOM_SALLE from SEANCE s where titre in(select titre from joue where NOM_ACTEUR='Tom Cruise' intersect (select titre from joue where  NOM_ACTEUR='Nicole Kidman' ));
select NOM_ACTEUR from film f,PRODUIT p,JOUE j where f.NOM_REALISATEUR=p.NOM_PRODUCTEUR  and f.NOM_REALISATEUR=j.NOM_ACTEUR;
select titre from joue where NOM_ACTEUR ='Tom Cruise' MINUS (select titre from joue where NOM_ACTEUR ='Nicole Kidman');
select NOM_PRODUCTEUR from PRODUIT where NOM_PRODUCTEUR not  in (select titre from film where NOM_REALISATEUR='Spielberg');

