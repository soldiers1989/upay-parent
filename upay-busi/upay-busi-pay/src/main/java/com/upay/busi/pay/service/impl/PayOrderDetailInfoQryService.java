/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
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
import com.upay.busi.pay.service.dto.PayOrderDetailInfoQryDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 订单详情查询
 * 
 * @author zhanggr
 * 
 */
public class PayOrderDetailInfoQryService extends AbstractDipperHandler<PayOrderDetailInfoQryDto> {

    @Resource
    private IDaoService daoService;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public PayOrderDetailInfoQryDto execute(PayOrderDetailInfoQryDto dto, Message msg) throws Exception {
        // 获取订单号
        String orderNo = dto.getOrderNo();
        // 订单号非空判断
        if (StringUtils.isBlank(orderNo)) {
            logger.debug("-----------------------------------订单号是否为空");
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        // 支付是否超时判定compareTo(date) == -1)
        PayOrderListPo order = new PayOrderListPo();
        order.setOrderNo(orderNo);
        // 获取订单信息
        order = daoService.selectOne(order);
        // 订单信息非空判断
        if (order == null) {
            logger.debug("-----------------------------------订信息是否为空");
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "订单信息");

        } else {
            SimpleDateFormat sim1 = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMD);
            SimpleDateFormat sim2 = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
            SimpleDateFormat sim3 = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_Y_M_D_H_M_S);
            dto.setOrderNo(orderNo);
            dto.setOrderName(order.getOrderName()); // 返回订单名称
            Date orderTime = order.getOrderTime();
            if (null != orderTime) {
                dto.setOrderCreateTime(sim3.format(orderTime));// 返回订单日期
            }
            BigDecimal transAmt = order.getTransAmt();
            if (null != transAmt) {
                dto.setTransAmt(MoneyUtil.moneyFormat(transAmt)); // 返回交易交易金额
            }

            dto.setOrderStat(order.getOrderStat()); // 返回订单状态
            Date merDate = order.getMerDate();
            if (null != merDate) {
                dto.setMerOrderDate(sim1.format(merDate));
            }
            dto.setOrderLmtTime(order.getOrderLmtTime());// 返回订单时效
            dto.setMerSeq(order.getMerSeq());
            dto.setMerNo(order.getMerNo());
            dto.setSecMerNo(order.getSecMerNo());
            dto.setMerOrderNo(order.getOuterOrderNo());
            Date outerOrderStartDate = order.getOuterOrderStartDate();
            if (null != outerOrderStartDate) {
                dto.setMerOrderTime(sim2.format(outerOrderStartDate));
            }
            Date outerOrderEndDate = order.getOuterOrderEndDate();
            if (null != outerOrderEndDate) {
                dto.setMerOrderEndTime(sim2.format(outerOrderEndDate));
            }

            dto.setOrderType(order.getOrderType());
            dto.setOrderDes(order.getOrderDes());
            dto.setOrderTransCode(order.getTransCode());
            if (null != order.getOrderLmtTime()) {
                dto.setOrderValidTime(order.getOrderLmtTime().toString());
            }

            dto.setOrderCurr(order.getCurr());
            dto.setTransAmt(order.getTransAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        return dto;
    }

}
