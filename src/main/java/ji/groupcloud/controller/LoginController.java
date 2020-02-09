package ji.groupcloud.controller;

import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public Result<String> login(Account account) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(account.getUsername(), account.getPassword()));

        if (subject.isAuthenticated()) {
            return new Result<String>("0001", "登录成功", "登录成功");
        } else {
            return new Result<String>("0000", "登录失败", "登录失败");
        }
    }
}
