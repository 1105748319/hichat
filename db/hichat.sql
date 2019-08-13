/*
Navicat MySQL Data Transfer

Source Server         : GJJ������
Source Server Version : 50641
Source Host           : 120.79.5.163:3306
Source Database       : hichat

Target Server Type    : MYSQL
Target Server Version : 50641
File Encoding         : 65001

Date: 2018-08-28 08:33:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for h_comment
-- ----------------------------
DROP TABLE IF EXISTS `h_comment`;
CREATE TABLE `h_comment` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `user_id` bigint(10) DEFAULT NULL,
  `essay_id` bigint(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of h_comment
-- ----------------------------

-- ----------------------------
-- Table structure for h_essay
-- ----------------------------
DROP TABLE IF EXISTS `h_essay`;
CREATE TABLE `h_essay` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(8) NOT NULL,
  `content` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '����',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `imgs` varchar(500) DEFAULT NULL,
  `small_imgs` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of h_essay
-- ----------------------------

-- ----------------------------
-- Table structure for h_message
-- ----------------------------
DROP TABLE IF EXISTS `h_message`;
CREATE TABLE `h_message` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(16) DEFAULT NULL COMMENT '������',
  `to_user_id` bigint(16) DEFAULT NULL COMMENT '������',
  `message` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '������Ϣ',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '������Ϣ',
  `read_flag` varchar(16) DEFAULT NULL COMMENT 'noδ�� yes�Ѷ�',
  `msg_type` int(8) DEFAULT NULL,
  `small_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_message
-- ----------------------------

-- ----------------------------
-- Table structure for h_permission
-- ----------------------------
DROP TABLE IF EXISTS `h_permission`;
CREATE TABLE `h_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '�Զ�id,��Ҫ��ǰ��չʾȨ���б��������ʹ��.',
  `menu_code` varchar(255) DEFAULT '' COMMENT '�����˵�,ǰ���жϲ�չʾ�˵�ʹ��,',
  `menu_name` varchar(255) DEFAULT '' COMMENT '�˵�����������',
  `permission_code` varchar(255) DEFAULT '' COMMENT 'Ȩ�޵Ĵ���/ͨ���,��Ӧ������@RequiresPermissions ��value',
  `permission_name` varchar(255) DEFAULT '' COMMENT '��Ȩ�޵���������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='��̨Ȩ�ޱ�';

-- ----------------------------
-- Records of h_permission
-- ----------------------------
INSERT INTO `h_permission` VALUES ('1', 'userinfo', '�û���Ϣ����', 'userinfo:query', '��ѯ');
INSERT INTO `h_permission` VALUES ('2', 'userinfo', '�û���Ϣ����', 'userinfo:send', '������Ϣ');
INSERT INTO `h_permission` VALUES ('3', 'userinfo', '�û���Ϣ����', 'userinfo:delete', 'ɾ��');
INSERT INTO `h_permission` VALUES ('4', 'chatrecord', '�����¼����', 'chatrecord:query', '��ѯ');
INSERT INTO `h_permission` VALUES ('5', 'essayinfo', '���¹���', 'essayinfo:query', '��ѯ');
INSERT INTO `h_permission` VALUES ('6', 'essayinfo', '���¹���', 'essayinfo:delete', 'ɾ��');
INSERT INTO `h_permission` VALUES ('7', 'essayinfo', '���¹���', 'essayinfo:load', '�鿴����');
INSERT INTO `h_permission` VALUES ('8', 'employee', 'ϵͳ�û�����', 'employee:query', '��ѯ');
INSERT INTO `h_permission` VALUES ('9', 'employee', 'ϵͳ�û�����', 'employee:edit', '�༭');
INSERT INTO `h_permission` VALUES ('11', 'employee', 'ϵͳ�û�����', 'employee:delete', 'ɾ��');
INSERT INTO `h_permission` VALUES ('12', 'employee', 'ϵͳ�û�����', 'employee:reset', '��������');
INSERT INTO `h_permission` VALUES ('13', 'employee', 'ϵͳ�û�����', 'employee:assign', '�����ɫ');
INSERT INTO `h_permission` VALUES ('14', 'role', 'ϵͳ��ɫ����', 'role:query', '��ɫ�����ѯ');
INSERT INTO `h_permission` VALUES ('15', 'role', 'ϵͳ��ɫ����', 'role:edit', '�༭');
INSERT INTO `h_permission` VALUES ('17', 'role', 'ϵͳ��ɫ����', 'role:delete', 'ɾ��');
INSERT INTO `h_permission` VALUES ('18', 'role', 'ϵͳ��ɫ����', 'role:assign', '����Ȩ��');

-- ----------------------------
-- Table structure for h_role
-- ----------------------------
DROP TABLE IF EXISTS `h_role`;
CREATE TABLE `h_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '��ɫ��',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8 COMMENT='��̨��ɫ��';

-- ----------------------------
-- Records of h_role
-- ----------------------------
INSERT INTO `h_role` VALUES ('1001', '����Ա', '2018-07-27 14:45:35', null);
INSERT INTO `h_role` VALUES ('1002', '���Թ���ʦ', '2018-07-27 14:45:53', null);
INSERT INTO `h_role` VALUES ('1003', '��Ŀ����', '2018-07-27 14:45:59', null);
INSERT INTO `h_role` VALUES ('1004', '�����ܼ�', '2018-07-27 14:46:07', null);
INSERT INTO `h_role` VALUES ('1005', '����Ա', '2018-07-25 16:35:00', '2018-08-03 21:15:15');

-- ----------------------------
-- Table structure for h_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `h_role_permission`;
CREATE TABLE `h_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '��ɫid',
  `permission_id` int(11) DEFAULT NULL COMMENT 'Ȩ��id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɫ-Ȩ�޹�����';

-- ----------------------------
-- Records of h_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for h_user
-- ----------------------------
DROP TABLE IF EXISTS `h_user`;
CREATE TABLE `h_user` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL COMMENT '�û��˺�',
  `nickname` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '�û��ǳ�',
  `password` varchar(255) DEFAULT NULL COMMENT '�û�����',
  `avatar` varchar(255) DEFAULT NULL COMMENT '�û�ͷ��',
  `sex` int(8) DEFAULT NULL COMMENT '�Ա� 0ΪŮ�� 1Ϊ����',
  `description` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '����ǩ��',
  `header` varchar(8) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_user
-- ----------------------------

-- ----------------------------
-- Table structure for h_web_role
-- ----------------------------
DROP TABLE IF EXISTS `h_web_role`;
CREATE TABLE `h_web_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `web_user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of h_web_role
-- ----------------------------
INSERT INTO `h_web_role` VALUES ('999', '999', '0');

-- ----------------------------
-- Table structure for h_web_user
-- ----------------------------
DROP TABLE IF EXISTS `h_web_user`;
CREATE TABLE `h_web_user` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of h_web_user
-- ----------------------------
INSERT INTO `h_web_user` VALUES ('999', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', '2018-07-23 09:06:56', '2018-07-25 15:36:10');

-- ----------------------------
-- View structure for messageusers
-- ----------------------------
DROP VIEW IF EXISTS `messageusers`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `messageusers` AS select `userfrom`.`nickname` AS `fromUser`,`userfrom`.`id` AS `fromId`,`userto`.`id` AS `toId`,`userto`.`nickname` AS `toUser`,`message`.`create_time` AS `chatTime`,`message`.`message` AS `message`,(((`userfrom`.`id` + `userto`.`id`) + (`userfrom`.`id` * `userto`.`id`)) / ((`userfrom`.`id` * `userto`.`id`) * 2)) AS `groupId` from ((`h_message` `message` left join `h_user` `userfrom` on((`userfrom`.`id` = `message`.`from_user_id`))) left join `h_user` `userto` on((`userto`.`id` = `message`.`to_user_id`))) order by `message`.`create_time` desc ;

-- ----------------------------
-- View structure for permissionmenu
-- ----------------------------
DROP VIEW IF EXISTS `permissionmenu`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `permissionmenu` AS select (sum(`perm`.`id`) + 10000) AS `id`,`perm`.`menu_code` AS `menu_code`,`perm`.`menu_name` AS `menu_name` from `h_permission` `perm` group by `perm`.`menu_code`,`perm`.`menu_name` ;


-- ----------------------------
-- View structure for permissionlist
-- ----------------------------
DROP VIEW IF EXISTS `permissionlist`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `permissionlist` AS select `t`.`id` AS `pId`,`t`.`menu_name` AS `pLabel`,`child`.`id` AS `cId`,`child`.`permission_name` AS `cLabel` from (`permissionmenu` `t` left join `h_permission` `child` on((`child`.`menu_code` = `t`.`menu_code`))) order by `child`.`id` ;

