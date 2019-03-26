package com.sxt.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.sxt.dto.CustomerDto;
import com.sxt.pojo.Customer;
import com.sxt.pojo.User;
import com.sxt.service.ICustomerService;
import com.sxt.utils.Constant;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Resource
	private ICustomerService customerService;
	
	@RequestMapping("/customerUpdate")
	public String customerUpdate(Integer id,Model model){
		customerService.getUpdateInfo(id, model);
		return "customer/customerUpdate";
	}
	
	@RequestMapping("/queryPage")
	public String queryPage(CustomerDto dto,Model model) throws Exception{
		// 获取当前登录用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		PageInfo<CustomerDto> pageModel = customerService.queryPage(dto,user.getUserId());
		model.addAttribute(Constant.PAGEHELP_MODEL, pageModel);
		return "customer/customer";
	}
	
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(Customer customer){
		if(customer.getCustomerId()!=null&&customer.getCustomerId()>0){
			// 表示更新数据
			customerService.updateCustomer(customer);
		}else{
			// 表示添加数据
			customerService.addCustomer(customer);
		}
		return "success";
	}
	
	@RequestMapping("/delete")
	public String deleteCustomer(Integer id){
		customerService.deleteCustomer(id);
		return "redirect:/customer/queryPage";
	}
	@RequestMapping("/queryBaseIdByCustomerId")
	@ResponseBody
	public Integer queryBaseIdByCustomerId(Customer customer ){
		
		List<Customer> list = customerService.query(customer);
		if(list !=null && list.size() == 1){
			return list.get(0).getBaseId();
		}
		return 0;
	}
}
