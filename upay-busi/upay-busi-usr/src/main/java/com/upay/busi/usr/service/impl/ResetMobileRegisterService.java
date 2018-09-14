package com.upay.busi.usr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.ResetMobileRegisterDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrChgMobileApplyPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 手机号变更申请
 * 
 * @author liu
 * 
 */
public class ResetMobileRegisterService extends AbstractDipperHandler<ResetMobileRegisterDto> {

    private static final Logger LOG = LoggerFactory.getLogger(ResetMobileRegisterService.class);

    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService seq;


    @Override
    public ResetMobileRegisterDto execute(ResetMobileRegisterDto resetMobileRegisterDto, Message msg)
            throws Exception {

        String userId = resetMobileRegisterDto.getUserId();// 用户id
        String chnlId = resetMobileRegisterDto.getChnlId();// 渠道号
        String applyType = resetMobileRegisterDto.getApplyType();// 申请方式
        String certName = resetMobileRegisterDto.getCertName();// 证件姓名
        String certType = resetMobileRegisterDto.getCertType();// 证件类型
        String certNo = resetMobileRegisterDto.getCertNo();// 证件号
        String certFront = resetMobileRegisterDto.getCertFront();// 证件正面照
        String certBack = resetMobileRegisterDto.getCertBack();// 证件背面照
        String certPerson = resetMobileRegisterDto.getCertPerson();// 用户手持证件照
        String oldMobile = resetMobileRegisterDto.getOldMobile();// 旧手机号
        String newMobile = resetMobileRegisterDto.getNewMobile();// 新手机号
        // String transCode = resetMobileRegisterDto.getTransCode();

        if (StringUtils.isBlank(applyType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "申请方式"); // 申请方式不可为空
        }

        // 验证用户是否有提交带审批的申请修改手机号记录
        UsrChgMobileApplyPo usrChgMobileApplyCheckPo = new UsrChgMobileApplyPo();
        usrChgMobileApplyCheckPo.setUserId(userId);
        usrChgMobileApplyCheckPo.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_WAIT);
        usrChgMobileApplyCheckPo = daoService.selectOne(usrChgMobileApplyCheckPo);
        if (null != usrChgMobileApplyCheckPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0028);
        }

        // 验证新手机号是否已经提交审批且为待审核状态
        UsrChgMobileApplyPo usrChgMobileApplyCheckMobilePo = new UsrChgMobileApplyPo();
        usrChgMobileApplyCheckMobilePo.setMobile(newMobile);
        usrChgMobileApplyCheckMobilePo.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_WAIT);
        usrChgMobileApplyCheckMobilePo = daoService.selectOne(usrChgMobileApplyCheckMobilePo);
        if (null != usrChgMobileApplyCheckMobilePo) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0038);
        }

        // 验证新手机号是否注册
      /*  UsrRegInfoPo usrRegInfoCheckMobilePo = new UsrRegInfoPo();
        usrRegInfoCheckMobilePo.setMobile(newMobile);
        usrRegInfoCheckMobilePo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
        usrRegInfoCheckMobilePo = daoService.selectOne(usrRegInfoCheckMobilePo);
        if (null != usrRegInfoCheckMobilePo) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0039, newMobile); // 手机号已被注册
        }*/

        // 获取用户认证等级
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (null == usrRegInfoPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId); // 用户不存在
        }
        String userCertLevel = usrRegInfoPo.getUserCertLevel();

        // 登记用户修改手机号申请表---------数据库表需要添加字段
        UsrChgMobileApplyPo usrChgMobileApplyPo = new UsrChgMobileApplyPo();
        if (DataBaseConstants_USR.APPLY_TYPE_ONLINE.equals(applyType)) {// 联机(实时)修改
            // 验证原手机号是否正确
            UsrRegInfoPo usrRegInfoCheckOldMobilePo = new UsrRegInfoPo();
            usrRegInfoCheckOldMobilePo.setUserId(userId);
            usrRegInfoCheckOldMobilePo.setMobile(oldMobile);
            usrRegInfoCheckOldMobilePo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
            usrRegInfoCheckOldMobilePo = daoService.selectOne(usrRegInfoCheckOldMobilePo);
            if (null == usrRegInfoCheckOldMobilePo) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId); // 用户不存在
            }
            String regType = usrRegInfoCheckOldMobilePo.getRegType();

            if (DataBaseConstants_USR.USER_CERT_LEVEL_MOBILE.equals(userCertLevel)) {// 用户认证等级为手机认证等级
                // 修改手机号码
                UsrRegInfoPo usrRegInfoUpdPo = new UsrRegInfoPo();
                usrRegInfoUpdPo.setMobile(newMobile);
                usrRegInfoUpdPo.setLastUpdateTime(resetMobileRegisterDto.getSysTime());
                // if (regType != DataBaseConstants_USR.USER_REG_TYPE_MEMBER) {
                // usrRegInfoUpdPo.setUserName(newMobile);
                // }
                UsrRegInfoPo usrRegInfoWherePo = new UsrRegInfoPo();
                usrRegInfoWherePo.setUserId(userId);
                daoService.update(usrRegInfoUpdPo, usrRegInfoWherePo);
            } else if (DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH.equals(userCertLevel)) {// 用户认证等级为弱实名认证等级
                if (StringUtils.isBlank(certName)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "证件名");
                }
                if (StringUtils.isBlank(certType)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "证件类型");
                }
                if (StringUtils.isBlank(certNo)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "证件号");
                }
                certNo = StringUtils.upperCase(certNo);
                // 验证用户证件号信息 应该验证用户基本信息表
                UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
                usrBaseInfoPo.setUserId(userId);
                usrBaseInfoPo.setCertType(certType);
                usrBaseInfoPo.setCertNo(certNo);
                usrBaseInfoPo.setCertName(certName);

                usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
                if (null == usrBaseInfoPo) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "用户基本信息"); // 用户基本信息不存在
                }
                usrChgMobileApplyPo.setCertName(certName);
                usrChgMobileApplyPo.setCertType(certType);
                usrChgMobileApplyPo.setCertNo(certNo);
            }
            usrChgMobileApplyPo.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_YES);
            usrChgMobileApplyPo.setOldMobile(oldMobile);
            // 修改用户 手机号
            UsrRegInfoPo usrRegInfoChgMobilePo = new UsrRegInfoPo();
            usrRegInfoChgMobilePo.setMobile(newMobile);
            usrRegInfoChgMobilePo.setLastUpdateTime(resetMobileRegisterDto.getSysTime());
            // if (regType != DataBaseConstants_USR.USER_REG_TYPE_MEMBER) {
            // usrRegInfoChgMobilePo.setUserName(newMobile);
            // }
            UsrRegInfoPo usrRegInfoChgMobileWherePo = new UsrRegInfoPo();
            usrRegInfoChgMobileWherePo.setUserId(userId);
            daoService.update(usrRegInfoChgMobilePo, usrRegInfoChgMobileWherePo);

        } else if (DataBaseConstants_USR.APPLY_TYPE_VERIFY.equals(applyType)) {// 审核修改
            if (StringUtils.isBlank(certFront)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "身份证正面照文件名");
            }
            if (StringUtils.isBlank(certBack)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "身份证背面照文件名");
            }
            if (StringUtils.isBlank(certPerson)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "身份证本人手持照文件名");
            }
            if (DataBaseConstants_USR.USER_CERT_LEVEL_MOBILE.equals(userCertLevel)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0042); // 用户认证等级为手机认证，不能进行审核认证
            }
            // 验证用户证件号信息
            UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
            usrBaseInfoPo.setUserId(userId);
            usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
            if (null == usrBaseInfoPo) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId); // 用户不存在
            }
            String certTypes = usrBaseInfoPo.getCertType();
            String certNos = usrBaseInfoPo.getCertNo();
            String certNames = usrBaseInfoPo.getCertName();

            // 获取原手机号
            UsrRegInfoPo usrRegInfoGetMobilePo = new UsrRegInfoPo();
            usrRegInfoGetMobilePo.setUserId(userId);
            usrRegInfoGetMobilePo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
            usrRegInfoGetMobilePo = daoService.selectOne(usrRegInfoGetMobilePo);
            if (null == usrRegInfoGetMobilePo) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId); // 用户不存在
                                                                      // //用户状态异常
            }
            String oldMobiles = usrRegInfoGetMobilePo.getMobile();
            usrChgMobileApplyPo.setOldMobile(oldMobiles);

            usrChgMobileApplyPo.setCertName(certNames);
            usrChgMobileApplyPo.setCertType(certTypes);
            usrChgMobileApplyPo.setCertNo(certNos);
            usrChgMobileApplyPo.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_WAIT);
            usrChgMobileApplyPo.setCertFront(certFront);
            usrChgMobileApplyPo.setCertBack(certBack);
            usrChgMobileApplyPo.setCertPerson(certPerson);
        }
        usrChgMobileApplyPo.setUserId(userId);
        usrChgMobileApplyPo.setMobile(newMobile);
        usrChgMobileApplyPo.setApplyTime(new Date());
        usrChgMobileApplyPo.setApplyChnlId(chnlId);
        usrChgMobileApplyPo.setApplyFlag(applyType);
        usrChgMobileApplyPo.setWfStatus(DataBaseConstants_USR.WF_STATUS_1);
        usrChgMobileApplyPo.setAuthApplyNo(seq.authApplyNo());// 新加的认证申请编号
        daoService.insert(usrChgMobileApplyPo);

        return resetMobileRegisterDto;
    }

}
