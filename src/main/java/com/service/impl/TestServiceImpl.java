package com.service.impl;

import com.rabbitmq.MessageProducer;
import com.redis.RedisService;
import com.service.TestService;
import com.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    @Override
    public void testHttp() {
        String url = "https://baidu.com";
        Map<String, String> params = new HashMap<>();
        params.put("wd", "springboot");
        Response response = HttpUtil.get(url, params);
        if (response.isSuccessful() && null != response.body()) {
            try {
                String htmlResponse = response.body().string();
                log.info(htmlResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
