
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class SmokeStlDetailPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "SmokeStlDetail";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_SEQ_NO = "序号";
	public static final String ALIAS_TRANS_TYPE = "1-清算 2-补贴";
	public static final String ALIAS_BANK_FLAG = "0-行内 1-行外";
	public static final String ALIAS_PAYER_MER_NO = "付款商户号";
	public static final String ALIAS_MER_DATE = "商户日期";
	public static final String ALIAS_MER_SEQ = "商户流水号";
	public static final String ALIAS_PAYER_CARD_TYPE = "11：借记卡 12：贷记卡";
	public static final String ALIAS_PAYER_ACCT_NO = "付款人账号";
	public static final String ALIAS_PAYER_NAME = "付款人名称";
	public static final String ALIAS_PAYEE_MER_NO = "收款商户号";
	public static final String ALIAS_PAYEE_CARD_TYPE = "11：借记卡 12：贷记卡";
	public static final String ALIAS_PAYEE_ACCT_NO = "收款人账号";
	public static final String ALIAS_PAYEE_NAME = "收款人名称";
	public static final String ALIAS_TRANS_AMT = "交易金额";
	public static final String ALIAS_FEE_AMT = "交易手续费";
	public static final String ALIAS_UPAY_DATE = "红塔支付系统日期";
	public static final String ALIAS_UPAY_SEQ = "红塔支付平台流水号";
	public static final String ALIAS_RESULT = "0 成功 1 失败";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	

	//columns START
    /**
     * 序号       db_column: SEQ_NO 
     */ 	
	private java.lang.String seqNo;
    /**
     * 1-清算 2-补贴       db_column: TRANS_TYPE 
     */ 	
	private java.lang.String transType;
    /**
     * 0-行内 1-行外       db_column: BANK_FLAG 
     */ 	
	private java.lang.String bankFlag;
    /**
     * 付款商户号       db_column: PAYER_MER_NO 
     */ 	
	private java.lang.String payerMerNo;
    /**
     * 商户日期       db_column: MER_DATE 
     */ 	
	private java.util.Date merDate;
    /**
     * 商户流水号       db_column: MER_SEQ 
     */ 	
	private java.lang.String merSeq;
    /**
     * 11：借记卡 12：贷记卡       db_column: PAYER_CARD_TYPE 
     */ 	
	private java.lang.String payerCardType;
    /**
     * 付款人账号       db_column: PAYER_ACCT_NO 
     */ 	
	private java.lang.String payerAcctNo;
    /**
     * 付款人名称       db_column: PAYER_NAME 
     */ 	
	private java.lang.String payerName;
    /**
     * 收款商户号       db_column: PAYEE_MER_NO 
     */ 	
	private java.lang.String payeeMerNo;
    /**
     * 11：借记卡 12：贷记卡       db_column: PAYEE_CARD_TYPE 
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
     * 红塔支付平台流水号       db_column: UPAY_SEQ 
     */ 	
	private java.lang.String upaySeq;
    /**
     * 0 成功 1 失败       db_column: RESULT 
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
	//columns END


	
	
	public java.lang.String getSeqNo() {
		return this.seqNo;
	}
	
	public void setSeqNo(java.lang.String value) {
		this.seqNo = value;
	}
	
	
	public java.lang.String getTransType() {
		return this.transType;
	}
	
	public void setTransType(java.lang.String value) {
		this.transType = value;
	}
	
	
	public java.lang.String getBankFlag() {
		return this.bankFlag;
	}
	
	public void setBankFlag(java.lang.String value) {
		this.bankFlag = value;
	}
	
	
	public java.lang.String getPayerMerNo() {
		return this.payerMerNo;
	}
	
	public void setPayerMerNo(java.lang.String value) {
		this.payerMerNo = value;
	}
	
	
	public java.util.Date getMerDate() {
		return this.merDate;
	}
	
	public void setMerDate(java.util.Date value) {
		this.merDate = value;
	}
	
	
	public java.lang.String getMerSeq() {
		return this.merSeq;
	}
	
	public void setMerSeq(java.lang.String value) {
		this.merSeq = value;
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
	
	
	public java.lang.String getPayeeMerNo() {
		return this.payeeMerNo;
	}
	
	public void setPayeeMerNo(java.lang.String value) {
		this.payeeMerNo = value;
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
	
	
	public java.lang.String getUpaySeq() {
		return this.upaySeq;
	}
	
	public void setUpaySeq(java.lang.String value) {
		this.upaySeq = value;
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
	

	

}

