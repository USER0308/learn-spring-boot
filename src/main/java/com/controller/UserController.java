package com.controller;

import com.common.ThreadPoolExecutorWrapper;
import com.exception.CustomException;
import com.constants.ErrorCode;
import com.dao.UserDAO;
import com.entity.User;
import com.request.CreateUserRequest;
import com.request.UpdateUserRequest;
import com.task.ThreadTask;
import com.utils.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@EnableTransactionManagement
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;


    @GetMapping(value = "/index")
    public String index() {
        return "\\{'message': 'index'\\}";
    }

    @GetMapping(value = "/get/{uuid}")
    public User getUser(@PathVariable("uuid") String uuid) {
        User user = userDAO.findByUuid(uuid);
        if (null == user) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        log.info("user is [{}]", user);
        return user;
    }

    @PostMapping(value = "/create")
    public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUuid(UuidGenerator.generateWithName("User"));
        user.setName(createUserRequest.getName());
        user.setEnableFlag(true);
        userDAO.save(user);
        return ResponseEntity.ok(user.toString());
    }

    @PutMapping(value = "/update")
    public ResponseEntity updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        User user = userDAO.findByUuid(updateUserRequest.getUuid());
        if (null == user) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        user.setName(updateUserRequest.getNewName());
        userDAO.save(user);
        return ResponseEntity.ok(user.toString());
    }





    @GetMapping(value = "/thread")
    @Transactional(rollbackFor = Exception.class)
    public void testThreadPool() {
        ThreadTask task = new ThreadTask();
        ThreadPoolExecutorWrapper threadPoolExecutorWrapper = new ThreadPoolExecutorWrapper(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        threadPoolExecutorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                log.info("in thread");
            }
        });
    }



    @GetMapping(value = "test")
    @Transactional(rollbackFor = Exception.class)
    public String test() throws Exception{
        User user = new User();
        user.setName("aaaFinally");
        userDAO.save(user);
//        inner();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ThreadTask threadTask = new ThreadTask(userDAO);
        executor.submit(threadTask);
        return "doing task";
    }

    @GetMapping(value = "outter")
    @Transactional(rollbackFor = Exception.class)
    public String outter() throws Exception{
        User user = new User();
        user.setName("aaaFinally");
        userDAO.save(user);
        inner();
        return "doing task";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void inner() {
        List<User> userList = userDAO.findByName("user05");
        if (null == userList || 0 == userList.size()) {
            log.info("userList is empty");
            return;
        }
        for (User user : userList) {
            log.info("user's id and name are:[{}], [{}]", user.getId(), user.getName());
        }

    }
}
