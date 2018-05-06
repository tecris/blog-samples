#! /bin/bash

# mvn clean package
# ./oneLinerJava.sh salzburg
java -DstorePassword=$1 -jar target/log4j2.kafka-1.0-SNAPSHOT.jar

