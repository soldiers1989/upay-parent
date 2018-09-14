
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrComRegRecPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrComRegRec";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_COM_EMAIL = "企业邮箱";
	public static final String ALIAS_SID = "验签内容";
	public static final String ALIAS_GENERATE_FLAG = "是否已生产用户 0-没有 1-已生成";
	public static final String ALIAS_SEND_NUM = "发送次数";
	public static final String ALIAS_VALIED_DATE = "有效期";
	public static final String ALIAS_LAST_SEND_TIME = "最后发送时间";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	

	//columns START
    /**
     * 企业邮箱       db_column: COM_EMAIL 
     */ 	
	private java.lang.String comEmail;
    /**
     * 验签内容       db_column: SID 
     */ 	
	private java.lang.String sid;
    /**
     * 是否已生产用户 0-没有 1-已生成       db_column: GENERATE_FLAG 
     */ 	
	private java.lang.String generateFlag;
    /**
     * 发送次数       db_column: SEND_NUM 
     */ 	
	private java.lang.Integer sendNum;
    /**
     * 有效期       db_column: VALIED_DATE 
     */ 	
	private java.util.Date valiedDate;
    /**
     * 最后发送时间       db_column: LAST_SEND_TIME 
     */ 	
	private java.util.Date lastSendTime;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
	//columns END


	
	
	public java.lang.String getComEmail() {
		return this.comEmail;
	}
	
	public void setComEmail(java.lang.String value) {
		this.comEmail = value;
	}
	
	
	public java.lang.String getSid() {
		return this.sid;
	}
	
	public void setSid(java.lang.String value) {
		this.sid = value;
	}
	
	
	public java.lang.String getGenerateFlag() {
		return this.generateFlag;
	}
	
	public void setGenerateFlag(java.lang.String value) {
		this.generateFlag = value;
	}
	
	
	public java.lang.Integer getSendNum() {
		return this.sendNum;
	}
	
	public void setSendNum(java.lang.Integer value) {
		this.sendNum = value;
	}
	
	
	public java.util.Date getValiedDate() {
		return this.valiedDate;
	}
	
	public void setValiedDate(java.util.Date value) {
		this.valiedDate = value;
	}
	
	
	public java.util.Date getLastSendTime() {
		return this.lastSendTime;
	}
	
	public void setLastSendTime(java.util.Date value) {
		this.lastSendTime = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	

	

}

