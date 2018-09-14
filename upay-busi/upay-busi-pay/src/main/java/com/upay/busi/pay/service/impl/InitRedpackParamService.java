/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.InitRedpackParamDto;
import com.upay.busi.pay.service.dto.RedpackRegisterDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayRedpackListPo;

/**
 * 红包登记准备参数
 * 
 * 
 * 
 */
public class InitRedpackParamService extends
		AbstractDipperHandler<InitRedpackParamDto> {

	@Resource
	private IDaoService daoService;

	@Override
	public InitRedpackParamDto execute(InitRedpackParamDto dto,
			Message msg) throws Exception {
		String merNo = dto.getMerNo();
		if (StringUtils.isNotBlank(merNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
		}
		MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
		merBaseInfo.setMerNo(merNo);
		merBaseInfo = daoService.selectOne(merBaseInfo);
		
		return dto;
	}

}
