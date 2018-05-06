#/bin/bash

# run kafka in ssl mode
CONTAINER=kafka.tls
# CONTAINER=zookeeper
# run kafka in non ssl mode
# CONTAINER=kafka

export KAFKA_HOST=`ip -o -4 addr list wlp3s0 | awk '{print $4}' | cut -d/ -f1`

export KAFKA_PORT=9092
#export KAFKA_HOST=HOST_IP_ADDRESS

docker-compose up -d $CONTAINER
