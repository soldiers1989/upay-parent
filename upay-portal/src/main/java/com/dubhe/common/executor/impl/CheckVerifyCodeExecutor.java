package com.dubhe.common.executor.impl;

import com.dubhe.common.executor.ExecutorDTO;
import com.dubhe.common.executor.IExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.dict.AppCodeDict;

import org.apache.commons.lang.StringUtils;

import java.util.Set;

import javax.annotation.Resource;


/**
 * 图形验证码校验
 * @author freeplato
 *
 */
public class CheckVerifyCodeExecutor implements IExecutor {

	/** 需要校验验证码的交易 */
    private Set<String> excludeTransCode;
   
    private IDipperCached cacheClient;

    @Override
    public ExecutorDTO execute(ExecutorDTO commTransationDTO){
    	if (excludeTransCode.contains(commTransationDTO.getTransCode())) {
	    	String verifyKey = (String) commTransationDTO.getBody().get(CommunicationConstants.VERIFY_KEY);
    		if(StringUtils.isBlank(verifyKey)) {
    			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "VERIFY_KEY");
    		}
	    	String verifyCodeflag = cacheClient.get(CacheConstants.VERIFY_CODE.concat(verifyKey));
	    	if(StringUtils.isNotBlank(verifyCodeflag)){
	    		String validateCode = cacheClient.get(CacheConstants.VALIDATE_CODE.concat(verifyKey));
	    		
	    		if(StringUtils.isBlank(validateCode)){
	    			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0005);
	    		}
	    		String verifyCode = (String) commTransationDTO.getBody().get(CommunicationConstants.VERIFY_CODE);
	    		if(StringUtils.isBlank(verifyCode)){
	    			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0006);
	    		}
	    		if(!verifyCode.equalsIgnoreCase(validateCode)){
	    			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0007);
	    		}
	    		
	    		//验证码验证通过，删除验证码缓存记录
	    		cacheClient.delete(CacheConstants.VALIDATE_CODE.concat(verifyKey));
	    		cacheClient.delete(CacheConstants.VERIFY_CODE.concat(verifyKey));
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
