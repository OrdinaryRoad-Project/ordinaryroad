FROM nacos/nacos-server:2.0.3

MAINTAINER mjz(miaojinzhou@ordinaryroad.tech)

ENV PREFER_HOST_MODE="hostname" \
    MODE="standalone" \
    SPRING_DATASOURCE_PLATFORM="mysql" \
#    MYSQL_SERVICE_DB_PARAM="characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false"
    MYSQL_SERVICE_DB_PARAM="useUnicode=true&characterEncoding=utf-8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true"