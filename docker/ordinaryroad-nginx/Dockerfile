FROM nginx:1.21.3

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

COPY ./conf/nginx.conf /etc/nginx/nginx.conf

VOLUME $PWD/ssl /etc/nginx/ssl
VOLUME $PWD/dist /usr/share/nginx/html

EXPOSE 8090 8302 8021