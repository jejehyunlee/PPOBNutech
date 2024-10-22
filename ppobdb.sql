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

 Date: 22/10/2024 22:30:24
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
INSERT INTO `m_banner` VALUES (1, 'ddd', 'ddddd', 'ddd');

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
INSERT INTO `m_customer` VALUES ('6fa7f676-f637-4308-a43a-0f7efb428d9b', 10800, 'jejej989@gmail.com', 'Jefri', 'Amel', 'http://localhost:8080/uploads/6a7c7ce9-20c6-458a-a792-ee9885dbd382_foto dwi utomo.JPG', '6c0e73f9-ee35-4bd9-9ad0-835258e6dbfc');

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
INSERT INTO `m_service` VALUES (1, 'PLN', 'TOKEN PLN', 100, 1);

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
  INDEX `FKkydv7fvax8cgav3962gt8hadi`(`customer_id` ASC) USING BTREE,
  INDEX `FKampsrgjwpb5nkxqkbi24y221y`(`service_id` ASC) USING BTREE,
  CONSTRAINT `FKampsrgjwpb5nkxqkbi24y221y` FOREIGN KEY (`service_id`) REFERENCES `m_service` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKkydv7fvax8cgav3962gt8hadi` FOREIGN KEY (`customer_id`) REFERENCES `m_customer` (`id_customer`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of m_transaction
-- ----------------------------
INSERT INTO `m_transaction` VALUES (1, '2024-10-22 21:42:42.000000', '2024-10-22T21:42:42.639075300TOP UP', NULL, 'TOP UP BALANCE', 10000, 'TOP UP', '6fa7f676-f637-4308-a43a-0f7efb428d9b', NULL);
INSERT INTO `m_transaction` VALUES (2, '2024-10-22 21:43:26.000000', 'INV2024-10-22T21:43:26.958746-614f5', 'PLN', 'TOKEN PLN', 100, 'PAYMENT', '6fa7f676-f637-4308-a43a-0f7efb428d9b', 1);
INSERT INTO `m_transaction` VALUES (3, '2024-10-22 21:50:48.000000', '2024-10-22T21:50:48.568639800TOP UP', NULL, 'TOP UP BALANCE', 1000, 'TOP UP', '6fa7f676-f637-4308-a43a-0f7efb428d9b', NULL);
INSERT INTO `m_transaction` VALUES (4, '2024-10-22 21:51:00.000000', 'INV2024-10-22T21:51:00.650139900-357a5', 'PLN', 'TOKEN PLN', 100, 'PAYMENT', '6fa7f676-f637-4308-a43a-0f7efb428d9b', 1);

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
INSERT INTO `user_credential` VALUES ('6c0e73f9-ee35-4bd9-9ad0-835258e6dbfc', 'jejej989@gmail.com', '$2a$10$uIJaIApwi8UVBbhvRbQp6.pxLPnVde0C4pYidoRkEqVjepSW7fJ3y');

SET FOREIGN_KEY_CHECKS = 1;
