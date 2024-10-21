DROP DATABASE IF EXISTS medilabo;

CREATE DATABASE medilabo;
USE medilabo;

CREATE TABLE patient
(
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `firstname` VARCHAR(30) NOT NULL,
  `lastname` VARCHAR(30) NOT NULL,
  `birthdate` DATE NOT NULL,
  `gender` VARCHAR(1) NOT NULL,
  `address` VARCHAR(50),
  `phone_number` VARCHAR(10)
);

INSERT INTO patient (lastname, firstname, birthdate, gender, address, phone_number) 
VALUES
("TestNone", "Test", "1966-12-31", "F", "1 Brookside St", "1002223333"),
("TestBorderline", "Test", "1945-06-24", "M", "2 High St", "2003334444"),
("TestInDanger", "Test", "2004-06-18", "M", "3 Club Road", "3004445555"),
("TestEarlyOnset", "Test", "2002-06-28", "F", "4 Valley Dr", "4005556666");