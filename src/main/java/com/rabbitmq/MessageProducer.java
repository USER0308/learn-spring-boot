package com.rabbitmq;

import com.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String key, Object message) {
        log.info("sending message [{}]...", message);
        rabbitTemplate.convertAndSend(RabbitMqConfig.topicExchangeName, key, message);
    }

}
