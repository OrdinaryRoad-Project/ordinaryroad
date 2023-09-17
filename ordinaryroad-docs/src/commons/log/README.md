# 日志模块

![1.png](/assets/image/commons-log/1.png)

![2.png](/assets/image/commons-log/2.png)

![3.png](/assets/image/commons-log/3.png)

## 功能

- 打印DEBUG级别操作日志
- 操作日志入库
- 仿Safari操作日志查询界面

## 实现方式

1. 拦截器封装Request和Response，使支持多次读取body
2. 过滤器记录请求和响应，并入库
