
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class ChkMerListPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChkMerList";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CHK_DATE = "对账日期";
	public static final String ALIAS_MER_FLAG = "商户类型 01：特约商户 02：发卡机构 03：合作公司";
	public static final String ALIAS_MER_NO = "商户代码";
	public static final String ALIAS_CHK_FILE = "对账文件名称";
	public static final String ALIAS_REC_NUM = "记录序号";
	public static final String ALIAS_TXN_DATE = "交易日期  支付系统交易时间";
	public static final String ALIAS_TXN_TIME = "交易时间   支付系统交易时间";
	public static final String ALIAS_ORDER_NO = "交易订单号  亚盟内部订单号";
	public static final String ALIAS_MER_ORDER = "商户订单号   商家订单号或发卡机构流水号";
	public static final String ALIAS_TRANS_TYPE = "交易类型";
	public static final String ALIAS_MER_TIME = "商户时间";
	public static final String ALIAS_ACCT_NO = "账号";
	public static final String ALIAS_CARDMER_CODE = "发卡机构代码   账号所在发卡机构代码";
	public static final String ALIAS_REL_ACCT = "对方账号";
	public static final String ALIAS_BEF_AMT = "优惠前金额";
	public static final String ALIAS_TXN_AMT = "交易金额";
	public static final String ALIAS_ORI_TXN_DATE = "原交易日期  退货时填写";
	public static final String ALIAS_ORI_ORDER_NO = "原交易订单号  退货时填写";
	public static final String ALIAS_CHK_FLAG = "对账标志  0-未对账 1-对账成功 2-对账不平 默认：0";
	public static final String ALIAS_CHK_BATCH_NO = "对账批次号  对账批次号";
	public static final String ALIAS_MEMO = "备用";
	public static final String ALIAS_ORDER_STAT = "订单状态";
	public static final String ALIAS_SEC_MER_NO = "二级商户号";
	public static final String ALIAS_MER_FEE_AMT = "商户手续费";
	public static final String ALIAS_SEC_MER_FEE_AMT = "二级商户手续费";
	public static final String ALIAS_MER_DATE = "商户日期";
	public static final String ALIAS_EXTENSION_PARTY = "推广方";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 对账日期       db_column: CHK_DATE 
     */ 	
	private java.util.Date chkDate;
    /**
     * 商户类型 01：特约商户 02：发卡机构 03：合作公司       db_column: MER_FLAG 
     */ 	
	private java.lang.String merFlag;
    /**
     * 商户代码       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 对账文件名称       db_column: CHK_FILE 
     */ 	
	private java.lang.String chkFile;
    /**
     * 记录序号       db_column: REC_NUM 
     */ 	
	private java.lang.Integer recNum;
    /**
     * 交易日期  支付系统交易时间       db_column: TXN_DATE 
     */ 	
	private java.util.Date txnDate;
    /**
     * 交易时间   支付系统交易时间       db_column: TXN_TIME 
     */ 	
	private java.util.Date txnTime;
    /**
     * 交易订单号  亚盟内部订单号       db_column: ORDER_NO 
     */ 	
	private java.lang.String orderNo;
    /**
     * 商户订单号   商家订单号或发卡机构流水号       db_column: MER_ORDER 
     */ 	
	private java.lang.String merOrder;
    /**
     * 交易类型       db_column: TRANS_TYPE 
     */ 	
	private java.lang.String transType;
    /**
     * 商户时间       db_column: MER_TIME 
     */ 	
	private java.util.Date merTime;
    /**
     * 账号       db_column: ACCT_NO 
     */ 	
	private java.lang.String acctNo;
    /**
     * 发卡机构代码   账号所在发卡机构代码       db_column: CARDMER_CODE 
     */ 	
	private java.lang.String cardmerCode;
    /**
     * 对方账号       db_column: REL_ACCT 
     */ 	
	private java.lang.String relAcct;
    /**
     * 优惠前金额       db_column: BEF_AMT 
     */ 	
	private java.math.BigDecimal befAmt;
    /**
     * 交易金额       db_column: TXN_AMT 
     */ 	
	private java.math.BigDecimal txnAmt;
    /**
     * 原交易日期  退货时填写       db_column: ORI_TXN_DATE 
     */ 	
	private java.util.Date oriTxnDate;
    /**
     * 原交易订单号  退货时填写       db_column: ORI_ORDER_NO 
     */ 	
	private java.lang.String oriOrderNo;
    /**
     * 对账标志  0-未对账 1-对账成功 2-对账不平 默认：0       db_column: CHK_FLAG 
     */ 	
	private java.lang.String chkFlag;
    /**
     * 对账批次号  对账批次号       db_column: CHK_BATCH_NO 
     */ 	
	private java.lang.String chkBatchNo;
    /**
     * 备用       db_column: MEMO 
     */ 	
	private java.lang.String memo;
    /**
     * 订单状态       db_column: ORDER_STAT 
     */ 	
	private java.lang.String orderStat;
    /**
     * 二级商户号       db_column: SEC_MER_NO 
     */ 	
	private java.lang.String secMerNo;
    /**
     * 商户手续费       db_column: MER_FEE_AMT 
     */ 	
	private java.math.BigDecimal merFeeAmt;
    /**
     * 二级商户手续费       db_column: SEC_MER_FEE_AMT 
     */ 	
	private java.math.BigDecimal secMerFeeAmt;
    /**
     * 商户日期       db_column: MER_DATE 
     */ 	
	private java.util.Date merDate;
    /**
     * 推广方       db_column: EXTENSION_PARTY 
     */ 	
	private java.lang.String extensionParty;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
	//columns END


	
	
	public java.util.Date getChkDate() {
		return this.chkDate;
	}
	
	public void setChkDate(java.util.Date value) {
		this.chkDate = value;
	}
	
	
	public java.lang.String getMerFlag() {
		return this.merFlag;
	}
	
	public void setMerFlag(java.lang.String value) {
		this.merFlag = value;
	}
	
	
	public java.lang.String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(java.lang.String value) {
		this.merNo = value;
	}
	
	
	public java.lang.String getChkFile() {
		return this.chkFile;
	}
	
	public void setChkFile(java.lang.String value) {
		this.chkFile = value;
	}
	
	
	public java.lang.Integer getRecNum() {
		return this.recNum;
	}
	
	public void setRecNum(java.lang.Integer value) {
		this.recNum = value;
	}
	
	
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
	
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(java.lang.String value) {
		this.orderNo = value;
	}
	
	
	public java.lang.String getMerOrder() {
		return this.merOrder;
	}
	
	public void setMerOrder(java.lang.String value) {
		this.merOrder = value;
	}
	
	
	public java.lang.String getTransType() {
		return this.transType;
	}
	
	public void setTransType(java.lang.String value) {
		this.transType = value;
	}
	
	
	public java.util.Date getMerTime() {
		return this.merTime;
	}
	
	public void setMerTime(java.util.Date value) {
		this.merTime = value;
	}
	
	
	public java.lang.String getAcctNo() {
		return this.acctNo;
	}
	
	public void setAcctNo(java.lang.String value) {
		this.acctNo = value;
	}
	
	
	public java.lang.String getCardmerCode() {
		return this.cardmerCode;
	}
	
	public void setCardmerCode(java.lang.String value) {
		this.cardmerCode = value;
	}
	
	
	public java.lang.String getRelAcct() {
		return this.relAcct;
	}
	
	public void setRelAcct(java.lang.String value) {
		this.relAcct = value;
	}
	
	
	public java.math.BigDecimal getBefAmt() {
		return this.befAmt;
	}
	
	public void setBefAmt(java.math.BigDecimal value) {
		this.befAmt = value;
	}
	
	
	public java.math.BigDecimal getTxnAmt() {
		return this.txnAmt;
	}
	
	public void setTxnAmt(java.math.BigDecimal value) {
		this.txnAmt = value;
	}
	
	
	public java.util.Date getOriTxnDate() {
		return this.oriTxnDate;
	}
	
	public void setOriTxnDate(java.util.Date value) {
		this.oriTxnDate = value;
	}
	
	
	public java.lang.String getOriOrderNo() {
		return this.oriOrderNo;
	}
	
	public void setOriOrderNo(java.lang.String value) {
		this.oriOrderNo = value;
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
	
	
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	
	public java.lang.String getOrderStat() {
		return this.orderStat;
	}
	
	public void setOrderStat(java.lang.String value) {
		this.orderStat = value;
	}
	
	
	public java.lang.String getSecMerNo() {
		return this.secMerNo;
	}
	
	public void setSecMerNo(java.lang.String value) {
		this.secMerNo = value;
	}
	
	
	public java.math.BigDecimal getMerFeeAmt() {
		return this.merFeeAmt;
	}
	
	public void setMerFeeAmt(java.math.BigDecimal value) {
		this.merFeeAmt = value;
	}
	
	
	public java.math.BigDecimal getSecMerFeeAmt() {
		return this.secMerFeeAmt;
	}
	
	public void setSecMerFeeAmt(java.math.BigDecimal value) {
		this.secMerFeeAmt = value;
	}
	
	
	public java.util.Date getMerDate() {
		return this.merDate;
	}
	
	public void setMerDate(java.util.Date value) {
		this.merDate = value;
	}
	
	
	public java.lang.String getExtensionParty() {
		return this.extensionParty;
	}
	
	public void setExtensionParty(java.lang.String value) {
		this.extensionParty = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

