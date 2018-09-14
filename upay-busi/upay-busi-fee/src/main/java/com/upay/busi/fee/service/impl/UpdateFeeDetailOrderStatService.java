package com.upay.busi.fee.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.fee.service.dto.UpdateFeeDetailOrderStatDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.fee.FeeDetailPo;


/**
 * 修改手续费交易状态
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午3:12:20
 */
public class UpdateFeeDetailOrderStatService extends AbstractDipperHandler<UpdateFeeDetailOrderStatDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public UpdateFeeDetailOrderStatDto execute(UpdateFeeDetailOrderStatDto dto, Message message)
            throws Exception {
        String orderNo = dto.getOrderNo();
        String txnStat = dto.getTxnStat();
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if (StringUtils.isBlank(txnStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易状态");
        }
        FeeDetailPo fee = new FeeDetailPo();
        fee.setOrderNo(orderNo);
        FeeDetailPo f = daoService.selectOne(fee);
        if (f != null) {
            FeeDetailPo param = new FeeDetailPo();
            param.setTxnStat(txnStat);
            daoService.update(param, fee);
        }
        return dto;
    }

}
