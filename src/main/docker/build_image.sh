#!/bin/bash
cp ../../../target/learn-spring-boot-1.0-SNAPSHOT.jar ./
cp ../resources/application-test.yml ./application.yml
sudo docker build -t learn-spring-boot:1.0.0 .
