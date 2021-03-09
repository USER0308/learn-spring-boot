import com.Application;
import com.dao.UserDAO;
import com.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = Application.class)
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;

    @Test
    public void addUser() {
        User user = new User();
        user.setName("user01");
        userDAO.save(user);
    }

    @Test
    public void getUser() {
        List<User> userList = userDAO.findByName("user02");
        if (null == userList) {
            log.info("userList is null");
        }
        log.info("userList is [{}]", userList.toString());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)
    public void outer() {
        User user = new User();
        user.setName("user77777777");
        userDAO.save(user);
//        inner();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ThreadTask threadTask = new ThreadTask(userDAO);
        executor.submit(threadTask);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Rollback(false)
    public void inner() {
        List<User> userList = userDAO.findByName("user05");
        if (null == userList || 0 == userList.size()) {
            log.info("userList is empty");
            return;
        }
        for (User user : userList) {
            log.info("user's id and name are:[{}], [{}]", user.getId(), user.getName());
        }
    }
}
