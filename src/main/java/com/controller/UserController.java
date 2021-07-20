package com.controller;

import com.common.ThreadPoolExecutorWrapper;
import com.dao.UserDAO;
import com.entity.User;
import com.request.CreateUserRequest;
import com.request.UpdateUserRequest;
import com.service.UserService;
import com.task.ThreadTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;


    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/list")
    public List<User> listUser() {
        return userService.listAllUser();
    }

    @GetMapping(value = "/getByUuid/{uuid}")
    public User getUserByUuid(@PathVariable("uuid") String uuid) {
        return userService.getUserByUuid(uuid);
    }

    @GetMapping(value = "/getByName/{name}")
    public User getUserByName(@PathVariable("name") String name) {
        return userService.getUserByName(name);
    }

    @PostMapping(value = "/create")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest.getName());
    }

    @PutMapping(value = "/update")
    public User updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest.getUuid(), updateUserRequest.getNewName());
    }

    @DeleteMapping(value = "/delete/{uuid}")
    public User updateUser(@PathVariable("uuid") String uuid) {
        return userService.deleteUser(uuid);
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
        List<User> userList = userDAO.findByNameLike("user05");
        if (null == userList || 0 == userList.size()) {
            log.info("userList is empty");
            return;
        }
        for (User user : userList) {
            log.info("user's id and name are:[{}], [{}]", user.getId(), user.getName());
        }

    }
}
