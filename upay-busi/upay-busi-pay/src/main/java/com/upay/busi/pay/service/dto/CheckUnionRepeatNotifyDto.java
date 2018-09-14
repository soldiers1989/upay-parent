package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class CheckUnionRepeatNotifyDto extends BaseDto{
	private String orderId;//平台流水号(银联返回)
	private String respCode;//银联返回响应码
	private boolean repeatFlag;//是否是重复发起的通知
	private String routeCode;
	private String zjEbankStat;//中金支付状态码
	private String routeSeq;
	private String settleKey;
	private String transSubSeq;
	private String attach;
	
	
	
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTransSubSeq() {
		return transSubSeq;
	}
	public void setTransSubSeq(String transSubSeq) {
		this.transSubSeq = transSubSeq;
	}
	public String getSettleKey() {
		return settleKey;
	}
	public void setSettleKey(String settleKey) {
		this.settleKey = settleKey;
	}
	private String voucherNum;//由银联统一生成的交易索引，永久唯一，用于后续业务处理。
	
	public String getVoucherNum() {
		return voucherNum;
	}
	public void setVoucherNum(String voucherNum) {
		this.voucherNum = voucherNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public boolean isRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(boolean repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public String getRouteCode() {
		return routeCode;
	}
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getZjEbankStat() {
		return zjEbankStat;
	}
	public void setZjEbankStat(String zjEbankStat) {
		this.zjEbankStat = zjEbankStat;
	}
	public String getRouteSeq() {
		return routeSeq;
	}
	public void setRouteSeq(String routeSeq) {
		this.routeSeq = routeSeq;
	}
	
	
	
	
	
	
}
