package com.upay.busi.gnr.service.impl;


import java.util.List;

import javax.annotation.Resource;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.gnr.service.dto.AreaCodeGetSubDto;
import com.upay.busi.gnr.service.dto.AreaCodeListGetDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.BaseMappingUtils;
import com.upay.dao.po.gnr.GnrAreaCodePo;

/**
 * 获取地区码对应地区集合
 * @author liubing
 *
 */
public class AreaCodeListGetServiceImpl extends AbstractDipperHandler<AreaCodeListGetDto> {
	@Resource
	private IDaoService daoService;
	
	@Override
	public AreaCodeListGetDto execute(AreaCodeListGetDto areaCodeListGetDto, Message message)
			throws Exception {
		
		String areaCode = areaCodeListGetDto.getAreaCode();
		GnrAreaCodePo gnrAreaCodePo = new GnrAreaCodePo();
		gnrAreaCodePo.setAreaCode(areaCode);
		gnrAreaCodePo = daoService.selectOne(gnrAreaCodePo);
		if(gnrAreaCodePo == null){
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0012, areaCode);
		}
		String upAreaCode = gnrAreaCodePo.getUpAreaCode();
		
		//获取区(县)代码集合
		gnrAreaCodePo = new GnrAreaCodePo();
		gnrAreaCodePo.setUpAreaCode(upAreaCode);
		gnrAreaCodePo.addOrder(Order.asc("areaCode"));
		List<GnrAreaCodePo> countyList = daoService.selectList(gnrAreaCodePo);
		areaCodeListGetDto.setCountyList((List<AreaCodeGetSubDto>)BaseMappingUtils.populateTListbyDListByApache(countyList, AreaCodeGetSubDto.class));

		//获取市代码集合
		gnrAreaCodePo = new GnrAreaCodePo();
		gnrAreaCodePo.setAreaCode(upAreaCode);
		gnrAreaCodePo = daoService.selectOne(gnrAreaCodePo);
		if(gnrAreaCodePo == null){
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0012, upAreaCode);
		}
		upAreaCode = gnrAreaCodePo.getUpAreaCode();
		gnrAreaCodePo = new GnrAreaCodePo();
		gnrAreaCodePo.setUpAreaCode(upAreaCode);
		gnrAreaCodePo.addOrder(Order.asc("areaCode"));
		List<GnrAreaCodePo> cityList = daoService.selectList(gnrAreaCodePo);
		areaCodeListGetDto.setCityList((List<AreaCodeGetSubDto>)BaseMappingUtils.populateTListbyDListByApache(cityList, AreaCodeGetSubDto.class));

		//获取省代码集合
		gnrAreaCodePo.setUpAreaCode(CommonConstants_GNR.AREA_CODE_TOP);
		gnrAreaCodePo.addOrder(Order.asc("areaCode"));
		List<GnrAreaCodePo> provinceList = daoService.selectList(gnrAreaCodePo);
		areaCodeListGetDto.setProvinceList((List<AreaCodeGetSubDto>)BaseMappingUtils.populateTListbyDListByApache(provinceList, AreaCodeGetSubDto.class));

		return areaCodeListGetDto;
	}

	public void setDaoService(IDaoService daoService) {
		this.daoService = daoService;
	}

}
