package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.InitCoreThirdInfoDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;

/**
 * Esb渠道判断，调用核心接添加参数 为本行网银他行卡充值 添加
 */
public class InitCoreThirdInfoService extends
		AbstractDipperHandler<InitCoreThirdInfoDto> {
	private static final Logger LOG = LoggerFactory.getLogger(InitCoreThirdInfoService.class);
	@Resource
	private IDaoService daoService;

	@Override
	public InitCoreThirdInfoDto execute(InitCoreThirdInfoDto dto, Message msg)
			throws Exception {
		String chnlTp = dto.getChnlTp();
		LOG.info("chnlTp : "+chnlTp);
		if (CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(chnlTp)) {
			dto.setThirdFlag(DataBaseConstants_PAY.THIRD_FLAG);
			dto.setThirdAccount(dto.getPayerAcctNo());
			
			LOG.info("thirdFlag : "   +dto.getThirdFlag()+  "    thirdAccount ："+dto.getThirdAccount());
		}
		return dto;
	}

}
