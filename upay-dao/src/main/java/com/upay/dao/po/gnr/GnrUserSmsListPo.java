
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrUserSmsListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrUserSmsList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_SMS_SEND_NO = "通道号 短信发送通道号";
	public static final String ALIAS_MOBILE = "接收手机号";
	public static final String ALIAS_SMS_NO = "短信编号";
	public static final String ALIAS_SMS_RIGHT_MSG = "短信信息";
	public static final String ALIAS_SMS_SEND_TIME = "短信发送时间";
	

	//columns START
    /**
     * 通道号 短信发送通道号       db_column: SMS_SEND_NO 
     */ 	
	private java.lang.String smsSendNo;
    /**
     * 接收手机号       db_column: MOBILE 
     */ 	
	private java.lang.String mobile;
    /**
     * 短信编号       db_column: SMS_NO 
     */ 	
	private java.lang.String smsNo;
    /**
     * 短信信息       db_column: SMS_RIGHT_MSG 
     */ 	
	private java.lang.String smsRightMsg;
    /**
     * 短信发送时间       db_column: SMS_SEND_TIME 
     */ 	
	private java.util.Date smsSendTime;
	//columns END


	
	
	public java.lang.String getSmsSendNo() {
		return this.smsSendNo;
	}
	
	public void setSmsSendNo(java.lang.String value) {
		this.smsSendNo = value;
	}
	
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	
	
	public java.lang.String getSmsNo() {
		return this.smsNo;
	}
	
	public void setSmsNo(java.lang.String value) {
		this.smsNo = value;
	}
	
	
	public java.lang.String getSmsRightMsg() {
		return this.smsRightMsg;
	}
	
	public void setSmsRightMsg(java.lang.String value) {
		this.smsRightMsg = value;
	}
	
	
	public java.util.Date getSmsSendTime() {
		return this.smsSendTime;
	}
	
	public void setSmsSendTime(java.util.Date value) {
		this.smsSendTime = value;
	}
	

	

}

