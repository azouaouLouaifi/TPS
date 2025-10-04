alter session set "_ORACLE_SCRIPT"=true;
create user TP4 identified by TP4;
grant all privileges to TP4;
create table employe(
    NUmE INTEGER, 
    NomE varchar(30),
    fontion varchar(100),
    codeS varchar(3) ,
    constraint PK_EMPLOYE primary key (NUmE)
);
create table Service(
    codeS VARCHAR(3) ,
    Noms VARCHAR(20),
    chef_S INTEGER,
    constraint PK_SERVICE primary key (codeS)
);

create table operation(
    codeop varchar(5)  constraint PK_operation primary key ,
    duree number(2),
    chef INTEGER ,
    datedeb date,
    budget number(8,2),
    codeS varchar(3)
);

create table membre(
    numE INTEGER,
    codeop varchar(5),
    constraint PK_membre primary key (codeop, numE)
);


insert into operation values ('OP001', '48', '13', '01/10/2018', 180000.00, 'S01');
insert into operation values ('OP002', '48', '5', '10/09/2016', 100000.00, 'S04');
insert into operation values ('OP003', '36', '14', '05/01/2018', 200000.00, 'S02');
insert into operation values ('OP004', '30', '13', '10/09/2017', 270000.00, 'S01');
insert into operation values ('OP005', '30', '8', '01/03/2018', 100000.00, 'S05');
insert into operation values ('OP006', '36', '5', '10/09/2019', 300000.00, 'S04');
insert into operation values ('OP007', '36', '13', '10/05/2018', 200000.00, 'S01');
insert into operation values ('OP008', '36', '4', '10/01/2020', 100000.00, 'S05');
insert into operation values ('OP009', '30', '13', '10/09/2020', 150000.00, 'S01');


insert into Service values ('S01', 'informatique', '13');
insert into Service values ('S02', 'panification', '14');
insert into Service values ('S03', 'comptabilité', '3');
insert into Service values  ('S04', 'construction', '5');
insert into Service values ('S05', 'technique', '8');
insert into Service values ('S06', 'statistique', '11');

insert into employe values (1, 'belkadi', 'ingénieur d"état en informatique','S01');
insert into employe values (2, 'benslimane', 'technicien','S04');
insert into employe values (3, 'zerguine', 'chef comtable','S03');
insert into employe values (4, 'naili', 'ingénieur p.c','S05');
insert into employe values (5, 'touahri', 'architecte ','S04');
insert into employe values (6, 'mehdjoubi', 'comptable ','S03');
insert into employe values (7, 'messaoude', 'ingénieur d"application en informatique','S01');
insert into employe values (8, 'ghrissi', 'ingénieur  g.c','S05');
insert into employe values (9, 'otmani', 'comptable','S06');
insert into employe values (10, 'benyahia', 'technicien ','S05');
insert into employe values (11, 'kadi', 'master en satatistique','S06');

insert into employe values (12, 'sissaoui', 'agent administratif','S02');
insert into employe values (13, 'benaissa', 'ingénieur d"état en informatique','S01');
insert into employe values (14, 'achour', 'administrateur principale','S02');
insert into Membre values (13, 'OP001');
insert into Membre values (7, 'OP001');
insert into Membre values (1, 'OP001');
insert into Membre values (5, 'OP002');
insert into Membre values (2, 'OP002');
create view ListeService(codeS,Noms) as select codeS,Noms from service;
select * from ListeService;
create view EmployeOp(numE, nbop) as select numE ,count(codeOP) from  membre group by nume;
select * from EmployeOp;

create or replace view chef_sevice(codeS ,Noms,chef_S,nomChef) as
 select s.codeS,s.Noms,s.chef_s,e.NomE from service s, employe e 
 where s.chef_S=e.numE;

select * from chef_sevice;

create view employe2(numE) as select numE from employe minus  (select chef_S from chef_sevice);
select * from employe2;
insert into service (codeS, Noms) values ('S20','production');
select * from ListeService;
insert into membre values(13,'OP007');
insert into membre values(7,'OP007');
insert into membre values(13,'OP009');

select  nomChef from chef_sevice where Noms='technique';

select nome from employe where numE in (select nume from EmployeOp where nbop= (select max(nbop) from EmployeOp));
select e.nome,e.numE,nbop from employe e,employe2 e2,EmployeOp e1 where e.numE=e1.numE and e.numE=e2.numE ;

insert into ListeService values('S21','formation');
update ListeService set Noms='maintenance' where codeS='S20';
delete from ListeService where codeS='S20';
select * from ListeService;

insert into EmployeOp values(3,2);
update EmployeOp set numE=4 where numE=7;
delete from EmployeOp where numE=5;
/* erreur car la vue contient un count*/
insert into chef_sevice values('S21','formation',4,'naili');
/* on ne peut pas inserer dans une vu qui contient des jointure*/

update chef_sevice set Noms='maintenance' where codeS='S01';
delete from chef_sevice where codeS='S01';
select * from chef_sevice;

insert into employe2 values(15);
update employe2 set numE=12 where numE=6;
delete from employe2 where numE=9;
/* on ne peut pas faire de manipulation sur cette table virtuel employe2 car elle contient un minus*/
create or replace view ListeService(codeS,Noms) as select codeS,Noms from service with read only;
insert into ListeService values('S23','equipement');

create view OperationPlus48mois (codeOP,duree,datedeb) as select codeOP,duree,datedeb from operation where duree> 48;
select * from OperationPlus48mois;
insert into OperationPlus48mois values(11,24,'15-10-2021');

create or replace view OperationPlus48mois (codeOP,duree,datedeb) as select codeOP,duree,datedeb from operation where duree> 48 with CHECK option;
select * from OperationPlus48mois;
insert into OperationPlus48mois values(12,40,'15-03-2022');

create or replace view Services_Equip as 
select x.codeS, y.nomEquipment
from Service x , Equipment y
where x.codeS = y.codeS;
/* ON ne peut pas la cree*/

create or replace force view Services_Equip as 
select x.codeS, y.nomEquipment
from Service x , Equipment y
where x.codeS = y.codeS;
/* cree avec erreur de compilation*/




















