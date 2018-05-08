#! /bin/bash

export KAFKA_PORT=9092
export KAFKA_HOST=kafka

docker-compose up -d kafka
sleep 5
docker-compose up run-test

docker-compose down
