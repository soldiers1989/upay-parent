package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;

import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RefundOrderDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 生成并保存退款订单信息
 * @author zhangjianfeng
 * @since 2016/08/19 00:50
 */
public class RecordRefundOrderService extends AbstractDipperHandler<RefundOrderDto> {

    private static final Logger LOG = LoggerFactory.getLogger(RecordRefundOrderService.class);

    @Resource
    private IDaoService daoService;

    @Resource
    private ISequenceService sequenceService;

    @Override
    public RefundOrderDto execute(RefundOrderDto dto, Message message) throws Exception {
        PayOrderListPo refundOrder = new PayOrderListPo();
//        if("1".equals(dto.getIsResubmitRefund())) {//重新提交
//            refundOrder.setOrderNo(dto.getRefundOrderNo());
//            refundOrder.setMerNo(dto.getMerNo());
//            refundOrder.setOuterOrderNo(dto.getMerRefundOrderNo());
//            refundOrder = daoService.selectOne(refundOrder);
//        } else {
//            String refundOrderNo = sequenceService.generateOrderNo("UPAY"); //支付平台生成的退款订单的订单号
//            refundOrder.setOrderNo(refundOrderNo);
//            dto.setRefundOrderNo(refundOrderNo);
//        }
        String refundOrderNo = sequenceService.generateOrderNo("UPAY"); //支付平台生成的退款订单的订单号
        refundOrder.setOrderNo(refundOrderNo);
        dto.setOrderNo(refundOrderNo);
        refundOrder.setChnlId(dto.getChnlId()); //渠道标认
        Date merDate = StringUtils.isNotBlank(dto.getMerDate()) ? DateUtil.parse(dto.getMerDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD) : null;
        refundOrder.setMerDate(merDate);
        refundOrder.setMerSeq(dto.getMerSeq());
        refundOrder.setMerNo(dto.getMerNo());
        refundOrder.setSecMerNo(dto.getSecMerNo());
        refundOrder.setOuterOrderNo(dto.getOuterOrderNo());
        Date merTime = null;
        if(StringUtils.isNotBlank(dto.getMerDate()) && StringUtils.isNotBlank(dto.getMerTime())) {
            merTime = DateUtil.parse(dto.getMerDate()+dto.getMerTime(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1);
        }
        refundOrder.setOuterOrderStartDate(merTime);
        refundOrder.setOrderType(DataBaseConstants_PAY.T_ORDER_LIST_TYPE_OUT_ONLINE);
        refundOrder.setPayType(DataBaseConstants_PAY.T_PAY_TYPE_REFUND_AMOUNT);
        refundOrder.setOrderDes(dto.getRefundDes());//退款说明
        refundOrder.setTransCode(dto.getTransCode());
        refundOrder.setUserId(dto.getOriUserId());
        refundOrder.setOrderDate(dto.getSysDate());
        refundOrder.setOrderTime(dto.getSysTime());
        refundOrder.setOrderLmtTime(0);
        refundOrder.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        refundOrder.setStlFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        refundOrder.setChkFlag(DataBaseConstants_BATCH.T_STL_FLAG_NO);
        refundOrder.setCurr(dto.getOriCcy());
        refundOrder.setTransAmt(dto.getUserTransAmt());
        refundOrder.setOriDate(dto.getOriTransDate());
        refundOrder.setOriOrderNo(dto.getOriOrderNo());
        refundOrder.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING);
        refundOrder.setSpbillCreateIp(dto.getSpbillCreateIp());//用户ip
        refundOrder.setNotifyUrl(URLDecoder.decode(dto.getNotifyUrl()==null?"":dto.getNotifyUrl(),"UTF-8"));//异步
        refundOrder.setLastUpdateTime(new Date());
        refundOrder.setTransType(CommonConstants_GNR.SYS_TRANS_TYPE_REF);
        refundOrder.setPromoterDeptNo(dto.getPromoterDeptNo());
        if(StringUtils.isNotBlank(dto.getOriOrderName())){
            refundOrder.setOrderName(dto.getOriOrderName());
        }
        daoService.insert(refundOrder);
//        if("1".equals(dto.getIsResubmitRefund())) {//重新提交
//            PayOrderListPo param=new PayOrderListPo();
//            param.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING);
//            PayOrderListPo where=new PayOrderListPo();
//            where.setOrderNo(refundOrder.getOrderNo());
//            daoService.update(param,where);
//        } else {
//            daoService.insert(refundOrder);
//        }
        LOG.debug("保存商户[{}]退款订单，订单号[{}]，原交易订单号[{}]", new Object[]{dto.getMerNo(), refundOrder.getOrderNo(), dto.getOriOrderNo()});

        return dto;
    }
}