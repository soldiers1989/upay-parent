/**
 * 
 */
package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;


/**
 * 刷卡支付订单状态校验
 * 
 * @author zhanggr
 *
 */
public class AlipayOrderStateCheckDto extends BaseDto {
    private String orderNo;// 统一支付订单号
    private String outerOrderNo;// 商户订单号
    private String orderStateFlag;// 是否成功标识 true:成功 false:失败
    private String merNo;// 商户号
    private String secMerNo;// 二级商户号
    private String transSubSeq;// 流水号
    private BigDecimal transAmt;// 交易金额
    private String tradeState;// 订单状态
    private String orderDes;
    private String totalFee;
    private String orderTime;
    private String reqType;
    


    public String getReqType() {
		return reqType;
	}


	public void setReqType(String reqType) {
		this.reqType = reqType;
	}


	public String getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}


	public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getOuterOrderNo() {
        return outerOrderNo;
    }


    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }


    public String getOrderStateFlag() {
        return orderStateFlag;
    }


    public void setOrderStateFlag(String orderStateFlag) {
        this.orderStateFlag = orderStateFlag;
    }


    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }


    public String getTransSubSeq() {
        return transSubSeq;
    }


    public void setTransSubSeq(String transSubSeq) {
        this.transSubSeq = transSubSeq;
    }


    public BigDecimal getTransAmt() {
        return transAmt;
    }


    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }


    public String getTradeState() {
        return tradeState;
    }


    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }


    public String getSecMerNo() {
        return secMerNo;
    }


    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }


    public String getOrderDes() {
        return orderDes;
    }


    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }


	public String getTotalFee() {
		return totalFee;
	}


	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

}
