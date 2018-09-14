package com.upay.busi.acc.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

public class QueryRouteByBindCardNoDto extends BaseDto {
	private String payeeMobile;//转账的手机号
	private String payeeComMail;//转账户的企业邮箱
	private String payeeUserName;//转账户的用户名
	private String payerTransferUserId;
	// 输入
	
	private String cardBin; // 卡BIN
	private String certName;// 证件姓名

	// 输出
	private String paymentAccountName;//备用金账户名称
	private String paymentAccountType;//备用金账户类型
	private String paymentAccountNumber;//中金备用金账户
	
	private String vbindAcctNo;// 绑定的卡号
	private String bindAcctBankName;//绑定卡银行名称
	private String bindAcctBankNo;//绑定卡银行号
	private String bindRouteCode;//绑定卡的资金通道
	private String bindOrgName;//绑定卡银行机构名称
	private String bindAcctType;//绑定卡的类型  11借记卡  12贷记卡
	private String bindUserId;//绑定卡的userID
	private String bindName;//绑卡人姓名
	
	private String coreClrAcctNo;//核心待清算账户
	private String coreTransAcctNo;//核心资金池账户
	private String coreRouteCode;//核心资金通道
	private String coreBankName;//核心银行名称
	private String coreBankNo;//核心银行行号
	private String coreAcctType;//核心资金池账户 清算账户类型
	private String coreClrType;//核心清算方式
	private String coreName;//核心名称
	private String coreUserId;//核心userid
	
	private String thirdTransAcctNo;//第三方往来户
	private String thirdRouteCode;//第三方资金通道
	private String thirdBankName;//第三方银行名称
	private String thirdBankNo;//第三方银行行号
	private String thirdAcctType;//第三方心资金池账户 清算账户类型
	private String thirdClrType;//第三方清算方式
	private String thirdName;//第三方名称
	private String thirdUserId;//三方userid
	
	private String vAcctNo;// 虚拟账号
	private String payerUser;//付款人userId
	private String payerName;//转账户付款方的姓名
	private String payeeVacctNo;//转账时收款方的虚拟账号
	private String payeeName;//转账时收款方的姓名
	private String payeeUserId;//收款人用户ID
	private String thisRouteCode;//平台资金通道
	private String thisBankName;//平台银行名称
	private String thisBankNo;//平台银行行号
	private String thisAcctType;//平台账户类型  01虚拟账户  
	private String thisClrType;//平台清算方式
	
	
	private String transDate;// 交易时间
	private String transferPayerAcctNo;//转账付款人账号
	private BigDecimal transAmt;// 交易金额
	private String machineDate;// 调核心接口传入日期
	private String machineTime;// 调核心接口传入时间
	private String bizDate;

	
	private String routeType;//支付方式
	
	private String routeCode;// 资金通道
	private String routeFuncCode;// 功能代码 鉴仅 充值 提现
	private int orderLmtTime;// 订单等待支付时间 0：即时支付 >0：允许延时支付（分）

	private String orderName;
	private String transComments;// 交易说明

	private String bankId;// 银行ID
	

//	private String payerAccNo;// 付款账号
//	private String payerName;// 付款户名
//	private String payerOrgName;// 付款账户机构名
//	private String payerBankNo;// 付款账户行号
//	private String payerBankName;// 付款账户行名
//	private String payerUserId;// 付款用户id
//	private String payerAccType;// 付款账户类型
//
//	private String payeeAccNo;// 收款账号
//	private String payeeAccType;// 收款账户类型
//	private String payeeName;// 收款账户名
//	private String payeeOrgName;// 收款账户机构名
//	private String payeeBankNo;// 收款账户行号
//	private String payeeBankName;// 收款账户行号
//	private String clrType;//清算类型

	// 当是中金记账的时候需要记第二条，第二条是记录核心记账
//	private String routeCode2;//第二笔记录是核心流水资金通道
//	private String payerAccNo2;// 付款账号
//	private String payerName2;// 付款户名
//	private String payerOrgName2;// 付款账户机构名
//	private String payerBankNo2;// 付款账户行号
//	private String payerBankName2;// 付款账户行名
//	private String payerUserId2;// 付款用户id
//	private String payerAccType2;// 付款账户类型
//
//	private String payeeAccNo2;// 收款账号
//	private String payeeAccType2;// 收款账户类型
//	private String payeeName2;// 收款账户名
//	private String payeeOrgName2;// 收款账户机构名
//	private String payeeBankNo2;// 收款账户行号
//	private String payeeBankName2;// 收款账户行号
//	private String clrType2;//清算类型


