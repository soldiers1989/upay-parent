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
import com.upay.busi.pay.service.dto.CheckOrderUserIdDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 用户和订单建立绑定关系
 * @author shang
 * 2016年10月28日
 */
public class CheckOrderUserIdService extends AbstractDipperHandler<CheckOrderUserIdDto> {

    
    @Resource
    IDaoService daoService;
    @Override
    public CheckOrderUserIdDto execute(CheckOrderUserIdDto dto, Message msg) throws Exception {
        String orderNo=dto.getOrderNo();
        String userId=dto.getUserId();
        if(StringUtils.isBlank(dto.getOrderUserId())){
            if(StringUtils.isBlank(orderNo)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
            }
            if(StringUtils.isBlank(userId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户id");
            }
            PayOrderListPo order=new PayOrderListPo();
            order.setOrderNo(orderNo);
            order=daoService.selectOne(order);
            if(order==null){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
            }
            if(StringUtils.isBlank(order.getUserId())){                
                //建立绑定关系
                PayOrderListPo orderw=new PayOrderListPo();
                orderw.setOrderNo(orderNo);
                PayOrderListPo orderp=new PayOrderListPo();
                orderp.setUserId(userId);
                daoService.update(orderp, orderw);
            }
        }
        return dto;
    }

}
