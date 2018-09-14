
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccVopenBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccVopenBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_VACCT_NO = "虚拟账户账号";
	public static final String ALIAS_OPEN_TIME = "开户时间";
	public static final String ALIAS_OPEN_CHNL = "开户渠道";
	public static final String ALIAS_OPEN_ORG = "开户机构";
	public static final String ALIAS_ACCT_KIND = "开户账户种类 账户种类";
	public static final String ALIAS_VBIND_FLAG = "绑卡方式 当E_OPEN_FLAG为1时 1：第三方鉴权 2：打款验证";
	public static final String ALIAS_THIRD_AUTH_CHNL = "第三方鉴权渠道 当E_BIND_FLAG 为1时 0001：核心系统 0002：二代支付系统 0003：银联全渠道支付平台 0004：帮付宝 0005：快钱";
	public static final String ALIAS_OTHER_VERIFY_AMT = "打款验证金额 当E_BIND_FLAG为2时";
	public static final String ALIAS_VBIND_BANK_FLAG = "虚拟账户绑定账户银行类别 1：本行账户 2：他行账户";
	public static final String ALIAS_VBIND_BANK_CODE = "虚拟账户绑定账户行号";
	public static final String ALIAS_VBIND_BANK_NAME = "虚拟账户绑定账户行名";
	public static final String ALIAS_VBIND_OPEN_CODE = "虚拟账户绑定账户开户行号";
	public static final String ALIAS_VBIND_ACCT_NO = "虚拟账户绑定账户";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 虚拟账户账号       db_column: V_ACCT_NO 
     */ 	
	private java.lang.String vacctNo;
    /**
     * 开户时间       db_column: OPEN_TIME 
     */ 	
	private java.util.Date openTime;
    /**
     * 开户渠道       db_column: OPEN_CHNL 
     */ 	
	private java.lang.String openChnl;
    /**
     * 开户机构       db_column: OPEN_ORG 
     */ 	
	private java.lang.String openOrg;
    /**
     * 开户账户种类 账户种类       db_column: ACCT_KIND 
     */ 	
	private java.lang.String acctKind;
    /**
     * 绑卡方式 当E_OPEN_FLAG为1时 1：第三方鉴权 2：打款验证       db_column: V_BIND_FLAG 
     */ 	
	private java.lang.String vbindFlag;
    /**
     * 第三方鉴权渠道 当E_BIND_FLAG 为1时 0001：核心系统 0002：二代支付系统 0003：银联全渠道支付平台 0004：帮付宝 0005：快钱       db_column: THIRD_AUTH_CHNL 
     */ 	
	private java.lang.String thirdAuthChnl;
    /**
     * 打款验证金额 当E_BIND_FLAG为2时       db_column: OTHER_VERIFY_AMT 
     */ 	
	private java.math.BigDecimal otherVerifyAmt;
    /**
     * 虚拟账户绑定账户银行类别 1：本行账户 2：他行账户       db_column: V_BIND_BANK_FLAG 
     */ 	
	private java.lang.String vbindBankFlag;
    /**
     * 虚拟账户绑定账户行号       db_column: V_BIND_BANK_CODE 
     */ 	
	private java.lang.String vbindBankCode;
    /**
     * 虚拟账户绑定账户行名       db_column: V_BIND_BANK_NAME 
     */ 	
	private java.lang.String vbindBankName;
    /**
     * 虚拟账户绑定账户开户行号       db_column: V_BIND_OPEN_CODE 
     */ 	
	private java.lang.String vbindOpenCode;
    /**
     * 虚拟账户绑定账户       db_column: V_BIND_ACCT_NO 
     */ 	
	private java.lang.String vbindAcctNo;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用3       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getVacctNo() {
		return this.vacctNo;
	}
	
	public void setVacctNo(java.lang.String value) {
		this.vacctNo = value;
	}
	
	
	public java.util.Date getOpenTime() {
		return this.openTime;
	}
	
	public void setOpenTime(java.util.Date value) {
		this.openTime = value;
	}
	
	
	public java.lang.String getOpenChnl() {
		return this.openChnl;
	}
	
	public void setOpenChnl(java.lang.String value) {
		this.openChnl = value;
	}
	
	
	public java.lang.String getOpenOrg() {
		return this.openOrg;
	}
	
	public void setOpenOrg(java.lang.String value) {
		this.openOrg = value;
	}
	
	
	public java.lang.String getAcctKind() {
		return this.acctKind;
	}
	
	public void setAcctKind(java.lang.String value) {
		this.acctKind = value;
	}
	
	
	public java.lang.String getVbindFlag() {
		return this.vbindFlag;
	}
	
	public void setVbindFlag(java.lang.String value) {
		this.vbindFlag = value;
	}
	
	
	public java.lang.String getThirdAuthChnl() {
		return this.thirdAuthChnl;
	}
	
	public void setThirdAuthChnl(java.lang.String value) {
		this.thirdAuthChnl = value;
	}
	
	
	public java.math.BigDecimal getOtherVerifyAmt() {
		return this.otherVerifyAmt;
	}
	
	public void setOtherVerifyAmt(java.math.BigDecimal value) {
		this.otherVerifyAmt = value;
	}
	
	
	public java.lang.String getVbindBankFlag() {
		return this.vbindBankFlag;
	}
	
	public void setVbindBankFlag(java.lang.String value) {
		this.vbindBankFlag = value;
	}
	
	
	public java.lang.String getVbindBankCode() {
		return this.vbindBankCode;
	}
	
	public void setVbindBankCode(java.lang.String value) {
		this.vbindBankCode = value;
	}
	
	
	public java.lang.String getVbindBankName() {
		return this.vbindBankName;
	}
	
	public void setVbindBankName(java.lang.String value) {
		this.vbindBankName = value;
	}
	
	
	public java.lang.String getVbindOpenCode() {
		return this.vbindOpenCode;
	}
	
	public void setVbindOpenCode(java.lang.String value) {
		this.vbindOpenCode = value;
	}
	
	
	public java.lang.String getVbindAcctNo() {
		return this.vbindAcctNo;
	}
	
	public void setVbindAcctNo(java.lang.String value) {
		this.vbindAcctNo = value;
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
	
	
	public java.lang.String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(java.lang.String value) {
		this.remark3 = value;
	}
	

	

}

