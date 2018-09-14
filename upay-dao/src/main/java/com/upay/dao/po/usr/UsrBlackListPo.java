
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrBlackListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrBlackList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_BLACKLIST_CHG_TIME = "异动时间";
	public static final String ALIAS_BLACKLIST_CHG_TYPE = "异动处理方式， 1：手工 2：批量导入";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_BLACKLIST_FLAG = "黑名单异动标志， 1：正常转黑名单 2：黑名单转正常";
	public static final String ALIAS_BLACKLIST_TYPE = "黑名单类型代码，当BLACK_FLAG为1时";
	public static final String ALIAS_BLACK_OPER = "执行操作员";
	public static final String ALIAS_BLACK_CHG_REASON = "异动原因";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 异动时间       db_column: BLACKLIST_CHG_TIME 
     */ 	
	private java.util.Date blacklistChgTime;
    /**
     * 异动处理方式， 1：手工 2：批量导入       db_column: BLACKLIST_CHG_TYPE 
     */ 	
	private java.lang.String blacklistChgType;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 黑名单异动标志， 1：正常转黑名单 2：黑名单转正常       db_column: BLACKLIST_FLAG 
     */ 	
	private java.lang.String blacklistFlag;
    /**
     * 黑名单类型代码，当BLACK_FLAG为1时       db_column: BLACKLIST_TYPE 
     */ 	
	private java.lang.String blacklistType;
    /**
     * 执行操作员       db_column: BLACK_OPER 
     */ 	
	private java.lang.String blackOper;
    /**
     * 异动原因       db_column: BLACK_CHG_REASON 
     */ 	
	private java.lang.String blackChgReason;
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


	
	
	public java.util.Date getBlacklistChgTime() {
		return this.blacklistChgTime;
	}
	
	public void setBlacklistChgTime(java.util.Date value) {
		this.blacklistChgTime = value;
	}
	
	
	public java.lang.String getBlacklistChgType() {
		return this.blacklistChgType;
	}
	
	public void setBlacklistChgType(java.lang.String value) {
		this.blacklistChgType = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getBlacklistFlag() {
		return this.blacklistFlag;
	}
	
	public void setBlacklistFlag(java.lang.String value) {
		this.blacklistFlag = value;
	}
	
	
	public java.lang.String getBlacklistType() {
		return this.blacklistType;
	}
	
	public void setBlacklistType(java.lang.String value) {
		this.blacklistType = value;
	}
	
	
	public java.lang.String getBlackOper() {
		return this.blackOper;
	}
	
	public void setBlackOper(java.lang.String value) {
		this.blackOper = value;
	}
	
	
	public java.lang.String getBlackChgReason() {
		return this.blackChgReason;
	}
	
	public void setBlackChgReason(java.lang.String value) {
		this.blackChgReason = value;
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

