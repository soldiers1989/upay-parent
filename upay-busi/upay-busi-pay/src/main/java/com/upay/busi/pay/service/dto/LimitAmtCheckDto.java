package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月2日 下午3:04:47
 */
public class LimitAmtCheckDto extends BaseDto {
    private String merTransCtrlCode;//商户权限控制代码
    private BigDecimal transAmt;//支付金额
    private String routeCode;//资金通道
    private String merNo;//商户号
    private String accType;//账户类型
    private String payType;//支付方式
    private String bankAccType;//支付卡类型
    private String checkUserLmt;//是否进行账户限额   Y 是，N 否
    
    private String userLevel;//用户认证等级
    private String dcFlag;//账户借贷标志
    private String sysLmtType;//账户系统限额配置表，限额类型
    private String lmtAccountFlag;//限额交易累计表，累计限额类型 
    private String orderNo;//订单号
    private String accNo;//账户号
    
    private Map<String,Boolean> accLmtMap;
    private String sysLmtId;
    private String transType;
    private String payServicType;//支付服务类型

    
    
    
    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getCheckUserLmt() {
        return checkUserLmt;
    }

    public void setCheckUserLmt(String checkUserLmt) {
        this.checkUserLmt = checkUserLmt;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
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

    

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLmtAccountFlag() {
        return lmtAccountFlag;
    }

    public void setLmtAccountFlag(String lmtAccountFlag) {
        this.lmtAccountFlag = lmtAccountFlag;
    }

    public String getSysLmtType() {
        return sysLmtType;
    }

    public void setSysLmtType(String sysLmtType) {
        this.sysLmtType = sysLmtType;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public String getDcFlag() {
        return dcFlag;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public void setDcFlag(String dcFlag) {
        this.dcFlag = dcFlag;
    }

    public String getBankAccType() {
        return bankAccType;
    }

    public void setBankAccType(String bankAccType) {
        this.bankAccType = bankAccType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

   

    public String getMerTransCtrlCode() {
        return merTransCtrlCode;
    }

    public void setMerTransCtrlCode(String merTransCtrlCode) {
        this.merTransCtrlCode = merTransCtrlCode;
    }
    
    

}
