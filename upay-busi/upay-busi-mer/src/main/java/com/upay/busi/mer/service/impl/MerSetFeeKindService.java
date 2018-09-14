package com.upay.busi.mer.service.impl;

import java.math.BigDecimal;
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
import com.upay.busi.mer.service.dto.MerSetFeeKindDto;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
/**
 * 一级商户维护二级商户手续费计算方法
 * @author yanzixiong
 * @version 创建时间：2016年11月10日15:50:59
 */
public class MerSetFeeKindService extends AbstractDipperHandler<MerSetFeeKindDto> {

	@Resource
    private IDaoService daoService;
	@Autowired
    private ISequenceService sequenceService;
	
	@Override
	public MerSetFeeKindDto execute(MerSetFeeKindDto dto, Message msg)
			throws Exception {
		String userId = dto.getUserId();
		String feeName = dto.getFeeName();
		String feeMode = dto.getFeeMode();
		String fixFee1 = dto.getFixFee();
		String rationFee1 = dto.getRationFee();
		String highFee1 = dto.getHighFee();
		String lowFee1 = dto.getLowFee();
		BigDecimal fixFee = null;
		BigDecimal rationFee = null;
		BigDecimal highFee = null;
		BigDecimal lowFee = null;
		String operation = dto.getOperation();
		
		if (StringUtils.isBlank(operation)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "操作方式");
		}
		if (StringUtils.isBlank(feeMode)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "手续费收取方式");
		}
		
		if(DateBaseConstants_MER.MER_OPERATION_ADD.equals(operation)){
			Map<String, Object> whereMap1 = new HashMap<String, Object>();
	        whereMap1.put("userId", userId);
	        MerBaseInfoPo merbaseinfopo = new MerBaseInfoPo();
	        merbaseinfopo = daoService.selectOne(MerBaseInfoPo.class.getName() + ".selectOneMer", whereMap1);
	        if (null == merbaseinfopo) {
	            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "一级商户");
	        }
			if (StringUtils.isBlank(feeName)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "收费名称");
			}
			FeeKindPo feeKindPo1 = new FeeKindPo();
			feeKindPo1.setFeeName(feeName);
			feeKindPo1 = daoService.selectOne(feeKindPo1);
			if (null != feeKindPo1){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0031, feeName);
			}
			if (null != highFee1 && null != lowFee1){
				highFee = new BigDecimal(highFee1);
				lowFee = new BigDecimal(lowFee1);
				if (highFee.compareTo(lowFee) <= 0){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0030, "手续费上限","或等于手续费下限");
				}
			}
			
			FeeKindPo feeKindPo = new FeeKindPo();
//			FeeKindPo feeKindPo1 = new FeeKindPo();
			String feeCode = sequenceService.generateFeeCode();
			feeKindPo.setFeeCode(feeCode);
			feeKindPo.setFeeName(feeName);
			feeKindPo.setFeeMode(feeMode);
			feeKindPo.setMemo(merbaseinfopo.getMerNo());
//			feeKindPo1.setFeeMode(feeMode);
			if (DataBaseConstants_FEE.FEE_MODE_ONE.equals(feeMode)){
				if (null == fixFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "单笔固定金额");
				}
				fixFee = new BigDecimal(fixFee1);
				feeKindPo.setFixFee(fixFee.setScale(2));
//				feeKindPo1.setFixFee(fixFee.setScale(2));
				feeKindPo.setRationFee(BigDecimal.ZERO.setScale(2));
			} else if (DataBaseConstants_FEE.FEE_MODE_TWO.equals(feeMode)){
				if (null == fixFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "单笔固定金额");
				}
				if (null == rationFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "按交易金额比例");
				}
				fixFee = new BigDecimal(fixFee1);
				rationFee = new BigDecimal(rationFee1);
				feeKindPo.setFixFee(fixFee.setScale(2));
//				feeKindPo1.setFixFee(fixFee.setScale(2));
				feeKindPo.setRationFee(rationFee.setScale(2));
//				feeKindPo1.setRationFee(rationFee.setScale(2));
			} else {
				if (null == rationFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "按交易金额比例");
				}
				rationFee = new BigDecimal(rationFee1);
				feeKindPo.setFixFee(BigDecimal.ZERO.setScale(2));
				feeKindPo.setRationFee(rationFee.setScale(2));
