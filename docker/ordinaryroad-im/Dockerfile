FROM moxm/java:1.8-full

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

ARG APP_NAME=ordinaryroad-im

RUN mkdir -p /ordinaryroad/$APP_NAME/app

WORKDIR /ordinaryroad/$APP_NAME/app

COPY ./app/$APP_NAME.jar app.jar

# 防止日志中文乱码
ENV LANG=C.UTF-8

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 10; java -jar $JAVA_OPTS app.jar
