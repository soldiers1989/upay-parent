/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.AlipayOrderStateCheckDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 刷卡支付订单状态校验
 * 
 *
 */
public class UnionOrderStateCheckService extends
        AbstractDipperHandler<AlipayOrderStateCheckDto> {

    @Resource
    IDaoService daoService;


    @Override
    public AlipayOrderStateCheckDto execute(
    		AlipayOrderStateCheckDto dto, Message arg1) throws Exception {
        SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = dto.getOrderNo();// 统一支付订单号
        String outerOrderNo = dto.getOuterOrderNo();// 商户订单号
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
                dto.setTradeState(orderStat);
            }
            payFlowListPo.setOrderNo(payOrderList.getOrderNo());
            payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
            payFlowListPo = daoService.selectOne(payFlowListPo);
            if (payFlowListPo != null) {
                String transSubSeq = payFlowListPo.getTransSubSeq();
                String transTime=SIM_YMD.format(payFlowListPo.getTransTime());
                dto.setOrderNo(payFlowListPo.getOrderNo());
                dto.setOrderTime(transTime);
                dto.setTransSubSeq(transSubSeq);
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "该订单对应的支付宝流水");
            }
            dto.setMerNo(payOrderList.getMerNo());
            dto.setSecMerNo(payOrderList.getSecMerNo());
            dto.setTransAmt(payOrderList.getTransAmt());
            dto.setOrderNo(payOrderList.getOrderNo());
            dto.setOuterOrderNo(payOrderList.getOuterOrderNo());
            dto.setOrderDes(payOrderList.getOrderDes());
            dto.setTotalFee(String.valueOf(MoneyUtil.transferY2F(payOrderList.getTransAmt(), 2)));
        }
        dto.setReqType("0350000903");
        dto.setOrderStateFlag(orderStateFlag);
        return dto;
    }
}
