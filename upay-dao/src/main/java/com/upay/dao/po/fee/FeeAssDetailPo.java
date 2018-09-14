
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeAssDetailPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "FeeAssDetail";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TXN_DATE = "交易日期";
	public static final String ALIAS_TXN_TIME = "交易时间";
	public static final String ALIAS_ACCT_NO = "账号";
	public static final String ALIAS_ACCT_TYPE = "账户类型";
	public static final String ALIAS_CHNL_ID = "渠道编号";
	public static final String ALIAS_TRANS_CODE = "交易代码";
	public static final String ALIAS_ASS_CODE = "分润代码";
	public static final String ALIAS_ASS_ID = "001：统一支付平台 002：资金通道 003：银行机构 004：合作商户";
	public static final String ALIAS_ORG_CODE = "分润方代码";
	public static final String ALIAS_ASS_RATE = "分润比率";
	public static final String ALIAS_ASS_AMT = "分润金额";
	public static final String ALIAS_DC_FLAG = "借贷标志  D：借（应付） C：贷（应收）";
	public static final String ALIAS_REMARK_CODE = "摘要代码";
	public static final String ALIAS_TXN_STAT = "交易状态，取订单状态";
	public static final String ALIAS_MEMO = "备用";
	public static final String ALIAS_ORDER_NO = "订单号";
	public static final String ALIAS_STL_BATCH_NO = "stlBatchNo";
	public static final String ALIAS_STL_DATE = "stlDate";
	public static final String ALIAS_STL_FLAG = "stlFlag";
	public static final String ALIAS_ROUTE_CODE = "routeCode";
	

	//columns START
    /**
     * 交易日期       db_column: TXN_DATE 
     */ 	
	private java.util.Date txnDate;
    /**
     * 交易时间       db_column: TXN_TIME 
     */ 	
	private java.util.Date txnTime;
    /**
     * 账号       db_column: ACCT_NO 
     */ 	
	private java.lang.String acctNo;
    /**
     * 账户类型       db_column: ACCT_TYPE 
     */ 	
	private java.lang.String acctType;
    /**
     * 渠道编号       db_column: CHNL_ID 
     */ 	
	private java.lang.String chnlId;
    /**
     * 交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 分润代码       db_column: ASS_CODE 
     */ 	
	private java.lang.String assCode;
    /**
     * 001：统一支付平台 002：资金通道 003：银行机构 004：合作商户       db_column: ASS_ID 
     */ 	
	private java.lang.String assId;
    /**
     * 分润方代码       db_column: ORG_CODE 
     */ 	
	private java.lang.String orgCode;
    /**
     * 分润比率       db_column: ASS_RATE 
     */ 	
	private java.math.BigDecimal assRate;
    /**
     * 分润金额       db_column: ASS_AMT 
     */ 	
	private java.math.BigDecimal assAmt;
    /**
     * 借贷标志  D：借（应付） C：贷（应收）       db_column: DC_FLAG 
     */ 	
	private java.lang.String dcFlag;
    /**
     * 摘要代码       db_column: REMARK_CODE 
     */ 	
	private java.lang.String remarkCode;
    /**
     * 交易状态，取订单状态       db_column: TXN_STAT 
     */ 	
	private java.lang.String txnStat;
    /**
     * 备用       db_column: MEMO 
     */ 	
	private java.lang.String memo;
    /**
     * 订单号       db_column: ORDER_NO 
     */ 	
	private java.lang.String orderNo;
    /**
     * stlBatchNo       db_column: STL_BATCH_NO 
     */ 	
	private java.lang.String stlBatchNo;
    /**
     * stlDate       db_column: STL_DATE 
     */ 	
	private java.util.Date stlDate;
    /**
     * stlFlag       db_column: STL_FLAG 
     */ 	
	private java.lang.String stlFlag;
    /**
     * routeCode       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
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
	
	
	public java.lang.String getAcctType() {
		return this.acctType;
	}
	
	public void setAcctType(java.lang.String value) {
		this.acctType = value;
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
	
	
	public java.lang.String getAssCode() {
		return this.assCode;
	}
	
	public void setAssCode(java.lang.String value) {
		this.assCode = value;
	}
	
	
	public java.lang.String getAssId() {
		return this.assId;
	}
	
	public void setAssId(java.lang.String value) {
		this.assId = value;
	}
	
	
	public java.lang.String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(java.lang.String value) {
		this.orgCode = value;
	}
	
	
	public java.math.BigDecimal getAssRate() {
		return this.assRate;
	}
	
	public void setAssRate(java.math.BigDecimal value) {
		this.assRate = value;
	}
	
	
	public java.math.BigDecimal getAssAmt() {
		return this.assAmt;
	}
	
	public void setAssAmt(java.math.BigDecimal value) {
		this.assAmt = value;
	}
	
	
	public java.lang.String getDcFlag() {
		return this.dcFlag;
	}
	
	public void setDcFlag(java.lang.String value) {
		this.dcFlag = value;
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
	
	
	public java.lang.String getStlBatchNo() {
		return this.stlBatchNo;
	}
	
	public void setStlBatchNo(java.lang.String value) {
		this.stlBatchNo = value;
	}
	
	
	public java.util.Date getStlDate() {
		return this.stlDate;
	}
	
	public void setStlDate(java.util.Date value) {
		this.stlDate = value;
	}
	
	
	public java.lang.String getStlFlag() {
		return this.stlFlag;
	}
	
	public void setStlFlag(java.lang.String value) {
		this.stlFlag = value;
	}
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	

	

}

