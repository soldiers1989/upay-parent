package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class AdjustFlowListServiceDto extends BaseDto{
	/** 差错处理方式 */
	private String dealType;
	/** 差错平台流水号 */
	private String errFlowSeq;
	/** 订单号 */
	private String orderNo;
	/** 通道代码 */
	private String routeCode;
	/** 是否进行差错处理 */
	private String isDeal;
	/**对账状态 */
	private String chkStat;
	/**需要调整的流水状态 */
	private String dealFlowStat;
	
	private String transStat;
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	
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
	public String getDealFlowStat() {
		return dealFlowStat;
	}
	public void setDealFlowStat(String dealFlowStat) {
		this.dealFlowStat = dealFlowStat;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	
	
}
