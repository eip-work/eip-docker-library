#!/bin/bash
set -e
set -x

sed -i "/profiler.collector.ip=/ s/=.*/=${PINPOINT_COLLECTOR_IP}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.collector.tcp.port=/ s/=.*/=${PINPOINT_COLLECTOR_TCP_PORT}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.collector.stat.port=/ s/=.*/=${PINPOINT_COLLECTOR_STAT_PORT}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.collector.span.port=/ s/=.*/=${PINPOINT_COLLECTOR_SPAN_PORT}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.sampling.rate=/ s/=.*/=${PINPOINT_PROFILER_SAMPLING_RATE}/" /pinpoint-agent/pinpoint.config

sed -i "/level value=/ s/=.*/=\"${PINPOINT_AGENT_DEBUG_LEVEL}\"\/>/g" /pinpoint-agent/lib/log4j.xml

rm -f /pinpoint-agent/pinpoint-bootstrap.jar
ln -s /pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar /pinpoint-agent/pinpoint-bootstrap.jar

exec "$@"