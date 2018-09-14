/**
 * 
 */
package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年11月7日
 */
public class CheckLoginFlagDto extends BaseDto {
    private String ifExist;
    private String merNo;//商户号
    private String loginFlag;//登陆标识
    
    public String getIfExist() {
        return ifExist;
    }
    public void setIfExist(String ifExist) {
        this.ifExist = ifExist;
    }
    public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    public String getLoginFlag() {
        return loginFlag;
    }
    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }
    
    
}
