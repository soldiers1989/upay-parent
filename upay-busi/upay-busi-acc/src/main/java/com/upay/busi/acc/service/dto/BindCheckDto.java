package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 根据卡号查询预留手机号并对比
 * 
 * @author liyulong
 * 
 */
public class BindCheckDto extends BaseDto {
    private String vBindAcctNo;// 卡号
    private String mobile;// 手机号(需要和预留手机号比较)


    public String getvBindAcctNo() {
        return vBindAcctNo;
    }


    public void setvBindAcctNo(String vBindAcctNo) {
        this.vBindAcctNo = vBindAcctNo;
    }


    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
