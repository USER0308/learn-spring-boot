package com.task.flowable;

import com.constants.MessageQueueConstants;
import com.rabbitmq.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Task1 {

    @Autowired
    private MessageQueueConstants messageProducer;

    @RabbitListener(queues = MessageQueueConstants.taskStartQueue1)
    public void start(Message message) {
        String s = new String(message.getBody());
        log.info("message is [{}]", s);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task1 start finish");
        s = s.concat(", task1 finish");
        log.info("begin send message[{}] to queue[{}]", s, MessageQueueConstants.taskSuccessQueue1);
        messageProducer.send(MessageQueueConstants.topicExchangeTask1, MessageQueueConstants.taskSuccessQueue1, s);
        log.info("begin send message[{}] to queue[{}]", s, MessageQueueConstants.taskStartQueue2);
        messageProducer.send(MessageQueueConstants.topicExchangeTask2, MessageQueueConstants.taskStartQueue2, s);
    }

    @RabbitListener(queues = MessageQueueConstants.taskStopQueue1)
    public void stop(Message message) {
        String s = new String(message.getBody());
        log.info("message is [{}]", s);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task stop finish");
        s = s.concat(", task1 stop");
        messageProducer.send(MessageQueueConstants.topicExchangeTask1, MessageQueueConstants.taskFailQueue1, s);
    }

    @RabbitListener(queues = MessageQueueConstants.taskRestartQueue1)
    public void restart(Message message) {
        String s = new String(message.getBody());
        log.info("message is [{}]", s);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task restart finish");
        s = s.concat(", task1 finish");
        messageProducer.send(MessageQueueConstants.topicExchangeTask1, MessageQueueConstants.taskSuccessQueue1, s);
    }
}
