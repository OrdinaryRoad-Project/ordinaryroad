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
    `uuid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time`    datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`     datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '更新者uuid',
    `permission_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '权限code',
    `description`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '权限描述',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_permission_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_cs COMMENT = '权限表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission`
VALUES (1, 'bd954bf75b2d4fbcac18714470fb986b', '2021-11-09 10:16:34', NULL, '2021-12-11 17:43:56', NULL,
        'upms:user:delete', '删除用户');
INSERT INTO `sys_permission`
VALUES (2, '343407312c4841f1b3c00b2793e36b10', '2021-11-09 10:16:52', NULL, NULL, NULL, 'upms:role:delete', '删除角色');
INSERT INTO `sys_permission`
VALUES (3, '1f5890f597ec40a7b560149bef107542', '2021-11-09 10:17:03', NULL, NULL, NULL, 'upms:permission:delete',
        '删除权限');
INSERT INTO `sys_permission`
VALUES (4, 'e632b522d085400b8cc79681c8f6348e', '2021-11-09 10:17:18', NULL, '2021-12-11 23:20:06', NULL,
        'upms:request_path:delete', '删除请求路径');
INSERT INTO `sys_permission`
VALUES (6, '04ab8971cfcb44aea2c397877eeed0c2', '2021-12-11 22:45:54', NULL, '2021-12-12 10:18:57', NULL,
        'upms:user:list', '查询用户列表');
INSERT INTO `sys_permission`
VALUES (7, '17b0cfe601e04cf3b612b4df9a94570d', '2021-12-12 10:17:00', NULL, NULL, NULL, 'upms:user:create', '创建用户');
INSERT INTO `sys_permission`
VALUES (8, '62120888853b4556befe4246700d6e7b', '2021-12-12 10:17:23', NULL, NULL, NULL, 'upms:user:update', '更新用户');
INSERT INTO `sys_permission`
VALUES (9, 'b6db670ac60f42c4b7de8867e85e0d20', '2021-12-12 10:17:40', NULL, NULL, NULL, 'upms:user:reset:password',
        '重置用户密码');
INSERT INTO `sys_permission`
VALUES (10, '882df767d05440dcb3dd7507a17e0e29', '2021-12-12 10:17:56', NULL, NULL, NULL, 'upms:user:update:enabled',
        '帐号停用/启用');
INSERT INTO `sys_permission`
VALUES (11, '406d0fe89fcc49c181f1cf5728cd3e6a', '2021-12-12 10:38:48', NULL, NULL, NULL, 'upms:role:create', '创建角色');
INSERT INTO `sys_permission`
VALUES (12, 'e03f347c1aab45618ad794f38beb2bec', '2021-12-12 10:39:15', NULL, NULL, NULL, 'upms:role:update', '更新角色');
INSERT INTO `sys_permission`
VALUES (13, '7e800fe9018b4f009e2b9527a3e4acc1', '2021-12-12 10:39:34', NULL, NULL, NULL, 'upms:role:list', '查询角色列表');
INSERT INTO `sys_permission`
VALUES (14, '63a44136bfe34c16bedf04887bb333c5', '2021-12-12 10:40:02', NULL, NULL, NULL, 'upms:role:update:role_users',
        '更新角色关联的用户');
INSERT INTO `sys_permission`
VALUES (15, 'e13b37e83e3c46c9831f8eb19a85086f', '2021-12-12 10:40:23', NULL, NULL, NULL,
        'upms:role:update:role_permissions', '更新角色拥有的权限');
INSERT INTO `sys_permission`
VALUES (16, '2b5e5774fd044661937e7aa839fdcd28', '2021-12-12 12:02:56', NULL, NULL, NULL, 'upms:user:update:user_roles',
        '更新用户拥有的角色');
INSERT INTO `sys_permission`
VALUES (17, 'f2b9cc30a8024a46923ba208c16bf396', '2021-12-12 12:04:02', NULL, NULL, NULL, 'upms:request_path:create',
        '创建请求路径');
INSERT INTO `sys_permission`
VALUES (18, '94b536a914d04350b537f8235a112254', '2021-12-12 12:11:09', NULL, NULL, NULL, 'upms:request_path:update',
        '更新请求路径');
