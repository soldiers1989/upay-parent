
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrUserNoticeListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrUserNoticeList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_NOTICE_NO = "消息编号";
	public static final String ALIAS_NOTICE_SEND_TYPE = "消息发送方类型 1：直销银行系统 2：平台用户";
	public static final String ALIAS_NOTICE_USER = "消息来源用户 当NOTICE_SEND_TYPE为2时";
	public static final String ALIAS_NOTICE_RIGHT_MSG = "消息信息";
	public static final String ALIAS_NOTICE_EFFECT_TIME = "消息产生时间";
	public static final String ALIAS_IS_NOTICE_READ = "是否已读 0：未读 1：已读";
	

	//columns START
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 消息编号       db_column: NOTICE_NO 
     */ 	
	private java.lang.String noticeNo;
    /**
     * 消息发送方类型 1：直销银行系统 2：平台用户       db_column: NOTICE_SEND_TYPE 
     */ 	
	private java.lang.String noticeSendType;
    /**
     * 消息来源用户 当NOTICE_SEND_TYPE为2时       db_column: NOTICE_USER 
     */ 	
	private java.lang.String noticeUser;
    /**
     * 消息信息       db_column: NOTICE_RIGHT_MSG 
     */ 	
	private java.lang.String noticeRightMsg;
    /**
     * 消息产生时间       db_column: NOTICE_EFFECT_TIME 
     */ 	
	private java.util.Date noticeEffectTime;
    /**
     * 是否已读 0：未读 1：已读       db_column: IS_NOTICE_READ 
     */ 	
	private java.lang.String isNoticeRead;
	//columns END


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getNoticeNo() {
		return this.noticeNo;
	}
	
	public void setNoticeNo(java.lang.String value) {
		this.noticeNo = value;
	}
	
	
	public java.lang.String getNoticeSendType() {
		return this.noticeSendType;
	}
	
	public void setNoticeSendType(java.lang.String value) {
		this.noticeSendType = value;
	}
	
	
	public java.lang.String getNoticeUser() {
		return this.noticeUser;
	}
	
	public void setNoticeUser(java.lang.String value) {
		this.noticeUser = value;
	}
	
	
	public java.lang.String getNoticeRightMsg() {
		return this.noticeRightMsg;
	}
	
	public void setNoticeRightMsg(java.lang.String value) {
		this.noticeRightMsg = value;
	}
	
	
	public java.util.Date getNoticeEffectTime() {
		return this.noticeEffectTime;
	}
	
	public void setNoticeEffectTime(java.util.Date value) {
		this.noticeEffectTime = value;
	}
	
	
	public java.lang.String getIsNoticeRead() {
		return this.isNoticeRead;
	}
	
	public void setIsNoticeRead(java.lang.String value) {
		this.isNoticeRead = value;
	}
	

	

}

