import com.Application;
import com.common.ThreadPoolExecutorWrapper;
import com.dao.UserDAO;
import com.entity.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor
@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = Application.class)
public class ThreadTask implements Callable<Boolean> {

    @Autowired
    private UserDAO userDAO;

    public ThreadTask(UserDAO userDao) {
        this.userDAO = userDao;
    }

    @Override
    public Boolean call() throws Exception {
        List<User> userList = userDAO.findByNameLike("user77777777");
        if (null == userList || 0 == userList.size()) {
            log.info("userList is null or empty in call()");
            return Boolean.FALSE;
        }
        log.info("in call() method");
        System.out.println("in call() method");
        for (User user : userList) {
            log.info(user.toString());
        }
        return Boolean.TRUE;
    }

    public static void main(String[] args) {
        ThreadTask task = new ThreadTask();
        ThreadPoolExecutorWrapper threadPoolExecutorWrapper = new ThreadPoolExecutorWrapper(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        threadPoolExecutorWrapper.submit(task);
    }
}
