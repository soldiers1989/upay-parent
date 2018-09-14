
package com.upay.dao.po.gnr;
import com.pactera.dipper.po.BasePo;

public class GnrUserCertListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "GnrUserCertList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CERT_CHECK_CHNL = "核查渠道";
	public static final String ALIAS_CERT_TYPE = "证件类型";
	public static final String ALIAS_CERT_NO = "证件号码";
	public static final String ALIAS_CERT_CHECK_RESULT = "核查结果 0：成功 1：失败";
	public static final String ALIAS_CERT_CHECK_FAIL_MSG = "核查失败原因";
	public static final String ALIAS_CERT_CHECK_TIME = "核查时间";
	

	//columns START
    /**
     * 核查渠道       db_column: CERT_CHECK_CHNL 
     */ 	
	private java.lang.String certCheckChnl;
    /**
     * 证件类型       db_column: CERT_TYPE 
     */ 	
	private java.lang.String certType;
    /**
     * 证件号码       db_column: CERT_NO 
     */ 	
	private java.lang.String certNo;
    /**
     * 核查结果 0：成功 1：失败       db_column: CERT_CHECK_RESULT 
     */ 	
	private java.lang.String certCheckResult;
    /**
     * 核查失败原因       db_column: CERT_CHECK_FAIL_MSG 
     */ 	
	private java.lang.String certCheckFailMsg;
    /**
     * 核查时间       db_column: CERT_CHECK_TIME 
     */ 	
	private java.util.Date certCheckTime;
	//columns END


	
	
	public java.lang.String getCertCheckChnl() {
		return this.certCheckChnl;
	}
	
	public void setCertCheckChnl(java.lang.String value) {
		this.certCheckChnl = value;
	}
	
	
	public java.lang.String getCertType() {
		return this.certType;
	}
	
	public void setCertType(java.lang.String value) {
		this.certType = value;
	}
	
	
	public java.lang.String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(java.lang.String value) {
		this.certNo = value;
	}
	
	
	public java.lang.String getCertCheckResult() {
		return this.certCheckResult;
	}
	
	public void setCertCheckResult(java.lang.String value) {
		this.certCheckResult = value;
	}
	
	
	public java.lang.String getCertCheckFailMsg() {
		return this.certCheckFailMsg;
	}
	
	public void setCertCheckFailMsg(java.lang.String value) {
		this.certCheckFailMsg = value;
	}
	
	
	public java.util.Date getCertCheckTime() {
		return this.certCheckTime;
	}
	
	public void setCertCheckTime(java.util.Date value) {
		this.certCheckTime = value;
	}
	

	

}

