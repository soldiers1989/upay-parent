package com.upay.busi.mer.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.FirstMerCancelDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

public class FirstMerCancelService extends
		AbstractDipperHandler<FirstMerCancelDto> {
	@Resource
	private IDaoService daoService;

	@Override
	public FirstMerCancelDto execute(FirstMerCancelDto dto, Message arg1)
			throws Exception {
		String merNo = dto.getMerNo();
		String merState = dto.getMerState();
		if (StringUtils.isBlank(merNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "注销商户号不能为空");
		}
		if (StringUtils.isBlank(merState)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户状态信息不能为空");
		}
		MerBaseInfoPo mb = new MerBaseInfoPo();
		mb.setMerNo(merNo);
		mb = daoService.selectOne(mb);
		if (null == mb) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户【" + merNo + "】不存在");
		} else {
			if(merState.equals(mb.getMerState())){
				if(DateBaseConstants_MER.MER_STAT_NORMAL.equals(merState)){
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户【" + merNo + "】状态为己启用");
				}else if(DateBaseConstants_MER.MER_STAT_STOP.equals(merState)){
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户【" + merNo + "】状态为己停用");
				}
			}
			
			MerBaseInfoPo setmb = new MerBaseInfoPo();
			setmb.setMerState(merState);
			MerBaseInfoPo wheremb = new MerBaseInfoPo();
			wheremb.setMerNo(merNo);

			daoService.update(setmb, wheremb);
		}
		return dto;
	}

}
