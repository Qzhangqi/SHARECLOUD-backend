package ji.groupcloud.controller;

import ji.groupcloud.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AccountController {
    /**
     * 注册账户
     */
    @PostMapping("/account")
    public Result<String> postAccount() {
        return null;
    }
}
