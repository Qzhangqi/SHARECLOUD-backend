package ji.groupcloud.controller;

import ji.groupcloud.bean.AccountRole;
import ji.groupcloud.constant.Role;
import ji.groupcloud.constant.SessionAttr;
import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.bean.Account;
import ji.groupcloud.bean.Result;
import ji.groupcloud.dto.PostAccountDTO;
import ji.groupcloud.exception.SMSException;
import ji.groupcloud.util.AuthCodeCache;
import ji.groupcloud.util.InviteTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
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
            return new Result<String>(Result.OTHER_FAIL, "获取失败", "用户不存在");

        String tokenStr = InviteTokenCache.creatInviteToken(username);
        return new Result<String>(Result.SUCCESS, "获取成功", tokenStr);
    }

    /**
     * 获取短信验证码
     */
    @GetMapping("/AuthCode")
    public Result<String> getAuthCode(String phoneNumber) {
        try {
            AuthCodeCache.getAuthCode(phoneNumber);
        } catch (SMSException e) {
            log.error("短信服务异常", e);
            return new Result<String>(Result.OTHER_FAIL, "获取失败", e.getMessage());
        }
        return new Result<String>(Result.SUCCESS, "获取成功", "获取成功");
    }


    /**
     * 注册账户
     */
    @PostMapping("/account")
    public Result<String> postAccount(@RequestBody PostAccountDTO postAccountDTO) {
        String username = postAccountDTO.getUsername();
        List<Account> accountList = accountRepository.findAllByUsername(username);

        if (!accountList.isEmpty())
            return new Result<String>(Result.OTHER_FAIL, "账户已存在", "账户已存在");

        try {
            if (!AuthCodeCache.verifyAuthCode(postAccountDTO.getUsername(), postAccountDTO.getAuthcode())) {
                return new Result<String>(Result.OTHER_FAIL, "验证失败", "验证码错误");
            }
        } catch (SMSException e) {
            log.error("验证码验证失败", e);
            return new Result<String>(Result.OTHER_FAIL, "验证失败", e.getMessage());
        }

        Account account = new Account();
        account.setUsername(postAccountDTO.getUsername());
        accountRepository.save(account);
        AccountRole accountRole = new AccountRole();
        accountRole.setUsername(username);
        accountRole.setRole("user");
        accountRoleRepository.save(accountRole);

        return new Result<String>(Result.SUCCESS, "注册成功", "注册成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody Account account, HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        boolean rememberMe = true;
        subject.login(new UsernamePasswordToken(account.getUsername(), "1", rememberMe));

        if (subject.isAuthenticated()) {
            session.setAttribute("username", account.getUsername());
            return new Result<String>(Result.OTHER_FAIL, "登录成功", "登录成功");
        } else {
            return new Result<String>(Result.SUCCESS, "登录失败", "登录失败");
        }
    }

    /**
     * 获取邀请者
     */
    @RequiresRoles(Role.USER)
    @GetMapping("/getInviter")
    public Result<String> getInviter(HttpSession session) {
        String inviter = (String)session.getAttribute("username");

        if (inviter == null) {
            return new Result<String>(Result.OTHER_FAIL, "请求错误", "请求错误");
        } else {
            return new Result<String>(Result.SUCCESS, "获取成功", inviter);
        }
    }
}
