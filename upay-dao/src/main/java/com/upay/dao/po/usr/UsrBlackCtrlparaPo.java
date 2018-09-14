
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrBlackCtrlparaPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrBlackCtrlpara";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BLACKLIST_TYPE = "黑名单类型代码";
	public static final String ALIAS_BLACKLIST_NAME = "黑名单类型名称";
	public static final String ALIAS_TRANS_CODE = "禁止操作交易代码";
	public static final String ALIAS_LAST_UPDATE_OPER = "最后更新操作员";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后更新时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 黑名单类型代码       db_column: BLACKLIST_TYPE 
     */ 	
	private java.lang.String blacklistType;
    /**
     * 黑名单类型名称       db_column: BLACKLIST_NAME 
     */ 	
	private java.lang.String blacklistName;
    /**
     * 禁止操作交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
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
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 备用3       db_column: REMARK3 
     */ 	
	private java.lang.String remark3;
	//columns END


	
	
	public java.lang.String getBlacklistType() {
		return this.blacklistType;
	}
	
	public void setBlacklistType(java.lang.String value) {
		this.blacklistType = value;
	}
	
	
	public java.lang.String getBlacklistName() {
		return this.blacklistName;
	}
	
	public void setBlacklistName(java.lang.String value) {
		this.blacklistName = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
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

