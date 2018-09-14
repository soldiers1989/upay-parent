
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeAssInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeAssInfo";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ASS_ID = "分润方ID";
	public static final String ALIAS_ASS_ACCT_NO = "分润方收款账号";
	public static final String ALIAS_ASS_ACCT_TYPE = "分润方账户类型 借记卡-11 贷记卡-12 个人结算户-13 第三方平台账户-14 单位结算户-15 内部账户-21 他行借记卡-22 本行对公账户-23他行对公账户-24";
	public static final String ALIAS_ASS_NAME = "分润方名称";
	public static final String ALIAS_ASS_ACCT_NAME = "分润账户姓名";
	public static final String ALIAS_CERT_TYPE = "证件类型";
	public static final String ALIAS_CERT_NO = "证件号码";
	public static final String ALIAS_BANK_ID = "bankId";
	public static final String ALIAS_MOBILE = "预留手机号";
	public static final String ALIAS_REMARK1 = "备用字段1";
	public static final String ALIAS_REMARK2 = "备用字段1";
	

	//columns START
    /**
     * 分润方ID       db_column: ASS_ID 
     */ 	
	private java.lang.String assId;
    /**
     * 分润方收款账号       db_column: ASS_ACCT_NO 
     */ 	
	private java.lang.String assAcctNo;
    /**
     * 分润方账户类型 借记卡-11 贷记卡-12 个人结算户-13 第三方平台账户-14 单位结算户-15 内部账户-21 他行借记卡-22 本行对公账户-23他行对公账户-24       db_column: ASS_ACCT_TYPE 
     */ 	
	private java.lang.String assAcctType;
    /**
     * 分润方名称       db_column: ASS_NAME 
     */ 	
	private java.lang.String assName;
    /**
     * 分润账户姓名       db_column: ASS_ACCT_NAME 
     */ 	
	private java.lang.String assAcctName;
    /**
     * 证件类型       db_column: CERT_TYPE 
     */ 	
	private java.lang.String certType;
    /**
     * 证件号码       db_column: CERT_NO 
     */ 	
	private java.lang.String certNo;
    /**
     * bankId       db_column: BANK_ID 
     */ 	
	private java.lang.String bankId;
    /**
     * 预留手机号       db_column: MOBILE 
     */ 	
	private java.lang.String mobile;
    /**
     * 备用字段1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用字段1       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
	//columns END


	
	
	public java.lang.String getAssId() {
		return this.assId;
	}
	
	public void setAssId(java.lang.String value) {
		this.assId = value;
	}
	
	
	public java.lang.String getAssAcctNo() {
		return this.assAcctNo;
	}
	
	public void setAssAcctNo(java.lang.String value) {
		this.assAcctNo = value;
	}
	
	
	public java.lang.String getAssAcctType() {
		return this.assAcctType;
	}
	
	public void setAssAcctType(java.lang.String value) {
		this.assAcctType = value;
	}
	
	
	public java.lang.String getAssName() {
		return this.assName;
	}
	
	public void setAssName(java.lang.String value) {
		this.assName = value;
	}
	
	
	public java.lang.String getAssAcctName() {
		return this.assAcctName;
	}
	
	public void setAssAcctName(java.lang.String value) {
		this.assAcctName = value;
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
	
	
	public java.lang.String getBankId() {
		return this.bankId;
	}
	
	public void setBankId(java.lang.String value) {
		this.bankId = value;
	}
	
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
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
	

	

}

