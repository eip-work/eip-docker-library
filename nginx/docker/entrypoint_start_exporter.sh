#!/bin/bash
echo 'wait 10s to start nginx-vts-exporter'
sleep 10s
nginx-vts-exporter -nginx.scrape_timeout 10 -nginx.scrape_uri http://localhost:8080/status/format/json