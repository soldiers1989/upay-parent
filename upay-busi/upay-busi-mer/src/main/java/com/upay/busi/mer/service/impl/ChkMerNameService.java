package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.ChkMerNameDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerApplyBookPo;

/**
 * 商户名称重复校验
 * 
 * @author yanzixiong
 * @version 创建时间：2016年12月13日15:40:07
 */
public class ChkMerNameService extends AbstractDipperHandler<ChkMerNameDto> {

	@Resource
    private IDaoService daoService;
	
	@Override
	public ChkMerNameDto execute(ChkMerNameDto dto, Message msg)
			throws Exception {
		String merName = dto.getMerName();
		String merApplyNo = dto.getMerApplyNo();
		if (StringUtils.isBlank(merName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户名称");
        }
		MerApplyBookPo merApplyBookPo = new MerApplyBookPo();
		merApplyBookPo.setMerName(merName);
		merApplyBookPo = daoService.selectOne(merApplyBookPo);
		if (null != merApplyBookPo){
			if (null == merApplyNo || !merApplyBookPo.getMerApplyNo().equals(merApplyNo) ){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0035,merName);
			}
		}
		
		return dto;
	}

}
