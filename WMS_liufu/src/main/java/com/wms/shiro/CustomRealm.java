package com.wms.shiro;

import java.util.HashSet;
import java.util.Set;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wms.entity.User;
import com.wms.repository.UserRepository;

@Component
public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
    private UserRepository userRepository;

	

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		User account = userRepository.findByName(token.getUsername());
		if(account != null){
			//若密码不正确则返回IncorrectCredentialsException异常
			return new SimpleAuthenticationInfo(account,account.getPassword(), getName());
		}
		//若用户名不存在则返回UnknownAccountException异常
		return null;
	}

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取当前登录的用户信息
		Subject subject = SecurityUtils.getSubject();
		User account = (User) subject.getPrincipal();
		//设置角色
		Set<String> rolesset = new HashSet<>();
		rolesset.add(account.getRole());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(rolesset);
		//设置权限
		info.addStringPermission(account.getPerms());
		return info;
	}
}
