package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckOrderListStatDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 检查订单状态，如果检查订单已经超时，更改订单状态（此原子服务不要放在事务中）
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月16日 下午2:03:06
 */
public class CheckOrderListStatService extends AbstractDipperHandler<CheckOrderListStatDto> {

    @Resource
    private IDaoService daoService;
    private static final Logger LOG = LoggerFactory.getLogger(CheckOrderListStatService.class);

    @Override
    public CheckOrderListStatDto execute(CheckOrderListStatDto dto, Message message) throws Exception {
        String orderNo = dto.getOrderNo();
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if (StringUtils.isBlank(dto.getIfUpdateOrd())) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单状态锁定标识");
        }
        PayOrderListPo order = new PayOrderListPo();
        order.setOrderNo(orderNo);
        order = daoService.selectOne(order);
        if (order == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
        }
        Date createTime = order.getOrderTime();
        String orderStat = order.getOrderStat();
        if(StringUtils.isBlank(orderStat)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单状态");
        }
        int minute = order.getOrderLmtTime();
        if (!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(orderStat)) {
            if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付完成");
            } else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(orderStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付中");
            }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(orderStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付失败");
            }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC.equals(orderStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "超时关闭");
            }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC.equals(orderStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "已取消");
            }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(orderStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付完成");
            }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(orderStat)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付完成");
            }else{
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0031,orderNo);
            }
        }
        if(CommonConstants_GNR.LOCK_ORDER_STAT_YES.equals(dto.getIfUpdateOrd())){            
            PayOrderListPo parm=new PayOrderListPo();
            PayOrderListPo wher=new PayOrderListPo();
            wher.setOrderNo(orderNo);
            wher.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);
            parm.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);
            int num=daoService.update(parm, wher);
            if(num!=1){
                order.setOrderNo(orderNo);
                order = daoService.selectOne(order);
                if (order == null) {
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
                }
                if (!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(orderStat)) {
                    if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)) {
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付完成");
                    } else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(orderStat)){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付中");
                    }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(orderStat)){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付失败");
                    }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC.equals(orderStat)){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "超时关闭");
                    }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC.equals(orderStat)){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "已取消");
                    }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(orderStat)){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付完成");
                    }else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(orderStat)){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0030, "支付完成");
                    }else{
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0031,orderNo);
                    }
                }
            }
        }
        Date now = new Date();
        boolean a=(int) ((now.getTime() - createTime.getTime()) / 1000L / 60L) - minute >= 0;
        boolean b=order.getOuterOrderEndDate()==null?true:order.getOuterOrderEndDate().compareTo(now)<=0;
        LOG.info(a+"------------"+b);
        if (a||b) {
            PayOrderListPo param=new PayOrderListPo();
            param.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC);
            PayOrderListPo where=new PayOrderListPo();
            where.setOrderNo(orderNo);
            daoService.update(param, where);
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0030,"超时关闭");
        }
//        boolean checkMerDate=order.getOuterOrderEndDate()==null?true:order.getOuterOrderEndDate().compareTo(now)>=0?true:false;
//        if(!checkMerDate){
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0024, orderNo);
//        }
        
        //拆分成了两个原子服务   QueryOrderByOrderNoService 
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
