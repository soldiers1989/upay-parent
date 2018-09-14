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
import com.upay.busi.pay.service.dto.PayOrderStateCheckOfMicropayDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 刷卡支付订单状态校验
 * 
 * @author zhanggr
 *
 */
public class PayOrderStateCheckOfMicropayService extends
        AbstractDipperHandler<PayOrderStateCheckOfMicropayDto> {

    @Resource
    IDaoService daoService;


    @Override
    public PayOrderStateCheckOfMicropayDto execute(
            PayOrderStateCheckOfMicropayDto payOrderStateCheckOfMicropayDto, Message arg1) throws Exception {

        String orderNo = payOrderStateCheckOfMicropayDto.getOrderNo();// 统一支付订单号
        String outerOrderNo = payOrderStateCheckOfMicropayDto.getOuterOrderNo();// 商户订单号
        if (StringUtils.isBlank(orderNo) && StringUtils.isBlank(outerOrderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0052);
        }
        PayOrderListPo payOrderList = new PayOrderListPo();
        PayFlowListPo payFlowListPo = new PayFlowListPo();
        if (StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(outerOrderNo)) {
            payOrderList.setOrderNo(outerOrderNo);
            payOrderList.setOuterOrderNo(outerOrderNo);
        } else if (StringUtils.isNotBlank(orderNo) && StringUtils.isBlank(outerOrderNo)) {
            payOrderList.setOrderNo(orderNo);
        } else {
            payOrderList.setOuterOrderNo(outerOrderNo);
        }
        payOrderList = daoService.selectOne(payOrderList);
        String orderStateFlag = "false";
        if (payOrderList == null) {// 不存在
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0051);
        } else {// 存在、判断订单状态返回
            String orderStat = payOrderList.getOrderStat();// 订单状态
            // 如果订单状态为终态:支付成功超时关闭 手工关闭确认收货 预约支付 待确认收货,则直接返回状态给商户
            if (!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(orderStat)
                    && !DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(orderStat)) {
                orderStateFlag = "true";
                payOrderStateCheckOfMicropayDto.setTradeState(orderStat);
            }
            payFlowListPo.setOrderNo(payOrderList.getOrderNo());
            payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
            payFlowListPo = daoService.selectOne(payFlowListPo);
            if (payFlowListPo != null) {
                String transSubSeq = payFlowListPo.getTransSubSeq();
                payOrderStateCheckOfMicropayDto.setTransSubSeq(transSubSeq);
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "该订单对应的微信流水");
            }
            payOrderStateCheckOfMicropayDto.setMerNo(payOrderList.getMerNo());
            payOrderStateCheckOfMicropayDto.setSecMerNo(payOrderList.getSecMerNo());
            payOrderStateCheckOfMicropayDto.setTransAmt(payOrderList.getTransAmt());
            payOrderStateCheckOfMicropayDto.setMerFeeAmt(payOrderList.getMerFeeAmt());
            payOrderStateCheckOfMicropayDto.setOrderNo(payOrderList.getOrderNo());
            payOrderStateCheckOfMicropayDto.setOuterOrderNo(payOrderList.getOuterOrderNo());
            payOrderStateCheckOfMicropayDto.setOrderDes(payOrderList.getOrderDes());
        }
        payOrderStateCheckOfMicropayDto.setOrderStateFlag(orderStateFlag);
        return payOrderStateCheckOfMicropayDto;
    }
}
