package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class InitDataQueryRedPackDto extends BaseDto{
	//商户订单号
	private String outerOrderNo;
	//本平台红包订单号
	private String transSubSeq;
	
	public String getOuterOrderNo() {
		return outerOrderNo;
	}

	public void setOuterOrderNo(String outerOrderNo) {
		this.outerOrderNo = outerOrderNo;
	}

	public String getTransSubSeq() {
		return transSubSeq;
	}

	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}

	
	
}
