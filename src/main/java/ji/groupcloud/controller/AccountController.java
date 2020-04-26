package ji.groupcloud.controller;

import ji.groupcloud.constant.Role;
import ji.groupcloud.constant.SessionAttr;
import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.bean.Account;
import ji.groupcloud.bean.AccountRole;
import ji.groupcloud.bean.Result;
import ji.groupcloud.util.InviteTokenCache;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    /**
     * 获取注册邀请码
     */
    @RequiresRoles(Role.USER)
    @GetMapping("/inviteToken")
    public Result<String> getInviteToken(String username) {
        List<Account> accountList = accountRepository.findAllByUsername(username);

        if (accountList.isEmpty())
            return new Result<String>("0000", "获取失败", "用户不存在");

        String tokenStr = InviteTokenCache.creatInviteToken(username);
        return new Result<String>("0001", "获取成功", tokenStr);
    }

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
        AccountRole accountRole = new AccountRole();
        accountRole.setUsername(username);
        accountRole.setRole("user");
        accountRoleRepository.save(accountRole);

        return new Result<String>("0001", "注册成功", "注册成功");
    }

    /**
     * 登录
     */
    @RequiresGuest
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

    /**
     * 获取邀请者
     */
    @RequiresGuest
    @GetMapping("/getInviter")
    public Result<String> getInviter(HttpSession session) {
        Object inviter = session.getAttribute(SessionAttr.INVITER);

        if (inviter == null) {
            return new Result<String>("0000", "请求错误", "请求错误");
        } else {
            return new Result<String>("0001", "获取成功", (String) inviter);
        }
    }
}
