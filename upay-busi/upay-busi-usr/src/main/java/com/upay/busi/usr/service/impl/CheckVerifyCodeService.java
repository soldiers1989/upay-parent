package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.upay.busi.usr.service.dto.CheckVerifyCodeServiceDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.dict.AppCodeDict;

public class CheckVerifyCodeService extends
		AbstractDipperHandler<CheckVerifyCodeServiceDto> {
	@Resource
	IDipperCached cacheClient;

	@Override
	public CheckVerifyCodeServiceDto execute(CheckVerifyCodeServiceDto dto,
			Message arg1) throws Exception {
		String verifyCode = dto.getVerifyCode();
		String verifyKey = dto.getVerifyKey();
		if (StringUtils.isBlank(verifyKey)) {
			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "VERIFY_KEY");
		}

		// String verifyCodeflag =
		// cacheClient.get(CacheConstants.VERIFY_CODE.concat(verifyKey));
		// if(StringUtils.isNotBlank(verifyCodeflag)){
		if (StringUtils.isNotBlank(verifyCode)
				&& StringUtils.isNotBlank(verifyKey)) {
			String validateCode = cacheClient.get(CacheConstants.VALIDATE_CODE
					.concat(verifyKey));

			if (StringUtils.isBlank(validateCode)) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0005);
			}

			if (StringUtils.isBlank(verifyCode)) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0006);
			}
			if (!verifyCode.equalsIgnoreCase(validateCode)) {
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0007);
			} else {
				// 验证码验证通过，删除验证码缓存记录
				cacheClient.delete(CacheConstants.VALIDATE_CODE
						.concat(verifyKey));
				cacheClient
						.delete(CacheConstants.VERIFY_CODE.concat(verifyKey));
			}
		}

		// }
		return dto;
	}

}
