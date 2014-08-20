DROP TABLE `account_user`;
CREATE TABLE `account_user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`loginname` VARCHAR(64) NOT NULL COMMENT '登录名',
	`email` VARCHAR(64) NULL DEFAULT NULL COMMENT '邮箱',
	`mobile` VARCHAR(16) NULL DEFAULT NULL COMMENT '手机号码',
	`password` VARCHAR(32) NULL DEFAULT NULL COMMENT '密码',
	`password_salt` VARCHAR(16) NULL DEFAULT NULL COMMENT '密码盐',
	`password_strength` INT(11) NULL DEFAULT NULL COMMENT '密码强度',
	`status` INT(11) NULL DEFAULT NULL COMMENT '状态',
	`level` INT(11) NULL DEFAULT NULL COMMENT '用户等级',
	`type` INT(11) NULL DEFAULT NULL COMMENT '用户类型',
	`validate` INT(11) NULL DEFAULT NULL COMMENT '校验位',
	`nickname` VARCHAR(64) NULL DEFAULT NULL COMMENT '昵称',
	`gender` CHAR(1) NULL DEFAULT NULL COMMENT '性别',
	`birthday` DATE NULL DEFAULT NULL COMMENT '生日',
	`source` VARCHAR(32) NULL DEFAULT NULL COMMENT '来源',
	`description` VARCHAR(256) NULL DEFAULT NULL COMMENT '描述',
	`open_id` VARCHAR(64) NULL DEFAULT NULL COMMENT '第三方账号（QQ、sina）的ID',
	`open_type` VARCHAR(16) NULL DEFAULT NULL COMMENT '第三方账号类型',
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `loginname` (`loginname`),
	UNIQUE INDEX `email` (`email`),
	UNIQUE INDEX `mobile` (`mobile`),
	UNIQUE INDEX `open_id` (`open_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;