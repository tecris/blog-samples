#! /bin/bash

export KAFKA_PORT=9092
export KAFKA_HOST=kafka.tls

docker-compose up -d kafka.tls 
sleep 5
docker-compose up run-test

docker rm -f log4j2-kafka-test

docker-compose down
