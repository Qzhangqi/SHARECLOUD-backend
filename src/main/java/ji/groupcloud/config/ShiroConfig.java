package ji.groupcloud.config;

import ji.groupcloud.authorization.GcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    GcRealm gcRealm() {
        return new GcRealm();
    }

    @Bean
    SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(gcRealm());
        return manager;
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //指定 SecurityManager
        bean.setSecurityManager(securityManager());
        //登录页面
        bean.setLoginUrl("/login");
        //登录成功页面
        bean.setSuccessUrl("/index");
        //访问未获授权路径时跳转的页面
        bean.setUnauthorizedUrl("/unauthorizedurl");
        //配置路径拦截规则，注意，要有序
        Map<String, String> map = new LinkedHashMap<>();
        //anon 不需要登录就能访问
        map.put("/login", "anon");
        //authc 需要认证才能访问
        map.put("/user/**", "authc");
        //roles 需要认证某种角色才能访问
        map.put("/admin/**", "roles[\"admin\"]");

        bean.setFilterChainDefinitionMap(map);
        return bean;
    }
}
