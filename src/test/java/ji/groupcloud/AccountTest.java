package ji.groupcloud;

import ji.groupcloud.controller.AccountController;
import ji.groupcloud.controller.LoginController;
import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.AccountRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private LoginController loginController;

    @Test
    public void TestSignUp() {
        Account admin = new Account();
        admin.setUsername("admin");
        admin.setPassword("123");
        log.info(loginController.postAccount(admin).getData());

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
        Account zq = new Account();
        zq.setUsername("zq");
        zq.setPassword("123");
        log.info(loginController.login(zq).toString());
    }

    @Test
    public void someTest() {
        String tokenStr = RandomStringUtils.random(20, true, true);
        log.info(tokenStr);
    }
}
