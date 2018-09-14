
package com.upay.dao.po.gnr;

import com.pactera.dipper.po.BasePo;

public class GnrCurviewpicInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "当前使用轮播图信息表";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CHNL_ID = "渠道代码";
	public static final String ALIAS_DEV_TYPE = "设备类型";
	public static final String ALIAS_PIC_LINK = "图片链接";
	public static final String ALIAS_EVENT_ID = "活动ID";
	public static final String ALIAS_REL_TIME = "发布时间";
	public static final String ALIAS_PRIORITY = "优先级";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 渠道代码       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 设备类型       db_column: DEV_TYPE 
     */ 	
	private java.lang.String devType;
    /**
     * 图片链接       db_column: PIC_LINK 
     */ 	
	private java.lang.String picLink;
    /**
     * 活动ID       db_column: EVENT_ID 
     */ 	
	private java.lang.String eventId;
    /**
     * 发布时间       db_column: REL_TIME 
     */ 	
	private java.util.Date relTime;
    /**
     * 优先级       db_column: PRIORITY 
     */ 	
	private java.lang.Integer priority;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用3       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getDevType() {
		return this.devType;
	}
	
	public void setDevType(java.lang.String value) {
		this.devType = value;
	}
	
	
	public java.lang.String getPicLink() {
		return this.picLink;
	}
	
	public void setPicLink(java.lang.String value) {
		this.picLink = value;
	}
	
	
	public java.lang.String getEventId() {
		return this.eventId;
	}
	
	public void setEventId(java.lang.String value) {
		this.eventId = value;
	}
	
	
	public java.util.Date getRelTime() {
		return this.relTime;
	}
	
	public void setRelTime(java.util.Date value) {
		this.relTime = value;
	}
	
	
	public java.lang.Integer getPriority() {
		return this.priority;
	}
	
	public void setPriority(java.lang.Integer value) {
		this.priority = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	
	
	public java.lang.String getRemark2() {
		return this.remark2;
	}
	
	public void setRemark2(java.lang.String value) {
		this.remark2 = value;
	}
	
	
	public java.lang.String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(java.lang.String value) {
		this.remark3 = value;
	}
	

	

}

