/**
 * 
 */
package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author Administrator
 * 
 */
public class QueryUserNameIsExistDto extends BaseDto {
	private String userName;
	private String isExist;
	
	
	public String getIsExist() {
		return isExist;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
