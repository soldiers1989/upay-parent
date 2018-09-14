/**
 * 
 */
package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckConfirmDeliveryDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * @author shang
 * 2016年12月16日
 * 检查 确认收货订单
 */
public class CheckConfirmDeliveryService extends AbstractDipperHandler<CheckConfirmDeliveryDto> {

    private static final Logger LOG = LoggerFactory.getLogger(CheckConfirmDeliveryService.class);
    
    @Resource
    private IDaoService daoService;
    
    @Override
    public CheckConfirmDeliveryDto execute(CheckConfirmDeliveryDto dto, Message message) throws Exception {
        String merNo = dto.getMerNo(); //商户号
        if(StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }

        String outerOrderNo = dto.getOuterOrderNo(); //商户订单号
        String orderNo = dto.getOrderNo(); //支付平台订单号
        if(StringUtils.isBlank(outerOrderNo)&&StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户订单号或订单号");
        }

        //查询订单信息
        PayOrderListPo order = new PayOrderListPo();
        if(StringUtils.isNotBlank(orderNo)){
            order.setOrderNo(orderNo);
        }else{
            order.setOuterOrderNo(outerOrderNo);
        }
        order = daoService.selectOne(order);

        //订单是否存在
        if(null == order) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0020, outerOrderNo); //订单[{}]不存在
        }
        if(!order.getMerNo().equals(dto.getMerNo())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0048, "商户");
        }
        if(StringUtils.isNotBlank(dto.getSecMerNo())&&(!(order.getSecMerNo()==null?true:dto.getSecMerNo().equals(order.getSecMerNo())))){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0048, "二级商户");
        }
        if(!DataBaseConstants_PAY.T_MER_PAY_SERVER_TYPE_GUARANTEE.equals(order.getPayServicType())){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0049, "非担保交易");
        }
        //订单状态检查
        String orderStatus = order.getOrderStat();
        LOG.debug("[{}]订单状态为[{}]", new Object[]{orderNo, orderStatus});
        if(!DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(orderStatus)) {
            if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(orderStatus)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0049, "已经确认收货");
            }else{
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0049, "未成功支付");
            }
        }
        dto.setOrderNo(order.getOrderNo());
        return dto;
    }

}
