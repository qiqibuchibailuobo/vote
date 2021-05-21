/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80013
Source Host           : 127.0.0.1:3306
Source Database       : voting_system

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2021-05-21 16:07:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '参与竞争者名称',
  `gain_votes` int(255) unsigned zerofill NOT NULL DEFAULT '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000' COMMENT '得票数',
  `create_time` datetime DEFAULT NULL COMMENT '参与时间',
  `vote_id` int(11) NOT NULL COMMENT '参与竞争的投票',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `user_id` int(11) DEFAULT NULL COMMENT '参与者',
  `gain_votes_percentage` float NOT NULL DEFAULT '0' COMMENT '得票占百分比',
  `state` int(255) NOT NULL DEFAULT '0' COMMENT '参赛选手审核，通过为1未通过为0\r\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES ('1', 'yq', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006', '2021-05-16 17:47:27', '3', 'player测试', '1', '40', '1');
INSERT INTO `player` VALUES ('2', '111', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000006', '2021-05-16 18:14:11', '3', '111', null, '40', '1');
INSERT INTO `player` VALUES ('3', '222', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003', '2021-05-16 18:14:26', '3', '222', null, '20', '1');
INSERT INTO `player` VALUES ('5', '1234', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '2021-05-17 18:14:00', '3', '测试2', '6', '0', '0');
INSERT INTO `player` VALUES ('6', 'addPlayerTest', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '2021-05-19 22:46:45', '3', '123', null, '0', '1');
INSERT INTO `player` VALUES ('8', '123', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '2021-05-19 23:22:25', '4', '123', null, '0', '1');
INSERT INTO `player` VALUES ('10', '11111', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '2021-05-19 23:31:03', '1', '123123123', null, '0', '1');
INSERT INTO `player` VALUES ('11', '123123', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '2021-05-21 14:43:47', '4', '123123', null, '0', '1');
INSERT INTO `player` VALUES ('12', '1', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '2021-05-21 14:48:28', '4', '1', null, '0', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `user_type` int(255) NOT NULL COMMENT '用户权限',
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123', 'yq', '0', '1103113563@qq.com');
INSERT INTO `user` VALUES ('2', '123433', '123456', '123433', '1', '11@qq.com');
INSERT INTO `user` VALUES ('6', '12345', '12345', '12345', '1', '111@qq.com');
INSERT INTO `user` VALUES ('9', '111', '111', '111', '1', '11@qq.com');
INSERT INTO `user` VALUES ('10', '123', '123', '123', '1', '1123@qq.com');

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '投票名称',
  `user_id` int(11) DEFAULT NULL COMMENT '投票发起人id',
  `state` int(11) NOT NULL COMMENT '投票审核',
  `create_date` datetime NOT NULL COMMENT '投票创建时间',
  `adopt_date` datetime DEFAULT NULL COMMENT '审核通过时间',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `max_choice` int(11) NOT NULL DEFAULT '1' COMMENT '最多可以给几个竞争人投票，默认为1人',
  `end_date` datetime NOT NULL COMMENT '投票结束时间',
  `end_state` int(10) NOT NULL DEFAULT '0' COMMENT '投票是否已经结束，未结束是0，结束是1，未开始是2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of vote
-- ----------------------------
INSERT INTO `vote` VALUES ('1', 'test', '1', '1', '2021-05-13 20:24:19', '2021-05-13 20:24:23', '测试', '1', '2021-05-15 20:24:30', '1');
INSERT INTO `vote` VALUES ('2', 'test2', '1', '1', '2021-05-06 20:24:47', '2021-05-13 20:24:51', '测试1', '1', '2021-05-22 20:24:57', '0');
INSERT INTO `vote` VALUES ('3', '测试更新', '1', '1', '2021-05-05 20:25:22', '2021-05-13 20:25:25', '测试更新', '5', '2021-05-21 00:00:00', '1');
INSERT INTO `vote` VALUES ('4', '1234', '1', '0', '2021-05-18 12:41:46', null, '123', '1', '2021-05-22 00:00:00', '0');
INSERT INTO `vote` VALUES ('8', '665', '1', '0', '2021-05-21 00:00:00', null, '44', '1', '2021-05-29 00:00:00', '0');

-- ----------------------------
-- Table structure for vote_info
-- ----------------------------
DROP TABLE IF EXISTS `vote_info`;
CREATE TABLE `vote_info` (
  `user_id` int(11) DEFAULT NULL COMMENT '投票人id',
  `player_id` int(11) NOT NULL COMMENT '选手id',
  `vote_id` int(11) NOT NULL COMMENT '投票的id',
  `real_name` int(11) DEFAULT NULL COMMENT '是否匿名，实名为0，匿名为1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of vote_info
-- ----------------------------
INSERT INTO `vote_info` VALUES ('1', '1', '3', '0');
INSERT INTO `vote_info` VALUES ('1', '1', '3', '0');
INSERT INTO `vote_info` VALUES ('1', '2', '3', '0');
INSERT INTO `vote_info` VALUES ('5', '1', '3', '0');
INSERT INTO `vote_info` VALUES ('5', '2', '3', '0');
INSERT INTO `vote_info` VALUES ('5', '3', '3', '0');
INSERT INTO `vote_info` VALUES ('7', '3', '3', '0');
INSERT INTO `vote_info` VALUES ('7', '1', '3', '1');
INSERT INTO `vote_info` VALUES ('7', '2', '3', '1');
