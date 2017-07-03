/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     6/29/2017 1:56:01 PM                         */
/*==============================================================*/

drop schema if exists distribuidas;

create schema `distribuidas`;

use distribuidas;

drop table if exists FACULTAD;

drop table if exists TIPO;

drop table if exists PERSONA;
/*==============================================================*/
/* Table: FACULTAD                                              */
/*==============================================================*/
create table FACULTAD
(
   IDF                  int not null auto_increment,
   NOMBREFACULTAD       varchar(20) not null,
   primary key (IDF)
);

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA
(
   IDP                  int not null auto_increment,
   IDF                  int not null,
   IDT                  int not null,
   NOMBREUSUARIO        varchar(20) not null,
   CONTRASENA           varchar(20) not null,
   primary key (IDP)
);

/*==============================================================*/
/* Table: TIPO                                                  */
/*==============================================================*/
create table TIPO
(
   IDT                  int not null auto_increment,
   DESCRIPCION          varchar(20) not null,
   primary key (IDT)
);

alter table PERSONA add constraint FK_FACULTADPERSONA foreign key (IDF)
      references FACULTAD (IDF) on delete restrict on update restrict;

alter table PERSONA add constraint FK_PERSONATIPO foreign key (IDT)
      references TIPO (IDT) on delete restrict on update restrict;
      
/*==============================================================*/
/* data                                                         */
/*==============================================================*/

insert into facultad(nombrefacultad) values ('Sistemas');
insert into facultad(nombrefacultad) values ('Mecanica');
insert into facultad(nombrefacultad) values ('Fisica');
insert into facultad(nombrefacultad) values ('Electronica');

insert into tipo(descripcion) values ('Estudiante');
insert into tipo(descripcion) values ('Profesor');
insert into tipo(descripcion) values ('Administrativo');

insert into persona(idf, idt, nombreusuario, contrasena) values ('1', '1', 'Daniel', 'segura');
insert into persona(idf, idt, nombreusuario, contrasena) values ('1', '2', 'Victoria', 'unica');
insert into persona(idf, idt, nombreusuario, contrasena) values ('1', '3', 'Raquel', 'fuerte');
insert into persona(idf, idt, nombreusuario, contrasena) values ('2', '1', 'Julia', 'debil');
insert into persona(idf, idt, nombreusuario, contrasena) values ('2', '2', 'Fernanda', 'nueva');
insert into persona(idf, idt, nombreusuario, contrasena) values ('2', '3', 'Erika', 'vieja');
insert into persona(idf, idt, nombreusuario, contrasena) values ('3', '1', 'Lisseth', 'olvidada');
insert into persona(idf, idt, nombreusuario, contrasena) values ('3', '2', 'Michelle', 'pensada');
insert into persona(idf, idt, nombreusuario, contrasena) values ('3', '3', 'Katherine', 'malhecha');
insert into persona(idf, idt, nombreusuario, contrasena) values ('4', '1', 'Emma', '123456');
insert into persona(idf, idt, nombreusuario, contrasena) values ('4', '2', 'Britney', 'contrasena');
insert into persona(idf, idt, nombreusuario, contrasena) values ('4', '3', 'Zoey', 'password');