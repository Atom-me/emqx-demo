# emqx-demo


# emqx 4.2.9  installation

doc: https://www.emqx.cn/downloads#

### 1、下载镜像

> docker pull emqx/emqx:4.2.9

### 2、创建临时EMQX容器


```shell
docker run -d \
--name emqx-4.2.9 \
-p 1883:1883 \
-p 8883:8883 \
-p 8083:8083 \
-p 8084:8084 \
-p 8081:8081 \
-p 18083:18083 \
emqx/emqx:4.2.9
```

### 3、在服务器上创建emqx挂载目录

```shell
mkdir -p /Users/atom/work/docker/emqx/{etc,lib,data,log}
```

### 4、将临时EMQX容器的文件拷贝到服务器

```shell
docker cp emqx-4.2.9:/opt/emqx/etc /Users/atom/work/docker/emqx
docker cp emqx-4.2.9:/opt/emqx/lib /Users/atom/work/docker/emqx
docker cp emqx-4.2.9:/opt/emqx/data /Users/atom/work/docker/emqx
docker cp emqx-4.2.9:/opt/emqx/log /Users/atom/work/docker/emqx
```

### 5、删除当前临时emqx容器

```shell
docker stop emqx-4.2.9
docker rm emqx-4.2.9
```

### 6、重新启动一个EMQX(挂载目录到服务器)

```shell
docker run -d \
--name emqx-4.2.9 \
-p 1883:1883 \
-p 8883:8883 \
-p 8083:8083 \
-p 8084:8084 \
-p 8081:8081 \
-p 18083:18083 \
-v /Users/atom/work/docker/emqx/etc:/opt/emqx/etc \
-v /Users/atom/work/docker/emqx/lib:/opt/emqx/lib \
-v /Users/atom/work/docker/emqx/data:/opt/emqx/data \
-v /Users/atom/work/docker/emqx/log:/opt/emqx/log \
emqx/emqx:4.2.9
```

### 7、Dashboard

```shell
http://localhost:18083

admin/public

```



# emqx 3.2.4  installation

```shell
docker pull emqx/emqx:v3.2.4



docker run -d \
--name emqx-3.2.4 \
-p 1883:1883 \
-p 8883:8883 \
-p 8083:8083 \
-p 8084:8084 \
-p 8081:8081 \
-p 18083:18083 \
emqx/emqx:v3.2.4




docker cp emqx-3.2.4:/opt/emqx/etc /Users/atom/work/docker/emqx
docker cp emqx-3.2.4:/opt/emqx/lib /Users/atom/work/docker/emqx
docker cp emqx-3.2.4:/opt/emqx/data /Users/atom/work/docker/emqx
docker cp emqx-3.2.4:/opt/emqx/log /Users/atom/work/docker/emqx



docker run -d \
--name emqx-3.2.4 \
-p 1883:1883 \
-p 8883:8883 \
-p 8083:8083 \
-p 8084:8084 \
-p 8081:8081 \
-p 18083:18083 \
-v /Users/atom/work/docker/emqx/etc:/opt/emqx/etc \
-v /Users/atom/work/docker/emqx/lib:/opt/emqx/lib \
-v /Users/atom/work/docker/emqx/data:/opt/emqx/data \
-v /Users/atom/work/docker/emqx/log:/opt/emqx/log \
emqx/emqx:v3.2.4
```

