
package com.upay.dao.po.usr;
import com.pactera.dipper.po.BasePo;

public class UsrPwdListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "UsrPwdList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_COUNT_DATE = "统计日期";
	public static final String ALIAS_LOG_DAY_ERR = "当日登录密码连续错误次数 密码连续错误次数超过系统设置的参数，则需要密码锁定，次日才能登录";
	public static final String ALIAS_LOG_TOT_ERR = "登录密码连续累计错误总次数 当日密码连续错误锁定N次后，长期锁定";
	public static final String ALIAS_LAST_LOGPWD_ERRTIME = "最后一次登录密码错误时间";
	public static final String ALIAS_TRADE_DAY_ERR = "当日交易密码连续错误次数 密码连续错误次数超过系统设置的参数，则需要密码锁定，隔日再用";
	public static final String ALIAS_TRADE_TOT_ERR = "交易密码连续累计错误总次数 当日密码连续错误锁定N次后，长期锁定";
	public static final String ALIAS_LAST_TRADEPWD_ERRTIME = "最后一次交易密码错误时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	

	//columns START
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 统计日期       db_column: COUNT_DATE 
     */ 	
	private java.util.Date countDate;
    /**
     * 当日登录密码连续错误次数 密码连续错误次数超过系统设置的参数，则需要密码锁定，次日才能登录       db_column: LOG_DAY_ERR 
     */ 	
	private java.lang.Integer logDayErr;
    /**
     * 登录密码连续累计错误总次数 当日密码连续错误锁定N次后，长期锁定       db_column: LOG_TOT_ERR 
     */ 	
	private java.lang.Integer logTotErr;
    /**
     * 最后一次登录密码错误时间       db_column: LAST_LOGPWD_ERRTIME 
     */ 	
	private java.util.Date lastLogpwdErrtime;
    /**
     * 当日交易密码连续错误次数 密码连续错误次数超过系统设置的参数，则需要密码锁定，隔日再用       db_column: TRADE_DAY_ERR 
     */ 	
	private java.lang.Integer tradeDayErr;
    /**
     * 交易密码连续累计错误总次数 当日密码连续错误锁定N次后，长期锁定       db_column: TRADE_TOT_ERR 
     */ 	
	private java.lang.Integer tradeTotErr;
    /**
     * 最后一次交易密码错误时间       db_column: LAST_TRADEPWD_ERRTIME 
     */ 	
	private java.util.Date lastTradepwdErrtime;
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


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.util.Date getCountDate() {
		return this.countDate;
	}
	
	public void setCountDate(java.util.Date value) {
		this.countDate = value;
	}
	
	
	public java.lang.Integer getLogDayErr() {
		return this.logDayErr;
	}
	
	public void setLogDayErr(java.lang.Integer value) {
		this.logDayErr = value;
	}
	
	
	public java.lang.Integer getLogTotErr() {
		return this.logTotErr;
	}
	
	public void setLogTotErr(java.lang.Integer value) {
		this.logTotErr = value;
	}
	
	
	public java.util.Date getLastLogpwdErrtime() {
		return this.lastLogpwdErrtime;
	}
	
	public void setLastLogpwdErrtime(java.util.Date value) {
		this.lastLogpwdErrtime = value;
	}
	
	
	public java.lang.Integer getTradeDayErr() {
		return this.tradeDayErr;
	}
	
	public void setTradeDayErr(java.lang.Integer value) {
		this.tradeDayErr = value;
	}
	
	
	public java.lang.Integer getTradeTotErr() {
		return this.tradeTotErr;
	}
	
	public void setTradeTotErr(java.lang.Integer value) {
		this.tradeTotErr = value;
	}
	
	
	public java.util.Date getLastTradepwdErrtime() {
		return this.lastTradepwdErrtime;
	}
	
	public void setLastTradepwdErrtime(java.util.Date value) {
		this.lastTradepwdErrtime = value;
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

