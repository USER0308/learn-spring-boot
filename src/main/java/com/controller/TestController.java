package com.controller;

import com.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private TestService testService;


    @GetMapping("/testRedis")
    public void test(@RequestParam("key") String key, @RequestParam("value") String value) {
        testService.testRedis(key, value);
    }

    @GetMapping("/testRabbitMq")
    public void testSend(@RequestParam("message") String message, @RequestParam("key") String key) {
        testService.testRabbitMq(message, key);
    }

    @GetMapping("/testThreadPool")
    public void testThreadPool() {
        testService.testThreadPool();
    }

    @GetMapping("/testHttp")
    public void testHttp() {
        testService.testHttp();
    }
}
