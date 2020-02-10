package ji.groupcloud.controller;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.AccountRole;
import ji.groupcloud.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    /**
     * 修改管理员的账户和密码
     */
    @PostMapping("/account")
    public Result<String> postAccount(Account account) {
        Account preAccount = accountRepository.getOne(0);
        accountRoleRepository.deleteAllByUsername(preAccount.getUsername());

        account.setId(0);
        accountRepository.save(account);

        AccountRole accountRole = new AccountRole();
        accountRole.setUsername(account.getUsername());
        accountRole.setRole("admin");
        accountRoleRepository.save(accountRole);

        accountRole.setRole("user");
        accountRoleRepository.save(accountRole);

        return new Result<String>("0001", "修改成功", "修改成功");
    }
}
