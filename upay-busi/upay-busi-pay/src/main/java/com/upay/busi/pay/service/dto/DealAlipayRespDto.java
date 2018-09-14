package com.upay.busi.pay.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;

public class DealAlipayRespDto extends BaseDto{
	private String returnCode;
	private String orderStat;
	private String transStat;
	private String routeTransStat;
	
	private String transSubSeq;
	private String routeSeq;
	private String gmtPayment;
	private Date payTime;
	private String tradeNo;
	private String routeCode;
	private String isNotifyCoreSys;
	private String outTradeNo;
	private String payType;
	private String authCode;
	private String orderNo;
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
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

	public String getRouteTransStat() {
		return routeTransStat;
	}

	public void setRouteTransStat(String routeTransStat) {
		this.routeTransStat = routeTransStat;
	}

	public String getTransSubSeq() {
		return transSubSeq;
	}

	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}

	public String getRouteSeq() {
		return routeSeq;
	}

	public void setRouteSeq(String routeSeq) {
		this.routeSeq = routeSeq;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getIsNotifyCoreSys() {
		return isNotifyCoreSys;
	}

	public void setIsNotifyCoreSys(String isNotifyCoreSys) {
		this.isNotifyCoreSys = isNotifyCoreSys;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
