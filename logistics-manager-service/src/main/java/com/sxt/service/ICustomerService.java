package com.sxt.service;

import java.util.List;

import org.springframework.ui.Model;

import com.github.pagehelper.PageInfo;
import com.sxt.dto.CustomerDto;
import com.sxt.pojo.Customer;

public interface ICustomerService {

	/**
	 * 获取添加或者更新客户需要的数据
	 * @param id
	 * @param model
	 */
	public void getUpdateInfo(Integer id, Model model);
	
	public PageInfo<CustomerDto> queryPage(CustomerDto dto,Integer userId) throws Exception;
	
	public List<Customer> query(CustomerDto dto);
	
	public List<Customer> query(Customer customer);
	
	public void addCustomer(Customer customer);
	
	public void updateCustomer(Customer customer);
	
	public void deleteCustomer(int id);
}
