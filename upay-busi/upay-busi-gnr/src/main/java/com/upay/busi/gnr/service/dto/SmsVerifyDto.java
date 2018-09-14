/**
 * 
 */
package com.upay.busi.gnr.service.dto;

import com.upay.commons.dto.BaseDto;



/**
 * @author zacks
 * 
 */
public class SmsVerifyDto extends BaseDto {

    private String smsCode;

    private String mobile;


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
