package ji.groupcloud.authorization;

import ji.groupcloud.dao.AccountRepository;
import ji.groupcloud.dao.AccountRoleRepository;
import ji.groupcloud.dao.RolePermissionsRepository;
import ji.groupcloud.bean.Account;
import ji.groupcloud.bean.AccountRole;
import ji.groupcloud.util.InviteTokenCache;
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

    public GcRealm() {
        super();
        setAuthenticationTokenClass(AuthenticationToken.class);
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

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
        if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken t = (UsernamePasswordToken) token;
            String username = token.getPrincipal().toString();

            List<Account> justOne = accountRepository.findAllByUsername(username);
            if (justOne.isEmpty()) {
                throw new AuthenticationException("账户不存在");
            }

            return new SimpleAuthenticationInfo(username, "1", getName());
        } else if (token instanceof InviteToken) {
            String tokenStr = (String) token.getPrincipal();

            if (InviteTokenCache.verifyInviteToken(tokenStr) == null) {
                throw new AuthenticationException("认证信息不存在");
            }

            return new SimpleAuthenticationInfo(tokenStr, tokenStr, getName());
        } else {
            throw new AuthenticationException("token 类型异常");
        }
    }
}
