/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;


/**
 * 订单详情查询
 * 
 * @author zhanggr
 * 
 */
public class PayOrderDetailInfoQryDto extends BaseDto {
    private String orderNo; // 支付订单号
    private String orderName; // 订单名称
    private BigDecimal transAmt; // 交易金额
    private String orderStat;// 订单状态
    private String merOrderDate;// 商户订单日期
    private String merSeq;// 商户流水号
    private String merNo;// 商户号
    private String secMerNo;// 二级商户号
    private String merOrderNo;// 商户订单号
    private String merOrderTime;// 商户订单时间
    private String merOrderEndTime;// 商户订单失效时间
    private String orderType;// 订单类型
    private String payType;// 支付方式
    private String orderDes;// 订单描述
    private String orderTransCode;// 订单交易码
    private String orderCreateTime;// 订单创建时间
    private String orderValidTime;// 订单有效时间（分）
    private String orderCurr;// 币种
    private int orderLmtTime;// 订单时效


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public int getOrderLmtTime() {
        return orderLmtTime;
    }


    public void setOrderLmtTime(int orderLmtTime) {
        this.orderLmtTime = orderLmtTime;
    }


    public String getOrderNo() {
        return orderNo;
    }


    public String getOrderName() {
        return orderName;
    }


    public String getOrderStat() {
        return orderStat;
    }


    public String getMerOrderDate() {
        return merOrderDate;
    }


    public String getMerSeq() {
        return merSeq;
    }


    public String getMerNo() {
        return merNo;
    }


    public String getSecMerNo() {
        return secMerNo;
    }


    public String getMerOrderNo() {
        return merOrderNo;
    }


    public String getMerOrderTime() {
        return merOrderTime;
    }


    public String getMerOrderEndTime() {
        return merOrderEndTime;
    }


    public String getOrderType() {
        return orderType;
    }


    public String getPayType() {
        return payType;
    }


    public String getOrderDes() {
        return orderDes;
    }


    public String getOrderTransCode() {
        return orderTransCode;
    }


    public String getOrderCreateTime() {
        return orderCreateTime;
    }


    public String getOrderValidTime() {
        return orderValidTime;
    }


    public String getOrderCurr() {
        return orderCurr;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }


    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }


    public void setMerOrderDate(String merOrderDate) {
        this.merOrderDate = merOrderDate;
    }


    public void setMerSeq(String merSeq) {
        this.merSeq = merSeq;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }


    public void setMerOrderNo(String merOrderNo) {
        this.merOrderNo = merOrderNo;
    }


    public void setMerOrderTime(String merOrderTime) {
        this.merOrderTime = merOrderTime;
    }


    public void setMerOrderEndTime(String merOrderEndTime) {
        this.merOrderEndTime = merOrderEndTime;
    }


    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public void setPayType(String payType) {
        this.payType = payType;
    }


    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }


    public void setOrderTransCode(String orderTransCode) {
        this.orderTransCode = orderTransCode;
    }


    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }


    public void setOrderValidTime(String orderValidTime) {
        this.orderValidTime = orderValidTime;
    }


    public void setOrderCurr(String orderCurr) {
        this.orderCurr = orderCurr;
    }

}
