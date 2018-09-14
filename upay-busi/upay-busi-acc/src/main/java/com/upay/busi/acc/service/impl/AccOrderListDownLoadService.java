package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.dao.po.usr.UsrRegInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.BeanCopyUtil;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOrderListQueryDto;
import com.upay.busi.acc.service.dto.AccOrderListQuerySubDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * Created by xuxin 账户交易明细下载
 */
public class AccOrderListDownLoadService extends AbstractDipperHandler<AccOrderListQueryDto> {
    private static final Logger LOG = LoggerFactory.getLogger(AccOrderListDownLoadService.class);
    @Resource
    private IDaoService daoService;


    @Override
    public AccOrderListQueryDto execute(AccOrderListQueryDto payOrderListQueryDto, Message message)
            throws Exception {
        LOG.info("=====用户交易明细下载开始====");
        String userId = payOrderListQueryDto.getUserId();
        List<Map<String, Object>> transList = new ArrayList<Map<String, Object>>();
        DecimalFormat df = new DecimalFormat(".00");
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "用户Id");
        }
        payOrderListQueryDto.setSysDate(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat(DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS);
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != payOrderListQueryDto.getStartDate() && null != payOrderListQueryDto.getEndDate()) {
            SimpleDateFormat sdfFormat = new SimpleDateFormat(DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdfFormat.parse(payOrderListQueryDto.getStartDate()));
            cal.set(Calendar.HOUR, 00);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
            payOrderListQueryDto.setQueryStart(cal.getTime());
            cal.setTime(sdfFormat.parse(payOrderListQueryDto.getEndDate()));
            cal.set(Calendar.HOUR, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            payOrderListQueryDto.setQueryEnd(cal.getTime());
        }
        map.put("orderStartDate", payOrderListQueryDto.getQueryStart());
        map.put("orderEndDate", payOrderListQueryDto.getQueryEnd());
        LOG.info(" queryBegin = " + payOrderListQueryDto.getQueryStart() + " queryEnd = "
                + payOrderListQueryDto.getQueryEnd());
        map.put("transType", payOrderListQueryDto.getTransType());

        map.put("secMerNo", payOrderListQueryDto.getSecMerNo());
        map.put("routeCode", payOrderListQueryDto.getRouteCode());

        map.put("orderNo",payOrderListQueryDto.getOrderNo());
        map.put("outerOrderNo",payOrderListQueryDto.getOuterOrderNo());
        map.put("stlFlag",payOrderListQueryDto.getStlFlag());


        List<String> orderStats = new ArrayList<String>();
        if (null == payOrderListQueryDto.getOrderStat()) {
            orderStats.add("0");
            orderStats.add("1");
            orderStats.add("2");
            orderStats.add("3");
            orderStats.add("4");
            orderStats.add("5");
            orderStats.add("6");
            orderStats.add("7");
            orderStats.add("8");
            orderStats.add("9");// 退款中*（基本不会显示）
            orderStats.add("10");// 退款失败*
            orderStats.add("11");// 退款成功*
            orderStats.add("12");// 部分退款*
            orderStats.add("13");// 全额退款*
        } else {
            String orderStat = payOrderListQueryDto.getOrderStat();
            if ("0".equals(orderStat)) {
                orderStats.add("0");
                orderStats.add("8");
                orderStats.add("6");
                orderStats.add("11");
                orderStats.add("12");
                orderStats.add("13");
            } else if ("1".equals(orderStat)) {
                orderStats.add("1");
                orderStats.add("2");
                orderStats.add("7");
                orderStats.add("9");
            } else if ("3".equals(orderStat)) {
                orderStats.add("3");
                orderStats.add("4");
                orderStats.add("5");
                orderStats.add("10");
            }
        }
        map.put("orderStat", orderStats);



       String merNo = payOrderListQueryDto.getMerNo();
        if (StringUtils.isNotBlank(merNo)) {
            MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
            merBaseInfoPo.setMerNo(merNo);
            merBaseInfoPo = daoService.selectOne(merBaseInfoPo);
            if (merBaseInfoPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户信息不存在");
            } else {
                map.put("merNo", merNo);
            }
        } else {
            map.put("userId", userId);
        }


//        MerBaseInfoPo merPo = new MerBaseInfoPo();
//        merPo.setUserId(userId);
//        List<MerBaseInfoPo> merBaseInfoPos = daoService.selectList(merPo);
//        List<String> merNos = new ArrayList<String>();
//        if (null != merBaseInfoPos && merBaseInfoPos.size() > 0) {
//            for (MerBaseInfoPo po : merBaseInfoPos) {
//                merNos.add(po.getMerNo());
//            }
//            map.put("merNos", merNos);
//            map.put("secMerNos", merNos);
//        }
        List<PayOrderListPo> listPayOrderListPo =
                daoService.selectList(PayOrderListPo.class.getName() + ".selectQueryResultByTs4", map);
        if (null != listPayOrderListPo && listPayOrderListPo.size() > 0) {
            for (PayOrderListPo payOrderListPo : listPayOrderListPo) {
                AccOrderListQuerySubDto accOrderListQuerySubDto = new AccOrderListQuerySubDto();
                String trans = payOrderListPo.getTransType();
                String transType = null;
                if ("01".equals(trans)) {
                    transType = "支付";
                } else if ("02".equals(trans)) {
                    transType = "充值";
                } else if ("03".equals(trans)) {
                    transType = "提现";
                } else if ("04".equals(trans)) {
                    transType = "转账";
                } else if ("09".equals(trans)) {
                    transType = "退款";
                }
                accOrderListQuerySubDto.setTransType(transType);
                accOrderListQuerySubDto.setMerNo(payOrderListPo.getMerNo());
                String stat = payOrderListPo.getOrderStat();
                if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("成功");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("处理中");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("支付中");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("失败");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("超时关闭");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_HC.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("手工关闭");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("确认收货");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_AP.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("预约支付");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("待确认收货");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("退款中");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("退款失败");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("退款成功");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("部分退款");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL.equals(stat)) {
                    accOrderListQuerySubDto.setOrderStat("全额退款");
                } else {
                    accOrderListQuerySubDto.setOrderStat("状态未知");
                }
                if ("09".equals(trans)){
                	accOrderListQuerySubDto.setOrderNo(payOrderListPo.getOriOrderNo());
                }else{
                	accOrderListQuerySubDto.setOrderNo(payOrderListPo.getOrderNo());
                }
                accOrderListQuerySubDto.setOrderName(payOrderListPo.getOrderName());
                accOrderListQuerySubDto.setMerFeeAmt(payOrderListPo.getMerFeeAmt());
                accOrderListQuerySubDto.setFeeAmt(payOrderListPo.getFeeAmt());
                accOrderListQuerySubDto.setTransAmt(new BigDecimal(df.format(payOrderListPo.getTransAmt()
                    .doubleValue())));
                accOrderListQuerySubDto.setOrderTime(sdf.format(payOrderListPo.getOrderTime()));
                transList.add(BeanCopyUtil.copyBean2MapStrObjNoClass(accOrderListQuerySubDto));
            }
        }
        LOG.debug("-----------" + transList.size());
        payOrderListQueryDto.setTransList(transList);
        LOG.info("=====用户交易明细下载结束====");
        return payOrderListQueryDto;
    }
}