//				feeKindPo1.setRationFee(rationFee.setScale(2));
			}
			if (null != highFee1){
				highFee = new BigDecimal(highFee1);
				feeKindPo.setHighFee(highFee.setScale(2));
//				feeKindPo1.setHighFee(highFee.setScale(2));
			}
			if (null != lowFee1){
				lowFee = new BigDecimal(lowFee1);
				feeKindPo.setLowFee(lowFee.setScale(2));
//				feeKindPo1.setLowFee(lowFee.setScale(2));
			}
			/*feeKindPo1 = daoService.selectOne(feeKindPo1);
			if (null != feeKindPo1) {
				ExInfo.throwDipperEx(AppCodeDict.BISMER0028, "相同的费率计算方法");
			}*/
			
			daoService.insert(feeKindPo);
		} else {
			String feeCode = dto.getFeeCode();
			if (StringUtils.isBlank(feeCode)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "收费代码");
			}
			FeeKindPo feeKindPo = new FeeKindPo();
			feeKindPo.setFeeCode(feeCode);
			feeKindPo = daoService.selectOne(feeKindPo);
			if (null == feeKindPo){
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "该条计算方法");
			}
			if (DataBaseConstants_FEE.FEE_MODE_FOUR.equals(feeKindPo.getFeeMode())){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0026, "分段收取的计算方法");
			}
			Map<String, Object> whereMap = new HashMap<String, Object>();
			
			List<FeeGetPo> feeGetPoList = null;
			FeeGetPo feeGetPo = new FeeGetPo();
			feeGetPo.setFeeCode(feeCode);
			feeGetPoList = daoService.selectList(feeGetPo);
			if(feeGetPoList.size() != 0){
				ExInfo.throwDipperEx(AppCodeDict.BISMER0029, "修改");
			}
			whereMap.put("feeCode", feeCode);
			
			if (StringUtils.isNotBlank(feeName)) {
				whereMap.put("feeName", feeName);
				FeeKindPo feeKindPo1 = new FeeKindPo();
				feeKindPo1.setFeeName(feeName);
				feeKindPo1 = daoService.selectOne(feeKindPo1);
				if (null != feeKindPo1 && !feeKindPo1.getFeeCode().equals(feeCode)){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0031, feeName);
				}
			}
			whereMap.put("feeMode", feeMode);
			if (DataBaseConstants_FEE.FEE_MODE_ONE.equals(feeMode)){
				if (null == fixFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "单笔固定金额");
				}
				fixFee = new BigDecimal(fixFee1);
				whereMap.put("fixFee", fixFee.setScale(2));
				whereMap.put("rationFee", BigDecimal.ZERO.setScale(2));
			} else if (DataBaseConstants_FEE.FEE_MODE_TWO.equals(feeMode)){
				if (null == fixFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "单笔固定金额");
				}
				if (null == rationFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "按交易金额比例");
				}
				fixFee = new BigDecimal(fixFee1);
				rationFee = new BigDecimal(rationFee1);
				whereMap.put("fixFee", fixFee.setScale(2));
				whereMap.put("rationFee", rationFee.setScale(2));
			} else {
				if (null == rationFee1) {
					ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "按交易金额比例");
				}
				rationFee = new BigDecimal(rationFee1);
				whereMap.put("fixFee", BigDecimal.ZERO.setScale(2));
				whereMap.put("rationFee", rationFee.setScale(2));
			}
			if (null != highFee1){
				highFee = new BigDecimal(highFee1);
				whereMap.put("highFee", highFee.setScale(2));
			}
			if (null != lowFee1){
				lowFee = new BigDecimal(lowFee1);
				whereMap.put("lowFee", lowFee.setScale(2));
			}
			if (null != highFee1 && null != lowFee1){
				highFee = new BigDecimal(highFee1);
				lowFee = new BigDecimal(lowFee1);
				if (highFee.compareTo(lowFee) <= 0){
					ExInfo.throwDipperEx(AppCodeDict.BISMER0030, "手续费上限","或等于手续费下限");
				}
			}

			daoService.update(FeeKindPo.class.getName() + ".updateFee",whereMap);
		}
		
		return dto;
	}

}
