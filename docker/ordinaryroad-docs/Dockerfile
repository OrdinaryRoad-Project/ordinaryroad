FROM nginx:1.21.3

MAINTAINER mjz(or-mjz@qq.com)

ARG APP_NAME=ordinaryroad-docs

RUN mkdir -p /ordinaryroad/$APP_NAME/app

WORKDIR /ordinaryroad/$APP_NAME/app

COPY ./app .

COPY ./conf.d /etc/nginx/conf.d

EXPOSE 80