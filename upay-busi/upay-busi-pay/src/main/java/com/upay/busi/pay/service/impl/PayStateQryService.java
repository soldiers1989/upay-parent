package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.PayStateQryDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;


/**
 * 支付流水号查询订单状态--单笔
 * 
 * @author liu
 * 
 */
public class PayStateQryService extends AbstractDipperHandler<PayStateQryDto> {

    private static final Logger LOG = LoggerFactory.getLogger(PayStateQryService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public PayStateQryDto execute(PayStateQryDto sysSeqCheckDto, Message msg) throws Exception {

        String sysSeq = sysSeqCheckDto.getSysSeq();
        if (StringUtils.isBlank(sysSeq)) { // 流水号不能为空
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "流水号");
        }

        PayFlowListPo payFlowListPo = new PayFlowListPo();
        payFlowListPo.setTransSubSeq(sysSeq);

        payFlowListPo = daoService.selectOne(payFlowListPo);
        if (payFlowListPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "流水号");
        }

        sysSeqCheckDto.setSysSeq(sysSeq);
        sysSeqCheckDto.setTransStat(payFlowListPo.getTransStat());
        sysSeqCheckDto.setMerNo(payFlowListPo.getMerNo());
        sysSeqCheckDto.setSubMerNo(payFlowListPo.getSecMerNo());

        return sysSeqCheckDto;
    }
}
