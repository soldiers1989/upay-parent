
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrNoticeConfPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrNoticeConf";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_EVENT_NO = "事件编号";
	public static final String ALIAS_NOTICE_SEND_OBJ = "消息发送对象类型 1：本人 2：关系人";
	public static final String ALIAS_NOTICE_SEQ = "消息序号";
	public static final String ALIAS_NOTICE_NO = "消息编号";
	public static final String ALIAS_NOTICE_DESC = "消息名称";
	public static final String ALIAS_NOTICE_SEND_TYPE = "消息发送方类型 1：直销银行系统 2：平台用户";
	public static final String ALIAS_NOTICE_MSG = "消息信息 以文本的方式输入消息内容，当有参数性的字段时，以$作为占位符体现";
	public static final String ALIAS_NOTICE_STAT = "消息状态 0：停用 1：启用";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后修改时间";
	public static final String ALIAS_LAST_OPER = "最后修改操作员";
	

	//columns START
    /**
     * 事件编号       db_column: EVENT_NO 
     */ 	
	private java.lang.String eventNo;
    /**
     * 消息发送对象类型 1：本人 2：关系人       db_column: NOTICE_SEND_OBJ 
     */ 	
	private java.lang.String noticeSendObj;
    /**
     * 消息序号       db_column: NOTICE_SEQ 
     */ 	
	private java.lang.Integer noticeSeq;
    /**
     * 消息编号       db_column: NOTICE_NO 
     */ 	
	private java.lang.String noticeNo;
    /**
     * 消息名称       db_column: NOTICE_DESC 
     */ 	
	private java.lang.String noticeDesc;
    /**
     * 消息发送方类型 1：直销银行系统 2：平台用户       db_column: NOTICE_SEND_TYPE 
     */ 	
	private java.lang.String noticeSendType;
    /**
     * 消息信息 以文本的方式输入消息内容，当有参数性的字段时，以$作为占位符体现       db_column: NOTICE_MSG 
     */ 	
	private java.lang.String noticeMsg;
    /**
     * 消息状态 0：停用 1：启用       db_column: NOTICE_STAT 
     */ 	
	private java.lang.String noticeStat;
    /**
     * 最后修改时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 最后修改操作员       db_column: LAST_OPER 
     */ 	
	private java.lang.String lastOper;
	//columns END


	
	
	public java.lang.String getEventNo() {
		return this.eventNo;
	}
	
	public void setEventNo(java.lang.String value) {
		this.eventNo = value;
	}
	
	
	public java.lang.String getNoticeSendObj() {
		return this.noticeSendObj;
	}
	
	public void setNoticeSendObj(java.lang.String value) {
		this.noticeSendObj = value;
	}
	
	
	public java.lang.Integer getNoticeSeq() {
		return this.noticeSeq;
	}
	
	public void setNoticeSeq(java.lang.Integer value) {
		this.noticeSeq = value;
	}
	
	
	public java.lang.String getNoticeNo() {
		return this.noticeNo;
	}
	
	public void setNoticeNo(java.lang.String value) {
		this.noticeNo = value;
	}
	
	
	public java.lang.String getNoticeDesc() {
		return this.noticeDesc;
	}
	
	public void setNoticeDesc(java.lang.String value) {
		this.noticeDesc = value;
	}
	
	
	public java.lang.String getNoticeSendType() {
		return this.noticeSendType;
	}
	
	public void setNoticeSendType(java.lang.String value) {
		this.noticeSendType = value;
	}
	
	
	public java.lang.String getNoticeMsg() {
		return this.noticeMsg;
	}
	
	public void setNoticeMsg(java.lang.String value) {
		this.noticeMsg = value;
	}
	
	
	public java.lang.String getNoticeStat() {
		return this.noticeStat;
	}
	
	public void setNoticeStat(java.lang.String value) {
		this.noticeStat = value;
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
	

	

}

