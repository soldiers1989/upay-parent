package com.upay.busi.mer.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.ChkSecondIsMerDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**
 * 检查二级商户是否所属一级商户
 * @author yanzixiong
 * @version 创建时间：2016年8月18日15:50:11
 */
public class ChkSecondIsMerService extends AbstractDipperHandler<ChkSecondIsMerDto> {

	@Resource
    private IDaoService daoService;
	
	@Override
	public ChkSecondIsMerDto execute(ChkSecondIsMerDto dto, Message msg)
			throws Exception {
		String userId = dto.getUserId();
		String secMerNo = dto.getSecMerNo();
		if (StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
		}
		if (StringUtils.isBlank(secMerNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "二级商户号");
		}
		//查询一级商户
		Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("userId", userId);
		MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
		merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
		if (null == merbaseinfopo) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
		}
		//查询二级商户
		MerBaseInfoPo merbaseinfopo2 = new MerBaseInfoPo();
		merbaseinfopo2.setMerNo(secMerNo);
		merbaseinfopo2 = daoService.selectOne(merbaseinfopo2);
		if (null == merbaseinfopo2) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "该二级商户");
		}
		if (merbaseinfopo2.getParentMerNo() == null){
			ExInfo.throwDipperEx(AppCodeDict.BISMER0004);
		}
		if (!merbaseinfopo2.getParentMerNo().equals(merbaseinfopo.getMerNo())){
			ExInfo.throwDipperEx(AppCodeDict.BISMER0005);
		}
		
		return dto;
	}

}
