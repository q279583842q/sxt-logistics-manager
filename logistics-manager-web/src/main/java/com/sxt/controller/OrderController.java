package com.sxt.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxt.dto.OrderDto;
import com.sxt.service.IOrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private IOrderService orderService;

	@RequestMapping("/orderUpdate")
	public String orderUpdate(Integer id,Model model){
		orderService.getOrderUpdateInfo(id, model);
		return "order/orderUpdate";
	}
	@RequestMapping("/save")
	@ResponseBody
	public String save(@RequestBody OrderDto dto){
		orderService.saveOrder(dto);
		return "success";
	}
}
