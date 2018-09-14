package com.upay.busi.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.exception.SysErrCode;
import com.upay.dao.po.pay.PayOrderListPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RefundExceptionDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.pay.PayFlowListPo;

/**
 * 退款异常处理
 * @author zhangjianfeng
 * @since 2016/08/22 09:09
 */
public class RefundExceptionProcService extends AbstractDipperHandler<RefundExceptionDto> {

    private static final Logger LOG = LoggerFactory.getLogger(RefundExceptionProcService.class);

    @Resource
    private IDaoService daoService;

    @Override
    public RefundExceptionDto execute(RefundExceptionDto dto, Message message) throws Exception {

        String errorCode = message.getFault().getCode();
        String errorMsg = message.getFault().getMsg();

        String refundOrderNo = dto.getRefundOrderNo(); //退款订单号
        if(StringUtils.isNotBlank(refundOrderNo)) {
            String routeCode = dto.getRouteCode(); //资金通道代码
            String routeTransResultFlag = dto.getRouteTransResultFlag(); //通道支付结果标记，用于异常处理
            //退款订单
            PayOrderListPo refundOrder = new PayOrderListPo();
            refundOrder.setOrderNo(refundOrderNo);
            refundOrder = daoService.selectOne(refundOrder);

            //当前退款支付流水
            String transSubSeq = dto.getTransSubSeq();
            PayFlowListPo refundPayFlow = null;
            if(StringUtils.isNotBlank(transSubSeq)) {
                refundPayFlow = new PayFlowListPo();
                refundPayFlow.setOrderNo(refundOrderNo);
                refundPayFlow.setTransSubSeq(transSubSeq);
            }

            //如果退款订单不存在；或退款订单状态为支付失败、超时关闭、手工关闭状态；则无需处理退款订单和退款支付流水
            if(refundOrder == null || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(refundOrder.getOrderStat())
                    || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC.equals(refundOrder.getOrderStat())
                    || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC.equals(refundOrder.getOrderStat())) {
                dto.setIsOrderExceptionProc("0"); //0-不需要异常处理；1-需要异常处理；
                dto.setIsPayFlowExceptionProc("0"); //0-不需要异常处理；1-需要异常处理；
            } else if(refundPayFlow == null && (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(refundOrder.getOrderStat())
                    || DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(refundOrder.getOrderStat()))) {
                //如果支付流水不存在且订单状态为未支付或支付中，则需修改退款订单状态为支付失败
                dto.setIsOrderExceptionProc("1"); //订单异常处理，修改订单状态为支付失败
                dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL);
                dto.setOrderNo(refundOrderNo);
                dto.setIsPayFlowExceptionProc("0");
            } else if(SysErrCode.SYS003.equals(errorCode)) { //资金通道通讯异常，修改支付流水状态为未知
                dto.setIsOrderExceptionProc("0"); //0-不需要异常处理；1-需要异常处理；
                dto.setIsPayFlowExceptionProc("1"); //0-不需要异常处理；1-需要异常处理；
                dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN); //交易结果未知
            } else if(refundPayFlow != null && DataBaseConstants_PAY.T_PAY_TX_PROING.equals(refundPayFlow.getTransStat())
                    && transSubSeq.concat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS).equals(routeTransResultFlag)) {
                //资金通道响应成功，修改支付流水时失败；修改支付流水状态为未知，由批量再次确认支付流水状态是否成功且完成后续处理
                dto.setIsOrderExceptionProc("0"); //0-不需要异常处理；1-需要异常处理；
                dto.setIsPayFlowExceptionProc("1"); //0-不需要异常处理；1-需要异常处理；
                dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN); //交易结果未知
            } else if(refundPayFlow != null && DataBaseConstants_PAY.T_PAY_TX_PROING.equals(refundPayFlow.getTransStat())
                    && !SysErrCode.SYS003.equals(errorCode)
                    && !transSubSeq.concat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS).equals(routeTransResultFlag)) {
                //资金通道响应失败，修改支付流水状态为失败
                if(!DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)) {
                    //TODO 退款冲正

                }
                dto.setIsOrderExceptionProc("1"); //0-不需要异常处理；1-需要异常处理；
                dto.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL);
                dto.setOrderNo(refundOrderNo);
                dto.setIsPayFlowExceptionProc("1"); //0-不需要异常处理；1-需要异常处理；
                dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL); //交易失败
            }

        } else { //如支付平台退款订单号为空，说明在生成退款订单时或之前出现异常，这种情况下无需处理退款支付订单和退款支付流水
            dto.setIsOrderExceptionProc("0"); //0-不需要异常处理；1-需要异常处理；
            dto.setIsPayFlowExceptionProc("0"); //0-不需要异常处理；1-需要异常处理；
        }

        return dto;
    }
}
