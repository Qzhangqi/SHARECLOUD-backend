package ji.groupcloud.controller;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.Result;
import ji.groupcloud.util.InviteTokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 获取注册邀请码
     */
    @GetMapping("/inviteToken")
    public Result<String> getInviteToken(String username) {
        List<Account> accountList = accountRepository.findAllByUsername(username);

        if (accountList.isEmpty())
            return new Result<String>("0000", "获取失败", "用户不存在");

        String tokenStr = InviteTokenCache.creatInviteToken(username);
        return new Result<String>("0001", "获取成功", tokenStr);
    }
}
