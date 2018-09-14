package com.upay.busi.mer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.SecondMerSetFeeDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.mer.MerBaseInfoPo;

/**
 * 维护费率信息
 * @author yanzixiong
 * @version 创建时间：2016年8月18日16:42:09
 */
public class SecondMerSetFeeService extends AbstractDipperHandler<SecondMerSetFeeDto> {
	
	static final SimpleDateFormat sim = new SimpleDateFormat(DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
	
	@Resource
    private IDaoService daoService;
	@Autowired
    private ISequenceService sequenceService;
	
	@Override
	public SecondMerSetFeeDto execute(SecondMerSetFeeDto dto, Message msg)
			throws Exception {	
		Date now = SysInfoContext.getSysDate();
		
		String feeName = dto.getFeeName();
		String secMerNo = dto.getSecMerNo();
		String feeCode = dto.getFeeCode();
		String assCode = dto.getAssCode();
		String startDate1 = dto.getStartDate();
		String endDate1 = dto.getEndDate();
		String perFee = dto.getPerFee();
		String userId = dto.getUserId();
		String operation = dto.getOperation();
				
		if (StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户ID");
		}
		if (StringUtils.isBlank(operation)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "操作方式");
		}
		Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("userId", userId);
		MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
		merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap);
		
		if(DateBaseConstants_MER.MER_OPERATION_ADD.equals(operation)){
			if (StringUtils.isBlank(secMerNo)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "二级商户号");
			}
			if (StringUtils.isBlank(feeCode)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "收费代码");
			}
			if (StringUtils.isBlank(feeName)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "收费方法名称");
			}
			if (startDate1 == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "费用收取起始日期");
			}
			if (endDate1 == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "费用收取结止日期");
			}
			Date startDate = sim.parse(startDate1);
			Date endDate = sim.parse(endDate1);
			//日期判断
			if (now.compareTo(startDate) > 0){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0030, "收取起始日期","当前日期");
			}
			if (startDate.compareTo(endDate) > 0){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "费用收取起始日期","费用收取结止日期");
			}
			
			List<FeeGetPo> feeGetPoList = null;
			FeeGetPo feeGetPo = new FeeGetPo();
			feeGetPo.setSecMerNo(secMerNo);
			feeGetPoList = daoService.selectList(feeGetPo);
			for (FeeGetPo feeGetPo1 : feeGetPoList){
				if (feeGetPo1.getStartDate().compareTo(startDate) <= 0
						&& feeGetPo1.getEndDate().compareTo(startDate) >= 0
						|| feeGetPo1.getStartDate().compareTo(endDate) <= 0
						&& feeGetPo1.getEndDate().compareTo(endDate) >= 0
						|| feeGetPo1.getStartDate().compareTo(startDate) >= 0
						&& feeGetPo1.getEndDate().compareTo(endDate) <= 0) {
					ExInfo.throwDipperEx(AppCodeDict.BISMER0007);
				}
			}
			FeeGetPo fee2 = new FeeGetPo();
			fee2.setFeeName(feeName);
			fee2 = daoService.selectOne(fee2);
			if(fee2 != null){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0012,fee2.getFeeName());
			}
			FeeGetPo fee = new FeeGetPo();
			String feeId = sequenceService.generateFeeID();
			fee.setFeeId(feeId);
			fee.setFeeName(feeName);
			fee.setMerNo(merbaseinfopo.getMerNo());
			fee.setSecMerNo(secMerNo);
			fee.setTransCode(dto.getTransCode());
			fee.setFeeCode(feeCode);
			if (null != assCode) { fee.setAssCode(assCode); }
			fee.setFreeCycle(DataBaseConstants_FEE.FEE_FREE_CYCLE_NO);
			fee.setStartDate(startDate);
			fee.setEndDate(endDate);
			if (null != perFee){ fee.setPerFee(new Integer(perFee)); }
			
			daoService.insert(fee);
		} else if (DateBaseConstants_MER.MER_OPERATION_UPDATE.equals(operation)){
			if (StringUtils.isBlank(secMerNo)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "二级商户号");
			}
			if (StringUtils.isBlank(dto.getFeeId())){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "费用ID");
			}
			FeeGetPo feeGetPo = new FeeGetPo();
			feeGetPo.setFeeId(dto.getFeeId());
			feeGetPo = daoService.selectOne(feeGetPo);
			if (feeGetPo == null){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "该条数据");
			}
			//判断该方法是否生效中
			if (now.compareTo(feeGetPo.getStartDate())>=0 && now.compareTo(feeGetPo.getEndDate())<=0){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0023, "修改");
			}
			//判断时间
			List<FeeGetPo> feeGetPoList = null;
			FeeGetPo feeGetPo1 = new FeeGetPo();
			feeGetPo1.setSecMerNo(secMerNo);
			feeGetPoList = daoService.selectList(feeGetPo1);
			if (startDate1 != null && endDate1 == null) {
				Date startDate = sim.parse(startDate1);
				if (now.compareTo(startDate) > 0){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0030, "收取起始日期","当前日期");
				}
				for (FeeGetPo feeGetPo2 : feeGetPoList) {
					if (feeGetPo2.getId().equals(feeGetPo.getId())) {
						if (feeGetPo2.getEndDate().compareTo(startDate) < 0) {
							ExInfo.throwDipperEx(AppCodeDict.BISMER0006,
									"费用收取起始日期", "费用收取结止日期");
						}
					}else{
						if (feeGetPo2.getStartDate().compareTo(startDate) <= 0
								&& feeGetPo2.getEndDate().compareTo(startDate) >= 0
								|| feeGetPo2.getStartDate().compareTo(startDate) >= 0
								&& feeGetPo2.getEndDate().compareTo(feeGetPo.getEndDate()) <= 0) {
							ExInfo.throwDipperEx(AppCodeDict.BISMER0007);
						}
					}
				}
			} else if (startDate1 == null && endDate1 != null) {
				Date endDate = sim.parse(endDate1);
				if (now.compareTo(endDate) > 0){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "当前日期","费用收取结止日期");
				}
				for (FeeGetPo feeGetPo2 : feeGetPoList) {
					if (feeGetPo2.getId().equals(feeGetPo.getId())) {
						if (feeGetPo2.getStartDate().compareTo(endDate) > 0) {
							ExInfo.throwDipperEx(AppCodeDict.BISMER0006,
									"费用收取起始日期", "费用收取结止日期");
						}
					}else{
						if (feeGetPo2.getStartDate().compareTo(endDate) <= 0
								&& feeGetPo2.getEndDate().compareTo(endDate) >= 0
								|| feeGetPo2.getStartDate().compareTo(feeGetPo.getStartDate()) >= 0
								&& feeGetPo2.getEndDate().compareTo(endDate) <= 0) {
							ExInfo.throwDipperEx(AppCodeDict.BISMER0007);
						}
					}
				}
			} else if (startDate1 != null && endDate1 != null){
				Date startDate = sim.parse(startDate1);
				Date endDate = sim.parse(endDate1);
				if (now.compareTo(startDate) > 0){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0030, "收取起始日期","当前日期");
				}
				if (startDate.compareTo(endDate) > 0){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "费用收取起始日期","费用收取结止日期");
				}
				for (FeeGetPo feeGetPo2 : feeGetPoList){
					if (!feeGetPo2.getId().equals(feeGetPo.getId())){
						if (feeGetPo2.getStartDate().compareTo(startDate) <= 0
								&& feeGetPo2.getEndDate().compareTo(startDate) >= 0
								|| feeGetPo2.getStartDate().compareTo(endDate) <= 0
								&& feeGetPo2.getEndDate().compareTo(endDate) >= 0
								|| feeGetPo2.getStartDate().compareTo(startDate) >= 0
								&& feeGetPo2.getEndDate().compareTo(endDate) <= 0) {
							ExInfo.throwDipperEx(AppCodeDict.BISMER0007);
						}
					}	
				}
			}
			
			FeeGetPo fee = new FeeGetPo();
			if (feeName != null) {
				fee.setFeeName(feeName);
				FeeGetPo fee2 = new FeeGetPo();
				fee2.setFeeName(feeName);
				fee2 = daoService.selectOne(fee2);
				if (fee2 != null) {
					if ( !fee2.getFeeId().equals(feeGetPo.getFeeId()) ){
						ExInfo.throwDipperEx(AppCodeDict.BISMER0012,fee2.getFeeName());
					}	
				}
			}
			fee.setTransCode(dto.getTransCode());
			if (feeCode != null) { fee.setFeeCode(feeCode); }
			if (assCode != null) { fee.setAssCode(assCode); }
			if (startDate1 != null) {
				Date startDate = sim.parse(startDate1);
				fee.setStartDate(startDate); 
			}
			if (endDate1 != null) { 
				Date endDate = sim.parse(endDate1);
				fee.setEndDate(endDate); 	
			}
			if (null != perFee){ fee.setPerFee(new Integer(perFee)); }
			
			FeeGetPo fee1 = new FeeGetPo();
			fee1.setFeeId(dto.getFeeId());
			daoService.update(fee, fee1);
		} else {
			if (StringUtils.isBlank(dto.getFeeId())){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "费用ID");
			}
			FeeGetPo feeGetPo = new FeeGetPo();
			feeGetPo.setFeeId(dto.getFeeId());
			feeGetPo = daoService.selectOne(feeGetPo);
			if (feeGetPo == null){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "该条数据");
			}
			//判断该方法是否生效中
			if (now.compareTo(feeGetPo.getStartDate())>=0 && now.compareTo(feeGetPo.getEndDate())<=0){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0023, "删除");
			}
			FeeGetPo feeGetPo1 = new FeeGetPo();
			feeGetPo1.setFeeId(dto.getFeeId());
			daoService.delete(feeGetPo1);
		}

		return dto;
	}

}
