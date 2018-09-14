package com.upay.busi.gnr.service.dto;


import java.util.List;

import com.upay.commons.dto.BaseDto;



/**
 * 地区代码获取
 * @author lb
 *
 */
public class AreaCodeGetDto extends BaseDto {

	private String areaCode;
	
	private List<AreaCodeGetSubDto> areaCodeList;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<AreaCodeGetSubDto> getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(List<AreaCodeGetSubDto> areaCodeList) {
		this.areaCodeList = areaCodeList;
	}

}
