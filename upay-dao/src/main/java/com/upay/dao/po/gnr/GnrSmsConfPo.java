
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrSmsConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrSmsConf";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_EVENT_NO = "事件编号";
	public static final String ALIAS_SMS_SEND_OBJ = "短信发送对象类型 1：本人 2：关系人";
	public static final String ALIAS_SMS_SEQ = "短信序号";
	public static final String ALIAS_SMS_NO = "短信编号";
	public static final String ALIAS_SMS_DESC = "短信名称";
	public static final String ALIAS_SMS_MSG = "短信内容 以文本的方式输入消息内容，当有参数性的字段时，以$作为占位符体现";
	public static final String ALIAS_SMS_STAT = "短信状态 0：停用 1：启用";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	public static final String ALIAS_SMS_START_TIME = "发送起始时间";
	public static final String ALIAS_SMS_END_TIME = "发送终止时间";
	

	//columns START
    /**
     * 事件编号       db_column: EVENT_NO 
     */ 	
	private java.lang.String eventNo;
    /**
     * 短信发送对象类型 1：本人 2：关系人       db_column: SMS_SEND_OBJ 
     */ 	
	private java.lang.String smsSendObj;
    /**
     * 短信序号       db_column: SMS_SEQ 
     */ 	
	private java.lang.Integer smsSeq;
    /**
     * 短信编号       db_column: SMS_NO 
     */ 	
	private java.lang.String smsNo;
    /**
     * 短信名称       db_column: SMS_DESC 
     */ 	
	private java.lang.String smsDesc;
    /**
     * 短信内容 以文本的方式输入消息内容，当有参数性的字段时，以$作为占位符体现       db_column: SMS_MSG 
     */ 	
	private java.lang.String smsMsg;
    /**
     * 短信状态 0：停用 1：启用       db_column: SMS_STAT 
     */ 	
	private java.lang.String smsStat;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改操作员       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
    /**
     * 发送起始时间       db_column: SMS_START_TIME 
     */ 	
	private java.lang.String smsStartTime;
    /**
     * 发送终止时间       db_column: SMS_END_TIME 
     */ 	
	private java.lang.String smsEndTime;
	//columns END


	
	
	public java.lang.String getEventNo() {
		return this.eventNo;
	}
	
	public void setEventNo(java.lang.String value) {
		this.eventNo = value;
	}
	
	
	public java.lang.String getSmsSendObj() {
		return this.smsSendObj;
	}
	
	public void setSmsSendObj(java.lang.String value) {
		this.smsSendObj = value;
	}
	
	
	public java.lang.Integer getSmsSeq() {
		return this.smsSeq;
	}
	
	public void setSmsSeq(java.lang.Integer value) {
		this.smsSeq = value;
	}
	
	
	public java.lang.String getSmsNo() {
		return this.smsNo;
	}
	
	public void setSmsNo(java.lang.String value) {
		this.smsNo = value;
	}
	
	
	public java.lang.String getSmsDesc() {
		return this.smsDesc;
	}
	
	public void setSmsDesc(java.lang.String value) {
		this.smsDesc = value;
	}
	
	
	public java.lang.String getSmsMsg() {
		return this.smsMsg;
	}
	
	public void setSmsMsg(java.lang.String value) {
		this.smsMsg = value;
	}
	
	
	public java.lang.String getSmsStat() {
		return this.smsStat;
	}
	
	public void setSmsStat(java.lang.String value) {
		this.smsStat = value;
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
	
	
	public java.lang.String getSmsStartTime() {
		return this.smsStartTime;
	}
	
	public void setSmsStartTime(java.lang.String value) {
		this.smsStartTime = value;
	}
	
	
	public java.lang.String getSmsEndTime() {
		return this.smsEndTime;
	}
	
	public void setSmsEndTime(java.lang.String value) {
		this.smsEndTime = value;
	}
	

	

}

