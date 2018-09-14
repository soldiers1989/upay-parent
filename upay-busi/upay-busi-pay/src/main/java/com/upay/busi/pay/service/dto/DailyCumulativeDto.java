package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

public class DailyCumulativeDto extends BaseDto{
	private String merNo;//商户号
	private BigDecimal transAmt;//交易金额
	private String refundDailyFlag;//退款标志
	private String orderStat;
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public BigDecimal getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}
	public String getRefundDailyFlag() {
		return refundDailyFlag;
	}
	public void setRefundDailyFlag(String refundDailyFlag) {
		this.refundDailyFlag = refundDailyFlag;
	}
	public String getOrderStat() {
		return orderStat;
	}
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}
	
	
	
}
