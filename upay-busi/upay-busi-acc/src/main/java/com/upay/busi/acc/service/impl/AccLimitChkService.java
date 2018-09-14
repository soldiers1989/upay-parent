/**
 * 
 */
package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccLimitChkDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.acc.AccSysLmtBookPo;

/**
 * 微信单笔限额检查
 * 
 * @author zhanggr
 * 
 */
public class AccLimitChkService extends AbstractDipperHandler<AccLimitChkDto> {

	@Resource
	private IDaoService daoService;

	@Override
	public AccLimitChkDto execute(AccLimitChkDto accLimitChkDto, Message message)
			throws Exception {

		String transCode = accLimitChkDto.getTransCode();
		BigDecimal transAmt = accLimitChkDto.getTransAmt();
		if (StringUtils.isBlank(transCode)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "交易码");
		}
		if (transAmt == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "交易金额");
		}
		if (transAmt.compareTo(BigDecimal.ZERO) <= 0) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0021, "交易金额");
		}

		AccSysLmtBookPo accSysLmtBookPo = new AccSysLmtBookPo();
		accSysLmtBookPo.setTransCode(transCode);
		accSysLmtBookPo = daoService.selectOne(accSysLmtBookPo);
		if (accSysLmtBookPo == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "交易限额配置");
		} else {
			BigDecimal singleAmtLimit = accSysLmtBookPo.getSingleAmtLimit();// 单笔限额
			if (singleAmtLimit.compareTo(BigDecimal.ZERO) <= 0) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0021, "单笔限额");
			}
			if (transAmt.compareTo(singleAmtLimit) > 0) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0026,singleAmtLimit);
			}
		}

		return accLimitChkDto;
	}

}
