package com.sxt.dto;

import java.util.List;

import com.sxt.pojo.User;

/**
 * 用户数据传输对象
 * @author dengp
 *
 */
public class UserDto extends BasePage {
	
	private User user;

	private List<Integer> roleIds;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}
	
}
