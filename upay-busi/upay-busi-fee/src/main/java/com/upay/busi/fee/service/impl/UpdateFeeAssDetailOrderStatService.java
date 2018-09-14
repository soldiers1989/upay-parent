package com.upay.busi.fee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.fee.service.dto.UpdateFeeAssDetailOrderStatDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.fee.FeeAssDetailPo;


/**
 * 更新分润明细表的交易状态
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月23日 下午4:22:05
 */
public class UpdateFeeAssDetailOrderStatService extends AbstractDipperHandler<UpdateFeeAssDetailOrderStatDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public UpdateFeeAssDetailOrderStatDto execute(UpdateFeeAssDetailOrderStatDto dto, Message message)
            throws Exception {
        String orderNo = dto.getOrderNo();
        String txnStat = dto.getTxnStat();
        if (StringUtils.isBlank(txnStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易状态");
        }
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        FeeAssDetailPo ass = new FeeAssDetailPo();
        ass.setOrderNo(orderNo);
        List<FeeAssDetailPo> assList = daoService.selectList(ass);
        if (assList != null && assList.size() > 0) {
            FeeAssDetailPo param = new FeeAssDetailPo();
            param.setTxnStat(txnStat);
            FeeAssDetailPo where = new FeeAssDetailPo();
            where.setOrderNo(orderNo);
            daoService.update(param, where);
        }
        return dto;
    }

}
