package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 退款异常处理DTO
 * @author zhangjianfeng
 * @since 2016/08/22 09:00
 */
public class RefundExceptionDto extends BaseDto {

    /** 商户号 */
    private String merNo;
    /** 二级商户号 */
    private String secMerNo;
    /** 原订单商户订单号 */
    private String oriMerOrderNo;
    /** 原订单支付平台订单号 */
    private String oriOrderNo;
    /** 退款商户订单号 */
    private String merRefundOrderNo;
    /** 支付平台退款订单号 */
    private String refundOrderNo;
    /** 需要异常处理的支付流水号 */
    private String transSubSeq;
    /** 资金通道代码 */
    private String routeCode;
    /** 通道支付结果标记，用于异常处理 */
    private String routeTransResultFlag;

    /** 是否订单异常处理：0-不需要异常处理；1-需要异常处理； */
    private String isOrderExceptionProc;
    /** 是否支付流水异常处理：0-不需要异常处理；1-需要异常处理； */
    private String isPayFlowExceptionProc;
    /** 订单号 = 支付平台退款订单号 */
    private String orderNo;
    /** 订单状态 */
    private String orderStat;
    /** 支付流水状态 */
    private String transStat;


    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getOriMerOrderNo() {
        return oriMerOrderNo;
    }

    public void setOriMerOrderNo(String oriMerOrderNo) {
        this.oriMerOrderNo = oriMerOrderNo;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    public String getMerRefundOrderNo() {
        return merRefundOrderNo;
    }

    public void setMerRefundOrderNo(String merRefundOrderNo) {
        this.merRefundOrderNo = merRefundOrderNo;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

    public String getIsOrderExceptionProc() {
        return isOrderExceptionProc;
    }

    public void setIsOrderExceptionProc(String isOrderExceptionProc) {
        this.isOrderExceptionProc = isOrderExceptionProc;
    }

    public String getIsPayFlowExceptionProc() {
        return isPayFlowExceptionProc;
    }

    public void setIsPayFlowExceptionProc(String isPayFlowExceptionProc) {
        this.isPayFlowExceptionProc = isPayFlowExceptionProc;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getTransStat() {
        return transStat;
    }

    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteTransResultFlag() {
        return routeTransResultFlag;
    }

    public void setRouteTransResultFlag(String routeTransResultFlag) {
        this.routeTransResultFlag = routeTransResultFlag;
    }
}
