package com.upay.busi.acc.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author xuxin
 * @version 创建时间：2016年8月29日 
 */
@SuppressWarnings("serial")
public class PermissionCheckDto extends BaseDto implements Serializable{
    // 输入项
    private String userId;// 用户号
    private String acctType;// 账户类型
    private String eCardNo;// 电子账户卡号
    private String eAcctCertLevel;// 用户认证等级检查
    private BigDecimal transAmt;// 用户认证等级检查


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * @return the eCardNo
     */
    public String geteCardNo() {
        return eCardNo;
    }


    /**
     * @param eCardNo
     *            the eCardNo to set
     */
    public void seteCardNo(String eCardNo) {
        this.eCardNo = eCardNo;
    }


    public String getAcctType() {
        return acctType;
    }


    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }


    public String geteAcctCertLevel() {
        return eAcctCertLevel;
    }


    public void seteAcctCertLevel(String eAcctCertLevel) {
        this.eAcctCertLevel = eAcctCertLevel;
    }


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
     
    
}




