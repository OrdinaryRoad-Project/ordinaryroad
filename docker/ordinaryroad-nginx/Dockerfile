FROM nginx:1.21.3

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

COPY ./ssl/auth-server.ordinaryroad.tech.crt /etc/nginx/ssl/auth-server.ordinaryroad.tech.crt
COPY ./ssl/auth-server.ordinaryroad.tech.key /etc/nginx/ssl/auth-server.ordinaryroad.tech.key
COPY ./ssl/ordinaryroad.tech.crt /etc/nginx/ssl/ordinaryroad.tech.crt
COPY ./ssl/ordinaryroad.tech.key /etc/nginx/ssl/ordinaryroad.tech.key

EXPOSE 8090 8302