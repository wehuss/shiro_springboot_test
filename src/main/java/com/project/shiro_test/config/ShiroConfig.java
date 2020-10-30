package com.project.shiro_test.config;

import com.project.shiro_test.realm.JWTRealm;
import com.project.shiro_test.realm.PasswordLoginRealm;
import com.project.shiro_test.realm.UserModularRealmAuthenticator;
import com.project.shiro_test.filter.JWTFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.*;

@Configuration
@Slf4j
public class ShiroConfig {

    @Bean("hashedCredentialsMatcher")
    //配置解码方式
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置哈希算法名称
        matcher.setHashAlgorithmName("MD5");
        // 设置哈希迭代次数
        matcher.setHashIterations(1024);
        // 设置存储凭证十六进制编码
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    @Bean
    public PasswordLoginRealm passwordRealm(HashedCredentialsMatcher matcher) {
        PasswordLoginRealm passwordLoginRealm = new PasswordLoginRealm();
        //将解码方式设置为自定义的hashedCredentialsMatcher
        passwordLoginRealm.setCredentialsMatcher(matcher);
        return passwordLoginRealm;
    }

    @Bean
    public UserModularRealmAuthenticator userModularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        //AtLeastOneSuccessfulStrategy的意思为只要成功通过一个realm就算验证成功
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }


    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(JWTRealm realm, PasswordLoginRealm passwordLoginRealm) {
        log.info("getManager注册realm");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setAuthenticator(userModularRealmAuthenticator());
//        manager.setRealm(realm);
//        manager.set
        Collection<Realm> realmList = new ArrayList<>();
        realmList.add(realm);
        realmList.add(passwordLoginRealm);
        manager.setRealms(realmList);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        log.info("开始注册过滤器");

        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("appFilter", new JWTFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);

        //创建url认证规则表，权重为先到后
        Map<String, String> filterRuleMap = new LinkedHashMap<>();//如果将LinkedHashMap换成HashMap则无法进行权重验证
        //放行login
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/user/register", "anon");
        // 所有请求通过我们自己的Filter
        filterRuleMap.put("/user/**", "appFilter");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        log.info("defaultAdvisorAutoProxyCreator被调用,开始注入shiro");
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();

        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        log.info("开始注入shiro----authorizationAttributeSourceAdvisor");
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
