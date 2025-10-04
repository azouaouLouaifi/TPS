alter session set "_ORACLE_SCRIPT"=true;
create user TP6 identified by TP6;
grant all privileges to TP6;
create or replace function Total_budjet (x in operation.codeS%TYPE) 
return operation.budget%TYPE
is 
y operation.budget%TYPE;
begin
select sum(budget) into y from operation where codeS=x;
return y;
end;
/
select Total_budjet('S01') from dual;
create or replace function Nombre_empolyes (x in service.codeS%TYPE) 
return INTEGER
is 
y INTEGER;
begin
select count(numE) into y from employe group by x;
return y;
end;
/

select Nombre_empolyes('S01') from dual;



declare 
x operation.budget%TYPE;
y INTEGER;
z service.Noms%TYPE;
begin
x :=Total_budjet('S01');
y :=Nombre_empolyes('S01');
select Noms into z from service where codeS='S01';
DBMS_OUTPUT.PUT_LINE('le nom du service S01'||z||'le totale des budjet:'||x||'nombre empolye'||y);
end;
/



create or replace procedure info_service(x in service.codeS%TYPE) 
is
k operation.budget%TYPE;
y INTEGER;
z service.Noms%TYPE;
begin
k :=Total_budjet(x);
y :=Nombre_empolyes(x);
select Noms into z from service where codeS=x;
DBMS_OUTPUT.PUT_LINE('le nom du service:'||x||'est:'||z||'le totale des budjet:'||x||'nombre empolye'||y);
end;
/
execute info_service('S01');
execute info_service('S04');
execute info_service('S10');

create or replace procedure info_service(x in service.codeS%TYPE) 
is
k operation.budget%TYPE;
y INTEGER;
z service.Noms%TYPE;
begin
k :=Total_budjet(x);
y :=Nombre_empolyes(x);
select Noms into z from service where codeS=x;
DBMS_OUTPUT.PUT_LINE('le nom du service'||z||'le totale des budjet:'||x||'nombre empolye'||y);
Exception 
when no_data_found then DBMS_OUTPUT.PUT_LINE('Ce service n''existe pas');
end;
/
create or replace procedure info_OP (x in operation.chef%TYPE) is
code operation.codeOP%type;
budget operation.budget%type;
duree operation.duree%type;
begin
select codeOP,budget,duree into code,budget,duree from operation where chef=x;
DBMS_OUTPUT.PUT_LINE('le code operation du chef : '||x||'est:'||code||'  le budget'||budget||'  la duree:'||duree);
end;
/
execute info_OP(14);
execute info_OP(8);
execute info_OP(4);
execute info_OP(13);

create or replace procedure info_OP (x in operation.chef%TYPE) is
code operation.codeOP%type;
budget operation.budget%type;
duree operation.duree%type;
begin
select codeOP,budget,duree into code,budget,duree from operation where chef=x;
DBMS_OUTPUT.PUT_LINE('le code operation du chef : '||x||'est:'||code||'  le budget'||budget||'  la duree:'||duree);
Exception
when too_many_rows then DBMS_OUTPUT.PUT_LINE('Chef de plus d''une operation');
end;
/



create or replace procedure ListedesService is
cursor aff is select codeS,Noms,chef_S from service;
par aff%rowtype;
x INTEGER:=0;
n employe.nome%type;
begin
for par in aff loop
select count(codeOP) into x from operation where codes=par.codeS;
select NomE into n from employe where par.chef_S= numE;

DBMS_OUTPUT.PUT_LINE('le code service  : '||par.codeS||'nom:'||par.Noms||' chef service:'||n||' nombre operation:'
||x);
end loop;
end;
/
execute ListedesService;


create or replace function EmpOP (x in employe.numE%TYPE) 
return INTEGER
is 
y INTEGER;
begin
select count(codeOP) into y from membre where nume=x;
return y;
end;
/

 select EmpOP(1) from dual;

create or replace function chefOP (x in employe.numE%TYPE) 
return INTEGER
is 
y INTEGER;
begin
select count(codeOP) into y from operation where chef=x;
return y;
end;
/

 select chefOP(1) from dual;


create or replace procedure Info_Emp is
cursor cr is select numE,nome,fontion from employe;
par cr%rowtype;
x INTEGER:=0;
y INTEGER:=0;
begin
for par in cr loop
x:=chefOP(par.numE);
y:=EmpOP(par.numE);
DBMS_OUTPUT.PUT_LINE('l''employe'||par.numE ||'nom'||par.nome||'fonction:'||par.fontion||' nombre d''operation:'||y||' nombre de fois chef:'
||x);
end loop;
end;
/

execute Info_Emp;

create or replace trigger budget_control
before update of budget on operation
for each row
begin
declare
RAISE_APPLICATION_ERROR Exception ;
begin
if (:new.budget<:old.budget) then  raise RAISE_APPLICATION_ERROR ;

end if;
Exception 
when RAISE_APPLICATION_ERROR then DBMS_OUTPUT.PUT_LINE('IMPOSSIBLE DE REDUIRE LE BUDGET');
:new.budget:=:old.budget;

end;
end;
/
update operation set budget=10 WHERE codeOP='OP002';













