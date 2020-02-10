package ji.groupcloud.authorization;

import org.apache.shiro.authc.AuthenticationToken;

public class InviteToken implements AuthenticationToken {
    private String tokenStr;

    public InviteToken(String tokenStr) {
        this.tokenStr = tokenStr;
    }

    @Override
    public Object getPrincipal() {
        return tokenStr;
    }

    @Override
    public Object getCredentials() {
        return tokenStr;
    }
}
