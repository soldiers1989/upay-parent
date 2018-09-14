package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.upay.busi.usr.service.dto.CheckLoginStatDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shangqiankun
 * @version 创建时间：2016年8月1日 下午8:41:25
 * 
 */
public class CheckLoginStatService extends AbstractDipperHandler<CheckLoginStatDto> {
    private static final Logger LOG = LoggerFactory.getLogger(CheckLoginStatService.class);
    
    @Resource
    private IDipperCached cache;

    /**
     * 检查登录状态
     */
    @Override
    public CheckLoginStatDto execute(CheckLoginStatDto dto, Message message) throws Exception {
        LOG.info("检查登录状态开始");
        String userId=dto.getUserId();
        String chnl=dto.getChnlId();
        String sess=dto.getSessionId();
        
        
        
        if(StringUtils.isBlank(sess)){
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0013);
        }
        
        if(StringUtils.isBlank(userId)){
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户id");
        }
        
        if(StringUtils.isBlank(chnl)){
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "渠道id");
        }
    	
		
        String session =null;
        if(CommonConstants_GNR.CHNL_ID_APP.equals(chnl)){
            session=cache.get(CacheConstants.SESSION_APP+userId);
        }else if (CommonConstants_GNR.CHNL_ID_WEB.equals(chnl)){
            session=cache.get(CacheConstants.SESSION_WEB+userId);
        }

        //session不为空并且匹配，更新session
        if(StringUtils.isNotBlank(session)){
            if(sess.equals(session)){
                if(CommonConstants_GNR.CHNL_ID_APP.equals(chnl)){
                    int sessionValidMinute = Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_APP_INVALID_MINUTE).toString()) ;
                    LOG.debug("设置session失效时间："+userId+"："+sessionValidMinute+"分钟");
                    cache.set(CacheConstants.SESSION_APP.concat(userId), sess,sessionValidMinute*60);
                }else if(CommonConstants_GNR.CHNL_ID_WEB.equals(chnl)){
                    int sessionValidMinute = Integer.valueOf(DipperParm.getParmByKey(CmparmConstants.SESSION_WEB_INVALID_MINUTE).toString());
                    LOG.debug("设置session失效时间："+userId+"："+sessionValidMinute+"分钟");
                    cache.set(CacheConstants.SESSION_WEB.concat(userId),sess, sessionValidMinute*60);
                }                
            }else{
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0052);
            }
            dto.setSessionId(session);
        }else{//session为空或者不相匹配，删除session
            if (CommonConstants_GNR.CHNL_ID_APP.equals(chnl)) {
                cache.delete(CacheConstants.SESSION_APP.concat(userId));
            } else if (CommonConstants_GNR.CHNL_ID_WEB.equals(chnl)) {
                cache.delete(CacheConstants.SESSION_WEB.concat(userId));
            }
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0013);
        }
        
        cache.set(session+"name", "liubing   "+session);
        LOG.debug("缓存中的名字::::::::"+cache.get(session+"name"));
        
        String tokenValue = dto.getTokenId();
    	String sessionId=dto.getSessionId();
    	LOG.debug("sessionId:::::::::::::::"+sessionId);
    	LOG.debug("前端传入的token value:::::::::::::::"+tokenValue);
    	
    	LOG.debug("验证是否重复1 :::::::::::::::");
		String cacheTokenKey = CacheConstants.TOKEN_CODE.concat(sessionId);
		LOG.debug("缓存中的的token keys:::::::::::::::"+cacheTokenKey);
		String cacheTokenValue = cache.get(cacheTokenKey);
		LOG.debug("缓存中的的token value:::::::::::::::"+cacheTokenValue);
		
		//当传入的token ID 并且sesseionId不能空时 验证是否是重复点击    
		if(StringUtils.isNotBlank(tokenValue)){
			if (StringUtils.isNotBlank(cacheTokenValue)) {
				LOG.debug("验证是否重复结束2 :::::::::::::::");
				if (tokenValue.equals(cacheTokenValue)) {
					cache.delete(cacheTokenKey);
					LOG.debug("验证是否重复3 :::::::::::::::");
				} else {
					LOG.debug("验证是否重复4 :::::::::::::::");
					ExInfo.throwDipperEx(AppCodeDict.CIPSYS0010,"TOKEN_ID");
				}
			} else {
				LOG.debug("验证是否重复5 :::::::::::::::");
				ExInfo.throwDipperEx(AppCodeDict.CIPSYS0010, "TOKEN_ID");
			}
		}
		LOG.debug("验证是否重复结束 :::::::::::::::");

        LOG.info("检查登录状态结束");
        return dto;
    }

}
