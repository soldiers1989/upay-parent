package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

public class AccLimitQueryDto  extends BaseDto{
	
	private String lmtTransCode;//限额交易
	
	private String userCertLevel;//用户认证等级
	/**
     * 借记卡单笔限额       db_column: SINGLE_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal debitSingleAmtLimit;
    /**
     * 借记卡日累计限额       db_column: DAY_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal debitDaySumAmtLimit;
	
	/**
     * 信用卡单笔限额       db_column: SINGLE_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal creditSingleAmtLimit;
    /**
     * 信用卡日累计限额       db_column: DAY_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal creditDaySumAmtLimit;
	
	
	
	public String getLmtTransCode() {
		return lmtTransCode;
	}

	public void setLmtTransCode(String lmtTransCode) {
		this.lmtTransCode = lmtTransCode;
	}

	public java.math.BigDecimal getCreditSingleAmtLimit() {
		return creditSingleAmtLimit;
	}

	public void setCreditSingleAmtLimit(java.math.BigDecimal creditSingleAmtLimit) {
		this.creditSingleAmtLimit = creditSingleAmtLimit;
	}

	public java.math.BigDecimal getCreditDaySumAmtLimit() {
		return creditDaySumAmtLimit;
	}

	public void setCreditDaySumAmtLimit(java.math.BigDecimal creditDaySumAmtLimit) {
		this.creditDaySumAmtLimit = creditDaySumAmtLimit;
	}

	public String getUserCertLevel() {
		return userCertLevel;
	}

	public void setUserCertLevel(String userCertLevel) {
		this.userCertLevel = userCertLevel;
	}

	public java.math.BigDecimal getDebitSingleAmtLimit() {
		return debitSingleAmtLimit;
	}

	public void setDebitSingleAmtLimit(java.math.BigDecimal debitSingleAmtLimit) {
		this.debitSingleAmtLimit = debitSingleAmtLimit;
	}

	public java.math.BigDecimal getDebitDaySumAmtLimit() {
		return debitDaySumAmtLimit;
	}

	public void setDebitDaySumAmtLimit(java.math.BigDecimal debitDaySumAmtLimit) {
		this.debitDaySumAmtLimit = debitDaySumAmtLimit;
	}
	
}
