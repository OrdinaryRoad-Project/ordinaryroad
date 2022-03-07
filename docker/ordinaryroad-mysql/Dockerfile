FROM mysql/mysql-server:8.0.26

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

# MYSQL_ROOT_PASSWORD root账户密码
ENV TZ=Asia/Shanghai MYSQL_ROOT_PASSWORD=Gpt8GB9RlsPseeoy

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./sql /docker-entrypoint-initdb.d

COPY ./etc/my.cnf /etc/my.cnf