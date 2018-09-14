package com.upay.busi.gnr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.gnr.service.dto.SmsVerifyDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.encryptor.UnionOtpAPI;
import com.upay.commons.util.ValidateUtil;


/**
 * 短信验证码校验
 * 
 * @author freeplato
 * 
 */
public class SmsVerifyServiceImpl extends AbstractDipperHandler<SmsVerifyDto> {
	private static final Logger log = LoggerFactory.getLogger(SmsVerifyServiceImpl.class);
    @Resource
    private IDipperCached cacheClient;

    @Resource
    UnionOtpAPI unionOtpAPI;

    @Resource
    private IDaoService daoService;
    @Override
    public SmsVerifyDto execute(SmsVerifyDto smsVerifyDto, Message message) throws Exception {

        String smsCode = smsVerifyDto.getSmsCode();

        String mobile = smsVerifyDto.getMobile();

        if (StringUtils.isBlank(smsCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "SMS_CODE");
        }
        if (StringUtils.isBlank(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "手机号");
        }
        // 验证手机号格式
        if (!ValidateUtil.checkMobile(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0009, mobile);
        }

        if (!cacheClient.keyExists(CacheConstants.SMS_NO.concat(mobile))) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0008, mobile);// 验证码已失效
        }
        // String smsNoCache =
        // cacheClient.get(CacheConstants.SMS_NO.concat(mobile));

        /** 功能开关 */
        boolean getSmsFlag =
                Boolean.parseBoolean(((String) DipperParm
                    .getParmByKey(CommonConstants_GNR.MOBILE_VERI_CODE_SWITCH)));
        if (getSmsFlag) {
            unionOtpAPI.getShortConnection(cacheClient,getSmsCodeServerInfo());
            log.debug("UPAY:" + mobile+"       前端传入的验证码是::::::::::::"+smsCode);
            boolean flag = unionOtpAPI.VerifySmsVerificationCode("UPAY:" + mobile, smsCode);
            if (!flag) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0009);// 验证码输入错误
            } else {
                cacheClient.delete(CacheConstants.SMS_NO.concat(mobile));
                cacheClient.delete(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile));
            }
        } else {
            String smsCodeFlag = "999999";// 测试用
            if (!smsCode.equals(smsCodeFlag)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0009);// 验证码输入错误
            } else {
                cacheClient.delete(CacheConstants.SMS_NO.concat(mobile));
                cacheClient.delete(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile));
            }
        }

        smsVerifyDto.setSmsCodeVerifyFlag(CommonConstants_GNR.SMS_CODE_VERIFY_FLAG_YES);
        return smsVerifyDto;
    }
    
    private String getSmsCodeServerInfo(){
		String getSmsCode = cacheClient.get("GET_SMS_CODE");
		if(StringUtils.isNotBlank(getSmsCode)){
			return getSmsCode;
		}
		GnrParmPo parm=new GnrParmPo();
		parm.setParmId("GET_SMS_CODE");
		parm = daoService.selectOne(parm);
		if(parm!=null){
			getSmsCode=parm.getParmValue();
			cacheClient.add("GET_SMS_CODE", getSmsCode);
		}
		return getSmsCode;
	}
}
