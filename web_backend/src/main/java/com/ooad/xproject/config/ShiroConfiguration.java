package com.ooad.xproject.config;

import com.ooad.xproject.filter.URLPathMatchingFilter;
import com.ooad.xproject.realm.XRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String > filterChainDefinitionMap = new LinkedHashMap<>();
        Map<String, Filter> customizedFilter = new HashMap<>();

        // 设置自定义过滤器名称为 url
        customizedFilter.put("url", getURLPathMatchingFilter());

        // 对管理接口的访问启用自定义拦截（url 规则），即执行 URLPathMatchingFilter 中定义的过滤方法
        filterChainDefinitionMap.put("/api/all/**", "url");
        filterChainDefinitionMap.put("/api/admin/**", "url");
        filterChainDefinitionMap.put("/api/teacher/**", "url");
        filterChainDefinitionMap.put("/api/student/**", "url");

        // 启用自定义过滤器
        shiroFilterFactoryBean.setFilters(customizedFilter);
        filterChainDefinitionMap.put("/api/authentication", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getXRealm());
        return securityManager;
    }

    @Bean
    public XRealm getXRealm() {
        XRealm xRealm = new XRealm();
        xRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return xRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("EVANNIGHTLY_WAOU".getBytes());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200);     // 3 days
        return simpleCookie;
    }

    public URLPathMatchingFilter getURLPathMatchingFilter() {
        return new URLPathMatchingFilter();
    }
}