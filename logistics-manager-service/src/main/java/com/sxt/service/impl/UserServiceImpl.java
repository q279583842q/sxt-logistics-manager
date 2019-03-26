package com.sxt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.dto.UserDto;
import com.sxt.mapper.RoleMapper;
import com.sxt.mapper.UserMapper;
import com.sxt.pojo.Role;
import com.sxt.pojo.RoleExample;
import com.sxt.pojo.User;
import com.sxt.pojo.UserExample;
import com.sxt.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;

	@Override
	public List<User> query(User user) {
		UserExample example = new UserExample();
		if(user!=null ){
			if(!"".equals(user.getUserName())&&user.getUserName()!=null){
				example.createCriteria().andUserNameEqualTo(user.getUserName());
			}
		}
		return userMapper.selectByExample(example );
	}

	@Override
	public void addUser(User user) throws Exception {
		userMapper.insertSelective(user);
	}

	@Override
	public void updateUser(User user) throws Exception {
		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public void deleteUser(int id) throws Exception {
		// 1.删除管理关系
		userMapper.deleteRoleByUserId(id);
		// 2.删除用户信息
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void getUserUpdateInfo(Integer id, Model model) throws Exception {
		if(id !=null && id > 0){
			// 表示更新操作
			// 1.根据id查询用户信息
			User user = userMapper.selectByPrimaryKey(id);
			// 2.根据用户编号从T_USER_ROLE中获取角色信息
			List<Integer> roleIds = userMapper.selectRoleIdByUserId(id);
			model.addAttribute("user", user);
			model.addAttribute("roleIds", roleIds);
		}
		// 查询出所有的角色信息
		RoleExample roleExample = new RoleExample();
		List<Role> roles = roleMapper.selectByExample(roleExample );
		model.addAttribute("roles", roles);
	}

	@Override
	public void saveOrUpdate(UserDto userDto) throws Exception {
		// 1.获取User信息
		User user = userDto.getUser();
		// 2.获取分配的角色信息
		List<Integer> roleIds = userDto.getRoleIds();
		
		if(user.getUserId()!=null && user.getUserId() > 0){
			// 更新数据
			// 1.更新用户信息
			userMapper.updateByPrimaryKeySelective(user);
			// 2.根据用户编号删除所有的角色关系
			userMapper.deleteRoleByUserId(user.getUserId());
			
		}else{
			// 添加数据
			// 添加用户数据 T_USER  
			userMapper.insert(user);
		}
		
		// 添加用户和角色的中间表的数据 T_USER_ROLE userId?怎么获取
		if(roleIds !=null && roleIds.size() > 0){
			for (Integer rid : roleIds) {
				userMapper.insertUserIdAndRoleId(user.getUserId(),rid);
			}
		}
		
	}

	@Override
	public PageInfo<User> queryPage(UserDto userDto) throws Exception {
		// 设置分页的条件
		PageHelper.startPage(userDto.getPageNum(), userDto.getPageSize());
		List<User> list = this.query(userDto.getUser());
		PageInfo<User> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<Role> queryRoleByUserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return roleMapper.queryRoleByUserId(userId);
	}

	@Override
	public List<User> queryUserByRoleName(String roleSalesman) {
		
		return userMapper.queryUserByRoleName(roleSalesman);
	}

}
