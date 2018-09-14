package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.upay.busi.pay.service.dto.PayErrHandleDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UpdateStatusManageService extends AbstractDipperHandler<PayErrHandleDto> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UpdateStatusManageService.class);

    @Override
    public PayErrHandleDto execute(PayErrHandleDto dto, Message message) throws Exception {
        //1.银联同步返回  00  表示受理成功  不代表 交易成功
        if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(dto.getRespCode() == null ? "" : dto.getRespCode())) {
            //查询银联订单状态    确定流水状态
            Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();

            log.info(" 银联订单查询map{{}}", map);
            if (map.containsKey("respCode")) {
                log.info(" 银联订单查询respCode{{}}", map);
                String respCode = (String) map.get("respCode");
                String origRespCode = (String) map.get("origRespCode");


                //1..respCode 表示 {//如果查询交易 受理成功
                if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(respCode) ||
                        DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode)) {

                    //1.1查询返回00或者A6 表示订单成功
                    if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(origRespCode) ||
                            DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(origRespCode)) {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    }
                    //  1.2   处理中03/04/05   订单处理中，需要稍后发起交易状态查询
                    //   前后台交易查询时间间隔参考接口规范
                    //   交易状态查询交易描述   //需要 订单查询 状态
                    //   由订单状态同步处理
                    else if ("03".equals(origRespCode) || "04".equals(origRespCode) || "05".equals(origRespCode)) {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
                    }

                    //1.3 其他状态 失败
                    else {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }

                }
                //2..交易状态未知  可能银联先收到  订单查询  后收到  交易  由订单状态同步处理
                else if ("34".equals(origRespCode)) {
                    dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
                    dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
                }
                //3.其他应答码 都为失败
                else {
                    dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                    dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                }

            //2.查询交易失败 检查报文是否正确  视为处理中   发起订单查询失败       订单未处理中
            } else {
                dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
                dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
            }
        }
        //2.银联未受理成功 请检查报文
        else {
            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
        }
        log.info(" 银联订单查询dto{{}}", dto);
        return dto;
    }
}
