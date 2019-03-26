package com.sxt.dto;

import java.util.List;

import com.sxt.pojo.Order;
import com.sxt.pojo.OrderDetail;

public class OrderDto extends Order{

	private List<OrderDetail> orderDetails;

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	
}
