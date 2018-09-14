package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.QueryQRCodeParmDto;
import com.upay.dao.po.mer.MerBaseInfoPo;

public class QueryQRCodeParmService extends
		AbstractDipperHandler<QueryQRCodeParmDto> {
	@Resource
	IDaoService daoService;

	@Override
	public QueryQRCodeParmDto execute(QueryQRCodeParmDto dto, Message mes)
			throws Exception {
		dto.setTransCode("SI_PAY3009");
		dto.setChnlId("02");
		dto.setSpbillCreateIp("127.0.0.1");
		String orderNo = dto.getOrderNo();
		MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
		merBaseInfoPo.setQrOrderNo(orderNo);
		MerBaseInfoPo merBaseInfo = daoService.selectOne(merBaseInfoPo);
		dto.setMerNo(merBaseInfo.getMerNo());
		dto.setTransAmtStr(dto.getTxnAmt());
		dto.setUserId(merBaseInfo.getUserId());
		return dto;
	}

}
