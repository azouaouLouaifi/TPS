alter session set "_ORACLE_SCRIPT"=true;
create user BDDAdmin IDENTIFIED by TPAdmin;
grant all privileges to BDDAdmin;
create table etudiant  (
   nom_etu                VARCHAR2(40),
   matricule_etu           INTEGER            not null,
   prenim_etu               VARCHAR2(40),
   date_naissance            date,
   constraint PK_etduient primary key (matricule_etu)
);

create table unite  (
   code_Unite           INTEGER                     not null,
   libelle              VARCHAR2(40),
   nbr_heure            INTEGER,
    matricule_ens        INTEGER                    not null,
   constraint PK_unite primary key (code_Unite),
 constraint fk_unite_enseignant foreign key (matricule_ens) references enseignant (matricule_ens)
);

create table enseignant  (
   matricule_ens          INTEGER                     not null,
   nom_ens             VARCHAR2(40),
  prenim_ens               VARCHAR2(40),
    age                    INTEGER,
   constraint PK_enseignant primary key (matricule_ens)
);
create table etudiantunite  (
   matricule_etu          INTEGER                     not null,
   code_Unite           INTEGER                     not null,
  note_CC                 INTEGER,
  note_tp                 INTEGER,
  note_examen                 INTEGER,
  constraint PK_etudiantunite primary key (matricule_etu, code_Unite),
  constraint fk_etudiantunite_etu foreign key (matricule_etu) references etudiant (matricule_etu),
  constraint fk_etudiantunite_unite foreign key (code_Unite) references unite (code_Unite)
);

create  index index_etu on etudiant (
   nom_etu ASC
);
create  index index_ens on enseignant (
   nom_ens deSC
);
alter session set "_ORACLE_SCRIPT"=true;
create user Etudiant IDENTIFIED by TPEtudiant;
grant select on etudiant to etudiant;
alter session set "_ORACLE_SCRIPT"=true;
create user enseignant IDENTIFIED by TPEnseigant;
grant select,insert on enseignant to enseignant;
alter table etudiant add(adresse VARCHAR2(100));
alter table enseignant drop COLUMN age;
alter table etudiant add constraint mat_entre CHECK (matricule_etu>20190000 and matricule_etu<20199999);
alter table etudiant MODIFY prenim_etu VARCHAR2(45);
alter table etudiant drop constraint mat_entre;


iNSERT into etudiant VALUES( 'BOUSSAI', 20190001 , 'MOHAMED', '12-01-2000', 'Alger');
iNSERT into etudiant VALUES( 'CHAID', 20190002 , 'LAMIA', '01-10-1999', 'Batna');
iNSERT into etudiant VALUES( 'BRAHIM', 20190003 , 'SOUAD', '18-11-2000', 'Setif');
iNSERT into etudiant VALUES( 'LAMA', 20190004 , 'SAID', '23-05-1999', 'Oran');

iNSERT into enseignant VALUES( 20000001, 'HAROUNI' , 'AMINE');
iNSERT into enseignant VALUES( 19990011, 'FATHI' , 'OMAR');
iNSERT into enseignant VALUES( 19980078, 'BOUZIDANE' , 'FARAH');
iNSERT into enseignant VALUES( 20170015, 'ARABI' , 'ZOUBIDA');




iNSERT into unite VALUES( 0001,  'POO' , 6, 20000001);
iNSERT into unite VALUES( 0002,  'BDD' , 6, 19990011);
iNSERT into unite VALUES( 0003,  'RESEAU' , 3, 20170015);
iNSERT into unite VALUES( 0004,  'SYSTEME' , 6, 19980078);

iNSERT into etudiantunite VALUES( 20190001, 0001 , 10, 15, 9);
iNSERT into etudiantunite VALUES( 20190002, 0001 , 20, 13, 10);
iNSERT into etudiantunite VALUES( 20190004, 0001 , 13, 17, 16);
iNSERT into etudiantunite VALUES( 20190002, 0002 , 10, 16, 17);
iNSERT into etudiantunite VALUES( 20190003, 0002 , 9, 8, 15);
iNSERT into etudiantunite VALUES( 20190004, 0002 , 15, 9, 20);
iNSERT into etudiantunite VALUES( 20190002, 0004 , 12, 18, 14);
iNSERT into etudiantunite VALUES( 20190003, 0004 , 17, 12, 15);
iNSERT into etudiantunite VALUES( 20190004, 0004 , 12, 13, 20);


UPDATE etudiantunite SET note_CC = note_CC+2 where matricule_Etu in ( select matricule_Etu from etudiant where nom_etu like 'b%' );

UPDATE etudiantunite set note_examen=0 where code_Unite in (select code_Unite from unite where libelle='SYSTEME');

select nom_etu,prenim_etu from etudiant where matricule_Etu in (select matricule_Etu from etudiantunite where note_examen=20);
select nom_etu,prenim_etu from etudiant where matricule_Etu not in (select matricule_Etu from etudiantunite where code_Unite in (select code_Unite from unite where libelle='POO'));
select libelle from unite where code_Unite not in (select code_Unite from etudiantunite );
select nom_etu from etudiant where matricule_Etu not in (select matricule_Etu from etudiantunite where code_Unite in (select code_Unite from unite where libelle='POO'));