package com.upay.busi.gnr.service.impl;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.gnr.service.dto.AreaCodeListGetDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.gnr.GnrAreaCodePo;

/**
 * 根据区（县）码获取对应的省、市代码
 * @author liubing
 *
 */
public class AreaCodeGetByCountyServiceImpl extends AbstractDipperHandler<AreaCodeListGetDto> {
	@Resource
	private IDaoService daoService;
	
	@Override
	public AreaCodeListGetDto execute(AreaCodeListGetDto areaCodeListGetDto, Message message)
			throws Exception {
		
		String areaCode = areaCodeListGetDto.getAreaCode();
		if (StringUtils.isNotBlank(areaCode)) {
		    GnrAreaCodePo gnrAreaCodePo = new GnrAreaCodePo();
	        gnrAreaCodePo.setAreaCode(areaCode);
	        gnrAreaCodePo = daoService.selectOne(gnrAreaCodePo);
	        if(gnrAreaCodePo == null){
	            ExInfo.throwDipperEx(AppCodeDict.BISGNR0012, areaCode);
	        }
	        areaCodeListGetDto.setCountyAreaName(gnrAreaCodePo.getAreaName());
	        String upAreaCode = gnrAreaCodePo.getUpAreaCode();
	        
	        //根据区(县)代码获取市级代码
	        GnrAreaCodePo areaCodePo = new GnrAreaCodePo();
	        areaCodePo.setAreaCode(upAreaCode);
	        areaCodePo.addOrder(Order.asc("areaCode"));
	        areaCodePo = daoService.selectOne(areaCodePo);
	        if(areaCodePo == null){
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0012, upAreaCode);
            }
	        areaCodeListGetDto.setCityAreaCode(areaCodePo.getAreaCode());
	        areaCodeListGetDto.setCityAreaName(areaCodePo.getAreaName());


	        //根据区(县)代码获取省级代码
	        String newUpAreaCode = areaCodePo.getUpAreaCode();
	        gnrAreaCodePo = new GnrAreaCodePo();
	        gnrAreaCodePo.setAreaCode(newUpAreaCode);
	        gnrAreaCodePo = daoService.selectOne(gnrAreaCodePo);
	        if(gnrAreaCodePo == null){
	            ExInfo.throwDipperEx(AppCodeDict.BISGNR0012, newUpAreaCode);
	        }
	        areaCodeListGetDto.setProvinceAreaCode(gnrAreaCodePo.getAreaCode());
	        areaCodeListGetDto.setProvinceAreaName(gnrAreaCodePo.getAreaName());  
		}
		return areaCodeListGetDto;
	}

	public void setDaoService(IDaoService daoService) {
		this.daoService = daoService;
	}

}
