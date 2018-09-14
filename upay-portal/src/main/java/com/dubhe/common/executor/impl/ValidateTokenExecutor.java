package com.dubhe.common.executor.impl;

import com.dubhe.common.executor.ExecutorDTO;
import com.dubhe.common.executor.IExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.dict.AppCodeDict;

import org.apache.commons.lang.StringUtils;

import java.util.Set;

import javax.annotation.Resource;

/**
 * Created by dongweizhao on 15-5-26. 表单防重复提交执行器
 * 
 * @author dongweizhao
 */
public class ValidateTokenExecutor implements IExecutor {
	/** 需要校验验证码的交易 */
	private Set<String> excludeTransCode;
	
	private IDipperCached cacheClient;

	@Override
	public ExecutorDTO execute(ExecutorDTO commTransationDTO) {
		if (commTransationDTO.getChnlId().equals(CommonConstants_GNR.CHNL_ID_WEB)) {
			if (excludeTransCode.contains(commTransationDTO.getTransCode())) {

				String tokenValue = (String) commTransationDTO.getBody()
						.get(CommunicationConstants.TOKEN_ID);
				String cacheTokenKey = CacheConstants.TOKEN_CODE.concat(commTransationDTO.getSessionId());
				String cacheTokenValue = cacheClient.get(cacheTokenKey);
				if (StringUtils.isNotBlank(tokenValue)) {
					if(StringUtils.isNotBlank(cacheTokenValue)&&tokenValue.equals(cacheTokenValue)){
						cacheClient.delete(cacheTokenKey);
					}else{
						ExInfo.throwDipperEx(AppCodeDict.CIPSYS0010, "TOKEN_ID");
					}
				} else  {
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0010, "TOKEN_ID");
				}
			}

		}
		return commTransationDTO;
	}


	public void setExcludeTransCode(Set<String> excludeTransCode) {
		this.excludeTransCode = excludeTransCode;
	}


	public void setCacheClient(IDipperCached cacheClient) {
		this.cacheClient = cacheClient;
	}

}
