CREATE TABLE `issue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `file` longblob,
  `file_name` varchar(255) DEFAULT NULL,
  `priority` int NOT NULL,
  `priority_text` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `project_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcombytcpeogaqi2012phvvvhy` (`project_id`),
  KEY `FKip9bige7u8us6u9wtqd2r0th7` (`user_id`),
  CONSTRAINT `FKcombytcpeogaqi2012phvvvhy` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKip9bige7u8us6u9wtqd2r0th7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(150) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `resolved_issue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file` longblob,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `issue_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ybl4mfvge39dkj2tvsnarb2s` (`issue_id`),
  CONSTRAINT `FK3ybl4mfvge39dkj2tvsnarb2s` FOREIGN KEY (`issue_id`) REFERENCES `issue` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



