
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrPwdlockBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrPwdlockBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_LOCK_TIME = "锁定时间";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_LOCK_FLAG = "锁定标志 1：锁定 2：解锁";
	public static final String ALIAS_LOCK_FUNCTION = "锁定功能 1：登录密码锁定 2：交易密码锁定 3：手势密码锁定";
	public static final String ALIAS_LOCK_WAY = "锁定方式 1：日连续错误锁定 2：累计连续错误锁定";
	public static final String ALIAS_LOCK_REASON = "锁定原因 当LOCK_SOURCE为2时";
	public static final String ALIAS_LOCK_OPER = "锁定操作员 当LOCK_SOURCE为2时";
	public static final String ALIAS_LOCK_MODE = "锁定时效 1：临时锁定 2：非临时锁定";
	public static final String ALIAS_PART_LOCK_HOUR = "临时锁定持续时间(小时) 当LOCK_FLAG为1时 且当LOCK_MODE为1时";
	public static final String ALIAS_UNLOCK_TIME = "解锁时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_UNLOCK_OPER = "解锁操作员";
	public static final String ALIAS_UNLOCK_REASON = "解锁原因";
	

	//columns START
    /**
     * 锁定时间       db_column: LOCK_TIME 
     */ 	
	private java.util.Date lockTime;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 锁定标志 1：锁定 2：解锁       db_column: LOCK_FLAG 
     */ 	
	private java.lang.String lockFlag;
    /**
     * 锁定功能 1：登录密码锁定 2：交易密码锁定 3：手势密码锁定       db_column: LOCK_FUNCTION 
     */ 	
	private java.lang.String lockFunction;
    /**
     * 锁定方式 1：日连续错误锁定 2：累计连续错误锁定       db_column: LOCK_WAY 
     */ 	
	private java.lang.String lockWay;
    /**
     * 锁定原因 当LOCK_SOURCE为2时       db_column: LOCK_REASON 
     */ 	
	private java.lang.String lockReason;
    /**
     * 锁定操作员 当LOCK_SOURCE为2时       db_column: LOCK_OPER 
     */ 	
	private java.lang.String lockOper;
    /**
     * 锁定时效 1：临时锁定 2：非临时锁定       db_column: LOCK_MODE 
     */ 	
	private java.lang.String lockMode;
    /**
     * 临时锁定持续时间(小时) 当LOCK_FLAG为1时 且当LOCK_MODE为1时       db_column: PART_LOCK_HOUR 
     */ 	
	private java.lang.Integer partLockHour;
    /**
     * 解锁时间       db_column: UNLOCK_TIME 
     */ 	
	private java.util.Date unlockTime;
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
     * 解锁操作员       db_column: UNLOCK_OPER 
     */ 	
	private java.lang.String unlockOper;
    /**
     * 解锁原因       db_column: UNLOCK_REASON 
     */ 	
	private java.lang.String unlockReason;
	//columns END


	
	
	public java.util.Date getLockTime() {
		return this.lockTime;
	}
	
	public void setLockTime(java.util.Date value) {
		this.lockTime = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getLockFlag() {
		return this.lockFlag;
	}
	
	public void setLockFlag(java.lang.String value) {
		this.lockFlag = value;
	}
	
	
	public java.lang.String getLockFunction() {
		return this.lockFunction;
	}
	
	public void setLockFunction(java.lang.String value) {
		this.lockFunction = value;
	}
	
	
	public java.lang.String getLockWay() {
		return this.lockWay;
	}
	
	public void setLockWay(java.lang.String value) {
		this.lockWay = value;
	}
	
	
	public java.lang.String getLockReason() {
		return this.lockReason;
	}
	
	public void setLockReason(java.lang.String value) {
		this.lockReason = value;
	}
	
	
	public java.lang.String getLockOper() {
		return this.lockOper;
	}
	
	public void setLockOper(java.lang.String value) {
		this.lockOper = value;
	}
	
	
	public java.lang.String getLockMode() {
		return this.lockMode;
	}
	
	public void setLockMode(java.lang.String value) {
		this.lockMode = value;
	}
	
	
	public java.lang.Integer getPartLockHour() {
		return this.partLockHour;
	}
	
	public void setPartLockHour(java.lang.Integer value) {
		this.partLockHour = value;
	}
	
	
	public java.util.Date getUnlockTime() {
		return this.unlockTime;
	}
	
	public void setUnlockTime(java.util.Date value) {
		this.unlockTime = value;
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
	
	
	public java.lang.String getUnlockOper() {
		return this.unlockOper;
	}
	
	public void setUnlockOper(java.lang.String value) {
		this.unlockOper = value;
	}
	
	
	public java.lang.String getUnlockReason() {
		return this.unlockReason;
	}
	
	public void setUnlockReason(java.lang.String value) {
		this.unlockReason = value;
	}
	

	

}

