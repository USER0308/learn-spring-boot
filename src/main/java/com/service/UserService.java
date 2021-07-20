package com.service;

import com.entity.User;

import java.util.List;

public interface UserService {
    Boolean checkNameExist(String name);
    User createUser(String userName);
    User updateUser(String uuid, String newName);
    User deleteUser(String uuid);
    User getUserByUuid(String uuid);
    User getUserByName(String name);
    List<User> listAllUser();
    List<User> findByNameLike(String nameLike);
}
