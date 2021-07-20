package com.controller.open;

import com.entity.User;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/openApi/v1/user")
public class UserForOpen {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getByUuid/{uuid}")
    public User getUserByUuid(@PathVariable("uuid") String uuid) {
        return userService.getUserByUuid(uuid);
    }
}
