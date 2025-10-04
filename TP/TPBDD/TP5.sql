alter session set "_ORACLE_SCRIPT"=true;
create user USER1 identified by USER1;
create user USER2 identified by USER2;
grant all privileges to USER1_1;
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
 grant create session to USER2;
 grant create table to USER2;
conn USER2/USER2;
conn USER1/USER1;

grant references  on employe to USER2;
grant references  on operation to USER2;


select* from USER1.employe;
grant select on employe to USER2;
grant update (fontion) on employe to USER2;
select* from USER1.employe;
update user1.employe set fontion='technicien superieur' where numE=2;
revoke update  on employe from USER2;
update user1.employe set fontion='technicien superieur' where numE=3;
grant select on chef_sevice to USER2;
select  nomChef from user1.chef_sevice where Noms='technique';
alter session set "_ORACLE_SCRIPT"=true;
create role agent;
grant select on service to agent;
grant update on operation to agent;
grant agent to USER2;
select * from user1.service;
update USER1.operation set datedeb='05-10-2021' where codeOP='OP003';
grant select on operation to agent;
 select * from user1.operation;
 select nome from user1.employe where numE in(select numE from membre);







