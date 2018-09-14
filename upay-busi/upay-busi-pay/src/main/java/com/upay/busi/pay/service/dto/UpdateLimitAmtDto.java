/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年10月14日
 */
public class UpdateLimitAmtDto extends BaseDto {

    private String feeInOrUp;//new更新限额，update修改限额
    
    private String merTransCtrlCode;//商户权限控制代码
    private BigDecimal transAmt;//支付金额
    private String merNo;//商户号
    private String accType;//账户类型
    private String payType;//支付方式
    private String bankAccType;//支付卡类型
    
    private String userLevel;//用户认证等级
    private String dcFlag;//账户借贷标志
    private String sysLmtType;//账户系统限额配置表，限额类型
    private String lmtAccountFlag;//限额交易累计表，累计限额类型 
    private String orderNo;//订单号
    private String accNo;//账户号
    private String sysLmtId;//系统限额序号
    private String orderStat;//订单状态
    private Map<String,Boolean> accLmtMap;
    
    
    
    public String getOrderStat() {
        return orderStat;
    }
    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }
    public Map<String, Boolean> getAccLmtMap() {
        return accLmtMap;
    }
    public void setAccLmtMap(Map<String, Boolean> accLmtMap) {
        this.accLmtMap = accLmtMap;
    }
    public String getSysLmtId() {
        return sysLmtId;
    }
    public void setSysLmtId(String sysLmtId) {
        this.sysLmtId = sysLmtId;
    }
    public String getFeeInOrUp() {
        return feeInOrUp;
    }
    public void setFeeInOrUp(String feeInOrUp) {
        this.feeInOrUp = feeInOrUp;
    }
    public String getMerTransCtrlCode() {
        return merTransCtrlCode;
    }
    public void setMerTransCtrlCode(String merTransCtrlCode) {
        this.merTransCtrlCode = merTransCtrlCode;
    }
    
    public BigDecimal getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
    public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    public String getAccType() {
        return accType;
    }
    public void setAccType(String accType) {
        this.accType = accType;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getBankAccType() {
        return bankAccType;
    }
    public void setBankAccType(String bankAccType) {
        this.bankAccType = bankAccType;
    }
    public String getUserLevel() {
        return userLevel;
    }
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
    public String getDcFlag() {
        return dcFlag;
    }
    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }
    public String getSysLmtType() {
        return sysLmtType;
    }
    public void setSysLmtType(String sysLmtType) {
        this.sysLmtType = sysLmtType;
    }
    public String getLmtAccountFlag() {
        return lmtAccountFlag;
    }
    public void setLmtAccountFlag(String lmtAccountFlag) {
        this.lmtAccountFlag = lmtAccountFlag;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getAccNo() {
        return accNo;
    }
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    
    
    
    
}
