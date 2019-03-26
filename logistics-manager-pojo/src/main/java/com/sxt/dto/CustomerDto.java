package com.sxt.dto;

import com.sxt.pojo.Customer;

public class CustomerDto extends BasePage{
	
	private Customer customer;
	
	// 业务员姓名
	private String salesMan;
	// 常用区间
	private String interval;
	// 客户具有的订单个数
	private Integer orderNum;
	
	public String getSalesMan() {
		return salesMan;
	}

	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
