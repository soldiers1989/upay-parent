/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.ReturnAccountAmtDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 检查是否需要做余额回退操作
 * @author shang
 * 2016年10月19日
 */
public class ReturnAccountAmtService extends AbstractDipperHandler<ReturnAccountAmtDto> {
    
    @Resource
    IDaoService daoService;

    @Override
    public ReturnAccountAmtDto execute(ReturnAccountAmtDto dto, Message message) throws Exception {
        
        String orderStat=dto.getOrderStat();
        String orderNo=dto.getOrderNo();
        if(StringUtils.isBlank(orderStat)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单状态");
        }
        if(StringUtils.isBlank(orderNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        PayOrderListPo order=new PayOrderListPo();
        order.setOrderNo(orderNo);
        order=daoService.selectOne(order);
        if(order==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
        }
        BigDecimal payAmt=order.getTransAmt();
        
        dto.setTransAmt(payAmt);//订单金额
        dto.setFeeAmt(order.getFeeAmt());//手续费
        dto.setIsAccAmtRefund(CommonBaseConstans_PAY.ACCAMT_REFUND_N);
        if((DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(orderStat)||
                DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL.equals(orderStat)||
                DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(orderStat))&&dto.isUpdateUserAccountAmt()){
            String accNo=dto.getAccNo();
            if(StringUtils.isBlank(accNo)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "虚拟账号");
            }
            if(payAmt==null){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
            }
            if(dto.isUpdateUserAccountAmt()){                
                dto.setIsAccAmtRefund(CommonBaseConstans_PAY.ACCAMT_REFUND_Y);
            }
            dto.setUpdateAmt(payAmt);
//                Map<String,Object> map=new HashMap<String,Object>();
//                map.put("accNo", accNo);
//                map.put("transAmt", payAmt);
//                daoService.update(AccVbookPo.class.getName().concat(".updateAccountAmtAdd"), map);
        }
        return dto;
    }
}
