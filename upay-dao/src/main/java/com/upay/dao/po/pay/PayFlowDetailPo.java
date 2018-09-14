
package com.upay.dao.po.pay;
import com.pactera.dipper.po.BasePo;

public class PayFlowDetailPo extends BasePo{
	private static final long serialVersionUID = 1L;
	//alias
	public static final String TABLE_ALIAS = "PayFlowDetail";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_ORDER_NO = "订单号";
	public static final String ALIAS_ORDER_DES = "订单描述";
	public static final String ALIAS_MER_NO = "商户号";
	public static final String ALIAS_SEC_MER_NO = "二级商户号";
	public static final String ALIAS_SYS_DATE = "交易日期";
	public static final String ALIAS_ROUTE_CODE = "通道代码";
	public static final String ALIAS_PAY_USER_ID = "支付用户ID 对应用户基本信息表中的USER_ID,只有支付方式为11公众号支付时可为空，其他支付方式必输";
	public static final String ALIAS_PAYER_ACCT_TYPE = "付款账号类型";
	public static final String ALIAS_PAYER_ACCT_NO = "付款账号";
	public static final String ALIAS_PAYER_NAME = "付款户名";
	public static final String ALIAS_PAYER_ORG_NAME = "付款人开户机构名 登记卡bin行号对照表中的行名";
	public static final String ALIAS_PAYER_BANK_NO = "付款开户行号";
	public static final String ALIAS_PAYER_BANK_NAME = "付款开户行名";
	public static final String ALIAS_PAYEE_ACCT_TYPE = "收款账号类型";
	public static final String ALIAS_PAYEE_ACCT_NO = "收款账号";
	public static final String ALIAS_PAYEE_NAME = "收款户名";
	public static final String ALIAS_PAYEE_ORG_NAME = "收款人开户机构名 登记卡bin行号对照表中的行名";
	public static final String ALIAS_PAYEE_BANK_NO = "收款开户行号";
	public static final String ALIAS_PAYEE_BANK_NAME = "收款开户行名";
	public static final String ALIAS_CCY = "币种";
	public static final String ALIAS_TRANS_AMT = "支付金额";
	public static final String ALIAS_TRANS_STAT = "内部交易状态";
	public static final String ALIAS_TRANS_TIME = "支付时间";
	public static final String ALIAS_LAST_UPDATE_TIME = "最后变更时间";
	public static final String ALIAS_REMARK1 = "备用1";
	public static final String ALIAS_REMARK2 = "备用2";
	public static final String ALIAS_REMARK3 = "备用3";
	public static final String ALIAS_FEE_AMT = "支付手续费";
	public static final String ALIAS_TRANS_SUB_SEQ = "支付流水号，与订单流水表的明细流水对应";
	public static final String ALIAS_MER_TRANS_DATE = "商户交易日期";
	public static final String ALIAS_MER_TRANS_SEQ = "商户 交易流水号";
	public static final String ALIAS_BRCH_NO = "核心交易机构号";
	public static final String ALIAS_TELLER_NO = "核心交易柜员号";
	public static final String ALIAS_TRANSFER_TYPE = "转账类型 01电信转账 02微信转账";
	public static final String ALIAS_FEE_ACCT_TYPE = "手续费账户类型";
	public static final String ALIAS_FEE_ACCT_NAME = "手续费账户姓名";
	public static final String ALIAS_FEE_ACCT_NO = "手续费账户";
	public static final String ALIAS_TRF_BATCH_NO = "转账批次号用于标识一个批次转账";
	public static final String ALIAS_TRF_SEQ = "转账批次号用于标识一个批次转账";
	public static final String ALIAS_DIS_BATCH_NO = "分布式业务处理或多线程业务处理批次号";
	public static final String ALIAS_TRANS_FLAG = "交易标志  1：代扣  2：代付";
	public static final String ALIAS_BANK_ID = "银行编号";
	public static final String ALIAS_CERT_TYPE = "证件类型";
	public static final String ALIAS_CERT_NO = "证件号";
	public static final String ALIAS_CERT_MOBILE = "手机号";
	public static final String ALIAS_CERT_NAME = "姓名";
	public static final String ALIAS_CHNLID = "渠道：12柜台 06网银";
	public static final String ALIAS_PROCESS = "用于批量框架处理多线程数据不一致问题 N:代表未处理 Y:代表已处理";
	

