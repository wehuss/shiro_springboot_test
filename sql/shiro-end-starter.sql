/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.10.91_3306
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : shiro-end-starter

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 30/10/2020 11:38:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_per
-- ----------------------------
DROP TABLE IF EXISTS `role_per`;
CREATE TABLE `role_per`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '表主键id',
  `roleId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色表的主键id',
  `perId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限表的主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_per
-- ----------------------------
INSERT INTO `role_per` VALUES (1, '100', 'M01');
INSERT INTO `role_per` VALUES (2, '100', 'M02');
INSERT INTO `role_per` VALUES (3, '100', 'M03');
INSERT INTO `role_per` VALUES (4, '200', 'M204');
INSERT INTO `role_per` VALUES (5, '100', 'M204');

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions`  (
  `perId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限表id 作为表主键 用于关联',
  `permissionsName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `perRemarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，预留字段',
  PRIMARY KEY (`perId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions` VALUES ('M01', 'resetPassword', '重置密码');
INSERT INTO `sys_permissions` VALUES ('M02', 'querySystemLog', '查看系统日志');
INSERT INTO `sys_permissions` VALUES ('M03', 'exportUserInfo', '导出用户信息');
INSERT INTO `sys_permissions` VALUES ('M204', 'queryMyUserInfo', '查看个人信息');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `roleId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ' 角色id 作为表主键 用于关联',
  `roleName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `roleRemarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，预留字段',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('0', 'superAdmin', '超管');
INSERT INTO `sys_role` VALUES ('100', 'admin', '系统管理员');
INSERT INTO `sys_role` VALUES ('200', 'common', '普通用户');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'user表的id字段',
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id 作为表主键 用于关联',
  `userName` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录帐号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录密码',
  `userRemarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注，预留字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '10001', 'adminHong', '202cb962ac59075b964b07152d234b70', '小红');
INSERT INTO `sys_user` VALUES (2, '15564819717', '2', 'f10bfea990b657f6e8355c6e3ee67e2d', 'hehe');
INSERT INTO `sys_user` VALUES (3, '177900', '武文生', 'c4dbde004d409a733e4a7c8b00466613', '生生酱');
INSERT INTO `sys_user` VALUES (4, '20001', 'jc', 'e165421110ba03099a1c0393373c5b43', 'JC');
INSERT INTO `sys_user` VALUES (5, '18864819717', 'myDear', '756b3c2e758a2b8c728fa2e4d3f3294d', 'nice today i love you');
INSERT INTO `sys_user` VALUES (8, '18262699169', 'hello', '4cad6da13952ad1621e4f8ede54d9fad', 'hello');
INSERT INTO `sys_user` VALUES (9, '15698756214', 'good luck', '713741121ec6b5d854b9c15e78a36f27', 'good luck');
INSERT INTO `sys_user` VALUES (10, '12345678922', '测试一下', 'a753bbccb874ef05b43b9fceffb949dd', '闲来无聊');
INSERT INTO `sys_user` VALUES (13, '4396', 'testUser', '63a54e0562e208a580e041d5ec40d7d0', NULL);
INSERT INTO `sys_user` VALUES (14, '43967', 'testUser', '3d7bc9e0be81a05373a77e40b8109914', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '表主键id',
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帐号表的主键id',
  `roleId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色表的主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, '10001', '100');
INSERT INTO `user_role` VALUES (2, '20001', '200');
INSERT INTO `user_role` VALUES (3, '177900', '200');
INSERT INTO `user_role` VALUES (4, '15564819717', '200');
INSERT INTO `user_role` VALUES (5, '18864819717', '200');
INSERT INTO `user_role` VALUES (8, '18262699169', '200');
INSERT INTO `user_role` VALUES (9, '15698756214', '200');
INSERT INTO `user_role` VALUES (10, '12345678922', '200');
INSERT INTO `user_role` VALUES (12, '43967', '100');
INSERT INTO `user_role` VALUES (13, '4396', '0');

SET FOREIGN_KEY_CHECKS = 1;
