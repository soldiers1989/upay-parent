
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayBankInfoPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayBankInfo";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BANK_NO = "银行机构行号 银行在支付平台机构号";
	public static final String ALIAS_BANK_NAME = "银行机构名称 银行在支付平台机构名称";
	public static final String ALIAS_LOGO_ID = "银行LOGO 对应LOGO配置表的LOGO_ID";
	public static final String ALIAS_STAT = "状态 0：停用 1：启用";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 银行机构行号 银行在支付平台机构号       db_column: BANK_NO 
     */ 	
	private java.lang.String bankNo;
    /**
     * 银行机构名称 银行在支付平台机构名称       db_column: BANK_NAME 
     */ 	
	private java.lang.String bankName;
    /**
     * 银行LOGO 对应LOGO配置表的LOGO_ID       db_column: LOGO_ID 
     */ 	
	private java.lang.String logoId;
    /**
     * 状态 0：停用 1：启用       db_column: STAT 
     */ 	
	private java.lang.String stat;
    /**
     * 最后变更时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
	//columns END


	
	
	public java.lang.String getBankNo() {
		return this.bankNo;
	}
	
	public void setBankNo(java.lang.String value) {
		this.bankNo = value;
	}
	
	
	public java.lang.String getBankName() {
		return this.bankName;
	}
	
	public void setBankName(java.lang.String value) {
		this.bankName = value;
	}
	
	
	public java.lang.String getLogoId() {
		return this.logoId;
	}
	
	public void setLogoId(java.lang.String value) {
		this.logoId = value;
	}
	
	
	public java.lang.String getStat() {
		return this.stat;
	}
	
	public void setStat(java.lang.String value) {
		this.stat = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
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

