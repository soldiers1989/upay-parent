package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.AccountTypeServiceDto;
import com.upay.busi.pay.service.dto.ExceptionInfoDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 *
 * 
 * @author
 * 
 */
public class ExceptionInfoSevice extends AbstractDipperHandler<ExceptionInfoDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionInfoSevice.class);
    private static String ERR_FLAG="1";
	@Override
	public ExceptionInfoDto execute(ExceptionInfoDto dto, Message message)
			throws Exception {

		if(ERR_FLAG.equals(dto.getErrFlag())){
			String errCode = dto.getErrCode();
			String errInfo = dto.getErrInfo();
			message.getFault().setMsg(errInfo);
			message.getFault().setOutMsg(errInfo);
			message.getFault().setCode(errCode);
			message.getFault().setOutCode(errCode);
		}else{
			String msg = message.getFault().getMsg();
			String code = message.getFault().getCode();
			if(StringUtils.isBlank(msg)){
				msg=message.getFault().getOutMsg();
			}
			if(StringUtils.isBlank(code)){
				code = message.getFault().getOutCode();
			}
			dto.setErrCode(code);
			dto.setErrInfo(msg);
			dto.setErrFlag(ERR_FLAG);
		}





		return dto;
	}
}
