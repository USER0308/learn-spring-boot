package com.constants;

import com.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageQueueConstants {
    public final static String taskStartQueue1 = "queue.user.task1.start";
    public final static String taskStopQueue1 = "queue.user.task1.stop";
    public final static String taskRestartQueue1 = "queue.user.task1.restart";
    public final static String taskSuccessQueue1 = "queue.user.task1.success";
    public final static String taskFailQueue1 = "queue.user.task1.fail";

    public final static String taskStartQueue2 = "queue.user.task2.start";
    public final static String taskStopQueue2 = "queue.user.task2.stop";
    public final static String taskRestartQueue2 = "queue.user.task2.restart";
    public final static String taskSuccessQueue2 = "queue.user.task2.success";
    public final static String taskFailQueue2 = "queue.user.task2.fail";

    public final static String taskStartQueue3 = "queue.user.task3.start";
    public final static String taskStopQueue3 = "queue.user.task3.stop";
    public final static String taskRestartQueue3 = "queue.user.task3.restart";
    public final static String taskSuccessQueue3 = "queue.user.task3.success";
    public final static String taskFailQueue3 = "queue.user.task3.fail";

    public static final String topicExchangeTask1 = "task1-exchange";
    public static final String topicExchangeTask2 = "task2-exchange";

    @Bean(taskStartQueue1)
    Queue taskStartQueue1() {
        return new Queue(taskStartQueue1, true);
    }

    @Bean(taskStopQueue1)
    Queue taskStopQueue1() {
        return new Queue(taskStopQueue1, true);
    }

    @Bean(taskRestartQueue1)
    Queue taskRestartQueue1() {
        return new Queue(taskRestartQueue1, true);
    }

    @Bean(taskSuccessQueue1)
    Queue taskSuccessQueue1() {
        return new Queue(taskSuccessQueue1, true);
    }

    @Bean(taskFailQueue1)
    Queue taskFailQueue1() {
        return new Queue(taskFailQueue1, true);
    }

    @Bean(topicExchangeTask1)
    TopicExchange topicExchangeTask1() {
        return new TopicExchange(topicExchangeTask1);
    }

    @Bean(taskStartQueue2)
    Queue taskStartQueue2() {
        return new Queue(taskStartQueue2, true);
    }

    @Bean(taskStopQueue2)
    Queue taskStopQueue2() {
        return new Queue(taskStopQueue2, true);
    }

    @Bean(taskRestartQueue2)
    Queue taskRestartQueue2() {
        return new Queue(taskRestartQueue2, true);
    }

    @Bean(taskSuccessQueue2)
    Queue taskSuccessQueue2() {
        return new Queue(taskSuccessQueue2, true);
    }

    @Bean(taskFailQueue2)
    Queue taskFailQueue2() {
        return new Queue(taskFailQueue2, true);
    }

    @Bean(topicExchangeTask2)
    TopicExchange topicExchangeTask2() {
        return new TopicExchange(topicExchangeTask2);
    }

    @Bean
    Binding bindingTask2Start(@Qualifier(taskStartQueue2) Queue queue, @Qualifier(topicExchangeTask2) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskStartQueue2);
    }

    @Bean
    Binding bindingTask2Stop(@Qualifier(taskStopQueue2) Queue queue, @Qualifier(topicExchangeTask2) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskStopQueue2);
    }

    @Bean
    Binding bindingTask2Restart(@Qualifier(taskRestartQueue2) Queue queue, @Qualifier(topicExchangeTask2) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskRestartQueue2);
    }

    @Bean
    Binding bindingTask2Success(@Qualifier(taskSuccessQueue2) Queue queue, @Qualifier(topicExchangeTask2) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskSuccessQueue2);
    }

    @Bean
    Binding bindingTask2Fail(@Qualifier(taskFailQueue2) Queue queue, @Qualifier(topicExchangeTask2) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskFailQueue2);
    }

    @Bean
    Binding bindingTask1Start(@Qualifier(taskStartQueue1) Queue queue, @Qualifier(topicExchangeTask1) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskStartQueue1);
    }

    @Bean
    Binding bindingTask1Stop(@Qualifier(taskStopQueue1) Queue queue, @Qualifier(topicExchangeTask1) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskStopQueue1);
    }

    @Bean
    Binding bindingTask1Restart(@Qualifier(taskRestartQueue1) Queue queue, @Qualifier(topicExchangeTask1) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskRestartQueue1);
    }

    @Bean
    Binding bindingTask1Success(@Qualifier(taskSuccessQueue1) Queue queue, @Qualifier(topicExchangeTask1) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskSuccessQueue1);
    }

    @Bean
    Binding bindingTask1Fail(@Qualifier(taskFailQueue1) Queue queue, @Qualifier(topicExchangeTask1) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(taskFailQueue1);
    }



    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routeKey, Object message) {
        log.info("sending message [{}]...", message);
        rabbitTemplate.convertAndSend(exchange, routeKey, message);
    }
}
