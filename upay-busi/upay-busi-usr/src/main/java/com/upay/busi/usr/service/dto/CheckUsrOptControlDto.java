package com.upay.busi.usr.service.dto;

import com.upay.commons.dto.BaseDto;

/**
 * 检查用户交易权限
 * @author shangqiankun
 * @version 创建时间：2016年8月13日 上午10:18:27
 */
public class CheckUsrOptControlDto extends BaseDto {
    private String mobile;
    private String payType;//支付方式
    private String routeCode;//资金通道

    
    
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    

}
