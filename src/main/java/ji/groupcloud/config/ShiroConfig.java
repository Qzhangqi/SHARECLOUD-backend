package ji.groupcloud.config;

import ji.groupcloud.authorization.GcRealm;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("auth");
        //记住我cookie生效时间30天 ,单位秒
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }

    @Bean
    GcRealm gcRealm() {
        return new GcRealm();
    }

    @Bean
    SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(gcRealm());
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //指定 SecurityManager
        bean.setSecurityManager(securityManager());
        //登录页面
//        bean.setLoginUrl("/login");
        //登录成功页面
//        bean.setSuccessUrl("/index");
        //访问未获授权路径时跳转的页面
//        bean.setUnauthorizedUrl("/unauthorizedurl");
        //配置路径拦截规则，注意，要有序
//        Map<String, String> map = new LinkedHashMap<>();
        //anon 不需要登录就能访问
//        map.put("/login", "anon");
//        map.put("/noauthc", "anon");
//        //authc 需要认证才能访问
//        //roles 需要认证某种角色才能访问
//        map.put("/invitee/**", "roles[\"invitee\"]");
//        map.put("/user/**", "roles[\"user\"]");
//        map.put("/admin/**", "roles[\"admin\"]");

//        bean.setFilterChainDefinitionMap(map);
        return bean;
    }
}
