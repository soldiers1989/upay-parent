
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccLmtCountHisPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccLmtCountHis";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TRANS_DATE = "交易日期 YYYYMMDD";
	public static final String ALIAS_USER_ID = "账户";
	public static final String ALIAS_LMT_ACCOUNT_FLAG = "累计限额类型 1：账户系统限额";
	public static final String ALIAS_SYS_LMT_ID = "系统限额序号 见附录 当LMT_ACCOUNT_FLAG为1时";
	public static final String ALIAS_SYS_TRANSLMT_ID = "系统交易限额序号 见附录 当LMT_ACCOUNT_FLAG为2时";
	public static final String ALIAS_DAY_SUM_AMT_LIMIT = "已累计限额（日）";
	public static final String ALIAS_DAY_SUM_COUNT_LIMIT = "已累计笔数（日）";
	public static final String ALIAS_MON_SUM_AMT_LIMIT = "已累计限额（月）";
	public static final String ALIAS_MON_SUM_COUNT_LIMIT = "已累计笔数（月）";
	public static final String ALIAS_REMARK1 = "备注";
	public static final String ALIAS_YEAR_SUM_AMT_LIMIT = "已累计限额（年）";
	public static final String ALIAS_YEAR_SUM_COUNT_LIMIT = "已累计笔数（年）";
	

	//columns START
    /**
     * 交易日期 YYYYMMDD       db_column: TRANS_DATE 
     */ 	
	private java.util.Date transDate;
    /**
     * 账户       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 累计限额类型 1：账户系统限额       db_column: LMT_ACCOUNT_FLAG 
     */ 	
	private java.lang.String lmtAccountFlag;
    /**
     * 系统限额序号 见附录 当LMT_ACCOUNT_FLAG为1时       db_column: SYS_LMT_ID 
     */ 	
	private java.lang.String sysLmtId;
    /**
     * 系统交易限额序号 见附录 当LMT_ACCOUNT_FLAG为2时       db_column: SYS_TRANSLMT_ID 
     */ 	
	private java.lang.String sysTranslmtId;
    /**
     * 已累计限额（日）       db_column: DAY_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal daySumAmtLimit;
    /**
     * 已累计笔数（日）       db_column: DAY_SUM_COUNT_LIMIT 
     */ 	
	private java.lang.Integer daySumCountLimit;
    /**
     * 已累计限额（月）       db_column: MON_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal monSumAmtLimit;
    /**
     * 已累计笔数（月）       db_column: MON_SUM_COUNT_LIMIT 
     */ 	
	private java.lang.Integer monSumCountLimit;
    /**
     * 备注       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 已累计限额（年）       db_column: YEAR_SUM_AMT_LIMIT 
     */ 	
	private java.math.BigDecimal yearSumAmtLimit;
    /**
     * 已累计笔数（年）       db_column: YEAR_SUM_COUNT_LIMIT 
     */ 	
	private java.lang.Integer yearSumCountLimit;
	//columns END


	
	
	public java.util.Date getTransDate() {
		return this.transDate;
	}
	
	public void setTransDate(java.util.Date value) {
		this.transDate = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getLmtAccountFlag() {
		return this.lmtAccountFlag;
	}
	
	public void setLmtAccountFlag(java.lang.String value) {
		this.lmtAccountFlag = value;
	}
	
	
	public java.lang.String getSysLmtId() {
		return this.sysLmtId;
	}
	
	public void setSysLmtId(java.lang.String value) {
		this.sysLmtId = value;
	}
	
	
	public java.lang.String getSysTranslmtId() {
		return this.sysTranslmtId;
	}
	
	public void setSysTranslmtId(java.lang.String value) {
		this.sysTranslmtId = value;
	}
	
	
	public java.math.BigDecimal getDaySumAmtLimit() {
		return this.daySumAmtLimit;
	}
	
	public void setDaySumAmtLimit(java.math.BigDecimal value) {
		this.daySumAmtLimit = value;
	}
	
	
	public java.lang.Integer getDaySumCountLimit() {
		return this.daySumCountLimit;
	}
	
	public void setDaySumCountLimit(java.lang.Integer value) {
		this.daySumCountLimit = value;
	}
	
	
	public java.math.BigDecimal getMonSumAmtLimit() {
		return this.monSumAmtLimit;
	}
	
	public void setMonSumAmtLimit(java.math.BigDecimal value) {
		this.monSumAmtLimit = value;
	}
	
	
	public java.lang.Integer getMonSumCountLimit() {
		return this.monSumCountLimit;
	}
	
	public void setMonSumCountLimit(java.lang.Integer value) {
		this.monSumCountLimit = value;
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
	
	
	public java.lang.Integer getYearSumCountLimit() {
		return this.yearSumCountLimit;
	}
	
	public void setYearSumCountLimit(java.lang.Integer value) {
		this.yearSumCountLimit = value;
	}
	

	

}

