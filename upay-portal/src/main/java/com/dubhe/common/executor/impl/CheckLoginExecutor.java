package com.dubhe.common.executor.impl;

import com.dubhe.common.executor.ExecutorDTO;
import com.dubhe.common.executor.IExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import org.apache.commons.lang.StringUtils;

import java.util.*;


/**
 * 登录状态检查
 * @author freeplato
 *
 */
public class CheckLoginExecutor implements IExecutor {

    /** 不需要校验的交易 */
    private Set<String> excludeTransCode;
    
    private IDipperCached cacheClient;
    

    @Override
    public ExecutorDTO execute(ExecutorDTO commTransationDTO){
        String transCode = commTransationDTO.getTransCode();
        // 已登录用户需上送用户号及SESSIONID
        if (!excludeTransCode.contains(transCode)) {

        	String chnlId = commTransationDTO.getChnlId();
        	if(!CommonConstants_GNR.CHNL_ID_WEIXIN.equals(chnlId)){
        		String sessionId = commTransationDTO.getSessionId();
        		if(StringUtils.isBlank(sessionId)){
        			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0002);
        		}
            	String userId = commTransationDTO.getUserId();
            	if(StringUtils.isBlank(userId)){
            		ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "USER_ID");
            	}
        		//检查session登录状态
        		String cacheClientPrefix = CacheConstants.SESSION_WEB;
        		if(CommonConstants_GNR.CHNL_ID_APP.equals(chnlId)){
        			cacheClientPrefix = CacheConstants.SESSION_APP;
        		}
        		String sessionIdLogin = cacheClient.get(cacheClientPrefix.concat(userId));
        		if(StringUtils.isNotBlank(sessionIdLogin)){
        			if(!sessionIdLogin.equals(sessionId)){
        				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0003);
        			}
        		}else{
        			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0002);
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
