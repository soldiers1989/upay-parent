package com.upay.busi.acc.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;

public class QueryOnlineBankDto extends BaseDto{
	private List<Map<String,Object>> logoList;//绑定的卡集合
	private String queryFlag;//查询标志0：全部    1：网银
	public List<Map<String, Object>> getLogoList() {
		return logoList;
	}
	public void setLogoList(List<Map<String, Object>> logoList) {
		this.logoList = logoList;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	
	
}
