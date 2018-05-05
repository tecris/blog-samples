#! /bin/bash

# mvn clean package
java -DstorePassword=$1 -jar target/log4j2.kafka-1.0-SNAPSHOT.jar

