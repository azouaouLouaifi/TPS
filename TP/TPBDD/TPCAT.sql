alter session set "_ORACLE_SCRIPT"=true;
create user USER1_2 identified by USER1;
create user USER2_2 identified by USER2;
grant all privileges to USER1_2;
 grant create session to USER2_2;
 grant create table to USER2_2;
 create table employe(
    NUmE INTEGER, 
    NomE varchar(30),
    fontion varchar(100),
    codeS varchar(3) ,
    constraint PK_EMPLOYE primary key (NUmE)
);
create table operation(
    codeop varchar(5)  constraint PK_operation primary key ,
    duree number(2),
    chef INTEGER ,
    datedeb date,
    budget number(8,2),
    codeS varchar(3)
);
conn  USER1_2/USER1;
insert into operation values ('OP001', '48', '13', '01/10/2018', 180000.00, 'S01');
insert into operation values ('OP002', '48', '5', '10/09/2016', 100000.00, 'S04');
insert into operation values ('OP003', '36', '14', '05/01/2018', 200000.00, 'S02');
insert into operation values ('OP004', '30', '13', '10/09/2017', 270000.00, 'S01');
insert into operation values ('OP005', '30', '8', '01/03/2018', 100000.00, 'S05');
insert into operation values ('OP006', '36', '5', '10/09/2019', 300000.00, 'S04');
insert into operation values ('OP007', '36', '13', '10/05/2018', 200000.00, 'S01');
insert into operation values ('OP008', '36', '4', '10/01/2020', 100000.00, 'S05');
insert into operation values ('OP009', '30', '13', '10/09/2020', 150000.00, 'S01');
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

grant select,references (NUmE) on employe to USER2_2;
grant select,references (codeop) on operation to USER2_2;

conn USER2_2/USER2;
create table membre(
    numE INTEGER,
    codeop VARchar(5),
    constraint FK_membre FOREIGN KEY (numE) REFERENCES USER1_2.employe(NUmE),
    constraint PK_membre primary key (codeop, numE),
    constraint FK_membre2 FOREIGN KEY (codeop) REFERENCES USER1_2.operation(codeop)
);
conn USER1_2/USER1;
desc dict;
select table_name from dict where table_name like 'USER_TAB%';
select comments from dict where table_name='ALL_TAB_COLUMNS';
select comments from dict where table_name='ALL_CONSTRAINTS';
select comments from dict where table_name='USER_TAB_PRIVS';
select comments from dict where table_name='USER_VIEWS';
select comments from dict where table_name='USER_USERS';
desc user_users;
select USERNAME,ACCOUNT_status,DEFAULT_TABLESPACE from user_users;
desc user_users;
desc user_tables;

select  table_name from user_tables;
SELECT COUNT(table_name) from all_tables;
conn USER2_2/USER2;
select  table_name from user_tables;
desc user_tab_columns;
select COLUMN_NAME,DATA_TYPE,DATA_LENGTH from user_tab_columns where table_name='OPERATION';
conn USER2_2/USER2;
select COLUMN_NAME,DATA_TYPE,DATA_LENGTH from user_tab_columns where table_name='MEMBRE';
desc user_constraints;
select owner,constraint_name,constraint_type from user_constraints;


conn USER1_2/USER1;
select table_name from user_tables;
select constraint_name from user_constraints;
select view_name from USER_VIEWS;

create table Service(
    codeS VARCHAR(3) ,
    Noms VARCHAR(20),
    chef_S INTEGER,
    constraint PK_SERVICE primary key (codeS),
    constraint FK_SERVICE FOREIGN KEY (chef_S) REFERENCES employe(NUmE) DISABLE
);

select table_name from user_tables;
select constraint_name from user_constraints;
select view_name from USER_VIEWS;

create or replace view chef_sevice(codeS ,Noms,chef_S,nomChef) as
 select s.codeS,s.Noms,s.chef_s,e.NomE from service s, employe e 
 where s.chef_S=e.numE;
