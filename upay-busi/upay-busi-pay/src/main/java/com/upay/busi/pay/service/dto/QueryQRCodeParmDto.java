package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class QueryQRCodeParmDto extends BaseDto {
	private String orderNo;
	private String merNo;//商户号
	private String transAmtStr;
	private String txnAmt;
	private String userId;
    private String spbillCreateIp;// 用户IP

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTransAmtStr() {
		return transAmtStr;
	}

	public void setTransAmtStr(String transAmtStr) {
		this.transAmtStr = transAmtStr;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	

}
