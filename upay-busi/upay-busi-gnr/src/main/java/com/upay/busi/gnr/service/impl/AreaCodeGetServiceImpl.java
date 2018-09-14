package com.upay.busi.gnr.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.gnr.service.dto.AreaCodeGetDto;
import com.upay.busi.gnr.service.dto.AreaCodeGetSubDto;
import com.upay.commons.util.BaseMappingUtils;
import com.upay.dao.po.gnr.GnrAreaCodePo;

/**
 * 地区代码获取
 * @author liubing
 *
 */
public class AreaCodeGetServiceImpl extends AbstractDipperHandler<AreaCodeGetDto> {
	@Resource
	private IDaoService daoService;
	
	@Override
	public AreaCodeGetDto execute(AreaCodeGetDto areaCodeGetDto, Message message) throws Exception {
		
		String areaCode = areaCodeGetDto.getAreaCode();
		GnrAreaCodePo gnrAreaCodePo = new GnrAreaCodePo();
		gnrAreaCodePo.setUpAreaCode(areaCode);
		gnrAreaCodePo.addOrder(Order.asc("areaCode"));
		List<GnrAreaCodePo> listGnrAreaCodePo = daoService.selectList(gnrAreaCodePo);
		List<AreaCodeGetSubDto> rows = new ArrayList<AreaCodeGetSubDto>();
		for (GnrAreaCodePo gnrAreaCodePo2 : listGnrAreaCodePo) {
			AreaCodeGetSubDto areaCodeGetSubDto = new AreaCodeGetSubDto();
			BaseMappingUtils.populateTbyDByApache(gnrAreaCodePo2, areaCodeGetSubDto);
			rows.add(areaCodeGetSubDto);
		}

		areaCodeGetDto.setAreaCodeList(rows);
		return areaCodeGetDto;
	}

	public void setDaoService(IDaoService daoService) {
		this.daoService = daoService;
	}

}
