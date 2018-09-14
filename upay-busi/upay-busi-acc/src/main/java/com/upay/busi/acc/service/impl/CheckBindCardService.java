package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccOpenStatusDto;
import com.upay.busi.acc.service.dto.CheckBindCardServiceDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccVbookPo;

/**
 * 检查绑定卡的类型 如果是信用卡需要传入有效期和安全码
 * 
 * @author shangqiankun
 * @version 创建时间：2016年7月21日 上午9:40:59
 */
public class CheckBindCardService extends
		AbstractDipperHandler<CheckBindCardServiceDto> {

	private static final Logger log = LoggerFactory
			.getLogger(CheckBindCardService.class);

	@Resource
	private IDaoService daoService;

	@Override
	public CheckBindCardServiceDto execute(CheckBindCardServiceDto dto,
			Message message) throws Exception {
		if (DataBaseConstans_ACC.ACCT_TYPE_CREDIT_CARD.equals(dto
				.getBindAcctType())) {
			if (StringUtils.isBlank(dto.getValidDate())) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "信用卡有效期");
			}else if(dto.getValidDate().length()!=6){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "信用卡有效期长度不正确");
			}
			if (StringUtils.isBlank(dto.getCvn2())) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "信用卡安全码");
			}else if(dto.getCvn2().length()!=3){
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "信用卡安全码长度不正确");
			}
		}
		return dto;
	}

}
