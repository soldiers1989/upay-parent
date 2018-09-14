package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 检查二级商户是否所属一级商户
 * 
 * @author yanzixiong
 * 
 */
public class ChkSecondIsMerDto extends BaseDto{
	/** 二级商户代码 */
	private String secMerNo;

	public String getSecMerNo() {
		return secMerNo;
	}

	public void setSecMerNo(String secMerNo) {
		this.secMerNo = secMerNo;
	}
	
}
