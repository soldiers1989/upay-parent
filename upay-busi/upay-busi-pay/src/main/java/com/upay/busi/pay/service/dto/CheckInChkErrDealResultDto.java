package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class CheckInChkErrDealResultDto extends BaseDto{
	/** 差错平台流水号 */
	private String errFlowSeq;
	/** 订单号 */
	private String orderNo;
	/** 通道代码 */
	private String routeCode;
	private String transStat;//交易状态
	private String routeTransStat;// 通道交易状态
    private String routeDate; // 通道交易日期
    private String routeSeq; // 通道交易流水号
    private String tellerNo;//操作柜员号
    private String isDeal;//是否处理差错
    private String chkStat;//对账状态
    private String errTransAmt;//差错流水交易金额
    private String dealType;//差错处理方式
    private String dealFlowStat;
    //冲核心的补账流水
    private String coreSubSeq;
    //手续费冲账流水
    private String feePaySeq;
    //冲微信、中金的流水
    private String refundSeq;
    //手续费记账标志
    private String isReqFeeCore;
    private String errRouteCode;
    //中金差错流水号（通道方）
    private String zjErrRouteSeq;
    private String isAddFee;
    /**结算差错 */
	private String stlErr;
    
	public String getErrFlowSeq() {
		return errFlowSeq;
	}
	public void setErrFlowSeq(String errFlowSeq) {
		this.errFlowSeq = errFlowSeq;
	}
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
	public String getRouteTransStat() {
		return routeTransStat;
	}
	public void setRouteTransStat(String routeTransStat) {
		this.routeTransStat = routeTransStat;
	}
	public String getRouteDate() {
		return routeDate;
	}
	public void setRouteDate(String routeDate) {
		this.routeDate = routeDate;
	}
	public String getRouteSeq() {
		return routeSeq;
	}
	public void setRouteSeq(String routeSeq) {
		this.routeSeq = routeSeq;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	public String getTellerNo() {
		return tellerNo;
	}
	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}
	public String getChkStat() {
		return chkStat;
	}
	public void setChkStat(String chkStat) {
		this.chkStat = chkStat;
	}
	public String getErrTransAmt() {
		return errTransAmt;
	}
	public void setErrTransAmt(String errTransAmt) {
		this.errTransAmt = errTransAmt;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getDealFlowStat() {
		return dealFlowStat;
	}
	public void setDealFlowStat(String dealFlowStat) {
		this.dealFlowStat = dealFlowStat;
	}
	public String getCoreSubSeq() {
		return coreSubSeq;
	}
	public void setCoreSubSeq(String coreSubSeq) {
		this.coreSubSeq = coreSubSeq;
	}
	public String getFeePaySeq() {
		return feePaySeq;
	}
	public void setFeePaySeq(String feePaySeq) {
		this.feePaySeq = feePaySeq;
	}
	public String getRefundSeq() {
		return refundSeq;
	}
	public void setRefundSeq(String refundSeq) {
		this.refundSeq = refundSeq;
	}
	public String getIsReqFeeCore() {
		return isReqFeeCore;
	}
	public void setIsReqFeeCore(String isReqFeeCore) {
		this.isReqFeeCore = isReqFeeCore;
	}
	public String getErrRouteCode() {
		return errRouteCode;
	}
	public void setErrRouteCode(String errRouteCode) {
		this.errRouteCode = errRouteCode;
	}
	public String getZjErrRouteSeq() {
		return zjErrRouteSeq;
	}
	public void setZjErrRouteSeq(String zjErrRouteSeq) {
		this.zjErrRouteSeq = zjErrRouteSeq;
	}
	public String getIsAddFee() {
		return isAddFee;
	}
	public void setIsAddFee(String isAddFee) {
		this.isAddFee = isAddFee;
	}
	public String getStlErr() {
		return stlErr;
	}
	public void setStlErr(String stlErr) {
		this.stlErr = stlErr;
	}
	
	
}
