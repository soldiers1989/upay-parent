package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class UserPwdCheckDto extends BaseDto {
    /** 登录密码 */
    private String loginPwd;
    /** 新密码 */
    private String newLoginPwd;
    /** 验证码KEY */
    private String verifyKey;
    /** // 错误密码统计标识 Y 需要统计 N不需要统计 */
    private String pwdCheckFlag;
    /** 还允许输入密码的次数 */
    private int logDayRemainderErr;
    /** 新密码与旧密码重复校验(01:登录密码，02：支付密码) */
    private String repeatCheckFlag;

    private String miType;//密码控件类型

    public String getMiType() {
		return miType;
	}
	public void setMiType(String miType) {
		this.miType = miType;
	}
    public int getLogDayRemainderErr() {
        return logDayRemainderErr;
    }


    public void setLogDayRemainderErr(int logDayRemainderErr) {
        this.logDayRemainderErr = logDayRemainderErr;
    }


    public String getPwdCheckFlag() {
        return pwdCheckFlag;
    }


    public void setPwdCheckFlag(String pwdCheckFlag) {
        this.pwdCheckFlag = pwdCheckFlag;
    }


    public String getLoginPwd() {
        return loginPwd;
    }


    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }


    public String getVerifyKey() {
        return verifyKey;
    }


    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }


    public String getRepeatCheckFlag() {
        return repeatCheckFlag;
    }


    public void setRepeatCheckFlag(String repeatCheckFlag) {
        this.repeatCheckFlag = repeatCheckFlag;
    }


    public String getNewLoginPwd() {
        return newLoginPwd;
    }


    public void setNewLoginPwd(String newLoginPwd) {
        this.newLoginPwd = newLoginPwd;
    }

}
