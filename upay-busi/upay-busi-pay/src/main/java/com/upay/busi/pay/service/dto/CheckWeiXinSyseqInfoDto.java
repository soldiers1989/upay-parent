/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * 根据订单号和资金通道判断微信流水是否已经登记
 * @author zhanggr
 *
 */
public class CheckWeiXinSyseqInfoDto extends BaseDto {
    private String orderNo;//订单号
    private String routeCode;//资金通道
    private String registFlag;//登记标识
    
    private String merNo;//商户号
    private BigDecimal transAmt;//交易金额
    private String transSubSeq;//交易流水
    private java.lang.String orderDes;//订单描述
    private java.lang.String payUserId;//付款用户
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getRouteCode() {
        return routeCode;
    }
    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
    public String getRegistFlag() {
        return registFlag;
    }
    public void setRegistFlag(String registFlag) {
        this.registFlag = registFlag;
    }
    public String getMerNo() {
        return merNo;
    }
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }
    public BigDecimal getTransAmt() {
        return transAmt;
    }
    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }
    public String getTransSubSeq() {
        return transSubSeq;
    }
    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }
    public java.lang.String getOrderDes() {
        return orderDes;
    }
    public void setOrderDes(java.lang.String orderDes) {
        this.orderDes = orderDes;
    }
    public java.lang.String getPayUserId() {
        return payUserId;
    }
    public void setPayUserId(java.lang.String payUserId) {
        this.payUserId = payUserId;
    }
    
}
