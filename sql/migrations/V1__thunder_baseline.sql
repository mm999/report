/*
Navicat MariaDB Data Transfer

Source Server         : local
Source Server Version : 50544
Source Host           : 127.0.0.1:3306
Source Database       : storm

Target Server Type    : MariaDB
Target Server Version : 50544
File Encoding         : 65001

Date: 2015-07-22 17:36:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` int(11) NOT NULL COMMENT '日期',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '总用户数 = 关注未激活用户-取消关注用户数',
  `not_activited` int(11) NOT NULL DEFAULT '0' COMMENT '未激活用户数',
  `activited` int(11) NOT NULL DEFAULT '0' COMMENT '激活用户数',
  `bind_mobile` int(11) NOT NULL DEFAULT '0' COMMENT '绑定手机号用户数',
  `certified` int(11) NOT NULL DEFAULT '0' COMMENT '实名验证用户数',
  `bind_card` int(11) NOT NULL DEFAULT '0' COMMENT '绑定银行卡用户数',
  `deposited` int(11) NOT NULL DEFAULT '0' COMMENT '理财用户数',
  `not_follow_total` int(11) NOT NULL DEFAULT '0' COMMENT '取消关注用户数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户信息';
