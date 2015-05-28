-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.30 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-05-28 13:17:01
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for flus_cms
DROP DATABASE IF EXISTS `flus_cms`;
CREATE DATABASE IF NOT EXISTS `flus_cms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `flus_cms`;


-- Dumping structure for table flus_cms.attachment
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE IF NOT EXISTS `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL COMMENT '附件标题',
  `url` varchar(128) DEFAULT NULL COMMENT '访问地址',
  `file_size` bigint(64) DEFAULT NULL COMMENT '附件大小',
  `uploader_id` bigint(64) NOT NULL COMMENT '上传者ID',
  `uploader` varchar(64) DEFAULT NULL COMMENT '上传者',
  `upload_time` datetime NOT NULL COMMENT '上传时间',
  `delete_flag` char(1) NOT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table flus_cms.category
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL COMMENT '父栏目ID',
  `depth` int(11) NOT NULL COMMENT '栏目深度',
  `name` varchar(32) NOT NULL COMMENT '栏目名称',
  `status` int(11) NOT NULL COMMENT '状态',
  `allow_topic` char(1) NOT NULL COMMENT '本栏目是否允许发表主题',
  `topic_audit` char(1) NOT NULL COMMENT '本栏目发表的主题是否需要审核',
  `allow_reply` char(1) NOT NULL COMMENT '本栏目是否允许回复',
  `reply_audit` char(1) NOT NULL COMMENT '本栏目的回复是否需要审核',
  `weight` int(11) DEFAULT NULL COMMENT '排序',
  `creator_id` bigint(64) NOT NULL COMMENT '创建者ID',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `delete_flag` char(1) NOT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table flus_cms.reply
DROP TABLE IF EXISTS `reply`;
CREATE TABLE IF NOT EXISTS `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL COMMENT '主题ID',
  `status` int(11) NOT NULL COMMENT '状态',
  `author_id` bigint(64) NOT NULL COMMENT '作者ID',
  `author` varchar(64) DEFAULT NULL COMMENT '作者名称',
  `content` text COMMENT '内容',
  `recommend` char(1) DEFAULT NULL COMMENT '推荐',
  `praise_count` int(11) DEFAULT NULL COMMENT '点赞次数',
  `create_time` datetime NOT NULL COMMENT '发表时间',
  `delete_flag` char(1) NOT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table flus_cms.topic
DROP TABLE IF EXISTS `topic`;
CREATE TABLE IF NOT EXISTS `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL COMMENT '栏目ID',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `summary` varchar(512) DEFAULT NULL COMMENT '摘要',
  `thumbnail` varchar(128) DEFAULT NULL COMMENT '缩略图地址',
  `content` longtext COMMENT '内容',
  `keywords` varchar(64) DEFAULT NULL COMMENT '关键字',
  `author_id` bigint(64) NOT NULL COMMENT '作者ID',
  `author` varchar(64) DEFAULT NULL COMMENT '作者名称',
  `source` varchar(64) DEFAULT NULL COMMENT '来源',
  `status` int(11) NOT NULL COMMENT '状态',
  `link_out` char(1) DEFAULT NULL COMMENT '是否为外链',
  `link_url` varchar(128) DEFAULT NULL COMMENT '外链地址',
  `allow_reply` char(1) NOT NULL COMMENT '是否允许回复',
  `place_top` char(1) DEFAULT NULL COMMENT '是否置顶',
  `view_count` int(11) DEFAULT NULL COMMENT '查看次数',
  `reply_count` int(11) DEFAULT NULL COMMENT '评论条数',
  `praise_count` int(11) DEFAULT NULL COMMENT '点赞的次数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `last_replyer_id` bigint(64) DEFAULT NULL COMMENT '最后回复者ID',
  `last_replyer` varchar(64) DEFAULT NULL COMMENT '最后回复者名称',
  `last_reply_time` datetime DEFAULT NULL COMMENT '最后回复时间',
  `delete_flag` char(1) NOT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id`),
  KEY `column_id` (`category_id`),
  KEY `user_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
