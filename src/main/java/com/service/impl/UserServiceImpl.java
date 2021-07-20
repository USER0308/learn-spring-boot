package com.service.impl;

import com.constants.ErrorCode;
import com.dao.UserDAO;
import com.entity.User;
import com.exception.CustomException;
import com.service.UserService;
import com.utils.UuidGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public User createUser(String userName) {
        User user = new User();
        user.setUuid(UuidGenerator.generateWithName("User"));
        user.setName(userName);
        user.setEnableFlag(true);
        userDAO.save(user);
        return user;
    }

    @Override
    public User updateUser(String uuid, String newName) {
        User user = userDAO.getByUuid(uuid);
        log.info("check uuid if user exist");
        if (null == user) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        log.info("check name if user exist");
        User userExist = userDAO.getByName(newName);
        if (null != userExist) {
            throw new CustomException(ErrorCode.ALREADY_EXIST);
        }
        user.setName(newName);
        userDAO.save(user);
        return user;
    }

    @Override
    public User deleteUser(String uuid) {
        User user = userDAO.getByUuid(uuid);
        if (null == user) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        user.setEnableFlag(false);
        userDAO.save(user);
        return user;
    }

    @Override
    public User getUserByUuid(String uuid) {
        User user = userDAO.getByUuid(uuid);
        if (null == user) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        log.info("user is [{}]", user);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = userDAO.getByName(name);
        if (null == user) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        log.info("user is [{}]", user);
        return user;
    }


    @Override
    public List<User> listAllUser() {
        return userDAO.findAll();
    }

    @Override
    public List<User> findByNameLike(String nameLike) {
        return userDAO.findByNameLike(nameLike);
    }
}
