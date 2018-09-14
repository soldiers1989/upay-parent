/**
 * 
 */
package com.upay.busi.pay.service.dto;

import com.upay.commons.dto.BaseDto;


/**
 * 微信支付状态转换为订单状态
 * 
 * @author zhanggr
 *
 */
public class MicropayStateChangeDto extends BaseDto {

    private String orderStat;// 订单状态
    private String tradeState;// 微信状态


    public String getOrderStat() {
        return orderStat;
    }


    public void setOrderStat(String orderStat) {
        this.orderStat = orderStat;
    }


    public String getTradeState() {
        return tradeState;
    }


    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

}
