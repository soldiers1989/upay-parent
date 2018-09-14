package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.CertCheckDto;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrBlackListPo;
import com.upay.dao.po.usr.UsrCertListHisPo;
import com.upay.dao.po.usr.UsrCertListPo;


/**
 * 用户身份验证，验证用户身份信息
 * 
 * @author liu
 * 
 */
public class CertCheckService extends AbstractDipperHandler<CertCheckDto> {

    private static final Logger LOG = LoggerFactory.getLogger(CertCheckService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public CertCheckDto execute(CertCheckDto certCheckDto, Message msg) throws Exception {

        String userId = certCheckDto.getUserId();
        String certFlag = certCheckDto.getCertFlag(); // 认证方式
        String certName = certCheckDto.getCertName(); // 证件名
        String certType = certCheckDto.getCertType(); // 证件类型
        String approveType = certCheckDto.getApproveType();
        
        String certNo = certCheckDto.getCertNo().toUpperCase();// 将取到的证件号转成大写，不做区分(主要是针对最后一位为X的)
        if (StringUtils.isBlank(certType)) {
            certType = DataBaseConstants_USR.CERT_TYPE_CERT; // 如果上送的证件类型为空，则默认为身份证‘01’
        }
        String userCertLevel = certCheckDto.getUserCertLevel().trim();

        // 实时认证 certFlag = "1"
        if (DataBaseConstants_USR.CERT_FLAG_ACTUAL_CERT.equals(certFlag)) {

            // 实时认证和联机认证合并，如果已经实名认证了，就直接实时，如果没实名认证，就走联机认证
            if (DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH.equals(userCertLevel)) { // 实名认证用户
                UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
                usrBaseInfoPo.setUserId(userId);
                usrBaseInfoPo.setCertType(certType);
                usrBaseInfoPo.setCertNo(certNo);
                usrBaseInfoPo.setCertName(certName);
                usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
                if (usrBaseInfoPo == null) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0027); // 认证信息不匹配
                }

            } else if (DataBaseConstants_USR.USER_CERT_LEVEL_MOBILE.equals(userCertLevel)) { // 未做实名认证
                // 验证用户是否在自然人黑名单控制表中
                UsrBlackListPo usrBlackListPo = new UsrBlackListPo();
                usrBlackListPo.setUserId(userId);
                // usrBlackListPo.setCertNo(certNo);
                usrBlackListPo.setBlacklistFlag(DataBaseConstants_USR.BLACK_LIST_FLAG_YES); // 黑名单标识
                                                                                            // 1---黑名单
                usrBlackListPo = daoService.selectOne(usrBlackListPo);
                if (usrBlackListPo != null) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0014, userId); // 用户已被列入黑名单
                }

                // 调用身份核查系统验证
                certCheckDto.setIsOnlineCertCheck(CommunicationConstants.IS_ONLINE_CERT_CHECK_YES);
            }
            // 审核认证 certFlag = "2"
        } else if (DataBaseConstants_USR.CERT_FLAG_VERIFY.equals(certFlag)) {

            UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
            usrBaseInfoPo.setUserId(userId);
            usrBaseInfoPo.setCertType(certType);
            usrBaseInfoPo.setCertNo(certNo);
            usrBaseInfoPo.setCertName(certName);
            usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
            if (usrBaseInfoPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0027); // 您提交的身份信息与实名认证身份信息不匹配
            }

            String certFront = certCheckDto.getCertFront();
            String certBack = certCheckDto.getCertBack();
            String certPerson = certCheckDto.getCertPerson();
            if (StringUtils.isBlank(certFront)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "身份证正面照文件名");
            }
            if (StringUtils.isBlank(certBack)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "身份证反面照文件名");
            }
            if (StringUtils.isBlank(certPerson)) {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "身份证本人手持文件名");
            }

            // 判断用户是否有待审批的弱实名认证 用户实名认证表
            UsrCertListPo usrCertListCheckPo = new UsrCertListPo();
            usrCertListCheckPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
            usrCertListCheckPo.setUserId(userId);
            usrCertListCheckPo.setCertStat(DataBaseConstants_USR.CERT_STAT_WAIT);
            usrCertListCheckPo = daoService.selectOne(usrCertListCheckPo);
            if (usrCertListCheckPo == null) {
                // 判断用户是否有待审批的弱实名认证 用户实名认证历史表
                UsrCertListHisPo usrCertListHisCheckPo = new UsrCertListHisPo();
                usrCertListHisCheckPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
                usrCertListHisCheckPo.setUserId(userId);
                usrCertListHisCheckPo.setCertStat(DataBaseConstants_USR.CERT_STAT_WAIT);
                usrCertListHisCheckPo = daoService.selectOne(usrCertListHisCheckPo);
                if (null != usrCertListHisCheckPo) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0028); // 您已有待审批的申请，请等待审核，不要重复提交
                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0028); // 您已有待审批的申请，请等待审核，不要重复提交
            }

            UsrCertListPo usrCertListPo = new UsrCertListPo();
            usrCertListPo.setCertWeakWay(DataBaseConstants_USR.CERT_WEAK_WAY_UPLOAD_CERT);
            usrCertListPo.setCertStat(DataBaseConstants_USR.CERT_STAT_WAIT);
            usrCertListPo.setCertFront(certCheckDto.getCertFront());
            usrCertListPo.setCertBack(certCheckDto.getCertBack());
            usrCertListPo.setCertPerson(certCheckDto.getCertPerson());
            usrCertListPo.setCertApplyTime(certCheckDto.getSysDate());
            usrCertListPo.setUserId(userId);
            usrCertListPo.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);
            usrCertListPo.setCertChnlId(certCheckDto.getChnlId());
            usrCertListPo.setCertType(certType);
            usrCertListPo.setCertNo(certNo);
            usrCertListPo.setCertName(certName);
            usrCertListPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
            daoService.insert(usrCertListPo);
        }
        return certCheckDto;
    }

}
