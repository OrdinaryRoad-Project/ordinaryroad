FROM node:14.17.3

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

ARG APP_NAME=ordinaryroad-ui

RUN mkdir -p /ordinaryroad/$APP_NAME/app

WORKDIR /ordinaryroad/$APP_NAME/app

COPY ./app .

EXPOSE 3001

#During build step, for npm/yarn, NODE_ENV=production or --production should NOT be used.
#ENV NODE_ENV=production

ENV HOST=0.0.0.0

# 提示nuxt权限不够，chmod -R 777 .
CMD npm install --no-package-lock; npm run build; npm run start