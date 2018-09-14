/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayOrderInfoQryOfWeiXinDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 外部微信公众号订单信息查询
 * 
 * @author zhanggr
 *
 */
public class PayOrderInfoQryOfWeiXinService extends AbstractDipperHandler<PayOrderInfoQryOfWeiXinDto> {

    @Resource
    IDaoService daoService;


    @Override
    public PayOrderInfoQryOfWeiXinDto execute(PayOrderInfoQryOfWeiXinDto payOrderInfoQryOfWeiXinDto,
            Message msg) throws Exception {
        String orderNo = payOrderInfoQryOfWeiXinDto.getOrderNo();
        String outerOrderNo = payOrderInfoQryOfWeiXinDto.getOuterOrderNo();
        if (StringUtils.isBlank(outerOrderNo) && StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0052);
        }
        PayOrderListPo payOrderList = new PayOrderListPo();
        if (StringUtils.isNotBlank(orderNo) && StringUtils.isNotBlank(outerOrderNo)) {
            payOrderList.setOrderNo(outerOrderNo);
            payOrderList.setOuterOrderNo(outerOrderNo);
        } else if (StringUtils.isNotBlank(orderNo) && StringUtils.isBlank(outerOrderNo)) {
            payOrderList.setOrderNo(orderNo);
        } else {
            payOrderList.setOuterOrderNo(outerOrderNo);
        }
        payOrderList = daoService.selectOne(payOrderList);
        if (payOrderList == null) {// 不存在
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0051);
        } else {
            SimpleDateFormat SIM_YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
            payOrderInfoQryOfWeiXinDto.setMerNo(payOrderList.getMerNo());
            payOrderInfoQryOfWeiXinDto.setOrderStat(payOrderList.getOrderStat());
            payOrderInfoQryOfWeiXinDto.setCurr(payOrderList.getCurr());
            payOrderInfoQryOfWeiXinDto.setOrderDes(payOrderList.getOrderDes());
            payOrderInfoQryOfWeiXinDto.setOrderName(payOrderList.getOrderName());
            payOrderInfoQryOfWeiXinDto.setOuterOrderNo(payOrderList.getOuterOrderNo());
            payOrderInfoQryOfWeiXinDto.setOrderNo(payOrderList.getOrderNo());
            payOrderInfoQryOfWeiXinDto.setTransAmt(payOrderList.getTransAmt());
            payOrderInfoQryOfWeiXinDto.setPayServicType(payOrderList.getPayServicType());
            payOrderInfoQryOfWeiXinDto.setMerDate(payOrderList.getMerDate());
            payOrderInfoQryOfWeiXinDto.setSecMerNo(payOrderList.getSecMerNo());
            payOrderInfoQryOfWeiXinDto.setOuterOrderStartDate(payOrderList.getOuterOrderStartDate()==null?"":SIM_YMDHMS.format(payOrderList.getOuterOrderStartDate()));
            payOrderInfoQryOfWeiXinDto.setOuterOrderEndDate(payOrderList.getOuterOrderEndDate()==null?"":SIM_YMDHMS.format(payOrderList.getOuterOrderEndDate()));
            payOrderInfoQryOfWeiXinDto.setOrderDate(payOrderList.getOrderDate());
            payOrderInfoQryOfWeiXinDto.setOrderTime(payOrderList.getOrderTime()==null?"":SIM_YMDHMS.format(payOrderList.getOrderTime()));
            payOrderInfoQryOfWeiXinDto.setSpbillCreateIp(payOrderList.getSpbillCreateIp());
            payOrderInfoQryOfWeiXinDto.setNotifyUrl(payOrderList.getNotifyUrl());
            payOrderInfoQryOfWeiXinDto.setTransComments(payOrderList.getTransComments());
            payOrderInfoQryOfWeiXinDto.setOpenId(payOrderList.getOpenId());
            payOrderInfoQryOfWeiXinDto.setPayTime(payOrderList.getPayTime());
            payOrderInfoQryOfWeiXinDto.setMerFeeAmt(payOrderList.getMerFeeAmt());
            
            PayFlowListPo po=new PayFlowListPo();
            po.setOrderNo(payOrderList.getOrderNo());
            po.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_WECHAT);
            List<PayFlowListPo> selectList = daoService.selectList(po);
            if(selectList!=null&&selectList.size()>0){
            	PayFlowListPo payFlowListPo = selectList.get(0);
            	if(payFlowListPo!=null){
            		payOrderInfoQryOfWeiXinDto.setBankType(payFlowListPo.getPayerBankName());
            	}
            }
            
        }

        return payOrderInfoQryOfWeiXinDto;
    }

}
