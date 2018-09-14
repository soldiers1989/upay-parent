
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeDetailPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeDetail";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TXN_DATE = "交易日期";
	public static final String ALIAS_TXN_TIME = "账号交易时间";
	public static final String ALIAS_ACCT_NO = "支付账号";
	public static final String ALIAS_CHNL_ID = "渠道编号";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_FEE_CODE = "收费代码";
	public static final String ALIAS_ASS_CODE = "分润代码 默认0000:表示不分润";
	public static final String ALIAS_TXN_AMT = "交易金额";
	public static final String ALIAS_FEE_AMT = "手续费金额";
	public static final String ALIAS_REMARK_CODE = "摘要代码";
	public static final String ALIAS_TXN_STAT = "交易状态，取订单状态";
	public static final String ALIAS_FREE_FLAG = "免收标志";
	public static final String ALIAS_GET_TYPE = "手续费收起方式  0：内扣 1：外扣";
	public static final String ALIAS_MEMO = "备用";
	public static final String ALIAS_ORDER_NO = "支付订单号";
	public static final String ALIAS_ACCT_TYPE = "账户类型";
	public static final String ALIAS_USER_ID = "用户ID";
	public static final String ALIAS_FEE_ID = "费用方法ID";
	

	//columns START
    /**
     * 交易日期       db_column: TXN_DATE 
     */ 	
	private java.util.Date txnDate;
    /**
     * 账号交易时间       db_column: TXN_TIME 
     */ 	
	private java.util.Date txnTime;
    /**
     * 支付账号       db_column: ACCT_NO 
     */ 	
	private java.lang.String acctNo;
    /**
     * 渠道编号       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 收费代码       db_column: FEE_CODE 
     */ 	
	private java.lang.String feeCode;
    /**
     * 分润代码 默认0000:表示不分润       db_column: ASS_CODE 
     */ 	
	private java.lang.String assCode;
    /**
     * 交易金额       db_column: TXN_AMT 
     */ 	
	private java.math.BigDecimal txnAmt;
    /**
     * 手续费金额       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 摘要代码       db_column: REMARK_CODE 
     */ 	
	private java.lang.String remarkCode;
    /**
     * 交易状态，取订单状态       db_column: TXN_STAT 
     */ 	
	private java.lang.String txnStat;
    /**
     * 免收标志       db_column: FREE_FLAG 
     */ 	
	private java.lang.String freeFlag;
    /**
     * 手续费收起方式  0：内扣 1：外扣       db_column: GET_TYPE 
     */ 	
	private java.lang.String getType;
    /**
     * 备用       db_column: MEMO 
     */ 	
	private java.lang.String memo;
    /**
     * 支付订单号       db_column: ORDER_NO 
     */ 	
	private java.lang.String orderNo;
    /**
     * 账户类型       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 用户ID       db_column: USER_ID 
     */ 	
	private java.lang.String userId;
    /**
     * 费用方法ID       db_column: FEE_ID 
     */ 	
	private java.lang.String feeId;
	//columns END


	
	
	public java.util.Date getTxnDate() {
		return this.txnDate;
	}
	
	public void setTxnDate(java.util.Date value) {
		this.txnDate = value;
	}
	
	
	public java.util.Date getTxnTime() {
		return this.txnTime;
	}
	
	public void setTxnTime(java.util.Date value) {
		this.txnTime = value;
	}
	
	
	public java.lang.String getAcctNo() {
		return this.acctNo;
	}
	
	public void setAcctNo(java.lang.String value) {
		this.acctNo = value;
	}
	
	
	public java.lang.String getChnlId() {
		return this.chnlId;
	}
	
	public void setChnlId(java.lang.String value) {
		this.chnlId = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getFeeCode() {
		return this.feeCode;
	}
	
	public void setFeeCode(java.lang.String value) {
		this.feeCode = value;
	}
	
	
	public java.lang.String getAssCode() {
		return this.assCode;
	}
	
	public void setAssCode(java.lang.String value) {
		this.assCode = value;
	}
	
	
	public java.math.BigDecimal getTxnAmt() {
		return this.txnAmt;
	}
	
	public void setTxnAmt(java.math.BigDecimal value) {
		this.txnAmt = value;
	}
	
	
	public java.math.BigDecimal getFeeAmt() {
		return this.feeAmt;
	}
	
	public void setFeeAmt(java.math.BigDecimal value) {
		this.feeAmt = value;
	}
	
	
	public java.lang.String getRemarkCode() {
		return this.remarkCode;
	}
	
	public void setRemarkCode(java.lang.String value) {
		this.remarkCode = value;
	}
	
	
	public java.lang.String getTxnStat() {
		return this.txnStat;
	}
	
	public void setTxnStat(java.lang.String value) {
		this.txnStat = value;
	}
	
	
	public java.lang.String getFreeFlag() {
		return this.freeFlag;
	}
	
	public void setFreeFlag(java.lang.String value) {
		this.freeFlag = value;
	}
	
	
	public java.lang.String getGetType() {
		return this.getType;
	}
	
	public void setGetType(java.lang.String value) {
		this.getType = value;
	}
	
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
	}
	
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	
	public java.lang.String getFeeId() {
		return this.feeId;
	}
	
	public void setFeeId(java.lang.String value) {
		this.feeId = value;
	}
	

	

}

