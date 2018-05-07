#! /bin/bash

set -x

HOST_IP=192.168.0.10
export KAFKA_HOST=$HOST_IP
export KAFKA_PORT=9092

docker-compose -f docker/docker-compose.yaml up -d kafka.tls
./runTest.sh
