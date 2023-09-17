# OrdinaryRoad博客

Frontend: [![构建状态](https://ordinaryroad.coding.net/badges/ordinaryroad/job/1483779/build.svg)](https://ordinaryroad.coding.net/p/ordinaryroad/ci/job)
Backend: [![构建状态](https://ordinaryroad.coding.net/badges/ordinaryroad/job/1495289/build.svg "Backend")](https://ordinaryroad.coding.net/p/ordinaryroad/ci/job)

项目原型为[我的个人博客](https://blog.ordinaryroad.top)，使用Quarkus框架重构，一个MD风格的Markdown多人博客。

地址：[https://blog.ordinaryroad.tech](https://blog.ordinaryroad.tech)。

## 界面

- 支持跟随系统日夜间模式
- Material Design
- Markdown
- 瀑布流

## 功能

### 用户模块

- 第三方账号
- 账号临时封禁、永久封禁、解封
- 个人空间
- 用户角色分配
- 角色用户管理
- 角色启用禁用

### 文章模块

- Markdown
- 草稿箱、垃圾箱
- 文章历史版本
- 文章发布前审核
- 发布后随机审核
- 文章违规申诉

### 分类模块

- 自建分类

### 标签模块

- 公共标签

### 评论模块

- 评论文章
- 回复评论
- 评论删除
- [ ] 评论敏感词校验

### 日志模块

- 请求
- 响应
- 耗时

### 搜索模块

- 文章搜索
- 分类搜索
- 用户搜索

### 数据统计模块

- 文章UV、PV
- 文章点赞量
- 发布文章数
- 点赞文章数
- 分类数
- 评论发表数
- 时间段内有文章发布的日期/月份/年份
- 时间段内每天/每月/每年文章发布数
- 分类文章数Top10
- 文章评论数Top10
- 文章点赞数Top10
- 文章浏览数Top10

### 友链模块

- 手动添加
- 主动申请
- 申请结果通知
- 友链状态变更通知
- 悬浮预览友链快照

### 其他

- 国际化

## 框架

- Quarkus
- MyBatis Plus
- SaToken
- Nuxt.js