package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.NotifyStatusCheckDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 * Created by Guo on 16/8/17. 支付结果通知前检查
 */
public class AtNotifyStatusCheckService extends AbstractDipperHandler<NotifyStatusCheckDto> {
    private static final Logger LOG = LoggerFactory.getLogger(AtNotifyStatusCheckService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public NotifyStatusCheckDto execute(NotifyStatusCheckDto dto, Message message) throws Exception {
        LOG.info("支付结果通知前检查开始");

        String returnCode = dto.getReturnCode();
        String resultCode = dto.getResultCode();// 支付通知结果,业务响应代码
        if (StringUtils.isBlank(returnCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付通讯响应代码");
        }
        if (StringUtils.isBlank(resultCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付结果响应代码");
        }

        String transSubSeq = dto.getTransSubSeq();
        if (StringUtils.isBlank(transSubSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "支付流水号");
        }
        // 根据支付流水号查询支付流水表
        PayFlowListPo flowListPo = new PayFlowListPo();
        flowListPo.setTransSubSeq(transSubSeq);
        flowListPo = daoService.selectOne(flowListPo);
        if(!flowListPo.getTransAmt().equals(dto.getTransAmt())){
        	 ExInfo.throwDipperEx(AppCodeDict.BISPAY0119, "");
        }
        if (null != flowListPo) {
            dto.setOrderNo(flowListPo.getOrderNo());
            dto.setSrFlag(flowListPo.getSrFlag());
            dto.setPayeeAccNo(flowListPo.getPayeeAcctNo());
            dto.setPayerAccNo(flowListPo.getPayerAcctNo());
            dto.setMerNo(flowListPo.getMerNo());
            dto.setTransAmt(flowListPo.getTransAmt());
            dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0021, transSubSeq);// 支付流水不存在
        }
        PayOrderListPo orderListPo = new PayOrderListPo();
        orderListPo.setOrderNo(dto.getOrderNo());
        orderListPo = daoService.selectOne(orderListPo);

        if (null == orderListPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, dto.getOrderNo());// 订单不存在
        }

        String isNotifyCoreSys = "true";
        String refundFlag = "false";
        String orderStat = orderListPo.getOrderStat();
        // 如果订单状态为终态:支付成功,则直接返回成功给第三方支付
        if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat)) {
            isNotifyCoreSys = "false";
            String payType = orderListPo.getPayType();
            if (StringUtils.isNotBlank(payType)
                    && !DataBaseConstants_PAY.T_PAY_TYPE_WEIXIN_QR_CODE.equals(payType)
                    && !DataBaseConstants_PAY.T_PAY_TYPE_PUBLIC_NO.equals(payType)) {
                refundFlag = "true";
            }
        }
        dto.setPayServicType(orderListPo.getPayServicType());
        dto.setIsNotifyCoreSys(isNotifyCoreSys);
        dto.setOrderDes(orderListPo.getOrderDes());
        dto.setRefundFlag(refundFlag);
        LOG.info("支付结果通知前检查结束");
        return dto;
    }
}
