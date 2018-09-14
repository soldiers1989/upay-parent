
package com.upay.dao.po.mer;
import com.pactera.dipper.po.BasePo;

public class MerWhiteListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "MerWhiteList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_ACCT_NO = "白名单帐号";
	public static final String ALIAS_BANK_NO = "银行机构行号 白名单帐号归属银行银行在支付平台的机构号";
	public static final String ALIAS_REMARK1 = "备注1";
	public static final String ALIAS_REMARK2 = "备注2";
	public static final String ALIAS_ACCT_NAME = "账户户名";
	public static final String ALIAS_CERT_TYPE = "证件类型";
	public static final String ALIAS_CERT_NO = "证件号码";
	public static final String ALIAS_ACCT_TYPE = "账户类型";
	

	//columns START
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 白名单帐号       db_column: ACCT_NO 
     */ 	
	private java.lang.String acctNo;
    /**
     * 银行机构行号 白名单帐号归属银行银行在支付平台的机构号       db_column: BANK_NO 
     */ 	
	private java.lang.String bankNo;
    /**
     * 备注1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备注2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 账户户名       db_column: ACCT_NAME 
     */ 	
	private java.lang.String acctName;
    /**
     * 证件类型       db_column: CERT_TYPE 
     */ 	
	private java.lang.String certType;
    /**
     * 证件号码       db_column: CERT_NO 
     */ 	
	private java.lang.String certNo;
    /**
     * 账户类型       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
	//columns END


	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getAcctNo() {
		return this.acctNo;
	}
	
	public void setAcctNo(java.lang.String value) {
		this.acctNo = value;
	}
	
	
	public java.lang.String getBankNo() {
		return this.bankNo;
	}
	
	public void setBankNo(java.lang.String value) {
		this.bankNo = value;
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
	
	
	public java.lang.String getAcctName() {
		return this.acctName;
	}
	
	public void setAcctName(java.lang.String value) {
		this.acctName = value;
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
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	

	

}

