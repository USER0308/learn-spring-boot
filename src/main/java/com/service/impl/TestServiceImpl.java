package com.service.impl;

import com.rabbitmq.MessageProducer;
import com.redis.RedisService;
import com.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private MessageProducer messageProducer;
    @Autowired
    @Qualifier("commonThreadPoolExecutor")
    private ThreadPoolExecutor commonThreadPoolExecutor;

    @Override
    public void testRedis(String key, String value) {
        if (redisService.hasKey(StringUtils.trimWhitespace(key))) {
            log.info("old value is [{}]", redisService.get(key));
        }
        log.info("setting value to key");
        redisService.set(key, value);
    }

    @Override
    public void testRabbitMq(String message, String key) {
        messageProducer.send(key, message);
    }

    @Override
    public void testThreadPool() {
        Observable.just(1).map(integer -> {
            log.info("current Thread Name = [{}]", Thread.currentThread().getName());
            return integer.toString();
        }).subscribeOn(Schedulers.from(commonThreadPoolExecutor)).subscribe(log::info);

    }
}
