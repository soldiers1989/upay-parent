
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class StlRouteFeeBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "StlRouteFeeBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_STL_DATE = "结算日期";
	public static final String ALIAS_STL_BATCH_NO = "结算批次号";
	public static final String ALIAS_ASS_ID = "分润方 1、资金通道手续费 2、分润费";
	public static final String ALIAS_FLAG = "手续费分润标志";
	public static final String ALIAS_PAYER_CARD_TYPE = "付款方账号类型 借记卡-11 贷记卡-12 个人结算户-13 第三方平台账户-14 单位结算户-15 内部账户-21 他行借记卡-22 本行对公账户-23他行对公账户-24";
	public static final String ALIAS_PAYER_ACCT_NO = "付款人账号";
	public static final String ALIAS_PAYER_NAME = "付款人名称 借记卡-11 贷记卡-12 个人结算户-13 第三方平台账户-14 单位结算户-15 内部账户-21 他行借记卡-22 本行对公账户-23他行对公账户-24";
	public static final String ALIAS_PAYEE_CARD_TYPE = "收款账号类型";
	public static final String ALIAS_PAYEE_ACCT_NO = "收款人账号";
	public static final String ALIAS_PAYEE_NAME = "收款人名称";
	public static final String ALIAS_TRANS_AMT = "交易金额";
	public static final String ALIAS_FEE_AMT = "交易手续费";
	public static final String ALIAS_UPAY_DATE = "红塔支付系统日期";
	public static final String ALIAS_UPAY_ORDER_NO = "红塔支付平台订单号";
	public static final String ALIAS_RESULT = "处理结果 1 成功 2 失败 3 处理中";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_ROUTE_CODE = "资金通道";
	

	//columns START
    /**
     * 结算日期       db_column: STL_DATE 
     */ 	
	private java.util.Date stlDate;
    /**
     * 结算批次号       db_column: STL_BATCH_NO 
     */ 	
	private java.lang.String stlBatchNo;
    /**
     * 分润方 1、资金通道手续费 2、分润费       db_column: ASS_ID 
     */ 	
	private java.lang.String assId;
    /**
     * 手续费分润标志       db_column: FLAG 
     */ 	
	private java.lang.String flag;
    /**
     * 付款方账号类型 借记卡-11 贷记卡-12 个人结算户-13 第三方平台账户-14 单位结算户-15 内部账户-21 他行借记卡-22 本行对公账户-23他行对公账户-24       db_column: PAYER_CARD_TYPE 
     */ 	
	private java.lang.String payerCardType;
    /**
     * 付款人账号       db_column: PAYER_ACCT_NO 
     */ 	
	private java.lang.String payerAcctNo;
    /**
     * 付款人名称 借记卡-11 贷记卡-12 个人结算户-13 第三方平台账户-14 单位结算户-15 内部账户-21 他行借记卡-22 本行对公账户-23他行对公账户-24       db_column: PAYER_NAME 
     */ 	
	private java.lang.String payerName;
    /**
     * 收款账号类型       db_column: PAYEE_CARD_TYPE 
     */ 	
	private java.lang.String payeeCardType;
    /**
     * 收款人账号       db_column: PAYEE_ACCT_NO 
     */ 	
	private java.lang.String payeeAcctNo;
    /**
     * 收款人名称       db_column: PAYEE_NAME 
     */ 	
	private java.lang.String payeeName;
    /**
     * 交易金额       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 交易手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 红塔支付系统日期       db_column: UPAY_DATE 
     */ 	
	private java.util.Date upayDate;
    /**
     * 红塔支付平台订单号       db_column: UPAY_ORDER_NO 
     */ 	
	private java.lang.String upayOrderNo;
    /**
     * 处理结果 1 成功 2 失败 3 处理中       db_column: RESULT 
     */ 	
	private java.lang.String result;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private java.lang.String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private java.lang.String remark2;
    /**
     * 资金通道       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
	//columns END


	
	
	public java.util.Date getStlDate() {
		return this.stlDate;
	}
	
	public void setStlDate(java.util.Date value) {
		this.stlDate = value;
	}
	
	
	public java.lang.String getStlBatchNo() {
		return this.stlBatchNo;
	}
	
	public void setStlBatchNo(java.lang.String value) {
		this.stlBatchNo = value;
	}
	
	
	public java.lang.String getAssId() {
		return this.assId;
	}
	
	public void setAssId(java.lang.String value) {
		this.assId = value;
	}
	
	
	public java.lang.String getFlag() {
		return this.flag;
	}
	
	public void setFlag(java.lang.String value) {
		this.flag = value;
	}
	
	
	public java.lang.String getPayerCardType() {
		return this.payerCardType;
	}
	
	public void setPayerCardType(java.lang.String value) {
		this.payerCardType = value;
	}
	
	
	public java.lang.String getPayerAcctNo() {
		return this.payerAcctNo;
	}
	
	public void setPayerAcctNo(java.lang.String value) {
		this.payerAcctNo = value;
	}
	
	
	public java.lang.String getPayerName() {
		return this.payerName;
	}
	
	public void setPayerName(java.lang.String value) {
		this.payerName = value;
	}
	
	
	public java.lang.String getPayeeCardType() {
		return this.payeeCardType;
	}
	
	public void setPayeeCardType(java.lang.String value) {
		this.payeeCardType = value;
	}
	
	
	public java.lang.String getPayeeAcctNo() {
		return this.payeeAcctNo;
	}
	
	public void setPayeeAcctNo(java.lang.String value) {
		this.payeeAcctNo = value;
	}
	
	
	public java.lang.String getPayeeName() {
		return this.payeeName;
	}
	
	public void setPayeeName(java.lang.String value) {
		this.payeeName = value;
	}
	
	
	public java.math.BigDecimal getTransAmt() {
		return this.transAmt;
	}
	
	public void setTransAmt(java.math.BigDecimal value) {
		this.transAmt = value;
	}
	
	
	public java.math.BigDecimal getFeeAmt() {
		return this.feeAmt;
	}
	
	public void setFeeAmt(java.math.BigDecimal value) {
		this.feeAmt = value;
	}
	
	
	public java.util.Date getUpayDate() {
		return this.upayDate;
	}
	
	public void setUpayDate(java.util.Date value) {
		this.upayDate = value;
	}
	
	
	public java.lang.String getUpayOrderNo() {
		return this.upayOrderNo;
	}
	
	public void setUpayOrderNo(java.lang.String value) {
		this.upayOrderNo = value;
	}
	
	
	public java.lang.String getResult() {
		return this.result;
	}
	
	public void setResult(java.lang.String value) {
		this.result = value;
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
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	

	

}

