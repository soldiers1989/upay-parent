package com.upay.busi.pay.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.OrderSyncStatusManageDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 更改订单信息(订单状态，支付方式，用户id)
 * 
 * @author liu
 * 
 */
public class OrderSyncStatusManageService extends AbstractDipperHandler<OrderSyncStatusManageDto> {

    private static final Logger LOG = LoggerFactory.getLogger(OrderSyncStatusManageService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public OrderSyncStatusManageDto execute(OrderSyncStatusManageDto dto, Message msg)
            throws Exception {
        Date now = new Date();
        String orderNo = dto.getOrderNo();
        String orderStat = dto.getOrderStat();
        String payType=dto.getPayType();
        String userId=dto.getUserId();
        Date payTime=dto.getPayTime(); 

        // 订单号不能为空,订单状态不为空
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }

        PayOrderListPo where = new PayOrderListPo();
        PayOrderListPo param = new PayOrderListPo();

        // 判断订单号是否可以在订单表中查到
        where.setOrderNo(orderNo);
//        PayOrderListPo pay = daoService.selectOne(payOrderListPo);
//        if (pay == null) {
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "订单:" + orderNo);
//        }
        // 将数据添加到po中
        if(StringUtils.isNotBlank(orderStat)){            
            param.setOrderStat(orderStat);
        }
        if(StringUtils.isNotBlank(payType)){
            param.setPayType(payType);
        }
        if(StringUtils.isNotBlank(userId)){
            param.setUserId(userId);
        }
        if(payTime!=null){
            param.setPayTime(payTime);
        }
        if(StringUtils.isNotBlank(dto.getStlFlag())){
            param.setStlFlag(dto.getStlFlag());
        }
        if(StringUtils.isNotBlank(dto.getClearFlag())){
            param.setClearFlag(dto.getClearFlag());
        }
        if(StringUtils.isNotBlank(dto.getReturnUrl())){
            param.setReturnUrl(dto.getReturnUrl());
        }
        param.setLastUpdateTime(now);
        param.setRouteCode(dto.getOrgRouteCode());
        syncCheckOrder(param,where,dto);

        return dto;
    }
    
    public synchronized void syncCheckOrder(PayOrderListPo param, PayOrderListPo where, OrderSyncStatusManageDto dto){
    	PayOrderListPo payOrderList = new PayOrderListPo();
    	payOrderList.setOrderNo(dto.getOrderNo());
    	payOrderList = daoService.selectOne(payOrderList);
    	if (null == payOrderList) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, dto.getOrderNo());// 订单不存在
        }
    	if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(payOrderList.getOrderStat())){
    		dto.setIsNotifyCoreSys("false");
    		return;
    	}
    	// 更新订单表
        daoService.update(param, where);
    }

}
