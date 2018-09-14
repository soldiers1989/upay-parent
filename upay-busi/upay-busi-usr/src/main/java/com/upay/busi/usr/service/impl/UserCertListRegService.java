package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserCertListRegDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.gnr.GnrUserCertListPo;


/**
 * 身份证联网核查登记记录
 * 
 * @author liubing
 * 
 */
public class UserCertListRegService extends AbstractDipperHandler<UserCertListRegDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public UserCertListRegDto execute(UserCertListRegDto userCertListRegDto, Message message)
            throws Exception {
        String isOnlineCertCheck = userCertListRegDto.getIsOnlineCertCheck();
        if (StringUtils.isNotBlank(isOnlineCertCheck)) {
            String certCheckChnl = userCertListRegDto.getCertCheckChnl();
            String certType = userCertListRegDto.getCertType();
            if (StringUtils.isBlank(certCheckChnl)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "certCheckChnl");
            }
            if (StringUtils.isBlank(certType)) {
                certType = DataBaseConstants_USR.CERT_TYPE_CERT;
            }

            GnrUserCertListPo gnrUserCertListPo = new GnrUserCertListPo();
            gnrUserCertListPo.setCertCheckChnl(certCheckChnl);
            gnrUserCertListPo.setCertType(certType);
            gnrUserCertListPo.setCertNo(userCertListRegDto.getCertNo());
            gnrUserCertListPo.setCertCheckTime(userCertListRegDto.getSysTime());
            String certCheckResult = DataBaseConstants_USR.CERT_CHECK_RESULT_SUCCESS;
            String rspCode = message.getFault().getCode();
            if (!DataBaseConstants_USR.RSP_CODE_SUCCESS.equals(rspCode)) {
                certCheckResult = DataBaseConstants_USR.CERT_CHECK_RESULT_FAIL;
                String rspMsg = message.getFault().getMsg();
                gnrUserCertListPo.setCertCheckFailMsg(rspMsg);
            }
            gnrUserCertListPo.setCertCheckResult(certCheckResult);
            daoService.insert(gnrUserCertListPo);
        }
        return userCertListRegDto;
    }
}
