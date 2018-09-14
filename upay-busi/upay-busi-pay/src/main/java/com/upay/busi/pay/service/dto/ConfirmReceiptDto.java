package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 用户确认收货DTO
 * @author  zhangjianfeng
 * @since 2016/8/16
 */
public class ConfirmReceiptDto extends BaseDto {

    /** 商户号 */
    private String merNo;

    /** 二级商户号 */
    private String secMerNo;

    /** 商户订单号 */
    private String outerOrderNo;

    /** 支付平台订单号 */
    private String orderNo;

    private String returnUrl;// 同步通知路径


    
    

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getSecMerNo() {
        return secMerNo;
    }

    public void setSecMerNo(String secMerNo) {
        this.secMerNo = secMerNo;
    }

    public String getOuterOrderNo() {
        return outerOrderNo;
    }

    public void setOuterOrderNo(String outerOrderNo) {
        this.outerOrderNo = outerOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
