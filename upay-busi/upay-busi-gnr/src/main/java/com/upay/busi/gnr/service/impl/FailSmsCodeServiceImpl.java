package com.upay.busi.gnr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.upay.busi.gnr.service.dto.FailSmsCodeDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;

/**
 * 失效短信验证码
 * @author shangqiankun
 *
 */
public class FailSmsCodeServiceImpl extends AbstractDipperHandler<FailSmsCodeDto> {
	 private static final Logger log = LoggerFactory.getLogger(FailSmsCodeServiceImpl.class);
	@Resource
	IDipperCached cacheClient;
	
	@Override
	public FailSmsCodeDto execute(FailSmsCodeDto failSmsCodeDto, Message message) throws Exception {
		
		String mobile = failSmsCodeDto.getMobile();
		if(StringUtils.isBlank(mobile)){
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "手机号");
		}
		String isDelete = (String)cacheClient.get(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile).concat(CommonConstants_GNR.NO_DELETE));
		log.debug("是否删除中的验证码:::::::::::::::::"+isDelete);
		//如果删除验证码缓存标志为空，或者不为 不删除的情况下，删除缓存
		if(StringUtils.isBlank(isDelete)||!CommonConstants_GNR.NO_DELETE.equals(isDelete)){
			cacheClient.delete(CacheConstants.SMS_NO.concat(mobile));
	    	cacheClient.delete(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile));
		}
		if(StringUtils.isNotBlank(isDelete)){
			cacheClient.delete(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile).concat(CommonConstants_GNR.NO_DELETE));
		}
		return failSmsCodeDto;
	}
}
