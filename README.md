# StudentManagmentSystem_war_packaging
project allow Student login using JWT Token , view courses , register course and get course schedule as pdf  
in order to run the project you should run the following mySql script to create tables and fill the role table  


# create database student_managment_system

CREATE TABLE courses (
  id int NOT NULL AUTO_INCREMENT,
  course_description varchar(255) NOT NULL,
  course_schedule varchar(255) NOT NULL,
  PRIMARY KEY (id)
) 

CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(100) NOT NULL,
  family_name varchar(100) NOT NULL,
  email varchar(255) NOT NULL,
  age int NOT NULL,
  password varchar(200) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email),
  UNIQUE KEY first_name (first_name)
) 

CREATE TABLE roles (
  id int NOT NULL AUTO_INCREMENT,
  role_name varchar(20) NOT NULL,
  PRIMARY KEY (id)
) 

CREATE TABLE user_courses (
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  course_id int NOT NULL,
  PRIMARY KEY (id,user_id,course_id),
  KEY user_id (user_id),
  KEY course_id (course_id),
  CONSTRAINT user_courses_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT user_courses_ibfk_2 FOREIGN KEY (course_id) REFERENCES courses (id)
) 

CREATE TABLE user_roles (
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (id,user_id,role_id),
  KEY user_id (user_id),
  KEY role_id (role_id),
  CONSTRAINT user_roles_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT user_roles_ibfk_2 FOREIGN KEY (role_id) REFERENCES roles (id)
) ;

use student_managment_system;
insert into roles values(0,'ROLE_USER');
insert into roles values(0,'ROLE_MODERATOR');
insert into roles values(0,'ROLE_ADMIN');


# but if you want oracle script it is ready also  the following Oracle script to create tables and fill role table 



create user student_managment_system identified by student_managment_system

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE courses (
  id number(10) NOT NULL,
  course_description varchar2(255) NOT NULL,
  course_schedule varchar2(255) NOT NULL,
  PRIMARY KEY (id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE courses_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER courses_seq_tr
 BEFORE INSERT ON courses FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT courses_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/ 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE user (
  id number(10) NOT NULL,
  first_name varchar2(100) NOT NULL,
  family_name varchar2(100) NOT NULL,
  email varchar2(255) NOT NULL,
  age number(10) NOT NULL,
  password varchar2(200) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT email UNIQUE (email),
  CONSTRAINT first_name UNIQUE (first_name)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE user_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER user_seq_tr
 BEFORE INSERT ON user FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT user_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/ 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE roles (
  id number(10) NOT NULL,
  role_name varchar2(20) NOT NULL,
  PRIMARY KEY (id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE roles_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER roles_seq_tr
 BEFORE INSERT ON roles FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT roles_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/ 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE user_courses (
  id number(10) NOT NULL,
  user_id number(10) NOT NULL,
  course_id number(10) NOT NULL,
  PRIMARY KEY (id,user_id,course_id)
 ,
  CONSTRAINT user_courses_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT user_courses_ibfk_2 FOREIGN KEY (course_id) REFERENCES courses (id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE user_courses_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER user_courses_seq_tr
 BEFORE INSERT ON user_courses FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT user_courses_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/

CREATE INDEX user_id ON user_courses (user_id);
CREATE INDEX course_id ON user_courses (course_id); 

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE user_roles (
  id number(10) NOT NULL,
  user_id number(10) NOT NULL,
  role_id number(10) NOT NULL,
  PRIMARY KEY (id,user_id,role_id)
 ,
  CONSTRAINT user_roles_ibfk_1 FOREIGN KEY (user_id) REFERENCES user (id),
  CONSTRAINT user_roles_ibfk_2 FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- Generate ID using sequence and trigger
CREATE SEQUENCE user_roles_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER user_roles_seq_tr
 BEFORE INSERT ON user_roles FOR EACH ROW
 WHEN (NEW.id IS NULL)
BEGIN
 SELECT user_roles_seq.NEXTVAL INTO :NEW.id FROM DUAL;
END;
/

CREATE INDEX user_id ON user_roles (user_id);
CREATE INDEX role_id ON user_roles (role_id); 

alter session set current_schema = student_managment_system;
insert into roles values(0,'ROLE_USER');
insert into roles values(0,'ROLE_MODERATOR');
insert into roles values(0,'ROLE_ADMIN');

# Actullay in project i configure my Sql connection in application.properties Like That:


server.port=8093
#server.servlet.context-path=/StudentManagmentSystem
spring.datasource.url=jdbc:mysql://localhost:3306/student_managment_system
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.data.jpa.repositories.bootstrap-mode=default
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql=true

# MemCache lib that what we use to cache date  comming from database 
1-you should Download a stable version, in either 32-bit or 64-bit I have tested the 64-bit version.
2-Unzip it in some hard drive folder. For example C:\memcached
There will be memcached.exe file in the unzipped folder.
Open a command prompt (need to be opened as administrator).
3-Run c:\memcached\memcached.exe -d install
For start and stop run following command line
4-if you want to stop it 
c:\memcached\memcached.exe -d stop

5- you have to confige port and ip  you want memcache to work on  for example

memcached.ip= 127.0.0.1
memcached.port= 11211


# courseSchedule path config in order to dawnload pdf 
you have to configure   courseSchedule path in application.properties like that

courseSchedule.path=F:\\course-schedule\\
and application will concat the courseSchedule name from database .pdf in order to get the pdf file to dawnload it  like that
F:\\course-schedule\\Math.pdf
so you have set Math course pdf under the config courseSchedule path  in order to dawnload it


# i am providng postman collection for api testing  with name  "boubyan-studentManagmentSystem-Api.postman_collection" you can dawnload it 





