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
  AUTO_INCREMENT = 29
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'config_info'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info`
VALUES (1, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        '4df68b481fc564de14c4e22e4ee956b2', '2021-10-29 02:32:25', '2021-11-22 02:51:37', 'nacos', '0:0:0:0:0:0:0:1',
        '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (2, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        'd6422f6229503fccf6f24d2f8353ad9e', '2021-10-29 02:33:10', '2021-11-23 01:23:42', 'nacos', '0:0:0:0:0:0:0:1',
        '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (4, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP',
        'sa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        '3869433fa17df354d211e0a5ac11760c', '2021-10-29 05:56:28', '2021-11-28 06:36:49', 'nacos', '0:0:0:0:0:0:0:1',
        '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (5, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: smtp.qq.com\n    #发送者的邮件的用户名\n    username: 1962247851@qq.com\n    #移动端客户授权码(在邮箱中设置)\n    password: ${QQ_MAIL_AUTHORIZATION_CODE}\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        'ca2f2de1a2dc0d1559f3cfd234e69b1d', '2021-10-29 06:39:33', '2021-11-28 05:03:08', 'nacos', '0:0:0:0:0:0:0:1',
        '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info`
VALUES (22, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-11-27 13:34:16', '2021-11-27 13:34:16', NULL, '0:0:0:0:0:0:0:1', '',
        '', NULL, NULL, NULL, 'yaml', NULL);

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
  AUTO_INCREMENT = 33
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = '多租户改造'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info`
VALUES (1, 11, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }',
        '109236c62143573a51692af171f41ca4', '2021-11-01 15:38:28', '2021-11-01 07:38:28', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (5, 12, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\r\n  client:\r\n    config:\r\n      default:\r\n        loggerLevel: BASIC',
        'def4bd1065429cb15e4f546dbbd69fcd', '2021-11-22 10:25:26', '2021-11-22 02:25:26', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (1, 13, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\n# Tomcat\nserver:\n  port: 9302\nspring:\n  application:\n    # 应用名称\n    name: ordinaryroad-auth-server\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: 127.0.0.1\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '55b0819d050123bebd25ff73fe69c730', '2021-11-22 10:39:53', '2021-11-22 02:39:54', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (2, 14, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  # 数据库配置\r\n  datasource:\r\n    dynamic:\r\n      datasource:\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://ordinaryroad-mysql:3307/ordinaryroad_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\r\n          username: root\r\n          password: Gpt8GB9RlsPseeoy\r\n  # 出现错误时, 直接抛出异常\r\n  mvc:\r\n    throw-exception-if-no-handler-found: true\r\n  web:\r\n    resources:\r\n      add-mappings: false\r\n# swagger配置\r\nswagger:\r\n  title: 系统模块接口文档\r\n  license: Powered By OrdinaryRoad\r\n  licenseUrl: https://ordinaryroad.top',
        'd8b14d388ca1302847ed0bbae2ebacae', '2021-11-22 10:47:10', '2021-11-22 02:47:10', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (1, 15, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        '# Tomcat\nserver:\n  port: 9302\n  devtools:\n  livereload:\n    port: 39302\nspring:\n  application:\n    # 应用名称\n    name: ordinaryroad-auth-server\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        '7af548f430d540030e0b3aa9eec5e34d', '2021-11-22 10:47:39', '2021-11-22 02:47:40', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 16, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        '# Tomcat\nserver:\n  port: 9090\nspring:\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway',
        '2272d8d187d81d46443422ac116ab9f4', '2021-11-22 10:49:26', '2021-11-22 02:49:27', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 17, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'sa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\n# Tomcat\nserver:\n  port: 9090\nspring:\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway',
        '892ed39adda89eb5b0f068400f65e37f', '2021-11-22 10:50:23', '2021-11-22 02:50:24', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 18, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'sa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\n# Tomcat\nserver:\n  port: 9090\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1',
        '325b21dd6282d35b8abc900e9f287b11', '2021-11-22 10:50:45', '2021-11-22 02:50:45', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (1, 19, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        '# Tomcat\nserver:\n  port: 9302\nspring:\n  devtools:\n    livereload:\n      port: 39302\n  application:\n    # 应用名称\n    name: ordinaryroad-auth-server\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        'fafdf4f27866f8827cb083a2301906d1', '2021-11-22 10:51:06', '2021-11-22 02:51:06', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (1, 20, 'ordinaryroad-auth-server-dev.yaml', 'DEFAULT_GROUP', '',
        'spring:\n  devtools:\n    livereload:\n      port: 39302\n  application:\n    # 应用名称\n    name: ordinaryroad-auth-server\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/or_oauth2_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy',
        '46e17a75456906def9430e1106361fa6', '2021-11-22 10:51:37', '2021-11-22 02:51:37', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (2, 21, 'ordinaryroad-upms-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\ndebug: true\nlogging:\n  level: { tech.ordinaryroad: debug }\nspring:\n  devtools:\n    livereload:\n      port: 39401\n  # 数据库配置\n  datasource:\n    dynamic:\n      datasource:\n        master:\n          driver-class-name: com.mysql.cj.jdbc.Driver\n          url: jdbc:mysql://ordinaryroad-mysql:3307/ordinaryroad_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true\n          username: root\n          password: Gpt8GB9RlsPseeoy\n  # 出现错误时, 直接抛出异常\n  mvc:\n    throw-exception-if-no-handler-found: true\n  web:\n    resources:\n      add-mappings: false\n# swagger配置\nswagger:\n  title: 系统模块接口文档\n  license: Powered By OrdinaryRoad\n  licenseUrl: https://ordinaryroad.top',
        'e842e6f1c8152cf6e63e7d6c7be1800f', '2021-11-23 09:23:42', '2021-11-23 01:23:42', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (0, 22, 'ordinaryroad-push', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-11-27 21:33:17', '2021-11-27 13:33:17', NULL, '0:0:0:0:0:0:0:1', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (20, 23, 'ordinaryroad-push', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-11-27 21:33:39', '2021-11-27 13:33:39', NULL, '0:0:0:0:0:0:0:1', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (0, 24, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-11-27 21:34:05', '2021-11-27 13:34:05', NULL, '0:0:0:0:0:0:0:1', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (0, 25, 'ordinaryroad-push-dev.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-11-27 21:34:15', '2021-11-27 13:34:16', NULL, '0:0:0:0:0:0:0:1', 'I',
        '');
INSERT INTO `his_config_info`
VALUES (21, 26, 'ordinaryroad-push.yaml', 'DEFAULT_GROUP', '',
        '# 打印Debug日志\r\ndebug: true\r\nlogging:\r\n  level: { tech.ordinaryroad: debug }\r\nspring:\r\n  devtools:\r\n    livereload:\r\n      port: 39402',
        'f10f60b783d4d0522a6cef05eb9599be', '2021-11-27 21:34:20', '2021-11-27 13:34:20', NULL, '0:0:0:0:0:0:0:1', 'D',
        '');
INSERT INTO `his_config_info`
VALUES (5, 27, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        'a0e82d05bfa14836069eb86fb6e64170', '2021-11-28 11:05:36', '2021-11-28 03:05:36', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (5, 28, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    host: mail.ordinaryroad.tech\n    port: 25\n    username: system@ordinaryroad.tech\n    password: NU0Z8k5jPLY9RgOb\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        'a616c3d1bdb3e9eff2eb441c48cd8ab0', '2021-11-28 11:26:27', '2021-11-28 03:26:28', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (5, 29, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: mail.ordinaryroad.tech\n    #发送者的邮件的用户名\n    username: system@ordinaryroad.tech\n    #移动端客户授权码(在邮箱中设置)\n    password: NU0Z8k5jPLY9RgOb\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail.smtp.auth: true\n      mail.smtp.starttls.enable: true\n      mail.smtp.starttls.required: true\n      mail.smtp.socketFactory.port: 465\n      mail.smtp.socketFactory.class: javax.net.ssl.SSLSocketFactory\n      mail.smtp.socketFactory.fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        'f47a1df6211b7acd094e2054ec4fc4d9', '2021-11-28 12:47:54', '2021-11-28 04:47:55', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (5, 30, 'ordinaryroad-dev.yaml', 'DEFAULT_GROUP', '',
        'feign:\n  client:\n    config:\n      default:\n        loggerLevel: BASIC\nspring:\n  # 邮箱配置\n  mail:\n    port: 465\n    #邮件协议smtp\n    host: mail.ordinaryroad.tech\n    #发送者的邮件的用户名\n    username: system@ordinaryroad.tech\n    #移动端客户授权码(在邮箱中设置)\n    password: NU0Z8k5jPLY9RgOb\n    #使用的编码\n    default-encoding: utf-8\n    properties:\n      mail:\n        smtp:\n          auth: true\n          starttls:\n            enable: true\n            required: true\n          ssl:\n            enable: true\n          socketFactory:\n            port: 465\n            class: javax.net.ssl.SSLSocketFactory\n            fallback: false\n  # redis配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: ordinaryroad-redis\n    # Redis服务器连接端口\n    port: 6379\n    # Redis服务器连接密码（默认为空）\n    password:\n    # 连接超时时间（毫秒）\n    timeout: 10000ms\n    lettuce:\n      pool:\n        # 连接池最大连接数\n        max-active: 200\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n        # 连接池中的最大空闲连接\n        max-idle: 10\n        # 连接池中的最小空闲连接\n        min-idle: 0',
        '809786ba550642cdcf96018f228ad238', '2021-11-28 13:03:07', '2021-11-28 05:03:08', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 31, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'sa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1',
        '91215451faff4f5d415742744065f71d', '2021-11-28 14:33:54', '2021-11-28 06:33:55', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');
INSERT INTO `his_config_info`
VALUES (4, 32, 'ordinaryroad-gateway-dev.yaml', 'DEFAULT_GROUP', '',
        'sa-token:\n  oauth2:\n    auth-server-host: lb://ordinaryroad-auth-server/oauth2\n\nspring:\n  devtools:\n    livereload:\n      port: 39090\n  application:\n    # 应用名称\n    name: ordinaryroad-gateway\n  cloud:\n    gateway:\n      routes:\n        - id: ordinaryroad-gateway\n          uri: lb://ordinaryroad-gateway\n          predicates:\n            - Path=/**\n          filters:\n            - ValidateCodeFilter\n        - id: ordinaryroad-auth-server\n          uri: lb://ordinaryroad-auth-server\n          predicates:\n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-upms\n          uri: lb://ordinaryroad-upms\n          predicates:\n            - Path=/upms/**\n          filters:\n            - StripPrefix=1\n        - id: ordinaryroad-push\n          uri: lb://ordinaryroad-push\n          predicates:\n            - Path=/push/**\n          filters:\n            - StripPrefix=1',
        '225fafb1d98a4786f23862a8528cc605', '2021-11-28 14:36:49', '2021-11-28 06:36:49', 'nacos', '0:0:0:0:0:0:0:1',
        'U', '');

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
  CHARACTER SET = utf8
  COLLATE = utf8_bin COMMENT = 'tenant_info'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

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
