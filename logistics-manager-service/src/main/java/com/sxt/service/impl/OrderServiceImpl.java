package com.sxt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.sxt.dto.CustomerDto;
import com.sxt.dto.OrderDto;
import com.sxt.mapper.OrderDetailMapper;
import com.sxt.mapper.OrderMapper;
import com.sxt.pojo.BasicData;
import com.sxt.pojo.Customer;
import com.sxt.pojo.OrderDetail;
import com.sxt.pojo.OrderDetailExample;
import com.sxt.pojo.User;
import com.sxt.service.IBasicService;
import com.sxt.service.ICustomerService;
import com.sxt.service.IOrderService;
import com.sxt.service.IUserService;
import com.sxt.utils.Constant;
@Service
public class OrderServiceImpl implements IOrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private OrderDetailMapper orderDetailMapper;
	@Resource
	private IUserService userService;
	@Resource
	private IBasicService basicService;
	@Resource
	private ICustomerService customerService;

	/**
	 * 新增订单
	 * 	1.查询所有的业务员
	 *  2.查询所有的客户信息
	 *  3.基础数据查询
	 *  	常用区间(国家)
	 *      付款方式
	 *      货运方式
	 *      取件方式
	 *      单位 
	 */
	@Override
	public void getOrderUpdateInfo(Integer id, Model model) {
		// 1.查询所有的业务员
		List<User> users = userService.queryUserByRoleName(Constant.ROLE_SALESMAN);
		Customer customer = new Customer();
		// 2.查询所有的客户信息
		List<Customer> customers = customerService.query(customer);
		// 3.查询常用区间
		List<BasicData> intervals = basicService.getBasicDataByParentName(Constant.BASIC_COMMON_INTERVAL);
		// 4.付款方式
		List<BasicData> pays = basicService.getBasicDataByParentName(Constant.BASIC_PAYMENT_TYPE);
		// 5.货运方式
		List<BasicData> freights = basicService.getBasicDataByParentName(Constant.BASIC_FREIGHT_TYPE);
		// 6.取件方式
		List<BasicData> fetchs = basicService.getBasicDataByParentName(Constant.BASIC_FETCH_TYPE);
		// 7.获取所有的单位
		List<BasicData> units = basicService.getBasicDataByParentName(Constant.BASIC_UNIT);
	
		model.addAttribute("users", users);
		model.addAttribute("customers",customers );
		model.addAttribute("intervals",intervals );
		model.addAttribute("pays", pays);
		model.addAttribute("freights",freights );
		model.addAttribute("fetchs",fetchs );
		model.addAttribute("units",units );
	}

	@Override
	public void saveOrder(OrderDto dto) {
		
		// 添加订单主表信息
		orderMapper.insert(dto);
		
		// 获取订单详细信息
		List<OrderDetail> details = dto.getOrderDetails();
		if(details!=null && details.size() > 0){
			for (OrderDetail d : details) {
				// 给OrderDetail对象设置对应的订单编号
				d.setOrderId(dto.getOrderId());
				orderDetailMapper.insert(d);
			}
		}
		
	}

}
