package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 资金通道状态检查
 * 
 * @author liyulong
 * 
 */
public class PayRouteStateChkDto extends BaseDto {

    private String routeCode;// 通道代码
    private String routeFuncCode;// 功能代码
    private String transSyncFlag;// 交易同步/异步标识
    private String routeStat;// 通道状态
    private String statTime;// 开放开始时间
    private String endTime;// 开放截止时间

    private String routeTransCode;  //资金通道交易代码
    
    
    public String getRouteTransCode() {
		return routeTransCode;
	}


	public void setRouteTransCode(String routeTransCode) {
		this.routeTransCode = routeTransCode;
	}


	public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public String getRouteFuncCode() {
        return routeFuncCode;
    }


    public void setRouteFuncCode(String routeFuncCode) {
        this.routeFuncCode = routeFuncCode;
    }


    public String getTransSyncFlag() {
        return transSyncFlag;
    }


    public void setTransSyncFlag(String transSyncFlag) {
        this.transSyncFlag = transSyncFlag;
    }


    public String getRoutStat() {
        return routeStat;
    }


    public void setRoutStat(String routStat) {
        this.routeStat = routStat;
    }


    public String getStatTime() {
        return statTime;
    }


    public void setStatTime(String statTime) {
        this.statTime = statTime;
    }


    public String getEndTime() {
        return endTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
