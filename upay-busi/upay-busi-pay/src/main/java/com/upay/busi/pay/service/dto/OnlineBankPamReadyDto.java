package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class OnlineBankPamReadyDto extends BaseDto {
	private String payeeAccountType;//收款方卡号类型  为网银单独添加
	/** 订单类型 */
	private String orderType;
	/** 订单交易类型 */
	private String transType;
	/** 订单名称 */
	private String orderName;

	private String acountName;
	
	private String payerAcctNo;//	 付款人银行卡号  
	private String payerAcctName;//	付款人证件姓名;
	private String payerCertNo;//	付款人证件号码  
	private String payerMobile;//	付款人手机号码  
	private String payerBranchName;//	付款人
	private String payerProvince;//	付款人
	private String payerCity;//	付款人
	private String payerEmail;//	付款人
	
	
	private String payeeAcctNo;//	收款人银行卡号  
	private String payeeAcctName;//	收款人证件姓名
	private String payeeCertNo;//	收款人证件号码  
	private String payeeMobile;//	收款人手机号码  


	private String routeCode;
	private String totalFee;//分
	private String amount;//
	private String orderDes;// 订单描述
	private String routeType;//资金通道
	
	private String paymentAccountName;//备用金账户名称
	private String paymentAccountType;//备用金账户类型
	private String paymentAccountNumber;//中金备用金账户
	private String bankTxTime;//中金（银行处理时间）

	private String coreClrAcctNo;// 核心待清算账户
	private String coreTransAcctNo;// 核心资金池账户
	private String coreRouteCode;// 核心资金通道
	private String coreBankName;// 核心银行名称
	private String coreBankNo;// 核心银行行号
	private String coreAcctType;// 核心资金池账户 清算账户类型
	private String coreClrType;// 核心清算方式
	private String coreName;// 核心名称
	private String coreUserId;// 核心userid
	
	
	//当本行代扣对公账户时  ，记核心流水时需要加上这几个参数
    private String cupBankName;
    private String cnapsBankNo; 
    private String cardBinType;
    

	private String thirdTransAcctNo;// 第三方往来户
	private String thirdRouteCode;// 第三方资金通道
	private String thirdBankName;// 第三方银行名称
	private String thirdBankNo;// 第三方银行行号
	private String thirdAcctType;// 第三方心资金池账户 清算账户类型
	private String thirdClrType;// 第三方清算方式
	private String thirdName;// 第三方名称
	private String thirdUserId;// 三方userid
	
	//核心记账接口（08001）参数
	private String machineDate;// 自然日期(必输)
	private String machineTime;// 自然时间(必输)
	private String bizDate;// 业务日期(必输)
	private String bizSerialNo;// 业务流水号(必输)
	private String currency;// 货币代码
	private String trantype;// 核心交易类型
	private String bankCardNo;
	private String setAccount;
	
	//中金代收接口（2011）参数
	private String logoId;
	private String accountType ;
	private String validDate ;
	private String cvn2 ;
	private String accountName ;
	private String accountNumber ;
	private String branchName ;
	private String province ;
	private String city ;
	private String email ;
	private String routeCertType ;
	private String certNo ;
	private String note ;
	private String contractUserID ;
	private String phoneNumber ;
	private String settlementFlag ;
	private String cardMediaType ;
	
	

	

	public String getPayeeAccountType() {
		return payeeAccountType;
	}

	public void setPayeeAccountType(String payeeAccountType) {
		this.payeeAccountType = payeeAccountType;
	}

	public String getPayerBranchName() {
		return payerBranchName;
	}

	public void setPayerBranchName(String payerBranchName) {
		this.payerBranchName = payerBranchName;
	}

	public String getPayerProvince() {
		return payerProvince;
	}

	public void setPayerProvince(String payerProvince) {
		this.payerProvince = payerProvince;
	}

	public String getPayerCity() {
		return payerCity;
	}

	public void setPayerCity(String payerCity) {
		this.payerCity = payerCity;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getAcountName() {
		return acountName;
	}

	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}

	public String getPayerAcctNo() {
		return payerAcctNo;
	}

	public void setPayerAcctNo(String payerAcctNo) {
		this.payerAcctNo = payerAcctNo;
	}

	public String getPayerAcctName() {
		return payerAcctName;
	}

	public void setPayerAcctName(String payerAcctName) {
		this.payerAcctName = payerAcctName;
	}

	public String getPayerCertNo() {
		return payerCertNo;
	}

	public void setPayerCertNo(String payerCertNo) {
		this.payerCertNo = payerCertNo;
	}

	public String getPayerMobile() {
		return payerMobile;
	}

	public void setPayerMobile(String payerMobile) {
		this.payerMobile = payerMobile;
	}

	public String getPayeeAcctNo() {
		return payeeAcctNo;
	}

	public void setPayeeAcctNo(String payeeAcctNo) {
		this.payeeAcctNo = payeeAcctNo;
	}

	public String getPayeeAcctName() {
		return payeeAcctName;
	}

	public void setPayeeAcctName(String payeeAcctName) {
		this.payeeAcctName = payeeAcctName;
	}

	public String getPayeeCertNo() {
		return payeeCertNo;
	}

	public void setPayeeCertNo(String payeeCertNo) {
		this.payeeCertNo = payeeCertNo;
	}

	public String getPayeeMobile() {
		return payeeMobile;
	}

	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderDes() {
		return orderDes;
	}

	public void setOrderDes(String orderDes) {
		this.orderDes = orderDes;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getPaymentAccountName() {
		return paymentAccountName;
	}

	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}

	public String getPaymentAccountType() {
		return paymentAccountType;
	}

	public void setPaymentAccountType(String paymentAccountType) {
		this.paymentAccountType = paymentAccountType;
	}

	public String getPaymentAccountNumber() {
		return paymentAccountNumber;
	}

	public void setPaymentAccountNumber(String paymentAccountNumber) {
		this.paymentAccountNumber = paymentAccountNumber;
	}

	public String getBankTxTime() {
		return bankTxTime;
	}

	public void setBankTxTime(String bankTxTime) {
		this.bankTxTime = bankTxTime;
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

	public String getCoreUserId() {
		return coreUserId;
	}

	public void setCoreUserId(String coreUserId) {
		this.coreUserId = coreUserId;
	}

	public String getCupBankName() {
		return cupBankName;
	}

	public void setCupBankName(String cupBankName) {
		this.cupBankName = cupBankName;
	}

	public String getCnapsBankNo() {
		return cnapsBankNo;
	}

	public void setCnapsBankNo(String cnapsBankNo) {
		this.cnapsBankNo = cnapsBankNo;
	}

	public String getCardBinType() {
		return cardBinType;
	}

	public void setCardBinType(String cardBinType) {
		this.cardBinType = cardBinType;
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

	public String getThirdUserId() {
		return thirdUserId;
	}

	public void setThirdUserId(String thirdUserId) {
		this.thirdUserId = thirdUserId;
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

	public String getBizSerialNo() {
		return bizSerialNo;
	}

	public void setBizSerialNo(String bizSerialNo) {
		this.bizSerialNo = bizSerialNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getSetAccount() {
		return setAccount;
	}

	public void setSetAccount(String setAccount) {
		this.setAccount = setAccount;
	}

	public String getLogoId() {
		return logoId;
	}

	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRouteCertType() {
		return routeCertType;
	}

	public void setRouteCertType(String routeCertType) {
		this.routeCertType = routeCertType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getContractUserID() {
		return contractUserID;
	}

	public void setContractUserID(String contractUserID) {
		this.contractUserID = contractUserID;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSettlementFlag() {
		return settlementFlag;
	}

	public void setSettlementFlag(String settlementFlag) {
		this.settlementFlag = settlementFlag;
	}

	public String getCardMediaType() {
		return cardMediaType;
	}

	public void setCardMediaType(String cardMediaType) {
		this.cardMediaType = cardMediaType;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
