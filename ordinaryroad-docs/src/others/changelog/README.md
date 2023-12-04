# 更新日志

[GitHub Releases](https://github.com/1962247851/ordinaryroad/releases)

### 2023-12-04 v1.6.1

- fix: CDN优化
- docs: 增加indexnow统计代码
- docs: 增加公安备案信息

### 2023-09-14 v1.6.0

- perf: 图片压缩参数优化
- fix: i18n
- fix: 改正不恰当的变量名
- feat: 增加百度统计代码

### 2023-01-22 v1.5.5

- refactor: 使用[ordinaryroad-vuetify](https://github.com/1962247851/ordinaryroad-vuetify)
- fix: JSON解析操作日志时没有默认值问题
- perf: 设置loading的this.$dialog在提交时不能通过点击空白处关闭对话框

---

### 2023-01-09 v1.5.4

- feat: 增加重置密码功能
- perf: 美化OAuth2登录授权页面

---

### 2023-01-07 v1.5.3

- perf: 优化日志模块，适配文件上传Request
- perf: 文件DataTable增加默认排序（创建时间倒序）

---

### 2022-12-22 v1.5.2

- perf(权限认证): 增加用户、角色、权限缓存
- fix(日志拦截器): 捕获非json的响应体异常
- fix(日志拦截器): 修复RequestWrapper拿不到请求体的问题
- perf(日志拦截器): 支持MultipartHttpServletRequest
- refactor: 常量类的类型从class改为interface
- perf: Result.fail增加StatusCode

---

### 2022-12-05 v1.5.1

- feat(操作日志): 搜索条件增加请求路径
- refactor: no facade

---

### 2022-12-01 v1.5.0

- feat: 增加操作日志功能
- feat: DO类增加updatable = false注解
- fix: 屏蔽swagger和新springboot不兼容问题
- build: 版本升级
    - spring-boot 2.4.6->2.6.11
    - spring-cloud 2020.0.3->2021.0.4
    - spring-cloud-alibaba 2021.1->2021.0.4.0
    - mapper-spring-boot-starter 2.1.5->4.2.2
    - hutool 5.7.15->5.8.9
    - dynamic-datasource-spring-boot-starter 3.4.1->3.5.2
    - pagehelper-starter 1.4.3->1.4.5
    - sa-token 1.30.0->1.32.0
    - fastjson2 2.0.4->2.0.19
    - pagehelper 5.3.1->5.3.2