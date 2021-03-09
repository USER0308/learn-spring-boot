package com.dao;

import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDAO extends JpaRepository<User,Integer> {
    List<User> findByName(String name);

//    @Query(nativeQuery = true, value = "select * from user where uuid=:uuid and enable_flag=1")
    User findByUuid(String uuid);
}