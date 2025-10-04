alter session set "_ORACLE_SCRIPT"=true;
create user TP3 identified by TP3;
grant all privileges to TP3;
create table Service(
    codeS VARCHAR(3)  constraint PK_SERVICE primary key,
    Noms VARCHAR(20),
    chef_S INTEGER
    );
insert into Service values ('S01', 'informatique', '13');
insert into Service values ('S02', 'panification', '14');
insert into Service values ('S03', 'comptabilité', '3');
insert into Service values  ('S04', 'construction', '5');
insert into Service values ('S05', 'technique', '8');
insert into Service values ('S06', 'statistique', '11');

alter table service add NbOP INTEGER DEFAULT 0; 
create table operation(
    codeop varchar(5)  constraint PK_operation primary key ,
    duree number(2),
    chef INTEGER ,
    datedeb date,
    budget number(8,2),
    codeS varchar(3)
);
 create or replace trigger MAJ_NbOp
 after
  insert 
on operation
 for each row 
 begin 
 UPDATE service set NbOP =NbOP+1
 where 
 codeS=:New.codeS;
 end;
 /
 select * from service;


insert into operation values ('OP001', '48', '13', '01/10/2018', 180000.00, 'S01');
insert into operation values ('OP002', '48', '5', '10/09/2016', 100000.00, 'S04');
insert into operation values ('OP003', '36', '14', '05/01/2018', 200000.00, 'S02');
insert into operation values ('OP004', '30', '13', '10/09/2017', 270000.00, 'S01');
insert into operation values ('OP005', '30', '8', '01/03/2018', 100000.00, 'S05');
insert into operation values ('OP006', '36', '5', '10/09/2019', 300000.00, 'S04');
insert into operation values ('OP007', '36', '13', '10/05/2018', 200000.00, 'S01');
insert into operation values ('OP008', '36', '4', '10/01/2020', 100000.00, 'S05');
insert into operation values ('OP009', '30', '13', '10/09/2020', 150000.00, 'S01');

alter table operation  add NbE INTEGER DEFAULT 0; 
create table membre(
    numE INTEGER,
    codeop varchar(5),
    constraint PK_membre primary key (codeop, numE)
);

create or replace trigger MAJ_NbE
 after
  insert or delete 
on membre
 for each row 
 begin
 if inserting then  
 UPDATE operation  set NbE =NbE+1
 where 
 codeOP=:New.codeOP;
 end if;
 if deleting then update operation set NbE =NbE-1
 where 
 codeOP=:old.codeOP;
 end if;
 end;
 /
 insert into membre values ('13', 'OP001');
insert into membre values ('7', 'OP001');
insert into membre values ('1', 'OP001');
insert into membre values ('5', 'OP002');
insert into membre values ('2', 'OP002');
select * from operation where codeOP='OP001' and  codeOP='OP002';
delete from membre where nume=5 and codeOP='OP002';
select * from operation where codeOP='OP002'; 
create table employe(
    NUmE INTEGER, 
    NomE varchar(30),
    fontion varchar(100),
    codeS varchar(3) ,
    constraint PK_EMPLOYE primary key (NUmE)
);
alter table employe add nbchf INTEGER DEFAULT 0;

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
 



update employe e set e.nbchf =(select count(chef) from operation o where o.chef= e.numE group by chef);
select * from employe;

create or replace trigger MAJ_Nbchf
 after
  insert or delete or update
on operation
 for each row 
 begin
 if inserting then  
 UPDATE employe  set nbchf =nbchf+1
 where 
 numE=:New.chef;
 end if;
 if deleting then update employe set nbchf =nbchf-1
 where 
 numE=:old.chef;
 end if;
 if updating then 
 UPDATE employe  set nbchf =nbchf+1
 where 
 numE=:New.chef;
 update employe set nbchf =nbchf-1
 where 
 numE=:old.chef;
 end if;
 end;
 /

 update operation set chef=12 where codeOP='OP003';
  update operation set chef=10 where codeOP='OP005';
  insert into operation (codeOP,duree,chef,datedeb,codeS)values('OP010', 36,3,'20/10/2022','S01');

