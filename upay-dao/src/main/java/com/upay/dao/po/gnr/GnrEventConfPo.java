
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrEventConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrEventConf";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_EVENT_NO = "事件编号";
	public static final String ALIAS_EVENT_NAME = "事件名称";
	public static final String ALIAS_EVENT_DESC = "事件描述";
	public static final String ALIAS_EVENT_TYPE = "事件类型 1：实时通知 2：批量通知";
	public static final String ALIAS_USER_CERT_LEVEL = "用户认证等级 见附录";
	public static final String ALIAS_USER_VALUE_LEVEL = "用户价值等级 见附录";
	public static final String ALIAS_CHNI_ID = "渠道";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 事件编号       db_column: EVENT_NO 
     */ 	
	private java.lang.String eventNo;
    /**
     * 事件名称       db_column: EVENT_NAME 
     */ 	
	private java.lang.String eventName;
    /**
     * 事件描述       db_column: EVENT_DESC 
     */ 	
	private java.lang.String eventDesc;
    /**
     * 事件类型 1：实时通知 2：批量通知       db_column: EVENT_TYPE 
     */ 	
	private java.lang.String eventType;
    /**
     * 用户认证等级 见附录       db_column: USER_CERT_LEVEL 
     */ 	
	private java.lang.String userCertLevel;
    /**
     * 用户价值等级 见附录       db_column: USER_VALUE_LEVEL 
     */ 	
	private java.lang.String userValueLevel;
    /**
     * 渠道       db_column: CHNI_ID 
     */ 	
	private java.lang.String chniId;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改操作员       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
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


	
	
	public java.lang.String getEventNo() {
		return this.eventNo;
	}
	
	public void setEventNo(java.lang.String value) {
		this.eventNo = value;
	}
	
	
	public java.lang.String getEventName() {
		return this.eventName;
	}
	
	public void setEventName(java.lang.String value) {
		this.eventName = value;
	}
	
	
	public java.lang.String getEventDesc() {
		return this.eventDesc;
	}
	
	public void setEventDesc(java.lang.String value) {
		this.eventDesc = value;
	}
	
	
	public java.lang.String getEventType() {
		return this.eventType;
	}
	
	public void setEventType(java.lang.String value) {
		this.eventType = value;
	}
	
	
	public java.lang.String getUserCertLevel() {
		return this.userCertLevel;
	}
	
	public void setUserCertLevel(java.lang.String value) {
		this.userCertLevel = value;
	}
	
	
	public java.lang.String getUserValueLevel() {
		return this.userValueLevel;
	}
	
	public void setUserValueLevel(java.lang.String value) {
		this.userValueLevel = value;
	}
	
	
	public java.lang.String getChniId() {
		return this.chniId;
	}
	
	public void setChniId(java.lang.String value) {
		this.chniId = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getLastOper() {
		return this.lastOper;
	}
	
	public void setLastOper(java.lang.String value) {
		this.lastOper = value;
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

