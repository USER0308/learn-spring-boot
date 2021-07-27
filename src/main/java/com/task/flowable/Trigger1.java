package com.task.flowable;

import com.constants.MessageQueueConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Trigger1 {
    @Autowired
    private MessageQueueConstants messageProducer;


    public void triggerStart() {
        Object message = "starting task1";
        log.info("message is [{}]", message);
        messageProducer.send(MessageQueueConstants.topicExchangeTask1, MessageQueueConstants.taskStartQueue1, message);
    }

    public void triggerStop() {
        Object message = "stopping task1";
        log.info("message is [{}]", message);
        messageProducer.send(MessageQueueConstants.topicExchangeTask1, MessageQueueConstants.taskStopQueue1, message);
    }

    public void triggerRetart() {
        Object message = "restarting task1";
        log.info("message is [{}]", message);
        messageProducer.send(MessageQueueConstants.topicExchangeTask1, MessageQueueConstants.taskRestartQueue1, message);
    }
}
