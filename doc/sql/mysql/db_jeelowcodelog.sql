/*
 Navicat Premium Data Transfer

 Source Server         : 内网-root
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 192.168.5.120:3306
 Source Schema         : db_jeelowcodelog

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 31/12/2024 13:48:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lowcode_log_api
-- ----------------------------
DROP TABLE IF EXISTS `lowcode_log_api`;
CREATE TABLE `lowcode_log_api`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(0) NULL DEFAULT 0 COMMENT '是否已删除',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求标题',
  `model_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块标题',
  `request_uri` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `time` bigint(0) NULL DEFAULT NULL COMMENT '执行时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_create_time`(`create_time`) USING BTREE,
  INDEX `ind_time`(`time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '请求日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lowcode_log_api
-- ----------------------------

-- ----------------------------
-- Table structure for lowcode_log_api_error
-- ----------------------------
DROP TABLE IF EXISTS `lowcode_log_api_error`;
CREATE TABLE `lowcode_log_api_error`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '000000' COMMENT '租户ID',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(0) NULL DEFAULT 0 COMMENT '是否已删除',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求标题',
  `model_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块标题',
  `request_uri` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `method_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `time` bigint(0) NULL DEFAULT NULL COMMENT '执行时间',
  `error` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误原因',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ind_create_time`(`create_time`) USING BTREE,
  INDEX `ind_time`(`time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '请求错误日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lowcode_log_api_error
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
