package com.sxt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sxt.dto.CustomerDto;
import com.sxt.mapper.CustomerMapper;
import com.sxt.pojo.BasicData;
import com.sxt.pojo.Customer;
import com.sxt.pojo.CustomerExample;
import com.sxt.pojo.Role;
import com.sxt.pojo.User;
import com.sxt.service.IBasicService;
import com.sxt.service.ICustomerService;
import com.sxt.service.IUserService;
import com.sxt.utils.Constant;

@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Resource
	public CustomerMapper customerMapper;
	
	@Resource
	public IUserService userService;
	
	@Resource
	public IBasicService basicService;

	/**
	 * 添加
	 * 	1.所有的业务员
	 *  2.基础数据中的常用区间查询
	 * 	
	 * 修改
	 * 	1.所有的业务员
	 *  2.基础数据中的常用区间查询
	 *  3.根据客户编号查询客户详细信息
	 */
	@Override
	public void getUpdateInfo(Integer id, Model model) {
		// 1.查询所有的业务员
		List<User> users = userService.queryUserByRoleName(Constant.ROLE_SALESMAN);
		// 2.查询基础数据中的常用区间
		List<BasicData> intervals = basicService.getBasicDataByParentName(Constant.BASIC_COMMON_INTERVAL);
		
		model.addAttribute("users", users);
		model.addAttribute("intervals", intervals);
		if(id != null && id > 0){
			// 表示更新操作
			Customer customer = new Customer();
			customer.setCustomerId(id);
			// 根据客户编号查询出 客户详细信息
			List<CustomerDto> list = customerMapper.queryView(customer);
			if(list !=null && list.size() == 1){
				model.addAttribute("dto", list.get(0));
			}
		}
	}

	/**
	 * 业务员只能查看自己所属的客户信息
	 * 操作员和管理员可以查看所有的客户信息
	 * @throws Exception 
	 */
	@Override
	public PageInfo<CustomerDto> queryPage(CustomerDto dto,Integer userId) throws Exception {
		PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
		Customer customer = dto.getCustomer();
		// 如果当前登录用户是业务员 将用户的id作为查询条件即可
		List<Role> roles = userService.queryRoleByUserId(userId);
		boolean flag = true;
		if(roles !=null && roles.size() > 0 ){
			for (Role role : roles) {
				if(role.getRoleName().equals(Constant.ROLE_ADMIN) || 
						role.getRoleName().equals(Constant.ROLE_OPERATOR)){
					flag = false;
					break;
				}
			}
		}
		if(flag){
			// 表明当前用户只有业务员的角色
			customer = new Customer();
			customer.setUserId(userId);
		}
		List<CustomerDto> list = customerMapper.queryView(customer);
		return new PageInfo<>(list);
	}

	@Override
	public List<Customer> query(CustomerDto dto) {
		
		CustomerExample example = new CustomerExample();
		return customerMapper.selectByExample(example );
	}

	@Override
	public List<Customer> query(Customer customer) {
		// TODO Auto-generated method stub
		CustomerExample example = new CustomerExample();
		if(customer.getCustomerId()!=null && customer.getCustomerId() > 0){
			example.createCriteria().andCustomerIdEqualTo(customer.getCustomerId());
		}
		return customerMapper.selectByExample(example );
	}

	@Override
	public void addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerMapper.insertSelective(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerMapper.updateByPrimaryKeySelective(customer);
	}

	@Override
	public void deleteCustomer(int id) {
		// TODO Auto-generated method stub
		customerMapper.deleteByPrimaryKey(id);
	}

}
