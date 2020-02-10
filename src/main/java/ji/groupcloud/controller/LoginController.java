package ji.groupcloud.controller;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.AccountRole;
import ji.groupcloud.dto.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    /**
     * 注册账户
     */
    @PostMapping("/invitee/account")
    public Result<String> postAccount(Account account) {
        String username = account.getUsername();
        List<Account> accountList = accountRepository.findAllByUsername(username);

        if (!accountList.isEmpty())
            return new Result<String>("0000", "用户名已存在", "用户名已存在");

        accountRepository.save(account);
        AccountRole accountRole = new AccountRole();
        accountRole.setUsername(username);
        accountRole.setRole("user");
        accountRoleRepository.save(accountRole);

        return new Result<String>("0001", "注册成功", "注册成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<String> login(Account account) {
        Subject subject = SecurityUtils.getSubject();
        boolean rememberMe = true;
        subject.login(new UsernamePasswordToken(account.getUsername(), account.getPassword(), rememberMe));

        if (subject.isAuthenticated()) {
            return new Result<String>("0001", "登录成功", "登录成功");
        } else {
            return new Result<String>("0000", "登录失败", "登录失败");
        }
    }
}
