package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 商户交易限额检查DTO
 * @author zhangjianfeng
 * @since 2016/11/24 17:10
 */
public class MerTransLimitCheckDto extends BaseDto {

    /** 一级商户号 */
    public String merNo;

    /** 二级商户号 */
    public String secMerNo;

    /** 支付方式 */
    public String payType;

    /** 支付账户类型 */
    public String payCardType;

    /** 交易金额 */
    public BigDecimal transAmt;
    
    /**交易类型*
     */
    public String transType;
    
    

    public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public void setPayCardType(String payCardType) {
        this.payCardType = payCardType;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
}
