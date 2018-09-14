
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class ChkThirdDetailPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChkThirdDetail";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_CHNL_DATE = "渠道日期  第三方交易日期";
	public static final String ALIAS_CHNL_TIME = "渠道时间  第三方交易时间";
	public static final String ALIAS_CHNL_SEQ = " 渠道流水号  第三方流水号";
	public static final String ALIAS_ORDER_NO = "订单号   门户生成/商家订单号";
	public static final String ALIAS_ORG_CODE = "第三方机构代码  （对应支付订单中的商户代码）";
	public static final String ALIAS_PAY_ACCT = "付款账号";
	public static final String ALIAS_PAY_BANKNO = "付款人机构号";
	public static final String ALIAS_PAYEE_ACCT = "收款人账号";
	public static final String ALIAS_PAYEE_BANKNO = "收款人机构号";
	public static final String ALIAS_TRANS_AMT = "交易金额";
	public static final String ALIAS_FEE_AMT = "手续费";
	public static final String ALIAS_CCY = "币种  默认CNY";
	public static final String ALIAS_THIRD_STAT = "第三方对账状态 0-未对账 1-对账成功 2-对方多 3-本系统多 4-金额不符 默认：0";
	public static final String ALIAS_THIRD_DATE = "第三方交易日期";
	public static final String ALIAS_THIRD_SEQ = "第三方交易流水号";
	public static final String ALIAS_CANCEL_FLAG = "撤消标志 0否 1是 默认为“0”";
	public static final String ALIAS_REV_FLAG = "冲正标志 0否 1是 默认为“0”";
	public static final String ALIAS_TRANS_STAT = "交易状态";
	public static final String ALIAS_REMARK = "备用";
	public static final String ALIAS_CHK_BATCH_NO = "对账批次号";
	public static final String ALIAS_CHK_DATE = "对账日期";
	public static final String ALIAS_MER_ID = "第三方平台商户号";
	public static final String ALIAS_IS_REGISTER_FEEAMT = "是否已经统计手续费 0 未统计  1  已统计";
	

	//columns START
    /**
     * 渠道日期  第三方交易日期       db_column: CHNL_DATE 
     */ 	
	private java.util.Date chnlDate;
    /**
     * 渠道时间  第三方交易时间       db_column: CHNL_TIME 
     */ 	
	private java.util.Date chnlTime;
    /**
     *  渠道流水号  第三方流水号       db_column: CHNL_SEQ 
     */ 	
	private String chnlSeq;
    /**
     * 订单号   门户生成/商家订单号       db_column: ORDER_NO 
     */ 	
	private String orderNo;
    /**
     * 第三方机构代码  （对应支付订单中的商户代码）       db_column: ORG_CODE 
     */ 	
	private String orgCode;
    /**
     * 付款账号       db_column: PAY_ACCT 
     */ 	
	private String payAcct;
    /**
     * 付款人机构号       db_column: PAY_BANKNO 
     */ 	
	private String payBankno;
    /**
     * 收款人账号       db_column: PAYEE_ACCT 
     */ 	
	private String payeeAcct;
    /**
     * 收款人机构号       db_column: PAYEE_BANKNO 
     */ 	
	private String payeeBankno;
    /**
     * 交易金额       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 币种  默认CNY       db_column: CCY 
     */ 	
	private String ccy;
    /**
     * 第三方对账状态 0-未对账 1-对账成功 2-对方多 3-本系统多 4-金额不符 默认：0       db_column: THIRD_STAT 
     */ 	
	private String thirdStat;
    /**
     * 第三方交易日期       db_column: THIRD_DATE 
     */ 	
	private java.util.Date thirdDate;
    /**
     * 第三方交易流水号       db_column: THIRD_SEQ 
     */ 	
	private String thirdSeq;
    /**
     * 撤消标志 0否 1是 默认为“0”       db_column: CANCEL_FLAG 
     */ 	
	private String cancelFlag;
    /**
     * 冲正标志 0否 1是 默认为“0”       db_column: REV_FLAG 
     */ 	
	private String revFlag;
    /**
     * 交易状态       db_column: TRANS_STAT 
     */ 	
	private String transStat;
    /**
     * 备用       db_column: REMARK 
     */ 	
	private String remark;
    /**
     * 对账批次号       db_column: CHK_BATCH_NO 
     */ 	
	private String chkBatchNo;
    /**
     * 对账日期       db_column: CHK_DATE 
     */ 	
	private java.util.Date chkDate;
    /**
     * 第三方平台商户号       db_column: MER_ID 
     */ 	
	private String merId;
    /**
     * 是否已经统计手续费 0 未统计  1  已统计       db_column: IS_REGISTER_FEEAMT 
     */ 	
	private String isRegisterFeeamt;
	//columns END


	
	
	public java.util.Date getChnlDate() {
		return this.chnlDate;
	}
	
	public void setChnlDate(java.util.Date value) {
		this.chnlDate = value;
	}
	
	
	public java.util.Date getChnlTime() {
		return this.chnlTime;
	}
	
	public void setChnlTime(java.util.Date value) {
		this.chnlTime = value;
	}
	
	
	public String getChnlSeq() {
		return this.chnlSeq;
	}
	
	public void setChnlSeq(String value) {
		this.chnlSeq = value;
	}
	
	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(String value) {
		this.orgCode = value;
	}
	
	
	public String getPayAcct() {
		return this.payAcct;
	}
	
	public void setPayAcct(String value) {
		this.payAcct = value;
	}
	
	
	public String getPayBankno() {
		return this.payBankno;
	}
	
	public void setPayBankno(String value) {
		this.payBankno = value;
	}
	
	
	public String getPayeeAcct() {
		return this.payeeAcct;
	}
	
	public void setPayeeAcct(String value) {
		this.payeeAcct = value;
	}
	
	
	public String getPayeeBankno() {
		return this.payeeBankno;
	}
	
	public void setPayeeBankno(String value) {
		this.payeeBankno = value;
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
	
	
	public String getCcy() {
		return this.ccy;
	}
	
	public void setCcy(String value) {
		this.ccy = value;
	}
	
	
	public String getThirdStat() {
		return this.thirdStat;
	}
	
	public void setThirdStat(String value) {
		this.thirdStat = value;
	}
	
	
	public java.util.Date getThirdDate() {
		return this.thirdDate;
	}
	
	public void setThirdDate(java.util.Date value) {
		this.thirdDate = value;
	}
	
	
	public String getThirdSeq() {
		return this.thirdSeq;
	}
	
	public void setThirdSeq(String value) {
		this.thirdSeq = value;
	}
	
	
	public String getCancelFlag() {
		return this.cancelFlag;
	}
	
	public void setCancelFlag(String value) {
		this.cancelFlag = value;
	}
	
	
	public String getRevFlag() {
		return this.revFlag;
	}
	
	public void setRevFlag(String value) {
		this.revFlag = value;
	}
	
	
	public String getTransStat() {
		return this.transStat;
	}
	
	public void setTransStat(String value) {
		this.transStat = value;
	}
	
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	
	public String getChkBatchNo() {
		return this.chkBatchNo;
	}
	
	public void setChkBatchNo(String value) {
		this.chkBatchNo = value;
	}
	
	
	public java.util.Date getChkDate() {
		return this.chkDate;
	}
	
	public void setChkDate(java.util.Date value) {
		this.chkDate = value;
	}
	
	
	public String getMerId() {
		return this.merId;
	}
	
	public void setMerId(String value) {
		this.merId = value;
	}
	
	
	public String getIsRegisterFeeamt() {
		return this.isRegisterFeeamt;
	}
	
	public void setIsRegisterFeeamt(String value) {
		this.isRegisterFeeamt = value;
	}
	

	

}

