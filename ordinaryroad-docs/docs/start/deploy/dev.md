# 开发环境部署

## 开发环境准备

| 工具      | 版本     | 说明                               |
|---------|--------|----------------------------------|
| jdk     | 1.8/11 | 使用 `commons-thingsboard` 模块时需要11 |
| MySQL   | 8.0+   ||
| Redis   |||
| Nacos   |||
| Node.js |||
| Maven   |||
| IDEA    |||

## 项目构建与部署

### 1. 代码下载

```shell
git clone https://github.com/1962247851/ordinaryroad.git
```

### 2. 配置本地host

推荐使用[SwitchHosts](https://github.com/oldj/SwitchHosts)

```text
# ordinaryroad Start
127.0.0.1   ordinaryroad-mysql
127.0.0.1   ordinaryroad-redis
127.0.0.1   ordinaryroad-nacos
127.0.0.1   ordinaryroad-gateway
127.0.0.1   ordinaryroad-auth-server
127.0.0.1   ordinaryroad-upms
127.0.0.1   ordinaryroad-push
127.0.0.1   ordinaryroad-im
# ordinaryroad End
```

### 3. 数据库初始化

- Nacos数据库

```
docker/ordinaryroad-mysql/sql/or_config_dev.sql
```

- OrdinaryRoad数据库

```
docker/ordinaryroad-mysql/sql/or_dev.sql
docker/ordinaryroad-mysql/sql/or_oauth2_dev.sql
docker/ordinaryroad-mysql/sql/or_commons_log_dev.sql
```

### 4. 启动Nacos

需要提前设置好数据库等信息

> [Nacos 快速开始](https://nacos.io/zh-cn/docs/quick-start.html)

使用Docker启动
> 注意版本号`v2.1.2-slim`后缀`-slim`为arm架构处理器需要

```shell
docker run --name or-nacos-dev \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=xxxxxx \
-e MYSQL_SERVICE_DB_NAME=or_config_dev \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=Root123. \
-e MYSQL_SERVICE_DB_PARAM='characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true' \
-p 8848:8848 -p 9848:9848 -p 9555:9555 \
-d nacos/nacos-server:v2.1.2-slim
```

### 5. 修改数据库连接相关信息

- Redis

```
ordinaryroad-dev.yaml
```

- MySQL

```
ordinaryroad-dev.yaml
ordinaryroad-auth-server-dev.yaml
ordinaryroad-upms-dev.yaml
```

### 6. 启动后端

直接运行下面几个类即可

- OrdinaryRoadAuthServerApp.java
- OrdinaryRoadUpmsApp.java
- OrdinaryRoadPushApp.java
- OrdinaryRoadGatewayApp.java

### 7. 启动前端

先在 `ordinaryroad-ui` 目录下创建 `.env` 文件，内容如下

```dotenv
# https://www.nuxtjs.cn/guide/runtime-config#env-support
BASE_URL=http://ordinaryroad-gateway:9090
```

安装依赖

```shell
# cd ordinaryroad-ui
npm install
```

然后启动

```shell
# cd ordinaryroad-ui
npm run dev
```

## 系统账号说明

|模块|地址|账号信息|说明|
|---|---|---|---|
|前端|http://localhost:3001|10001/Abc123||
|Knife|http://ordinaryroad-gateway:9090/doc.html|ordinaryroad-knife/secret|OAuth2认证，参考Knife文档https://doc.xiaominfo.com/knife4j/documentation/oauth2.html|
|Nacos|http://localhost:8848|nacos/nacos||