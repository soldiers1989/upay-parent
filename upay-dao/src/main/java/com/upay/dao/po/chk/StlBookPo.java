
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class StlBookPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "StlBook";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_STL_DATE = "结算清算日期";
	public static final String ALIAS_STL_BATCH_NO = "结算批次号  YYMMDD + 12位系统交易流水号";
	public static final String ALIAS_CARDMER_TYPE = "卡机构类型 1：本行卡途卡 2：他行卡";
	public static final String ALIAS_STL_MER_FLAG = "结算方标识 1：平台结算 2：第三方支付结算";
	public static final String ALIAS_MER_FLAG = "商户类型 01：特约商户 02：核心 03：第三方支付公司 04：二级商户";
	public static final String ALIAS_MER_NO = "商户代码  结算主体";
	public static final String ALIAS_SEC_MER_NO = "二级商户代码  STL_MER_FLAG=2的情况下，发卡机构对应的特约商户";
	public static final String ALIAS_TXN_TOT_AMT = "总金额";
	public static final String ALIAS_PAY_AMT = "应付本金";
	public static final String ALIAS_PAY_FEE = "应付手续费";
	public static final String ALIAS_REV_AMT = "应收本金";
	public static final String ALIAS_REV_FEE = "应收手续费";
	public static final String ALIAS_BAR_AMT = "汇总扎差";
	public static final String ALIAS_CARD_REV_FEE = "发卡机构应收手续费";
	public static final String ALIAS_CARD_PAY_FEE = "发卡机构应付手续费";
	public static final String ALIAS_PLATE_REV_FEE = "平台应收手续费";
	public static final String ALIAS_PLATE_PAY_FEE = "平台应付手续费";
	public static final String ALIAS_STAT = "状态 0：登记 1：完成（入账）";
	public static final String ALIAS_STL_FILE = "结算清算文件名称";
	public static final String ALIAS_REMARK = "备用";
	public static final String ALIAS_PROMOTER_DEPT_NO = "业务员组织机构代码";
	

	//columns START
    /**
     * 结算清算日期       db_column: STL_DATE 
     */ 	
	private java.util.Date stlDate;
    /**
     * 结算批次号  YYMMDD + 12位系统交易流水号       db_column: STL_BATCH_NO 
     */ 	
	private java.lang.String stlBatchNo;
    /**
     * 卡机构类型 1：本行卡途卡 2：他行卡       db_column: CARDMER_TYPE 
     */ 	
	private java.lang.String cardmerType;
    /**
     * 结算方标识 1：平台结算 2：第三方支付结算       db_column: STL_MER_FLAG 
     */ 	
	private java.lang.String stlMerFlag;
    /**
     * 商户类型 01：特约商户 02：核心 03：第三方支付公司 04：二级商户       db_column: MER_FLAG 
     */ 	
	private java.lang.String merFlag;
    /**
     * 商户代码  结算主体       db_column: MER_NO 
     */ 	
	private java.lang.String merNo;
    /**
     * 二级商户代码  STL_MER_FLAG=2的情况下，发卡机构对应的特约商户       db_column: SEC_MER_NO 
     */ 	
	private java.lang.String secMerNo;
    /**
     * 总金额       db_column: TXN_TOT_AMT 
     */ 	
	private java.math.BigDecimal txnTotAmt;
    /**
     * 应付本金       db_column: PAY_AMT 
     */ 	
	private java.math.BigDecimal payAmt;
    /**
     * 应付手续费       db_column: PAY_FEE 
     */ 	
	private java.math.BigDecimal payFee;
    /**
     * 应收本金       db_column: REV_AMT 
     */ 	
	private java.math.BigDecimal revAmt;
    /**
     * 应收手续费       db_column: REV_FEE 
     */ 	
	private java.math.BigDecimal revFee;
    /**
     * 汇总扎差       db_column: BAR_AMT 
     */ 	
	private java.math.BigDecimal barAmt;
    /**
     * 发卡机构应收手续费       db_column: CARD_REV_FEE 
     */ 	
	private java.math.BigDecimal cardRevFee;
    /**
     * 发卡机构应付手续费       db_column: CARD_PAY_FEE 
     */ 	
	private java.math.BigDecimal cardPayFee;
    /**
     * 平台应收手续费       db_column: PLATE_REV_FEE 
     */ 	
	private java.math.BigDecimal plateRevFee;
    /**
     * 平台应付手续费       db_column: PLATE_PAY_FEE 
     */ 	
	private java.math.BigDecimal platePayFee;
    /**
     * 状态 0：登记 1：完成（入账）       db_column: STAT 
     */ 	
	private java.lang.String stat;
    /**
     * 结算清算文件名称       db_column: STL_FILE 
     */ 	
	private java.lang.String stlFile;
    /**
     * 备用       db_column: REMARK 
     */ 	
	private java.lang.String remark;
    /**
     * 业务员组织机构代码       db_column: PROMOTER_DEPT_NO 
     */ 	
	private java.lang.String promoterDeptNo;
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
	
	
	public java.lang.String getCardmerType() {
		return this.cardmerType;
	}
	
	public void setCardmerType(java.lang.String value) {
		this.cardmerType = value;
	}
	
	
	public java.lang.String getStlMerFlag() {
		return this.stlMerFlag;
	}
	
	public void setStlMerFlag(java.lang.String value) {
		this.stlMerFlag = value;
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
	
	
	public java.lang.String getSecMerNo() {
		return this.secMerNo;
	}
	
	public void setSecMerNo(java.lang.String value) {
		this.secMerNo = value;
	}
	
	
	public java.math.BigDecimal getTxnTotAmt() {
		return this.txnTotAmt;
	}
	
	public void setTxnTotAmt(java.math.BigDecimal value) {
		this.txnTotAmt = value;
	}
	
	
	public java.math.BigDecimal getPayAmt() {
		return this.payAmt;
	}
	
	public void setPayAmt(java.math.BigDecimal value) {
		this.payAmt = value;
	}
	
	
	public java.math.BigDecimal getPayFee() {
		return this.payFee;
	}
	
	public void setPayFee(java.math.BigDecimal value) {
		this.payFee = value;
	}
	
	
	public java.math.BigDecimal getRevAmt() {
		return this.revAmt;
	}
	
	public void setRevAmt(java.math.BigDecimal value) {
		this.revAmt = value;
	}
	
	
	public java.math.BigDecimal getRevFee() {
		return this.revFee;
	}
	
	public void setRevFee(java.math.BigDecimal value) {
		this.revFee = value;
	}
	
	
	public java.math.BigDecimal getBarAmt() {
		return this.barAmt;
	}
	
	public void setBarAmt(java.math.BigDecimal value) {
		this.barAmt = value;
	}
	
	
	public java.math.BigDecimal getCardRevFee() {
		return this.cardRevFee;
	}
	
	public void setCardRevFee(java.math.BigDecimal value) {
		this.cardRevFee = value;
	}
	
	
	public java.math.BigDecimal getCardPayFee() {
		return this.cardPayFee;
	}
	
	public void setCardPayFee(java.math.BigDecimal value) {
		this.cardPayFee = value;
	}
	
	
	public java.math.BigDecimal getPlateRevFee() {
		return this.plateRevFee;
	}
	
	public void setPlateRevFee(java.math.BigDecimal value) {
		this.plateRevFee = value;
	}
	
	
	public java.math.BigDecimal getPlatePayFee() {
		return this.platePayFee;
	}
	
	public void setPlatePayFee(java.math.BigDecimal value) {
		this.platePayFee = value;
	}
	
	
	public java.lang.String getStat() {
		return this.stat;
	}
	
	public void setStat(java.lang.String value) {
		this.stat = value;
	}
	
	
	public java.lang.String getStlFile() {
		return this.stlFile;
	}
	
	public void setStlFile(java.lang.String value) {
		this.stlFile = value;
	}
	
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	
	public java.lang.String getPromoterDeptNo() {
		return this.promoterDeptNo;
	}
	
	public void setPromoterDeptNo(java.lang.String value) {
		this.promoterDeptNo = value;
	}
	

	

}