    /**
     * 订单号       db_column: ORDER_NO 
     */ 	
	private String orderNo;
    /**
     * 订单描述       db_column: ORDER_DES 
     */ 	
	private String orderDes;
    /**
     * 商户号       db_column: MER_NO 
     */ 	
	private String merNo;
    /**
     * 二级商户号       db_column: SEC_MER_NO 
     */ 	
	private String secMerNo;
    /**
     * 交易日期       db_column: SYS_DATE 
     */ 	
	private java.util.Date sysDate;
    /**
     * 通道代码       db_column: ROUTE_CODE 
     */ 	
	private String routeCode;
    /**
     * 支付用户ID 对应用户基本信息表中的USER_ID,只有支付方式为11公众号支付时可为空，其他支付方式必输       db_column: PAY_USER_ID 
     */ 	
	private String payUserId;
    /**
     * 付款账号类型       db_column: PAYER_ACCT_TYPE 
     */ 	
	private String payerAcctType;
    /**
     * 付款账号       db_column: PAYER_ACCT_NO 
     */ 	
	private String payerAcctNo;
    /**
     * 付款户名       db_column: PAYER_NAME 
     */ 	
	private String payerName;
    /**
     * 付款人开户机构名 登记卡bin行号对照表中的行名       db_column: PAYER_ORG_NAME 
     */ 	
	private String payerOrgName;
    /**
     * 付款开户行号       db_column: PAYER_BANK_NO 
     */ 	
	private String payerBankNo;
    /**
     * 付款开户行名       db_column: PAYER_BANK_NAME 
     */ 	
	private String payerBankName;
    /**
     * 收款账号类型       db_column: PAYEE_ACCT_TYPE 
     */ 	
	private String payeeAcctType;
    /**
     * 收款账号       db_column: PAYEE_ACCT_NO 
     */ 	
	private String payeeAcctNo;
    /**
     * 收款户名       db_column: PAYEE_NAME 
     */ 	
	private String payeeName;
    /**
     * 收款人开户机构名 登记卡bin行号对照表中的行名       db_column: PAYEE_ORG_NAME 
     */ 	
	private String payeeOrgName;
    /**
     * 收款开户行号       db_column: PAYEE_BANK_NO 
     */ 	
	private String payeeBankNo;
    /**
     * 收款开户行名       db_column: PAYEE_BANK_NAME 
     */ 	
	private String payeeBankName;
    /**
     * 币种       db_column: CCY 
     */ 	
	private String ccy;
    /**
     * 支付金额       db_column: TRANS_AMT 
     */ 	
	private java.math.BigDecimal transAmt;
    /**
     * 内部交易状态       db_column: TRANS_STAT 
     */ 	
	private String transStat;
    /**
     * 支付时间       db_column: TRANS_TIME 
     */ 	
	private java.util.Date transTime;
    /**
     * 最后变更时间       db_column: LAST_UPDATE_TIME 
     */ 	
	private java.util.Date lastUpdateTime;
    /**
     * 备用1       db_column: REMARK1 
     */ 	
	private String remark1;
    /**
     * 备用2       db_column: REMARK2 
     */ 	
	private String remark2;
    /**
     * 备用3       db_column: REMARK3 
     */ 	
	private String remark3;
    /**
     * 支付手续费       db_column: FEE_AMT 
     */ 	
	private java.math.BigDecimal feeAmt;
    /**
     * 支付流水号，与订单流水表的明细流水对应       db_column: TRANS_SUB_SEQ 
     */ 	
	private String transSubSeq;
    /**
     * 商户交易日期       db_column: MER_TRANS_DATE 
     */ 	
	private java.util.Date merTransDate;
    /**
     * 商户 交易流水号       db_column: MER_TRANS_SEQ 
     */ 	
	private String merTransSeq;
    /**
     * 核心交易机构号       db_column: BRCH_NO 
     */ 	
	private String brchNo;
    /**
     * 核心交易柜员号       db_column: TELLER_NO 
     */ 	
	private String tellerNo;
    /**
     * 转账类型 01电信转账 02微信转账       db_column: TRANSFER_TYPE 
     */ 	
	private String transferType;
    /**
     * 手续费账户类型       db_column: FEE_ACCT_TYPE 
     */ 	
	private String feeAcctType;
    /**
     * 手续费账户姓名       db_column: FEE_ACCT_NAME 
     */ 	
	private String feeAcctName;
    /**
     * 手续费账户       db_column: FEE_ACCT_NO 
     */ 	
	private String feeAcctNo;
    /**
     * 转账批次号用于标识一个批次转账       db_column: TRF_BATCH_NO 
     */ 	
	private String trfBatchNo;
    /**
     * 转账批次号用于标识一个批次转账       db_column: TRF_SEQ 
     */ 	
	private Integer trfSeq;
    /**
     * 分布式业务处理或多线程业务处理批次号       db_column: DIS_BATCH_NO 
     */ 	
	private String disBatchNo;
    /**
     * 交易标志  1：代扣  2：代付       db_column: TRANS_FLAG 
     */ 	
	private String transFlag;
    /**
     * 银行编号       db_column: BANK_ID 
     */ 	
	private String bankId;
    /**
     * 证件类型       db_column: CERT_TYPE 
     */ 	
	private String certType;
    /**
     * 证件号       db_column: CERT_NO 
     */ 	
	private String certNo;
    /**
     * 手机号       db_column: CERT_MOBILE 
     */ 	
	private String certMobile;
    /**
     * 姓名       db_column: CERT_NAME 
     */ 	
	private String certName;
    /**
     * 渠道：12柜台 06网银       db_column: CHNLID 
     */ 	
	private String chnlid;
    /**
     * 用于批量框架处理多线程数据不一致问题 N:代表未处理 Y:代表已处理       db_column: PROCESS 
     */ 	
	private String process;
	//columns END


	

	
	public String getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo(String value) {
		this.orderNo = value;
	}
	
	
	public String getOrderDes() {
		return this.orderDes;
	}
	
