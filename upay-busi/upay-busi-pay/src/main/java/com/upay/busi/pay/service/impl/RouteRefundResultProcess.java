package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.SysErrCode;
import com.upay.busi.pay.service.dto.RouteRefundResultDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资金通道退款结果处理
 * @author zhangjianfeng
 * @since 2016/11/11 14:52
 */
public class RouteRefundResultProcess extends AbstractDipperHandler<RouteRefundResultDto> {

    private static final Logger LOG = LoggerFactory.getLogger(RouteRefundResultProcess.class);

    @Override
    public RouteRefundResultDto execute(RouteRefundResultDto dto, Message message) throws Exception {

        String respCode = message.getFault().getCode(); //支付平台错误码
        String respMsg = message.getFault().getMsg(); //支付平台错误信息

        String outRespCode = message.getFault().getOutCode(); //资金通道响应码
        String outRespMsg = message.getFault().getOutMsg(); //资金通道响应信息

        String routeCode = dto.getRouteCode();

        LOG.debug("资金通道[{}]退款处理结果：[{}]:[{}]，系统响应信息：[{}]:[{}]", new Object[]{routeCode, outRespCode, outRespMsg,
                respCode, respMsg});

        if(Constants.ResponseCode.SUCCESS.equals(respCode)) { //资金通道处理成功
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
        } else if(SysErrCode.SYS003.equals(respCode)) { //通讯异常
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN); //交易结果未知
        } else { //资金通道处理失败
            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
        }

        dto.setRouteTransResultFlag(dto.getTransSubSeq().concat(dto.getTransStat()));

        return dto;
    }
}
