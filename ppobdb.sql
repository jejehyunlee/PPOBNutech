/*
 Navicat Premium Data Transfer

 Source Server         : ENIGMA
 Source Server Type    : MySQL
 Source Server Version : 100425 (10.4.25-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : ppobdb

 Target Server Type    : MySQL
 Target Server Version : 100425 (10.4.25-MariaDB)
 File Encoding         : 65001

 Date: 15/10/2024 03:49:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_banner
-- ----------------------------
DROP TABLE IF EXISTS `m_banner`;
CREATE TABLE `m_banner`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `banner_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `banner_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_banner
-- ----------------------------
INSERT INTO `m_banner` VALUES (1, 'ddd', 'ddd', 'ddd');

-- ----------------------------
-- Table structure for m_customer
-- ----------------------------
DROP TABLE IF EXISTS `m_customer`;
CREATE TABLE `m_customer`  (
  `id_customer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `balance` int NULL DEFAULT NULL,
  `customer_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `firstname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `lastname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `profile_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_credential_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_customer`) USING BTREE,
  UNIQUE INDEX `UKbypbllo4qv254rat34rc482td`(`user_credential_id` ASC) USING BTREE,
  CONSTRAINT `FK9xhqedb6g7u3i0nsyg2h11m6l` FOREIGN KEY (`user_credential_id`) REFERENCES `user_credential` (`user_credential_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_customer
-- ----------------------------
INSERT INTO `m_customer` VALUES ('40ed7568-37ba-4c3d-8e28-d883955993bf', 799500, 'Jefri1234@gmail.com', 'Jefri', 'Saputra', NULL, '66032f28-a8f2-471d-af4b-c8caf7fe179e');

-- ----------------------------
-- Table structure for m_role
-- ----------------------------
DROP TABLE IF EXISTS `m_role`;
CREATE TABLE `m_role`  (
  `id_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_CUSTOMER','ROLE_SELLER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_role
-- ----------------------------

-- ----------------------------
-- Table structure for m_service
-- ----------------------------
DROP TABLE IF EXISTS `m_service`;
CREATE TABLE `m_service`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_tariff` int NULL DEFAULT NULL,
  `banner_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKivtv2eurjpvqkqooihvrigtkd`(`banner_id` ASC) USING BTREE,
  CONSTRAINT `FKivtv2eurjpvqkqooihvrigtkd` FOREIGN KEY (`banner_id`) REFERENCES `m_banner` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_service
-- ----------------------------
INSERT INTO `m_service` VALUES (1, 'PLN', 'PLN', 100, 1);

-- ----------------------------
-- Table structure for m_transaction
-- ----------------------------
DROP TABLE IF EXISTS `m_transaction`;
CREATE TABLE `m_transaction`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime(6) NULL DEFAULT NULL,
  `invoice_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `amount` int NULL DEFAULT NULL,
  `transaction_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKbfslqy6hiqe1ggluadgxt6c1w`(`invoice_number` ASC) USING BTREE,
  INDEX `FKampsrgjwpb5nkxqkbi24y221y`(`service_id` ASC) USING BTREE,
  INDEX `FKkydv7fvax8cgav3962gt8hadi`(`customer_id` ASC) USING BTREE,
  CONSTRAINT `FKampsrgjwpb5nkxqkbi24y221y` FOREIGN KEY (`service_id`) REFERENCES `m_service` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKkydv7fvax8cgav3962gt8hadi` FOREIGN KEY (`customer_id`) REFERENCES `m_customer` (`id_customer`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_transaction
-- ----------------------------
INSERT INTO `m_transaction` VALUES (1, '2024-10-15 03:39:46.000000', NULL, NULL, 'TOP UP BALANCE', NULL, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (2, '2024-10-15 03:39:56.000000', 'INV2024-10-15T03:39:56.556769600-a1ce0', 'PLN', 'PLN', 100, 'PAYMENT', '40ed7568-37ba-4c3d-8e28-d883955993bf', 1);
INSERT INTO `m_transaction` VALUES (3, '2024-10-15 03:42:32.000000', NULL, NULL, 'TOP UP BALANCE', NULL, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (4, '2024-10-15 03:42:37.000000', NULL, NULL, 'TOP UP BALANCE', NULL, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (5, '2024-10-15 03:42:43.000000', NULL, NULL, 'TOP UP BALANCE', NULL, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (6, '2024-10-15 03:42:58.000000', 'INV2024-10-15T03:42:58.072146800-75acf', 'PLN', 'PLN', 100, 'PAYMENT', '40ed7568-37ba-4c3d-8e28-d883955993bf', 1);
INSERT INTO `m_transaction` VALUES (7, '2024-10-15 03:42:59.000000', 'INV2024-10-15T03:42:59.598587-50981', 'PLN', 'PLN', 100, 'PAYMENT', '40ed7568-37ba-4c3d-8e28-d883955993bf', 1);
INSERT INTO `m_transaction` VALUES (8, '2024-10-15 03:46:23.000000', '2024-10-15T03:46:22.963251100TOP-UP', NULL, 'TOP UP BALANCE', 100000, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (9, '2024-10-15 03:46:24.000000', '2024-10-15T03:46:24.557678TOP-UP', NULL, 'TOP UP BALANCE', 100000, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (10, '2024-10-15 03:46:26.000000', '2024-10-15T03:46:26.829183400TOP-UP', NULL, 'TOP UP BALANCE', 100000, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (11, '2024-10-15 03:46:29.000000', '2024-10-15T03:46:29.348131500TOP-UP', NULL, 'TOP UP BALANCE', 100000, 'TOP UP', '40ed7568-37ba-4c3d-8e28-d883955993bf', NULL);
INSERT INTO `m_transaction` VALUES (12, '2024-10-15 03:48:41.000000', 'INV2024-10-15T03:48:41.522670200-ba59a', 'PLN', 'PLN', 100, 'PAYMENT', '40ed7568-37ba-4c3d-8e28-d883955993bf', 1);
INSERT INTO `m_transaction` VALUES (13, '2024-10-15 03:48:43.000000', 'INV2024-10-15T03:48:43.205612200-c1376', 'PLN', 'PLN', 100, 'PAYMENT', '40ed7568-37ba-4c3d-8e28-d883955993bf', 1);

-- ----------------------------
-- Table structure for m_user_role
-- ----------------------------
DROP TABLE IF EXISTS `m_user_role`;
CREATE TABLE `m_user_role`  (
  `id_user_credential` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  INDEX `FK8r0vgj4oh9yadj6wx62tk0p5m`(`role_id` ASC) USING BTREE,
  INDEX `FK5myajstt3aviu653dg1r529d4`(`id_user_credential` ASC) USING BTREE,
  CONSTRAINT `FK5myajstt3aviu653dg1r529d4` FOREIGN KEY (`id_user_credential`) REFERENCES `user_credential` (`user_credential_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK8r0vgj4oh9yadj6wx62tk0p5m` FOREIGN KEY (`role_id`) REFERENCES `m_role` (`id_role`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for user_credential
-- ----------------------------
DROP TABLE IF EXISTS `user_credential`;
CREATE TABLE `user_credential`  (
  `user_credential_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_credential_id`) USING BTREE,
  UNIQUE INDEX `UKja400kbsfopl13c5pu8rhbo09`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credential
-- ----------------------------
INSERT INTO `user_credential` VALUES ('66032f28-a8f2-471d-af4b-c8caf7fe179e', 'Jefri1234@gmail.com', '$2a$10$BRUdBV9Uk0DWp62bQf5/BOS1B3mT3Exk.q1EPQ9iQmSyYgigj4rdK');

SET FOREIGN_KEY_CHECKS = 1;
