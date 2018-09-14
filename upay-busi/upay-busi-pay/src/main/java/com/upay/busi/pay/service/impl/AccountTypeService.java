package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.AccountTypeServiceDto;
import com.upay.busi.pay.service.dto.AdjustFlowListServiceDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 *
 * 
 * @author  中金账户类型转换
 * 
 */
public class AccountTypeService extends AbstractDipperHandler<AccountTypeServiceDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(AccountTypeService.class);


	@Override
	public AccountTypeServiceDto execute(AccountTypeServiceDto dto, Message message)
			throws Exception {

		String accountType = dto.getAccountType();
		if(DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(accountType)
				||DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(accountType)
				||DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(accountType)){
			dto.setAccountType(DataBaseConstants_PAY.ACCT_TYPE_BUSINESS);
		}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(accountType)
				||DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(accountType)){
			dto.setAccountType(DataBaseConstants_PAY.ACCT_TYPE_PERSON);
		}
		return dto;
	}
}
