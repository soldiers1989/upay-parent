
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccVbookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccVbook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_USER_ID = "用户号";
	public static final String ALIAS_VACCT_NO = "账号 系统自动生成，唯一索引，见附录4.2，V_ACCT_NO_SEQ";
	public static final String ALIAS_VACCT_STAT = "账户状态 见附录4.3，ACCT_STAT";
	public static final String ALIAS_STOP_STAT = "止付状态 见附录4.3，ACCT_STAT";
	public static final String ALIAS_FRZ_STAT = "冻结状态 见附录4.3，ACCT_STAT";
	public static final String ALIAS_SET_FLAG = "账户设置标志 扩展用 默认全0 第1位：";
	public static final String ALIAS_ACCT_NAME = "账户户名 非实名为登录名或者注册手机,实名后为用户证件姓名,如果改名需要同步";
	public static final String ALIAS_ACCT_OTHER_NAME = "账户别名";
	public static final String ALIAS_CCY = "币种 默认CNY";
	public static final String ALIAS_FRZ_BAL = "冻结余额 部分冻结用";
	public static final String ALIAS_ACCT_BAL = "账户余额 账户的余额";
	public static final String ALIAS_LAST_BAL = "昨日账户余额 对应昨日acct_bal的余额";
	public static final String ALIAS_CUT_BAL = "日切点余额";
	public static final String ALIAS_EXT_TIME = "注销时间";
	public static final String ALIAS_LAST_CHG_TIME = "最新修改日期 昨日余额更新的时候修改";
	public static final String ALIAS_LAST_TIME = "最后修改时间";
	public static final String ALIAS_DAC = "DAC验证码";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 用户号       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 账号 系统自动生成，唯一索引，见附录4.2，V_ACCT_NO_SEQ       db_column: V_ACCT_NO 
     */ 	
	private java.lang.String vacctNo;
    /**
     * 账户状态 见附录4.3，ACCT_STAT       db_column: V_ACCT_STAT 
     */ 	
	private java.lang.String vacctStat;
    /**
     * 止付状态 见附录4.3，ACCT_STAT       db_column: STOP_STAT 
     */ 	
	private java.lang.String stopStat;
    /**
     * 冻结状态 见附录4.3，ACCT_STAT       db_column: FRZ_STAT 
     */ 	
	private java.lang.String frzStat;
    /**
     * 账户设置标志 扩展用 默认全0 第1位：       db_column: SET_FLAG 
     */ 	
	private java.lang.String setFlag;
    /**
     * 账户户名 非实名为登录名或者注册手机,实名后为用户证件姓名,如果改名需要同步       db_column: ACCT_NAME 
     */ 	
	private java.lang.String acctName;
    /**
     * 账户别名       db_column: ACCT_OTHER_NAME 
     */ 	
	private java.lang.String acctOtherName;
    /**
     * 币种 默认CNY       db_column: CCY 
     */ 	
	private java.lang.String ccy;
    /**
     * 冻结余额 部分冻结用       db_column: FRZ_BAL 
     */ 	
	private java.math.BigDecimal frzBal;
    /**
     * 账户余额 账户的余额       db_column: ACCT_BAL 
     */ 	
	private java.math.BigDecimal acctBal;
    /**
     * 昨日账户余额 对应昨日acct_bal的余额       db_column: LAST_BAL 
     */ 	
	private java.math.BigDecimal lastBal;
    /**
     * 日切点余额       db_column: CUT_BAL 
     */ 	
	private java.math.BigDecimal cutBal;
    /**
     * 注销时间       db_column: EXT_TIME 
     */ 	
	private java.util.Date extTime;
    /**
     * 最新修改日期 昨日余额更新的时候修改       db_column: LAST_CHG_TIME 
     */ 	
	private java.util.Date lastChgTime;
    /**
     * 最后修改时间       db_column: LAST_TIME 
     */ 	
	private java.util.Date lastTime;
    /**
     * DAC验证码       db_column: DAC 
     */ 	
	private java.lang.String dac;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
	//columns END


	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getVacctNo() {
		return this.vacctNo;
	}
	
	public void setVacctNo(java.lang.String value) {
		this.vacctNo = value;
	}
	
	
	public java.lang.String getVacctStat() {
		return this.vacctStat;
	}
	
	public void setVacctStat(java.lang.String value) {
		this.vacctStat = value;
	}
	
	
	public java.lang.String getStopStat() {
		return this.stopStat;
	}
	
	public void setStopStat(java.lang.String value) {
		this.stopStat = value;
	}
	
	
	public java.lang.String getFrzStat() {
		return this.frzStat;
	}
	
	public void setFrzStat(java.lang.String value) {
		this.frzStat = value;
	}
	
	
	public java.lang.String getSetFlag() {
		return this.setFlag;
	}
	
	public void setSetFlag(java.lang.String value) {
		this.setFlag = value;
	}
	
	
	public java.lang.String getAcctName() {
		return this.acctName;
	}
	
	public void setAcctName(java.lang.String value) {
		this.acctName = value;
	}
	
	
	public java.lang.String getAcctOtherName() {
		return this.acctOtherName;
	}
	
	public void setAcctOtherName(java.lang.String value) {
		this.acctOtherName = value;
	}
	
	
	public java.lang.String getCcy() {
		return this.ccy;
	}
	
	public void setCcy(java.lang.String value) {
		this.ccy = value;
	}
	
	
	public java.math.BigDecimal getFrzBal() {
		return this.frzBal;
	}
	
	public void setFrzBal(java.math.BigDecimal value) {
		this.frzBal = value;
	}
	
	
	public java.math.BigDecimal getAcctBal() {
		return this.acctBal;
	}
	
	public void setAcctBal(java.math.BigDecimal value) {
		this.acctBal = value;
	}
	
	
	public java.math.BigDecimal getLastBal() {
		return this.lastBal;
	}
	
	public void setLastBal(java.math.BigDecimal value) {
		this.lastBal = value;
	}
	
	
	public java.math.BigDecimal getCutBal() {
		return this.cutBal;
	}
	
	public void setCutBal(java.math.BigDecimal value) {
		this.cutBal = value;
	}
	
	
	public java.util.Date getExtTime() {
		return this.extTime;
	}
	
	public void setExtTime(java.util.Date value) {
		this.extTime = value;
	}
	
	
	public java.util.Date getLastChgTime() {
		return this.lastChgTime;
	}
	
	public void setLastChgTime(java.util.Date value) {
		this.lastChgTime = value;
	}
	
	
	public java.util.Date getLastTime() {
		return this.lastTime;
	}
	
	public void setLastTime(java.util.Date value) {
		this.lastTime = value;
	}
	
	
	public java.lang.String getDac() {
		return this.dac;
	}
	
	public void setDac(java.lang.String value) {
		this.dac = value;
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

