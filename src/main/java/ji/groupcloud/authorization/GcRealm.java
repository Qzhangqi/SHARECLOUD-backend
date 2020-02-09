package ji.groupcloud.authorization;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.dao.RolePermissionsRepository;
import ji.groupcloud.dto.Account;
import ji.groupcloud.dto.AccountRole;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GcRealm extends AuthorizingRealm {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    private RolePermissionsRepository rolePermissionsRepository; //暂时用不到

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();

        for (AccountRole accountRole : accountRoleRepository.findAllByUsername(userName)) {
            s.addRole(accountRole.getRole());
        }

        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取账号密码
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String username = token.getPrincipal().toString();
        String password = new String(t.getPassword());

        List<Account> justOne = accountRepository.findAllByUsername(username);
        if (justOne.isEmpty()) {
            throw new AuthenticationException("账户不存在");
        }

        if (!password.equals(justOne.get(0).getPassword())) {
            throw new AuthenticationException("密码错误");
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}