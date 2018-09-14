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
import com.upay.busi.pay.service.dto.OrderStatusManageDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 更改订单信息(订单状态，支付方式，用户id)
 * 
 * @author liu
 * 
 */
public class OrderStatusManageService extends AbstractDipperHandler<OrderStatusManageDto> {

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusManageService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public OrderStatusManageDto execute(OrderStatusManageDto dto, Message msg)
            throws Exception {
        Date now = new Date();
        String orderNo = dto.getOrderNo();
        String orderStat = dto.getOrderStat();
        String payType=dto.getPayType();
        String userId=dto.getUserId();
        Date payTime=dto.getPayTime(); 
        String orgRouteCode = dto.getOrgRouteCode();

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
        if(StringUtils.isNotBlank(dto.getOrgRouteCode())){
            param.setRouteCode(orgRouteCode);
        }
        param.setLastUpdateTime(now);
        // 更新订单表
        daoService.update(param, where);

        return dto;
    }

}
