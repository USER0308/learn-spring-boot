package com.controller.open;

import com.constants.ErrorCode;
import com.dao.UserDAO;
import com.entity.User;
import com.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/openApi")
public class IndexForOpen {
    @Autowired
    private UserDAO userDAO;

    @GetMapping(value = "/index")
    public String index() {
        return "{'message': 'index for open'}";
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
}
