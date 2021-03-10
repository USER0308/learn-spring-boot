#!/bin/bash
sudo docker run -d --name nginx -p 80:80 -v /home/kexin/Data/nginx/config/:/etc/nginx/ -v /home/kexin/Data/nginx/logs/:/var/log/nginx nginx:1.12.2
sudo docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -v /home/kexin/Data/rabbitmq/data:/var/lib/rabbitmq/ -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3.8-management
sudo docker run -d --privileged=true -p 6379:6379 --restart always -v /home/kexin/Data/redis/config/:/etc/redis/ -v /home/kexin/Data/redis/data:/data --name redis redis:6.2.0
