-------------------------------------
-- DDL for to_do_list.task
-------------------------------------
CREATE TABLE `task` (
                        `id` int NOT NULL,
                        `title` varchar(35) NOT NULL,
                        `content` varchar(120) DEFAULT NULL,
                        `hashtag` varchar(45) DEFAULT NULL,
                        `complete` int NOT NULL,
                        `date` datetime NOT NULL,
                        `start_time` datetime NOT NULL,
                        `end_time` datetime NOT NULL,
                        `priority` varchar(6) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3