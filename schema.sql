create database talent default charset 'UTF8';
create user james identified with mysql_native_password by 'bond';
grant all on talent.* to james;
