package ji.groupcloud.controller;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    /**
     * 注册账户
     */
    @PostMapping("/account")
    public Result<String> postAccount(Account account) {
        String username = account.getUsername();
        List<Account> accountList = accountRepository.findAllByUsername(username);

        if (!accountList.isEmpty())
            return new Result<String>("0000", "用户名已存在", "用户名已存在");

        accountRepository.save(account);

        return new Result<String>("0000", "注册成功", "注册成功");
    }
}
