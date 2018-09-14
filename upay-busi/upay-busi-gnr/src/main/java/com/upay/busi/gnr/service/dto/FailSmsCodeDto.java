package com.upay.busi.gnr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 失效短信验证码
 * @author freeplato
 *
 */
public class FailSmsCodeDto extends BaseDto {
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 短信验证码
	 */
	private String smsCode;
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	
}
