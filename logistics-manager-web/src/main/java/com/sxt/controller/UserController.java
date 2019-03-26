package com.sxt.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.sxt.dto.UserDto;
import com.sxt.mapper.UserMapper;
import com.sxt.pojo.User;
import com.sxt.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userService;

	@RequestMapping("/query")
	public String query(User user,Model model){
		List<User> list = userService.query(user);
		model.addAttribute("list", list);
		return "user/user";
	}
	/**
	 * 请求该操作需要当前用户具有  "管理员"角色才行
	 * @param userDto
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequiresRoles(value={"管理员"})
	@RequestMapping("/queryPage")
	public String queryPage(UserDto userDto,Model model) throws Exception{
		PageInfo<User> pageModel = userService.queryPage(userDto);
		model.addAttribute("pageModel", pageModel);
		return "user/user";
	}
	
	/**
	 * 进入添加或者修改页面前触发的方法
	 *    添加
	 *    	查询所有的角色信息
	 *    修改
	 *    	查询所有的角色信息
	 *      根据id查询用户信息
	 *      根据id查询用户关联的角色信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/userUpdate")
	public String userUpdate(Integer id,Model model) throws Exception{
		userService.getUserUpdateInfo(id,model);
		return "user/userUpdate";
	}
	
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(UserDto userDto) throws Exception{
		userService.saveOrUpdate(userDto);
		return "redirect:/user/query";
	}
	
	@RequestMapping("/delete")
	public String deleteUser(Integer id) throws Exception{
		userService.deleteUser(id);
		return "redirect:/user/query";
	}
}
