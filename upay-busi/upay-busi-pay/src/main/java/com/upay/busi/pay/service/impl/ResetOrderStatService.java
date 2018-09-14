package com.upay.busi.pay.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.ResetOrderStatDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 获取要更改的订单状态
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午4:02:09
 */
public class ResetOrderStatService extends AbstractDipperHandler<ResetOrderStatDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public ResetOrderStatDto execute(ResetOrderStatDto dto, Message message) throws Exception {
        String orderNo = dto.getOrderNo();
        String payServicType=dto.getPayServicType();
        String transType=dto.getTransType();
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if(StringUtils.isBlank(transType)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易类型");
        }
        boolean isPay=false;
        if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)){   
            isPay=true;
            if (StringUtils.isBlank(payServicType)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付服务类型");
            }
        }
        String respCode = message.getFault().getCode();
        String orderStatCopy=dto.getOrderStat();
        boolean ordSuc=StringUtils.isBlank(orderStatCopy)?false:(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStatCopy)
                ||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(orderStatCopy)
                ||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(orderStatCopy));
        String orderStat=null;
        // 判断代码是否执行成功，执行成功则认为支付成功（访问外部接口超时需要抛错）
        if (CommonConstants_GNR.RSP_CODE_SUCCESS.equals(respCode)) {
        	if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){
        		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
        	}else{
        		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
        	}
        	dto.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
        } else {
            dto.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
            PayFlowListPo pay = new PayFlowListPo();
            pay.setOrderNo(orderNo);
            pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
            List<PayFlowListPo> payList = daoService.selectList(pay);
            /*
             * 判断是否有成功的交易流水，有则认为当前交易在进行第二笔交易流水的时候抛错，则认为支付失败，
             * 没有，继续判断流水状态是否有未知状态，有，则修改订单状态为支付中，没有，修改订单状态为待支付
             */
            if (payList == null || payList.size() == 0) {
            	pay = new PayFlowListPo();
                pay.setOrderNo(orderNo);
//                pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
                pay = daoService.selectOne(pay);
                String currentFlowStatus=null;
                if(pay!=null){
                    currentFlowStatus=pay.getTransStat();
                }
                if(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN.equals(currentFlowStatus)||DataBaseConstants_PAY.T_PAY_TX_PROING.equals(currentFlowStatus)){
                	//如果是第一笔流水是未知或支付中的状态 设置订单状态为支付中
                	if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){
                		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING;
                	}else{
                		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING;
                	}
                }else if(DataBaseConstants_PAY.T_PAY_TX_FAL.equals(currentFlowStatus)){
                	//如果是第一笔流水是失败的状态 设置订单状态为失败
                	if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){
                		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
                	}else{
                		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL;
                	}
                }else{
                	//其他情况 设置订单状态为未支付，退款设置为退款失败
                	if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){
                		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL;
                	}else{
                		orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N;
                	}
                }
            } else{
                PayOrderListPo order = new PayOrderListPo();
                order.setOrderNo(orderNo);
                order = daoService.selectOne(order);
                if (order != null) {
                    if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(order.getOrderStat())) {
                        orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y;
                    }else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING.equals(order.getOrderStat())){
                    	orderStat=DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC;
                    } else {
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0049, "状态有误");
                    }
                } else {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
                }
            }
        }
        if(StringUtils.isBlank(orderStat)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "订单状态获取服务");
        }
        if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)){
            Date now=new Date();
            dto.setPayTime(now);
        }
        if(isPay){
            orderStat=this.getOrderStat(orderStat, payServicType,transType);
        }
        dto.setOrderStat(orderStat);
        if(ordSuc){
            orderStat=orderStatCopy;
        }
        if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){
            if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(orderStat)){
                dto.setRefundStat(CommonBaseConstans_PAY.OUTER_REFUND_STAT_SUCCESS);
            }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL.equals(orderStat)){
                dto.setRefundStat(CommonBaseConstans_PAY.OUTER_REFUND_STAT_FAIL);
            }else{
                dto.setRefundStat(CommonBaseConstans_PAY.OUTER_REFUND_STAT_DOING);
            }
        }
        
        dto.setOrderStat(orderStat);
        return dto;
    }
    private String getOrderStat(String orderStat,String payServicType,String transType){
    	
        if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)&&DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(payServicType)&&DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)){
            return DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP;
        }else{
            return orderStat;
        }
    }

}
