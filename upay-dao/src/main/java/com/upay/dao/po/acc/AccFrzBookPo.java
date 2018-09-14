
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccFrzBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccFrzBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_FRZ_TIME = "首次冻结时间";
	public static final String ALIAS_FRZ_NO = "冻结编号 见附录";
	public static final String ALIAS_FRZ_FILE_NO = "冻结通知书编号";
	public static final String ALIAS_FRZ_AUTHORITY = "冻结机关 01：人民法院 02：税务机关 03：海关 04：人民检察院 05：公安机关 06：国家安全机关 07：军队保卫部门 08：监狱 09：走私犯罪侦查机关 10：工商行政管理机关 91：直销银行交易性冻结（T+0基金）";
	public static final String ALIAS_FRZ_AUTHORITY_NAME = "冻结机关名称";
	public static final String ALIAS_FRZ_AUTHORITY_CODE = "冻结机关代码 直销银行自动生成的冻结机关代码 见附录";
	public static final String ALIAS_USER_ID = "用户号";
	public static final String ALIAS_CERT_NO = "身份证号";
	public static final String ALIAS_CERT_NAME = "户名";
	public static final String ALIAS_ACCT_TYPE = "账户类型 见附录 默认虚拟账户";
	public static final String ALIAS_REMAIN_FRZ_AMT = "剩余冻结金额 当FRZ_STAT为4时";
	public static final String ALIAS_ACCT_NO = "冻结账号 默认虚拟账号";
	public static final String ALIAS_FRZ_ORDER_NUM = "当前冻结序号";
	public static final String ALIAS_IS_UNFRZ = "解冻标志 0：未解冻 1：已解冻";
	public static final String ALIAS_FRZ_FILE_ADDR = "冻结通知书下载地址";
	public static final String ALIAS_UNFRZ_OPER = "解冻操作员";
	public static final String ALIAS_REMARK1 = "备用";
	

	//columns START
    /**
     * 首次冻结时间       db_column: FRZ_TIME 
     */ 	
	private java.util.Date frzTime;
    /**
     * 冻结编号 见附录       db_column: FRZ_NO 
     */ 	
	private java.lang.String frzNo;
    /**
     * 冻结通知书编号       db_column: FRZ_FILE_NO 
     */ 	
	private java.lang.String frzFileNo;
    /**
     * 冻结机关 01：人民法院 02：税务机关 03：海关 04：人民检察院 05：公安机关 06：国家安全机关 07：军队保卫部门 08：监狱 09：走私犯罪侦查机关 10：工商行政管理机关 91：直销银行交易性冻结（T+0基金）       db_column: FRZ_AUTHORITY 
     */ 	
	private java.lang.String frzAuthority;
    /**
     * 冻结机关名称       db_column: FRZ_AUTHORITY_NAME 
     */ 	
	private java.lang.String frzAuthorityName;
    /**
     * 冻结机关代码 直销银行自动生成的冻结机关代码 见附录       db_column: FRZ_AUTHORITY_CODE 
     */ 	
	private java.lang.String frzAuthorityCode;
    /**
     * 用户号       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 身份证号       db_column: CERT_NO 
     */ 	
	private java.lang.String certNo;
    /**
     * 户名       db_column: CERT_NAME 
     */ 	
	private java.lang.String certName;
    /**
     * 账户类型 见附录 默认虚拟账户       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 剩余冻结金额 当FRZ_STAT为4时       db_column: REMAIN_FRZ_AMT 
     */ 	
	private java.math.BigDecimal remainFrzAmt;
    /**
     * 冻结账号 默认虚拟账号       db_column: ACCT_NO 
     */ 	
	private java.lang.String acctNo;
    /**
     * 当前冻结序号       db_column: FRZ_ORDER_NUM 
     */ 	
	private java.lang.Integer frzOrderNum;
    /**
     * 解冻标志 0：未解冻 1：已解冻       db_column: IS_UNFRZ 
     */ 	
	private java.lang.String isUnfrz;
    /**
     * 冻结通知书下载地址       db_column: FRZ_FILE_ADDR 
     */ 	
	private java.lang.String frzFileAddr;
    /**
     * 解冻操作员       db_column: UNFRZ_OPER 
     */ 	
	private java.lang.String unfrzOper;
    /**
     * 备用       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
	//columns END


	
	
	public java.util.Date getFrzTime() {
		return this.frzTime;
	}
	
	public void setFrzTime(java.util.Date value) {
		this.frzTime = value;
	}
	
	
	public java.lang.String getFrzNo() {
		return this.frzNo;
	}
	
	public void setFrzNo(java.lang.String value) {
		this.frzNo = value;
	}
	
	
	public java.lang.String getFrzFileNo() {
		return this.frzFileNo;
	}
	
	public void setFrzFileNo(java.lang.String value) {
		this.frzFileNo = value;
	}
	
	
	public java.lang.String getFrzAuthority() {
		return this.frzAuthority;
	}
	
	public void setFrzAuthority(java.lang.String value) {
		this.frzAuthority = value;
	}
	
	
	public java.lang.String getFrzAuthorityName() {
		return this.frzAuthorityName;
	}
	
	public void setFrzAuthorityName(java.lang.String value) {
		this.frzAuthorityName = value;
	}
	
	
	public java.lang.String getFrzAuthorityCode() {
		return this.frzAuthorityCode;
	}
	
	public void setFrzAuthorityCode(java.lang.String value) {
		this.frzAuthorityCode = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(java.lang.String value) {
		this.certNo = value;
	}
	
	
	public java.lang.String getCertName() {
		return this.certName;
	}
	
	public void setCertName(java.lang.String value) {
		this.certName = value;
	}
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	
	
	public java.math.BigDecimal getRemainFrzAmt() {
		return this.remainFrzAmt;
	}
	
	public void setRemainFrzAmt(java.math.BigDecimal value) {
		this.remainFrzAmt = value;
	}
	
	
	public java.lang.String getAcctNo() {
		return this.acctNo;
	}
	
	public void setAcctNo(java.lang.String value) {
		this.acctNo = value;
	}
	
	
	public java.lang.Integer getFrzOrderNum() {
		return this.frzOrderNum;
	}
	
	public void setFrzOrderNum(java.lang.Integer value) {
		this.frzOrderNum = value;
	}
	
	
	public java.lang.String getIsUnfrz() {
		return this.isUnfrz;
	}
	
	public void setIsUnfrz(java.lang.String value) {
		this.isUnfrz = value;
	}
	
	
	public java.lang.String getFrzFileAddr() {
		return this.frzFileAddr;
	}
	
	public void setFrzFileAddr(java.lang.String value) {
		this.frzFileAddr = value;
	}
	
	
	public java.lang.String getUnfrzOper() {
		return this.unfrzOper;
	}
	
	public void setUnfrzOper(java.lang.String value) {
		this.unfrzOper = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	

	

}

