package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月15日 下午7:27:51
 */
public class InitCoreBankParamDto extends BaseDto {
    
    private boolean txSNBindingNew;//绑定流水号
    
    private String transType;//交易类型
    private String payType;//支付方式
    private BigDecimal payAmount;//支付
    private String accType;//账户类型
    private String routeCode;//资金通道
    private String SuccessRouteCode;//成功的资金通道
    private String bankAccNo;//卡号
    
    private String validDate;//信用卡有效期
    private String totalFee;//交易金额（分） 
    private String amount;//支付金额
    /** 核心 */
    private String cvv2; //cvv2码
    private String validdate;//信用卡有效期
    private String tranCode;//交易代码(08001)(必输)
    private String machineDate;//自然日期(必输)
    private String machineTime;//自然时间(必输)
    private String bizDate;//业务日期(必输)
    private String bizSerialNo;//业务流水号(必输)
    private String time;
    private String routeSeq;//通道方交易流水
    
    private String bankCardNo;//银行卡号
    private String currency;//货币代码
    private String trantype;//交易类型
    private String charge;//手续费
    private String set_account;//内部账号
    private String mobile;
    
    
    
    /**中金2511(快捷支付)*/
    private String paymentNo;//中金支付流水号
    private String txSNBinding;//绑定流水号
    private String settlementFlag;//结算标识
    private String remark;//备注
    private String accountNumber;//账号
    private String identificationType;//证件类型
    private String cardType;//卡类型
    private String phoneNumber;//手机号码
    
    
    
    
    
    
    
    
    
    public String getValiddate() {
        return validdate;
    }
    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }
    public boolean isTxSNBindingNew() {
        return txSNBindingNew;
    }
    public void setTxSNBindingNew(boolean txSNBindingNew) {
        this.txSNBindingNew = txSNBindingNew;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getIdentificationType() {
        return identificationType;
    }
    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getTransType() {
        return transType;
    }
    public void setTransType(String transType) {
        this.transType = transType;
    }
    public String getBankAccNo() {
        return bankAccNo;
    }
    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }
    public String getBankCardNo() {
        return bankCardNo;
    }
    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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
    public String getCharge() {
        return charge;
    }
    public void setCharge(String charge) {
        this.charge = charge;
    }
    public String getSet_account() {
        return set_account;
    }
    public void setSet_account(String set_account) {
        this.set_account = set_account;
    }
    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
    public String getSuccessRouteCode() {
        return SuccessRouteCode;
    }
    public void setSuccessRouteCode(String successRouteCode) {
        SuccessRouteCode = successRouteCode;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public String getAccType() {
        return accType;
    }
    public void setAccType(String accType) {
        this.accType = accType;
    }
    public String getValidDate() {
        return validDate;
    }
    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }
    public String getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
    public String getCvv2() {
        return cvv2;
    }
    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }
    public String getTranCode() {
        return tranCode;
    }
    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getRouteSeq() {
        return routeSeq;
    }
    public void setRouteSeq(String routeSeq) {
        this.routeSeq = routeSeq;
    }
    public String getPaymentNo() {
        return paymentNo;
    }
    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getTxSNBinding() {
        return txSNBinding;
    }
    public void setTxSNBinding(String txSNBinding) {
        this.txSNBinding = txSNBinding;
    }
    public String getSettlementFlag() {
        return settlementFlag;
    }
    public void setSettlementFlag(String settlementFlag) {
        this.settlementFlag = settlementFlag;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
    
    
    
}
