/**
 * 
 */
package com.upay.busi.acc.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccLimitQueryDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.acc.AccSysLmtBookPo;

/**
 * @author lb
 *
 */
public class AccLimitQueryService extends AbstractDipperHandler<AccLimitQueryDto> {

	@Resource
	private IDaoService daoService;

	@Override
	public AccLimitQueryDto execute(AccLimitQueryDto dto, Message arg1)
			throws Exception {
		String lmtTransCode = dto.getLmtTransCode();
		if(StringUtils.isBlank(lmtTransCode)){
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "查询的限额交易码");
		}
		AccSysLmtBookPo lmtPo=new AccSysLmtBookPo();
		lmtPo.setAcctType(DataBaseConstans_ACC.ACCT_TYPE_DEBIT_CARD);
		lmtPo.setLmtStat(DataBaseConstans_ACC.LMT_STAT_ENABLE);
		lmtPo.setLmtType(DataBaseConstans_ACC.ACC_SYS_LMT_BOOK_LMT_TYPE_COMMON);
		lmtPo.setChnlId(dto.getChnlId());
		lmtPo.setTransCode(lmtTransCode);
		lmtPo.setUserLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);//暂时写死，因为这个字段是数据库的唯一索引
		lmtPo = daoService.selectOne(lmtPo);
		
		if(null!=lmtPo){
			//借记卡日累计限额
			dto.setDebitDaySumAmtLimit(MoneyUtil.moneyFormat(lmtPo.getDaySumAmtLimit()));
			//借记卡单笔累计限额
			dto.setDebitSingleAmtLimit(MoneyUtil.moneyFormat(lmtPo.getSingleAmtLimit()));
		}
		
		lmtPo=new AccSysLmtBookPo();
		lmtPo.setAcctType(DataBaseConstans_ACC.ACCT_TYPE_CREDIT_CARD);
		lmtPo.setLmtStat(DataBaseConstans_ACC.LMT_STAT_ENABLE);
		lmtPo.setLmtType(DataBaseConstans_ACC.ACC_SYS_LMT_BOOK_LMT_TYPE_COMMON);
		lmtPo.setChnlId(dto.getChnlId());
		lmtPo.setTransCode(lmtTransCode);
		lmtPo.setUserLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);//暂时写死，因为这个字段是数据库的唯一索引
		lmtPo = daoService.selectOne(lmtPo);
		

		if(null!=lmtPo){
			//信用卡日累计限额
			dto.setCreditDaySumAmtLimit(MoneyUtil.moneyFormat(lmtPo.getDaySumAmtLimit()));
			//信用卡单笔累计限额
			dto.setCreditSingleAmtLimit(MoneyUtil.moneyFormat(lmtPo.getSingleAmtLimit()));
		}
		
		return dto;
	}

}
