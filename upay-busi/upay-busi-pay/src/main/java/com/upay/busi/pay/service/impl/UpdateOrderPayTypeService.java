package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.UpdateOrderPayTypeDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 检查订单状态，如果检查订单已经超时，更改订单状态（此原子服务不要放在事务中）
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月16日 下午2:03:06
 */
public class UpdateOrderPayTypeService extends AbstractDipperHandler<UpdateOrderPayTypeDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public UpdateOrderPayTypeDto execute(UpdateOrderPayTypeDto dto, Message message) throws Exception {
    	
        String orderNo = dto.getOrderNo();
        String payType = dto.getPayType();
        String userId = dto.getUserId();
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if (StringUtils.isBlank(payType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付方式");
        }
//        if (StringUtils.isBlank(userId)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户ID");
//        }
        PayOrderListPo payOrderListWhere = new PayOrderListPo();
        payOrderListWhere.setOrderNo(orderNo);
        
        PayOrderListPo payOrderListParam = new PayOrderListPo();
        payOrderListParam.setPayType(payType);
        payOrderListParam.setUserId(userId);
        daoService.update(payOrderListParam, payOrderListWhere);
        return dto;
    }
}
