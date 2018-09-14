package com.upay.busi.mer.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;
/**
 * 商户类别查询
 * 
 * @author yanzixiong
 * 
 */
public class MerQueryReltypeDto extends BaseDto {
	/** 类目名称 */
	private String reltypeName;
	
	/** 类目编号 */
	private String reltypeId;
	
	private List<Map<String, Object>> merReltypeList;

	public String getReltypeName() {
		return reltypeName;
	}

	public void setReltypeName(String reltypeName) {
		this.reltypeName = reltypeName;
	}

	public String getReltypeId() {
		return reltypeId;
	}

	public void setReltypeId(String reltypeId) {
		this.reltypeId = reltypeId;
	}

	public List<Map<String, Object>> getMerReltypeList() {
		return merReltypeList;
	}

	public void setMerReltypeList(List<Map<String, Object>> merReltypeList) {
		this.merReltypeList = merReltypeList;
	}
	
	
}
