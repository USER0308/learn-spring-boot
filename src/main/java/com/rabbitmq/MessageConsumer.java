package com.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    @RabbitListener(queues = "queue.test")
    public void testQueueProcess(String message) {
        log.info("receive message=[{}] from [queue.test]", message);
    }
}
