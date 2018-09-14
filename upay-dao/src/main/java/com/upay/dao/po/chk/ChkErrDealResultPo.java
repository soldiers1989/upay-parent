
package com.upay.dao.po.chk;
import com.pactera.dipper.po.BasePo;

public class ChkErrDealResultPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "ChkErrDealResult";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ORG_CODE = "单位代码  默认为：00000000  对账的机构代码";
	public static final String ALIAS_HOST_CHK_BATCH_NO = "核心对账批次号";
	public static final String ALIAS_HOST_CHK_DATE = "核心对帐日期";
	public static final String ALIAS_THIRD_CHK_BATCH_NO = "第三方对账批次号";
	public static final String ALIAS_THIRD_CHK_DATE = "第三方对账日期";
	public static final String ALIAS_PLAT_DATE = "平台日期";
	public static final String ALIAS_SEQ_NO = "平台流水";
	public static final String ALIAS_HOST_SEQ = "主机流水号";
	public static final String ALIAS_CHNL_SEQ = "渠道流水号";
	public static final String ALIAS_DEAL_DATE = "调账日期 用于隔日调账时，使用同一日期及流水号进行调整，保障不会出现重复调账";
	public static final String ALIAS_DEAL_NO = "调账流水";
	public static final String ALIAS_CURR_NO = "币种";
	public static final String ALIAS_TRANS_CODE = "内部交易代码";
	public static final String ALIAS_RATE_TYPE = "钞汇属性";
	public static final String ALIAS_PAY_ACCT = "付款账号";
	public static final String ALIAS_PAY_BANKNO = "付款人机构号";
	public static final String ALIAS_PAYEE_ACCT = "收款人账号";
	public static final String ALIAS_PAYEE_BANKNO = "收款人机构号";
	public static final String ALIAS_TRANS_AMT = "交易金额";
	public static final String ALIAS_FEE_AMT = "应收手续费";
	public static final String ALIAS_RESULT_INFO = "差错处理结果 0— 未处理 1— 处理成功 2— 处理失败";
	public static final String ALIAS_PROC_NUM = "差错处理次数";
	public static final String ALIAS_PROC_TIME = "处理日期时间";
	public static final String ALIAS_TELLER_NO = "差错处理操作员";
	public static final String ALIAS_CHK_TELLER = "复核操作员";
	public static final String ALIAS_RESERVE1 = "备用字段1";
	public static final String ALIAS_RESERVE2 = "备用字段2";
	

	//columns START
    /**
     * 单位代码  默认为：00000000  对账的机构代码       db_column: ORG_CODE 
     */ 	
	private java.lang.String orgCode;
    /**
     * 核心对账批次号       db_column: HOST_CHK_BATCH_NO 
     */ 	
	private java.lang.String hostChkBatchNo;
    /**
     * 核心对帐日期       db_column: HOST_CHK_DATE 
     */ 	
	private java.util.Date hostChkDate;
    /**
     * 第三方对账批次号       db_column: THIRD_CHK_BATCH_NO 
     */ 	
	private java.lang.String thirdChkBatchNo;
    /**
     * 第三方对账日期       db_column: THIRD_CHK_DATE 
     */ 	
	private java.util.Date thirdChkDate;
    /**
     * 平台日期       db_column: PLAT_DATE 
     */ 	
	private java.util.Date platDate;
    /**
     * 平台流水       db_column: SEQ_NO 
     */ 	
	private java.lang.String seqNo;
    /**
     * 主机流水号       db_column: HOST_SEQ 
     */ 	
	private java.lang.String hostSeq;
    /**
     * 渠道流水号       db_column: CHNL_SEQ 
     */ 	
	private java.lang.String chnlSeq;
    /**
     * 调账日期 用于隔日调账时，使用同一日期及流水号进行调整，保障不会出现重复调账       db_column: DEAL_DATE 
     */ 	
	private java.util.Date dealDate;
    /**
     * 调账流水       db_column: DEAL_NO 
     */ 	
	private java.lang.String dealNo;
    /**
     * 币种       db_column: CURR_NO 
     */ 	
	private java.lang.String currNo;
    /**
     * 内部交易代码       db_column: TRANS_CODE 
     */ 	
	private java.lang.String transCode;
    /**
     * 钞汇属性       db_column: RATE_TYPE 
     */ 	
	private java.lang.String rateType;
    /**
     * 付款账号       db_column: PAY_ACCT 
     */ 	
	private java.lang.String payAcct;
    /**
     * 付款人机构号       db_column: PAY_BANKNO 
     */ 	
	private java.lang.String payBankno;
    /**
     * 收款人账号       db_column: PAYEE_ACCT 
     */ 	
	private java.lang.String payeeAcct;
    /**
     * 收款人机构号       db_column: PAYEE_BANKNO 
     */ 	
	private java.lang.String payeeBankno;
    /**
     * 交易金额       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 应收手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 差错处理结果 0— 未处理 1— 处理成功 2— 处理失败       db_column: RESULT_INFO 
     */ 	
	private java.lang.String resultInfo;
    /**
     * 差错处理次数       db_column: PROC_NUM 
     */ 	
	private java.lang.Integer procNum;
    /**
     * 处理日期时间       db_column: PROC_TIME 
     */ 	
	private java.util.Date procTime;
    /**
     * 差错处理操作员       db_column: TELLER_NO 
     */ 	
	private java.lang.String tellerNo;
    /**
     * 复核操作员       db_column: CHK_TELLER 
     */ 	
	private java.lang.String chkTeller;
    /**
     * 备用字段1       db_column: RESERVE1 
     */ 	
	private java.lang.String reserve1;
    /**
     * 备用字段2       db_column: RESERVE2 
     */ 	
	private java.lang.String reserve2;
	
	private java.lang.String flowFlag;
	//columns END


	
	
	public java.lang.String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(java.lang.String value) {
		this.orgCode = value;
	}
	
	
	public java.lang.String getHostChkBatchNo() {
		return this.hostChkBatchNo;
	}
	
	public void setHostChkBatchNo(java.lang.String value) {
		this.hostChkBatchNo = value;
	}
	
	
	public java.util.Date getHostChkDate() {
		return this.hostChkDate;
	}
	
	public void setHostChkDate(java.util.Date value) {
		this.hostChkDate = value;
	}
	
	
	public java.lang.String getThirdChkBatchNo() {
		return this.thirdChkBatchNo;
	}
	
	public void setThirdChkBatchNo(java.lang.String value) {
		this.thirdChkBatchNo = value;
	}
	
	
	public java.util.Date getThirdChkDate() {
		return this.thirdChkDate;
	}
	
	public void setThirdChkDate(java.util.Date value) {
		this.thirdChkDate = value;
	}
	
	
	public java.util.Date getPlatDate() {
		return this.platDate;
	}
	
	public void setPlatDate(java.util.Date value) {
		this.platDate = value;
	}
	
	
	public java.lang.String getSeqNo() {
		return this.seqNo;
	}
	
	public void setSeqNo(java.lang.String value) {
		this.seqNo = value;
	}
	
	
	public java.lang.String getHostSeq() {
		return this.hostSeq;
	}
	
	public void setHostSeq(java.lang.String value) {
		this.hostSeq = value;
	}
	
	
	public java.lang.String getChnlSeq() {
		return this.chnlSeq;
	}
	
	public void setChnlSeq(java.lang.String value) {
		this.chnlSeq = value;
	}
	
	
	public java.util.Date getDealDate() {
		return this.dealDate;
	}
	
	public void setDealDate(java.util.Date value) {
		this.dealDate = value;
	}
	
	
	public java.lang.String getDealNo() {
		return this.dealNo;
	}
	
	public void setDealNo(java.lang.String value) {
		this.dealNo = value;
	}
	
	
	public java.lang.String getCurrNo() {
		return this.currNo;
	}
	
	public void setCurrNo(java.lang.String value) {
		this.currNo = value;
	}
	
	
	public java.lang.String getTransCode() {
		return this.transCode;
	}
	
	public void setTransCode(java.lang.String value) {
		this.transCode = value;
	}
	
	
	public java.lang.String getRateType() {
		return this.rateType;
	}
	
	public void setRateType(java.lang.String value) {
		this.rateType = value;
	}
	
	
	public java.lang.String getPayAcct() {
		return this.payAcct;
	}
	
	public void setPayAcct(java.lang.String value) {
		this.payAcct = value;
	}
	
	
	public java.lang.String getPayBankno() {
		return this.payBankno;
	}
	
	public void setPayBankno(java.lang.String value) {
		this.payBankno = value;
	}
	
	
	public java.lang.String getPayeeAcct() {
		return this.payeeAcct;
	}
	
	public void setPayeeAcct(java.lang.String value) {
		this.payeeAcct = value;
	}
	
	
	public java.lang.String getPayeeBankno() {
		return this.payeeBankno;
	}
	
	public void setPayeeBankno(java.lang.String value) {
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
	
	
	public java.lang.String getResultInfo() {
		return this.resultInfo;
	}
	
	public void setResultInfo(java.lang.String value) {
		this.resultInfo = value;
	}
	
	
	public java.lang.Integer getProcNum() {
		return this.procNum;
	}
	
	public void setProcNum(java.lang.Integer value) {
		this.procNum = value;
	}
	
	
	public java.util.Date getProcTime() {
		return this.procTime;
	}
	
	public void setProcTime(java.util.Date value) {
		this.procTime = value;
	}
	
	
	public java.lang.String getTellerNo() {
		return this.tellerNo;
	}
	
	public void setTellerNo(java.lang.String value) {
		this.tellerNo = value;
	}
	
	
	public java.lang.String getChkTeller() {
		return this.chkTeller;
	}
	
	public void setChkTeller(java.lang.String value) {
		this.chkTeller = value;
	}
	
	
	public java.lang.String getReserve1() {
		return this.reserve1;
	}
	
	public void setReserve1(java.lang.String value) {
		this.reserve1 = value;
	}
	
	
	public java.lang.String getReserve2() {
		return this.reserve2;
	}
	
	public void setReserve2(java.lang.String value) {
		this.reserve2 = value;
	}

	public java.lang.String getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(java.lang.String flowFlag) {
		this.flowFlag = flowFlag;
	}
	

	

}

