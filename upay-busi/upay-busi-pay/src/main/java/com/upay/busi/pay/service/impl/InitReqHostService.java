package com.upay.busi.pay.service.impl;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.InitReqHostDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerAcctInfoPo;

/**
 * 商户内部账户余额查询--请求核心接口参数初始化 
 * @author yhy
 * @since 20170613
 */
public class InitReqHostService extends AbstractDipperHandler<InitReqHostDto> {

	private static final Logger logger = LoggerFactory.getLogger(MerChkFileDownService.class);

	@Resource
	IDaoService daoService;
	@Resource
    private ISequenceService sequenceService;

	@Override
	public InitReqHostDto execute(InitReqHostDto dto, Message message)
			throws Exception {
		String merNo = dto.getMerNo();

		if (StringUtils.isBlank(merNo)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
		}
		MerAcctInfoPo merAcctInfoPo = new MerAcctInfoPo();
		merAcctInfoPo.setMerNo(merNo);
		MerAcctInfoPo merAcctInfo = daoService.selectOne(merAcctInfoPo);
		if (merAcctInfo == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "商户结算账户");

		}
		String stlAcctType = merAcctInfo.getStlAcctType();
		//结算账户必须是本行对公账户或者内部账户才可以查询 ,本行借记卡调用08008
		if (!(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(stlAcctType)|| DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(stlAcctType) || DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(stlAcctType))) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0111);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
		dto.setStlAcctNo(merAcctInfo.getStlAcctNo());
		dto.setQueryDate(dateFormat.format(date));
		dto.setQueryTime(timeFormat.format(date));
		dto.setQuerySeq(sequenceService.generatePayFlowSeq());
		dto.setTranDate(dateFormat.format(SysInfoContext.getSysDate()));
		dto.setStlAcctType(stlAcctType);
		return dto;
	}

}
