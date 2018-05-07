#!/bin/bash

set -x

docker run --rm \
    --name maven-build \
    -v "$(pwd)":/opt \
    -v ~/.m2:/root/.m2 \
    -v "$(pwd)"/tls_certs/client/client.keystore.jks:/etc/security/tls/client.keystore.jks:ro \
    -v "$(pwd)"/tls_certs/client/client.truststore.jks:/etc/security/tls/client.truststore.jks:ro \
    maven:3.5.3-alpine \
    mvn -f /opt/pom.xml \
    -Dkafka.topic=log4j2-kafka-topic \
    -DstorePassword=salzburg \
    -Dkafka.broker.url=$KAFKA_HOST:9092 \
    clean test
