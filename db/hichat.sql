/*
Navicat MySQL Data Transfer

Source Server         : GJJ阿里云
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
  `content` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
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
  `from_user_id` bigint(16) DEFAULT NULL COMMENT '发送者',
  `to_user_id` bigint(16) DEFAULT NULL COMMENT '接收者',
  `message` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '聊天信息',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '聊天信息',
  `read_flag` varchar(16) DEFAULT NULL COMMENT 'no未读 yes已读',
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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='后台权限表';

-- ----------------------------
-- Records of h_permission
-- ----------------------------
INSERT INTO `h_permission` VALUES ('1', 'userinfo', '用户信息管理', 'userinfo:query', '查询');
INSERT INTO `h_permission` VALUES ('2', 'userinfo', '用户信息管理', 'userinfo:send', '发送信息');
INSERT INTO `h_permission` VALUES ('3', 'userinfo', '用户信息管理', 'userinfo:delete', '删除');
INSERT INTO `h_permission` VALUES ('4', 'chatrecord', '聊天记录管理', 'chatrecord:query', '查询');
INSERT INTO `h_permission` VALUES ('5', 'essayinfo', '文章管理', 'essayinfo:query', '查询');
INSERT INTO `h_permission` VALUES ('6', 'essayinfo', '文章管理', 'essayinfo:delete', '删除');
INSERT INTO `h_permission` VALUES ('7', 'essayinfo', '文章管理', 'essayinfo:load', '查看详情');
INSERT INTO `h_permission` VALUES ('8', 'employee', '系统用户管理', 'employee:query', '查询');
INSERT INTO `h_permission` VALUES ('9', 'employee', '系统用户管理', 'employee:edit', '编辑');
INSERT INTO `h_permission` VALUES ('11', 'employee', '系统用户管理', 'employee:delete', '删除');
INSERT INTO `h_permission` VALUES ('12', 'employee', '系统用户管理', 'employee:reset', '重置密码');
INSERT INTO `h_permission` VALUES ('13', 'employee', '系统用户管理', 'employee:assign', '分配角色');
INSERT INTO `h_permission` VALUES ('14', 'role', '系统角色管理', 'role:query', '角色管理查询');
INSERT INTO `h_permission` VALUES ('15', 'role', '系统角色管理', 'role:edit', '编辑');
INSERT INTO `h_permission` VALUES ('17', 'role', '系统角色管理', 'role:delete', '删除');
INSERT INTO `h_permission` VALUES ('18', 'role', '系统角色管理', 'role:assign', '分配权限');

-- ----------------------------
-- Table structure for h_role
-- ----------------------------
DROP TABLE IF EXISTS `h_role`;
CREATE TABLE `h_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of h_role
-- ----------------------------
INSERT INTO `h_role` VALUES ('1001', '程序员', '2018-07-27 14:45:35', null);
INSERT INTO `h_role` VALUES ('1002', '测试工程师', '2018-07-27 14:45:53', null);
INSERT INTO `h_role` VALUES ('1003', '项目经理', '2018-07-27 14:45:59', null);
INSERT INTO `h_role` VALUES ('1004', '技术总监', '2018-07-27 14:46:07', null);
INSERT INTO `h_role` VALUES ('1005', '管理员', '2018-07-25 16:35:00', '2018-08-03 21:15:15');

-- ----------------------------
-- Table structure for h_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `h_role_permission`;
CREATE TABLE `h_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of h_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for h_user
-- ----------------------------
DROP TABLE IF EXISTS `h_user`;
CREATE TABLE `h_user` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL COMMENT '用户账号',
  `nickname` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `sex` int(8) DEFAULT NULL COMMENT '性别 0为女性 1为男性',
  `description` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '个性签名',
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

