/**
 * 
 */
package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author lb
 * 
 */
public class CheckVerifyCodeServiceDto extends BaseDto {
	private String verifyKey;
	private String verifyCode;

	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
