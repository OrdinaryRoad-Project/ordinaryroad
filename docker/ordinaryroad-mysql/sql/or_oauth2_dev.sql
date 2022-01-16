DROP DATABASE IF EXISTS or_oauth2_dev;
CREATE DATABASE or_oauth2_dev;
USE or_oauth2_dev;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_openid
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_openid`;
CREATE TABLE `oauth2_openid`
(
    `id`           bigint(20)                                                    NOT NULL AUTO_INCREMENT,
    `uuid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT '主键UUID',
    `created_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`  datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新者uuid',
    `or_number`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'or帐号',
    `client_id`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT 'clientId',
    `openid`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT 'openid',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `oauth2_openid_openid_uindex` (`openid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '不同客户端下用户openid表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_openid
-- ----------------------------
INSERT INTO `oauth2_openid`
VALUES (1, '53b4726eef3d42c7ae2b2cdcb59cca45', NULL, NULL, NULL, NULL, '10001', 'ordinaryroad-gateway',
        '32_4hqkt8pr9vo8pq_t9m7k93rc825qook__');
INSERT INTO `oauth2_openid`
VALUES (2, 'd4efb17fbc614ade8deaaf5fdd4ded8e', NULL, NULL, NULL, NULL, '10002', 'ordinaryroad-gateway',
        'j5_86p60kbg9n82rt_9a24sjt6kop3u6sq__');

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`
(
    `id`            bigint(20)                                                     NOT NULL AUTO_INCREMENT,
    `uuid`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs   NOT NULL COMMENT '主键UUID',
    `created_time`  datetime                                                       NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '创建者uuid',
    `update_time`   datetime                                                       NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NULL DEFAULT NULL COMMENT '更新者uuid',
    `client_id`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs   NOT NULL COMMENT 'clientId',
    `client_secret` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NULL DEFAULT NULL COMMENT 'clientSecret',
    `client_name`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs  NOT NULL COMMENT 'clientName',
    `redirect_uris` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NULL DEFAULT NULL COMMENT '重定向链接',
    `scopes`        varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL COMMENT '授权范围',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `oauth2_registered_client_client_id_uindex` (`client_id`) USING BTREE,
    UNIQUE INDEX `oauth2_registered_client_client_name_uindex` (`client_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '注册的客户端表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client`
VALUES (1, '0df05654c005443ba18566b9aa98499f', '2021-11-15 10:56:25', NULL, NULL, NULL, 'ordinaryroad-gateway',
        'secret', '网关', 'https://ordinaryroad.tech:8090/authorized', 'openid,userinfo');
INSERT INTO `oauth2_registered_client`
VALUES (2, '65454fd95c3940a1a03acbe7f0cf4d42', '2021-11-15 12:09:51', NULL, NULL, NULL, 'ordinaryroad-knife', 'secret',
        'Knife', 'https://ordinaryroad.tech:8090/webjars/oauth/oauth2.html', 'read,write,reads,writes');
INSERT INTO `oauth2_registered_client`
VALUES (3, 'de9fac5117b34778a8ee8039dc296c09', '2022-01-15 15:49:35', NULL, NULL, NULL, 'ordinaryroad-ui', 'secret',
        'ui模块', '', '');

SET FOREIGN_KEY_CHECKS = 1;
