package ji.groupcloud;

import ji.groupcloud.controller.AccountController;
import ji.groupcloud.controller.LoginController;
import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dto.Account;
import lombok.extern.slf4j.Slf4j;
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
    private AccountController accountController;

    @Autowired
    private LoginController loginController;

    @Test
    public void TestSignUp() {
        Account zq = new Account();
        zq.setUsername("zq");
        zq.setPassword("123");
        zq.setInviter("admin");
        log.info(accountController.postAccount(zq).getData());

        Account xt = new Account();
        xt.setUsername("xt");
        xt.setPassword("123");
        xt.setInviter("admin");
        log.info(accountController.postAccount(xt).getData());

        zq.setPassword("456");
        log.info(accountController.postAccount(zq).getData());

        List<Account> accountList = accountRepository.findAll();
        for (Account account : accountList) {
            log.info(account.toString());
        }
    }

    @Test
    public void TestSignIn() {
        Account zq = new Account();
        zq.setUsername("zq");
        zq.setPassword("123");
        log.info(loginController.login(zq).toString());
    }

}
