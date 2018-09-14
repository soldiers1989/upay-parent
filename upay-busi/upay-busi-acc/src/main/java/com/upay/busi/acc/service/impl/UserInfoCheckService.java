package com.upay.busi.acc.service.impl;


import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.busi.acc.service.dto.UserInfoCheckDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.ValidateUtil;


/**
 * 富农汇实名认证
 * 
 * @author liyulong
 * 
 */
public class UserInfoCheckService extends AbstractDipperHandler<UserInfoCheckDto> {
    @Override
    public UserInfoCheckDto execute(UserInfoCheckDto dto, Message message) throws Exception {
    	String bindAcctNo = dto.geteBindAcctNo();
    	String certName = dto.getCertName();
    	String certNo = dto.getCertNo();
    	String reserveMobile = dto.getReserveMobile();
    	
    	if(StringUtils.isBlank(certNo)||StringUtils.isBlank(certName)){
    		ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "客户姓名和身份证号码");
    	}
    	if(StringUtils.isNotBlank(bindAcctNo)){
    		if(StringUtils.isBlank(reserveMobile)){
    			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "手机号码不能为空");
    		}
    		// 验证手机号格式
            if (!ValidateUtil.checkMobile(reserveMobile)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0009, reserveMobile);
            }
    	}
        return dto;
    }
}
