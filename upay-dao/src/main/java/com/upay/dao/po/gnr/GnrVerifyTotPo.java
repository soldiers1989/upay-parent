
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrVerifyTotPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrVerifyTot";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TRANS_DATE = "交易日期";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_VERIFY_LOCK_FLAG = "锁定标志，0：否   1：是";
	public static final String ALIAS_SMS_RESEND_NUM = "短信验证码频繁发送次数，短信验证码连续错误次数";
	public static final String ALIAS_SMS_RESEND_TIME = "短信验证码最近申请时间";
	public static final String ALIAS_SMS_VERTIFY_ERR_NUM = "短信验证码连续错误次数，连续错误次数";
	public static final String ALIAS_EMAIL_RESEND_NUM = "邮件验证频繁发送次数，当日连续重发未验证次数";
	public static final String ALIAS_EMAIL_RESEND_TIME = "邮件验证最近申请时间";
	public static final String ALIAS_EMAIL_VERTIFY_ERR_NUM = "邮件验证连续错误次数，默认为0";
	public static final String ALIAS_GRAP_RESEND_NUM = "图形验证码频繁刷新次数，当日连续重发未验证次数";
	public static final String ALIAS_GRAP_RESEND_TIME = "图形验证码最近申请时间";
	public static final String ALIAS_GRAP_VERTIFY_ERR_NUM = "图形验证码连续错误次数";
	

	//columns START
    /**
     * 交易日期       db_column: TRANS_DATE 
     */ 	
	private java.util.Date transDate;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 锁定标志，0：否   1：是       db_column: VERIFY_LOCK_FLAG 
     */ 	
	private java.lang.String verifyLockFlag;
    /**
     * 短信验证码频繁发送次数，短信验证码连续错误次数       db_column: SMS_RESEND_NUM 
     */ 	
	private java.lang.Integer smsResendNum;
    /**
     * 短信验证码最近申请时间       db_column: SMS_RESEND_TIME 
     */ 	
	private java.util.Date smsResendTime;
    /**
     * 短信验证码连续错误次数，连续错误次数       db_column: SMS_VERTIFY_ERR_NUM 
     */ 	
	private java.lang.Integer smsVertifyErrNum;
    /**
     * 邮件验证频繁发送次数，当日连续重发未验证次数       db_column: EMAIL_RESEND_NUM 
     */ 	
	private java.lang.Integer emailResendNum;
    /**
     * 邮件验证最近申请时间       db_column: EMAIL_RESEND_TIME 
     */ 	
	private java.util.Date emailResendTime;
    /**
     * 邮件验证连续错误次数，默认为0       db_column: EMAIL_VERTIFY_ERR_NUM 
     */ 	
	private java.lang.Integer emailVertifyErrNum;
    /**
     * 图形验证码频繁刷新次数，当日连续重发未验证次数       db_column: GRAP_RESEND_NUM 
     */ 	
	private java.lang.Integer grapResendNum;
    /**
     * 图形验证码最近申请时间       db_column: GRAP_RESEND_TIME 
     */ 	
	private java.util.Date grapResendTime;
    /**
     * 图形验证码连续错误次数       db_column: GRAP_VERTIFY_ERR_NUM 
     */ 	
	private java.lang.Integer grapVertifyErrNum;
	//columns END


	
	
	public java.util.Date getTransDate() {
		return this.transDate;
	}
	
	public void setTransDate(java.util.Date value) {
		this.transDate = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getVerifyLockFlag() {
		return this.verifyLockFlag;
	}
	
	public void setVerifyLockFlag(java.lang.String value) {
		this.verifyLockFlag = value;
	}
	
	
	public java.lang.Integer getSmsResendNum() {
		return this.smsResendNum;
	}
	
	public void setSmsResendNum(java.lang.Integer value) {
		this.smsResendNum = value;
	}
	
	
	public java.util.Date getSmsResendTime() {
		return this.smsResendTime;
	}
	
	public void setSmsResendTime(java.util.Date value) {
		this.smsResendTime = value;
	}
	
	
	public java.lang.Integer getSmsVertifyErrNum() {
		return this.smsVertifyErrNum;
	}
	
	public void setSmsVertifyErrNum(java.lang.Integer value) {
		this.smsVertifyErrNum = value;
	}
	
	
	public java.lang.Integer getEmailResendNum() {
		return this.emailResendNum;
	}
	
	public void setEmailResendNum(java.lang.Integer value) {
		this.emailResendNum = value;
	}
	
	
	public java.util.Date getEmailResendTime() {
		return this.emailResendTime;
	}
	
	public void setEmailResendTime(java.util.Date value) {
		this.emailResendTime = value;
	}
	
	
	public java.lang.Integer getEmailVertifyErrNum() {
		return this.emailVertifyErrNum;
	}
	
	public void setEmailVertifyErrNum(java.lang.Integer value) {
		this.emailVertifyErrNum = value;
	}
	
	
	public java.lang.Integer getGrapResendNum() {
		return this.grapResendNum;
	}
	
	public void setGrapResendNum(java.lang.Integer value) {
		this.grapResendNum = value;
	}
	
	
	public java.util.Date getGrapResendTime() {
		return this.grapResendTime;
	}
	
	public void setGrapResendTime(java.util.Date value) {
		this.grapResendTime = value;
	}
	
	
	public java.lang.Integer getGrapVertifyErrNum() {
		return this.grapVertifyErrNum;
	}
	
	public void setGrapVertifyErrNum(java.lang.Integer value) {
		this.grapVertifyErrNum = value;
	}
	

	

}

