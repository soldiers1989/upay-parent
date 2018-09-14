package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class QueryMerInfoByQrCodeDto extends BaseDto{
	private String qrCode;
	private String merNo;
	
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	
	
}
