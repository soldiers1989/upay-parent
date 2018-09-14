package com.upay.busi.mer.service.dto;

import java.sql.Blob;

import com.upay.commons.dto.BaseDto;

public class UpdateAlipayMerchantIdDto extends BaseDto{
	private String merNo;//商户号
    private String subMerchantId;//商户识别码
    private byte[] alipayUpdateParam;//支付宝修改参数
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
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
