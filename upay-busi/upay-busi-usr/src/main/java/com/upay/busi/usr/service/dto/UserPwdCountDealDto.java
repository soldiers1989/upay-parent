package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 用户密码错误次数统计
 * 
 * @author lihe
 * 
 */

public class UserPwdCountDealDto extends BaseDto {
    // // 错误密码统计标识 Y 需要统计 N不需要统计
    private String pwdCheckFlag;
    // 客户还允许输入密码的次数
    private int logDayRemainderErr;


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

}