INSERT INTO `sys_permission`
VALUES (19, '18501b30b9db4747b6db23de4155a31c', '2021-12-12 12:12:31', NULL, NULL, NULL, 'upms:request_path:list',
        '查询请求路径列表');
INSERT INTO `sys_permission`
VALUES (20, '99426a7f72c545b685c52ca821290f86', '2021-12-12 12:16:03', NULL, NULL, NULL, 'upms:permission:update',
        '更新权限');
INSERT INTO `sys_permission`
VALUES (21, '6f5a8a73c13b4005942fbf4dc2e36973', '2021-12-12 12:16:50', NULL, NULL, NULL, 'upms:permission:create',
        '创建权限');
INSERT INTO `sys_permission`
VALUES (22, '73da3231b3164f05bf27625747138cc8', '2021-12-12 12:17:47', NULL, NULL, NULL, 'upms:permission:list',
        '查询权限列表');
INSERT INTO `sys_permission`
VALUES (23, '140462770d2945da85ed6f17863e4a64', '2022-01-06 18:03:20', NULL, NULL, NULL, 'upms:dict:create', '创建字典');
INSERT INTO `sys_permission`
VALUES (24, 'a2ef2a0497b54016b546478c05edde87', '2022-01-06 18:04:32', NULL, NULL, NULL, 'upms:dict:delete', '删除字典');
INSERT INTO `sys_permission`
VALUES (25, '45e7cc43256b4d17a62b6ece7828d090', '2022-01-06 18:04:53', NULL, NULL, NULL, 'upms:dict:update', '更新字典');
INSERT INTO `sys_permission`
VALUES (26, '5f4666f586d34509bfd55a3baa8da9fa', '2022-01-06 18:05:07', NULL, NULL, NULL, 'upms:dict:list', '查询字典列表');
INSERT INTO `sys_permission`
VALUES (27, '88ec00ed22044e2bbe8edfa7fc2ba30f', '2022-01-06 18:03:20', NULL, NULL, NULL, 'upms:dict_item:create',
        '创建字典项');
INSERT INTO `sys_permission`
VALUES (28, '299eaee1ff694be2a843499ec9d2a6bd', '2022-01-06 18:04:32', NULL, NULL, NULL, 'upms:dict_item:delete',
        '删除字典项');
INSERT INTO `sys_permission`
VALUES (29, 'efb03858340c4614aea79b4ad8dd08ed', '2022-01-06 18:04:53', NULL, NULL, NULL, 'upms:dict_item:update',
        '更新字典项');
INSERT INTO `sys_permission`
VALUES (30, 'e1adfb6d17bf4353a0786d0ff49a1c95', '2022-01-06 18:05:07', NULL, NULL, NULL, 'upms:dict_item:list',
        '查询字典项列表');

