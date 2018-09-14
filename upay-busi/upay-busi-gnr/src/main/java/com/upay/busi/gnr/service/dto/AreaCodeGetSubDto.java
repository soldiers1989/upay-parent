package com.upay.busi.gnr.service.dto;


import java.io.Serializable;

/**
 * 地区代码获取
 * @author lb
 *
 */
public class AreaCodeGetSubDto implements Serializable{

	private static final long serialVersionUID = -8199232046919071108L;

	private String areaCode;
	
	private String areaName;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}
