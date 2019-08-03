FROM mysql/mysql-server:5.7.26

COPY docker/mysqld_exporter /usr/local/bin/

COPY docker/init_sql.sh /docker-entrypoint-initdb.d/
COPY docker/grant_exporter.sql /grant_exporter/grant_exporter.sql

RUN chmod +x /usr/local/bin/mysqld_exporter \
    && chmod +x /docker-entrypoint-initdb.d/init_sql.sh \
    && yum -y install vi \
    && echo "Asia/Shanghai" > /etc/timezone \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && rm -f /entrypoint.sh

COPY docker/entrypoint.sh /entrypoint.sh
COPY docker/entrypoint_start_eureka_client.sh /entrypoint_start_eureka_client.sh
COPY eureka-client-standalone/target/eureka-client-standalone-0.1.0.jar /eip/eureka-client-standalone-0.1.0.jar

ADD docker/jdk-8u202-linux-x64.tar.gz /var/lib/
ENV JAVA_HOME /var/lib/jdk1.8.0_202/
ENV PATH $PATH:/var/lib/jdk1.8.0_202/bin/

RUN chmod +x /entrypoint.sh \
    && chmod +x /entrypoint_start_eureka_client.sh

ENV DATA_SOURCE_NAME='exporter:eip_exporter_passwd@(localhost:3306)/'

ENV eureka.region=default
ENV eureka.name=no-name
ENV eureka.port=3306
ENV eureka.management.port=9104
ENV eureka.preferSameZone=true
ENV eureka.shouldUseDns=false
ENV eureka.serviceUrl.default=http://monitor-eureka:9000/eureka
