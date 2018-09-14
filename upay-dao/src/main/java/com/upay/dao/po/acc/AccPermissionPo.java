
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccPermissionPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccPermission";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ACCT_TYPE = "账户类型";
	public static final String ALIAS_VACCT_CERT_LEVEL = "账户类型为电子账户时必填 0:弱实名认证 1:强实名认证";
	public static final String ALIAS_SUIT_VACCT_STAT = "账户状态，此账户类型对此交易的使用账户状态";
	public static final String ALIAS_SUIT_STP_STAT = "止付状态";
	public static final String ALIAS_SUIT_FRZ_STAT = "冻结状态";
	public static final String ALIAS_SUIT_CHNL_ID = "渠道权限，账户类型适用渠道";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_PRM_STAT = "权限状态 0:关闭 1:开通";
	public static final String ALIAS_LAST_UPDATE_OPER = "最后更新操作员";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后更新时间";
	public static final String ALIAS_REMARK1 = "备用1";
	

	//columns START
    /**
     * 账户类型       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 账户类型为电子账户时必填 0:弱实名认证 1:强实名认证       db_column: V_ACCT_CERT_LEVEL 
     */ 	
	private java.lang.String vacctCertLevel;
    /**
     * 账户状态，此账户类型对此交易的使用账户状态       db_column: SUIT_V_ACCT_STAT 
     */ 	
	private java.lang.String suitVacctStat;
    /**
     * 止付状态       db_column: SUIT_STP_STAT 
     */ 	
	private java.lang.String suitStpStat;
    /**
     * 冻结状态       db_column: SUIT_FRZ_STAT 
     */ 	
	private java.lang.String suitFrzStat;
    /**
     * 渠道权限，账户类型适用渠道       db_column: SUIT_CHNL_ID 
     */ 	
	private java.lang.String suitChnlId;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 权限状态 0:关闭 1:开通       db_column: PRM_STAT 
     */ 	
	private java.lang.String prmStat;
    /**
     * 最后更新操作员       db_column: LAST_UPDATE_OPER 
     */ 	
	private java.lang.String lastUpdateOper;
    /**
     * 最后更新时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
	//columns END


	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	
	
	public java.lang.String getVacctCertLevel() {
		return this.vacctCertLevel;
	}
	
	public void setVacctCertLevel(java.lang.String value) {
		this.vacctCertLevel = value;
	}
	
	
	public java.lang.String getSuitVacctStat() {
		return this.suitVacctStat;
	}
	
	public void setSuitVacctStat(java.lang.String value) {
		this.suitVacctStat = value;
	}
	
	
	public java.lang.String getSuitStpStat() {
		return this.suitStpStat;
	}
	
	public void setSuitStpStat(java.lang.String value) {
		this.suitStpStat = value;
	}
	
	
	public java.lang.String getSuitFrzStat() {
		return this.suitFrzStat;
	}
	
	public void setSuitFrzStat(java.lang.String value) {
		this.suitFrzStat = value;
	}
	
	
	public java.lang.String getSuitChnlId() {
		return this.suitChnlId;
	}
	
	public void setSuitChnlId(java.lang.String value) {
		this.suitChnlId = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getPrmStat() {
		return this.prmStat;
	}
	
	public void setPrmStat(java.lang.String value) {
		this.prmStat = value;
	}
	
	
	public java.lang.String getLastUpdateOper() {
		return this.lastUpdateOper;
	}
	
	public void setLastUpdateOper(java.lang.String value) {
		this.lastUpdateOper = value;
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
	

	

}

