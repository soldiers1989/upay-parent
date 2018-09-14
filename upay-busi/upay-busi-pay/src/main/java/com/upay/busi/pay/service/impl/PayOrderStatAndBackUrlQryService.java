/**
 * 
 */
package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayOrderStatAndBackUrlQryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 查询订单状态和回调URL
 * 
 * @author zhanggr
 *
 */
public class PayOrderStatAndBackUrlQryService extends AbstractDipperHandler<PayOrderStatAndBackUrlQryDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public PayOrderStatAndBackUrlQryDto execute(PayOrderStatAndBackUrlQryDto payOrderStatAndBackUrlQryDto,
            Message msg) throws Exception {
        String orderNo = payOrderStatAndBackUrlQryDto.getOrderNo();
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }

        PayOrderListPo payOrderListPo = new PayOrderListPo();
        payOrderListPo.setOrderNo(orderNo);
        payOrderListPo = daoService.selectOne(payOrderListPo);

        if (payOrderListPo != null) {
            String orderStat = payOrderListPo.getOrderStat();
            String returnUrl = payOrderListPo.getReturnUrl();
            if (StringUtils.isBlank(orderStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0031, orderNo);
            } else {
                if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(orderStat)
                        || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(orderStat)
                        || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(orderStat)
                        || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)
                        || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(orderStat)
                        || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(orderStat)) {
                    payOrderStatAndBackUrlQryDto.setOrderStat(orderStat);
                } else {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0031, orderNo);
                }
            }
            if (StringUtils.isBlank(returnUrl)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "同步通知路径");
            } else {
                payOrderStatAndBackUrlQryDto.setReturnUrl(returnUrl);
            }
            payOrderStatAndBackUrlQryDto.setTransAmt(payOrderListPo.getTransAmt());
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "订单" + orderNo);
        }
        return payOrderStatAndBackUrlQryDto;
    }

}
