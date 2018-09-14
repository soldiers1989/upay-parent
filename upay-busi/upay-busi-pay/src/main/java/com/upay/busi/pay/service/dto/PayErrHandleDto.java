package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午6:58:12
 */
public class PayErrHandleDto extends BaseDto {
    
    private String zjTransCode;//中金接口名
    
    private String accType;//账户类型
    private String orderNo;
    private String transSubSeq;//交易流水
    private String transStat;//交易状态
    private String routeTimeout;//通道超时
    private String routeCode;//通道代码
    
    private String routeTransStat;// 通道交易状态
    private String routeDate; // 通道交易日期
    private String routeSeq; // 通道交易流水号
    
    private String bkDate;//核心日期
    private String bkSerialNo;//核心流水号
    private String respCode;//核心状态码
    
    
    private String paymentNo;//中金交易流水
    private String status;//中金
    private String bankTxTime;//中金（银行处理时间）
    private BigDecimal feeAmt;//手续费
    
    private String transType;//交易类型
    private String respMsg;
    
    private String returnCode;
    
    private String fundChange;
    
    private String tradeNo;
    private String gmtRefundPay;
    
    private String isEsbCore;
    private String machineDate;

	public String getMachineDate() {
		return machineDate;
	}

	public void setMachineDate(String machineDate) {
		this.machineDate = machineDate;
	}

	public String getIsEsbCore() {
		return isEsbCore;
	}

	public void setIsEsbCore(String isEsbCore) {
		this.isEsbCore = isEsbCore;
	}

	public String getZjTransCode() {
        return zjTransCode;
    }

    public void setZjTransCode(String zjTransCode) {
        this.zjTransCode = zjTransCode;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankTxTime() {
        return bankTxTime;
    }

    public void setBankTxTime(String bankTxTime) {
        this.bankTxTime = bankTxTime;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
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

    public String getBkDate() {
        return bkDate;
    }

    public void setBkDate(String bkDate) {
        this.bkDate = bkDate;
    }

    public String getBkSerialNo() {
        return bkSerialNo;
    }

    public void setBkSerialNo(String bkSerialNo) {
        this.bkSerialNo = bkSerialNo;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteTimeout() {
        return routeTimeout;
    }

    public void setRouteTimeout(String routeTimeout) {
        this.routeTimeout = routeTimeout;
    }

    public String getTransStat() {
        return transStat;
    }

    public void setTransStat(String transStat) {
        this.transStat = transStat;
    }

    public String getTransSubSeq() {
        return transSubSeq;
    }

    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

	public String getFundChange() {
		return fundChange;
	}

	public void setFundChange(String fundChange) {
		this.fundChange = fundChange;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getGmtRefundPay() {
		return gmtRefundPay;
	}

	public void setGmtRefundPay(String gmtRefundPay) {
		this.gmtRefundPay = gmtRefundPay;
	}

	
    
    
    
    
}
