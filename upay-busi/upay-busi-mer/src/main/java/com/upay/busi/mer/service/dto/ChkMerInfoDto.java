package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 商户名称重复校验
 * 
 * @author yanzixiong
 * 
 */
public class ChkMerInfoDto extends BaseDto {
	/** 商户名称 */
	private String merNo;
    private String authCode;
	
	private String subMchId;
	
	
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}


	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	

}
