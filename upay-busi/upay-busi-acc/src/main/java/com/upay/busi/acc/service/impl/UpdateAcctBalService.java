package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.UpdateAcctBalServiceDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccVbookPo;

/**
 * @author: liubing
 * @CreateDate:2015
 */
public class UpdateAcctBalService extends
		AbstractDipperHandler<UpdateAcctBalServiceDto> {
	private final static Logger log = LoggerFactory
			.getLogger(UpdateAcctBalService.class);
	@Resource
	private IDaoService daoService;

	@Override
	public UpdateAcctBalServiceDto execute(UpdateAcctBalServiceDto dto,
			Message msg) throws Exception {
		String routeType = dto.getRouteType();

		String vAcctNo = dto.getvAcctNo();
		// 充值提现的情况下
		if (DataBaseConstans_ACC.ROUTE_CODE_ZJ.equals(routeType)) {
			String status = dto.getStatus();
			if (CommonConstants_GNR.ZJ_Q_PAY_STAT_FAIL.equals(status)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "调用中金失败");
			}
		} else if (DataBaseConstans_ACC.ROUTE_CODE_CORE.equals(routeType)) {
			String respCode = dto.getRespCode();
			if (!CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(respCode)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "调用核心失败");
			}
		}

		String operationFlag = dto.getOperFlag();
		if (StringUtils.isBlank(operationFlag)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "账户交易类型");
		}
		if (StringUtils.isBlank(vAcctNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "虚拟账户号");
		}

		Date sysDate = SysInfoContext.getSysDate();
		BigDecimal transAmt = dto.getTransAmt();

		AccVbookPo vbook = new AccVbookPo();
		vbook.setVacctNo(vAcctNo);
		vbook = daoService.selectForUpdate(vbook);

		if (vbook == null) {
			// 【 异常】虚拟账户不存在
			ExInfo.throwDipperEx(AppCodeDict.BISACC0017, "虚拟账号");
		}

		// 提现操作，需要检查当前电子账户的余额是否足够
		if (DataBaseConstans_ACC.TRANS_CODE_WITHDRAW.equals(dto.getTransCode())) {
			BigDecimal usaBal = MoneyUtil.subtract(vbook.getAcctBal(),
					vbook.getFrzBal(), RoundingMode.HALF_DOWN);
			int result = usaBal.compareTo(transAmt);
			if (result < 0) {
				// 【 异常】当前余额不足以完成此操作
				ExInfo.throwDipperEx(AppCodeDict.BISACC0007, "当前可用");
			}
		}

		String sqlId = AccVbookPo.class.getName() + ".updateBalanceByAcctNo";
		HashMap<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("lastChgTime", sysDate);
		parmMap.put("vAcctNo", vAcctNo);
		parmMap.put("transAmt", transAmt);
		parmMap.put("operFlag", operationFlag);
		daoService.update(sqlId, parmMap);
		return dto;
	}
}