package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class FirstMerCancelDto extends BaseDto {
	private String merNo;// 商户号

	private String merState;// 商户停用启用标志 商户状态,0 正常 1 停用

	public String getMerState() {
		return merState;
	}

	public void setMerState(String merState) {
		this.merState = merState;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

}
