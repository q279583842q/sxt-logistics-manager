package com.sxt.service;

import org.springframework.ui.Model;

import com.sxt.dto.OrderDto;

public interface IOrderService {

	public void getOrderUpdateInfo(Integer id,Model model);

	public void saveOrder(OrderDto dto);
	
}
