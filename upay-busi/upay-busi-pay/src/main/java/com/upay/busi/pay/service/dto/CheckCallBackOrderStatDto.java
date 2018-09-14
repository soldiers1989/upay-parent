/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年11月25日
 */
public class CheckCallBackOrderStatDto extends BaseDto {
    
    
    private String zjEbankStat;//中金网银支付回调结果 状态
    private boolean ifOrderSuccess;//当前订单是否成功
    private boolean ifFlowSuccess;//当前流水是否成功
    private String routeCode;//资金通道
    /** 订单基本信息 */
    private String orderNo;//订单号
    private String orderStat;//订单 状态
    private BigDecimal transAmt;//订单金额
    private String transType;//交易类型
    private String payServicType;//支付服务类型
    /** 流水信息 */
    private String transStat;//流水状态
    private String transSubSeq;//流水 号
    private String routeDate;//资金通道的交易日期
    private String routeSeq;//通道流水号
    private String routeTransStat;//自己通道交易状态
    private String merNo;//商户号
    //银联返回
    private String txnAmt;//交易金额
    private String queryId;//银联流水号
    private String orderId;//平台流水号
    private String txnTime;
    private String otherRouteCode;//核心之外的通道
    private String totalFee;//交易金额 单位分
    private String orderClearFlag;
    
    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getRouteSeq() {
        return routeSeq;
    }

    public void setRouteSeq(String routeSeq) {
        this.routeSeq = routeSeq;
    }

    public String getRouteDate() {
        return routeDate;
    }

    public void setRouteDate(String routeDate) {
        this.routeDate = routeDate;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
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

    public boolean isIfOrderSuccess() {
        return ifOrderSuccess;
    }

    public void setIfOrderSuccess(boolean ifOrderSuccess) {
        this.ifOrderSuccess = ifOrderSuccess;
    }

    public boolean isIfFlowSuccess() {
        return ifFlowSuccess;
    }

    public void setIfFlowSuccess(boolean ifFlowSuccess) {
        this.ifFlowSuccess = ifFlowSuccess;
    }

    public String getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }

    public String getZjEbankStat() {
        return zjEbankStat;
    }

    public void setZjEbankStat(String zjEbankStat) {
        this.zjEbankStat = zjEbankStat;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getOtherRouteCode() {
		return otherRouteCode;
	}

	public void setOtherRouteCode(String otherRouteCode) {
		this.otherRouteCode = otherRouteCode;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getOrderClearFlag() {
		return orderClearFlag;
	}

	public void setOrderClearFlag(String orderClearFlag) {
		this.orderClearFlag = orderClearFlag;
	}

	
    
    
    
}
