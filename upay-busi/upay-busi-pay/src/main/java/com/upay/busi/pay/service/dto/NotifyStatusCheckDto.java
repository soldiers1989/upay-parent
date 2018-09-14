package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

import java.math.BigDecimal;


/**
 * Created by Guo on 16/8/17.
 */
public class NotifyStatusCheckDto extends BaseDto {

    private String returnCode;

    private String resultCode;

    /** 平台订单号,对应外部商户系统订单号 */
    private String orderNo;

    /** 平台明细流水,提供给外部系统的订单号 */
    private String transSubSeq;

    /** 支付通知结果 */
    private String rspCode;

    /** 是否通知核心系统 */
    private String isNotifyCoreSys;

    /** 往来标识 */
    private String srFlag;

    /** 收款账号 */
    private String payeeAccNo;

    /** 付款账号 */
    private String payerAccNo;

    private String merNo;

    /** 通道代码 */
    private String routeCode;

    /** 支付金额 */
    private BigDecimal transAmt;

    private String orderDes;

    private String refundFlag;// 是否订单重复，发起退款

    private String payServicType;// 支付服务类型
    
    private String totalFee;


    public String getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}


	public String getOrderDes() {
        return orderDes;
    }


    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }


    public String getReturnCode() {
        return returnCode;
    }


    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }


    public String getResultCode() {
        return resultCode;
    }


    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }


    public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getTransSubSeq() {
        return transSubSeq;
    }


    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }


    public String getRspCode() {
        return rspCode;
    }


    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }


    public String getIsNotifyCoreSys() {
        return isNotifyCoreSys;
    }


    public void setIsNotifyCoreSys(String isNotifyCoreSys) {
        this.isNotifyCoreSys = isNotifyCoreSys;
    }


    public String getSrFlag() {
        return srFlag;
    }


    public void setSrFlag(String srFlag) {
        this.srFlag = srFlag;
    }


    public String getPayeeAccNo() {
        return payeeAccNo;
    }


    public void setPayeeAccNo(String payeeAccNo) {
        this.payeeAccNo = payeeAccNo;
    }


    public String getPayerAccNo() {
        return payerAccNo;
    }


    public void setPayerAccNo(String payerAccNo) {
        this.payerAccNo = payerAccNo;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public String getRefundFlag() {
        return refundFlag;
    }


    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag;
    }


    public String getPayServicType() {
        return payServicType;
    }


    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

}
