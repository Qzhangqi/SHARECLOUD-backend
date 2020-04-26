package ji.groupcloud.controller;

import ji.groupcloud.constant.Role;
import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.bean.Account;
import ji.groupcloud.bean.AccountRole;
import ji.groupcloud.bean.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
    @RequiresRoles(Role.ADMIN)
    @PostMapping("/account")
    public Result<String> postAccount(Account account) {

        String adminName = accountRoleRepository.findAllByRole(Role.ADMIN).get(0).getUsername();

        accountRepository.deleteAllByUsername(adminName);

        accountRoleRepository.deleteAllByUsername(adminName);

        accountRepository.save(account);

        AccountRole accountRole = new AccountRole();
        accountRole.setUsername(account.getUsername());
        accountRole.setRole(Role.ADMIN);
        accountRoleRepository.save(accountRole);

        accountRole.setRole(Role.USER);
        accountRoleRepository.save(accountRole);

        return new Result<String>("0001", "修改成功", "修改成功");
    }
}
