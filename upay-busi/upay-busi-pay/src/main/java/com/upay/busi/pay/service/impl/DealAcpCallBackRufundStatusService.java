
package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.jmx.CommonNames;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckCallBackOrderStatDto;
import com.upay.busi.pay.service.dto.DealAcpCallBackRufundStatusDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 银联退款回调，处理流水、订单状态
 * 
 * 
 */
public class DealAcpCallBackRufundStatusService extends AbstractDipperHandler<DealAcpCallBackRufundStatusDto> {

    @Resource
    IDaoService daoService;
    
    @Override
    public DealAcpCallBackRufundStatusDto execute(DealAcpCallBackRufundStatusDto dto, Message message)
            throws Exception {
        String routCode=dto.getRouteCode();
        if(StringUtils.isBlank(routCode)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道");
        }
        PayFlowListPo  payFlow=new PayFlowListPo();
        	
        String transSeq=dto.getOrderId();
        if(StringUtils.isBlank(transSeq)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
        }
        payFlow.setTransSubSeq(transSeq);
        payFlow=daoService.selectOne(payFlow);
        
        if(payFlow==null){
        	ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "当前流水");
        }
        
        PayOrderListPo order=new PayOrderListPo();
        order.setOrderNo(payFlow.getOrderNo());
        order=daoService.selectOne(order);
        if(order==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "当前订单");
        }
        //隔日退款，订单清算状态为清算完成
        if(order.getOriDate().compareTo(order.getOrderDate()) < 0){
        	dto.setOrderClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        }else{
        	dto.setOrderClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        }
        String transStat=payFlow.getTransStat();
        boolean orderSuc=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(order.getOrderStat())
                ||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(order.getOrderStat())
                ||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(order.getOrderStat());
        if(orderSuc){
            dto.setIfOrderSuccess(true);
        }
        if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(transStat)){
            dto.setIfFlowSuccess(true);
        }
        if(!(DataBaseConstants_PAY.UNION_STAT_SUCC.equals(dto.getRespCode()) || DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(dto.getRespCode()))){
        	ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "银联退款");
        }
        dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        dto.setMerNo(order.getMerNo());
        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
        dto.setOrderNo(order.getOrderNo());
        dto.setTransAmt(order.getTransAmt());
        dto.setTransSubSeq(payFlow.getTransSubSeq());
        
       
        String queryId = dto.getQueryId();
        String txnTime = dto.getTxnTime();
        if(StringUtils.isBlank(queryId)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "银联流水号 ");
        }
        if(StringUtils.isBlank(txnTime)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "银联交易日期 ");
        }
        dto.setRouteSeq(queryId);
        dto.setRouteDate(txnTime.substring(0,8));
        dto.setTotalFee(dto.getTxnAmt());
        
        dto.setTransType(order.getTransType());
        dto.setPayServicType(order.getPayServicType());
        dto.setOtherRouteCode(routCode);
        return dto;
    }

}
