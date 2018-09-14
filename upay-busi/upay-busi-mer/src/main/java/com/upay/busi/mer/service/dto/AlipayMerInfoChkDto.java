package com.upay.busi.mer.service.dto;

import java.sql.Blob;

import com.upay.commons.dto.BaseDto;

public class AlipayMerInfoChkDto extends BaseDto{
	private String merNo;//商户号
	private String secMerNo;//二级商户号
	private String merName;//商户名+商户地址
	private String merNameResult;//商户名
	private String subMerchantId;
	private byte[] alipayUpdateParam;//支付宝修改参数
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
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getMerNameResult() {
		return merNameResult;
	}
	public void setMerNameResult(String merNameResult) {
		this.merNameResult = merNameResult;
	}
	public String getSubMerchantId() {
		return subMerchantId;
	}
	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}
	public byte[] getAlipayUpdateParam() {
		return alipayUpdateParam;
	}
	public void setAlipayUpdateParam(byte[] alipayUpdateParam) {
		this.alipayUpdateParam = alipayUpdateParam;
	}
	
}
