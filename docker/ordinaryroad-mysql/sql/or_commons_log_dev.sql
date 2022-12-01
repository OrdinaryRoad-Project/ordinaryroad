/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE if exists `operation_log`;
CREATE TABLE `operation_log`
(
    `id`               bigint(20)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `uuid`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci   NOT NULL COMMENT '主键UUID',
    `created_time`     datetime                                                       NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci   NULL     DEFAULT NULL COMMENT '创建者ID',
    `update_time`      datetime                                                       NULL     DEFAULT NULL COMMENT '更新时间',
    `update_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci   NULL     DEFAULT NULL COMMENT '更新者ID',

    `ip`               varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL COMMENT 'IP',
    `path`             varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL COMMENT '请求路径',
    `method`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci   NULL COMMENT '请求方法',
    `headers`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci          NULL COMMENT '请求头',
    `cookies`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci          NULL COMMENT '请求Cookie',
    `path_params`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci          NULL COMMENT '路径参数',
    `query_params`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci          NULL COMMENT '查询参数',
    `request`          mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci    NULL COMMENT '请求体',
    `status`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci  NULL COMMENT '响应状态',
    `response_headers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci          NULL COMMENT '响应头',
    `response_cookies` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci          NULL COMMENT '响应Cookie',
    `response`         mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci    NULL COMMENT '响应体',
    `consumed_time`    bigint(10)                                                     NULL COMMENT '耗时',
    `type`             int(10)                                                        NULL COMMENT '类型',
    `deleted`          bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `or_operation_log_uuid_uindex` (`uuid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_as_ci COMMENT = 'OR操作日志表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
