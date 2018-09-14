package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DealAlipayNotifyDto;
import com.upay.busi.pay.service.dto.NotifyStatusCheckDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 *  支付宝支付结果通知前检查
 */
public class DealAlipayNotifyService extends AbstractDipperHandler<DealAlipayNotifyDto> {
    private static final Logger LOG = LoggerFactory.getLogger(DealAlipayNotifyService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public DealAlipayNotifyDto execute(DealAlipayNotifyDto dto, Message message) throws Exception {
        LOG.info("支付结果通知前检查开始");
        String transSubSeq = dto.getOutTradeNo();//流水号
        String tradeNo = dto.getTradeNo();//支付宝流水号
        String totalAmount = dto.getTotalAmount();//交易金额
        String tradeStatus = dto.getTradeStatus();//交易状态
        
        if (StringUtils.isBlank(tradeStatus)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付宝返回交易状态");
        }
        
        if (StringUtils.isBlank(transSubSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付流水号");
        }
        // 查询支付流水
        PayFlowListPo alipayFlow = new PayFlowListPo();
        alipayFlow.setTransSubSeq(transSubSeq);
        alipayFlow = daoService.selectOne(alipayFlow);
        if (null == alipayFlow) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "流水"+tradeNo);// 流水不存在
        }
        
        //查询订单信息
        PayOrderListPo orderListPo = new PayOrderListPo();
        orderListPo.setOrderNo(alipayFlow.getOrderNo());
        orderListPo = daoService.selectOne(orderListPo);

        if (null == orderListPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, dto.getOrderNo());// 订单不存在
        }
        if(orderListPo.getTransAmt().compareTo(new BigDecimal(totalAmount)) != 0){
        	ExInfo.throwDipperEx(AppCodeDict.BISPAY0119);
        }
        
        String isNotifyCoreSys = "true";
        String orderStat = orderListPo.getOrderStat();
        // 如果订单状态为终态:支付成功,则直接返回成功给第三方支付
        if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat) 
        		|| !DataBaseConstants_PAY.ALIPAY_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
            isNotifyCoreSys = "false";
        }
        
        
        dto.setOrderNo(alipayFlow.getOrderNo());
        dto.setSrFlag(alipayFlow.getSrFlag());
        dto.setPayeeAccNo(alipayFlow.getPayeeAcctNo());
        dto.setPayerAccNo(alipayFlow.getPayerAcctNo());
        dto.setMerNo(alipayFlow.getMerNo());
        dto.setTransAmt(alipayFlow.getTransAmt());
        dto.setTotalFee(String.valueOf(MoneyUtil.transferY2F(alipayFlow.getTransAmt(), 2)));
        dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        dto.setPayServicType(orderListPo.getPayServicType());
        dto.setIsNotifyCoreSys(isNotifyCoreSys);
        dto.setOrderDes(orderListPo.getOrderDes());
        LOG.info("支付结果通知前检查结束");
        return dto;
    }
 
}
