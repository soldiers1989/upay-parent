package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.NotifyAcpStatusCheckDto;
import com.upay.dao.po.pay.PayFlowListPo;
/**
 * 查询是否含有流水
 *
 */
public class CheckFlowListPoService extends
AbstractDipperHandler<NotifyAcpStatusCheckDto> {
	@Resource
	private IDaoService daoService;
	
	@Override
	public NotifyAcpStatusCheckDto execute(NotifyAcpStatusCheckDto dto,
			Message msg) throws Exception {
		String transSubSeq = dto.getOrderNo();
		PayFlowListPo flowListPo = new PayFlowListPo();
		flowListPo.setTransSubSeq(transSubSeq);
		flowListPo = daoService.selectOne(flowListPo);
		if(flowListPo!=null){
		dto.setTransSubSeq(flowListPo.getTransSubSeq());
		}
		return dto;
	}

}
