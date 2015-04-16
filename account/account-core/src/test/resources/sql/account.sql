DROP TABLE IF EXISTS `account_user`;
CREATE TABLE IF NOT EXISTS `account_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(64) DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `password_salt` varchar(16) DEFAULT NULL COMMENT '密码盐',
  `password_strength` int(11) DEFAULT NULL COMMENT '密码强度',
  `status` int(11) NOT NULL COMMENT '状态',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `email_verified` char(1) DEFAULT NULL COMMENT '邮箱是否验证',
  `mobile_verified` char(1) DEFAULT NULL COMMENT '手机号码是否验证',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `source` varchar(64) DEFAULT NULL COMMENT '来源',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginname` (`loginname`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;