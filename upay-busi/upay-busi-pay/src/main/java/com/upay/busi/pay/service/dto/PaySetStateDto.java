/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.io.Serializable;
import java.util.Date;

import com.upay.commons.dto.BaseDto;


/**
 * 交易状态，订单状态赋值
 * 
 * @author zhanggr
 * 
 */
public class PaySetStateDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -987956839624662235L;

    private String orderStat;// 订单状态
    private String transStat;// 内部交易状态
    private String routeTransStat;// 通道交易状态
    private String statFlag;// 交易状态标志

    private Date sysTime;// 交易时间
    private String sysTimeOne; // 时间格式化

    private String payServicType;// 支付服务类型
    
    private String errCodeDes;

	

 	public String getErrCodeDes() {
 		return errCodeDes;
 	}

 	public void setErrCodeDes(String errCodeDes) {
 		this.errCodeDes = errCodeDes;
 	}


    public String getSysTimeOne() {
        return sysTimeOne;
    }


    public void setSysTimeOne(String sysTimeOne) {
        this.sysTimeOne = sysTimeOne;
    }


    public Date getSysTime() {
        return sysTime;
    }


    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }


    public String getStatFlag() {
        return statFlag;
    }


    public void setStatFlag(String statFlag) {
        this.statFlag = statFlag;
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


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public String getPayServicType() {
        return payServicType;
    }


    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }


    @Override
    public String toString() {
        return "PmtSetStateDto [orderStat=" + orderStat + ", transStat=" + transStat + ", routeTransStat="
                + routeTransStat + "]";
    }

}