	public String getPayeeUserName() {
		return payeeUserName;
	}

	public void setPayeeUserName(String payeeUserName) {
		this.payeeUserName = payeeUserName;
	}

	public String getTransferPayerAcctNo() {
		return transferPayerAcctNo;
	}

	public String getPayerTransferUserId() {
		return payerTransferUserId;
	}

	public void setPayerTransferUserId(String payerTransferUserId) {
		this.payerTransferUserId = payerTransferUserId;
	}

	public void setTransferPayerAcctNo(String transferPayerAcctNo) {
		this.transferPayerAcctNo = transferPayerAcctNo;
	}

	private BigDecimal payAmount;// 记录交易流水的交易金额
	private String orderDes;// 订单描述
	
//	private String paymentNo;//支付生成的流水号
	private String txSNBinding;//绑定卡时生成的流水号
	private String bankTxTime;//中金（银行处理时间）
	
	private String reserveMobile;//预留手机号
	
	private String transType;
	
	private String accType;//账户类型


	public String getPaymentAccountType() {
		return paymentAccountType;
	}

	public void setPaymentAccountType(String paymentAccountType) {
		this.paymentAccountType = paymentAccountType;
	}

	public String getPayerUser() {
		return payerUser;
	}

	public void setPayerUser(String payerUser) {
		this.payerUser = payerUser;
	}

	public String getPayeeUserId() {
		return payeeUserId;
	}

	public void setPayeeUserId(String payeeUserId) {
		this.payeeUserId = payeeUserId;
	}

	public String getCoreUserId() {
		return coreUserId;
	}

	public void setCoreUserId(String coreUserId) {
		this.coreUserId = coreUserId;
	}

	public String getThirdUserId() {
		return thirdUserId;
	}

	public void setThirdUserId(String thirdUserId) {
		this.thirdUserId = thirdUserId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPaymentAccountName() {
		return paymentAccountName;
	}

	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}