-- ----------------------------
-- Table structure for sys_request_path
-- ----------------------------
DROP TABLE IF EXISTS `sys_request_path`;
CREATE TABLE `sys_request_path`
(
    `id`              bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time`    datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`     datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '更新者uuid',
    `path`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '路径url',
    `path_name`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '路径名称',
    `permission_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '请求路径所需要的权限uuid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_request_path_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_cs COMMENT = '请求路径表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_request_path
-- ----------------------------
INSERT INTO `sys_request_path`
VALUES (1, '6ff7c559a6a64a4b984b35539b837c8f', '2021-11-09 14:53:08', NULL, '2021-12-12 12:04:19', NULL,
        '/upms/request_path/create', '创建请求路径', 'f2b9cc30a8024a46923ba208c16bf396');
INSERT INTO `sys_request_path`
VALUES (2, '78a97e8487be4ff8a37e03ac11d9dd24', '2021-11-09 15:05:03', NULL, '2021-11-09 15:06:58', NULL,
        '/upms/request_path/delete', '删除请求路径', 'e632b522d085400b8cc79681c8f6348e');
INSERT INTO `sys_request_path`
VALUES (4, 'fee5c4fef84347a69d656fcae92ec298', '2021-12-07 10:29:00', NULL, NULL, NULL, '/upms/user/delete', '删除用户',
        'bd954bf75b2d4fbcac18714470fb986b');
INSERT INTO `sys_request_path`
VALUES (5, '8ec31850d169411faa64a85c2a5156b2', '2021-12-07 10:49:10', NULL, '2021-12-11 23:22:37', NULL,
        '/upms/user/list', '查询用户列表', '04ab8971cfcb44aea2c397877eeed0c2');
INSERT INTO `sys_request_path`
VALUES (6, '7dad729ee0164d43bd1716d0af25e90b', '2021-12-12 10:07:04', NULL, '2021-12-12 10:18:05', NULL,
        '/upms/user/create', '创建用户', '17b0cfe601e04cf3b612b4df9a94570d');
INSERT INTO `sys_request_path`
VALUES (7, '9dcbbbede58e4a4f99c122be5059439d', '2021-12-12 10:07:36', NULL, '2021-12-12 10:18:12', NULL,
        '/upms/user/update', '更新用户', '62120888853b4556befe4246700d6e7b');
INSERT INTO `sys_request_path`
VALUES (8, '10e409541a214af896b354d701128383', '2021-12-12 10:08:25', NULL, '2021-12-12 10:18:17', NULL,
        '/upms/user/reset/password', '重置用户密码', 'b6db670ac60f42c4b7de8867e85e0d20');
INSERT INTO `sys_request_path`
VALUES (9, '0bc2f6ef30184d408477ebb5d6144a35', '2021-12-12 10:09:58', NULL, '2021-12-12 10:18:23', NULL,
        '/upms/user/update/enabled', '帐号停用/启用', '882df767d05440dcb3dd7507a17e0e29');
INSERT INTO `sys_request_path`
VALUES (10, '240540d262a4492d9eba0405fc1c7c48', '2021-12-12 10:22:41', NULL, '2021-12-12 12:03:10', NULL,
        '/upms/user/update/user_roles', '更新用户拥有的角色', '2b5e5774fd044661937e7aa839fdcd28');
INSERT INTO `sys_request_path`
VALUES (11, '000b93d9083e40d7a8ae466604818192', '2021-12-12 10:23:22', NULL, '2021-12-12 10:40:36', NULL,
        '/upms/role/update/role_users', '更新角色关联的用户', '63a44136bfe34c16bedf04887bb333c5');
INSERT INTO `sys_request_path`
VALUES (12, '7b2c2b60d48d465fbb31822f6da39d07', '2021-12-12 10:23:49', NULL, '2021-12-12 10:40:46', NULL,
        '/upms/role/create', '创建角色', '406d0fe89fcc49c181f1cf5728cd3e6a');
INSERT INTO `sys_request_path`
VALUES (13, '89b79612c7944276817d3604653b5fe0', '2021-12-12 10:24:05', NULL, '2021-12-12 10:40:52', NULL,
        '/upms/role/delete', '删除角色', '343407312c4841f1b3c00b2793e36b10');
INSERT INTO `sys_request_path`
VALUES (14, 'bf9d4c1ffb394bc98466b84892bc9ea9', '2021-12-12 10:24:22', NULL, '2021-12-12 10:40:57', NULL,
        '/upms/role/update', '更新角色', 'e03f347c1aab45618ad794f38beb2bec');
INSERT INTO `sys_request_path`
VALUES (15, '08f36e73cf5d4ba3a6e0b1f2a627b1f2', '2021-12-12 10:24:44', NULL, '2021-12-12 10:41:01', NULL,
        '/upms/role/list', '查询角色列表', '7e800fe9018b4f009e2b9527a3e4acc1');
INSERT INTO `sys_request_path`
VALUES (16, 'c3e0e43066ab48adaa0cd807ea5dd012', '2021-12-12 10:25:37', NULL, '2021-12-12 10:58:48', NULL,
        '/upms/role/update/role_permissions', '更新角色拥有的权限', 'e13b37e83e3c46c9831f8eb19a85086f');
INSERT INTO `sys_request_path`
VALUES (17, '06c6a3ea231a400b97504aed4389a8b6', '2021-12-12 12:10:27', NULL, '2021-12-12 12:11:43', NULL,
        '/upms/request_path/update', '更新请求路径', '94b536a914d04350b537f8235a112254');
INSERT INTO `sys_request_path`
VALUES (18, '5cf53b3728d14c2ca26e0c088fd580c3', '2021-12-12 12:12:16', NULL, '2021-12-12 12:12:47', NULL,
        '/upms/request_path/list', '查询请求路径列表', '18501b30b9db4747b6db23de4155a31c');
INSERT INTO `sys_request_path`
VALUES (19, 'ab3081b0bddd4eb7a7e5674e249d32f6', '2021-12-12 12:13:20', NULL, '2021-12-12 13:13:30', NULL,
        '/upms/permission/create', '创建权限', '6f5a8a73c13b4005942fbf4dc2e36973');
INSERT INTO `sys_request_path`
VALUES (20, 'e34181fc44d842cd812d5d1bc21bedd6', '2021-12-12 12:13:41', NULL, '2021-12-12 13:14:11', NULL,
        '/upms/permission/delete', '删除权限', '1f5890f597ec40a7b560149bef107542');
INSERT INTO `sys_request_path`
VALUES (21, '4d8ece0129e34c6693d5d7f8a71ea80d', '2021-12-12 12:14:08', NULL, '2021-12-12 13:14:21', NULL,
        '/upms/permission/update', '更新权限', '99426a7f72c545b685c52ca821290f86');
INSERT INTO `sys_request_path`
VALUES (22, '6281a48c06094afdb475c73ee9079d73', '2021-12-12 12:14:59', NULL, '2021-12-12 13:14:28', NULL,
        '/upms/permission/list', '查询权限列表', '73da3231b3164f05bf27625747138cc8');
INSERT INTO `sys_request_path`
VALUES (23, '908f6eca4d4e4f3790d19cab653d3d4f', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict/create', '创建字典',
        '140462770d2945da85ed6f17863e4a64');
INSERT INTO `sys_request_path`
VALUES (24, 'a22760555af74d53b111e725508fa4a0', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict/delete', '删除字典',
        'a2ef2a0497b54016b546478c05edde87');
INSERT INTO `sys_request_path`
VALUES (25, '4ec456f9767c47379204efc5a563b2d1', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict/update', '更新字典',
        '45e7cc43256b4d17a62b6ece7828d090');
INSERT INTO `sys_request_path`
VALUES (26, 'f303717162f244329a748ef30f5243cf', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict/list', '查询字典列表',
        '5f4666f586d34509bfd55a3baa8da9fa');
INSERT INTO `sys_request_path`
VALUES (27, '8e96f6ca2641463da37062709df40180', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict_item/create',
        '创建字典项', '88ec00ed22044e2bbe8edfa7fc2ba30f');
INSERT INTO `sys_request_path`
VALUES (28, 'a6d790ed75e3424b8e8149e41d730f98', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict_item/delete',
        '删除字典项', '299eaee1ff694be2a843499ec9d2a6bd');
INSERT INTO `sys_request_path`
VALUES (29, 'e58f7be01c9c46229c8da4a9da408f21', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict_item/update',
        '更新字典项', 'efb03858340c4614aea79b4ad8dd08ed');
INSERT INTO `sys_request_path`
VALUES (30, 'bfba6860e1a240888fde856dab2565fb', '2022-01-06 18:09:24', NULL, NULL, NULL, '/upms/dict_item/list',
        '查询字典项列表', 'e1adfb6d17bf4353a0786d0ff49a1c95');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '更新者uuid',
    `role_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '角色名称',
    `role_code`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '角色code',
    `enabled`      bit(1)                                                        NULL DEFAULT b'1' COMMENT '角色是否可用',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_role_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_cs COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '1a18d9a41ff34abfa07b73340779d63e', '2021-11-04 14:59:24', NULL, '2021-12-06 21:10:22', NULL, '开发者',
        'DEVELOPER', b'1');
