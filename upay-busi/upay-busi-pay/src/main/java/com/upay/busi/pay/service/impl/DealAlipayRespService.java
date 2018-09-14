package com.upay.busi.pay.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.exception.SysErrCode;
import com.upay.busi.pay.service.dto.DealAlipayRespDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

public class DealAlipayRespService extends AbstractDipperHandler<DealAlipayRespDto> {
    private static final Logger LOG = LoggerFactory.getLogger(DealAlipayRespService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public DealAlipayRespDto execute(DealAlipayRespDto dto, Message msg)
            throws Exception {
        LOG.info("支付宝返回处理开始");
        String code = msg.getFault().getCode();
        String returnCode = dto.getReturnCode();
        String isNotifyCoreSys = dto.getIsNotifyCoreSys();
        if (SysErrCode.SYS003.equals(code)) { // 通讯异常
            LOG.error("通讯异常");
            dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING);//支付中
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);//未知
            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);//处理中
        }else if ((Constants.ResponseCode.SUCCESS.equals(code) && DataBaseConstants_PAY.PAY_ALIPAY_SUCC.equals(returnCode))
        		||"true".equals(isNotifyCoreSys)) {
            dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
        } else {
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
            dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL);
            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
        }
        
        dto = this.updateFlowListByTransSubSeq(dto);
        //聚合支付会在调用聚合支付的时候将payType存入表中，回调时，不修改，扫码支付是在回调时设置payType。
        String orderNo = dto.getOrderNo();
        if(StringUtils.isBlank(orderNo)){
        	ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "订单号");
        }
        PayOrderListPo payOrder = new PayOrderListPo();
        payOrder.setOrderNo(orderNo);
        payOrder = daoService.selectOne(payOrder);
        if(payOrder == null){
        	ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, orderNo);
        }
       	if(StringUtils.isBlank(payOrder.getPayType())){
       		dto.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_ALIPAY_QR_CODE);
        }else{
        	dto.setPayType(payOrder.getPayType());
        }
        LOG.info("支付宝返回处理结束");
        dto.setPayTime(DateUtil.parse(dto.getGmtPayment(), "yyyy-MM-dd HH:mm:ss"));
        return dto;
    }

    /**
     * 根据支付流水号更新支付流水表
     *
     * @param dto
     */
    private DealAlipayRespDto updateFlowListByTransSubSeq(DealAlipayRespDto dto) {
    	String outTradeNo = dto.getOutTradeNo();
    	if(StringUtils.isBlank(outTradeNo)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付宝返回流水号");
    	}
        PayFlowListPo whereEntity = new PayFlowListPo();
        whereEntity.setTransSubSeq(outTradeNo);

        PayFlowListPo setEntity = new PayFlowListPo();
        setEntity.setRouteSeq(dto.getTradeNo());//通道支付订单号
        setEntity.setTransStat(dto.getTransStat());//交易状态
//        setEntity.setPayerBankName(getBankType(dto.getPayerBankName()));
        String gmtPayment = dto.getGmtPayment();
        if(StringUtils.isNotBlank(gmtPayment)){
        	setEntity.setTransTime(DateUtil.parse(gmtPayment, "yyyy-MM-dd HH:mm:ss"));//支付时间
        	setEntity.setRouteDate(DateUtil.parse(gmtPayment, "yyyy-MM-dd"));//通道方支付日期
        }
        setEntity.setRouteTransStat(dto.getRouteTransStat());//通道方交易状态ROUTE_TRANS_TYPE_SUCCESS
        setEntity.setLastUpdateTime(new Date());
        setEntity.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
        daoService.update(setEntity, whereEntity);
        return dto;
    }
    

}
