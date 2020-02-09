package ji.groupcloud;

import ji.groupcloud.dto.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DBTest {

    @Test
    public void TestAccount() {
        Account account = new Account();
        account.setId(1);
        account.setUsername("username");
        account.setPassword("password");
        log.info(account.toString());
    }

}
