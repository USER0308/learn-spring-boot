package com.service;

public interface TestService {
    void testRedis(String key, String value);
    void testRabbitMq(String message, String key);
    void testThreadPoolMethod1();
    void testThreadPoolMethod2();
    void testHttp();
}