INSERT INTO `sys_role`
VALUES (2, '11aa7ccc1d194afabb7a303d48b07683', '2021-11-04 14:59:59', NULL, '2021-12-06 20:03:48', NULL, '管理员', 'ADMIN',
        b'1');
INSERT INTO `sys_role`
VALUES (3, '63b55b8be8fe49af82a822aa9aa57868', '2021-12-06 20:00:34', NULL, NULL, NULL, '测试', 'TEST', b'1');

-- ----------------------------
-- Table structure for sys_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions`
(
    `id`              bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '主键UUID',
    `created_time`    datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`     datetime                                                     NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '更新者uuid',
    `role_uuid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '角色uuid',
    `permission_uuid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '权限uuid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_roles_permissions_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 66
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_cs COMMENT = '角色权限关联关系表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
INSERT INTO `sys_roles_permissions`
VALUES (1, '01ec7a746e8143889024a0652952f7f4', '2021-11-09 15:07:57', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'e632b522d085400b8cc79681c8f6348e');
INSERT INTO `sys_roles_permissions`
VALUES (4, 'eb1eb9cad831488790cb14084cd68bcc', '2021-11-09 15:08:15', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'bd954bf75b2d4fbcac18714470fb986b');
INSERT INTO `sys_roles_permissions`
VALUES (9, 'e8d2ca753f0943f8b2c0383f88431bed', '2021-12-11 20:29:42', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '343407312c4841f1b3c00b2793e36b10');
INSERT INTO `sys_roles_permissions`
VALUES (10, 'cbd63a647a784aff9d70ff9bbac03850', '2021-12-11 20:29:42', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '1f5890f597ec40a7b560149bef107542');
INSERT INTO `sys_roles_permissions`
VALUES (13, '2d477db5e1954ec89ce4f555ab863916', '2021-12-11 22:46:36', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '04ab8971cfcb44aea2c397877eeed0c2');
INSERT INTO `sys_roles_permissions`
VALUES (14, '38545c4f497d47298c040d71b82f5b7f', '2021-12-11 23:34:38', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '04ab8971cfcb44aea2c397877eeed0c2');
INSERT INTO `sys_roles_permissions`
VALUES (15, 'bf9157c885324ea8892b508812013b04', '2021-12-12 10:18:44', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '17b0cfe601e04cf3b612b4df9a94570d');
INSERT INTO `sys_roles_permissions`
VALUES (16, '31723250c33545bc97ce17875f7f7963', '2021-12-12 10:18:44', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '62120888853b4556befe4246700d6e7b');
INSERT INTO `sys_roles_permissions`
VALUES (17, 'a7e7efb717b54f5e93182b31b23a0071', '2021-12-12 10:18:44', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'b6db670ac60f42c4b7de8867e85e0d20');
INSERT INTO `sys_roles_permissions`
VALUES (18, 'b5054be0be784489a35f4972e6138d88', '2021-12-12 10:18:44', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '882df767d05440dcb3dd7507a17e0e29');
INSERT INTO `sys_roles_permissions`
VALUES (21, '314183dfbc104334b0f93510d8f713cd', '2021-12-12 10:58:10', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '406d0fe89fcc49c181f1cf5728cd3e6a');
INSERT INTO `sys_roles_permissions`
VALUES (22, '8934dc44d6e4420cbe284f9f4982821d', '2021-12-12 10:58:10', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'e03f347c1aab45618ad794f38beb2bec');
INSERT INTO `sys_roles_permissions`
VALUES (23, '0b7283628dc04520813e036cd4e22861', '2021-12-12 10:58:10', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '7e800fe9018b4f009e2b9527a3e4acc1');
INSERT INTO `sys_roles_permissions`
VALUES (24, '636c7a1234b1479faddc352cfbcdc0bb', '2021-12-12 10:58:10', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '63a44136bfe34c16bedf04887bb333c5');
INSERT INTO `sys_roles_permissions`
VALUES (25, '3041ecb791b84924aba5fa7cf7a7abf7', '2021-12-12 10:58:10', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'e13b37e83e3c46c9831f8eb19a85086f');
INSERT INTO `sys_roles_permissions`
VALUES (26, 'ed31188f2e7f43489ce765b0cddf288f', '2021-12-12 11:49:14', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '7e800fe9018b4f009e2b9527a3e4acc1');
INSERT INTO `sys_roles_permissions`
VALUES (28, '401b54d84e514a5f906b65204bd631c0', '2021-12-12 12:03:23', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '2b5e5774fd044661937e7aa839fdcd28');
INSERT INTO `sys_roles_permissions`
VALUES (29, 'fe7d4f5718eb473f97903b80b222cddc', '2021-12-12 12:05:55', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'f2b9cc30a8024a46923ba208c16bf396');
INSERT INTO `sys_roles_permissions`
VALUES (30, '3d051567677c4f19b4e44fc80b917a56', '2021-12-12 12:11:25', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '94b536a914d04350b537f8235a112254');
INSERT INTO `sys_roles_permissions`
VALUES (31, 'a9872a7388ce45c79101c9e9f56f173c', '2021-12-12 12:12:37', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '18501b30b9db4747b6db23de4155a31c');
INSERT INTO `sys_roles_permissions`
VALUES (32, '05e8337760c642d79d562bc6e9569fdd', '2021-12-12 13:13:01', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '99426a7f72c545b685c52ca821290f86');
INSERT INTO `sys_roles_permissions`
VALUES (33, '0a4915a315fc4060ab23b104d9c64dc8', '2021-12-12 13:13:01', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '6f5a8a73c13b4005942fbf4dc2e36973');
INSERT INTO `sys_roles_permissions`
VALUES (34, 'fae543c77fa442aa8b847bc077a69cc0', '2021-12-12 13:13:01', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '73da3231b3164f05bf27625747138cc8');
INSERT INTO `sys_roles_permissions`
VALUES (38, 'b93418d1bf824b818b0876e7435a776f', '2021-12-12 13:15:18', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '18501b30b9db4747b6db23de4155a31c');
INSERT INTO `sys_roles_permissions`
VALUES (43, '3d9f253914b24b7098e54b0a008abaf2', '2021-12-13 20:00:21', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '73da3231b3164f05bf27625747138cc8');
INSERT INTO `sys_roles_permissions`
VALUES (44, 'f8c0442cfe954deba896eaae1d6e8daa', '2021-12-13 20:39:58', NULL, NULL, NULL,
        '63b55b8be8fe49af82a822aa9aa57868', '04ab8971cfcb44aea2c397877eeed0c2');
