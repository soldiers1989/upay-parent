package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.upay.busi.usr.service.dto.LogSuccessSessionDealDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;

/**
 * 登陆成功后session重置
 * @author shangqiankun
 * @version 创建时间：2016年7月25日 上午10:43:59
 */
public class LogSuccessSessionDealService extends AbstractDipperHandler<LogSuccessSessionDealDto> {
    
    private static final Logger log=LoggerFactory.getLogger(LogSuccessSessionDealService.class);

    @Resource
    private IDipperCached cacheClient;
    
    @Override
    public LogSuccessSessionDealDto execute(LogSuccessSessionDealDto dto, Message message) throws Exception {
        
            String userId = dto.getUserId();
            String chnlId = dto.getChnlId();
            String sessionId = dto.getSessionId();
            if(StringUtils.isBlank(userId)){
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户名");
            }
            if(StringUtils.isBlank(chnlId)){
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "渠道id");
            }
            if(StringUtils.isBlank(sessionId)){
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "session");
            }

            String singleLoginFlag = String.valueOf(DipperParm.getParmByKey(CmparmConstants.SINGLE_LOGIN_FLAG));
            //非多渠道登录，失效全部渠道session
            if(CmparmConstants.SINGLE_LOGIN_FLAG_YES.equals(singleLoginFlag)){
                cacheClient.set(CacheConstants.SESSION_APP.concat(userId),"");
                cacheClient.set(CacheConstants.SESSION_WEB.concat(userId),"");
            }
            
            //产生session
            if(CommonConstants_GNR.CHNL_ID_APP.equals(chnlId)){
                int sessionValidMinute = Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_APP_INVALID_MINUTE).toString()) ;
                cacheClient.set(CacheConstants.SESSION_APP.concat(userId),sessionId,sessionValidMinute*60);     
            }else if (CommonConstants_GNR.CHNL_ID_WEB.equals(chnlId)){
                int sessionValidMinute = Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_WEB_INVALID_MINUTE).toString());
                cacheClient.set(CacheConstants.SESSION_WEB.concat(userId),sessionId, sessionValidMinute*60);        
            }
            
            return dto;
    }

}
