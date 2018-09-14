/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.UpdateOriginalOrderStatDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author shang
 * 2016年12月12日
 * 退款成功修改原订单的订单状态图
 */
public class UpdateOriginalOrderStatService extends AbstractDipperHandler<UpdateOriginalOrderStatDto> {

    @Resource
    IDaoService daoService;
    
    @Override
    public UpdateOriginalOrderStatDto execute(UpdateOriginalOrderStatDto dto, Message message)
            throws Exception {
        String oriOrderNo=dto.getOriOrderNo();
        String orderStat=dto.getOrderStat();
        String refundAmtFlag=dto.getRefundAmtFlag();
        if(StringUtils.isBlank(oriOrderNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if(StringUtils.isBlank(refundAmtFlag)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "退款金额标识");
        }
        if(StringUtils.isBlank(orderStat)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单状态");
        }
        if(dto.getEjectAmt()==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "累计退款金额");
        }
        if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(orderStat)){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("orderNo", oriOrderNo);
            if(CommonBaseConstans_PAY.REFUND_AMT_ALL.equals(refundAmtFlag)){
                map.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
            }else{                
                map.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
            }
            map.put("oriOrderStatOne", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
            map.put("oriOrderStatTwo", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
            map.put("oriOrderStatThr", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
            map.put("ejectAmt", dto.getEjectAmt());
            int num=daoService.update(PayOrderListPo.class.getName().concat(".updateOrdStatAftRef"), map);
            if(num!=1){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "退款更新原订单");
            }
        }
        
        return dto;
    }

}
