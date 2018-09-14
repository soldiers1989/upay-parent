package com.upay.busi.fee.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月3日 下午4:44:00
 */
public class FeeGetBaseDto extends BaseDto {
    private String isMer;//是否为商户手续费计算：0不是，1是
    private BigDecimal transAmt;//交易金额
    
    
	private String merNo;// 商户号
	private String orderNo;// 订单号
	private String accNo;// 用户的账号
	private String accType;// 用户的账户类型
	private String bankAccNo;// 银行卡号
	private String routeCode;// 资金通道
	private String transType;//交易类型
	private String secMerNo;//二级商户号
	
	private String payType;//支付方式
	
	private String getType;//手续费收起方式：0内扣，1外扣
	
	private BigDecimal merFeeAmt;//商户手续费
	private BigDecimal feeAmt;//手续费
	private String feeAmtPoints;//手续费分
	
	private String feeGetType;//手续费收取方式
	
	
	
	private String routeTransCode;//通道交易码
	private BigDecimal routeFeeAmt;//通道手续费
	
	
	public BigDecimal getMerFeeAmt() {
		return merFeeAmt;
	}

	public void setMerFeeAmt(BigDecimal merFeeAmt) {
		this.merFeeAmt = merFeeAmt;
	}

	public BigDecimal getRouteFeeAmt() {
		return routeFeeAmt;
	}

	public void setRouteFeeAmt(BigDecimal routeFeeAmt) {
		this.routeFeeAmt = routeFeeAmt;
	}

	public String getRouteTransCode() {
		return routeTransCode;
	}

	public void setRouteTransCode(String routeTransCode) {
		this.routeTransCode = routeTransCode;
	}

	public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getIsMer() {
        return isMer;
    }

    public void setIsMer(String isMer) {
        this.isMer = isMer;
    }

    public String getGetType() {
        return getType;
    }

    public void setGetType(String getType) {
        this.getType = getType;
    }

    public String getFeeAmtPoints() {
		return feeAmtPoints;
	}

	public void setFeeAmtPoints(String feeAmtPoints) {
		this.feeAmtPoints = feeAmtPoints;
	}

	public String getFeeGetType() {
        return feeGetType;
    }

    public void setFeeGetType(String feeGetType) {
        this.feeGetType = feeGetType;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    

    
    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }
    

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
   

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getMerNo() {
		return merNo;
	}

	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}

}
