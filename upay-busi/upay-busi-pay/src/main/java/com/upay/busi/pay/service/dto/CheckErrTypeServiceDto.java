package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

public class CheckErrTypeServiceDto extends BaseDto{
	/** 差错处理方式 */
	private String dealType;
	/** 差错平台流水号 */
	private String errFlowSeq;
	/** 订单号 */
	private String orderNo;
	/** 通道代码 */
	private String errRouteCode;
	/** 是否进行差错处理 */
	private String isDeal;
	/**对账状态 */
	private String chkStat;
	/**结算差错 */
	private String stlErr;
	
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
	
	public String getErrRouteCode() {
		return errRouteCode;
	}
	public void setErrRouteCode(String errRouteCode) {
		this.errRouteCode = errRouteCode;
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
	public String getStlErr() {
		return stlErr;
	}
	public void setStlErr(String stlErr) {
		this.stlErr = stlErr;
	}
	
	
	
	
	
	
}
