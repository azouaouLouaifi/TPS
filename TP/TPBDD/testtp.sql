
/*NOM:LOUAIFI

PRENOM:AZOUAOU

MATRICULE:202031044812*/

alter session set "_ORACLE_SCRIPT"=true;
CREATE USER exam IDENTIFIED BY exam;
grant all privileges to exam;
connect exam/exam;
create table photo  (
   numphoto           VARCHAR2(40)                     not null,
   titre              VARCHAR2(40),
   dateprise            date,
    pays                VARCHAR2(40),
    nbconsult           INTEGER,
    numauteur           VARCHAR2(40),
     prix                 VARCHAR2(40)                    not null,
constraint PK_photo primary key (numphoto),
 constraint fk_hoto_auteur foreign key (numauteur) references auteur (numauteur) on Delete cascade,
  CONSTRAINT VERIFY_nbconsult CHECK
(nbconsult >= 0)
 );


create table auteur  (
   numauteur           VARCHAR2(40)                     not null,
   nom_aut              VARCHAR2(40)                not null,
   prenom_aut             VARCHAR2(40)               not null,
    email                    VARCHAR2(40)             UNIQUE,            
    statut                  VARCHAR2(40)                not null,
 constraint PK_auteur primary key (numauteur),
 CONSTRAINT VERIFY_STATUT CHECK
(statut IN( 'contractuel' , 'salarie', 'independant' ))
);

create table achete  (
   numcli           VARCHAR2(40)                     not null,
   numphoto              VARCHAR2(40),
   dateachat            date,
   constraint PK_unite primary key (numcli, numphoto),
 constraint fk_achete_client foreign key (numcli) references client (numcli) on Delete cascade,
  constraint fk_achete_photo foreign key (numphoto) references photo (numphoto) on Delete cascade
);


create table client  (
   numcli           VARCHAR2(40)                     not null,
   nom_cli              VARCHAR2(40),
   prenom_cli           VARCHAR2(40),
   constraint PK_client primary key (numcli)
);



desc auteur;
INSERT INTO client VALUES ('CL0001', 'TOUMANI', 'LEILA');
INSERT INTO client VALUES ('CL0002', 'loaufi', 'azwaw');
INSERT INTO client VALUES ('CL0003', 'toto', 'madjid');

INSERT INTO auteur VALUES ('A2100', 'BOURAS', 'OMAR', 'BOURAS_Omar@gmail.com', 'salarie');
INSERT INTO auteur VALUES ('A2101', 'BOULAM', 'BOULAM', 'BOULAM_Boulam@gmail.com', 'salarie');
INSERT INTO auteur VALUES ('A2102', 'KOREH', 'MOHAMED', 'KOREH_Mohamed@gmail.com', 'contractuel');

INSERT INTO photo VALUES ('PH1001', 'arbre', '12-08-1988', 'Maroc', 23, 'A2100','12.5');
INSERT INTO photo VALUES ('PH1002', 'plante', '12-07-1987', 'Algerie', 27, 'A2101','48');
INSERT INTO photo VALUES ('PH1003', 'ciel', '19-06-1989', 'France', 55, 'A2102','15');

INSERT INTO photo VALUES ('PH1004', 'art', '19-06-1955', 'France', 50, 'A2102','15');
INSERT INTO photo VALUES ('PH1005', 'sourie', '19-06-1989', 'Algerie', 55, 'A2101','19');

INSERT INTO achete VALUES ('CL0001', 'PH1004', '19-06-1955');
INSERT INTO achete VALUES ('CL0001', 'PH1003', '19-06-1955');
INSERT INTO achete VALUES ('CL0001', 'PH1005', '19-06-1955');

INSERT INTO achete VALUES ('CL0002', 'PH1004', '19-06-1960');
INSERT INTO achete VALUES ('CL0002', 'PH1002', '19-06-1960');
INSERT INTO achete VALUES ('CL0002', 'PH1005', '19-06-1960');

 INSERT INTO achete VALUES ('CL0003', 'PH1002', '19-06-1968');
INSERT INTO achete VALUES ('CL0003', 'PH1001', '19-06-1968');
 INSERT INTO achete VALUES ('CL0003', 'PH1004', '19-06-1968');

select nom_cli,prenom_cli,numcli from client where numcli not in(select numcli from achete );
select nom_cli from client where numcli in(select numcli from achete where numphoto in (select numphoto from photo where pays='Japon') );
select nom_cli,prenom_cli,numcli from client where numcli not in(select numcli from achete );

ALTER TABLE achete ADD( points INTEGER);
 ALTER TABLE achete ADD CONSTRAINT VERIFYacheter CHECK
(points IN( 1, 3, 5 ));











