#!/bin/bash
echo "ENABLE_EUREKA_CLIENT=$ENABLE_EUREKA_CLIENT"
if [ 0"$ENABLE_EUREKA_CLIENT" = "0TRUE" ]; then
  echo 'starting eureka-client'
  java -jar /eip/eureka-client-standalone-0.1.0.jar -Deureka.client.props=application
else
  echo '环境变量 ENABLE_EUREKA_CLIENT 不为 TRUE，将不会启动 eureka-client'
fi
