package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 用户注册标识检查
 * 
 * @author liubing
 * 
 */
public class RegistIdentityCheckDto extends BaseDto {

    /** 手机号 */
    private String mobile;
	
	/** 邮箱 */
	private String email;
	
	/** 登陆名 */
	private String userName;
	
	
	
	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