INSERT INTO `sys_roles_permissions`
VALUES (45, '91a616f790d64a3186dc882774563960', '2021-12-13 20:39:58', NULL, NULL, NULL,
        '63b55b8be8fe49af82a822aa9aa57868', '7e800fe9018b4f009e2b9527a3e4acc1');
INSERT INTO `sys_roles_permissions`
VALUES (46, '772723a6f8cf4dcdb46228006cb63a45', '2021-12-13 20:39:58', NULL, NULL, NULL,
        '63b55b8be8fe49af82a822aa9aa57868', '18501b30b9db4747b6db23de4155a31c');
INSERT INTO `sys_roles_permissions`
VALUES (47, '00d341c8a68740d598576fbc3103ad80', '2021-12-13 20:39:58', NULL, NULL, NULL,
        '63b55b8be8fe49af82a822aa9aa57868', '73da3231b3164f05bf27625747138cc8');
INSERT INTO `sys_roles_permissions`
VALUES (48, '12139798e842498896e5592d7a02fe81', '2021-12-13 20:40:10', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '17b0cfe601e04cf3b612b4df9a94570d');
INSERT INTO `sys_roles_permissions`
VALUES (49, '6f51599746984c08999fcb9ce00b9a00', '2021-12-13 20:40:10', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '406d0fe89fcc49c181f1cf5728cd3e6a');
INSERT INTO `sys_roles_permissions`
VALUES (50, '8c9760736a9f48cfacb81ffcf7d3443e', '2021-12-13 20:40:10', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', 'f2b9cc30a8024a46923ba208c16bf396');
INSERT INTO `sys_roles_permissions`
VALUES (51, 'c9805e01f6b34a3190d400b89b993d4f', '2021-12-13 20:40:10', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '6f5a8a73c13b4005942fbf4dc2e36973');
INSERT INTO `sys_roles_permissions`
VALUES (52, 'a8af7cae29604e2da9244a141612ed52', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '140462770d2945da85ed6f17863e4a64');
INSERT INTO `sys_roles_permissions`
VALUES (53, '163092a9f59b4430bf31f5b2b2d45767', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'a2ef2a0497b54016b546478c05edde87');
INSERT INTO `sys_roles_permissions`
VALUES (54, '1ddbec68b77a4e1fa9088fa7f31c9ef4', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '45e7cc43256b4d17a62b6ece7828d090');
INSERT INTO `sys_roles_permissions`
VALUES (55, '40b3a18ac75b42f38a36383d493408ad', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '5f4666f586d34509bfd55a3baa8da9fa');
INSERT INTO `sys_roles_permissions`
VALUES (56, 'dfd3feaae4bf470fb09eabad6873b976', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '88ec00ed22044e2bbe8edfa7fc2ba30f');
INSERT INTO `sys_roles_permissions`
VALUES (57, '4ad6156937dd472f87b2a78d28779a96', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '299eaee1ff694be2a843499ec9d2a6bd');
INSERT INTO `sys_roles_permissions`
VALUES (58, 'b497404c0859447daf024cf60facf6db', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'efb03858340c4614aea79b4ad8dd08ed');
INSERT INTO `sys_roles_permissions`
VALUES (59, '6e5bce5f23fa482dad18f8089d700e35', '2022-01-06 18:12:40', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', 'e1adfb6d17bf4353a0786d0ff49a1c95');
INSERT INTO `sys_roles_permissions`
VALUES (60, '66e0478bbf5345f49989dab21ed90755', '2022-01-06 18:13:09', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '140462770d2945da85ed6f17863e4a64');
INSERT INTO `sys_roles_permissions`
VALUES (61, '16f50731aef94951bcc3dba46c65f8f6', '2022-01-06 18:13:09', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '5f4666f586d34509bfd55a3baa8da9fa');
INSERT INTO `sys_roles_permissions`
VALUES (62, '1fe4f50546f94adfb4938d48f576209f', '2022-01-06 18:13:09', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', '88ec00ed22044e2bbe8edfa7fc2ba30f');
INSERT INTO `sys_roles_permissions`
VALUES (63, 'cf9c1ad8720145dd84c108e9a234b76a', '2022-01-06 18:13:09', NULL, NULL, NULL,
        '11aa7ccc1d194afabb7a303d48b07683', 'e1adfb6d17bf4353a0786d0ff49a1c95');
INSERT INTO `sys_roles_permissions`
VALUES (64, 'f4ee11f652ee49a7b6805be2ac284751', '2022-01-06 18:13:25', NULL, NULL, NULL,
        '63b55b8be8fe49af82a822aa9aa57868', '5f4666f586d34509bfd55a3baa8da9fa');
INSERT INTO `sys_roles_permissions`
VALUES (65, '3ff9cf02b72d46dabdd8595ba0da9b8a', '2022-01-06 18:13:25', NULL, NULL, NULL,
        '63b55b8be8fe49af82a822aa9aa57868', 'e1adfb6d17bf4353a0786d0ff49a1c95');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`                   bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time`         datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`          datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '更新者uuid',
    `or_number`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'or帐号',
    `avatar`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '头像地址',
    `email`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '邮箱',
    `username`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '用户名',
    `password`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
    `enabled`              bit(1)                                                        NULL DEFAULT b'1' COMMENT '账号是否可用',
    `not_expired`          bit(1)                                                        NULL DEFAULT b'1' COMMENT '帐号是否未过期',
    `not_locked`           bit(1)                                                        NULL DEFAULT b'1' COMMENT '账号是否未锁定',
    `password_not_expired` bit(1)                                                        NULL DEFAULT b'1' COMMENT '密码是否未过期',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_user_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (1, '76a8133381924e23a9172dac75100047', '2021-11-15 10:08:36', NULL, '2021-12-13 19:44:37', NULL, '10001', null,
        '1962247851@qq.com', 'mjz', '{bcrypt}$2a$10$Kn6IdZZ4sJYmI0IHB8zY.OoVrGiO3BHd7CPpCl8ayrgwOiiM0pKJe', b'1', b'1',
        b'1', b'1');
INSERT INTO `sys_user`
VALUES (2, '24f14f1a400b4bed9caa8d698b33d49a', '2021-11-15 16:07:41', NULL, '2021-12-14 09:47:10', NULL, '10002', null,
        '1781422987@qq.com', 'zjy', '{bcrypt}$2a$10$uATrbKq6RFqkCzRrH81mQ.BdhUpw9ssXjjy4znGeItuh/ed6fvyam', b'0', b'1',
        b'1', b'1');
INSERT INTO `sys_user`
VALUES (3, 'c8e93fc029d445119e0c0215ea5ee4b7', '2021-11-29 19:25:26', NULL, '2021-12-14 09:47:11', NULL, '10003', null,
        '204879304@qq.com', 'admin', '{bcrypt}$2a$10$AIMOVvFfXC5JAUASl74x9OpnMw2fsgifLl/dJqQr5lN56HtrkQYeq', b'0', b'1',
        b'1', b'1');
INSERT INTO `sys_user`
VALUES (4, '50b4a8fd617b48babb9342bf51ae0822', '2021-12-06 21:46:17', NULL, '2021-12-14 09:47:13', NULL, '10004', null,
        '452259370@qq.com', 'test', '{bcrypt}$2a$10$Kn6IdZZ4sJYmI0IHB8zY.OoVrGiO3BHd7CPpCl8ayrgwOiiM0pKJe', b'0', b'1',
        b'1', b'1');

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`
(
    `id`           bigint(20)                                                   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                     NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '更新者uuid',
    `user_uuid`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '用户uuid',
    `role_uuid`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '角色uuid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_users_roles_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 26
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_cs COMMENT = '用户角色关联关系表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles`
VALUES (2, 'd74bb2c5561b4258aa79de257ac12c65', '2021-11-15 16:07:41', NULL, NULL, NULL,
        '24f14f1a400b4bed9caa8d698b33d49a', '11aa7ccc1d194afabb7a303d48b07683');
INSERT INTO `sys_users_roles`
VALUES (3, 'e47db3e2a5ee4c69ac75690084a1fe91', '2021-12-07 20:05:02', NULL, NULL, NULL,
        '76a8133381924e23a9172dac75100047', '11aa7ccc1d194afabb7a303d48b07683');
INSERT INTO `sys_users_roles`
VALUES (4, '72251feb8e4e4d57a80ac4a2f57d863b', '2021-12-07 20:05:18', NULL, NULL, NULL,
        '76a8133381924e23a9172dac75100047', '63b55b8be8fe49af82a822aa9aa57868');
INSERT INTO `sys_users_roles`
VALUES (14, '38e96d1d13b647d8b7cbbe2eefeb0db1', '2021-12-09 15:09:34', NULL, NULL, NULL,
        '1a18d9a41ff34abfa07b73340779d63e', '76a8133381924e23a9172dac75100047');
INSERT INTO `sys_users_roles`
VALUES (22, '0814a0b16fe2477cb98f56da946fef00', '2021-12-12 11:00:54', NULL, NULL, NULL,
        '76a8133381924e23a9172dac75100047', '1a18d9a41ff34abfa07b73340779d63e');
INSERT INTO `sys_users_roles`
VALUES (23, '63921375dbab47de9ed7bcf026d8be8a', '2021-12-12 11:53:40', NULL, NULL, NULL,
        'c8e93fc029d445119e0c0215ea5ee4b7', '11aa7ccc1d194afabb7a303d48b07683');
INSERT INTO `sys_users_roles`
VALUES (24, 'cc1975b546c24189bb984cdb04ab9e70', '2021-12-12 11:54:49', NULL, NULL, NULL,
        '24f14f1a400b4bed9caa8d698b33d49a', '63b55b8be8fe49af82a822aa9aa57868');
INSERT INTO `sys_users_roles`
VALUES (25, '2675a59b2a624437ac49d89430df845d', '2021-12-12 11:55:05', NULL, NULL, NULL,
        '50b4a8fd617b48babb9342bf51ae0822', '63b55b8be8fe49af82a822aa9aa57868');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '更新者uuid',
    `dict_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '字典名称',
    `dict_code`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '字典code',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_dict_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_ci COMMENT = '字典表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict`
VALUES (1, '96bf1b7a62f942988f289b2d022d6b85', '2022-01-05 21:17:40', NULL, NULL, NULL, '性别', 'sex', '');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '更新者uuid',
    `dict_uuid`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '字典uuid',
    `label`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '显示标签',
    `value`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT '值',
    `sort`         int                                                           NULL DEFAULT NULL COMMENT '显示排序',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_dict_item_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典项表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item`
VALUES (1, '117181c2616d43798a62e69f412b432f', '2022-01-05 21:20:12', NULL, NULL, NULL,
        '96bf1b7a62f942988f289b2d022d6b85', '男', '1', 0, NULL);
INSERT INTO `sys_dict_item`
VALUES (2, 'b2c8a11eb2f04a8686bbe9db9d47fe0e', '2022-01-05 21:20:27', NULL, NULL, NULL,
        '96bf1b7a62f942988f289b2d022d6b85', '女', '0', 0, NULL);
INSERT INTO `sys_dict_item`
VALUES (3, '369e43d45b2a4b98ac1ac00d7aa01bd2', '2022-01-05 21:20:41', NULL, NULL, NULL,
        '96bf1b7a62f942988f289b2d022d6b85', '保密', '2', 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
