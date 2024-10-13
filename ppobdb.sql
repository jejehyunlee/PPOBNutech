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

 Date: 13/10/2024 21:39:04
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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_banner
-- ----------------------------
INSERT INTO `m_banner` VALUES (1, 'Bnner1.jpg', 'Banner1', 'LoremIpsum');
INSERT INTO `m_banner` VALUES (2, 'Bnner2', 'Banner2', 'LoremIpsum');

-- ----------------------------
-- Table structure for m_customer
-- ----------------------------
DROP TABLE IF EXISTS `m_customer`;
CREATE TABLE `m_customer`  (
  `balance` int NULL DEFAULT NULL,
  `customer_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `firstname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_customer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
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
INSERT INTO `m_customer` VALUES (150, 'Jefri1234@gmail.com', 'Jefri', 'b68edcda-ec98-4975-b00e-d29ffb797300', 'Saputra', NULL, '4e0ed745-6fc4-4fa7-b77b-d31e28984575');

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
  `service_tariff` int NULL DEFAULT NULL,
  `banner_id` bigint NULL DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKivtv2eurjpvqkqooihvrigtkd`(`banner_id` ASC) USING BTREE,
  CONSTRAINT `FKivtv2eurjpvqkqooihvrigtkd` FOREIGN KEY (`banner_id`) REFERENCES `m_banner` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_service
-- ----------------------------
INSERT INTO `m_service` VALUES (50, 1, 1, 'PLN', 'PLN');
INSERT INTO `m_service` VALUES (20, 2, 2, 'PULSA', 'PULSA');

-- ----------------------------
-- Table structure for m_transaction
-- ----------------------------
DROP TABLE IF EXISTS `m_transaction`;
CREATE TABLE `m_transaction`  (
  `amount` int NULL DEFAULT NULL,
  `created_on` datetime(6) NULL DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_id` bigint NULL DEFAULT NULL,
  `id_cust` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `invoice_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `transaction_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKbfslqy6hiqe1ggluadgxt6c1w`(`invoice_number` ASC) USING BTREE,
  INDEX `FKampsrgjwpb5nkxqkbi24y221y`(`service_id` ASC) USING BTREE,
  CONSTRAINT `FKampsrgjwpb5nkxqkbi24y221y` FOREIGN KEY (`service_id`) REFERENCES `m_service` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_transaction
-- ----------------------------
INSERT INTO `m_transaction` VALUES (NULL, '2024-10-13 21:35:59.000000', 1, NULL, 'b68edcda-ec98-4975-b00e-d29ffb797300', NULL, NULL, NULL, 'TOP UP');
INSERT INTO `m_transaction` VALUES (NULL, '2024-10-13 21:36:11.000000', 2, NULL, 'b68edcda-ec98-4975-b00e-d29ffb797300', NULL, NULL, NULL, 'TOP UP');
INSERT INTO `m_transaction` VALUES (50, '2024-10-13 21:38:28.000000', 3, 1, 'b68edcda-ec98-4975-b00e-d29ffb797300', 'INV2024-10-13T21:38:28.743868200-7f0dd', 'PLN', 'PLN', 'PAYMENT');

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
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_credential_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_credential_id`) USING BTREE,
  UNIQUE INDEX `UKja400kbsfopl13c5pu8rhbo09`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_credential
-- ----------------------------
INSERT INTO `user_credential` VALUES ('Jefri1234@gmail.com', '$2a$10$2eCWyZLAvjw.Adj55.Nh6eTdbTHn0/JKaBlafY7UV/NVLYzczqs0G', '4e0ed745-6fc4-4fa7-b77b-d31e28984575');

SET FOREIGN_KEY_CHECKS = 1;
