package ji.groupcloud;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.bean.Account;
import ji.groupcloud.bean.AccountRole;
import ji.groupcloud.exception.SMSException;
import ji.groupcloud.util.AuthCodeCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private AuthCodeCache authCodeCache;

    @Test
    public void TestSignUp() {
        Account admin = new Account();
        admin.setUsername("admin");
        admin.setPassword("123");
//        log.info(loginController.postAccount(admin).getData());

        AccountRole accountRole = new AccountRole();
        accountRole.setUsername("admin");
        accountRole.setRole("admin");

        accountRoleRepository.save(accountRole);

//        List<Account> accountList = accountRepository.findAll();
//        for (Account account : accountList) {
//            log.info(account.toString());
//        }
    }

    @Test
    public void TestSignIn() {

    }

    @Test
    public void someTest() {

    }
}
