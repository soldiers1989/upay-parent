package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 用户密码修改
 * 
 * @author liyulong
 * 
 */
public class PwdChgDto extends BaseDto {

    /** 输入的原登录密码 */
    private String loginPwd;

    /** 新登录密码 */
    private String newLoginPwd;

    /** 输入的原支付密码 */
    private String tradePwd;

    /** 新支付密码 */
    private String newTradePwd;

    /** 密码类型 1.登录密码 2.支付密码 */
    private String pwdFlag;

    private String miType;//密码控件类型

    public String getMiType() {
		return miType;
	}
	public void setMiType(String miType) {
		this.miType = miType;
	}
	
    public String getLoginPwd() {
        return loginPwd;
    }


    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }


    public String getPwdFlag() {
        return pwdFlag;
    }


    public void setPwdFlag(String pwdFlag) {
        this.pwdFlag = pwdFlag;
    }


    public String getNewLoginPwd() {
        return newLoginPwd;
    }


    public void setNewLoginPwd(String newLoginPwd) {
        this.newLoginPwd = newLoginPwd;
    }


    public String getTradePwd() {
        return tradePwd;
    }


    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }


    public String getNewTradePwd() {
        return newTradePwd;
    }


    public void setNewTradePwd(String newTradePwd) {
        this.newTradePwd = newTradePwd;
    }

}
