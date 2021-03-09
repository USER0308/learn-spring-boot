import com.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@RunWith(SpringRunner.class)
@EnableTransactionManagement
@SpringBootTest(classes = Application.class)
public class RedisTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void test()  {

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");

    }
}
