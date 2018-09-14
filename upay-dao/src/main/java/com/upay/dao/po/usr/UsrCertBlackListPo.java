
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrCertBlackListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrCertBlackList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BLACKLIST_INPUT_DATE = "导入时间";
	public static final String ALIAS_BLACKLIST_INPUT_TYPE = "导入处理方式 1：手工 2：批量导入";
	public static final String ALIAS_BLACKLIST_FLAG = "黑名单标志 0：正常 1：黑名单";
	public static final String ALIAS_CERT_TYPE = "证件类型";
	public static final String ALIAS_CERT_NO = "证件号码";
	public static final String ALIAS_BLACK_OPER = "执行操作员";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后一次更新时间";
	public static final String ALIAS_BLACK_INPUT_REASON = "导入原因";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_APPROVE_TYPE = "认证类型 01：免密授权 02：普通会员 03：商户会员";
	

	//columns START
    /**
     * 导入时间       db_column: BLACKLIST_INPUT_DATE 
     */ 	
	private java.util.Date blacklistInputDate;
    /**
     * 导入处理方式 1：手工 2：批量导入       db_column: BLACKLIST_INPUT_TYPE 
     */ 	
	private java.lang.String blacklistInputType;
    /**
     * 黑名单标志 0：正常 1：黑名单       db_column: BLACKLIST_FLAG 
     */ 	
	private java.lang.String blacklistFlag;
    /**
     * 证件类型       db_column: CERT_TYPE 
     */ 	
	private java.lang.String certType;
    /**
     * 证件号码       db_column: CERT_NO 
     */ 	
	private java.lang.String certNo;
    /**
     * 执行操作员       db_column: BLACK_OPER 
     */ 	
	private java.lang.String blackOper;
    /**
     * 最后一次更新时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 导入原因       db_column: BLACK_INPUT_REASON 
     */ 	
	private java.lang.String blackInputReason;
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
    /**
     * 认证类型 01：免密授权 02：普通会员 03：商户会员       db_column: APPROVE_TYPE 
     */ 	
	private java.lang.String approveType;
	//columns END


	
	
	public java.util.Date getBlacklistInputDate() {
		return this.blacklistInputDate;
	}
	
	public void setBlacklistInputDate(java.util.Date value) {
		this.blacklistInputDate = value;
	}
	
	
	public java.lang.String getBlacklistInputType() {
		return this.blacklistInputType;
	}
	
	public void setBlacklistInputType(java.lang.String value) {
		this.blacklistInputType = value;
	}
	
	
	public java.lang.String getBlacklistFlag() {
		return this.blacklistFlag;
	}
	
	public void setBlacklistFlag(java.lang.String value) {
		this.blacklistFlag = value;
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
	
	
	public java.lang.String getBlackOper() {
		return this.blackOper;
	}
	
	public void setBlackOper(java.lang.String value) {
		this.blackOper = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
	}
	
	
	public java.lang.String getBlackInputReason() {
		return this.blackInputReason;
	}
	
	public void setBlackInputReason(java.lang.String value) {
		this.blackInputReason = value;
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
	
	
	public java.lang.String getApproveType() {
		return this.approveType;
	}
	
	public void setApproveType(java.lang.String value) {
		this.approveType = value;
	}
	

	

}

