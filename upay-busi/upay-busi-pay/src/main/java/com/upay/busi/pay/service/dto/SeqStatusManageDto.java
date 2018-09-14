package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 流水号管理交易状态
 * 
 * @author liu
 * 
 */

public class SeqStatusManageDto extends BaseDto {

    private String transSubSeq; // 明细流水号
    private String transStat; // 内部交易状态
    private String routeTransStat;// 通道交易状态
    private String routeDate; // 通道交易日期
    private String routeSeq; // 通道交易流水号
    private String SuccessRouteCode;//成功的资金通道
    private String routeCode;//资金通道
    private String clrType;//通道清算方式
    
    
    private String responseCode;
    private String responseMessage;
    
    
    
    

    
    
    
    public String getResponseCode() {
        return responseCode;
    }


    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }


    public String getResponseMessage() {
        return responseMessage;
    }


    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


    public String getClrType() {
        return clrType;
    }


    public void setClrType(String clrType) {
        this.clrType = clrType;
    }


    public String getRouteCode() {
        return routeCode;
    }


    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }


    public String getSuccessRouteCode() {
        return SuccessRouteCode;
    }


    public void setSuccessRouteCode(String successRouteCode) {
        SuccessRouteCode = successRouteCode;
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


    public String getTransStat() {
        return transStat;
    }


    public void setTransStat(String transStat) {
        this.transStat = transStat;
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

}
