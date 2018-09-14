package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

public class SmokeTransferAccountDataDto extends BaseDto {
	private String coreClrAcctNo;// 核心待清算账户
	private String coreTransAcctNo;// 核心资金池账户
	private String coreRouteCode;// 核心资金通道
	private String coreBankName;// 核心银行名称
	private String coreBankNo;// 核心银行行号
	private String coreAcctType;// 核心资金池账户 清算账户类型
	private String coreClrType;// 核心清算方式
	private String coreName;// 核心名称
	private String coreUserId;// 核心userid

	private String payerAccNo;// 付款账号
	private String payerName;// 付款户名
	private String payerOrgName;// 付款账户机构名
	private String payerBankNo;// 付款账户行号
	private String payerBankName;// 付款账户行名
	private String payerUserId;// 付款用户id
	private String payerAccType;// 付款账户类型

	private String payeeAccNo;// 收款账号
	private String payeeAccType;// 收款账户类型
	private String payeeName;// 收款账户名
	private String payeeOrgName;// 收款账户机构名
	private String payeeBankNo;// 收款账户行号
	private String payeeBankName;// 收款账户行号
	private String payeeCardType;//收款卡号类型

	private String orderName;// 订单名称
	private String orderDes;// 订单描述
	private String transComments;
	private String routeCode;// 资金通道
	private String transType;// 交易类型
	private String vAcctNo;// 虚拟账号
	private String stlAcctType;// 结算方式
	private String certName;// 证件姓名
	
	private String trantype;//核心的交易类型
	private String setAccount;//核心的记账账号
	private String bankCardNo;//核心的记账账号
	
	//请求中金代付接口参数 
	private String sysSeq; //流水号
	private String paymentAccountName; //付款方姓名
	private String paymentAccountNumber;//付款方账号
	private String accountType;//账户类型 
	private String bankId; //银行编号
	private String vbindAcctNo;//收款方账户
	private String bankAccountNumber;//收款账户名称
	private String totalFee; //代付金额
	//中金流水参数
	private String zjAccType; //中金流水中付款方账户类型
	private String bankTxTime;//交易时间
	private String zjRouteCode;//中金通道代码 用于记录流水转换
	private String secondCoreAccType;//代付交易核心流水收款方账户类型
	private String secondCoreAccName;//代付交易核心流水收款方账户名称
	private String secondCorePayerAcctName;//代付交易核心流水付款方账户名称
	private String logoId;
	public String getSetAccount() {
		return setAccount;
	}

	public void setSetAccount(String setAccount) {
		this.setAccount = setAccount;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getPayeeCardType() {
		return payeeCardType;
	}

	public void setPayeeCardType(String payeeCardType) {
		this.payeeCardType = payeeCardType;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public String getTransComments() {
		return transComments;
	}

	public void setTransComments(String transComments) {
		this.transComments = transComments;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getPayerOrgName() {
		return payerOrgName;
	}

	public void setPayerOrgName(String payerOrgName) {
		this.payerOrgName = payerOrgName;
	}

	public String getPayerBankNo() {
		return payerBankNo;
	}

	public void setPayerBankNo(String payerBankNo) {
		this.payerBankNo = payerBankNo;
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName;
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getPayerAccType() {
		return payerAccType;
	}

	public void setPayerAccType(String payerAccType) {
		this.payerAccType = payerAccType;
	}

	public String getPayeeAccType() {
		return payeeAccType;
	}

	public void setPayeeAccType(String payeeAccType) {
		this.payeeAccType = payeeAccType;
	}

	public String getPayeeOrgName() {
		return payeeOrgName;
	}

	public void setPayeeOrgName(String payeeOrgName) {
		this.payeeOrgName = payeeOrgName;
	}

	public String getPayeeBankNo() {
		return payeeBankNo;
	}

	public void setPayeeBankNo(String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}

	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
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

	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getCoreUserId() {
		return coreUserId;
	}

	public void setCoreUserId(String coreUserId) {
		this.coreUserId = coreUserId;
	}

	public String getPayerAccNo() {
		return payerAccNo;
	}

	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayeeAccNo() {
		return payeeAccNo;
	}

	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getvAcctNo() {
		return vAcctNo;
	}

	public void setvAcctNo(String vAcctNo) {
		this.vAcctNo = vAcctNo;
	}

	public String getCoreClrType() {
		return coreClrType;
	}

	public void setCoreClrType(String coreClrType) {
		this.coreClrType = coreClrType;
	}

	public String getStlAcctType() {
		return stlAcctType;
	}

	public void setStlAcctType(String stlAcctType) {
		this.stlAcctType = stlAcctType;
	}

	public String getSysSeq() {
		return sysSeq;
	}

	public void setSysSeq(String sysSeq) {
		this.sysSeq = sysSeq;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getVbindAcctNo() {
		return vbindAcctNo;
	}

	public void setVbindAcctNo(String vbindAcctNo) {
		this.vbindAcctNo = vbindAcctNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getBankTxTime() {
		return bankTxTime;
	}

	public void setBankTxTime(String bankTxTime) {
		this.bankTxTime = bankTxTime;
	}

	public String getZjAccType() {
		return zjAccType;
	}

	public void setZjAccType(String zjAccType) {
		this.zjAccType = zjAccType;
	}

	public String getZjRouteCode() {
		return zjRouteCode;
	}

	public void setZjRouteCode(String zjRouteCode) {
		this.zjRouteCode = zjRouteCode;
	}

	public String getSecondCoreAccType() {
		return secondCoreAccType;
	}

	public void setSecondCoreAccType(String secondCoreAccType) {
		this.secondCoreAccType = secondCoreAccType;
	}

	public String getSecondCoreAccName() {
		return secondCoreAccName;
	}

	public void setSecondCoreAccName(String secondCoreAccName) {
		this.secondCoreAccName = secondCoreAccName;
	}

	public String getSecondCorePayerAcctName() {
		return secondCorePayerAcctName;
	}

	public void setSecondCorePayerAcctName(String secondCorePayerAcctName) {
		this.secondCorePayerAcctName = secondCorePayerAcctName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}


	

	

	// private String operatorType;//操作类型 0：商户提现到待清算账户 1：待清算账户转到二级商户银行卡上

}
