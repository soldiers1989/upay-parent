package com.upay.busi.mer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.MerApplyChkDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerApplyBookPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**
 * 商户申请检查
 *
 * @author yanzixiong
 * @version 创建时间：2016年10月24日15:21:51
 */
public class MerApplyChkService extends AbstractDipperHandler<MerApplyChkDto> {

	@Resource
	private IDaoService daoService;

	@Override
	public MerApplyChkDto execute(MerApplyChkDto dto, Message msg)
			throws Exception {
		String userId = dto.getUserId();
		if (StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("userId", userId);
        whereMap.put("parentMerNo", dto.getParentMerNo());
		List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
		Map<String, String> orderByMerApplyNo= new HashMap<String, String>();
		orderByMerApplyNo.put("columnName", "MER_APPLY_NO");
		orderByMerApplyNo.put("sort", "desc");
		orderByList.add(orderByMerApplyNo);
		whereMap.put("orderBy", orderByList);
		MerApplyBookPo merApplyBookPo =null;
		List<MerApplyBookPo> merApplyBookPos = daoService.selectList(MerApplyBookPo.class.getName() + ".selectOneMer", whereMap);
		if(merApplyBookPos!=null&&merApplyBookPos.size()>0){
            merApplyBookPo= merApplyBookPos.get(0);
		}

		if (null == merApplyBookPo) {
			ExInfo.throwDipperEx(AppCodeDict.BISMER0019);
		}
		dto.setMerStat(DateBaseConstants_MER.MER_APPLY_CHK_APPLY);
		dto.setMerApplyNo(merApplyBookPo.getMerApplyNo());
		dto.setMerName(merApplyBookPo.getMerName());
		dto.setState(merApplyBookPo.getState());
		if (DateBaseConstants_MER.MER_APPLY_STAT_YES.equals(merApplyBookPo.getApplyState())) {
			dto.setMerStat(DateBaseConstants_MER.MER_APPLY_CHK_PASS);
			MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
			whereMap.put("merNo",dto.getMerNo());
	        merBaseInfoPo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
	        if (null == merBaseInfoPo) {
	            ExInfo.throwDipperEx(AppCodeDict.BISMER0018);
	        }
//	        dto.setContact(merBaseInfoPo.getContact());
	        dto.setMerNo(merBaseInfoPo.getMerNo());
		} else if (DateBaseConstants_MER.MER_APPLY_STAT_NO.equals(merApplyBookPo.getApplyState())){
			dto.setMerStat(DateBaseConstants_MER.MER_APPLY_CHK_REFUSE);
			dto.setAnswerApply(merApplyBookPo.getAnswerApply());
//			dto.setContact(merApplyBookPo.getContact());
		}
		dto.setContact(merApplyBookPo.getContact());
		dto.setApplyDate(DateUtil.format(merApplyBookPo.getApplyDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		return dto;
	}
}

