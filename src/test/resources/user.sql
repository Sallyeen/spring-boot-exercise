DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255),
    `password` VARCHAR(255),
    `gender` TINYINT,
    `avatar` VARCHAR(255),
    `email` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
