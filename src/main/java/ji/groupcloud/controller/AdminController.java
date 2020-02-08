package ji.groupcloud.controller;

import ji.groupcloud.dto.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    /**
     * 修改管理员的账户和密码
     */
    @PostMapping("/admin/account")
    public void postAccount(Account account) {

    }

    /**
     * 获取注册码
     */
    @GetMapping("/inviteToken")
    public void getInviteToken() {

    }
}
