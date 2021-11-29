DROP DATABASE IF EXISTS or_dev;
CREATE DATABASE or_dev;
USE or_dev;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `id`              bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '主键UUID',
    `created_time`    datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`     datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新者uuid',
    `permission_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限code',
    `description`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限描述',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_permission_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission`
VALUES (1, 'bd954bf75b2d4fbcac18714470fb986b', '2021-11-09 10:16:34', NULL, NULL, NULL, 'upms:user:delete', '删除用户');
INSERT INTO `sys_permission`
VALUES (2, '343407312c4841f1b3c00b2793e36b10', '2021-11-09 10:16:52', NULL, NULL, NULL, 'upms:role:delete', '删除角色');
INSERT INTO `sys_permission`
VALUES (3, '1f5890f597ec40a7b560149bef107542', '2021-11-09 10:17:03', NULL, NULL, NULL, 'upms:permission:delete',
        '删除权限');
INSERT INTO `sys_permission`
VALUES (4, 'e632b522d085400b8cc79681c8f6348e', '2021-11-09 10:17:18', NULL, NULL, NULL, 'upms:request_path:delete',
        '删除请求路径');

-- ----------------------------
-- Table structure for sys_request_path
-- ----------------------------
DROP TABLE IF EXISTS `sys_request_path`;
CREATE TABLE `sys_request_path`
(
    `id`              bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '主键UUID',
    `created_time`    datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`     datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新者uuid',
    `path`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径url',
    `path_name`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路径名称',
    `permission_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '请求路径所需要的权限uuid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_request_path_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '请求路径表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_request_path
-- ----------------------------
INSERT INTO `sys_request_path`
VALUES (1, '6ff7c559a6a64a4b984b35539b837c8f', '2021-11-09 14:53:08', NULL, NULL, NULL, '/upms/request_path/create',
        '创建请求路径', NULL);
INSERT INTO `sys_request_path`
VALUES (2, '78a97e8487be4ff8a37e03ac11d9dd24', '2021-11-09 15:05:03', NULL, '2021-11-09 15:06:58', NULL,
        '/upms/request_path/delete', '删除请求路径', 'e632b522d085400b8cc79681c8f6348e');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新者uuid',
    `role_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
    `role_code`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色code',
    `enabled`      bit(1)                                                        NULL DEFAULT b'1' COMMENT '角色是否可用',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_role_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '1a18d9a41ff34abfa07b73340779d63e', '2021-11-04 14:59:24', NULL, NULL, NULL, '开发者', 'DEVELOPER', b'1');
INSERT INTO `sys_role`
VALUES (2, '11aa7ccc1d194afabb7a303d48b07683', '2021-11-04 14:59:59', NULL, '2021-11-04 15:37:47', NULL, '管理员', 'ADMIN',
        b'1');

-- ----------------------------
-- Table structure for sys_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions`
(
    `id`              bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键UUID',
    `created_time`    datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`     datetime                                                     NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者uuid',
    `role_uuid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色uuid',
    `permission_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限uuid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_roles_permissions_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联关系表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
INSERT INTO `sys_roles_permissions`
VALUES (1, '01ec7a746e8143889024a0652952f7f4', '2021-11-09 15:07:57', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'e632b522d085400b8cc79681c8f6348e');
INSERT INTO `sys_roles_permissions`
VALUES (2, '38befca855964468af6c65e8fd64a7a5', '2021-11-09 15:08:08', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '1f5890f597ec40a7b560149bef107542');
INSERT INTO `sys_roles_permissions`
VALUES (3, '10bc9057195a406f99ae2d05bd857ad7', '2021-11-09 15:08:11', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '343407312c4841f1b3c00b2793e36b10');
INSERT INTO `sys_roles_permissions`
VALUES (4, 'eb1eb9cad831488790cb14084cd68bcc', '2021-11-09 15:08:15', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'bd954bf75b2d4fbcac18714470fb986b');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '主键UUID',
    `created_time`         datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`          datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新者uuid',
    `or_number`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'or帐号',
    `email`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
    `username`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
    `enabled`              bit(1)                                                        NULL DEFAULT b'1' COMMENT '账号是否可用',
    `not_expired`          bit(1)                                                        NULL DEFAULT b'1' COMMENT '帐号是否未过期',
    `not_locked`           bit(1)                                                        NULL DEFAULT b'1' COMMENT '账号是否未锁定',
    `password_not_expired` bit(1)                                                        NULL DEFAULT b'1' COMMENT '密码是否未过期',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_user_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, '76a8133381924e23a9172dac75100047', '2021-11-15 10:08:36', NULL, NULL, NULL, '10001', 'developer',
        '1962247851@qq.com', '{bcrypt}$2a$10$7hEXm1/XC66HW8ehUhFASuyRzV7w4J5bTiH8VFUgWjxPF3/YzReD6', b'1', b'1', b'1',
        b'1');
INSERT INTO `sys_user`
VALUES (2, '24f14f1a400b4bed9caa8d698b33d49a', '2021-11-15 16:07:41', NULL, NULL, NULL, '10002', 'admin',
        '204879304@qq.com', '{bcrypt}$2a$10$7hEXm1/XC66HW8ehUhFASuyRzV7w4J5bTiH8VFUgWjxPF3/YzReD6', b'1', b'1', b'1',
        b'1');

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`
(
    `id`           bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                     NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者uuid',
    `user_uuid`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户uuid',
    `role_uuid`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色uuid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_users_roles_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联关系表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles`
VALUES (1, 'd1395a44794b45fca47fc05d9cb4e42e', '2021-11-15 16:07:41', NULL, NULL, NULL,
        '76a8133381924e23a9172dac75100047', '1a18d9a41ff34abfa07b73340779d63e');
INSERT INTO `sys_users_roles`
VALUES (2, 'd74bb2c5561b4258aa79de257ac12c65', '2021-11-15 16:07:41', NULL, NULL, NULL,
        '24f14f1a400b4bed9caa8d698b33d49a', '11aa7ccc1d194afabb7a303d48b07683');

SET FOREIGN_KEY_CHECKS = 1;
