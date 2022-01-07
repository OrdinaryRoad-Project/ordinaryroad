DROP DATABASE IF EXISTS or_config_dev;
CREATE DATABASE or_config_dev;
USE or_config_dev;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`
(
    `id`           bigint(0)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL,
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `gmt_modified` datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin         NULL COMMENT 'source user',
    `src_ip`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'source ip',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT '租户字段',
    `c_desc`       varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL,
    `c_use`        varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `effect`       varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `type`         varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `c_schema`     text CHARACTER SET utf8 COLLATE utf8_bin         NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 98
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info`
VALUES (61, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 23:58:01', '2021-12-13 23:58:01', NULL, '122.193.199.20', '',
        '689f8f73-7657-4175-9a0b-2d0b88147e39', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (62, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 23:58:01', '2021-12-13 23:58:01', NULL, '122.193.199.20', '',
        '689f8f73-7657-4175-9a0b-2d0b88147e39', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (63, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 23:58:01', '2021-12-13 23:58:01', NULL, '122.193.199.20', '',
        '689f8f73-7657-4175-9a0b-2d0b88147e39', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (64, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 23:58:01', '2021-12-13 23:58:01', NULL, '122.193.199.20', '',
        '689f8f73-7657-4175-9a0b-2d0b88147e39', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (65, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:58:01', '2021-12-13 23:58:01', NULL, '122.193.199.20', '',
        '689f8f73-7657-4175-9a0b-2d0b88147e39', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (76, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', '',
        'demo', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (77, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', '',
        'demo', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (78, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:20', '2021-12-14 09:47:20', 'nacos', '202.195.159.158',
        '', 'demo', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (79, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', '',
        'demo', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (80, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', '',
        'demo', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (86, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', '',
        'dev', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (87, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', '',
        'dev', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (88, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-14 00:36:55', '2021-12-14 00:37:06', 'nacos', '122.193.199.20', '',
        'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (89, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', '',
        'dev', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info`
VALUES (90, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', '',
        'dev', '', NULL, NULL, 'yaml', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`
(
    `id`           bigint(0)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT '内容',
    `gmt_modified` datetime(0)                                      NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '增加租户字段'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`
(
    `id`           bigint(0)                                         NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NOT NULL COMMENT 'group_id',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin      NOT NULL COMMENT 'content',
    `beta_ips`     varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'betaIps',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime(0)                                       NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `gmt_modified` datetime(0)                                       NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin          NULL COMMENT 'source user',
    `src_ip`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin   NULL     DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info_beta'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`
(
    `id`           bigint(0)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `gmt_modified` datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin         NULL COMMENT 'source user',
    `src_ip`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info_tag'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint(0)                                        NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin  NULL DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint(0)                                        NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE INDEX `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_tag_relation'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`
(
    `id`                bigint(0) UNSIGNED                               NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
    `usage`             int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '使用量',
    `max_size`          int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
    `gmt_create`        datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `gmt_modified`      datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_group_id` (`group_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`
(
    `id`           bigint(0) UNSIGNED                               NOT NULL,
    `nid`          bigint(0) UNSIGNED                               NOT NULL AUTO_INCREMENT,
    `data_id`      varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `group_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `app_name`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8 COLLATE utf8_bin     NOT NULL,
    `md5`          varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `gmt_create`   datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `gmt_modified` datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
    `src_user`     text CHARACTER SET utf8 COLLATE utf8_bin         NULL,
    `src_ip`       varchar(50) CHARACTER SET utf8 COLLATE utf8_bin  NULL     DEFAULT NULL,
    `op_type`      char(10) CHARACTER SET utf8 COLLATE utf8_bin     NULL     DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL     DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`) USING BTREE,
    INDEX `idx_gmt_create` (`gmt_create`) USING BTREE,
    INDEX `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    INDEX `idx_did` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 145
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '多租户改造'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info`
VALUES (4, 33, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'sa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        '3869433fa17df354d211e0a5ac11760c', '2021-12-13 21:29:53', '2021-12-13 21:29:53', 'nacos', '61.177.142.234',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 34, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:27:44', '2021-12-13 22:27:45', 'nacos', '61.177.142.234',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 35, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 22:28:08', '2021-12-13 22:28:08', 'nacos', '61.177.142.234',
        'U', '');
INSERT INTO `his_config_info`
VALUES (0, 36, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 22:41:14', '2021-12-13 22:41:14', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 37, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 22:41:14', '2021-12-13 22:41:14', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 38, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:41:14', '2021-12-13 22:41:14', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 39, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 22:41:14', '2021-12-13 22:41:14', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 40, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 22:41:14', '2021-12-13 22:41:14', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 41, 'ordinaryroad-auth-server.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 22:43:22', '2021-12-13 22:43:23', NULL, '61.177.142.234', 'I',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (0, 42, 'ordinaryroad-upms.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 22:43:22', '2021-12-13 22:43:23', NULL, '61.177.142.234', 'I',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (0, 43, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:43:22', '2021-12-13 22:43:23', NULL, '61.177.142.234', 'I',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (0, 44, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 22:43:22', '2021-12-13 22:43:23', NULL, '61.177.142.234', 'I',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (0, 45, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 22:43:23', '2021-12-13 22:43:23', NULL, '61.177.142.234', 'I',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (32, 46, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 22:43:35', '2021-12-13 22:43:35', NULL, '61.177.142.234', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (33, 47, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 22:43:35', '2021-12-13 22:43:35', NULL, '61.177.142.234', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (34, 48, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:43:35', '2021-12-13 22:43:35', NULL, '61.177.142.234', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (35, 49, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 22:43:35', '2021-12-13 22:43:35', NULL, '61.177.142.234', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (36, 50, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 22:43:35', '2021-12-13 22:43:35', NULL, '61.177.142.234', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 51, 'ordinaryroad-auth-server.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 22:43:41', '2021-12-13 22:43:42', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 52, 'ordinaryroad-upms.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 22:43:41', '2021-12-13 22:43:42', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 53, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:43:41', '2021-12-13 22:43:42', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 54, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 22:43:41', '2021-12-13 22:43:42', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 55, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 22:43:41', '2021-12-13 22:43:42', NULL, '61.177.142.234', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (39, 56, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:43:55', '2021-12-13 22:43:55', 'nacos', '61.177.142.234',
        'U', '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (0, 57, 'ordinaryroad-auth-server.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 22:44:32', '2021-12-13 22:44:32', NULL, '61.177.142.234', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 58, 'ordinaryroad-upms.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 22:44:32', '2021-12-13 22:44:32', NULL, '61.177.142.234', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 59, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 22:44:32', '2021-12-13 22:44:32', NULL, '61.177.142.234', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 60, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 22:44:32', '2021-12-13 22:44:32', NULL, '61.177.142.234', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 61, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 22:44:32', '2021-12-13 22:44:32', NULL, '61.177.142.234', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (1, 62, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 22:44:50', '2021-12-13 22:44:51', NULL, '61.177.142.234', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (2, 63, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 22:44:50', '2021-12-13 22:44:51', NULL, '61.177.142.234', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (4, 64, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 22:44:50', '2021-12-13 22:44:51', NULL, '61.177.142.234', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (5, 65, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 22:44:50', '2021-12-13 22:44:51', NULL, '61.177.142.234', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (22, 66, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 22:44:50', '2021-12-13 22:44:51', NULL, '61.177.142.234', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (42, 67, 'ordinaryroad-auth-server.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 23:53:06', '2021-12-13 23:53:06', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (43, 68, 'ordinaryroad-upms.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 23:53:06', '2021-12-13 23:53:06', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (44, 69, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 23:53:06', '2021-12-13 23:53:06', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (45, 70, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:53:06', '2021-12-13 23:53:06', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (46, 71, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 23:53:06', '2021-12-13 23:53:06', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 72, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 23:53:31', '2021-12-13 23:53:32', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 73, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 23:53:31', '2021-12-13 23:53:32', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 74, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 23:53:31', '2021-12-13 23:53:32', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 75, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:53:31', '2021-12-13 23:53:32', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 76, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 23:53:31', '2021-12-13 23:53:32', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (50, 77, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 23:53:39', '2021-12-13 23:53:40', 'nacos', '122.193.199.20',
        'U', '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 78, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:55:05', '2021-12-13 23:55:05', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (56, 79, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:55:10', '2021-12-13 23:55:10', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 80, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:57:12', '2021-12-13 23:57:12', NULL, '122.193.199.20', 'I',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (59, 81, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:57:17', '2021-12-13 23:57:17', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (37, 82, 'ordinaryroad-auth-server.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 23:57:25', '2021-12-13 23:57:25', NULL, '122.193.199.20', 'D',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (38, 83, 'ordinaryroad-upms.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 23:57:25', '2021-12-13 23:57:25', NULL, '122.193.199.20', 'D',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (39, 84, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 23:57:25', '2021-12-13 23:57:25', NULL, '122.193.199.20', 'D',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (40, 85, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:57:25', '2021-12-13 23:57:25', NULL, '122.193.199.20', 'D',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (41, 86, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 23:57:25', '2021-12-13 23:57:25', NULL, '122.193.199.20', 'D',
        '30aeb4a5-554f-4eaf-855b-95b56c7fea3d');
INSERT INTO `his_config_info`
VALUES (48, 87, 'ordinaryroad-auth-server.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 23:57:33', '2021-12-13 23:57:33', NULL, '122.193.199.20', 'D',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (49, 88, 'ordinaryroad-upms.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 23:57:33', '2021-12-13 23:57:33', NULL, '122.193.199.20', 'D',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (50, 89, 'ordinaryroad-gateway.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-13 23:57:33', '2021-12-13 23:57:33', NULL, '122.193.199.20', 'D',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (51, 90, 'ordinaryroad.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:57:33', '2021-12-13 23:57:33', NULL, '122.193.199.20', 'D',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (52, 91, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 23:57:33', '2021-12-13 23:57:33', NULL, '122.193.199.20', 'D',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 92, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-13 23:58:00', '2021-12-13 23:58:01', NULL, '122.193.199.20', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 93, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-13 23:58:00', '2021-12-13 23:58:01', NULL, '122.193.199.20', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 94, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 23:58:00', '2021-12-13 23:58:01', NULL, '122.193.199.20', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 95, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-13 23:58:00', '2021-12-13 23:58:01', NULL, '122.193.199.20', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (0, 96, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-13 23:58:00', '2021-12-13 23:58:01', NULL, '122.193.199.20', 'I',
        '689f8f73-7657-4175-9a0b-2d0b88147e39');
INSERT INTO `his_config_info`
VALUES (55, 97, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-13 23:58:17', '2021-12-13 23:58:18', 'nacos', '122.193.199.20',
        'U', '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (55, 98, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:08:44', '2021-12-14 00:08:44', 'nacos', '122.193.199.20',
        'U', '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (0, 99, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:13:47', '2021-12-14 00:13:47', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (68, 100, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:14:03', '2021-12-14 00:14:03', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (0, 101, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:14:11', '2021-12-14 00:14:11', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (70, 102, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:16:42', '2021-12-14 00:16:42', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (0, 103, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:35:18', '2021-12-14 00:35:18', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (0, 104, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:35:18', '2021-12-14 00:35:18', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (0, 105, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:35:18', '2021-12-14 00:35:18', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (0, 106, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:35:18', '2021-12-14 00:35:18', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (0, 107, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:35:18', '2021-12-14 00:35:18', NULL, '122.193.199.20', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (0, 108, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', 'I',
        'demo');
INSERT INTO `his_config_info`
VALUES (0, 109, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', 'I',
        'demo');
INSERT INTO `his_config_info`
VALUES (0, 110, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', 'I',
        'demo');
INSERT INTO `his_config_info`
VALUES (0, 111, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', 'I',
        'demo');
INSERT INTO `his_config_info`
VALUES (0, 112, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:20', '2021-12-14 00:36:20', NULL, '122.193.199.20', 'I',
        'demo');
INSERT INTO `his_config_info`
VALUES (53, 113, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:25', '2021-12-14 00:36:25', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (54, 114, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:25', '2021-12-14 00:36:25', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (55, 115, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:25', '2021-12-14 00:36:25', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (57, 116, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:25', '2021-12-14 00:36:25', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (60, 117, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:25', '2021-12-14 00:36:25', NULL, '122.193.199.20', 'D',
        '3cf178f6-6c9e-4907-a032-4899d1563ebf');
INSERT INTO `his_config_info`
VALUES (71, 118, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:31', '2021-12-14 00:36:32', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (72, 119, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:31', '2021-12-14 00:36:32', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (73, 120, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:31', '2021-12-14 00:36:32', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (74, 121, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:31', '2021-12-14 00:36:32', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (75, 122, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:31', '2021-12-14 00:36:32', NULL, '122.193.199.20', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (0, 123, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:40', '2021-12-14 00:36:41', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 124, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:40', '2021-12-14 00:36:41', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 125, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:40', '2021-12-14 00:36:41', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 126, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:40', '2021-12-14 00:36:41', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 127, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:40', '2021-12-14 00:36:41', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (81, 128, 'ordinaryroad-auth-server-demo.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:44', '2021-12-14 00:36:44', NULL, '122.193.199.20', 'D',
        'dev');
INSERT INTO `his_config_info`
VALUES (82, 129, 'ordinaryroad-upms-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:44', '2021-12-14 00:36:44', NULL, '122.193.199.20', 'D',
        'dev');
INSERT INTO `his_config_info`
VALUES (83, 130, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:44', '2021-12-14 00:36:44', NULL, '122.193.199.20', 'D',
        'dev');
INSERT INTO `his_config_info`
VALUES (84, 131, 'ordinaryroad-push-demo.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:44', '2021-12-14 00:36:44', NULL, '122.193.199.20', 'D',
        'dev');
INSERT INTO `his_config_info`
VALUES (85, 132, 'ordinaryroad-demo.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:44', '2021-12-14 00:36:44', NULL, '122.193.199.20', 'D',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 133, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'c1fdbb9eba34b52ac25e57a84b948857', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 134, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        '36eb94c90b0e5dea328c620c4913d87b', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 135, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 136, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (0, 137, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '72a3495913db6d4a6e469ab992d24243', '2021-12-14 00:36:55', '2021-12-14 00:36:55', NULL, '122.193.199.20', 'I',
        'dev');
INSERT INTO `his_config_info`
VALUES (88, 138, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 00:37:06', '2021-12-14 00:37:06', 'nacos', '122.193.199.20',
        'U', 'dev');
INSERT INTO `his_config_info`
VALUES (78, 139, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 09:28:54', '2021-12-14 09:28:54', 'nacos', '202.195.159.158',
        'U', 'demo');
INSERT INTO `his_config_info`
VALUES (78, 140, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-14 09:30:50', '2021-12-14 09:30:50', 'nacos', '202.195.159.158',
        'U', 'demo');
INSERT INTO `his_config_info`
VALUES (78, 141, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 09:31:15', '2021-12-14 09:31:16', 'nacos', '202.195.159.158',
        'U', 'demo');
INSERT INTO `his_config_info`
VALUES (78, 142, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-14 09:32:22', '2021-12-14 09:32:22', 'nacos', '202.195.159.158',
        'U', 'demo');
INSERT INTO `his_config_info`
VALUES (78, 143, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: true\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'acc6b00f80dc76b3d1799f1181972064', '2021-12-14 09:47:03', '2021-12-14 09:47:03', 'nacos', '202.195.159.158',
        'U', 'demo');
INSERT INTO `his_config_info`
VALUES (78, 144, 'ordinaryroad-gateway-demo.yaml', 'DEFAULT_GROUP', '',
        'ordinaryroad:\n  gateway:\n    demoMode: false\n\nsa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        'c6ffc7132258ed76631653e37636010f', '2021-12-14 09:47:19', '2021-12-14 09:47:20', 'nacos', '202.195.159.158',
        'U', 'demo');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `role`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `action`   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `role`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    UNIQUE INDEX `idx_user_role` (`username`, `role`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint(0) UNSIGNED                               NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
    `usage`             int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '使用量',
    `max_size`          int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int(0) UNSIGNED                                  NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
    `gmt_create`        datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `gmt_modified`      datetime(0)                                      NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '租户容量信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`
(
    `id`            bigint(0)                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin  NULL DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint(0)                                        NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint(0)                                        NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_tenant_info_kptenantid` (`kp`, `tenant_id`) USING BTREE,
    INDEX `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'tenant_info'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info`
VALUES (4, '1', 'demo', 'demo', '演示环境', 'nacos', 1639413350299, 1639413350299);
INSERT INTO `tenant_info`
VALUES (5, '1', 'dev', 'dev', '开发环境', 'nacos', 1639413364335, 1639413364335);
INSERT INTO `tenant_info`
VALUES (6, '1', 'pro', 'pro', '生产环境', 'nacos', 1639413372280, 1639413372280);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `enabled`  tinyint(1)                                                    NOT NULL,
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
