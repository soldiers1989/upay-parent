package com.upay.busi.pay.service.dto;

import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 查询订单号对订单状态管理
 * 
 * @author liu
 * 
 */
public class OrderSyncStatusManageDto extends BaseDto {

    private String orderNo; // 订单号
    private String orderStat; // 订单状态
    private Date payTime;//支付完成时间
    private String payType;//支付方式
    
    private String stlFlag;//结算标识
    private String clearFlag;//清算状态
    private String returnUrl;// 同步通知路径
    private String orgRouteCode;
  


	public String getOrgRouteCode() {
		return orgRouteCode;
	}


	public void setOrgRouteCode(String orgRouteCode) {
		this.orgRouteCode = orgRouteCode;
	}


	private String isNotifyCoreSys;
    
    
    public String getReturnUrl() {
        return returnUrl;
    }


    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }


    public String getClearFlag() {
        return clearFlag;
    }


    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }


    public String getStlFlag() {
        return stlFlag;
    }


    public void setStlFlag(String stlFlag) {
        this.stlFlag = stlFlag;
    }


    public String getPayType() {
        return payType;
    }


    public void setPayType(String payType) {
        this.payType = payType;
    }


    public Date getPayTime() {
        return payTime;
    }


    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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


	public String getIsNotifyCoreSys() {
		return isNotifyCoreSys;
	}


	public void setIsNotifyCoreSys(String isNotifyCoreSys) {
		this.isNotifyCoreSys = isNotifyCoreSys;
	}

}
