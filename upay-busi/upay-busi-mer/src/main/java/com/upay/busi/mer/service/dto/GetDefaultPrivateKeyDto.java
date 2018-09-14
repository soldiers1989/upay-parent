package com.upay.busi.mer.service.dto;

import com.upay.commons.dto.BaseDto;

public class GetDefaultPrivateKeyDto extends BaseDto {
	private String defaultKey;

	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}

}
