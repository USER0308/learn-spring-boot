package com.controller;

import com.rabbitmq.MessageProducer;
import com.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("/testRedis")
    public void test(@RequestParam("key") String key, @RequestParam("value") String value) {
        if (redisService.hasKey(StringUtils.trimWhitespace(key))) {
            log.info("old value is [{}]", redisService.get(key));
        }
        log.info("setting value to key");
        redisService.set(key, value);
    }

    @GetMapping("/testRabbitMq")
    public void testSend(@RequestParam("message") String message, @RequestParam("key") String key) {
        messageProducer.send(key, message);
    }
}
