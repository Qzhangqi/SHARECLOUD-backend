package ji.groupcloud;

import ji.groupcloud.dto.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@Slf4j
public class UserRepositoryTest {

    @Test
    public void TestAccount() {
        Account account = new Account();
        account.setId(1);
        account.setUsername("username");
        account.setPassword("password");
        log.info(account.toString());
    }
}

