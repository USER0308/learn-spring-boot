#!/bin/bash
sudo docker run -d -p 6379:6379 redis:6.2.0
sudo docker run -d -p 8033:8033 learn-spring-boot:1.0.0
sudo docker ps |grep learn-spring-boot
