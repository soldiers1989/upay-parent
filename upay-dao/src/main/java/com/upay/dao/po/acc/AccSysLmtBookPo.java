
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccSysLmtBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccSysLmtBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_SYS_LMT_ID = "系统限额序号 见附录";
	public static final String ALIAS_ACCT_TYPE = "账户类型 见附录";
	public static final String ALIAS_TRANS_CODE = "交易代码 与借贷标志互斥";
	public static final String ALIAS_LMT_TYPE = "限额类型 见附录";
	public static final String ALIAS_CHNL_ID = "渠道代码 见附录";
	public static final String ALIAS_USER_LEVEL = "用户认证等级 见用户模块";
	public static final String ALIAS_DC_FLAG = "账户借贷标志 见附录 与交易代码互斥";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_SINGLE_AMT_LIMIT = "单笔限额";
	public static final String ALIAS_DAY_SUM_AMT_LIMIT = "日累计限额";
	public static final String ALIAS_MON_SUM_AMT_LIMIT = "月累计限额";
	public static final String ALIAS_DAY_SUM_TIMES_LIMIT = "日累计限额次数";
	public static final String ALIAS_MON_SUM_TIMES_LIMIT = "月累计限额次数";
	public static final String ALIAS_BEG_DATE = "生效日期";
	public static final String ALIAS_END_DATE = "失效日期";
	public static final String ALIAS_LMT_STAT = "状态 1：启用 0：停用";
	public static final String ALIAS_STOP_DATE = "停用时间";
	public static final String ALIAS_LAST_UPDATE_OPER = "最后更新操作员";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后更新时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_YEAR_SUM_AMT_LIMIT = "年累计限额";
	public static final String ALIAS_YEAR_SUM_TIMES_LIMIT = "年累计限额次数";
	public static final String ALIAS_USER_TYPE = "用户类型";
	

	//columns START
    /**
     * 系统限额序号 见附录       db_column: SYS_LMT_ID 
     */ 	
	private java.lang.String sysLmtId;
    /**
     * 账户类型 见附录       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 交易代码 与借贷标志互斥       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 限额类型 见附录       db_column: LMT_TYPE 
     */ 	
	private java.lang.String lmtType;
    /**
     * 渠道代码 见附录       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 用户认证等级 见用户模块       db_column: USER_LEVEL 
     */ 	
	private java.lang.String userLevel;
    /**
     * 账户借贷标志 见附录 与交易代码互斥       db_column: DC_FLAG 
     */ 	
	private java.lang.String dcFlag;
    /**
     * 创建时间       db_column: CREATE_TIME 
     */ 	
	private java.util.Date createTime;
    /**
     * 单笔限额       db_column: SINGLE_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal singleAmtLimit;
    /**
     * 日累计限额       db_column: DAY_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal daySumAmtLimit;
    /**
     * 月累计限额       db_column: MON_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal monSumAmtLimit;
    /**
     * 日累计限额次数       db_column: DAY_SUM_TIMES_LIMIT 
     */ 	
	private java.lang.Integer daySumTimesLimit;
    /**
     * 月累计限额次数       db_column: MON_SUM_TIMES_LIMIT 
     */ 	
	private java.lang.Integer monSumTimesLimit;
    /**
     * 生效日期       db_column: BEG_DATE 
     */ 	
	private java.util.Date begDate;
    /**
     * 失效日期       db_column: END_DATE 
     */ 	
	private java.util.Date endDate;
    /**
     * 状态 1：启用 0：停用       db_column: LMT_STAT 
     */ 	
	private java.lang.String lmtStat;
    /**
     * 停用时间       db_column: STOP_DATE 
     */ 	
	private java.util.Date stopDate;
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
     * 年累计限额       db_column: YEAR_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal yearSumAmtLimit;
    /**
     * 年累计限额次数       db_column: YEAR_SUM_TIMES_LIMIT 
     */ 	
	private java.lang.Integer yearSumTimesLimit;
	/**
	 * 用户类型
	 */
	private java.lang.String userType;
	//columns END


	
	
	public java.lang.String getSysLmtId() {
		return this.sysLmtId;
	}
	
	public java.lang.String getUserType() {
        return userType;
    }

    public void setUserType(java.lang.String userType) {
        this.userType = userType;
    }

    public void setSysLmtId(java.lang.String value) {
		this.sysLmtId = value;
	}
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getLmtType() {
		return this.lmtType;
	}
	
	public void setLmtType(java.lang.String value) {
		this.lmtType = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getUserLevel() {
		return this.userLevel;
	}
	
	public void setUserLevel(java.lang.String value) {
		this.userLevel = value;
	}
	
	
	public java.lang.String getDcFlag() {
		return this.dcFlag;
	}
	
	public void setDcFlag(java.lang.String value) {
		this.dcFlag = value;
	}
	
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	
	public java.math.BigDecimal getSingleAmtLimit() {
		return this.singleAmtLimit;
	}
	
	public void setSingleAmtLimit(java.math.BigDecimal value) {
		this.singleAmtLimit = value;
	}
	
	
	public java.math.BigDecimal getDaySumAmtLimit() {
		return this.daySumAmtLimit;
	}
	
	public void setDaySumAmtLimit(java.math.BigDecimal value) {
		this.daySumAmtLimit = value;
	}
	
	
	public java.math.BigDecimal getMonSumAmtLimit() {
		return this.monSumAmtLimit;
	}
	
	public void setMonSumAmtLimit(java.math.BigDecimal value) {
		this.monSumAmtLimit = value;
	}
	
	
	public java.lang.Integer getDaySumTimesLimit() {
		return this.daySumTimesLimit;
	}
	
	public void setDaySumTimesLimit(java.lang.Integer value) {
		this.daySumTimesLimit = value;
	}
	
	
	public java.lang.Integer getMonSumTimesLimit() {
		return this.monSumTimesLimit;
	}
	
	public void setMonSumTimesLimit(java.lang.Integer value) {
		this.monSumTimesLimit = value;
	}
	
	
	public java.util.Date getBegDate() {
		return this.begDate;
	}
	
	public void setBegDate(java.util.Date value) {
		this.begDate = value;
	}
	
	
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	public void setEndDate(java.util.Date value) {
		this.endDate = value;
	}
	
	
	public java.lang.String getLmtStat() {
		return this.lmtStat;
	}
	
	public void setLmtStat(java.lang.String value) {
		this.lmtStat = value;
	}
	
	
	public java.util.Date getStopDate() {
		return this.stopDate;
	}
	
	public void setStopDate(java.util.Date value) {
		this.stopDate = value;
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
	
	
	public java.math.BigDecimal getYearSumAmtLimit() {
		return this.yearSumAmtLimit;
	}
	
	public void setYearSumAmtLimit(java.math.BigDecimal value) {
		this.yearSumAmtLimit = value;
	}
	
	
	public java.lang.Integer getYearSumTimesLimit() {
		return this.yearSumTimesLimit;
	}
	
	public void setYearSumTimesLimit(java.lang.Integer value) {
		this.yearSumTimesLimit = value;
	}
	

	

}

