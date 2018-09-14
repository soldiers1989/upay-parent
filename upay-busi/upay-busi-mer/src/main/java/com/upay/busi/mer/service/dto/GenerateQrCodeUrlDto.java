package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class GenerateQrCodeUrlDto extends BaseDto{
	private String merNo;
	private String merName;
	private String alipayWeChatUrl;//支付宝、微信聚合码链接
	private String unionQrUrl;//银联标准码聚合链接
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getAlipayWeChatUrl() {
		return alipayWeChatUrl;
	}
	public void setAlipayWeChatUrl(String alipayWeChatUrl) {
		this.alipayWeChatUrl = alipayWeChatUrl;
	}
	public String getUnionQrUrl() {
		return unionQrUrl;
	}
	public void setUnionQrUrl(String unionQrUrl) {
		this.unionQrUrl = unionQrUrl;
	}
	
	
}
