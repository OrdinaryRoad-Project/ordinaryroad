# LiveChatClient弹幕客户端

![license](https://img.shields.io/github/license/OrdinaryRoad-Project/ordinaryroad-live-chat-client) ![release](https://img.shields.io/github/v/release/OrdinaryRoad-Project/ordinaryroad-live-chat-client) ![Maven Central](https://img.shields.io/maven-central/v/tech.ordinaryroad/live-chat-client)

> This project is in progress... 👨‍💻
>
> 有问题欢迎提交[issuse](https://github.com/OrdinaryRoad-Project/ordinaryroad-live-chat-client/issues)，
> 觉得有用的话可以点个小星星⭐️鼓励一下，感谢
>
> 如果对项目感兴趣也欢迎[交流讨论](https://github.com/OrdinaryRoad-Project/ordinaryroad-live-chat-client/discussions)，
> 提交PR
>
> ToDo List: https://github.com/orgs/OrdinaryRoad-Project/projects/1

- [x] Bilibili
  - [x] BilibiliLiveChatClient
  - [x] 支持 cookie
  - [x] 支持 短房间id
  - [x] 支持 弹幕发送
- [x] Douyu
  - [x] DouyuLiveChatClient
  - [x] 支持 cookie
  - [x] 支持 短房间id
  - [x] 支持 弹幕发送

- [ ] Huya
- [ ] ...

---

Live room WebSocket chat client

- Feature 0: Netty
- Feature 1: 消息中的未知属性统一放到单独的MAP中
- Feature 2: 支持自动重连
- Feature 3: 支持同时监听多个直播间
- Feature 4: 支持直播间短id
- Feature 5: 支持弹幕发送
- Feature 6: 内置收到弹幕、收到礼物回调

## 1 安装

### B站

```xml

<dependency>
    <groupId>tech.ordinaryroad</groupId>
    <artifactId>live-chat-client-bilibili</artifactId>
    <!-- 参考github release版本，不需要前缀`v` -->
    <version>${ordinaryroad-live-chat-client.version}</version>
</dependency>
```  

### 斗鱼

```xml

<dependency>
    <groupId>tech.ordinaryroad</groupId>
    <artifactId>live-chat-client-douyu</artifactId>
    <!-- 参考github release版本，不需要前缀`v` -->
    <version>${ordinaryroad-live-chat-client.version}</version>
</dependency>
```

## 2 使用

> 测试类包含了多种样例，可供参考

### 2.1 Client模式

> Spring Boot 3 示例 [client-example](https://github.com/OrdinaryRoad-Project/ordinaryroad-live-chat-client/tree/main/live-chat-client-examples/client-example)

```java
public class ClientModeExample {
    public static void main(String[] args) {
        String cookie = System.getenv("cookie");
        BilibiliLiveChatClientConfig config = BilibiliLiveChatClientConfig.builder()
                // TODO 浏览器Cookie
                .cookie(cookie)
                // TODO 直播间id（支持短id）
                .roomId(7777)
                .build();

        BilibiliLiveChatClient client = new BilibiliLiveChatClient(config, new IBilibiliMsgListener() {
            @Override
            public void onDanmuMsg(BilibiliBinaryFrameHandler binaryFrameHandler, DanmuMsgMsg msg) {
                IBilibiliMsgListener.super.onDanmuMsg(binaryFrameHandler, msg);

                System.out.printf("%s 收到弹幕 %s(%d)：%s%n", binaryFrameHandler.getRoomId(), msg.getUsername(), msg.getUid(), msg.getContent());
            }

            @Override
            public void onGiftMsg(BilibiliBinaryFrameHandler binaryFrameHandler, SendGiftMsg msg) {
                IBilibiliMsgListener.super.onGiftMsg(binaryFrameHandler, msg);
             
                log.info("{} 收到礼物 {}({}) {} {}({})x{}({})", binaryFrameHandler.getRoomId(), msg.getUsername(), msg.getUid(), msg.getData().getAction(), msg.getGiftName(), msg.getGiftId(), msg.getGiftCount(), msg.getGiftPrice());
            }
        });
        client.connect();

        client.addStatusChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue().equals(ClientStatusEnums.CONNECTED)) {
                    // TODO 要发送的弹幕内容，请注意控制发送频率；框架内置支持设置发送弹幕的最少时间间隔，小于时将忽略该次发送
                    client.sendDanmu("666666" + RandomUtil.randomNumbers(1));
                }
            }
        });
    }
}
```

### 2.2 高级模式

> 参考 [BilibiliHandlerModeExample](https://github.com/OrdinaryRoad-Project/ordinaryroad-live-chat-client/tree/main/live-chat-client-examples/handler-example/src/main/java/tech/ordinaryroad/live/chat/client/example/handler/BilibiliHandlerModeExample.java)

## 3 项目说明
- commons（主要是抽象接口、抽象类的定义）
  - commons-base
    - 定义了一些基础的接口、抽象类：消息、消息监听器、连接连监听器
    - 消息
      - msg：收到的所有msg
        - cmdMsg：有些平台的一些消息正文中没有消息类型cmd字段，例如B站的心跳包，因此再细分为cmdMsg
      - IDanmuMsg: 内置用户名、弹幕内容等方法
      - IGiftMsg：内置发送方、接收方、礼物ID、礼物个数等
    - 消息监听器
      - onDanmuMsg：收到弹幕消息
      - onGiftMsg：收到礼物消息
      - onMsg：所有消息（不管消息内容）都会调用
      - onCmdMsg：cmd消息（消息体中有表示消息类型的字段时），并且该类型需要处理（例如心跳回复包不需要处理）时调用
      - onOtherCmdMsg：该消息类型不需要处理（例如PK、点赞数更新等类型）时调用
      - onUnknownCmd：该消息类型未知（没有对应的枚举类）时调用
  - commons-client
    - 定义了Client的配置：连接地址、房间id、Cookie、心跳、自动重连等相关参数
    - 定义了Client的一些方法：初始化、连接、断开、发送消息等
    - 定义了Client的生命周期
  - commons-util
    - 一些工具类：时间、反射、Cookie
- servers（对使用的连接工具的抽象）
  - servers-netty
    - 定义了连接处理Handler
    - 定义了数据处理Handler
  - servers-netty-client（基于netty实现的Client）
    - 扩展了Client、ClientConfig
    - 扩展Handler增加了Client成员变量
- clients（对servers-netty-client的具体实现）
  - client-bilibili
  - client-douyu

## 感谢以下开源项目
- [douyu-crawler-demo](https://github.com/cj1128/douyu-crawler-demo)（登录状态的请求包构建）
