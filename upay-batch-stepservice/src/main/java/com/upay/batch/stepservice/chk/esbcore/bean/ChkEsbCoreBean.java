package com.upay.batch.stepservice.chk.esbcore.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ChkEsbCoreBean {

    private Date transDate;//核心交易日期
    private String transSeq;//核心流水号
    private String transTeller;//交易柜员
    private String transOrg;//交易机构码
    
    private Date preTransDate;//前置交易日期
    private Date preTransTime;//前置交易时间
    private String preTransSeq;//前置交易流水号
    private String preDate;//前置日期
    
    private String clearDate;//清算日期
    private String oirPreTransSeq;//原前置交易流水号
    private String businessCore;//业务种类码
    private String operationFlag;//操作标志
    
    private String cardNo1;//卡号1
    private String account1;//账号1
    private String paymentCode1;//款项代码1
    private String cardAccountNumber1;//卡内帐户序号1
    private String cardpaymentCode1;//开户机构码1
    
    
    private String cardNo2;//卡号2
    private String account2;//账号2
    private String paymentCode2;//款项代码2
    private String cardAccountNumber2;//卡内帐户序号2
    private String cardpaymentCode2;//开户机构码2
    
    private String accrual;//发生额
    private String cny;//币种
    private BigDecimal amount2;//金额2
    private String cny2;//币种2
    
    private String channelNumber;//渠道编号
    private String typeDetail;//业务发生地类型描述
    private String cardTransType;//卡交易类型
    private String cardTransTypeDetail;//卡交易类型描述
    private String transType;//交易种类
    private String transTypeDetail;//交易种类描述
    
    private String merNumber;//商户代码
    private String merType;//商户类型
    private String deviceCode;//设备代码
    private String validFlag;//有效标志
    private String deviceTransSeq;//设备交易流水号
    private Date deviceTransDate;//设备传输日期时间
    private String deviceAcceptanceOrg;//设备受理行机构码
    private String deviceSendOrg;//设备发送行机构码
    private String requestSeq;//请求端流水
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransSeq() {
		return transSeq;
	}
	public void setTransSeq(String transSeq) {
		this.transSeq = transSeq;
	}
	public String getTransTeller() {
		return transTeller;
	}
	public void setTransTeller(String transTeller) {
		this.transTeller = transTeller;
	}
	public String getTransOrg() {
		return transOrg;
	}
	public void setTransOrg(String transOrg) {
		this.transOrg = transOrg;
	}
	public Date getPreTransDate() {
		return preTransDate;
	}
	public void setPreTransDate(Date preTransDate) {
		this.preTransDate = preTransDate;
	}
	public Date getPreTransTime() {
		return preTransTime;
	}
	public void setPreTransTime(Date preTransTime) {
		this.preTransTime = preTransTime;
	}
	public String getPreTransSeq() {
		return preTransSeq;
	}
	public void setPreTransSeq(String preTransSeq) {
		this.preTransSeq = preTransSeq;
	}
	public String getPreDate() {
		return preDate;
	}
	public void setPreDate(String preDate) {
		this.preDate = preDate;
	}
	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	public String getOirPreTransSeq() {
		return oirPreTransSeq;
	}
	public void setOirPreTransSeq(String oirPreTransSeq) {
		this.oirPreTransSeq = oirPreTransSeq;
	}
	public String getBusinessCore() {
		return businessCore;
	}
	public void setBusinessCore(String businessCore) {
		this.businessCore = businessCore;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public String getCardNo1() {
		return cardNo1;
	}
	public void setCardNo1(String cardNo1) {
		this.cardNo1 = cardNo1;
	}
	public String getAccount1() {
		return account1;
	}
	public void setAccount1(String account1) {
		this.account1 = account1;
	}
	public String getPaymentCode1() {
		return paymentCode1;
	}
	public void setPaymentCode1(String paymentCode1) {
		this.paymentCode1 = paymentCode1;
	}
	public String getCardAccountNumber1() {
		return cardAccountNumber1;
	}
	public void setCardAccountNumber1(String cardAccountNumber1) {
		this.cardAccountNumber1 = cardAccountNumber1;
	}
	public String getCardpaymentCode1() {
		return cardpaymentCode1;
	}
	public void setCardpaymentCode1(String cardpaymentCode1) {
		this.cardpaymentCode1 = cardpaymentCode1;
	}
	public String getCardNo2() {
		return cardNo2;
	}
	public void setCardNo2(String cardNo2) {
		this.cardNo2 = cardNo2;
	}
	public String getAccount2() {
		return account2;
	}
	public void setAccount2(String account2) {
		this.account2 = account2;
	}
	public String getPaymentCode2() {
		return paymentCode2;
	}
	public void setPaymentCode2(String paymentCode2) {
		this.paymentCode2 = paymentCode2;
	}
	public String getCardAccountNumber2() {
		return cardAccountNumber2;
	}
	public void setCardAccountNumber2(String cardAccountNumber2) {
		this.cardAccountNumber2 = cardAccountNumber2;
	}
	public String getCardpaymentCode2() {
		return cardpaymentCode2;
	}
	public void setCardpaymentCode2(String cardpaymentCode2) {
		this.cardpaymentCode2 = cardpaymentCode2;
	}
	public String getAccrual() {
		return accrual;
	}
	public void setAccrual(String accrual) {
		this.accrual = accrual;
	}
	public String getCny() {
		return cny;
	}
	public void setCny(String cny) {
		this.cny = cny;
	}
	public BigDecimal getAmount2() {
		return amount2;
	}
	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}
	public String getCny2() {
		return cny2;
	}
	public void setCny2(String cny2) {
		this.cny2 = cny2;
	}
	public String getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}
	public String getTypeDetail() {
		return typeDetail;
	}
	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}
	public String getCardTransType() {
		return cardTransType;
	}
	public void setCardTransType(String cardTransType) {
		this.cardTransType = cardTransType;
	}
	public String getCardTransTypeDetail() {
		return cardTransTypeDetail;
	}
	public void setCardTransTypeDetail(String cardTransTypeDetail) {
		this.cardTransTypeDetail = cardTransTypeDetail;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getTransTypeDetail() {
		return transTypeDetail;
	}
	public void setTransTypeDetail(String transTypeDetail) {
		this.transTypeDetail = transTypeDetail;
	}
	public String getMerNumber() {
		return merNumber;
	}
	public void setMerNumber(String merNumber) {
		this.merNumber = merNumber;
	}
	public String getMerType() {
		return merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getDeviceTransSeq() {
		return deviceTransSeq;
	}
	public void setDeviceTransSeq(String deviceTransSeq) {
		this.deviceTransSeq = deviceTransSeq;
	}
	public Date getDeviceTransDate() {
		return deviceTransDate;
	}
	public void setDeviceTransDate(Date deviceTransDate) {
		this.deviceTransDate = deviceTransDate;
	}
	public String getDeviceAcceptanceOrg() {
		return deviceAcceptanceOrg;
	}
	public void setDeviceAcceptanceOrg(String deviceAcceptanceOrg) {
		this.deviceAcceptanceOrg = deviceAcceptanceOrg;
	}
	public String getDeviceSendOrg() {
		return deviceSendOrg;
	}
	public void setDeviceSendOrg(String deviceSendOrg) {
		this.deviceSendOrg = deviceSendOrg;
	}
	public String getRequestSeq() {
		return requestSeq;
	}
	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}
    
    
    
    
    
}
