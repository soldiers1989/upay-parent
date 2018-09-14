package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

public class UpdateUserNameDto extends BaseDto{
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
