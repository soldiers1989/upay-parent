package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

public class PayOrderCheckDto extends BaseDto {
	private String orderNo; //平台订单号
	private String outerOrderNo;// 商户订单号
	private String isCreateOrder;
	private String spbillCreateIp;
	private String outerOrderStartDate;// 商户订单生成日期
	private String outerOrderEndDate;// 商户订单失效日期
	private BigDecimal transAmt;//交易金额
	private String curr;//币种
	private String payServicType;//支付服务类型
	private BigDecimal otherTranAmt;//其他费用
	private BigDecimal productAmt;//商品费用
	
	
    public BigDecimal getOtherTranAmt() {
        return otherTranAmt;
    }

    public void setOtherTranAmt(BigDecimal otherTranAmt) {
        this.otherTranAmt = otherTranAmt;
    }

    public BigDecimal getProductAmt() {
        return productAmt;
    }

    public void setProductAmt(BigDecimal productAmt) {
        this.productAmt = productAmt;
    }

    public String getPayServicType() {
        return payServicType;
    }

    public void setPayServicType(String payServicType) {
        this.payServicType = payServicType;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public BigDecimal getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(BigDecimal transAmt) {
        this.transAmt = transAmt;
    }

    public String getOuterOrderNo() {
		return outerOrderNo;
	}

	public void setOuterOrderNo(String outerOrderNo) {
		this.outerOrderNo = outerOrderNo;
	}

	public String getIsCreateOrder() {
		return isCreateOrder;
	}

	public void setIsCreateOrder(String isCreateOrder) {
		this.isCreateOrder = isCreateOrder;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getOuterOrderEndDate() {
		return outerOrderEndDate;
	}

	public void setOuterOrderEndDate(String outerOrderEndDate) {
		this.outerOrderEndDate = outerOrderEndDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOuterOrderStartDate() {
		return outerOrderStartDate;
	}

	public void setOuterOrderStartDate(String outerOrderStartDate) {
		this.outerOrderStartDate = outerOrderStartDate;
	}
}
