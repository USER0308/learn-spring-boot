package com.controller;

import com.entity.User;
import com.service.TestService;
import com.service.UserService;
import com.task.ThreadTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RestController
@RequestMapping("/business/test")
@Api(value = "测试类")
public class TestController {
    @Autowired
    private UserService userService;
    @Autowired
    private TestService testService;

    @ApiOperation(value = "基本类型返回值String测试")
    @GetMapping("/string")
    public String returnString() {
        return "index";
    }

    @ApiOperation(value = "基本类型返回值Boolean测试")
    @GetMapping("/boolean")
    public Boolean returnBoolean() {
        return true;
    }

    @ApiOperation(value = "基本类型返回值Integer测试")
    @GetMapping("/int")
    public int returnInt() {
        return 200;
    }

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
        testService.testThreadPoolMethod1();
        testService.testThreadPoolMethod2();
    }

    @GetMapping("/testHttp")
    public void testHttp() {
        testService.testHttp();
    }



    @GetMapping(value = "test")
    @Transactional(rollbackFor = Exception.class)
    public String test() throws Exception{
        userService.createUser("aaaFinally");
//        inner();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ThreadTask threadTask = new ThreadTask();
        executor.submit(threadTask);
        return "doing task";
    }

    @GetMapping(value = "outter")
    @Transactional(rollbackFor = Exception.class)
    public String outter() throws Exception{
        userService.createUser("aaaFinally");
        inner();
        return "doing task";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void inner() {
        List<User> userList = userService.findByNameLike("user05");
        if (null == userList || 0 == userList.size()) {
            log.info("userList is empty");
            return;
        }
        for (User user : userList) {
            log.info("user's id and name are:[{}], [{}]", user.getId(), user.getName());
        }

    }
}
