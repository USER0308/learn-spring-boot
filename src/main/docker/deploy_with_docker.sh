#!/bin/bash
$MAVEN_HOME/mvn clean package -Dmaven.test.skip=true -f ../../../pom.xml
cp ../../../target/learn-spring-boot-1.0-SNAPSHOT.jar ./
cp ../resources/application-docker.yml ./application.yml
sudo docker build -t learn-spring-boot:1.0.0 .
sudo docker-compose -f ./docker-compose.yaml up -d