package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * @author shangqiankun
 * @version 创建时间：2016年7月19日 上午9:16:35
 */
public class UserRegDto extends BaseDto {
//	private String userId;//用戶号
	private String userName;//用户名
	private String name;//真实姓名
	private String certType;//证件类型
	private String certNo;//证件号
	private String sex;//性别
	private String mobile;//手机号
	private String email;//邮箱
	private String address;//地址
	private String merNo;//商户号
	private String platformType;//合作平台
	private String platformUserNo;//合作平台的用户账号
	
	private String regType;//注册类型
	private String loginPwd;//用户登录密码
	private String userNickName;//用户昵称
	
	private String smsCode;//手机验证码
	
	private String miType;//密码控件类型

    public String getMiType() {
		return miType;
	}
	public void setMiType(String miType) {
		this.miType = miType;
	}
	public String getSmsCode() {
        return smsCode;
    }
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    public String getUserNickName() {
        return userNickName;
    }
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
    public String getLoginPwd() {
        return loginPwd;
    }
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
    public String getRegType() {
        return regType;
    }
    public void setRegType(String regType) {
        this.regType = regType;
    }
    public String getPlatformType() {
        return platformType;
    }
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }
    public String getPlatformUserNo() {
        return platformUserNo;
    }
    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
	
	
	
	
}
