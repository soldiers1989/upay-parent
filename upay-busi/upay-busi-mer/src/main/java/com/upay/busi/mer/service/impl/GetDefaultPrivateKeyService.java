package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.mer.service.dto.GetDefaultPrivateKeyDto;
import com.upay.commons.constants.DateBaseConstants_MER;

public class GetDefaultPrivateKeyService extends AbstractDipperHandler<GetDefaultPrivateKeyDto>{
	@Resource
	private IDaoService daoService;
	@Override
	public GetDefaultPrivateKeyDto execute(GetDefaultPrivateKeyDto dto, Message arg1) throws Exception {
		//商户平台加密参数信息
		GnrParmPo gnrParm=new GnrParmPo();
		gnrParm.setParmId(DateBaseConstants_MER.MER_DEFAULT_PRIVATE_3DSKEY);
		gnrParm = daoService.selectOne(gnrParm);
		if(gnrParm!=null){
			dto.setDefaultKey(gnrParm.getParmValue());//默认的加密串
		}
		return dto;
	}

}
