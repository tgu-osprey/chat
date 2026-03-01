/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : fish_backfonted

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 14/02/2026 21:19:35
*/
CREATE DATABASE IF NOT EXISTS fish_backfonted DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE fish_backfonted;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for talking_message
-- ----------------------------
DROP TABLE IF EXISTS `talking_message`;
CREATE TABLE `talking_message`  (
                                    `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '信息内容',
                                    `user_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
                                    `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'uuid',
                                    `time` datetime(3) NOT NULL COMMENT '发送时间',
                                    `time_string` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送时间戳'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_name
-- ----------------------------
DROP TABLE IF EXISTS `user_name`;
CREATE TABLE `user_name`  (
                              `random_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '随机名称',
                              `status` tinyint NOT NULL COMMENT '状态值（1启用0弃用）',
                              `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'uuid,作为唯一识别码',
                              `abandon_time` datetime NULL DEFAULT NULL COMMENT '弃用时间',
                              PRIMARY KEY (`random_name`, `uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
