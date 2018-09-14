package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class UserTrPwdCheckDto extends BaseDto {
    /** 登录密码 */
    private String tradePwd;
    /** 新密码 */
    private String newTradePwd;
    /** 验证码KEY */
    private String verifyKey;
    /** 错误密码统计标识标识 Y 需要统计 N不需要统计 */
    private String traPwdCheckFlag;
    /** 新密码与旧密码重复校验(01:登录密码，02：支付密码) */
    private String repeatCheckFlag;
    private String miType;//密码控件类型

    public String getMiType() {
		return miType;
	}
	public void setMiType(String miType) {
		this.miType = miType;
	}

    public String getTradePwd() {
        return tradePwd;
    }


    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }


    public String getVerifyKey() {
        return verifyKey;
    }


    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }


    public String getTraPwdCheckFlag() {
        return traPwdCheckFlag;
    }


    public void setTraPwdCheckFlag(String traPwdCheckFlag) {
        this.traPwdCheckFlag = traPwdCheckFlag;
    }


    public String getNewTradePwd() {
        return newTradePwd;
    }


    public void setNewTradePwd(String newTradePwd) {
        this.newTradePwd = newTradePwd;
    }


    public String getRepeatCheckFlag() {
        return repeatCheckFlag;
    }


    public void setRepeatCheckFlag(String repeatCheckFlag) {
        this.repeatCheckFlag = repeatCheckFlag;
    }

}
