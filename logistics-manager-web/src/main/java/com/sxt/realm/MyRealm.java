package com.sxt.realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.sxt.pojo.Role;
import com.sxt.pojo.User;
import com.sxt.service.IUserService;

public class MyRealm extends  AuthorizingRealm{
	
	@Resource
	private IUserService userService;

	/**
	 * 认证的方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取登录提交的账号信息
		UsernamePasswordToken t = (UsernamePasswordToken) token;
		String username = t.getUsername();
		User user = new User();
		System.out.println("---->"+username);
		user.setUserName(username);
		// 完成账号认证
		List<User> list = userService.query(user);
		if(list == null){
			// 表示账号不存在
			return null;
		}else if(list.size() > 1){
			throw new AuthenticationException("账号过多......");
		}
		user = list.get(0);
		return new SimpleAuthenticationInfo(user, user.getPassword(), "dpb");
	}
	
	/**
	 * 授权的方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取认证的信息
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			List<Role> list = userService.queryRoleByUserId(user.getUserId());
			if(list != null && list.size() > 0){
				for (Role role : list) {
					// 授权操作
					info.addRole(role.getRoleName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
}
