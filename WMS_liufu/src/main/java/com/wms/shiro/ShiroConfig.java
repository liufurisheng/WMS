package com.wms.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		Map<String,String> map = new HashMap<String, String>();
		//登录状态下才可以访问main页面，manage权限可访问manage页面，admin角色可访问admin页面
		map.put("/main", "authc");
		map.put("/main.html", "authc");
		map.put("/manage","perms[manage]");
		map.put("/admin", "roles[admin]");
		map.put("/user/*", "roles[admin]");//指定哪些角色可以访问哪些页面/user/
		//map.put("/clientfeign/*", "roles[admin]");
		
		factoryBean.setFilterChainDefinitionMap(map);
		//未登录状态下访问将跳转至login页面
		factoryBean.setLoginUrl("/login");
		//无授限状态下访问将请求unauthor
		factoryBean.setUnauthorizedUrl("/unauthor");
		return factoryBean;
	}
	
	@Bean
	public DefaultWebSecurityManager securityManager(@Qualifier("accoutRealm") CustomRealm accountRealm){
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(accountRealm);
		return manager;
	}
	
	@Bean
	public CustomRealm accoutRealm(){
		return new CustomRealm();
	}
	@Bean
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}
}

    