package com.sxt.service;

import java.util.List;

import org.springframework.ui.Model;

import com.github.pagehelper.PageInfo;
import com.sxt.dto.UserDto;
import com.sxt.pojo.User;

public interface IUserService {

	/**
	 * 根据条件查询用户信息
	 * 
	 * @param user
	 * @return
	 */
	public List<User> query(User user);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void addUser(User user) throws Exception;

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteUser(int id) throws Exception;

	/**
	 * 查询添加或者修改需要的信息
	 * @param id
	 * @param model
	 */
	public void getUserUpdateInfo(Integer id, Model model)throws Exception;

	public void saveOrUpdate(UserDto userDto)throws Exception;

	/**
	 * 根据条件查询分页数据
	 * 	
	 * @param userDto
	 * @return
	 */
	public PageInfo<User> queryPage(UserDto userDto)throws Exception;

}
