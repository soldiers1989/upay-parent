
package com.upay.dao.po.acc;
import com.pactera.dipper.po.BasePo;

public class AccStpBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "AccStpBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_STP_TIME = "止付时间";
	public static final String ALIAS_CHNL_ID = "止付渠道，预留，用户从渠道端自主停用";
	public static final String ALIAS_STP_NO = "止付编号";
	public static final String ALIAS_OPER_TYPE = "操作员类型 0：用户 1：内管";
	public static final String ALIAS_USER_ID = "用户号";
	public static final String ALIAS_ACCT_TYPE = "账户类型";
	public static final String ALIAS_ACCT_NO = "止付账号";
	public static final String ALIAS_STP_STAT = "止付状态";
	public static final String ALIAS_STP_MODE = "止付方式 1：临时止付 2：非临时止付";
	public static final String ALIAS_PART_STP_TIME = "临时止付时间(小时)，当STP_MODE为2时";
	public static final String ALIAS_STP_AMT = "止付金额，当STP_TYPE为3时";
	public static final String ALIAS_PAY_AMT = "解付金额";
	public static final String ALIAS_FRZ_REASON = "止付原因";
	public static final String ALIAS_UNSTP_TIME = "解付时间";
	public static final String ALIAS_UNSTP_REASON = "解付原因";
	public static final String ALIAS_STP_OPER = "止付操作员";
	public static final String ALIAS_UNSTP_OPER = "解付操作员";
	public static final String ALIAS_REMARK1 = "备用";
	

	//columns START
    /**
     * 止付时间       db_column: STP_TIME 
     */ 	
	private java.util.Date stpTime;
    /**
     * 止付渠道，预留，用户从渠道端自主停用       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 止付编号       db_column: STP_NO 
     */ 	
	private java.lang.String stpNo;
    /**
     * 操作员类型 0：用户 1：内管       db_column: OPER_TYPE 
     */ 	
	private java.lang.String operType;
    /**
     * 用户号       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 账户类型       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 止付账号       db_column: ACCT_NO 
     */ 	
	private java.lang.String acctNo;
    /**
     * 止付状态       db_column: STP_STAT 
     */ 	
	private java.lang.String stpStat;
    /**
     * 止付方式 1：临时止付 2：非临时止付       db_column: STP_MODE 
     */ 	
	private java.lang.String stpMode;
    /**
     * 临时止付时间(小时)，当STP_MODE为2时       db_column: PART_STP_TIME 
     */ 	
	private java.lang.Integer partStpTime;
    /**
     * 止付金额，当STP_TYPE为3时       db_column: STP_AMT 
     */ 	
	private java.math.BigDecimal stpAmt;
    /**
     * 解付金额       db_column: PAY_AMT 
     */ 	
	private java.math.BigDecimal payAmt;
    /**
     * 止付原因       db_column: FRZ_REASON 
     */ 	
	private java.lang.String frzReason;
    /**
     * 解付时间       db_column: UNSTP_TIME 
     */ 	
	private java.util.Date unstpTime;
    /**
     * 解付原因       db_column: UNSTP_REASON 
     */ 	
	private java.lang.String unstpReason;
    /**
     * 止付操作员       db_column: STP_OPER 
     */ 	
	private java.lang.String stpOper;
    /**
     * 解付操作员       db_column: UNSTP_OPER 
     */ 	
	private java.lang.String unstpOper;
    /**
     * 备用       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
	//columns END


	
	
	public java.util.Date getStpTime() {
		return this.stpTime;
	}
	
	public void setStpTime(java.util.Date value) {
		this.stpTime = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getStpNo() {
		return this.stpNo;
	}
	
	public void setStpNo(java.lang.String value) {
		this.stpNo = value;
	}
	
	
	public java.lang.String getOperType() {
		return this.operType;
	}
	
	public void setOperType(java.lang.String value) {
		this.operType = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	
	
	public java.lang.String getAcctNo() {
		return this.acctNo;
	}
	
	public void setAcctNo(java.lang.String value) {
		this.acctNo = value;
	}
	
	
	public java.lang.String getStpStat() {
		return this.stpStat;
	}
	
	public void setStpStat(java.lang.String value) {
		this.stpStat = value;
	}
	
	
	public java.lang.String getStpMode() {
		return this.stpMode;
	}
	
	public void setStpMode(java.lang.String value) {
		this.stpMode = value;
	}
	
	
	public java.lang.Integer getPartStpTime() {
		return this.partStpTime;
	}
	
	public void setPartStpTime(java.lang.Integer value) {
		this.partStpTime = value;
	}
	
	
	public java.math.BigDecimal getStpAmt() {
		return this.stpAmt;
	}
	
	public void setStpAmt(java.math.BigDecimal value) {
		this.stpAmt = value;
	}
	
	
	public java.math.BigDecimal getPayAmt() {
		return this.payAmt;
	}
	
	public void setPayAmt(java.math.BigDecimal value) {
		this.payAmt = value;
	}
	
	
	public java.lang.String getFrzReason() {
		return this.frzReason;
	}
	
	public void setFrzReason(java.lang.String value) {
		this.frzReason = value;
	}
	
	
	public java.util.Date getUnstpTime() {
		return this.unstpTime;
	}
	
	public void setUnstpTime(java.util.Date value) {
		this.unstpTime = value;
	}
	
	
	public java.lang.String getUnstpReason() {
		return this.unstpReason;
	}
	
	public void setUnstpReason(java.lang.String value) {
		this.unstpReason = value;
	}
	
	
	public java.lang.String getStpOper() {
		return this.stpOper;
	}
	
	public void setStpOper(java.lang.String value) {
		this.stpOper = value;
	}
	
	
	public java.lang.String getUnstpOper() {
		return this.unstpOper;
	}
	
	public void setUnstpOper(java.lang.String value) {
		this.unstpOper = value;
	}
	
	
	public java.lang.String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(java.lang.String value) {
		this.remark1 = value;
	}
	

	

}