	public void setOrderDes(String value) {
		this.orderDes = value;
	}
	
	
	public String getMerNo() {
		return this.merNo;
	}
	
	public void setMerNo(String value) {
		this.merNo = value;
	}
	
	
	public String getSecMerNo() {
		return this.secMerNo;
	}
	
	public void setSecMerNo(String value) {
		this.secMerNo = value;
	}
	
	
	public java.util.Date getSysDate() {
		return this.sysDate;
	}
	
	public void setSysDate(java.util.Date value) {
		this.sysDate = value;
	}
	
	
	public String getRouteCode() {
		return this.routeCode;
	}
	
	public void setRouteCode(String value) {
		this.routeCode = value;
	}
	
	
	public String getPayUserId() {
		return this.payUserId;
	}
	
	public void setPayUserId(String value) {
		this.payUserId = value;
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
	
	
	public String getPayerName() {
		return this.payerName;
	}
	
	public void setPayerName(String value) {
		this.payerName = value;
	}
	
	
	public String getPayerOrgName() {
		return this.payerOrgName;
	}
	
	public void setPayerOrgName(String value) {
		this.payerOrgName = value;
	}
	
	
	public String getPayerBankNo() {
		return this.payerBankNo;
	}
	
	public void setPayerBankNo(String value) {
		this.payerBankNo = value;
	}
	
	
	public String getPayerBankName() {
		return this.payerBankName;
	}
	
	public void setPayerBankName(String value) {
		this.payerBankName = value;
	}
	
	
	public String getPayeeAcctType() {
		return this.payeeAcctType;
	}
	
	public void setPayeeAcctType(String value) {
		this.payeeAcctType = value;
	}
	
	
	public String getPayeeAcctNo() {
		return this.payeeAcctNo;
	}
	
	public void setPayeeAcctNo(String value) {
		this.payeeAcctNo = value;
	}
	
	
	public String getPayeeName() {
		return this.payeeName;
	}
	
	public void setPayeeName(String value) {
		this.payeeName = value;
	}
	
	
	public String getPayeeOrgName() {
		return this.payeeOrgName;
	}
	
	public void setPayeeOrgName(String value) {
		this.payeeOrgName = value;
	}
	
	
	public String getPayeeBankNo() {
		return this.payeeBankNo;
	}
	
	public void setPayeeBankNo(String value) {
		this.payeeBankNo = value;
	}
	
	
	public String getPayeeBankName() {
		return this.payeeBankName;
	}
	
	public void setPayeeBankName(String value) {
		this.payeeBankName = value;
	}
	
	
	public String getCcy() {
		return this.ccy;
	}
	
	public void setCcy(String value) {
		this.ccy = value;
	}
	
	
	public java.math.BigDecimal getTransAmt() {
		return this.transAmt;
	}
	
	public void setTransAmt(java.math.BigDecimal value) {
		this.transAmt = value;
	}
	
	
	public String getTransStat() {
		return this.transStat;
	}
	
	public void setTransStat(String value) {
		this.transStat = value;
	}
	
	
	public java.util.Date getTransTime() {
		return this.transTime;
	}
	
	public void setTransTime(java.util.Date value) {
		this.transTime = value;
	}
	
	
	public java.util.Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	public void setLastUpdateTime(java.util.Date value) {
		this.lastUpdateTime = value;
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
	
	
	public String getRemark3() {
		return this.remark3;
	}
	
	public void setRemark3(String value) {
		this.remark3 = value;
	}
	
	
	public java.math.BigDecimal getFeeAmt() {
		return this.feeAmt;
	}
	
	public void setFeeAmt(java.math.BigDecimal value) {
		this.feeAmt = value;
	}
	
	
	public String getTransSubSeq() {
		return this.transSubSeq;
	}
	
	public void setTransSubSeq(String value) {
		this.transSubSeq = value;
	}
	
	
	public java.util.Date getMerTransDate() {
		return this.merTransDate;
	}
	
	public void setMerTransDate(java.util.Date value) {
		this.merTransDate = value;
	}
	
	
	public String getMerTransSeq() {
		return this.merTransSeq;
	}
	
	public void setMerTransSeq(String value) {
		this.merTransSeq = value;
	}
	
	
	public String getBrchNo() {
		return this.brchNo;
	}
	
	public void setBrchNo(String value) {
		this.brchNo = value;
	}
	
	
	public String getTellerNo() {
		return this.tellerNo;
	}
	
	public void setTellerNo(String value) {
		this.tellerNo = value;
	}
	
	
	public String getTransferType() {
		return this.transferType;
	}
	
	public void setTransferType(String value) {
		this.transferType = value;
	}
	
	
	public String getFeeAcctType() {
		return this.feeAcctType;
	}
	
	public void setFeeAcctType(String value) {
		this.feeAcctType = value;
	}
	
	
	public String getFeeAcctName() {
		return this.feeAcctName;
	}
	
	public void setFeeAcctName(String value) {
		this.feeAcctName = value;
	}
	
	
	public String getFeeAcctNo() {
		return this.feeAcctNo;
	}
	
	public void setFeeAcctNo(String value) {
		this.feeAcctNo = value;
	}
	
	
	public String getTrfBatchNo() {
		return this.trfBatchNo;
	}
	
	public void setTrfBatchNo(String value) {
		this.trfBatchNo = value;
	}
	
	
	public Integer getTrfSeq() {
		return this.trfSeq;
	}
	
	public void setTrfSeq(Integer value) {
		this.trfSeq = value;
	}
	
	
	public String getDisBatchNo() {
		return this.disBatchNo;
	}
	
	public void setDisBatchNo(String value) {
		this.disBatchNo = value;
	}
	
	
	public String getTransFlag() {
		return this.transFlag;
	}
	
	public void setTransFlag(String value) {
		this.transFlag = value;
	}
	
	
	public String getBankId() {
		return this.bankId;
	}
	
	public void setBankId(String value) {
		this.bankId = value;
	}
	
	
	public String getCertType() {
		return this.certType;
	}
	
	public void setCertType(String value) {
		this.certType = value;
	}
	
	
	public String getCertNo() {
		return this.certNo;
	}
	
	public void setCertNo(String value) {
		this.certNo = value;
	}
	
	
	public String getCertMobile() {
		return this.certMobile;
	}
	
	public void setCertMobile(String value) {
		this.certMobile = value;
	}
	
	
	public String getCertName() {
		return this.certName;
	}
	
	public void setCertName(String value) {
		this.certName = value;
	}
	
	
	public String getChnlid() {
		return this.chnlid;
	}
	
	public void setChnlid(String value) {
		this.chnlid = value;
	}
	
	
	public String getProcess() {
		return this.process;
	}
	
	public void setProcess(String value) {
		this.process = value;
	}
	

	

}

