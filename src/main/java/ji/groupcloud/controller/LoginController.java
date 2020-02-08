package ji.groupcloud.controller;

import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;

public class LoginController {
    @PostMapping("/login")
    public Result<String> login(Account account) {
        return null;
    }
}
