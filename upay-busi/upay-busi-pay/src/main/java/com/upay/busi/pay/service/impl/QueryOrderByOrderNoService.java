/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.QueryOrderByOrderNoDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author shang
 * 2016年11月7日
 */
public class QueryOrderByOrderNoService extends AbstractDipperHandler<QueryOrderByOrderNoDto> {

	 private static final Logger LOG = LoggerFactory.getLogger(QueryOrderByOrderNoService.class);
    @Resource
    IDaoService daoService;

    @Override
    public QueryOrderByOrderNoDto execute(QueryOrderByOrderNoDto dto, Message msg) throws Exception {
    	 String orderNo = dto.getOrderNo();
         if (StringUtils.isBlank(orderNo)) {
             ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
         }
         PayOrderListPo order = new PayOrderListPo();
         order.setOrderNo(orderNo);
         order = daoService.selectOne(order);
         if (order == null) {
             ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
         }
         dto.setMerNo(order.getMerNo());
         if(StringUtils.isNotBlank(order.getSecMerNo())){
             dto.setSecMerNo(order.getSecMerNo());
         }
         dto.setTransAmt(order.getTransAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
         dto.setOrderDes(order.getOrderDes());
         dto.setReturnUrl(order.getReturnUrl());
         dto.setTransType(order.getTransType());
         dto.setOrderUserId(order.getUserId());
         dto.setPayServicType(order.getPayServicType());
         dto.setPromoterDeptNo(order.getPromoterDeptNo());
         if(StringUtils.isNotBlank(order.getOriOrderNo())){
             dto.setOriOrderNo(order.getOriOrderNo());
         }
         return dto;
    }

}
