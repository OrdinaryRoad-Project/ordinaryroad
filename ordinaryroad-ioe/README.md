### 登录流程

1. 先注册第三方客户端
2. 安卓端构建OAuth2授权链接
3. 认证服务器重定向到注册时填的redirect_uri
4. 接收code，换取access_token，获取Openid
5. 使用Openid登录IoE模块
6. 根据Openid判断IoE模块是否存在用户
7. 未存在则先使用access_token换取userinfo，拿到email、username，创建Customer、创建User、激活User、创建IoE用户
8. 已存在则直接将access_token和satoken和state传回authorized.html
9. 网页构建打开APP的URI，提前在APP中设置好
10. APP接收state、access_token和satoken
11. APP先判断state是否一致，不一致/access_token或satoken为空则提示登录失败
12. APP使用access_token向OAuth2服务器换取userinfo，保存用户信息
13. APP保存用于访问IoE模块的satoken
14. APP端登录完成

#### APP端添加设备流程简单版本

APP端输入设备Id，直接访问添加设备接口

IoE判断设备是否存在，设备是否已分配给其他人，记录是否存在

插入一条openid和deviceId关联记录，返回设备信息

### APP端添加设备流程

#### 租户管理员

1. 租户管理员先创建设备

#### 设备

1. 设备开机先联网、然后连接MQTT服务器、订阅Topic
2. 请求Customer信息，未收到则订阅和自身Id相关的Topic，进入配对流程
3. 收到配对请求时，publish一条消息

#### APP

1. APP打开添加设备界面，输入设备ID，获取Customer信息
2. 没有Customer信息则向和设备Id相关的Topic发送一条消息
3. 订阅配对响应Topic
4. 收到响应后判断state和发送的是否一致，一致则