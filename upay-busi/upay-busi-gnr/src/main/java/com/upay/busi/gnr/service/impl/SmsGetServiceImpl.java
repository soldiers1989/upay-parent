package com.upay.busi.gnr.service.impl;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

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
import com.upay.busi.gnr.service.dto.SmsGetDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.encryptor.UnionOtpAPI;
import com.upay.commons.util.ValidateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.gnr.GnrSmsConfPo;
import com.upay.dao.po.gnr.GnrTransConfPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 获取短信验证码
 * 
 * @author zacks
 * 
 */
public class SmsGetServiceImpl extends AbstractDipperHandler<SmsGetDto> {
    private static final Logger log = LoggerFactory.getLogger(SmsGetServiceImpl.class);

    @Resource
    IDipperCached cacheClient;
    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService seqService;
    
    @Resource
    UnionOtpAPI unionOtpAPI;


    @Override
    public SmsGetDto execute(SmsGetDto getSmsNoDto, Message message) throws Exception {
    	String userId = getSmsNoDto.getUserId();
    	if(StringUtils.isNotBlank(userId)){
    		log.debug("发送短信验证手机号码是否被篡改   从数据库读取手机号，不使用前端传入     开始-----------------------");
    		//为防止手机号被篡改 如果有userid时 则需要查询出对应的手机号
    		UsrRegInfoPo usrReg=new UsrRegInfoPo();
    		usrReg.setUserId(userId);
    		usrReg=daoService.selectOne(usrReg);
    		if(usrReg==null){
    			ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该用户未注册");
    		}else{
    			String usRmobile = usrReg.getMobile();
    			if(StringUtils.isNotBlank(usRmobile)){
    				getSmsNoDto.setMobile(usRmobile);
    			}else{
    				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "该用户未登记手机号");
    			}
    			
    		}
    		
    		log.debug("发送短信验证手机号码是否被篡改     结束-----------------------");
    	}
    	
        HashMap<String, Object> bodys = (HashMap<String, Object>) message.getTarget().getBodys();

        String smsEventParams = getSmsNoDto.getSmsEventParams();// 短信参数

        String transCode = getSmsNoDto.getTransCode();
        String mobile = getSmsNoDto.getMobile();
        String smsNo = null;
        String smsMsg = null;// 短信模版

        GnrTransConfPo transConf = new GnrTransConfPo();
        transConf.setTransCode(transCode);
        transConf = daoService.selectOne(transConf);
        if (null != transConf) {
            GnrSmsConfPo smsConPo = new GnrSmsConfPo();
            smsConPo.setSmsNo(transConf.getEventNo());
            smsConPo = daoService.selectOne(smsConPo);
            getSmsNoDto.setSmsNo(smsConPo.getSmsNo());
            smsNo = smsConPo.getSmsNo();
            smsMsg = smsConPo.getSmsMsg();
        }
        // ArrayList<String> smsSendMsg=getSmsNoDto.getSmsSendMsgs();
        if (StringUtils.isBlank(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "手机号");
        }
        if (StringUtils.isBlank(smsNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "短息模板编号");
        }

        getSmsNoDto.setPhoneNo(mobile);// 发送短信的手机号

        /** 功能开关 */
        boolean getSmsFlag =
                Boolean.parseBoolean(((String) DipperParm
                    .getParmByKey(CommonConstants_GNR.MOBILE_VERI_CODE_SWITCH)));
        if (getSmsFlag) {
            // 建立链接
            unionOtpAPI.getShortConnection(cacheClient,getSmsCodeServerInfo());
            // 验证手机号格式
            if (!ValidateUtil.checkMobile(mobile)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0009, mobile);
            }
            // GnrSmsConfPo gnrSmsConfPo = new GnrSmsConfPo();
            // gnrSmsConfPo.setSmsNo(smsNo);
            // gnrSmsConfPo=daoService.selectOne(gnrSmsConfPo);
            // if(gnrSmsConfPo==null){
            // ExInfo.throwDipperEx(AppCodeDict.BISGNR0024, smsNo);
            // }
            // String smsMsg=gnrSmsConfPo.getSmsMsg();
            int count = 0;
            int index = 0;

