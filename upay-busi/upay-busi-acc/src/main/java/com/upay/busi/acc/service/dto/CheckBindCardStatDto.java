package com.upay.busi.acc.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月5日 下午4:21:21
 */
public class CheckBindCardStatDto extends BaseDto {
    private String accNo;//用户账号
    private String bankAccNo;//银行卡号
    private String payType;//支付方式
    private String routeCode;
    private String TxSNBinding;//中金绑定流水号
    
    private String bindAccType;//绑定账户类型
    private String bindBankFlag;//绑定账户银行类别
    private String bindBankCode;//绑定账户行号
    private String bindBankName;//绑定账户行名
    private String bindAccNo;//绑定账户账号
    private String bindOpenCode;//绑定账户开户机构号
    private String bindOpenName;//绑定账户开户行名
    private String cvv2;//信用卡cvv2码
    private String validDate;//信用卡有效期
    
    private String cardFistPay;//是否是首次支付：0是，1不是
    private String accType;//账户类型
    
    private String bankBinFlag;//行内外标志
    
    
    
    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getTxSNBinding() {
        return TxSNBinding;
    }

    public void setTxSNBinding(String txSNBinding) {
        TxSNBinding = txSNBinding;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getBankBinFlag() {
        return bankBinFlag;
    }

    public void setBankBinFlag(String bankBinFlag) {
        this.bankBinFlag = bankBinFlag;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getCardFistPay() {
        return cardFistPay;
    }

    public void setCardFistPay(String cardFistPay) {
        this.cardFistPay = cardFistPay;
    }

    public String getBindAccType() {
        return bindAccType;
    }

    public String getBindBankFlag() {
        return bindBankFlag;
    }

    public String getBindBankCode() {
        return bindBankCode;
    }

    public String getBindBankName() {
        return bindBankName;
    }

    public String getBindAccNo() {
        return bindAccNo;
    }

    public String getBindOpenCode() {
        return bindOpenCode;
    }

    public String getBindOpenName() {
        return bindOpenName;
    }

    public void setBindAccType(String bindAccType) {
        this.bindAccType = bindAccType;
    }

    public void setBindBankFlag(String bindBankFlag) {
        this.bindBankFlag = bindBankFlag;
    }

    public void setBindBankCode(String bindBankCode) {
        this.bindBankCode = bindBankCode;
    }

    public void setBindBankName(String bindBankName) {
        this.bindBankName = bindBankName;
    }

    public void setBindAccNo(String bindAccNo) {
        this.bindAccNo = bindAccNo;
    }

    public void setBindOpenCode(String bindOpenCode) {
        this.bindOpenCode = bindOpenCode;
    }

    public void setBindOpenName(String bindOpenName) {
        this.bindOpenName = bindOpenName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }
    
}
