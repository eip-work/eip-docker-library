#!/bin/bash

cd eureka-client-standalone

mvn clean package -Dmaven.test.skip=true

cd ..

tag=eipwork/nginx

docker build -t $tag:latest .

if test "$1" != ""; then
  docker push $tag:latest
  docker tag $tag:latest $tag:$1
  docker push $tag:$1
fi
