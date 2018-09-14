/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * @author shang
 * 2016年10月28日
 */
public class CheckOrderUserIdDto extends BaseDto {
    private String orderNo;//订单号
    private String orderUserId;//订单的用户
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderUserId() {
        return orderUserId;
    }
    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }
    
    
}
