package com.controller.open;

import com.entity.User;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/openApi/v1/user")
public class UserForOpen {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getByUuid")
    public User getUserByUuid(@RequestParam("uuid") String uuid) {
        return userService.getUserByUuid(uuid);
    }
}
