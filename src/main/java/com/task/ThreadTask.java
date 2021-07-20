package com.task;

import com.dao.UserDAO;
import com.entity.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Service
@NoArgsConstructor
//public class ThreadTask implements Throwable<Boolean> {
public class ThreadTask implements Runnable {
//    @Autowired
    private UserDAO userDAO;

    public ThreadTask(UserDAO userDao) {
        this.userDAO = userDao;
    }

    @Override
//    public Boolean call() throws exception {
      public void run() {
        log.info("in call(), before finding user");
        List<User> userList = userDAO.findByNameLike("aaaFinally");
        if (null == userList || 0 == userList.size()) {
            log.info("userList is null or empty in call()");
//            return Boolean.FALSE;
        }
        log.info("in call() method, after finding user");
        System.out.println("in call() method");
        for (User user : userList) {
            log.info(user.toString());
        }
        log.info("before 1/0");
        int a = 1/0;
        log.info("after 1/0");
//        return Boolean.TRUE;
    }
}
