package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * 检查订单状态
 * @author shangqiankun
 * @version 创建时间：2016年8月16日 下午2:02:04
 */
public class CheckOrderListStatDto extends BaseDto {
    private String orderNo;//订单号
    private String merNo;//商户号
    private BigDecimal transAmt;//支付金额
    private String orderDes;//订单描述
    private String returnUrl;
    private String transType;//交易类型
    private String oriOrderNo;//原订单号
    private String orderUserId;//订单的用户
    private String payServicType;//支付服务类型
    private String secMerNo;//二级商户号
    
    private String ifUpdateOrd;//是否修改订单 状态为支付中
    
    private java.lang.String promoterDeptNo;//订单所属于银行哪个机构
    
    
	public java.lang.String getPromoterDeptNo() {
		return promoterDeptNo;
	}
	public void setPromoterDeptNo(java.lang.String promoterDeptNo) {
		this.promoterDeptNo = promoterDeptNo;
	}
    
    
    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getIfUpdateOrd() {
        return ifUpdateOrd;
    }

    public void setIfUpdateOrd(String ifUpdateOrd) {
        this.ifUpdateOrd = ifUpdateOrd;
    }

    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getOrderDes() {
        return orderDes;
    }

    public void setOrderDes(String orderDes) {
        this.orderDes = orderDes;
    }

    public String getMerNo() {
        return merNo;
    }


    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    
    

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    

}
