package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 商户名称重复校验
 * 
 * @author yanzixiong
 * 
 */
public class ChkMerNameDto extends BaseDto {
	/** 商户名称 */
	private String merName;
	
	/** 商户申请编号 */
	private String merApplyNo;

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerApplyNo() {
		return merApplyNo;
	}

	public void setMerApplyNo(String merApplyNo) {
		this.merApplyNo = merApplyNo;
	}
	
	
}
