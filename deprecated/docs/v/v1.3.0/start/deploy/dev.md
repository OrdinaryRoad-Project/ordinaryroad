# 开发环境部署

## 开发环境准备

|工具|版本|说明|
|---|---|---|
|jdk|1.8||
|MySQL|8.0+||
|Redis|||
|Node.js|||
|Maven|||
|IDEA|||

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
docker/ordinaryroad-mysql/sql/or_im_dev.sql
```

### 4. 启动Nacos

### 5. 修改数据库连接相关信息

- Redis

```
ordinaryroad-dev.yaml
```

- MySQL

```
ordinaryroad-auth-server-dev.yaml
ordinaryroad-upms-dev.yaml
```

### 6. 启动后端

直接运行下面几个类即可

- OrdinaryRoadAuthServerApp.java
- OrdinaryRoadUpmsApp.java
- OrdinaryRoadPushApp.java
- OrdinaryRoadGatewayApp.java
- OrdinaryRoadImApp.java（可选）

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