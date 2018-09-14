package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.upay.commons.dto.BaseDto;

/**
 * @author zhangjianfeng
 * @since 2016/11/25 21:38
 */
public class InitRouteSingleCollectionDto extends BaseDto {
	private String merNo;//商户号
	
	private String acctNo;//付款方
	private String acctType;//付款方卡类型
	private String acctName;//付款方姓名
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

	
	private String payeeAccountNo;//收款人账号
	private String payeeAccountType;//收款人账号类型
	private String payeeAccountName;//收款人账号名称
	private String payeeCertNo;//收款人身份证号
	private String payeeMobile;//收款人手机号码


	private String payRouteMethod;//具体支付渠道:  代付：0001:银联代付0002:中金代付    --0003:银联代收0004:银联无跳转支付0005:中金代收
	public String getPayRouteMethod() {
		return payRouteMethod;
	}
	public void setPayRouteMethod(String payRouteMethod) {
		this.payRouteMethod = payRouteMethod;
	}
	
	
	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}

	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}

	public String getPayeeAccountType() {
		return payeeAccountType;
	}

	public void setPayeeAccountType(String payeeAccountType) {
		this.payeeAccountType = payeeAccountType;
	}

	public String getPayeeAccountName() {
		return payeeAccountName;
	}

	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
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

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
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

	public String getBankTxTime() {
		return bankTxTime;
	}

	public void setBankTxTime(String bankTxTime) {
		this.bankTxTime = bankTxTime;
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

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
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

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
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

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
}
