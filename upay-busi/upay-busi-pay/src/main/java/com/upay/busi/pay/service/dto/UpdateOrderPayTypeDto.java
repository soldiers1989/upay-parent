package com.upay.busi.pay.service.dto;

import java.math.BigDecimal;

import com.upay.commons.dto.BaseDto;

/**
 * 检查订单状态
 * @author shangqiankun
 * @version 创建时间：2016年8月16日 下午2:02:04
 */
public class UpdateOrderPayTypeDto extends BaseDto {
    private String orderNo;//订单号
    private String payType;//支付方式
    private String userId;

    public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
    

}
