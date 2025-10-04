alter session set "_ORACLE_SCRIPT"=true;
create user TP2 identified by TP2;
grant all privileges to TP2;
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
    constraint PK_SERVICE primary key (codeS),
    constraint FK_SERVICE FOREIGN KEY (chef_S) REFERENCES employe(NUmE) DISABLE
);

ALTER TABLE employe ADD constraint FK_EMPLOYE FOREIGN KEY (codeS) REFERENCES SERVICE(codeS) DISABLE;
insert into employe values ('1', 'belkadi', 'ingénieur d"état en informatique','S01');
insert into employe values ('2', 'benslimane', 'technicien','S04');
insert into Service values ('S01', 'informatique', '13');
insert into Service values ('S02', 'panification', '14');



ALTER TABLE Service ENABLE CONSTRAINT FK_SERVICE;
ALTER TABLE employe ENABLE CONSTRAINT FK_EMPLOYE;

create table operation(
    codeop varchar(5),
    duree number(2),
    chef INTEGER,
    datedeb date,
    budget number(8,2),
    codeS varchar(3),
    constraint PK_operation primary key (codeop),
    constraint FK_operation1 FOREIGN KEY (chef) REFERENCES employe(NUmE),
    constraint FK_operation2 FOREIGN KEY (codeS) REFERENCES Service(codeS)
);
create table membre(
    numE INTEGER,
    codeop char(5),
    constraint FK_membre FOREIGN KEY (numE) REFERENCES employe(NUmE),
    constraint PK_membre primary key (codeop, numE),
    constraint FK_membre2 FOREIGN KEY (codeop) REFERENCES operation(codeop)
);


ALTER TABLE operation ADD CONSTRAINT NOT_NULL CHECK( datedeb IS NOT NULL);
ALTER TABLE SERVICE ADD CONSTRAINT UNIQUE_EMP UNIQUE (chef_S);
ALTER TABLE operation ADD CONSTRAINT DAT CHECK(datedeb>24);
ALTER TABLE operation DROP CONSTRAINT FK_operation2;
ALTER TABLE employe DROP CONSTRAINT FK_EMPLOYE;
ALTER TABLE operation ADD CONSTRAINT FK_operation2 FOREIGN KEY (codeS) REFERENCES SERVICE(codeS) ON DELETE cascade ;
ALTER TABLE employe ADD constraint FK_EMPLOYE FOREIGN KEY (codeS) REFERENCES SERVICE(codeS) SET NULL;
