DROP DATABASE IF EXISTS `platform_db`;
DROP USER IF EXISTS 'platform_app'@'localhost';

CREATE DATABASE `platform_db` DEFAULT CHARACTER SET utf8;

CREATE USER 'platform_app'@'localhost' IDENTIFIED BY 'platform_password';

GRANT SELECT,INSERT,UPDATE,DELETE ON `platform_db`.* TO 'platform_app'@'localhost';

USE `platform_db`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL UNIQUE,
  `password` nchar(255) NOT NULL,
  `name` varchar(25) NOT NULL,
  `patronymic` varchar(25) NOT NULL,
  `surname` varchar(25) NOT NULL,
  `role` enum('ADMIN', 'PARTICIPANT') NOT NULL DEFAULT 'PARTICIPANT',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `topics` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `conferences` (
  `id` int NOT NULL AUTO_INCREMENT,
  `topic_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `status` enum('PENDING','CANCELED','ENDED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`id`),
  CONSTRAINT `topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `applications` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `conference_id` INT NOT NULL,
  `description` varchar(255) NOT NULL,
  `status` ENUM('CLAIMED', 'CONFIRMED', 'REJECTED') NOT NULL DEFAULT 'CLAIMED',
  PRIMARY KEY (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `conference_id` FOREIGN KEY (`conference_id`) REFERENCES `conferences` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `question` varchar(255) NOT NULL,
  `answer` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  CONSTRAINT `user_id_idxx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `users` VALUES (1,'a.khaletski@nosuchmail.by','6347467a63336476636d513d','Антон','Васильевич','Халецкий','ADMIN'); /* Base64 хэш пароля "password" */
INSERT INTO `users` VALUES (2,'r.bogdanovich@nosuchmail.by','6347467a63336476636d513d','Роман','Ростиславович','Богданович','PARTICIPANT');
INSERT INTO `users` VALUES (3,'e.lebedeva@nosuchmail.by','6347467a63336476636d513d','Екатерина','Антоновна','Лебедева','PARTICIPANT');
INSERT INTO `users` VALUES (4,'v.novik@nosuchmail.by','6347467a63336476636d513d','Владислав','Владиславович','Новик','PARTICIPANT');
INSERT INTO `users` VALUES (5,'v.chernov@nosuchmail.by','6347467a63336476636d513d','Владимир','Фёдорович','Чернов','PARTICIPANT');
INSERT INTO `users` VALUES (6,'g.bitadze@nosuchmail.by','6347467a63336476636d513d','Георгий','Андреевич','Битадзе','PARTICIPANT');
INSERT INTO `users` VALUES (7,'v.kruglov@nosuchmail.by','6347467a63336476636d513d','Валентин','Антонович','Круглов','PARTICIPANT');
INSERT INTO `users` VALUES (8,'m.sakharov@nosuchmail.by','6347467a63336476636d513d','Марк','Антонович','Сахаров','PARTICIPANT');
INSERT INTO `users` VALUES (9,'a.livadov@nosuchmail.by','6347467a63336476636d513d','Андрей','Григорьевич','Ливадов','PARTICIPANT');
INSERT INTO `users` VALUES (10,'m.kuznetsov@nosuchmail.by','6347467a63336476636d513d','Максим','Евгеньевич','Кузнецов','PARTICIPANT');
INSERT INTO `users` VALUES (11,'a.kovaleva@nosuchmail.by','6347467a63336476636d513d','Александра','Степановна','Ковалева','PARTICIPANT');
INSERT INTO `users` VALUES (12,'v.novikova@nosuchmail.by','6347467a63336476636d513d','Вероника','Владиславовна','Новикова','PARTICIPANT');
INSERT INTO `users` VALUES (13,'v.gromova@nosuchmail.by','6347467a63336476636d513d','Валерия','Александровна','Громова','PARTICIPANT');
INSERT INTO `users` VALUES (14,'a.samoilova@nosuchmail.by','6347467a63336476636d513d','Арина','Анатольевна','Самойлова','PARTICIPANT');
INSERT INTO `users` VALUES (15,'m.utkin@nosuchmail.by','6347467a63336476636d513d','Михаил','Михайлович','Уткин','PARTICIPANT');
INSERT INTO `users` VALUES (16,'a.tokarev@nosuchmail.by','6347467a63336476636d513d','Анатолий','Романович','Токарев','PARTICIPANT');
INSERT INTO `users` VALUES (17,'r.chuprin@nosuchmail.by','6347467a63336476636d513d','Родион','Витальевич','Чуприн','PARTICIPANT');
INSERT INTO `users` VALUES (18,'a.skryabin@nosuchmail.by','6347467a63336476636d513d','Александр','Святославович','Скрябин','PARTICIPANT');
INSERT INTO `users` VALUES (19,'s.tikhonov@nosuchmail.by','6347467a63336476636d513d','Станислав','Степанович','Тихонов','PARTICIPANT');
INSERT INTO `users` VALUES (20,'a.dolgin@nosuchmail.by','6347467a63336476636d513d','Александр','Егорович','Долгин','PARTICIPANT');

INSERT INTO `topics` VALUES (1,'Tech','Конференции на тему современных технологий');
INSERT INTO `topics` VALUES (2,'Economics','Конференции на тему экономики');
INSERT INTO `topics` VALUES (3,'History','Конференции на тему истории');

INSERT INTO `conferences` VALUES (1,1,'Дефицит полупроводников 2020-н.в.','Описание конференции','2022-02-28','PENDING');
INSERT INTO `conferences` VALUES (2,2,'Проблемы экономического роста','Описание конференции','2022-02-28','PENDING');
INSERT INTO `conferences` VALUES (3,3,'Кризис III века','Описание конференции','2022-02-28','PENDING');

INSERT INTO `applications` VALUES (1, 3, 1,'Текст заявки','CLAIMED');
INSERT INTO `applications` VALUES (2, 3, 2,'Текст заявки','CLAIMED');
INSERT INTO `applications` VALUES (3, 3, 3,'Текст заявки','CLAIMED');
INSERT INTO `applications` VALUES (4, 4, 1,'Текст заявки','CLAIMED');
INSERT INTO `applications` VALUES (5, 4, 2,'Текст заявки','CLAIMED');
INSERT INTO `applications` VALUES (6, 4, 3,'Текст заявки','CLAIMED');
