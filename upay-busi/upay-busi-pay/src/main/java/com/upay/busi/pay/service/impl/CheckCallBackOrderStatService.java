/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckCallBackOrderStatDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 支付回调验证订单
 * @author shang
 * 2016年11月25日
 */
public class CheckCallBackOrderStatService extends AbstractDipperHandler<CheckCallBackOrderStatDto> {

    @Resource
    IDaoService daoService;
    
    @Override
    public CheckCallBackOrderStatDto execute(CheckCallBackOrderStatDto dto, Message message)
            throws Exception {
        SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
        String routCode=dto.getRouteCode();
        if(StringUtils.isBlank(routCode)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道");
        }
        PayFlowListPo  payFlow=new PayFlowListPo();
        if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routCode)){
            String routeSeq=dto.getRouteSeq();
            if(StringUtils.isBlank(routeSeq)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道方 流水号 ");
            }
            payFlow.setRouteSeq(routeSeq);
        }else if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routCode)){
        	
            String transSeq=dto.getOrderId();
            if(StringUtils.isBlank(transSeq)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
            }
            payFlow.setTransSubSeq(transSeq);
            
        }
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
        if(DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(order.getPayServicType())){            
            dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP);
        }else{
            dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        }
        dto.setMerNo(order.getMerNo());
        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
        dto.setOrderNo(order.getOrderNo());
        dto.setTransAmt(order.getTransAmt());
        dto.setTransSubSeq(payFlow.getTransSubSeq());
        
        if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routCode)){
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
        }else{
        	dto.setRouteSeq(payFlow.getRouteSeq());
        	dto.setRouteDate(SIM_YMD.format(new Date()));
        }
        dto.setTransType(order.getTransType());
        dto.setPayServicType(order.getPayServicType());
        dto.setOtherRouteCode(routCode);
        dto.setOrderClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        return dto;
    }

}
