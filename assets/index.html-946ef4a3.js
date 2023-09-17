import{_ as p}from"./plugin-vue_export-helper-c27b6911.js";import{r as l,o as i,c,a as n,b as s,d as e,e as t}from"./app-9f49f69b.js";const o={},u=t('<h1 id="生产环境部署" tabindex="-1"><a class="header-anchor" href="#生产环境部署" aria-hidden="true">#</a> 生产环境部署</h1><h2 id="k3s" tabindex="-1"><a class="header-anchor" href="#k3s" aria-hidden="true">#</a> k3s</h2><h1 id="podman" tabindex="-1"><a class="header-anchor" href="#podman" aria-hidden="true">#</a> Podman</h1>',3),r={href:"https://blog.csdn.net/Kiritow/article/details/113854996",target:"_blank",rel:"noopener noreferrer"},d={href:"https://www.cnblogs.com/WarBlog/p/15469441.html",target:"_blank",rel:"noopener noreferrer"},k=t(`<h2 id="搭建镜像仓库" tabindex="-1"><a class="header-anchor" href="#搭建镜像仓库" aria-hidden="true">#</a> 搭建镜像仓库</h2><p>Docker官方的Docker Registry是一个基础版本的Docker镜像仓库，具备仓库管理的完整功能，但是没有图形化界面。</p><p>搭建方式比较简单，命令如下：</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token function">podman</span> run <span class="token parameter variable">-d</span> <span class="token punctuation">\\</span>
    <span class="token parameter variable">--restart</span><span class="token operator">=</span>always <span class="token punctuation">\\</span>
    <span class="token parameter variable">--name</span> registry    <span class="token punctuation">\\</span>
    <span class="token parameter variable">-p</span> <span class="token number">5000</span>:5000 <span class="token punctuation">\\</span>
    <span class="token parameter variable">-v</span> registry-data:/var/lib/registry <span class="token punctuation">\\</span>
    registry
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>命令中挂载了一个数据卷registry-data（实际为/var/lib/containers/storage/volumes/registry-data）容器内的/var/lib/registry 目录，这是私有镜像库存放数据的目录。</p>`,5),v={href:"http://YourIp:5000/v2/_catalog",target:"_blank",rel:"noopener noreferrer"},m=t(`<div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token function">curl</span> http://localhost:5000/v2/_catalog
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="修改配置" tabindex="-1"><a class="header-anchor" href="#修改配置" aria-hidden="true">#</a> 修改配置</h2><p>其中prefix是pull的时候指定的镜像前缀，location是获取镜像的地址，如果不指定prefix则默认和location一致。insecure=true表示允许通过HTTP协议来获取镜像，对于私有化部署/内网测试环境下无https证书的环境来说很有帮助。</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token comment"># /etc/containers/registries.conf</span>

<span class="token punctuation">[</span><span class="token punctuation">[</span>registry<span class="token punctuation">]</span><span class="token punctuation">]</span>
prefix <span class="token operator">=</span> <span class="token string">&quot;10.0.12.13:5000&quot;</span>
location <span class="token operator">=</span> <span class="token string">&quot;10.0.12.13:5000&quot;</span>
insecure <span class="token operator">=</span> <span class="token boolean">true</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="containerd-再装一个podman吧还是" tabindex="-1"><a class="header-anchor" href="#containerd-再装一个podman吧还是" aria-hidden="true">#</a> <s>Containerd</s>（再装一个Podman吧还是）</h1>`,5),b={href:"https://www.shuzhiduo.com/A/qVde00jE5P/",target:"_blank",rel:"noopener noreferrer"},y={href:"https://www.csdn.net/tags/NtzaAgzsODIzNzAtYmxvZwO0O0OO0O0O.html",target:"_blank",rel:"noopener noreferrer"},h=t(`<p>TODO 使用podman吧</p><h2 id="todo-搭建镜像仓库" tabindex="-1"><a class="header-anchor" href="#todo-搭建镜像仓库" aria-hidden="true">#</a> TODO 搭建镜像仓库</h2><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>podman run -d \\
    --restart=always \\
    --name registry    \\
    -p 5000:5000 \\
    -v registry-data:/var/lib/registry \\
    registry
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code>ctr run --null-io --net-host <span class="token parameter variable">-d</span> <span class="token parameter variable">--mount</span> <span class="token assign-left variable">type</span><span class="token operator">=</span>bind,src<span class="token operator">=</span>/root/registry-data,dst<span class="token operator">=</span>/var/lib/registry,options<span class="token operator">=</span>rbind:rw docker.io/library/registry:latest registry

<span class="token comment">#src=registry-data  表示设置改目录为存放镜像路径</span>
<span class="token comment">#实际为/var/lib/containers/storage/volumes/registry-data</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="无认证" tabindex="-1"><a class="header-anchor" href="#无认证" aria-hidden="true">#</a> 无认证</h2><p>如果你使用的是非安全（http）私有仓库，那么可以通过下面的参数来配置 K3s 连接私有仓库：</p><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>root@ip-172-31-13-117:~# cat &gt;&gt; /etc/rancher/k3s/registries.yaml &lt;&lt;EOF
mirrors:
  &quot;172.31.6.200:5000&quot;:
    endpoint:
      - &quot;http://172.31.6.200:5000&quot;
EOF
systemctl restart k3s
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><p>然后可以通过 crictl 去 pull 镜像：</p><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>root@ip-172-31-13-117:~# crictl pull 172.31.6.200:5000/my-ubuntu
Image is up to date for sha256:9499db7817713c4d10240ca9f5386b605ecff7975179f5a46e7ffd59fff462ee
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><p>接下来，在看一下 containerd 的配置，可以看到文件末尾追加了如下配置：</p><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>root@ip-172-31-13-117:~# cat /var/lib/rancher/k3s/agent/etc/containerd/config.toml
[plugins.cri.registry.mirrors][plugins.cri.registry.mirrors.&quot;172.31.6.200:5000&quot;]
  endpoint = [&quot;http://172.31.6.200:5000&quot;][plugins.cri.registry.mirrors.&quot;rancher.ksd.top:5000&quot;]
  endpoint = [&quot;http://172.31.6.200:5000&quot;]
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="修改默认端口范围30000-32767" tabindex="-1"><a class="header-anchor" href="#修改默认端口范围30000-32767" aria-hidden="true">#</a> 修改默认端口范围30000-32767</h1><ol><li>编辑 kube-apiserver.yaml文件</li></ol><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token function">vi</span> /etc/systemd/system/k3s.service
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><ol start="2"><li>修改ExecStart，加上 <code>--kube-apiserver-arg service-node-port-range=1-65535</code></li></ol><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token assign-left variable">ExecStart</span><span class="token operator">=</span>/usr/local/bin/k3s server --kube-apiserver-arg service-node-port-range<span class="token operator">=</span><span class="token number">1</span>-65535
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><ol start="3"><li>重启 kubelet</li></ol><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code>systemctl daemon-reload
systemctl restart k3s
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="创建namespace" tabindex="-1"><a class="header-anchor" href="#创建namespace" aria-hidden="true">#</a> 创建Namespace</h1>`,19),g={href:"https://kubernetes.io/zh/docs/concepts/overview/working-with-objects/namespaces/",target:"_blank",rel:"noopener noreferrer"},f=t(`<p>控制台创建</p><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>apiVersion: v1
kind: Namespace
metadata:
  name: YOUR_NAMESPACE_NAME
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="查看namespace" tabindex="-1"><a class="header-anchor" href="#查看namespace" aria-hidden="true">#</a> 查看namespace</h2><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>kubectl get namespaces
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><h2 id="设置名字空间偏好" tabindex="-1"><a class="header-anchor" href="#设置名字空间偏好" aria-hidden="true">#</a> 设置名字空间偏好</h2><p>你可以永久保存名字空间，以用于对应上下文中所有后续 kubectl 命令。</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code>kubectl config set-context <span class="token parameter variable">--current</span> <span class="token parameter variable">--namespace</span><span class="token operator">=</span>or-dev
<span class="token comment"># 验证之</span>
kubectl config view <span class="token operator">|</span> <span class="token function">grep</span> namespace:
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="创建有状态应用mysql" tabindex="-1"><a class="header-anchor" href="#创建有状态应用mysql" aria-hidden="true">#</a> 创建有状态应用MySQL</h1><p>jnC8ZVj!TL%PJe8n</p>`,9),x={href:"https://kubernetes.io/zh/docs/tutorials/stateful-application/mysql-wordpress-persistent-volume/",target:"_blank",rel:"noopener noreferrer"},_=n("h2",{id:"创建-kustomization-yaml",tabindex:"-1"},[n("a",{class:"header-anchor",href:"#创建-kustomization-yaml","aria-hidden":"true"},"#"),s(" 创建 kustomization.yaml")],-1),P=n("h3",{id:"创建-secret-生成器",tabindex:"-1"},[n("a",{class:"header-anchor",href:"#创建-secret-生成器","aria-hidden":"true"},"#"),s(" 创建 Secret 生成器")],-1),q={href:"https://kubernetes.io/zh/docs/concepts/configuration/secret/",target:"_blank",rel:"noopener noreferrer"},S=n("code",null,"kubectl",-1),O=n("br",null,null,-1),R=n("code",null,"kustomization.yaml",-1),E=t(`<p>通过以下命令在<code>kustomization.yaml</code>中添加一个 Secret 生成器。您需要用您要使用的密码替换<code>YOUR_PASSWORD</code>。</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token comment"># /root/ordinaryroad/or-mysql/kustomization.yaml</span>

<span class="token function">cat</span> <span class="token operator">&lt;&lt;</span><span class="token string">EOF<span class="token bash punctuation"> <span class="token operator">&gt;</span>./kustomization.yaml</span>
secretGenerator:
- name: mysql-pass
  namespace: or-dev
  literals:
  - password=YOUR_PASSWORD
EOF</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="单实例mysql" tabindex="-1"><a class="header-anchor" href="#单实例mysql" aria-hidden="true">#</a> 单实例MySQL</h2><p>以下 manifest 文件描述了单实例 MySQL 部署。MySQL 容器将 PersistentVolume 挂载在<code>/var/lib/mysql</code>。 <code>MYSQL_ROOT_PASSWORD</code>环境变量设置来自 Secret<br> 的数据库密码。</p><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/or-mysql/mysql-deployment.yaml</span>

<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">type</span><span class="token punctuation">:</span> NodePort           <span class="token comment"># 配置为NodePort，外部可以访问</span>
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">3306</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">3306</span>     <span class="token comment"># 容器暴露的端口，与Dockerfile暴露端口保持一致</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">3306</span>       <span class="token comment"># NodePort，外部访问的端口</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">tier</span><span class="token punctuation">:</span> mysql
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> PersistentVolumeClaim
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">accessModes</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> ReadWriteOnce
  <span class="token key atrule">resources</span><span class="token punctuation">:</span>
    <span class="token key atrule">requests</span><span class="token punctuation">:</span>
      <span class="token key atrule">storage</span><span class="token punctuation">:</span> 20Gi
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">tier</span><span class="token punctuation">:</span> mysql
  <span class="token key atrule">strategy</span><span class="token punctuation">:</span>
    <span class="token key atrule">type</span><span class="token punctuation">:</span> Recreate
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">tier</span><span class="token punctuation">:</span> mysql
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">image</span><span class="token punctuation">:</span> mysql/mysql<span class="token punctuation">-</span>server<span class="token punctuation">:</span>8.0.26
          <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql
          <span class="token key atrule">env</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> TZ
              <span class="token key atrule">value</span><span class="token punctuation">:</span> Asia/Shanghai
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_ROOT_PASSWORD
              <span class="token key atrule">valueFrom</span><span class="token punctuation">:</span>
                <span class="token key atrule">secretKeyRef</span><span class="token punctuation">:</span>
                  <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql<span class="token punctuation">-</span>pass
                  <span class="token key atrule">key</span><span class="token punctuation">:</span> password
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">3306</span>
              <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql
          <span class="token key atrule">volumeMounts</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage
              <span class="token key atrule">mountPath</span><span class="token punctuation">:</span> /var/lib/mysql
      <span class="token key atrule">volumes</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> mysql<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage
          <span class="token key atrule">persistentVolumeClaim</span><span class="token punctuation">:</span>
            <span class="token key atrule">claimName</span><span class="token punctuation">:</span> mysql<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="补充到-kustomization-yaml-文件。" tabindex="-1"><a class="header-anchor" href="#补充到-kustomization-yaml-文件。" aria-hidden="true">#</a> 补充到 <code>kustomization.yaml</code> 文件。</h2><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token comment"># /root/ordinaryroad/or-mysql/kustomization.yaml</span>

<span class="token function">cat</span> <span class="token operator">&lt;&lt;</span><span class="token string">EOF<span class="token bash punctuation"> <span class="token operator">&gt;&gt;</span>./kustomization.yaml</span>
resources:
  - mysql-deployment.yaml
EOF</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="应用" tabindex="-1"><a class="header-anchor" href="#应用" aria-hidden="true">#</a> 应用</h2><p><code>kustomization.yaml</code>包含用于部署 WordPress 网站的所有资源以及 MySQL 数据库。您可以通过以下方式应用目录</p><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code>kubectl apply <span class="token parameter variable">-k</span> ./
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>secret/or-mysql-pass-2fg88hk64d created
service/or-mysql created
deployment.apps/or-mysql created
persistentvolumeclaim/or-mysql-pv-claim created
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="创建有状态应用redis" tabindex="-1"><a class="header-anchor" href="#创建有状态应用redis" aria-hidden="true">#</a> 创建有状态应用Redis</h1>`,12),M={href:"https://kubernetes.io/zh/docs/tutorials/configuration/configure-redis-using-configmap/",target:"_blank",rel:"noopener noreferrer"},N=t(`<h2 id="kustomization-yaml" tabindex="-1"><a class="header-anchor" href="#kustomization-yaml" aria-hidden="true">#</a> kustomization.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/or-redis/kustomization.yaml</span>
<span class="token key atrule">resources</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> redis<span class="token punctuation">-</span>config.yaml
  <span class="token punctuation">-</span> redis<span class="token punctuation">-</span>deployment.yaml
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="redis-config-yaml" tabindex="-1"><a class="header-anchor" href="#redis-config-yaml" aria-hidden="true">#</a> redis-config.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/or-redis/redis-config.yaml</span>

<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> ConfigMap
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>config
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">data</span><span class="token punctuation">:</span>
  <span class="token key atrule">redis-config</span><span class="token punctuation">:</span> <span class="token punctuation">|</span><span class="token scalar string">
    bind &#39;* -::*&#39;</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="redis-deployment-yaml" tabindex="-1"><a class="header-anchor" href="#redis-deployment-yaml" aria-hidden="true">#</a> redis-deployment.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/or-redis/redis-deployment.yaml</span>

<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> redis
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">type</span><span class="token punctuation">:</span> NodePort           <span class="token comment"># 配置为NodePort，外部可以访问</span>
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">6379</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">6379</span>     <span class="token comment"># 容器暴露的端口，与Dockerfile暴露端口保持一致</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">6379</span>       <span class="token comment"># NodePort，外部访问的端口</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">tier</span><span class="token punctuation">:</span> redis
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> PersistentVolumeClaim
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">accessModes</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> ReadWriteOnce
  <span class="token key atrule">resources</span><span class="token punctuation">:</span>
    <span class="token key atrule">requests</span><span class="token punctuation">:</span>
      <span class="token key atrule">storage</span><span class="token punctuation">:</span> 10Gi
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> redis
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">tier</span><span class="token punctuation">:</span> redis
  <span class="token key atrule">strategy</span><span class="token punctuation">:</span>
    <span class="token key atrule">type</span><span class="token punctuation">:</span> Recreate
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">tier</span><span class="token punctuation">:</span> redis
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">image</span><span class="token punctuation">:</span> redis<span class="token punctuation">:</span>6.2.6
          <span class="token key atrule">name</span><span class="token punctuation">:</span> redis
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">6379</span>
              <span class="token key atrule">name</span><span class="token punctuation">:</span> redis
          <span class="token key atrule">volumeMounts</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage
              <span class="token key atrule">mountPath</span><span class="token punctuation">:</span> /data
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>config
              <span class="token key atrule">mountPath</span><span class="token punctuation">:</span> /usr/local/etc/redis
      <span class="token key atrule">volumes</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage
          <span class="token key atrule">persistentVolumeClaim</span><span class="token punctuation">:</span>
            <span class="token key atrule">claimName</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>config
          <span class="token key atrule">configMap</span><span class="token punctuation">:</span>
            <span class="token key atrule">name</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>config
            <span class="token key atrule">items</span><span class="token punctuation">:</span>
              <span class="token punctuation">-</span> <span class="token key atrule">key</span><span class="token punctuation">:</span> redis<span class="token punctuation">-</span>config
                <span class="token key atrule">path</span><span class="token punctuation">:</span> redis.conf
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="应用-1" tabindex="-1"><a class="header-anchor" href="#应用-1" aria-hidden="true">#</a> 应用</h2><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code>kubectl apply <span class="token parameter variable">-k</span> ./
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div><div class="language-text line-numbers-mode" data-ext="text"><pre class="language-text"><code>configmap/redis-config created
service/redis created
deployment.apps/redis created
persistentvolumeclaim/redis-pv-claim created
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="创建有状态应用minio" tabindex="-1"><a class="header-anchor" href="#创建有状态应用minio" aria-hidden="true">#</a> 创建有状态应用Minio</h1><h2 id="kustomization-yaml-1" tabindex="-1"><a class="header-anchor" href="#kustomization-yaml-1" aria-hidden="true">#</a> kustomization.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/minio/kustomization.yaml</span>

<span class="token key atrule">secretGenerator</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>config
    <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
    <span class="token key atrule">literals</span><span class="token punctuation">:</span>
      <span class="token comment"># Access key length should be at least 3, and secret key length at least 8 characters</span>
      <span class="token punctuation">-</span> MINIO_ROOT_USER=minio
      <span class="token punctuation">-</span> MINIO_ROOT_PASSWORD=12345678
<span class="token key atrule">resources</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> minio<span class="token punctuation">-</span>deployment.yaml
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="minio-deployment-yaml" tabindex="-1"><a class="header-anchor" href="#minio-deployment-yaml" aria-hidden="true">#</a> minio-deployment.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/minio/minio-deployment.yaml</span>

<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> minio
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">type</span><span class="token punctuation">:</span> NodePort           <span class="token comment"># 配置为NodePort，外部可以访问</span>
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">9999</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">9999</span>     <span class="token comment"># 容器暴露的端口，与Dockerfile暴露端口保持一致</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">9999</span>       <span class="token comment"># NodePort，外部访问的端口</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">tier</span><span class="token punctuation">:</span> minio
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> PersistentVolumeClaim
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim<span class="token punctuation">-</span>data
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">accessModes</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> ReadWriteOnce
  <span class="token key atrule">resources</span><span class="token punctuation">:</span>
    <span class="token key atrule">requests</span><span class="token punctuation">:</span>
      <span class="token key atrule">storage</span><span class="token punctuation">:</span> 10Gi
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> PersistentVolumeClaim
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim<span class="token punctuation">-</span>config
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">accessModes</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> ReadWriteOnce
  <span class="token key atrule">resources</span><span class="token punctuation">:</span>
    <span class="token key atrule">requests</span><span class="token punctuation">:</span>
      <span class="token key atrule">storage</span><span class="token punctuation">:</span> 1Gi
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> minio
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">tier</span><span class="token punctuation">:</span> minio
  <span class="token key atrule">replicas</span><span class="token punctuation">:</span> <span class="token number">1</span>
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">tier</span><span class="token punctuation">:</span> minio
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> minio
          <span class="token key atrule">image</span><span class="token punctuation">:</span> minio/minio
          <span class="token key atrule">env</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MINIO_ROOT_USER
              <span class="token key atrule">valueFrom</span><span class="token punctuation">:</span>
                <span class="token key atrule">secretKeyRef</span><span class="token punctuation">:</span>
                  <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>config
                  <span class="token key atrule">key</span><span class="token punctuation">:</span> MINIO_ROOT_USER
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MINIO_ROOT_PASSWORD
              <span class="token key atrule">valueFrom</span><span class="token punctuation">:</span>
                <span class="token key atrule">secretKeyRef</span><span class="token punctuation">:</span>
                  <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>config
                  <span class="token key atrule">key</span><span class="token punctuation">:</span> MINIO_ROOT_PASSWORD
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">9999</span>
              <span class="token key atrule">name</span><span class="token punctuation">:</span> minio
          <span class="token key atrule">volumeMounts</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage<span class="token punctuation">-</span>data
              <span class="token key atrule">mountPath</span><span class="token punctuation">:</span> /data
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage<span class="token punctuation">-</span>config
              <span class="token key atrule">mountPath</span><span class="token punctuation">:</span> /root/.minio
          <span class="token key atrule">command</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> /bin/sh
          <span class="token key atrule">args</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token punctuation">-</span>c
            <span class="token punctuation">-</span> minio server /data <span class="token punctuation">-</span><span class="token punctuation">-</span>console<span class="token punctuation">-</span>address &quot;<span class="token punctuation">:</span>9999&quot;
      <span class="token key atrule">volumes</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage<span class="token punctuation">-</span>data
          <span class="token key atrule">persistentVolumeClaim</span><span class="token punctuation">:</span>
            <span class="token key atrule">claimName</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim<span class="token punctuation">-</span>data
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>persistent<span class="token punctuation">-</span>storage<span class="token punctuation">-</span>config
          <span class="token key atrule">persistentVolumeClaim</span><span class="token punctuation">:</span>
            <span class="token key atrule">claimName</span><span class="token punctuation">:</span> minio<span class="token punctuation">-</span>pv<span class="token punctuation">-</span>claim<span class="token punctuation">-</span>config
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="创建无状态应用nacos" tabindex="-1"><a class="header-anchor" href="#创建无状态应用nacos" aria-hidden="true">#</a> 创建无状态应用Nacos</h1><h2 id="kustomization-yaml-2" tabindex="-1"><a class="header-anchor" href="#kustomization-yaml-2" aria-hidden="true">#</a> kustomization.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/nacos/kustomization.yaml</span>

<span class="token key atrule">secretGenerator</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> nacos<span class="token punctuation">-</span>mysql<span class="token punctuation">-</span>pass
    <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
    <span class="token key atrule">literals</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> password=jnC8ZVj<span class="token tag">!TL%PJe8n</span>
<span class="token key atrule">resources</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> nacos<span class="token punctuation">-</span>deployment.yaml
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h2 id="nacos-deployment-yaml" tabindex="-1"><a class="header-anchor" href="#nacos-deployment-yaml" aria-hidden="true">#</a> nacos-deployment.yaml</h2><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/nacos/nacos-deployment.yaml</span>

<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nacos
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">type</span><span class="token punctuation">:</span> NodePort           <span class="token comment"># 配置为NodePort，外部可以访问</span>
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">8848</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">8848</span>     <span class="token comment"># 容器暴露的端口，与Dockerfile暴露端口保持一致</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">8848</span>       <span class="token comment"># NodePort，外部访问的端口</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">tier</span><span class="token punctuation">:</span> nacos
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nacos
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">tier</span><span class="token punctuation">:</span> nacos
  <span class="token key atrule">replicas</span><span class="token punctuation">:</span> <span class="token number">1</span>
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">tier</span><span class="token punctuation">:</span> nacos
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> nacos
          <span class="token key atrule">image</span><span class="token punctuation">:</span> nacos/nacos<span class="token punctuation">-</span>server<span class="token punctuation">:</span>v2.0.4
          <span class="token key atrule">env</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> PREFER_HOST_MODE
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;hostname&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MODE
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;standalone&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> SPRING_DATASOURCE_PLATFORM
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;mysql&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_SERVICE_DB_PARAM
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;useUnicode=true&amp;characterEncoding=utf-8&amp;useSSL=false&amp;useLegacyDatetimeCode=false&amp;serverTimezone=GMT%2B8&amp;allowPublicKeyRetrieval=true&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_SERVICE_HOST
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;mysql.or-dev&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_SERVICE_DB_NAME
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;or_config_pro&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_SERVICE_PORT
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;3306&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_SERVICE_USER
              <span class="token key atrule">value</span><span class="token punctuation">:</span> <span class="token string">&quot;root&quot;</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> MYSQL_SERVICE_PASSWORD
              <span class="token key atrule">valueFrom</span><span class="token punctuation">:</span>
                <span class="token key atrule">secretKeyRef</span><span class="token punctuation">:</span>
                  <span class="token key atrule">name</span><span class="token punctuation">:</span> nacos<span class="token punctuation">-</span>mysql<span class="token punctuation">-</span>pass
                  <span class="token key atrule">key</span><span class="token punctuation">:</span> password
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">8848</span>
              <span class="token key atrule">name</span><span class="token punctuation">:</span> nacos
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="开始部署ordinaryroad" tabindex="-1"><a class="header-anchor" href="#开始部署ordinaryroad" aria-hidden="true">#</a> 开始部署OrdinaryRoad</h1><h2 id="auth-server" tabindex="-1"><a class="header-anchor" href="#auth-server" aria-hidden="true">#</a> auth-server</h2><h3 id="dockerfile" tabindex="-1"><a class="header-anchor" href="#dockerfile" aria-hidden="true">#</a> Dockerfile</h3><div class="language-docker line-numbers-mode" data-ext="docker"><pre class="language-docker"><code><span class="token comment"># /root/ordinaryroad/auth-server/Dockerfile</span>

<span class="token instruction"><span class="token keyword">FROM</span> moxm/java:1.8-full</span>

<span class="token instruction"><span class="token keyword">MAINTAINER</span> mjz(miaojinzhou@ordinaryroad.tech)</span>

<span class="token instruction"><span class="token keyword">ARG</span> APP_NAME=ordinaryroad-auth-server</span>

<span class="token instruction"><span class="token keyword">RUN</span> mkdir -p /ordinaryroad/<span class="token variable">$APP_NAME</span>/app</span>

<span class="token instruction"><span class="token keyword">WORKDIR</span> /ordinaryroad/<span class="token variable">$APP_NAME</span>/app</span>

<span class="token instruction"><span class="token keyword">COPY</span> ./app/<span class="token variable">$APP_NAME</span>.jar app.jar</span>

<span class="token instruction"><span class="token keyword">EXPOSE</span> 9302</span>

<span class="token comment"># 防止日志中文乱码</span>
<span class="token instruction"><span class="token keyword">ENV</span> LANG=C.UTF-8</span>

<span class="token instruction"><span class="token keyword">ENV</span> TZ=Asia/Shanghai JAVA_OPTS=<span class="token string">&quot;-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom&quot;</span></span>

<span class="token instruction"><span class="token keyword">CMD</span> sleep 10; java -jar <span class="token variable">$JAVA_OPTS</span> app.jar</span>
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="build并push" tabindex="-1"><a class="header-anchor" href="#build并push" aria-hidden="true">#</a> build并push</h3><div class="language-bash line-numbers-mode" data-ext="sh"><pre class="language-bash"><code><span class="token function">podman</span> build <span class="token parameter variable">-f</span> Dockerfile <span class="token parameter variable">-t</span> <span class="token number">10.0</span>.12.13:5000/ordinaryroad-auth-server

<span class="token function">podman</span> images
REPOSITORY                                TAG         IMAGE ID      CREATED            SIZE
<span class="token number">10.0</span>.12.13:5000/ordinaryroad-auth-server  latest      35a3ea535b69  <span class="token number">43</span> minutes ago     <span class="token number">493</span> MB
docker.io/library/registry                latest      2e200967d166  <span class="token number">3</span> weeks ago        <span class="token number">24.7</span> MB
docker.io/moxm/java                       <span class="token number">1.8</span>-full    bb1391003ead  <span class="token number">22</span> months ago      <span class="token number">422</span> MB

<span class="token function">podman</span> push <span class="token variable">\${imageId}</span> <span class="token number">10.0</span>.12.13:5000/ordinaryroad-auth-server
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h3 id="deployment-yaml" tabindex="-1"><a class="header-anchor" href="#deployment-yaml" aria-hidden="true">#</a> deployment.yaml</h3><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token comment"># /root/ordinaryroad/auth-server/deployment.yaml</span>

<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> auth
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">type</span><span class="token punctuation">:</span> NodePort           <span class="token comment"># 配置为NodePort，外部可以访问</span>
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">9302</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">9302</span>     <span class="token comment"># 容器暴露的端口，与Dockerfile暴露端口保持一致</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">9302</span>       <span class="token comment"># NodePort，外部访问的端口</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">tier</span><span class="token punctuation">:</span> auth
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> auth
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">tier</span><span class="token punctuation">:</span> auth
  <span class="token key atrule">replicas</span><span class="token punctuation">:</span> <span class="token number">1</span>
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">tier</span><span class="token punctuation">:</span> auth
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> auth
          <span class="token key atrule">image</span><span class="token punctuation">:</span> 10.0.12.13<span class="token punctuation">:</span>5000/ordinaryroad<span class="token punctuation">-</span>auth<span class="token punctuation">-</span>server
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">9302</span>
              <span class="token key atrule">name</span><span class="token punctuation">:</span> auth
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div><h1 id="使用ingress暴露服务" tabindex="-1"><a class="header-anchor" href="#使用ingress暴露服务" aria-hidden="true">#</a> <s>使用Ingress暴露服务</s></h1>`,28),A={href:"https://kubernetes.io/zh/docs/concepts/services-networking/ingress/#the-ingress-resource",target:"_blank",rel:"noopener noreferrer"},D={href:"https://blog.csdn.net/qq_21187515/article/details/112363072",target:"_blank",rel:"noopener noreferrer"},V={href:"https://blog.csdn.net/qq_21187515/article/details/112497544",target:"_blank",rel:"noopener noreferrer"},T=t(`<p>步骤一，创建Ingress的yaml描述文件 首先创建ingress-nginx的文件目录（后面创建的ingress相关的文件都在这里面，便于维护） mkdir ingress-nginx 创建一个名称为deploy.yaml，内容如下<br> 注：<br> 1.这个yaml描述文件有点长，简单描述一下，里面主要创建了一个ingress-nginx的命名空间，nginx-ingress-controller的Deployment对象以及最后面创建ingress-nginx的Service，Service配置映射的端口<br> 2.我安装的k8s版本是1.18.0</p><div class="language-yaml line-numbers-mode" data-ext="yml"><pre class="language-yaml"><code><span class="token key atrule">kind</span><span class="token punctuation">:</span> ConfigMap
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>configuration
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token punctuation">---</span>
<span class="token key atrule">kind</span><span class="token punctuation">:</span> ConfigMap
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> tcp<span class="token punctuation">-</span>services
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token punctuation">---</span>
<span class="token key atrule">kind</span><span class="token punctuation">:</span> ConfigMap
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> udp<span class="token punctuation">-</span>services
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">replicas</span><span class="token punctuation">:</span> <span class="token number">1</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">app</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">app</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token key atrule">terminationGracePeriodSeconds</span><span class="token punctuation">:</span> <span class="token number">60</span>
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
          <span class="token key atrule">image</span><span class="token punctuation">:</span> registry.cn<span class="token punctuation">-</span>qingdao.aliyuncs.com/kubernetes_xingej/defaultbackend<span class="token punctuation">-</span>amd64<span class="token punctuation">:</span><span class="token number">1.5</span>      <span class="token comment">#建议提前在node节点下载镜像；</span>
          <span class="token key atrule">livenessProbe</span><span class="token punctuation">:</span>
            <span class="token key atrule">httpGet</span><span class="token punctuation">:</span>
              <span class="token key atrule">path</span><span class="token punctuation">:</span> /healthz
              <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">8080</span>
              <span class="token key atrule">scheme</span><span class="token punctuation">:</span> HTTP
            <span class="token key atrule">initialDelaySeconds</span><span class="token punctuation">:</span> <span class="token number">30</span>
            <span class="token key atrule">timeoutSeconds</span><span class="token punctuation">:</span> <span class="token number">5</span>
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">8080</span>
          <span class="token key atrule">resources</span><span class="token punctuation">:</span>
            <span class="token comment"># 这里调整了cpu和memory的大小，可能不同集群限制的最小值不同，看部署失败的原因就清楚</span>
            <span class="token key atrule">limits</span><span class="token punctuation">:</span>
              <span class="token key atrule">cpu</span><span class="token punctuation">:</span> 100m
              <span class="token key atrule">memory</span><span class="token punctuation">:</span> 100Mi
            <span class="token key atrule">requests</span><span class="token punctuation">:</span>
              <span class="token key atrule">cpu</span><span class="token punctuation">:</span> 100m
              <span class="token key atrule">memory</span><span class="token punctuation">:</span> 100Mi
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">80</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">8080</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">app</span><span class="token punctuation">:</span> default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> ServiceAccount
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>serviceaccount
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> rbac.authorization.k8s.io/v1beta1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> ClusterRole
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>clusterrole
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">rules</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> configmaps
      <span class="token punctuation">-</span> endpoints
      <span class="token punctuation">-</span> nodes
      <span class="token punctuation">-</span> pods
      <span class="token punctuation">-</span> secrets
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> list
      <span class="token punctuation">-</span> watch
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> nodes
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> get
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> services
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> get
      <span class="token punctuation">-</span> list
      <span class="token punctuation">-</span> watch
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> events
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> create
      <span class="token punctuation">-</span> patch
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;extensions&quot;</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;networking.k8s.io&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> ingresses
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> get
      <span class="token punctuation">-</span> list
      <span class="token punctuation">-</span> watch
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;extensions&quot;</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;networking.k8s.io&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> ingresses/status
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> update
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> rbac.authorization.k8s.io/v1beta1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Role
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>role
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">rules</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> configmaps
      <span class="token punctuation">-</span> pods
      <span class="token punctuation">-</span> secrets
      <span class="token punctuation">-</span> namespaces
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> get
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> configmaps
    <span class="token key atrule">resourceNames</span><span class="token punctuation">:</span>
      <span class="token comment"># Defaults to &quot;&lt;election-id&gt;-&lt;ingress-class&gt;&quot;</span>
      <span class="token comment"># Here: &quot;&lt;ingress-controller-leader&gt;-&lt;nginx&gt;&quot;</span>
      <span class="token comment"># This has to be adapted if you change either parameter</span>
      <span class="token comment"># when launching the nginx-ingress-controller.</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;ingress-controller-leader-nginx&quot;</span>
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> get
      <span class="token punctuation">-</span> update
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> configmaps
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> create
  <span class="token punctuation">-</span> <span class="token key atrule">apiGroups</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> <span class="token string">&quot;&quot;</span>
    <span class="token key atrule">resources</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> endpoints
    <span class="token key atrule">verbs</span><span class="token punctuation">:</span>
      <span class="token punctuation">-</span> get
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> rbac.authorization.k8s.io/v1beta1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> RoleBinding
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>role<span class="token punctuation">-</span>nisa<span class="token punctuation">-</span>binding
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">roleRef</span><span class="token punctuation">:</span>
  <span class="token key atrule">apiGroup</span><span class="token punctuation">:</span> rbac.authorization.k8s.io
  <span class="token key atrule">kind</span><span class="token punctuation">:</span> Role
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>role
<span class="token key atrule">subjects</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> <span class="token key atrule">kind</span><span class="token punctuation">:</span> ServiceAccount
    <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>serviceaccount
    <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> rbac.authorization.k8s.io/v1beta1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> ClusterRoleBinding
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>clusterrole<span class="token punctuation">-</span>nisa<span class="token punctuation">-</span>binding
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">roleRef</span><span class="token punctuation">:</span>
  <span class="token key atrule">apiGroup</span><span class="token punctuation">:</span> rbac.authorization.k8s.io
  <span class="token key atrule">kind</span><span class="token punctuation">:</span> ClusterRole
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>clusterrole
<span class="token key atrule">subjects</span><span class="token punctuation">:</span>
  <span class="token punctuation">-</span> <span class="token key atrule">kind</span><span class="token punctuation">:</span> ServiceAccount
    <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>serviceaccount
    <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> apps/v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Deployment
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>controller
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">replicas</span><span class="token punctuation">:</span> <span class="token number">2</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">matchLabels</span><span class="token punctuation">:</span>
      <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
      <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
  <span class="token key atrule">template</span><span class="token punctuation">:</span>
    <span class="token key atrule">metadata</span><span class="token punctuation">:</span>
      <span class="token key atrule">labels</span><span class="token punctuation">:</span>
        <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
        <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
      <span class="token key atrule">annotations</span><span class="token punctuation">:</span>
        <span class="token key atrule">prometheus.io/port</span><span class="token punctuation">:</span> <span class="token string">&quot;10254&quot;</span>
        <span class="token key atrule">prometheus.io/scrape</span><span class="token punctuation">:</span> <span class="token string">&quot;true&quot;</span>
    <span class="token key atrule">spec</span><span class="token punctuation">:</span>
      <span class="token comment"># wait up to five minutes for the drain of connections</span>
      <span class="token key atrule">terminationGracePeriodSeconds</span><span class="token punctuation">:</span> <span class="token number">300</span>
      <span class="token key atrule">serviceAccountName</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>serviceaccount
      <span class="token key atrule">nodeSelector</span><span class="token punctuation">:</span>
        <span class="token key atrule">kubernetes.io/os</span><span class="token punctuation">:</span> linux
      <span class="token key atrule">containers</span><span class="token punctuation">:</span>
        <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>controller
          <span class="token key atrule">image</span><span class="token punctuation">:</span> suisrc/ingress<span class="token punctuation">-</span>nginx<span class="token punctuation">:</span>0.30.0   <span class="token comment">#建议提前在node节点下载镜像；</span>
          <span class="token key atrule">args</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> /nginx<span class="token punctuation">-</span>ingress<span class="token punctuation">-</span>controller
            <span class="token punctuation">-</span> <span class="token punctuation">-</span><span class="token punctuation">-</span>default<span class="token punctuation">-</span>backend<span class="token punctuation">-</span>service=$(POD_NAMESPACE)/default<span class="token punctuation">-</span>http<span class="token punctuation">-</span>backend
            <span class="token punctuation">-</span> <span class="token punctuation">-</span><span class="token punctuation">-</span>configmap=$(POD_NAMESPACE)/nginx<span class="token punctuation">-</span>configuration
            <span class="token punctuation">-</span> <span class="token punctuation">-</span><span class="token punctuation">-</span>tcp<span class="token punctuation">-</span>services<span class="token punctuation">-</span>configmap=$(POD_NAMESPACE)/tcp<span class="token punctuation">-</span>services
            <span class="token punctuation">-</span> <span class="token punctuation">-</span><span class="token punctuation">-</span>udp<span class="token punctuation">-</span>services<span class="token punctuation">-</span>configmap=$(POD_NAMESPACE)/udp<span class="token punctuation">-</span>services
            <span class="token punctuation">-</span> <span class="token punctuation">-</span><span class="token punctuation">-</span>publish<span class="token punctuation">-</span>service=$(POD_NAMESPACE)/ingress<span class="token punctuation">-</span>nginx
            <span class="token punctuation">-</span> <span class="token punctuation">-</span><span class="token punctuation">-</span>annotations<span class="token punctuation">-</span>prefix=nginx.ingress.kubernetes.io
          <span class="token key atrule">securityContext</span><span class="token punctuation">:</span>
            <span class="token key atrule">allowPrivilegeEscalation</span><span class="token punctuation">:</span> <span class="token boolean important">true</span>
            <span class="token key atrule">capabilities</span><span class="token punctuation">:</span>
              <span class="token key atrule">drop</span><span class="token punctuation">:</span>
                <span class="token punctuation">-</span> ALL
              <span class="token key atrule">add</span><span class="token punctuation">:</span>
                <span class="token punctuation">-</span> NET_BIND_SERVICE
            <span class="token comment"># www-data -&gt; 101</span>
            <span class="token key atrule">runAsUser</span><span class="token punctuation">:</span> <span class="token number">101</span>
          <span class="token key atrule">env</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> POD_NAME
              <span class="token key atrule">valueFrom</span><span class="token punctuation">:</span>
                <span class="token key atrule">fieldRef</span><span class="token punctuation">:</span>
                  <span class="token key atrule">fieldPath</span><span class="token punctuation">:</span> metadata.name
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> POD_NAMESPACE
              <span class="token key atrule">valueFrom</span><span class="token punctuation">:</span>
                <span class="token key atrule">fieldRef</span><span class="token punctuation">:</span>
                  <span class="token key atrule">fieldPath</span><span class="token punctuation">:</span> metadata.namespace
          <span class="token key atrule">ports</span><span class="token punctuation">:</span>
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> http
              <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">80</span>
              <span class="token key atrule">protocol</span><span class="token punctuation">:</span> TCP
            <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> https
              <span class="token key atrule">containerPort</span><span class="token punctuation">:</span> <span class="token number">443</span>
              <span class="token key atrule">protocol</span><span class="token punctuation">:</span> TCP
          <span class="token key atrule">livenessProbe</span><span class="token punctuation">:</span>
            <span class="token key atrule">failureThreshold</span><span class="token punctuation">:</span> <span class="token number">3</span>
            <span class="token key atrule">httpGet</span><span class="token punctuation">:</span>
              <span class="token key atrule">path</span><span class="token punctuation">:</span> /healthz
              <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">10254</span>
              <span class="token key atrule">scheme</span><span class="token punctuation">:</span> HTTP
            <span class="token key atrule">initialDelaySeconds</span><span class="token punctuation">:</span> <span class="token number">10</span>
            <span class="token key atrule">periodSeconds</span><span class="token punctuation">:</span> <span class="token number">10</span>
            <span class="token key atrule">successThreshold</span><span class="token punctuation">:</span> <span class="token number">1</span>
            <span class="token key atrule">timeoutSeconds</span><span class="token punctuation">:</span> <span class="token number">10</span>
          <span class="token key atrule">readinessProbe</span><span class="token punctuation">:</span>
            <span class="token key atrule">failureThreshold</span><span class="token punctuation">:</span> <span class="token number">3</span>
            <span class="token key atrule">httpGet</span><span class="token punctuation">:</span>
              <span class="token key atrule">path</span><span class="token punctuation">:</span> /healthz
              <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">10254</span>
              <span class="token key atrule">scheme</span><span class="token punctuation">:</span> HTTP
            <span class="token key atrule">periodSeconds</span><span class="token punctuation">:</span> <span class="token number">10</span>
            <span class="token key atrule">successThreshold</span><span class="token punctuation">:</span> <span class="token number">1</span>
            <span class="token key atrule">timeoutSeconds</span><span class="token punctuation">:</span> <span class="token number">10</span>
          <span class="token key atrule">lifecycle</span><span class="token punctuation">:</span>
            <span class="token key atrule">preStop</span><span class="token punctuation">:</span>
              <span class="token key atrule">exec</span><span class="token punctuation">:</span>
                <span class="token key atrule">command</span><span class="token punctuation">:</span>
                  <span class="token punctuation">-</span> /wait<span class="token punctuation">-</span>shutdown
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> LimitRange
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">limits</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">min</span><span class="token punctuation">:</span>
        <span class="token key atrule">memory</span><span class="token punctuation">:</span> 90Mi
        <span class="token key atrule">cpu</span><span class="token punctuation">:</span> 100m
      <span class="token key atrule">type</span><span class="token punctuation">:</span> Container
<span class="token punctuation">---</span>
<span class="token key atrule">apiVersion</span><span class="token punctuation">:</span> v1
<span class="token key atrule">kind</span><span class="token punctuation">:</span> Service
<span class="token key atrule">metadata</span><span class="token punctuation">:</span>
  <span class="token key atrule">name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
  <span class="token key atrule">namespace</span><span class="token punctuation">:</span> or<span class="token punctuation">-</span>dev
  <span class="token key atrule">labels</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
<span class="token key atrule">spec</span><span class="token punctuation">:</span>
  <span class="token key atrule">type</span><span class="token punctuation">:</span> NodePort
  <span class="token key atrule">ports</span><span class="token punctuation">:</span>
    <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> http
      <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">80</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">80</span>
      <span class="token key atrule">protocol</span><span class="token punctuation">:</span> TCP
      <span class="token comment"># HTTP</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">80</span>
    <span class="token punctuation">-</span> <span class="token key atrule">name</span><span class="token punctuation">:</span> https
      <span class="token key atrule">port</span><span class="token punctuation">:</span> <span class="token number">443</span>
      <span class="token key atrule">targetPort</span><span class="token punctuation">:</span> <span class="token number">443</span>
      <span class="token key atrule">protocol</span><span class="token punctuation">:</span> TCP
      <span class="token comment"># HTTPS</span>
      <span class="token key atrule">nodePort</span><span class="token punctuation">:</span> <span class="token number">443</span>
  <span class="token key atrule">selector</span><span class="token punctuation">:</span>
    <span class="token key atrule">app.kubernetes.io/name</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
    <span class="token key atrule">app.kubernetes.io/part-of</span><span class="token punctuation">:</span> ingress<span class="token punctuation">-</span>nginx
</code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div><div class="line-number"></div></div></div>`,2);function w(C,z){const a=l("ExternalLinkIcon");return i(),c("div",null,[u,n("p",null,[n("a",r,[s("Podman添加私有镜像源配置 registries.conf_Kiritow的博客-CSDN博客_podman registry"),e(a)])]),n("p",null,[n("a",d,[s("Docker私有镜像仓库 - 一杯水M - 博客园 (cnblogs.com)"),e(a)])]),k,n("p",null,[s("访问"),n("a",v,[s("http://YourIp:5000/v2/_catalog"),e(a)]),s(" 可以查看当前私有镜像服务中包含的镜像")]),m,n("p",null,[n("a",b,[s("配置 containerd 镜像仓库完全攻略 (shuzhiduo.com)"),e(a)])]),n("p",null,[n("a",y,[s("containerd配置私有仓库 k8s - CSDN"),e(a)])]),h,n("p",null,[n("a",g,[s("名字空间 | Kubernetes"),e(a)])]),f,n("p",null,[n("a",x,[s("示例：使用 Persistent Volumes 部署 WordPress 和 MySQL | Kubernetes"),e(a)])]),_,P,n("p",null,[s("A "),n("a",q,[s("Secret"),e(a)]),s(" 是存储诸如密码或密钥之类的敏感数据的对象。从 1.14 开始，"),S,s("支持使用"),O,s(" kustomization 文件管理 Kubernetes 对象。您可以通过"),R,s("中的生成器创建一个 Secret。")]),E,n("p",null,[n("a",M,[s("使用 ConfigMap 来配置 Redis | Kubernetes"),e(a)])]),N,n("p",null,[n("a",A,[s("Ingress | Kubernetes"),e(a)])]),n("p",null,[n("a",D,[s("k8s-(七）暴露服务的三种方式_新林。的博客-CSDN博客_k8s 服务暴露"),e(a)])]),n("p",null,[n("a",V,[s("k8s-(八）通过Ingress-nginx暴露service给外部网络访问_新林。的博客-CSDN博客_spanking外网"),e(a)])]),T])}const G=p(o,[["render",w],["__file","index.html.vue"]]);export{G as default};
