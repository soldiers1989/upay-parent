package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 资金通道退款结果DTO
 * @author zhangjianfeng
 * @since 2016/11/11 14:53
 */
public class RouteRefundResultDto extends BaseDto {

    /** 资金通道字符串日期 yyyyMMdd */
    public String routeDate;

    /** 资金通道支付流水 */
    public String routeSeq;

    /** 支付流水交易状态 */
    public String transStat;

    /** 资金通道交易状态 */
    public String routeTransStat;

    /** 资金通道通讯超时标志 */
    public String routeTimeout;

    /** 资金通道代码 */
    public String routeCode;

    /** 支付流水流水号 */
    private String transSubSeq;

    /** 通道支付结果标记，用于异常处理 */
    private String routeTransResultFlag;

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

    public String getRouteTransStat() {
        return routeTransStat;
    }

    public void setRouteTransStat(String routeTransStat) {
        this.routeTransStat = routeTransStat;
    }

    public String getRouteTimeout() {
        return routeTimeout;
    }

    public void setRouteTimeout(String routeTimeout) {
        this.routeTimeout = routeTimeout;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

    public String getRouteTransResultFlag() {
        return routeTransResultFlag;
    }

    public void setRouteTransResultFlag(String routeTransResultFlag) {
        this.routeTransResultFlag = routeTransResultFlag;
    }
}
