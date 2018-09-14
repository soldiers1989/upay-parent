package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

public class TransferAccountCheckDto extends BaseDto{
	private String payeeMobile;//收付人手机号

	private String payeeUserName;//收付人用户名

	public String getPayeeUserName() {
		return payeeUserName;
	}

	public void setPayeeUserName(String payeeUserName) {
		this.payeeUserName = payeeUserName;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}
	
}
