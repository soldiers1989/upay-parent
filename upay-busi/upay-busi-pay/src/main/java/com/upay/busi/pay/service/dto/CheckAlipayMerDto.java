package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class CheckAlipayMerDto extends BaseDto{
	private String merNo;//商户号
	private String merchantId;//商户在支付宝商户号
	private String aliasName;//商户简称
	private String merName;
	
	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}
	
	
}
