package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

public class QueryUserIdByMerNoDto extends BaseDto{
	private String merNo;//商户号

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
}
