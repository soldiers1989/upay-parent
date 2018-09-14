package com.upay.busi.gnr.service.dto;



import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;



/**
 * 轮播图列表查询Dto
 * @author wuyuejun
 *
 */
public class ViewPicListQueryDto extends BaseDto{
	
//	private String devType;//设备类型
	
	private List<Map<String, Object>> viewPicList ;

	public List<Map<String, Object>> getViewPicList() {
		return viewPicList;
	}

	public void setViewPicList(List<Map<String, Object>> viewPicList) {
		this.viewPicList = viewPicList;
	}

	

//	public String getDevType() {
//		return devType;
//	}
//
//	public void setDevType(String devType) {
//		this.devType = devType;
//	}


}