            if (null != smsMsg) {
                String smsMsgCopy = smsMsg;
                while (smsMsgCopy.indexOf("$") != -1) {
                    index = smsMsgCopy.indexOf("$");
                    if (index + 1 <= smsMsgCopy.length()) {
                        smsMsgCopy = smsMsgCopy.substring(index + 1);
                    } else {
                        smsMsgCopy = "";
                    }
                    count++;
                }
            }

            if (CommonConstants_GNR.TRANS_TYPE_SEND_MOBILE_CHKMSG.equals(transCode)) {
                if (count != 1) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0025, smsNo);
                }

                // smsCode="999999";
                String smsTimeoutCheck = cacheClient.get(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile));
                log.debug("缓存中是存在:::::::::::" + smsTimeoutCheck);
                if (StringUtils.isNotBlank(smsTimeoutCheck)) {
                    cacheClient.set(
                        CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile)
                            .concat(CommonConstants_GNR.NO_DELETE), CommonConstants_GNR.NO_DELETE);// 未超过验证码失效时间，不能删除缓存
                    log.debug("设置不删除缓中的验证码:::::::::::"
                            + cacheClient.get(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile).concat(
                                CommonConstants_GNR.NO_DELETE)));
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0013);
                }
                /** 短信验证码 */
                String smsCode = "";
                if (getSmsFlag) {
                    /*
                     * smsCode =
                     * StringUtils.leftPad(String.valueOf(ThreadLocalRandom
                     * .current().nextInt(999999)), 6, "0");
                     */
                    smsCode = unionOtpAPI.getSmsVerificationCode("UPAY:" + mobile);
                    log.debug("UPAY:" + mobile+"               动态验证码返回:::::::::::" + smsCode);
                }
                // 短信发送失效时间(秒)
                long smsTimeout = Long.parseLong(DipperParm.getParmByKey("SMS_TIMEOUT").toString());
                // 短信重新获取时间(秒)
                long smsResendTime = Long.parseLong(DipperParm.getParmByKey("SMS_RESEND_TIME").toString());
                cacheClient.set(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile), smsCode, smsResendTime);
                cacheClient.set(CacheConstants.SMS_NO.concat(mobile), smsCode, smsTimeout);
                getSmsNoDto.setSmsTime(Long.valueOf(smsResendTime).toString());
                getSmsNoDto.setSmsTimeout(String.valueOf(smsTimeout));
                smsMsg = smsMsg.replaceFirst("\\$", smsCode);
            } else {
                if (StringUtils.isBlank(smsEventParams)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "短息模板参数");
                }
                String[] smsParms = smsEventParams.split("\\|");
                if (smsParms == null || smsParms.length == 0) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "短信内容");
                }
                if (count != smsParms.length) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0025, smsNo);
                }
                for (int i = 0; i < smsParms.length; i++) {
                    // smsMsgs[i]=smsMsgs[i].concat();
                    // sendMsg.concat(smsMsgs[i]);
                    log.info("短信模版===============" + bodys.get(smsParms[i]).toString());
                    smsMsg = smsMsg.replaceFirst("\\$", bodys.get(smsParms[i]).toString());
                }
            }
            getSmsNoDto.setSendMsg(smsMsg);
            // getSmsNoDto.setSmsNo(gnrSmsConfPo.getSmsNo());
        } else {
            log.debug("短信功能关闭,验证码设为999999，测试用");
            // 测试用
            String smsCode = "999999";
            // 短信发送失效时间(秒)
            long smsTimeout = Long.parseLong(DipperParm.getParmByKey("SMS_TIMEOUT").toString());
            // 短信重新获取时间(秒)
            long smsResendTime = Long.parseLong(DipperParm.getParmByKey("SMS_RESEND_TIME").toString());
            cacheClient.set(CacheConstants.SMS_RESEND_TIMEOUT.concat(mobile), smsCode, smsResendTime);
            cacheClient.set(CacheConstants.SMS_NO.concat(mobile), smsCode, smsTimeout);
            getSmsNoDto.setSmsTime(Long.valueOf(smsResendTime).toString());
            getSmsNoDto.setSmsTimeout(String.valueOf(smsTimeout));
            smsMsg = smsMsg.replaceFirst("\\$", smsCode);
            getSmsNoDto.setSendMsg(smsMsg);
        }
        if(getSmsNoDto.getIsEsbCore().equals("Y")){
        	getSmsNoDto.setTransSubSeq(seqService.generateEsbNo());
        }
        return getSmsNoDto;
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
