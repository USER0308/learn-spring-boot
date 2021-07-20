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

    @GetMapping(value = "/getByUuid")
    public User getUserByUuid(@RequestParam("uuid") String uuid) {
        return userService.getUserByUuid(uuid);
    }

    @GetMapping(value = "/getByName")
    public User getUserByName(@RequestParam("name") String name) {
        return userService.getUserByName(name);
    }

    @GetMapping(value = "/checkNameExist")
    public Boolean checkNameExist(@RequestParam("name") String name) {
        return userService.checkNameExist(name);
    }

    @PostMapping(value = "/create")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest.getName());
    }

    @PutMapping(value = "/update")
    public User updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest.getUuid(), updateUserRequest.getNewName());
    }

    @DeleteMapping(value = "/delete")
    public User updateUser(@RequestParam("uuid") String uuid) {
        return userService.deleteUser(uuid);
    }
}
