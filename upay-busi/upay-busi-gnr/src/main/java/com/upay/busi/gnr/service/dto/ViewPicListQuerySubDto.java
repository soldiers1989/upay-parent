package com.upay.busi.gnr.service.dto;



import java.io.Serializable;

/**
 * 轮播图列表查询子类
 * @author wuyuejun
 *
 */
public class ViewPicListQuerySubDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9109879740877800700L;

	private String picLink;//图片链接
	
	private String eventLink;//活动链接

	public String getPicLink() {
		return picLink;
	}

	public void setPicLink(String picLink) {
		this.picLink = picLink;
	}

	public String getEventLink() {
		return eventLink;
	}

	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}
	
	
}
