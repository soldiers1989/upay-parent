package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class RetrnURLSignDto extends BaseDto{
	private String orderNo; // 订单号
	private String returnUrl;//回调URL
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	
}
