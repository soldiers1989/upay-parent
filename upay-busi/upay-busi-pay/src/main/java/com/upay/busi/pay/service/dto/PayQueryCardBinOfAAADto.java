package com.upay.busi.pay.service.dto;

import java.io.Serializable;

import com.upay.commons.dto.BaseDto;


/**
 * 类或接口的功能说明 类或接口的使用说明:通过卡bin识别鉴权方式
 * 
 * @author: liyulong
 * 
 */
public class PayQueryCardBinOfAAADto extends BaseDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 绑定卡号 db_column: E_BIND_ACCT_NO
     */
    private String eBindAcctNo;

    /**
     * 通道代码 db_column: ROUTE_CODE
     */
    private String routeCode;

    /**
     * 绑卡方式db_column: E_BIND_FLAG
     */
    private String eBindFlag;



    public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public String geteBindAcctNo() {
        return eBindAcctNo;
    }


    public void seteBindAcctNo(String eBindAcctNo) {
        this.eBindAcctNo = eBindAcctNo;
    }


    public String geteBindFlag() {
        return eBindFlag;
    }


    public void seteBindFlag(String eBindFlag) {
        this.eBindFlag = eBindFlag;
    }

}
