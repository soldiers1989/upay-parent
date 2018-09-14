
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayFlowListHisPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayFlowListHis";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ORDER_NO = "订单号 商户订单";
	public static final String ALIAS_ORDER_DES = "订单描述 商户";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_SEC_MER_NO = "二级商户号";
	public static final String ALIAS_SR_FLAG = "往来标识 1：往（直销银行发起） 2：来（直销银行接收）";
	public static final String ALIAS_SYS_DATE = "交易日期 平台日期";
	public static final String ALIAS_TRANS_SUB_SEQ = "明细流水号，如一笔支付订单一次记账，可与交易流水号相同，否则不能相同；";
	public static final String ALIAS_ROUTE_CODE = "通道代码 见附录4.3,ROUTE_CODE";
	public static final String ALIAS_PAY_USER_ID = "支付用户ID";
	public static final String ALIAS_PAYER_ACCT_TYPE = "付款账号类型 见附录4.3,ACCT_TYPE";
	public static final String ALIAS_PAYER_ACCT_NO = "付款账号";
	public static final String ALIAS_PAYER_NAME = "付款户名";
	public static final String ALIAS_PAYER_ORG_NAME = "付款人开户机构名 登记卡bin行号对照表中的行名";
	public static final String ALIAS_PAYER_BANK_NO = "付款开户行号";
	public static final String ALIAS_PAYER_BANK_NAME = "付款开户行名";
	public static final String ALIAS_PAYEE_ACCT_TYPE = "收款账号类型 见附录4.3,acct_type";
	public static final String ALIAS_PAYEE_ACCT_NO = "收款账号";
	public static final String ALIAS_PAYEE_NAME = "收款户名";
	public static final String ALIAS_PAYEE_ORG_NAME = "收款人开户机构名 登记卡bin行号对照表中的行名";
	public static final String ALIAS_PAYEE_BANK_NO = "收款开户行号";
	public static final String ALIAS_PAYEE_BANK_NAME = "收款开户行名";
	public static final String ALIAS_CCY = "币种";
	public static final String ALIAS_TRANS_AMT = "支付金额";
	public static final String ALIAS_FEE_AMT = "支付手续费";
	public static final String ALIAS_TRANS_STAT = "内部交易状态 见附录4.3,trans_stat";
	public static final String ALIAS_ROUTE_TRANS_STAT = "通道方交易状态 0：成功， 1：失败， 2：处理中";
	public static final String ALIAS_TRANS_TIME = "支付时间";
	public static final String ALIAS_ROUTE_DATE = "通道交易日期";
	public static final String ALIAS_ROUTE_SEQ = "通道交易流水号";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_CHK_FLAG = "对账标志 0-未对账 1-对账成功 2-对账不平 默认：0";
	public static final String ALIAS_CHK_BATCH_NO = "对账批次号";
	public static final String ALIAS_CHK_DATE = "对账日期";
	public static final String ALIAS_OPEN_ID = "用户ID，微信公众号支付用户在公众号中的唯一标识";
	public static final String ALIAS_CLEAR_FLAG = "清算标志 默认：0 0：未清算  7：清算成功  8：待清算 9：清算中";
	public static final String ALIAS_CLEAR_BATCH_NO = "清算批次号";
	public static final String ALIAS_CLEAR_DATE = "清算日期";
	public static final String ALIAS_SEQ_NO = "seqNo";
	public static final String ALIAS_SETTLE_KEY = "清算主键（银联）";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 订单号 商户订单       db_column: ORDER_NO 
     */ 	
	private java.lang.String orderNo;
    /**
     * 订单描述 商户       db_column: ORDER_DES 
     */ 	
	private java.lang.String orderDes;
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 二级商户号       db_column: SEC_MER_NO 
     */ 	
	private java.lang.String secMerNo;
    /**
     * 往来标识 1：往（直销银行发起） 2：来（直销银行接收）       db_column: SR_FLAG 
     */ 	
	private java.lang.String srFlag;
    /**
     * 交易日期 平台日期       db_column: SYS_DATE 
     */ 	
	private java.util.Date sysDate;
    /**
     * 明细流水号，如一笔支付订单一次记账，可与交易流水号相同，否则不能相同；       db_column: TRANS_SUB_SEQ 
     */ 	
	private java.lang.String transSubSeq;
    /**
     * 通道代码 见附录4.3,ROUTE_CODE       db_column: ROUTE_CODE 
     */ 	
	private java.lang.String routeCode;
    /**
     * 支付用户ID       db_column: PAY_USER_ID 
     */ 	
	private java.lang.String payUserId;
    /**
     * 付款账号类型 见附录4.3,ACCT_TYPE       db_column: PAYER_ACCT_TYPE 
     */ 	
	private java.lang.String payerAcctType;
    /**
     * 付款账号       db_column: PAYER_ACCT_NO 
     */ 	
	private java.lang.String payerAcctNo;
    /**
     * 付款户名       db_column: PAYER_NAME 
     */ 	
	private java.lang.String payerName;
    /**
     * 付款人开户机构名 登记卡bin行号对照表中的行名       db_column: PAYER_ORG_NAME 
     */ 	
	private java.lang.String payerOrgName;
    /**
     * 付款开户行号       db_column: PAYER_BANK_NO 
     */ 	
	private java.lang.String payerBankNo;
    /**
     * 付款开户行名       db_column: PAYER_BANK_NAME 
     */ 	
	private java.lang.String payerBankName;
    /**
     * 收款账号类型 见附录4.3,acct_type       db_column: PAYEE_ACCT_TYPE 
     */ 	
	private java.lang.String payeeAcctType;
    /**
     * 收款账号       db_column: PAYEE_ACCT_NO 
     */ 	
	private java.lang.String payeeAcctNo;
    /**
     * 收款户名       db_column: PAYEE_NAME 
     */ 	
	private java.lang.String payeeName;
    /**
     * 收款人开户机构名 登记卡bin行号对照表中的行名       db_column: PAYEE_ORG_NAME 
     */ 	
	private java.lang.String payeeOrgName;
    /**
     * 收款开户行号       db_column: PAYEE_BANK_NO 
     */ 	
	private java.lang.String payeeBankNo;
    /**
     * 收款开户行名       db_column: PAYEE_BANK_NAME 
     */ 	
	private java.lang.String payeeBankName;
    /**
     * 币种       db_column: CCY 
     */ 	
	private java.lang.String ccy;
    /**
     * 支付金额       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 支付手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 内部交易状态 见附录4.3,trans_stat       db_column: TRANS_STAT 
     */ 	
	private java.lang.String transStat;
    /**
     * 通道方交易状态 0：成功， 1：失败， 2：处理中       db_column: ROUTE_TRANS_STAT 
     */ 	
	private java.lang.String routeTransStat;
    /**
     * 支付时间       db_column: TRANS_TIME 
     */ 	
	private java.util.Date transTime;
    /**
     * 通道交易日期       db_column: ROUTE_DATE 
     */ 	
	private java.util.Date routeDate;
    /**
     * 通道交易流水号       db_column: ROUTE_SEQ 
     */ 	
	private java.lang.String routeSeq;
    /**
     * 最后变更时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
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
     * 对账标志 0-未对账 1-对账成功 2-对账不平 默认：0       db_column: CHK_FLAG 
     */ 	
	private java.lang.String chkFlag;
    /**
     * 对账批次号       db_column: CHK_BATCH_NO 
     */ 	
	private java.lang.String chkBatchNo;
    /**
     * 对账日期       db_column: CHK_DATE 
     */ 	
	private java.util.Date chkDate;
    /**
     * 用户ID，微信公众号支付用户在公众号中的唯一标识       db_column: OPEN_ID 
     */ 	
	private java.lang.String openId;
    /**
     * 清算标志 默认：0 0：未清算  7：清算成功  8：待清算 9：清算中       db_column: CLEAR_FLAG 
     */ 	
	private java.lang.String clearFlag;
    /**
     * 清算批次号       db_column: CLEAR_BATCH_NO 
     */ 	
	private java.lang.String clearBatchNo;
    /**
     * 清算日期       db_column: CLEAR_DATE 
     */ 	
	private java.util.Date clearDate;
    /**
     * seqNo       db_column: SEQ_NO 
     */ 	
	private java.math.BigDecimal seqNo;
    /**
     * 清算主键（银联）       db_column: SETTLE_KEY 
     */ 	
	private java.lang.String settleKey;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	
	public java.lang.String getOrderDes() {
		return this.orderDes;
	}
	
	public void setOrderDes(java.lang.String value) {
		this.orderDes = value;
	}
	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getSecMerNo() {
		return this.secMerNo;
	}
	
	public void setSecMerNo(java.lang.String value) {
		this.secMerNo = value;
	}
	
	
	public java.lang.String getSrFlag() {
		return this.srFlag;
	}
	
	public void setSrFlag(java.lang.String value) {
		this.srFlag = value;
	}
	
	
	public java.util.Date getSysDate() {
		return this.sysDate;
	}
	
	public void setSysDate(java.util.Date value) {
		this.sysDate = value;
	}
	
	
	public java.lang.String getTransSubSeq() {
		return this.transSubSeq;
	}
	
	public void setTransSubSeq(java.lang.String value) {
		this.transSubSeq = value;
	}
	
	
	public java.lang.String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(java.lang.String value) {
		this.routeCode = value;
	}
	
	
	public java.lang.String getPayUserId() {
		return this.payUserId;
	}
	
	public void setPayUserId(java.lang.String value) {
		this.payUserId = value;
	}
	
	
	public java.lang.String getPayerAcctType() {
		return this.payerAcctType;
	}
	
	public void setPayerAcctType(java.lang.String value) {
		this.payerAcctType = value;
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
	
	
	public java.lang.String getPayerOrgName() {
		return this.payerOrgName;
	}
	
	public void setPayerOrgName(java.lang.String value) {
		this.payerOrgName = value;
	}
	
	
	public java.lang.String getPayerBankNo() {
		return this.payerBankNo;
	}
	
	public void setPayerBankNo(java.lang.String value) {
		this.payerBankNo = value;
	}
	
	
	public java.lang.String getPayerBankName() {
		return this.payerBankName;
	}
	
	public void setPayerBankName(java.lang.String value) {
		this.payerBankName = value;
	}
	
	
	public java.lang.String getPayeeAcctType() {
		return this.payeeAcctType;
	}
	
	public void setPayeeAcctType(java.lang.String value) {
		this.payeeAcctType = value;
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
	
	
	public java.lang.String getPayeeOrgName() {
		return this.payeeOrgName;
	}
	
	public void setPayeeOrgName(java.lang.String value) {
		this.payeeOrgName = value;
	}
	
	
	public java.lang.String getPayeeBankNo() {
		return this.payeeBankNo;
	}
	
	public void setPayeeBankNo(java.lang.String value) {
		this.payeeBankNo = value;
	}
	
	
	public java.lang.String getPayeeBankName() {
		return this.payeeBankName;
	}
	
	public void setPayeeBankName(java.lang.String value) {
		this.payeeBankName = value;
	}
	
	
	public java.lang.String getCcy() {
		return this.ccy;
	}
	
	public void setCcy(java.lang.String value) {
		this.ccy = value;
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
	
	
	public java.lang.String getTransStat() {
		return this.transStat;
	}
	
	public void setTransStat(java.lang.String value) {
		this.transStat = value;
	}
	
	
	public java.lang.String getRouteTransStat() {
		return this.routeTransStat;
	}
	
	public void setRouteTransStat(java.lang.String value) {
		this.routeTransStat = value;
	}
	
	
	public java.util.Date getTransTime() {
		return this.transTime;
	}
	
	public void setTransTime(java.util.Date value) {
		this.transTime = value;
	}
	
	
	public java.util.Date getRouteDate() {
		return this.routeDate;
	}
	
	public void setRouteDate(java.util.Date value) {
		this.routeDate = value;
	}
	
	
	public java.lang.String getRouteSeq() {
		return this.routeSeq;
	}
	
	public void setRouteSeq(java.lang.String value) {
		this.routeSeq = value;
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
	
	
	public java.lang.String getChkFlag() {
		return this.chkFlag;
	}
	
	public void setChkFlag(java.lang.String value) {
		this.chkFlag = value;
	}
	
	
	public java.lang.String getChkBatchNo() {
		return this.chkBatchNo;
	}
	
	public void setChkBatchNo(java.lang.String value) {
		this.chkBatchNo = value;
	}
	
	
	public java.util.Date getChkDate() {
		return this.chkDate;
	}
	
	public void setChkDate(java.util.Date value) {
		this.chkDate = value;
	}
	
	
	public java.lang.String getOpenId() {
		return this.openId;
	}
	
	public void setOpenId(java.lang.String value) {
		this.openId = value;
	}
	
	
	public java.lang.String getClearFlag() {
		return this.clearFlag;
	}
	
	public void setClearFlag(java.lang.String value) {
		this.clearFlag = value;
	}
	
	
	public java.lang.String getClearBatchNo() {
		return this.clearBatchNo;
	}
	
	public void setClearBatchNo(java.lang.String value) {
		this.clearBatchNo = value;
	}
	
	
	public java.util.Date getClearDate() {
		return this.clearDate;
	}
	
	public void setClearDate(java.util.Date value) {
		this.clearDate = value;
	}
	
	
	public java.math.BigDecimal getSeqNo() {
		return this.seqNo;
	}
	
	public void setSeqNo(java.math.BigDecimal value) {
		this.seqNo = value;
	}
	
	
	public java.lang.String getSettleKey() {
		return this.settleKey;
	}
	
	public void setSettleKey(java.lang.String value) {
		this.settleKey = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

