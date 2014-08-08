CREATE TABLE `account_user` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`loginname` VARCHAR(64) NOT NULL,
	`email` VARCHAR(64) NULL DEFAULT NULL,
	`mobile` VARCHAR(64) NULL DEFAULT NULL,
	`password` VARCHAR(64) NULL DEFAULT NULL,
	`password_salt` VARCHAR(32) NULL DEFAULT NULL,
	`fullname` VARCHAR(64) NULL DEFAULT NULL,
	`status` INT(10) NOT NULL DEFAULT '0',
	`create_time` DATETIME NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `loginname` (`loginname`),
	UNIQUE INDEX `email` (`email`),
	UNIQUE INDEX `mobile` (`mobile`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;