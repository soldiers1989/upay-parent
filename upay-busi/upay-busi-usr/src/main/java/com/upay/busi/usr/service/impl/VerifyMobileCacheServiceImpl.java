package com.upay.busi.usr.service.impl;



import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.upay.busi.usr.service.dto.SetMobileCacheDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;


/**
 * 用户重置密码时验证验证码是否通过
 * @author liubing
 * 
 */

public class VerifyMobileCacheServiceImpl extends AbstractDipperHandler<SetMobileCacheDto> {

    
    @Resource
    private IDipperCached cacheClient;
    
    private static final Logger log = LoggerFactory.getLogger(VerifyMobileCacheServiceImpl.class);

   

    @Override
    public SetMobileCacheDto execute(SetMobileCacheDto dto, Message message) throws Exception {
    	String mobile = dto.getMobile();
    	if(StringUtils.isBlank(mobile)){
    		ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "重置登录密码的手机号");
    	}
    	String verifyVal = cacheClient.get(mobile+DataBaseConstants_USR.VERIFY_IS_PASS);
    	log.debug("用户重置登录密码，验证码是否通过 "+verifyVal);
    	if(StringUtils.isBlank(verifyVal)){
    		ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "重置登录密码,手机号验证码未通过。");
    	}else{
    		cacheClient.delete(mobile+DataBaseConstants_USR.VERIFY_IS_PASS);
    	}
    	return dto;
    }
}
