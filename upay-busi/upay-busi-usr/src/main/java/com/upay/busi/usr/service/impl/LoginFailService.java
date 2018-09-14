package com.upay.busi.usr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.usr.service.dto.LoginFailDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ParmsContext;
import com.upay.dao.po.usr.UsrLogListPo;


/**
 * @author shangqiankun
 * @version 创建时间：2016年7月22日 上午8:34:36
 */
public class LoginFailService extends AbstractDipperHandler<LoginFailDto> {

    private static final Logger log = LoggerFactory.getLogger(LoginFailService.class);

    @Resource
    private IDaoService daoService;
    @Resource
    private IDipperCached cached;


    /**
     * 登陆失败处理
     */
    @Override
    public LoginFailDto execute(LoginFailDto dto, Message message) throws Exception {
        Date date = new Date();
        // 校验参数
        String userId = dto.getUserId();
        String session =dto.getSessionId();
        if (session != null) {
            // 记录登录日志表
            UsrLogListPo where = new UsrLogListPo();
            where.setSessionId(session);
            UsrLogListPo param = new UsrLogListPo();
            param.setFailReason(message.getFault().getMsg());
            param.setLoginStat(DataBaseConstants_USR.USR_LOGIN_STAT_FAIL);
            daoService.update(param, where);
//            // 系统设置的登录密码错误次数
//            int loginVerifyTimes =
//                    ParmsContext.getParmByKey(CmparmConstants.LOGIN_VERIFY_TIMES) == null ? 0 : Integer
//                        .parseInt(ParmsContext.getParmByKey(CmparmConstants.LOGIN_VERIFY_TIMES).toString());
//
//            // 此会话的当前登录密码错误次数
//            int loginPwdErrorTimes = 1;
//            if (cached.keyExists(CacheConstants.LOGIN_PWD_ERROR_TIMES.concat(userId))) {
//                loginPwdErrorTimes += (int) cached.get(CacheConstants.LOGIN_PWD_ERROR_TIMES.concat(userId));
//            }
//            cached.set(CacheConstants.LOGIN_PWD_ERROR_TIMES.concat(userId), loginPwdErrorTimes, 30 * 60);
//
//            if (loginVerifyTimes <= loginPwdErrorTimes) {
//                cached.set(CacheConstants.VERIFY_CODE.concat(userId), "true", 30 * 60);
//            }
            
            //判断是否达到需要输入图形验证码验证条件，记录需要验证图形验证码标注
      		String verifyCodeFlag = CommunicationConstants.VERIFY_CODE_FLAG_NO;

      		String cacheKey = dto.getVerifyKey();
      		if(StringUtils.isBlank(cacheKey)){
      			cacheKey = userId;
      			
      			if(StringUtils.isBlank(cacheKey)){
      				return dto;
      			}
      		}
          	
      		//系统设置的登录密码错误次数
      		int loginVerifyTimes = Integer.valueOf(ParmsContext.getParmByKey(CmparmConstants.LOGIN_VERIFY_TIMES).toString());

      		//此会话的当前登录密码错误次数
      		int loginPwdErrorTimes = 1;
      		if(cached.keyExists(CacheConstants.LOGIN_PWD_ERROR_TIMES.concat(cacheKey))){
      			loginPwdErrorTimes += (int)cached.get(CacheConstants.LOGIN_PWD_ERROR_TIMES.concat(cacheKey));
      		}
      		cached.set(CacheConstants.LOGIN_PWD_ERROR_TIMES.concat(cacheKey),loginPwdErrorTimes,30*60);

      		if(loginVerifyTimes <= loginPwdErrorTimes){
      			cached.set(CacheConstants.VERIFY_CODE.concat(cacheKey), "true",30*60);
      			verifyCodeFlag = CommunicationConstants.VERIFY_CODE_FLAG_YES;
      		}
      		
      		dto.setVerifyCodeFlag(verifyCodeFlag);
        }
        return dto;
    }

}
