#! /bin/bash

# mvn clean package
# ./oneLinerMaen.sh salzburg
mvn -Dtest.password=$1 test
