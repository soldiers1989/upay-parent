package com.upay.busi.gnr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.gnr.service.dto.SmsListRegDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.gnr.GnrUserSmsListPo;


/**
 * 短信发送成功登记记录
 * 
 * @author freeplato
 * 
 */
public class SmsListRegServiceImpl extends AbstractDipperHandler<SmsListRegDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public SmsListRegDto execute(SmsListRegDto smsListRegDto, Message message) throws Exception {
        Date sysTime = new Date();
        String mobile = smsListRegDto.getMobile();
        String sendMessage = smsListRegDto.getSendMsg();
        if (StringUtils.isBlank(mobile)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "手机号");
        }
        if (StringUtils.isNotBlank(sendMessage)) {
            GnrUserSmsListPo sms = new GnrUserSmsListPo();
            sms.setSmsSendNo(CommonConstants_GNR.SMS_SEND_NO_CORE);
            sms.setMobile(mobile);
            sms.setSmsNo(smsListRegDto.getSmsNo());
            sms.setSmsRightMsg(sendMessage);
            sms.setSmsSendTime(sysTime);
            daoService.insert(sms);
        }

        // Map<String, List<String>> smsSendMsgsMap =
        // smsListRegDto.getSmsSendMsgs();
        // Iterator<String> it = smsSendMsgsMap.keySet().iterator();
        // while(it.hasNext()){
        // String mobile = it.next();
        // List<String> smsSendMsgs = smsSendMsgsMap.get(mobile);
        // for (String smsMsg : smsSendMsgs) {
        // GnrUserSmsListPo gnrUserSmsListPo = new GnrUserSmsListPo();
        // gnrUserSmsListPo.setMobile(mobile);
        // gnrUserSmsListPo.setSmsRightMsg(smsMsg);
        // gnrUserSmsListPo.setSmsSendTime(sysTime);
        // gnrUserSmsListPo.setSmsSendNo(DataBaseConstants.SMS_SEND_NO_YIYANGXINTONG);
        // daoService.insert(gnrUserSmsListPo);
        // }
        // }
        return smsListRegDto;
    }

}
