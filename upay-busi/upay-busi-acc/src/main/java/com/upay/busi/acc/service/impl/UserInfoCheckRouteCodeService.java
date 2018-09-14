package com.upay.busi.acc.service.impl;



import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.acc.service.dto.UserInfoCheckRouteCodeDto;
import com.upay.commons.dict.AppCodeDict;

/**
 * 检查资金通道
 * 
 * @author liyulong
 * 
 */
public class UserInfoCheckRouteCodeService extends AbstractDipperHandler<UserInfoCheckRouteCodeDto> {
    @Override
    public UserInfoCheckRouteCodeDto execute(UserInfoCheckRouteCodeDto dto, Message message) throws Exception {
    	
    	
    	if(StringUtils.isBlank(dto.getRouteCode())){
    		ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "资金通道不存在");
    	}
    	return dto;
    }
}
