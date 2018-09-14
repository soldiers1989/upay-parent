
package com.upay.dao.po.fee;
import com.pactera.dipper.po.BasePo;

public class FeeStatisticsPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "手续费收入支出详细表";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ROUTE_CODE = "交易渠道 0001核心 0002 银联 0003微信  0004 中金 0005支付宝  (为空的情况下，记录我行收商户的手续费汇总)";
	public static final String ALIAS_FEE_TYPE = "0 手续费收入 1手续费支出";
	public static final String ALIAS_STATISTICS_TIME = "统计时间";
	public static final String ALIAS_TXN_TIME = "交易时间";
	public static final String ALIAS_MER_ID = "商户代码";
	public static final String ALIAS_ISS_CODE = "机构代码";
	public static final String ALIAS_PAYER_ACCT_TYPE = "付款方账户类型";
	public static final String ALIAS_PAYER_ACCT_NO = "手续费付款方账号";
	public static final String ALIAS_PAYER_ACCT_NAME = "手续费付款方名称";
	public static final String ALIAS_PAYEE_ACCT_NO = "手续费收款方账号";
	public static final String ALIAS_PAYEE_ACCT_TYPE = "收款方账户类型";
	public static final String ALIAS_PAYEE_ACCT_NAME = "手续费付款方名称";
	public static final String ALIAS_FEE_AMT = "手续费";
	public static final String ALIAS_ORDER_NO = "订单号";
	public static final String ALIAS_TRANS_STAT = "订单记账状态";
	public static final String ALIAS_BIZ_TYPE = "业务类型";
	public static final String ALIAS_CHNL_SEQ = "第三方平台流水号";
	public static final String ALIAS_REMARK1 = "备注";
	public static final String ALIAS_REMARK2 = "备注";
	

	//columns START
    /**
     * 交易渠道 0001核心 0002 银联 0003微信  0004 中金 0005支付宝  (为空的情况下，记录我行收商户的手续费汇总)       db_column: ROUTE_CODE 
     */ 	
	private String routeCode;
    /**
     * 0 手续费收入 1手续费支出       db_column: FEE_TYPE 
     */ 	
	private String feeType;
    /**
     * 统计时间       db_column: STATISTICS_TIME 
     */ 	
	private java.util.Date statisticsTime;
    /**
     * 交易时间       db_column: TXN_TIME 
     */ 	
	private java.util.Date txnTime;
    /**
     * 商户代码       db_column: MER_ID 
     */ 	
	private String merId;
    /**
     * 机构代码       db_column: ISS_CODE 
     */ 	
	private String issCode;
    /**
     * 付款方账户类型       db_column: PAYER_ACCT_TYPE 
     */ 	
	private String payerAcctType;
    /**
     * 手续费付款方账号       db_column: PAYER_ACCT_NO 
     */ 	
	private String payerAcctNo;
    /**
     * 手续费付款方名称       db_column: PAYER_ACCT_NAME 
     */ 	
	private String payerAcctName;
    /**
     * 手续费收款方账号       db_column: PAYEE_ACCT_NO 
     */ 	
	private String payeeAcctNo;
    /**
     * 收款方账户类型       db_column: PAYEE_ACCT_TYPE 
     */ 	
	private String payeeAcctType;
    /**
     * 手续费付款方名称       db_column: PAYEE_ACCT_NAME 
     */ 	
	private String payeeAcctName;
    /**
     * 手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 订单号       db_column: ORDER_NO 
     */ 	
	private String orderNo;
    /**
     * 订单记账状态       db_column: TRANS_STAT 
     */ 	
	private String transStat;
    /**
     * 业务类型       db_column: BIZ_TYPE 
     */ 	
	private String bizType;
    /**
     * 第三方平台流水号       db_column: CHNL_SEQ 
     */ 	
	private String chnlSeq;
    /**
     * 备注       db_column: REMARK1 
     */ 	
	private String remark1;
    /**
     * 备注       db_column: REMARK2 
     */ 	
	private String remark2;
	//columns END


	
	
	public String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(String value) {
		this.routeCode = value;
	}
	
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String value) {
		this.feeType = value;
	}
	
	
	public java.util.Date getStatisticsTime() {
		return this.statisticsTime;
	}
	
	public void setStatisticsTime(java.util.Date value) {
		this.statisticsTime = value;
	}
	
	
	public java.util.Date getTxnTime() {
		return this.txnTime;
	}
	
	public void setTxnTime(java.util.Date value) {
		this.txnTime = value;
	}
	
	
	public String getMerId() {
		return this.merId;
	}
	
	public void setMerId(String value) {
		this.merId = value;
	}
	
	
	public String getIssCode() {
		return this.issCode;
	}
	
	public void setIssCode(String value) {
		this.issCode = value;
	}
	
	
	public String getPayerAcctType() {
		return this.payerAcctType;
	}
	
	public void setPayerAcctType(String value) {
		this.payerAcctType = value;
	}
	
	
	public String getPayerAcctNo() {
		return this.payerAcctNo;
	}
	
	public void setPayerAcctNo(String value) {
		this.payerAcctNo = value;
	}
	
	
	public String getPayerAcctName() {
		return this.payerAcctName;
	}
	
	public void setPayerAcctName(String value) {
		this.payerAcctName = value;
	}
	
	
	public String getPayeeAcctNo() {
		return this.payeeAcctNo;
	}
	
	public void setPayeeAcctNo(String value) {
		this.payeeAcctNo = value;
	}
	
	
	public String getPayeeAcctType() {
		return this.payeeAcctType;
	}
	
	public void setPayeeAcctType(String value) {
		this.payeeAcctType = value;
	}
	
	
	public String getPayeeAcctName() {
		return this.payeeAcctName;
	}
	
	public void setPayeeAcctName(String value) {
		this.payeeAcctName = value;
	}
	
	
	public java.math.BigDecimal getFeeAmt() {
		return this.feeAmt;
	}
	
	public void setFeeAmt(java.math.BigDecimal value) {
		this.feeAmt = value;
	}
	
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	
	public String getTransStat() {
		return this.transStat;
	}
	
	public void setTransStat(String value) {
		this.transStat = value;
	}
	
	
	public String getBizType() {
		return this.bizType;
	}
	
	public void setBizType(String value) {
		this.bizType = value;
	}
	
	
	public String getChnlSeq() {
		return this.chnlSeq;
	}
	
	public void setChnlSeq(String value) {
		this.chnlSeq = value;
	}
	
	
	public String getRemark1() {
		return this.remark1;
	}
	
	public void setRemark1(String value) {
		this.remark1 = value;
	}
	
	
	public String getRemark2() {
		return this.remark2;
	}
	
	public void setRemark2(String value) {
		this.remark2 = value;
	}
	

	

}

