# 生产环境部署

## k3s

# Podman

[Podman添加私有镜像源配置 registries.conf_Kiritow的博客-CSDN博客_podman registry](https://blog.csdn.net/Kiritow/article/details/113854996)

[Docker私有镜像仓库 - 一杯水M - 博客园 (cnblogs.com)](https://www.cnblogs.com/WarBlog/p/15469441.html)

## 搭建镜像仓库

Docker官方的Docker Registry是一个基础版本的Docker镜像仓库，具备仓库管理的完整功能，但是没有图形化界面。

搭建方式比较简单，命令如下：

```shell
podman run -d \
    --restart=always \
    --name registry    \
    -p 5000:5000 \
    -v registry-data:/var/lib/registry \
    registry
```

命令中挂载了一个数据卷registry-data（实际为/var/lib/containers/storage/volumes/registry-data）容器内的/var/lib/registry 目录，这是私有镜像库存放数据的目录。

访问http://YourIp:5000/v2/_catalog 可以查看当前私有镜像服务中包含的镜像

```shell
curl http://localhost:5000/v2/_catalog
```

## 修改配置

其中prefix是pull的时候指定的镜像前缀，location是获取镜像的地址，如果不指定prefix则默认和location一致。insecure=true表示允许通过HTTP协议来获取镜像，对于私有化部署/内网测试环境下无https证书的环境来说很有帮助。

```shell
# /etc/containers/registries.conf

[[registry]]
prefix = "10.0.12.13:5000"
location = "10.0.12.13:5000"
insecure = true
```

# ~~Containerd~~（再装一个Podman吧还是）

[配置 containerd 镜像仓库完全攻略 (shuzhiduo.com)](https://www.shuzhiduo.com/A/qVde00jE5P/)

[containerd配置私有仓库 k8s - CSDN](https://www.csdn.net/tags/NtzaAgzsODIzNzAtYmxvZwO0O0OO0O0O.html)

TODO 使用podman吧

## TODO 搭建镜像仓库

```
podman run -d \
    --restart=always \
    --name registry    \
    -p 5000:5000 \
    -v registry-data:/var/lib/registry \
    registry
```

```shell
ctr run --null-io --net-host -d --mount type=bind,src=/root/registry-data,dst=/var/lib/registry,options=rbind:rw docker.io/library/registry:latest registry

#src=registry-data  表示设置改目录为存放镜像路径
#实际为/var/lib/containers/storage/volumes/registry-data
```

## 无认证

如果你使用的是非安全（http）私有仓库，那么可以通过下面的参数来配置 K3s 连接私有仓库：

```
root@ip-172-31-13-117:~# cat >> /etc/rancher/k3s/registries.yaml <<EOF
mirrors:
  "172.31.6.200:5000":
    endpoint:
      - "http://172.31.6.200:5000"
EOF
systemctl restart k3s
```

然后可以通过 crictl 去 pull 镜像：

```
root@ip-172-31-13-117:~# crictl pull 172.31.6.200:5000/my-ubuntu
Image is up to date for sha256:9499db7817713c4d10240ca9f5386b605ecff7975179f5a46e7ffd59fff462ee
```

接下来，在看一下 containerd 的配置，可以看到文件末尾追加了如下配置：

```
root@ip-172-31-13-117:~# cat /var/lib/rancher/k3s/agent/etc/containerd/config.toml
[plugins.cri.registry.mirrors][plugins.cri.registry.mirrors."172.31.6.200:5000"]
  endpoint = ["http://172.31.6.200:5000"][plugins.cri.registry.mirrors."rancher.ksd.top:5000"]
  endpoint = ["http://172.31.6.200:5000"]
```

# 修改默认端口范围30000-32767

1. 编辑 kube-apiserver.yaml文件

```shell
vi /etc/systemd/system/k3s.service
```

2. 修改ExecStart，加上 `--kube-apiserver-arg service-node-port-range=1-65535`

```shell
ExecStart=/usr/local/bin/k3s server --kube-apiserver-arg service-node-port-range=1-65535
```

3. 重启 kubelet

```shell
systemctl daemon-reload
systemctl restart k3s
```

# 创建Namespace

[名字空间 | Kubernetes](https://kubernetes.io/zh/docs/concepts/overview/working-with-objects/namespaces/)

控制台创建

```
apiVersion: v1
kind: Namespace
metadata:
  name: YOUR_NAMESPACE_NAME
```

## 查看namespace

```
kubectl get namespaces
```

## 设置名字空间偏好

你可以永久保存名字空间，以用于对应上下文中所有后续 kubectl 命令。

```shell
kubectl config set-context --current --namespace=or-dev
# 验证之
kubectl config view | grep namespace:
```

# 创建有状态应用MySQL

jnC8ZVj!TL%PJe8n

[示例：使用 Persistent Volumes 部署 WordPress 和 MySQL | Kubernetes](https://kubernetes.io/zh/docs/tutorials/stateful-application/mysql-wordpress-persistent-volume/)

## 创建 kustomization.yaml

### 创建 Secret 生成器

A [Secret](https://kubernetes.io/zh/docs/concepts/configuration/secret/) 是存储诸如密码或密钥之类的敏感数据的对象。从 1.14 开始，`kubectl`支持使用
kustomization 文件管理 Kubernetes 对象。您可以通过`kustomization.yaml`中的生成器创建一个 Secret。

通过以下命令在`kustomization.yaml`中添加一个 Secret 生成器。您需要用您要使用的密码替换`YOUR_PASSWORD`。

```shell
# /root/ordinaryroad/or-mysql/kustomization.yaml

cat <<EOF >./kustomization.yaml
secretGenerator:
- name: mysql-pass
  namespace: or-dev
  literals:
  - password=YOUR_PASSWORD
EOF
```

## 单实例MySQL

以下 manifest 文件描述了单实例 MySQL 部署。MySQL 容器将 PersistentVolume 挂载在`/var/lib/mysql`。 `MYSQL_ROOT_PASSWORD`环境变量设置来自 Secret
的数据库密码。

```yaml
# /root/ordinaryroad/or-mysql/mysql-deployment.yaml

apiVersion: v1
kind: Service
metadata:
  name: mysql
  namespace: or-dev
spec:
  type: NodePort           # 配置为NodePort，外部可以访问
  ports:
    - port: 3306
      targetPort: 3306     # 容器暴露的端口，与Dockerfile暴露端口保持一致
      nodePort: 3306       # NodePort，外部访问的端口
  selector:
    tier: mysql
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mysql-pv-claim
  namespace: or-dev
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 20Gi
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
  namespace: or-dev
spec:
  selector:
    matchLabels:
      tier: mysql
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        tier: mysql
    spec:
      containers:
        - image: mysql/mysql-server:8.0.26
          name: mysql
          env:
            - name: TZ
              value: Asia/Shanghai
            - name: MYSQL_ROOT_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: mysql-pass
                  key: password
          ports:
            - containerPort: 3306
              name: mysql
          volumeMounts:
            - name: mysql-persistent-storage
              mountPath: /var/lib/mysql
      volumes:
        - name: mysql-persistent-storage
          persistentVolumeClaim:
            claimName: mysql-pv-claim
```

## 补充到 `kustomization.yaml` 文件。

```shell
# /root/ordinaryroad/or-mysql/kustomization.yaml

cat <<EOF >>./kustomization.yaml
resources:
  - mysql-deployment.yaml
EOF
```

## 应用

`kustomization.yaml`包含用于部署 WordPress 网站的所有资源以及 MySQL 数据库。您可以通过以下方式应用目录

```shell
kubectl apply -k ./
```

```
secret/or-mysql-pass-2fg88hk64d created
service/or-mysql created
deployment.apps/or-mysql created
persistentvolumeclaim/or-mysql-pv-claim created
```

# 创建有状态应用Redis

[使用 ConfigMap 来配置 Redis | Kubernetes](https://kubernetes.io/zh/docs/tutorials/configuration/configure-redis-using-configmap/)

## kustomization.yaml

```yaml
# /root/ordinaryroad/or-redis/kustomization.yaml
resources:
  - redis-config.yaml
  - redis-deployment.yaml
```

## redis-config.yaml

```yaml
# /root/ordinaryroad/or-redis/redis-config.yaml

apiVersion: v1
kind: ConfigMap
metadata:
  name: redis-config
  namespace: or-dev
data:
  redis-config: |
    bind '* -::*'
```

## redis-deployment.yaml

```yaml
# /root/ordinaryroad/or-redis/redis-deployment.yaml

apiVersion: v1
kind: Service
metadata:
  name: redis
  namespace: or-dev
spec:
  type: NodePort           # 配置为NodePort，外部可以访问
  ports:
    - port: 6379
      targetPort: 6379     # 容器暴露的端口，与Dockerfile暴露端口保持一致
      nodePort: 6379       # NodePort，外部访问的端口
  selector:
    tier: redis
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: redis-pv-claim
  namespace: or-dev
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: redis
  namespace: or-dev
spec:
  selector:
    matchLabels:
      tier: redis
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        tier: redis
    spec:
      containers:
        - image: redis:6.2.6
          name: redis
          ports:
            - containerPort: 6379
              name: redis
          volumeMounts:
            - name: redis-persistent-storage
              mountPath: /data
            - name: redis-config
              mountPath: /usr/local/etc/redis
      volumes:
        - name: redis-persistent-storage
          persistentVolumeClaim:
            claimName: redis-pv-claim
        - name: redis-config
          configMap:
            name: redis-config
            items:
              - key: redis-config
                path: redis.conf
```

## 应用

```shell
kubectl apply -k ./
```

```
configmap/redis-config created
service/redis created
deployment.apps/redis created
persistentvolumeclaim/redis-pv-claim created
```

# 创建有状态应用Minio

## kustomization.yaml

```yaml
# /root/ordinaryroad/minio/kustomization.yaml

secretGenerator:
  - name: minio-config
    namespace: or-dev
    literals:
      # Access key length should be at least 3, and secret key length at least 8 characters
      - MINIO_ROOT_USER=minio
      - MINIO_ROOT_PASSWORD=12345678
resources:
  - minio-deployment.yaml
```

## minio-deployment.yaml

```yaml
# /root/ordinaryroad/minio/minio-deployment.yaml

apiVersion: v1
kind: Service
metadata:
  name: minio
  namespace: or-dev
spec:
  type: NodePort           # 配置为NodePort，外部可以访问
  ports:
    - port: 9999
      targetPort: 9999     # 容器暴露的端口，与Dockerfile暴露端口保持一致
      nodePort: 9999       # NodePort，外部访问的端口
  selector:
    tier: minio
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: minio-pv-claim-data
  namespace: or-dev
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Gi
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: minio-pv-claim-config
  namespace: or-dev
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 1Gi
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: minio
  namespace: or-dev
spec:
  selector:
    matchLabels:
      tier: minio
  replicas: 1
  template:
    metadata:
      labels:
        tier: minio
    spec:
      containers:
        - name: minio
          image: minio/minio
          env:
            - name: MINIO_ROOT_USER
              valueFrom:
                secretKeyRef:
                  name: minio-config
                  key: MINIO_ROOT_USER
            - name: MINIO_ROOT_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: minio-config
                  key: MINIO_ROOT_PASSWORD
          ports:
            - containerPort: 9999
              name: minio
          volumeMounts:
            - name: minio-persistent-storage-data
              mountPath: /data
            - name: minio-persistent-storage-config
              mountPath: /root/.minio
          command:
            - /bin/sh
          args:
            - -c
            - minio server /data --console-address ":9999"
      volumes:
        - name: minio-persistent-storage-data
          persistentVolumeClaim:
            claimName: minio-pv-claim-data
        - name: minio-persistent-storage-config
          persistentVolumeClaim:
            claimName: minio-pv-claim-config
```

# 创建无状态应用Nacos

## kustomization.yaml

```yaml
# /root/ordinaryroad/nacos/kustomization.yaml

secretGenerator:
  - name: nacos-mysql-pass
    namespace: or-dev
    literals:
      - password=jnC8ZVj!TL%PJe8n
resources:
  - nacos-deployment.yaml
```

## nacos-deployment.yaml

```yaml
# /root/ordinaryroad/nacos/nacos-deployment.yaml

apiVersion: v1
kind: Service
metadata:
  name: nacos
  namespace: or-dev
spec:
  type: NodePort           # 配置为NodePort，外部可以访问
  ports:
    - port: 8848
      targetPort: 8848     # 容器暴露的端口，与Dockerfile暴露端口保持一致
      nodePort: 8848       # NodePort，外部访问的端口
  selector:
    tier: nacos
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nacos
  namespace: or-dev
spec:
  selector:
    matchLabels:
      tier: nacos
  replicas: 1
  template:
    metadata:
      labels:
        tier: nacos
    spec:
      containers:
        - name: nacos
          image: nacos/nacos-server:v2.0.4
          env:
            - name: PREFER_HOST_MODE
              value: "hostname"
            - name: MODE
              value: "standalone"
            - name: SPRING_DATASOURCE_PLATFORM
              value: "mysql"
            - name: MYSQL_SERVICE_DB_PARAM
              value: "useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true"
            - name: MYSQL_SERVICE_HOST
              value: "mysql.or-dev"
            - name: MYSQL_SERVICE_DB_NAME
              value: "or_config_pro"
            - name: MYSQL_SERVICE_PORT
              value: "3306"
            - name: MYSQL_SERVICE_USER
              value: "root"
            - name: MYSQL_SERVICE_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: nacos-mysql-pass
                  key: password
          ports:
            - containerPort: 8848
              name: nacos
```

# 开始部署OrdinaryRoad

## auth-server

### Dockerfile

```dockerfile
# /root/ordinaryroad/auth-server/Dockerfile

FROM moxm/java:1.8-full

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

ARG APP_NAME=ordinaryroad-auth-server

RUN mkdir -p /ordinaryroad/$APP_NAME/app

WORKDIR /ordinaryroad/$APP_NAME/app

COPY ./app/$APP_NAME.jar app.jar

EXPOSE 9302

# 防止日志中文乱码
ENV LANG=C.UTF-8

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 10; java -jar $JAVA_OPTS app.jar
```

### build并push

```shell
podman build -f Dockerfile -t 10.0.12.13:5000/ordinaryroad-auth-server

podman images
REPOSITORY                                TAG         IMAGE ID      CREATED            SIZE
10.0.12.13:5000/ordinaryroad-auth-server  latest      35a3ea535b69  43 minutes ago     493 MB
docker.io/library/registry                latest      2e200967d166  3 weeks ago        24.7 MB
docker.io/moxm/java                       1.8-full    bb1391003ead  22 months ago      422 MB

podman push ${imageId} 10.0.12.13:5000/ordinaryroad-auth-server
```

### deployment.yaml

```yaml
# /root/ordinaryroad/auth-server/deployment.yaml

apiVersion: v1
kind: Service
metadata:
  name: auth
  namespace: or-dev
spec:
  type: NodePort           # 配置为NodePort，外部可以访问
  ports:
    - port: 9302
      targetPort: 9302     # 容器暴露的端口，与Dockerfile暴露端口保持一致
      nodePort: 9302       # NodePort，外部访问的端口
  selector:
    tier: auth
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: auth
  namespace: or-dev
spec:
  selector:
    matchLabels:
      tier: auth
  replicas: 1
  template:
    metadata:
      labels:
        tier: auth
    spec:
      containers:
        - name: auth
          image: 10.0.12.13:5000/ordinaryroad-auth-server
          ports:
            - containerPort: 9302
              name: auth
```

# ~~使用Ingress暴露服务~~

[Ingress | Kubernetes](https://kubernetes.io/zh/docs/concepts/services-networking/ingress/#the-ingress-resource)

[k8s-(七）暴露服务的三种方式_新林。的博客-CSDN博客_k8s 服务暴露](https://blog.csdn.net/qq_21187515/article/details/112363072)

[k8s-(八）通过Ingress-nginx暴露service给外部网络访问_新林。的博客-CSDN博客_spanking外网](https://blog.csdn.net/qq_21187515/article/details/112497544)

步骤一，创建Ingress的yaml描述文件 首先创建ingress-nginx的文件目录（后面创建的ingress相关的文件都在这里面，便于维护） mkdir ingress-nginx 创建一个名称为deploy.yaml，内容如下
注：
1.这个yaml描述文件有点长，简单描述一下，里面主要创建了一个ingress-nginx的命名空间，nginx-ingress-controller的Deployment对象以及最后面创建ingress-nginx的Service，Service配置映射的端口
2.我安装的k8s版本是1.18.0

```yaml
kind: ConfigMap
apiVersion: v1
metadata:
  name: nginx-configuration
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: tcp-services
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: udp-services
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: default-http-backend
  namespace: or-dev
  labels:
    app: default-http-backend
spec:
  replicas: 1
  selector:
    matchLabels:
      app: default-http-backend
  template:
    metadata:
      labels:
        app: default-http-backend
    spec:
      terminationGracePeriodSeconds: 60
      containers:
        - name: default-http-backend
          image: registry.cn-qingdao.aliyuncs.com/kubernetes_xingej/defaultbackend-amd64:1.5      #建议提前在node节点下载镜像；
          livenessProbe:
            httpGet:
              path: /healthz
              port: 8080
              scheme: HTTP
            initialDelaySeconds: 30
            timeoutSeconds: 5
          ports:
            - containerPort: 8080
          resources:
            # 这里调整了cpu和memory的大小，可能不同集群限制的最小值不同，看部署失败的原因就清楚
            limits:
              cpu: 100m
              memory: 100Mi
            requests:
              cpu: 100m
              memory: 100Mi
---
apiVersion: v1
kind: Service
metadata:
  name: default-http-backend
  namespace: or-dev
  labels:
    app: default-http-backend
spec:
  ports:
    - port: 80
      targetPort: 8080
  selector:
    app: default-http-backend
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: nginx-ingress-serviceaccount
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: ClusterRole
metadata:
  name: nginx-ingress-clusterrole
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
rules:
  - apiGroups:
      - ""
    resources:
      - configmaps
      - endpoints
      - nodes
      - pods
      - secrets
    verbs:
      - list
      - watch
  - apiGroups:
      - ""
    resources:
      - nodes
    verbs:
      - get
  - apiGroups:
      - ""
    resources:
      - services
    verbs:
      - get
      - list
      - watch
  - apiGroups:
      - ""
    resources:
      - events
    verbs:
      - create
      - patch
  - apiGroups:
      - "extensions"
      - "networking.k8s.io"
    resources:
      - ingresses
    verbs:
      - get
      - list
      - watch
  - apiGroups:
      - "extensions"
      - "networking.k8s.io"
    resources:
      - ingresses/status
    verbs:
      - update
---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: Role
metadata:
  name: nginx-ingress-role
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
rules:
  - apiGroups:
      - ""
    resources:
      - configmaps
      - pods
      - secrets
      - namespaces
    verbs:
      - get
  - apiGroups:
      - ""
    resources:
      - configmaps
    resourceNames:
      # Defaults to "<election-id>-<ingress-class>"
      # Here: "<ingress-controller-leader>-<nginx>"
      # This has to be adapted if you change either parameter
      # when launching the nginx-ingress-controller.
      - "ingress-controller-leader-nginx"
    verbs:
      - get
      - update
  - apiGroups:
      - ""
    resources:
      - configmaps
    verbs:
      - create
  - apiGroups:
      - ""
    resources:
      - endpoints
    verbs:
      - get
---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: RoleBinding
metadata:
  name: nginx-ingress-role-nisa-binding
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: Role
  name: nginx-ingress-role
subjects:
  - kind: ServiceAccount
    name: nginx-ingress-serviceaccount
    namespace: or-dev
---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: ClusterRoleBinding
metadata:
  name: nginx-ingress-clusterrole-nisa-binding
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: nginx-ingress-clusterrole
subjects:
  - kind: ServiceAccount
    name: nginx-ingress-serviceaccount
    namespace: or-dev
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-ingress-controller
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
spec:
  replicas: 2
  selector:
    matchLabels:
      app.kubernetes.io/name: ingress-nginx
      app.kubernetes.io/part-of: ingress-nginx
  template:
    metadata:
      labels:
        app.kubernetes.io/name: ingress-nginx
        app.kubernetes.io/part-of: ingress-nginx
      annotations:
        prometheus.io/port: "10254"
        prometheus.io/scrape: "true"
    spec:
      # wait up to five minutes for the drain of connections
      terminationGracePeriodSeconds: 300
      serviceAccountName: nginx-ingress-serviceaccount
      nodeSelector:
        kubernetes.io/os: linux
      containers:
        - name: nginx-ingress-controller
          image: suisrc/ingress-nginx:0.30.0   #建议提前在node节点下载镜像；
          args:
            - /nginx-ingress-controller
            - --default-backend-service=$(POD_NAMESPACE)/default-http-backend
            - --configmap=$(POD_NAMESPACE)/nginx-configuration
            - --tcp-services-configmap=$(POD_NAMESPACE)/tcp-services
            - --udp-services-configmap=$(POD_NAMESPACE)/udp-services
            - --publish-service=$(POD_NAMESPACE)/ingress-nginx
            - --annotations-prefix=nginx.ingress.kubernetes.io
          securityContext:
            allowPrivilegeEscalation: true
            capabilities:
              drop:
                - ALL
              add:
                - NET_BIND_SERVICE
            # www-data -> 101
            runAsUser: 101
          env:
            - name: POD_NAME
              valueFrom:
                fieldRef:
                  fieldPath: metadata.name
            - name: POD_NAMESPACE
              valueFrom:
                fieldRef:
                  fieldPath: metadata.namespace
          ports:
            - name: http
              containerPort: 80
              protocol: TCP
            - name: https
              containerPort: 443
              protocol: TCP
          livenessProbe:
            failureThreshold: 3
            httpGet:
              path: /healthz
              port: 10254
              scheme: HTTP
            initialDelaySeconds: 10
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 10
          readinessProbe:
            failureThreshold: 3
            httpGet:
              path: /healthz
              port: 10254
              scheme: HTTP
            periodSeconds: 10
            successThreshold: 1
            timeoutSeconds: 10
          lifecycle:
            preStop:
              exec:
                command:
                  - /wait-shutdown
---
apiVersion: v1
kind: LimitRange
metadata:
  name: ingress-nginx
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
spec:
  limits:
    - min:
        memory: 90Mi
        cpu: 100m
      type: Container
---
apiVersion: v1
kind: Service
metadata:
  name: ingress-nginx
  namespace: or-dev
  labels:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
spec:
  type: NodePort
  ports:
    - name: http
      port: 80
      targetPort: 80
      protocol: TCP
      # HTTP
      nodePort: 80
    - name: https
      port: 443
      targetPort: 443
      protocol: TCP
      # HTTPS
      nodePort: 443
  selector:
    app.kubernetes.io/name: ingress-nginx
    app.kubernetes.io/part-of: ingress-nginx
```

