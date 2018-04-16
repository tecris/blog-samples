#!/bin/bash

docker run --rm \
    --name maven-build \
    -v "$(pwd)":/opt \
    -v ~/.m2:/root/.m2 \
    maven:3.5.3-alpine \
    mvn -f /opt/pom.xml clean compile
