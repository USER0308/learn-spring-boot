package com.service;

import com.entity.User;

import java.util.List;

public interface UserService {
    public User createUser(String userName);
    public User updateUser(String uuid, String newName);
    public User deleteUser(String uuid);
    public User getUserByUuid(String uuid);
    public User getUserByName(String name);
    public List<User> listAllUser();
    public List<User> findByNameLike(String nameLike);
}
