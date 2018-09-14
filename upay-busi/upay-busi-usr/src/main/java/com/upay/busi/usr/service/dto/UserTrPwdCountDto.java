package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;


public class UserTrPwdCountDto extends BaseDto {
    // 错误密码统计标识 Y 需要统计 N不需要统计
    private String traPwdCheckFlag;
    // 客户还允许输入密码的次数
    private int logDayRemainderErr;


    public String getTraPwdCheckFlag() {
        return traPwdCheckFlag;
    }


    public void setTraPwdCheckFlag(String traPwdCheckFlag) {
        this.traPwdCheckFlag = traPwdCheckFlag;
    }


    public int getLogDayRemainderErr() {
        return logDayRemainderErr;
    }


    public void setLogDayRemainderErr(int logDayRemainderErr) {
        this.logDayRemainderErr = logDayRemainderErr;
    }

}