	public String getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	public void setPaymentAccountNumber(String paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getPayeeComMail() {
		return payeeComMail;
	}

	public void setPayeeComMail(String payeeComMail) {
		this.payeeComMail = payeeComMail;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getBankTxTime() {
		return bankTxTime;
	}

	public void setBankTxTime(String bankTxTime) {
		this.bankTxTime = bankTxTime;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getReserveMobile() {
		return reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}

//	public String getPaymentNo() {
//		return paymentNo;
//	}
//
//	public void setPaymentNo(String paymentNo) {
//		this.paymentNo = paymentNo;
//	}

	public String getTxSNBinding() {
		return txSNBinding;
	}

	public void setTxSNBinding(String txSNBinding) {
		this.txSNBinding = txSNBinding;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public String getVbindAcctNo() {
		return vbindAcctNo;
	}

	public void setVbindAcctNo(String vbindAcctNo) {
		this.vbindAcctNo = vbindAcctNo;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public BigDecimal getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(BigDecimal transAmt) {
		this.transAmt = transAmt;
	}

	public String getMachineDate() {
		return machineDate;
	}

	public void setMachineDate(String machineDate) {
		this.machineDate = machineDate;
	}

	public String getMachineTime() {
		return machineTime;
	}

	public void setMachineTime(String machineTime) {
		this.machineTime = machineTime;
	}

	public String getBizDate() {
		return bizDate;
	}

	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}

	public String getvAcctNo() {
		return vAcctNo;
	}

	public void setvAcctNo(String vAcctNo) {
		this.vAcctNo = vAcctNo;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getRouteFuncCode() {
		return routeFuncCode;
	}

	public void setRouteFuncCode(String routeFuncCode) {
		this.routeFuncCode = routeFuncCode;
	}

	public int getOrderLmtTime() {
		return orderLmtTime;
	}

	public void setOrderLmtTime(int orderLmtTime) {
		this.orderLmtTime = orderLmtTime;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getTransComments() {
		return transComments;
	}

	public void setTransComments(String transComments) {
		this.transComments = transComments;
	}
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBindAcctBankName() {
		return bindAcctBankName;
	}

	public void setBindAcctBankName(String bindAcctBankName) {
		this.bindAcctBankName = bindAcctBankName;
	}

	public String getBindAcctBankNo() {
		return bindAcctBankNo;
	}

	public void setBindAcctBankNo(String bindAcctBankNo) {
		this.bindAcctBankNo = bindAcctBankNo;
	}

	public String getBindRouteCode() {
		return bindRouteCode;
	}

	public void setBindRouteCode(String bindRouteCode) {
		this.bindRouteCode = bindRouteCode;
	}

	public String getBindOrgName() {
		return bindOrgName;
	}

	public void setBindOrgName(String bindOrgName) {
		this.bindOrgName = bindOrgName;
	}

	public String getBindAcctType() {
		return bindAcctType;
	}

	public void setBindAcctType(String bindAcctType) {
		this.bindAcctType = bindAcctType;
	}

	public String getBindUserId() {
		return bindUserId;
	}

	public void setBindUserId(String bindUserId) {
		this.bindUserId = bindUserId;
	}

	public String getBindName() {
		return bindName;
	}

	public void setBindName(String bindName) {
		this.bindName = bindName;
	}

	public String getCoreClrAcctNo() {
		return coreClrAcctNo;
	}

	public void setCoreClrAcctNo(String coreClrAcctNo) {
		this.coreClrAcctNo = coreClrAcctNo;
	}

	public String getCoreTransAcctNo() {
		return coreTransAcctNo;
	}

	public void setCoreTransAcctNo(String coreTransAcctNo) {
		this.coreTransAcctNo = coreTransAcctNo;
	}

	public String getCoreRouteCode() {
		return coreRouteCode;
	}

	public void setCoreRouteCode(String coreRouteCode) {
		this.coreRouteCode = coreRouteCode;
	}

	public String getCoreBankName() {
		return coreBankName;
	}

	public void setCoreBankName(String coreBankName) {
		this.coreBankName = coreBankName;
	}

	public String getCoreBankNo() {
		return coreBankNo;
	}

	public void setCoreBankNo(String coreBankNo) {
		this.coreBankNo = coreBankNo;
	}

	public String getCoreAcctType() {
		return coreAcctType;
	}

	public void setCoreAcctType(String coreAcctType) {
		this.coreAcctType = coreAcctType;
	}

	public String getCoreClrType() {
		return coreClrType;
	}

	public void setCoreClrType(String coreClrType) {
		this.coreClrType = coreClrType;
	}

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getThirdTransAcctNo() {
		return thirdTransAcctNo;
	}

	public void setThirdTransAcctNo(String thirdTransAcctNo) {
		this.thirdTransAcctNo = thirdTransAcctNo;
	}

	public String getThirdRouteCode() {
		return thirdRouteCode;
	}

	public void setThirdRouteCode(String thirdRouteCode) {
		this.thirdRouteCode = thirdRouteCode;
	}

	public String getThirdBankName() {
		return thirdBankName;
	}

	public void setThirdBankName(String thirdBankName) {
		this.thirdBankName = thirdBankName;
	}

	public String getThirdBankNo() {
		return thirdBankNo;
	}

	public void setThirdBankNo(String thirdBankNo) {
		this.thirdBankNo = thirdBankNo;
	}

	public String getThirdAcctType() {
		return thirdAcctType;
	}

	public void setThirdAcctType(String thirdAcctType) {
		this.thirdAcctType = thirdAcctType;
	}

	public String getThirdClrType() {
		return thirdClrType;
	}

	public void setThirdClrType(String thirdClrType) {
		this.thirdClrType = thirdClrType;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getPayeeVacctNo() {
		return payeeVacctNo;
	}

	public void setPayeeVacctNo(String payeeVacctNo) {
		this.payeeVacctNo = payeeVacctNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getThisRouteCode() {
		return thisRouteCode;
	}

	public void setThisRouteCode(String thisRouteCode) {
		this.thisRouteCode = thisRouteCode;
	}

	public String getThisBankName() {
		return thisBankName;
	}

	public void setThisBankName(String thisBankName) {
		this.thisBankName = thisBankName;
	}

	public String getThisBankNo() {
		return thisBankNo;
	}

	public void setThisBankNo(String thisBankNo) {
		this.thisBankNo = thisBankNo;
	}

	public String getThisAcctType() {
		return thisAcctType;
	}

	public void setThisAcctType(String thisAcctType) {
		this.thisAcctType = thisAcctType;
	}

	public String getThisClrType() {
		return thisClrType;
	}

	public void setThisClrType(String thisClrType) {
		this.thisClrType = thisClrType;
	}
	
}
