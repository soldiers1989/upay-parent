/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年10月19日
 */
public class ReturnAccountAmtDto extends BaseDto {
    private boolean updateUserAccountAmt;//是否已经更改用户余额
    private String accNo;//账户号
    private String payType;//支付方式
    private String orderStat;//订单状态
    private String orderNo;//订单号
    private BigDecimal transAmt;//交易金额
    private BigDecimal feeAmt;//手续费
    private String getType;//手续费收起方式：0内扣，1外扣
    
    private String isAccAmtRefund;//是否余额回退      Y-是，N-不是
    private BigDecimal updateAmt;//回退金额
    
    
    public BigDecimal getUpdateAmt() {
        return updateAmt;
    }
    public void setUpdateAmt(BigDecimal updateAmt) {
        this.updateAmt = updateAmt;
    }
    public String getIsAccAmtRefund() {
        return isAccAmtRefund;
    }
    public void setIsAccAmtRefund(String isAccAmtRefund) {
        this.isAccAmtRefund = isAccAmtRefund;
    }
    public String getGetType() {
		return getType;
	}
	public void setGetType(String getType) {
		this.getType = getType;
	}
	public BigDecimal getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}
	public BigDecimal getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderStat() {
        return orderStat;
    }
    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }
    
    public boolean isUpdateUserAccountAmt() {
        return updateUserAccountAmt;
    }
    public void setUpdateUserAccountAmt(boolean updateUserAccountAmt) {
        this.updateUserAccountAmt = updateUserAccountAmt;
    }
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    
    
    
}
