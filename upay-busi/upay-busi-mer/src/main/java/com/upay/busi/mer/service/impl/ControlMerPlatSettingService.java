/**
 * 
 */
package com.upay.busi.mer.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.mer.service.dto.ControlMerPlatSettingDto;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerPlatSettingPo;


/**
 * 操作商户参数配置
 * 
 * @author zhanggr
 *
 */
public class ControlMerPlatSettingService extends AbstractDipperHandler<ControlMerPlatSettingDto> {

    @Resource(name = "DIPPER_REDIS_CLIENT")
    private IDipperCached cacheClient;
    @Resource
    private IDaoService daoService;


    @Override
    public ControlMerPlatSettingDto execute(ControlMerPlatSettingDto controlMerPlatSettingDto, Message msg)
            throws Exception {
        // 增删改查 0：增加 1：删除 2：改 3：查
        String controlChnlId = controlMerPlatSettingDto.getControlChnlId();
        String contralFlag = controlMerPlatSettingDto.getContralFlag();
        String merNo = controlMerPlatSettingDto.getMerPlatNo();
        if (StringUtils.isBlank(contralFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "操作标识");
        }
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户号");
        }
        String merKey = "";
        MerPlatSettingPo merPlatSetting = new MerPlatSettingPo();
        if (DateBaseConstants_MER.CONTROL_MERPLAT_SETTING_FLAG_THREE.equals(contralFlag)) {
            if (StringUtils.isBlank(controlChnlId)) {
                merKey = cacheClient.get(merNo);
                if (StringUtils.isBlank(merKey)) {
                    merPlatSetting.setMerPlatNo(merNo);
                    merPlatSetting = daoService.selectOne(merPlatSetting);
                    if (merPlatSetting != null) {
                        String keyValue = merPlatSetting.getKey3des();
                        if (StringUtils.isNotBlank(keyValue)) {
                            merKey = keyValue;
                            cacheClient.set(merNo, keyValue);
                        } else {
                            ExInfo.throwDipperEx(AppCodeDict.BISMER0021, "商户密钥");
                        }
                    } else {
                        ExInfo.throwDipperEx(AppCodeDict.BISMER0033, merNo);
                    }
                }
                controlMerPlatSettingDto.setMerKey(merKey);
            } else {
                merPlatSetting.setMerPlatNo(merNo);
                merPlatSetting = daoService.selectOne(merPlatSetting);
                if (merPlatSetting != null) {
                    controlMerPlatSettingDto.setCheckFileFormType(merPlatSetting.getCheckFileFormType());
                    controlMerPlatSettingDto.setClearCheckFlag(merPlatSetting.getClearCheckFlag());
                    controlMerPlatSettingDto.setDateLastMaint(merPlatSetting.getDateLastMaint());
                    controlMerPlatSettingDto.setFileEncryType(merPlatSetting.getFileEncryType());
                    controlMerPlatSettingDto.setFileTransferMode(merPlatSetting.getFileTransferMode());
                    controlMerPlatSettingDto.setFileUploadPath(merPlatSetting.getFileUploadPath());
                    controlMerPlatSettingDto.setFileUrl(merPlatSetting.getFileUrl());
                    controlMerPlatSettingDto.setFtpIp(merPlatSetting.getFtpIp());
                    controlMerPlatSettingDto.setFtpPort(merPlatSetting.getFtpPort());
                    controlMerPlatSettingDto.setFtpPwd(merPlatSetting.getFtpPwd());
                    controlMerPlatSettingDto.setFtpUserName(merPlatSetting.getFtpUserName());
                    controlMerPlatSettingDto.setKey3des(merPlatSetting.getKey3des());
                    controlMerPlatSettingDto.setSignCheckFlag(merPlatSetting.getSignCheckFlag());
                    controlMerPlatSettingDto.setSignType(merPlatSetting.getSignType());
                    controlMerPlatSettingDto.setReferUrl(merPlatSetting.getReferUrl());
                    controlMerPlatSettingDto.setModifyUser(merPlatSetting.getModifyUser());
                    controlMerPlatSettingDto.setMerRepUrlF(merPlatSetting.getMerRepUrlF());
                    controlMerPlatSettingDto.setMerRepUrlB(merPlatSetting.getMerRepUrlB());
                } else {
                    ExInfo.throwDipperEx(AppCodeDict.BISMER0033, merNo);
                }
            }

        } else if (DateBaseConstants_MER.CONTROL_MERPLAT_SETTING_FLAG_TWO.equals(contralFlag)) {
        	MerPlatSettingPo merPlatSetting1 = new MerPlatSettingPo();
        	merPlatSetting1.setMerPlatNo(merNo);
            // 更新
            merPlatSetting.setCheckFileFormType(controlMerPlatSettingDto.getCheckFileFormType());
            merPlatSetting.setClearCheckFlag(controlMerPlatSettingDto.getClearCheckFlag());
            merPlatSetting.setDateLastMaint(new Date());
            merPlatSetting.setFileEncryType(controlMerPlatSettingDto.getFileEncryType());
            merPlatSetting.setFileTransferMode(controlMerPlatSettingDto.getFileTransferMode());
            merPlatSetting.setFileUploadPath(controlMerPlatSettingDto.getFileUploadPath());
            merPlatSetting.setFileUrl(controlMerPlatSettingDto.getFileUrl());
            merPlatSetting.setFtpIp(controlMerPlatSettingDto.getFtpIp());
            merPlatSetting.setFtpPort(controlMerPlatSettingDto.getFtpPort());
            merPlatSetting.setFtpUserName(controlMerPlatSettingDto.getFtpUserName());
            merPlatSetting.setKey3des(controlMerPlatSettingDto.getKey3des());
            merPlatSetting.setMerPlatNo(merNo);
            merPlatSetting.setMerRepUrlB(controlMerPlatSettingDto.getMerRepUrlB());
            merPlatSetting.setMerRepUrlF(controlMerPlatSettingDto.getMerRepUrlF());
            merPlatSetting.setModifyUser(controlMerPlatSettingDto.getModifyUser());
            merPlatSetting.setReferUrl(controlMerPlatSettingDto.getReferUrl());
            merPlatSetting.setSignCheckFlag(controlMerPlatSettingDto.getSignCheckFlag());
            merPlatSetting.setSignType(controlMerPlatSettingDto.getSignType());
            daoService.update(merPlatSetting,merPlatSetting1);
            if (StringUtils.isNotBlank(controlMerPlatSettingDto.getKey3des())) {
                cacheClient.set(merNo, controlMerPlatSettingDto.getKey3des());
            }
        } else if (DateBaseConstants_MER.CONTROL_MERPLAT_SETTING_FLAG_ONE.equals(contralFlag)) {
            merPlatSetting.setMerPlatNo(merNo);
            daoService.delete(merPlatSetting);
            cacheClient.delete(merNo);
            cacheClient.delete(merNo + ":merRepUrlB");
        } else if (DateBaseConstants_MER.CONTROL_MERPLAT_SETTING_FLAG_ZERO.equals(contralFlag)) {
            merPlatSetting.setCheckFileFormType(controlMerPlatSettingDto.getCheckFileFormType());
            merPlatSetting.setClearCheckFlag(controlMerPlatSettingDto.getClearCheckFlag());
            merPlatSetting.setDateLastMaint(new Date());
            merPlatSetting.setFileEncryType(controlMerPlatSettingDto.getFileEncryType());
            merPlatSetting.setFileTransferMode(controlMerPlatSettingDto.getFileTransferMode());
            merPlatSetting.setFileUploadPath(controlMerPlatSettingDto.getFileUploadPath());
            merPlatSetting.setFileUrl(controlMerPlatSettingDto.getFileUrl());
            merPlatSetting.setFtpIp(controlMerPlatSettingDto.getFtpIp());
            merPlatSetting.setFtpPort(controlMerPlatSettingDto.getFtpPort());
            merPlatSetting.setFtpUserName(controlMerPlatSettingDto.getFtpUserName());
            merPlatSetting.setKey3des(controlMerPlatSettingDto.getKey3des());
            merPlatSetting.setMerPlatNo(merNo);
            merPlatSetting.setMerRepUrlB(controlMerPlatSettingDto.getMerRepUrlB());
            merPlatSetting.setMerRepUrlF(controlMerPlatSettingDto.getMerRepUrlF());
            merPlatSetting.setModifyUser(controlMerPlatSettingDto.getModifyUser());
            merPlatSetting.setReferUrl(controlMerPlatSettingDto.getReferUrl());
            merPlatSetting.setSignCheckFlag(controlMerPlatSettingDto.getSignCheckFlag());
            merPlatSetting.setSignType(controlMerPlatSettingDto.getSignType());
            daoService.insert(merPlatSetting);
            cacheClient.set(merNo, controlMerPlatSettingDto.getKey3des());
            if (StringUtils.isNotBlank(controlMerPlatSettingDto.getMerRepUrlB())) {
                cacheClient.set(merNo + ":merRepUrlB", controlMerPlatSettingDto.getMerRepUrlB());
            }
        } else {
            ExInfo.throwDipperEx(AppCodeDict.BISMER0036);
        }

        return controlMerPlatSettingDto;
    }
}
