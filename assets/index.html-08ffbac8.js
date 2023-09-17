import{_ as r}from"./plugin-vue_export-helper-c27b6911.js";import{r as d,o as i,c as l,a,b as e,d as s,e as t}from"./app-9f49f69b.js";const o={},c=t(`<h1 id="开发环境部署" tabindex="-1"><a class="header-anchor" href="#开发环境部署" aria-hidden="true">#</a> 开发环境部署</h1><h2 id="开发环境准备" tabindex="-1"><a class="header-anchor" href="#开发环境准备" aria-hidden="true">#</a> 开发环境准备</h2><table><thead><tr><th>工具</th><th>版本</th><th>说明</th></tr></thead><tbody><tr><td>jdk</td><td>1.8/11</td><td>使用 <code>commons-thingsboard</code> 模块时需要11</td></tr><tr><td>MySQL</td><td>8.0+</td><td></td></tr><tr><td>Redis</td><td></td><td></td></tr><tr><td>Nacos</td><td></td><td></td></tr><tr><td>Node.js</td><td></td><td></td></tr><tr><td>Maven</td><td></td><td></td></tr><tr><td>IDEA</td><td></td><td></td></tr></tbody></table><h2 id="项目构建与部署" tabindex="-1"><a class="header-anchor" href="#项目构建与部署" aria-hidden="true">#</a> 项目构建与部署</h2><h3 id="_1-代码下载" tabindex="-1"><a class="header-anchor" href="#_1-代码下载" aria-hidden="true">#</a> 1. 代码下载</h3><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token function">git</span> clone https://github.com/1962247851/ordinaryroad.git
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h3 id="_2-配置本地host" tabindex="-1"><a class="header-anchor" href="#_2-配置本地host" aria-hidden="true">#</a> 2. 配置本地host</h3>`,7),p={href:"https://github.com/oldj/SwitchHosts",target:"_blank",rel:"noopener noreferrer"},u=t(`<div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code># ordinaryroad Start
127.0.0.1   ordinaryroad-mysql
127.0.0.1   ordinaryroad-redis
127.0.0.1   ordinaryroad-nacos
127.0.0.1   ordinaryroad-gateway
127.0.0.1   ordinaryroad-auth-server
127.0.0.1   ordinaryroad-upms
127.0.0.1   ordinaryroad-push
127.0.0.1   ordinaryroad-im
# ordinaryroad End
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="_3-数据库初始化" tabindex="-1"><a class="header-anchor" href="#_3-数据库初始化" aria-hidden="true">#</a> 3. 数据库初始化</h3><ul><li>Nacos数据库</li></ul><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>docker/ordinaryroad-mysql/sql/or_config_dev.sql
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><ul><li>OrdinaryRoad数据库</li></ul><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>docker/ordinaryroad-mysql/sql/or_dev.sql
docker/ordinaryroad-mysql/sql/or_oauth2_dev.sql
docker/ordinaryroad-mysql/sql/or_commons_log_dev.sql
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="_4-启动nacos" tabindex="-1"><a class="header-anchor" href="#_4-启动nacos" aria-hidden="true">#</a> 4. 启动Nacos</h3><p>需要提前设置好数据库等信息</p>`,8),h={href:"https://nacos.io/zh-cn/docs/quick-start.html",target:"_blank",rel:"noopener noreferrer"},v=t(`<p>使用Docker启动</p><blockquote><p>注意版本号<code>v2.1.2-slim</code>后缀<code>-slim</code>为arm架构处理器需要</p></blockquote><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token function">docker</span> run <span class="token parameter variable">--name</span> or-nacos-dev <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MODE</span><span class="token operator">=</span>standalone <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">SPRING_DATASOURCE_PLATFORM</span><span class="token operator">=</span>mysql <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MYSQL_SERVICE_HOST</span><span class="token operator">=</span>xxxxxx <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MYSQL_SERVICE_DB_NAME</span><span class="token operator">=</span>or_config_dev <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MYSQL_SERVICE_PORT</span><span class="token operator">=</span><span class="token number">3306</span> <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MYSQL_SERVICE_USER</span><span class="token operator">=</span>root <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MYSQL_SERVICE_PASSWORD</span><span class="token operator">=</span>Root123. <span class="token punctuation">\\</span>
<span class="token parameter variable">-e</span> <span class="token assign-left variable">MYSQL_SERVICE_DB_PARAM</span><span class="token operator">=</span><span class="token string">&#39;characterEncoding=utf8&amp;connectTimeout=1000&amp;socketTimeout=3000&amp;autoReconnect=true&amp;useSSL=false&amp;allowPublicKeyRetrieval=true&#39;</span> <span class="token punctuation">\\</span>
<span class="token parameter variable">-p</span> <span class="token number">8848</span>:8848 <span class="token parameter variable">-p</span> <span class="token number">9848</span>:9848 <span class="token parameter variable">-p</span> <span class="token number">9555</span>:9555 <span class="token punctuation">\\</span>
<span class="token parameter variable">-d</span> nacos/nacos-server:v2.1.2-slim
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="_5-修改数据库连接相关信息" tabindex="-1"><a class="header-anchor" href="#_5-修改数据库连接相关信息" aria-hidden="true">#</a> 5. 修改数据库连接相关信息</h3><ul><li>Redis</li></ul><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>ordinaryroad-dev.yaml
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><ul><li>MySQL</li></ul><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>ordinaryroad-dev.yaml
ordinaryroad-auth-server-dev.yaml
ordinaryroad-upms-dev.yaml
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="_6-启动后端" tabindex="-1"><a class="header-anchor" href="#_6-启动后端" aria-hidden="true">#</a> 6. 启动后端</h3><p>直接运行下面几个类即可</p><ul><li>OrdinaryRoadAuthServerApp.java</li><li>OrdinaryRoadUpmsApp.java</li><li>OrdinaryRoadPushApp.java</li><li>OrdinaryRoadGatewayApp.java</li></ul><h3 id="_7-启动前端" tabindex="-1"><a class="header-anchor" href="#_7-启动前端" aria-hidden="true">#</a> 7. 启动前端</h3><p>先在 <code>ordinaryroad-ui</code> 目录下创建 <code>.env</code> 文件，内容如下</p><div class="language-dotenv line-numbers-mode" data-ext="dotenv"><pre class="language-dotenv"><code># https://www.nuxtjs.cn/guide/runtime-config#env-support
BASE_URL=http://ordinaryroad-gateway:9090
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><p>安装依赖</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token comment"># cd ordinaryroad-ui</span>
<span class="token function">npm</span> <span class="token function">install</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><p>然后启动</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token comment"># cd ordinaryroad-ui</span>
<span class="token function">npm</span> run dev
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="系统账号说明" tabindex="-1"><a class="header-anchor" href="#系统账号说明" aria-hidden="true">#</a> 系统账号说明</h2>`,19),m=a("thead",null,[a("tr",null,[a("th",null,"模块"),a("th",null,"地址"),a("th",null,"账号信息"),a("th",null,"说明")])],-1),b=a("td",null,"前端",-1),_={href:"http://localhost:3001",target:"_blank",rel:"noopener noreferrer"},k=a("td",null,"10001/Abc123",-1),g=a("td",null,null,-1),f=a("td",null,"Knife",-1),x={href:"http://ordinaryroad-gateway:9090/doc.html",target:"_blank",rel:"noopener noreferrer"},y=a("td",null,"ordinaryroad-knife/secret",-1),S={href:"https://doc.xiaominfo.com/knife4j/documentation/oauth2.html",target:"_blank",rel:"noopener noreferrer"},R=a("td",null,"Nacos",-1),E={href:"http://localhost:8848",target:"_blank",rel:"noopener noreferrer"},q=a("td",null,"nacos/nacos",-1),A=a("td",null,null,-1);function L(M,N){const n=d("ExternalLinkIcon");return i(),l("div",null,[c,a("p",null,[e("推荐使用"),a("a",p,[e("SwitchHosts"),s(n)])]),u,a("blockquote",null,[a("p",null,[a("a",h,[e("Nacos 快速开始"),s(n)])])]),v,a("table",null,[m,a("tbody",null,[a("tr",null,[b,a("td",null,[a("a",_,[e("http://localhost:3001"),s(n)])]),k,g]),a("tr",null,[f,a("td",null,[a("a",x,[e("http://ordinaryroad-gateway:9090/doc.html"),s(n)])]),y,a("td",null,[e("OAuth2认证，参考Knife文档"),a("a",S,[e("https://doc.xiaominfo.com/knife4j/documentation/oauth2.html"),s(n)])])]),a("tr",null,[R,a("td",null,[a("a",E,[e("http://localhost:8848"),s(n)])]),q,A])])])])}const j=r(o,[["render",L],["__file","index.html.vue"]]);export{j as default};
