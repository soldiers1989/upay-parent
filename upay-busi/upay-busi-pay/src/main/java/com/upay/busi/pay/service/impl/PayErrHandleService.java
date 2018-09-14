package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayErrHandleDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowListPo;

import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 获取交易流水的状态，和通道方交易信息
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午6:58:51
 */
public class PayErrHandleService extends AbstractDipperHandler<PayErrHandleDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public PayErrHandleDto execute(PayErrHandleDto dto, Message message) throws Exception {
        String orderNo = dto.getOrderNo();
        String bkSerialNo=dto.getBkSerialNo();
        String routeDate="";
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        
        if(dto.getIsEsbCore().equals("N")){
        	routeDate=dto.getBkDate();
        }else{
            if(StringUtils.isNotBlank(dto.getMachineDate())){
        	routeDate=dto.getMachineDate();
            }
        }
        
        String code=message.getFault().getCode();
        SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
        if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)){
            String routeCode=dto.getRouteCode();
            if(StringUtils.isBlank(routeCode)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "通道代码");
            }
            //核心
            if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
                if(StringUtils.isBlank(routeDate)){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "核心日期");
                }
                if(StringUtils.isBlank(bkSerialNo)){
                    ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "核心状态码");
                }
                dto.setRouteSeq(bkSerialNo);
                dto.setRouteDate(routeDate);
                if(CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(dto.getRespCode())){
                    dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                    dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                }else{
                    dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                    dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                }
            }
            //银联
            if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode)) {
                //退款
                if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(dto.getTransType() == null ? "" : dto.getTransType())) {
                    dto.setRouteDate(SIM_YMD.format(new Date()));
                    if (CommonBaseConstans_PAY.UNION_REFUND_SUCCESS.equals(dto.getRespCode() == null ? "" : dto.getRespCode())) {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    } else {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }
                } else if (CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT.equals(dto.getTransType() == null ? "" : dto.getTransType())) {
                    dto.setRouteDate(SIM_YMD.format(new Date()));
                    if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(dto.getRespCode() == null ? "" : dto.getRespCode())) {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    } else {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }
                }
                //代收 无跳转支付
                else if (CommonConstants_GNR.SYS_TRANS_TYPE_COLLECTION.equals(dto.getTransType() == null ? "" : dto.getTransType())) {
                    dto.setRouteDate(SIM_YMD.format(new Date()));
                    //银联同步返回  00  表示受理成功  不代表 交易成功
                    if (CommonBaseConstans_PAY.UNION_PAYMENT_SUCCESS.equals(dto.getRespCode() == null ? "" : dto.getRespCode())) {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    } else {
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }
                }
            }

            //微信
            if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS.equals(routeCode)){
                if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(dto.getTransType()==null?"":dto.getTransType())){
                    dto.setRouteDate(SIM_YMD.format(new Date()));
                    if(CommonBaseConstans_PAY.WEIXIN_REFUND_SUCCESS.equals(dto.getReturnCode()==null?"":dto.getReturnCode())){
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    }else{
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }
                }
            }
            //支付宝
            if(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY.equals(routeCode)){
                if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(dto.getTransType()==null?"":dto.getTransType())){
                	
                	dto.setRouteDate(SIM_YMD.format(DateUtil.parse(dto.getGmtRefundPay(), "yyyy-MM-dd")));
                    dto.setRouteSeq(dto.getTradeNo());
                    if(DataBaseConstants_PAY.PAY_ALIPAY_SUCC.equals(dto.getReturnCode()==null?"":dto.getReturnCode()) && DataBaseConstants_PAY.FUND_CHANGE_Y.equals(dto.getFundChange())){
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    }else{
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }
                }
            }
            //中金快捷
            if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
                if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(dto.getTransType()==null?"":dto.getTransType())){
                    dto.setRouteDate(SIM_YMD.format(new Date()));
                    if(CommonBaseConstans_PAY.ZJ_REFUND_SUCCESS.equals(dto.getRespCode()==null?"":dto.getRespCode())){
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    }else{
                        dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    }
                }else{
                    if(StringUtils.isBlank(dto.getPaymentNo())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "中金流水号");
                    }
                    if(StringUtils.isBlank(dto.getStatus())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "中金状态");
                    }
                    if(StringUtils.isBlank(dto.getBankTxTime())){
                    	//当中金为处理中的情况时，处理时间为空，固要加一个默认时间
                    	dto.setBankTxTime(DateUtil.format(new Date(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS_1));
                    }
                    if(StringUtils.isBlank(dto.getBankTxTime())){
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "中金银行处理时间");
                    }
                    dto.setRouteSeq(dto.getPaymentNo());
                    dto.setRouteDate(dto.getBankTxTime().substring(0,8));
                    if(CommonConstants_GNR.ZJ_TRANS_CODE_4530.equals(dto.getZjTransCode()==null?"":dto.getZjTransCode())
                    		||CommonConstants_GNR.ZJ_TRANS_CODE_2011.equals(dto.getZjTransCode()==null?"":dto.getZjTransCode())){
                    	//代收代付 中金返回结果处理
                        if(CommonConstants_GNR.ZJ_C_PAY_STAT_SUCCESS.equals(dto.getStatus())){
                            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                        }
                        if(CommonConstants_GNR.ZJ_C_PAY_STAT_ING.equals(dto.getStatus())){
                            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
                        }
                        if(CommonConstants_GNR.ZJ_C_PAY_STAT_FAIL.equals(dto.getStatus())){
                            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                        }
                    }else{
                        if(CommonConstants_GNR.ZJ_Q_PAY_STAT_SUCCESS.equals(dto.getStatus())){
                            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                        }
                        if(CommonConstants_GNR.ZJ_Q_PAY_STAT_ING.equals(dto.getStatus())){
                            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
                        }
                        if(CommonConstants_GNR.ZJ_Q_PAY_STAT_FAIL.equals(dto.getStatus())){
                            dto.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                        }
                    }
                }
            }
        }else{
            if(StringUtils.isNotBlank(dto.getTransSubSeq())){
                PayFlowListPo pay = new PayFlowListPo();
                pay.setTransSubSeq(dto.getTransSubSeq());
                pay = daoService.selectOne(pay);
                if(pay != null&&!DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(pay.getTransStat())){                    
                    if (pay != null&&DataBaseConstants_PAY.T_PAY_TX_BEGIN.equals(pay.getTransStat())) {
                        String timeOut = dto.getRouteTimeout();
                        if (timeOut != null && CommonConstants_GNR.ROUTE_TIME_OUT_VALUE.equals(timeOut)) {
                            dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
                        } else {
                        	if(CommonConstants_GNR.OUT_PAY_STAT_ING.equals(dto.getRouteTransStat())){
                        		dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
                        	}else{
                        		dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                        	}
                            
                        }
                    }else{
                        ExInfo.throwDipperEx(AppCodeDict.BISPAY0034, orderNo);
                    }
                }
            }
        }
        return dto;
    }

}
