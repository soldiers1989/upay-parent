package com.upay.busi.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.AdjustFlowListServiceDto;
import com.upay.busi.pay.service.dto.CheckErrTypeServiceDto;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.pay.PayFlowListHisPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 差错处理-调整平台流水状态
 * 
 * @author yhy 20170516
 * 
 */
public class AdjustFlowListService extends AbstractDipperHandler<AdjustFlowListServiceDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(AdjustFlowListService.class);


	@Override
	public AdjustFlowListServiceDto execute(AdjustFlowListServiceDto dto, Message message)
			throws Exception {
		String errFlowSeq = dto.getErrFlowSeq();
		String dealType = dto.getDealType();
		String chkStat = dto.getChkStat();
		String dealFlowStat = dto.getDealFlowStat(); //流水状态
		if (StringUtils.isBlank(errFlowSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错平台流水号");
        }
		if (StringUtils.isBlank(dealType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错处理方式");
        }
		if (StringUtils.isBlank(chkStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "对账状态");
        }
		
		PayFlowListPo payFlowListWhere = new PayFlowListPo();
		payFlowListWhere.setTransSubSeq(errFlowSeq);
		PayFlowListPo payFlowListParam = new PayFlowListPo();
		payFlowListParam.setTransStat(dealFlowStat);
		daoService.update(payFlowListParam, payFlowListWhere);
		
		dto.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		
		return dto;
	}
}
