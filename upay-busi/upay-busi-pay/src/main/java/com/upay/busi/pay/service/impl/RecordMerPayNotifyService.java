/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RecordMerPayNotifyDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.mer.MerNotifiyPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author shang
 * 2017年1月4日
 */
public class RecordMerPayNotifyService extends AbstractDipperHandler<RecordMerPayNotifyDto> {

    @Resource
    IDaoService daoService;
    
    @Resource
    private ISequenceService sequenceService;
    
    @Override
    public RecordMerPayNotifyDto execute(RecordMerPayNotifyDto dto, Message message) throws Exception {
        if(StringUtils.isBlank(dto.getOrderNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        PayOrderListPo order=new PayOrderListPo();
        order.setOrderNo(dto.getOrderNo());
        order=daoService.selectOne(order);
        if(order==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, dto.getOrderNo());
        }
        String transStat=null;
        if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(order.getOrderStat())
                ||DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(order.getOrderStat())){
            transStat=CommonBaseConstans_PAY.MER_PAY_NOTIFY_STAT_SUC;
            if(transStat!=null&&StringUtils.isNotBlank(order.getNotifyUrl())){            
                String notifyId=sequenceService.generateNotifyId("NOTIFY");
                StringBuffer buf=new StringBuffer();
                SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMddHHmmss");
                buf.append("payServicType=").append(order.getPayServicType()).append("&")
                .append("transStat=").append(transStat).append("&")
                .append("chnlId=").append(order.getChnlId()).append("&")
                .append("merNo=").append(order.getMerNo()).append("&")
                .append("secMerNo=").append(order.getSecMerNo()).append("&")
                .append("outerOrderNo=").append(order.getOuterOrderNo()).append("&")
                .append("orderNo=").append(order.getOrderNo()).append("&")
                .append("transAmt=").append(order.getTransAmt().setScale(2, BigDecimal.ROUND_HALF_UP)).append("&")
                .append("CURR=").append(order.getCurr()).append("&")
                .append("notifyId=").append(notifyId).append("&")
                .append("timeEnd=").append(order.getPayTime()==null?null:SIM_YMD.format(order.getPayTime())).append("&")
                .append("otherTranAmt=").append(order.getOtherTranAmt()==null?0.00:order.getOtherTranAmt().setScale(2, BigDecimal.ROUND_HALF_UP)).append("&")
                .append("productAmt=").append(order.getProductAmt()==null?0.00:order.getProductAmt()).append("&")
                .append("transComments=").append(order.getTransComments());
                MerNotifiyPo notify=new MerNotifiyPo();
                notify.setMerNo(order.getMerNo());
                notify.setTransDate(order.getOrderDate());
                notify.setNotifyType(DataBaseConstants_PAY.T_MER_NOTIFY_TYPE);
                notify.setNotifyStatus(DataBaseConstants_PAY.T_MER_NOTIFY_STATUS_FALSE);
                notify.setTransSeq(order.getOrderNo());
                notify.setPlain(buf.toString());
                notify.setMerUrl(order.getNotifyUrl());
                notify.setSendTimes(0);
                notify.setOuterOrderNo(order.getOuterOrderNo());
                notify.setNotifyId(notifyId);
                int num=daoService.insert(notify);
                if(num!=1){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0100, "登记支付结果通知");
                }
            }
        }
        return dto;
    }

}
