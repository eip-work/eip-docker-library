#!/bin/sh
echo "start nginx-prometheus-exporter"
nohup /entrypoint_start_exporter.sh &
echo "start eureka-client"
nohup /entrypoint_start_eureka_client.sh &
echo "start nginx"
nginx -g "daemon off;"