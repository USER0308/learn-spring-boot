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
public class Task2 {

    @Autowired
    private MessageQueueConstants messageProducer;

    @RabbitListener(queues = MessageQueueConstants.taskStartQueue2)
    public void start(Message message) {
        String s = new String(message.getBody());
        log.info("message is [{}]", s);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task2 start finish");
        s = s.concat(", task2 finish");
        messageProducer.send(MessageQueueConstants.topicExchangeTask2, MessageQueueConstants.taskSuccessQueue2, s);
//        messageProducer.send(MessageQueueConstants.taskStartQueue3, s);
    }

    @RabbitListener(queues = MessageQueueConstants.taskStopQueue2)
    public void stop(Message message) {
        String s = new String(message.getBody());
        log.info("message is [{}]", s);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task stop finish");
        s = s.concat(", task2 stop");
        messageProducer.send(MessageQueueConstants.topicExchangeTask2, MessageQueueConstants.taskFailQueue2, s);
    }

    @RabbitListener(queues = MessageQueueConstants.taskRestartQueue2)
    public void restart(Message message) {
        String s = new String(message.getBody());
        log.info("message is [{}]", s);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("task restart finish");
        s = s.concat(", task2 finish");
        messageProducer.send(MessageQueueConstants.topicExchangeTask2, MessageQueueConstants.taskSuccessQueue2, s);
    }
}
