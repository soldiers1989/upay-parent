package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.UpateUnionPayFlowServiceDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.pay.PayFlowListPo;

/**
 *
 *修改银联支付流水状态为支付中
 *
 *
 */
public class UpateUnionPayFlowService extends AbstractDipperHandler<UpateUnionPayFlowServiceDto> {

    private static final Logger LOG = LoggerFactory.getLogger(UpateUnionPayFlowService.class);

    @Resource
    private IDaoService daoService;

    @Resource
    private ISequenceService sequenceService;

    @Override
    public UpateUnionPayFlowServiceDto execute(UpateUnionPayFlowServiceDto dto, Message message) throws Exception {
        String transSubSeq = dto.getTransSubSeq();
    	if(StringUtils.isBlank(transSubSeq)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "银联流水号");
        }
    	PayFlowListPo payFlowListParam = new PayFlowListPo();
    	payFlowListParam.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
        if(StringUtils.isNotBlank(dto.getSettleKey())){
    	payFlowListParam.setSettleKey(dto.getSettleKey());
        }
        payFlowListParam.setRouteSeq(dto.getOriRouteSeq());
    	PayFlowListPo payFlowListWhere = new PayFlowListPo();
    	payFlowListWhere.setTransSubSeq(transSubSeq);
    	
    	daoService.update(payFlowListParam, payFlowListWhere);
    	
    	return dto;
    }
}
