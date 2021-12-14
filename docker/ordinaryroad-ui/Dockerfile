FROM node:14.17.3

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

ARG APP_NAME=ordinaryroad-ui

RUN mkdir -p /ordinaryroad/$APP_NAME/app

WORKDIR /ordinaryroad/$APP_NAME/app

EXPOSE 3001

ENV NODE_ENV=production HOST=0.0.0.0

# 提示nuxt权限不够，chmod -R 777 .
CMD npm run build; npm run start