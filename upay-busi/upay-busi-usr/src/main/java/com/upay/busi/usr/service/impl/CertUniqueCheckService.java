package com.upay.busi.usr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.usr.service.dto.CertUniqueCheckDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrCertBlackListPo;
import com.upay.dao.po.usr.UsrCertListHisPo;
import com.upay.dao.po.usr.UsrCertListPo;


/**
 * 身份信息唯一性检查
 * 
 * @author liubing
 * 
 */
public class CertUniqueCheckService extends AbstractDipperHandler<CertUniqueCheckDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public CertUniqueCheckDto execute(CertUniqueCheckDto certUniqueCheckDto, Message msg) throws Exception {

        String certNo = certUniqueCheckDto.getCertNo();
        String certType = certUniqueCheckDto.getCertType();
        String certName = certUniqueCheckDto.getCertName();
        String userId = certUniqueCheckDto.getUserId();
        String approveType = certUniqueCheckDto.getApproveType();
        
        if (StringUtils.isBlank(certNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "CERT_NO");
        }
        certNo = StringUtils.upperCase(certNo);
        if (StringUtils.isBlank(certName)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "CERT_NAME");
        }
        if (StringUtils.isBlank(approveType)) {
        	 ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "认证类型");
        }
        if (StringUtils.isBlank(certType)) {
            certType = DataBaseConstants_USR.CERT_TYPE_CERT;
        }
        
        // if (DataBaseConstants_USR.CERT_TYPE_CERT.equals(certType)) {
        // if (!ValidateUtil.checkCert(certNo)) {
        // ExInfo.throwDipperEx(AppCodeDict.BISUSR0032, certNo);
        // }
        // }
        
        //检查绑定的身份证号是否是自然人黑名单
        
        UsrCertBlackListPo blackList=new UsrCertBlackListPo();
        blackList.setCertNo(certNo);
        blackList.setCertType(certType);
        blackList= daoService.selectOne(blackList);
        if(blackList!=null){
        	if(DataBaseConstants_USR.BLACK_LIST_FLAG_YES.equals(blackList.getBlacklistFlag())){
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0051);
			}
        }

        /** 用户实名认证申请信息 */
        UsrCertListPo usrCertListPo = new UsrCertListPo();
        usrCertListPo.setCertNo(certNo);
        usrCertListPo.setCertType(certType);
        usrCertListPo.setCertName(certName);
        usrCertListPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
        /** 对实名认证申请时间进行降序排序 */
        Order certApplyTimeDesc = Order.desc("certApplyTime");
        usrCertListPo.addOrder(certApplyTimeDesc);

        /** 实名认证申请时间降序后查询 */
        // 先判断原始表里是否有记录，若有就判断认证申请状态，若没有再判断历史表里的记录，判断其认证申请状态
        List<UsrCertListPo> listUsrCertListPo = daoService.selectList(usrCertListPo);
        if (listUsrCertListPo != null && listUsrCertListPo.size() > 0) {
            /** 取最近的一条记录 */
            usrCertListPo = listUsrCertListPo.get(0);
            /** 判断实名认证申请状态 */
            // 0:已通过 1:待审核 2:已拒绝
            if (userId.equals(usrCertListPo.getUserId())) {
                if (DataBaseConstants_USR.CERT_STAT_YES.equals(usrCertListPo.getCertStat())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0036, certNo);
                } else if (DataBaseConstants_USR.CERT_STAT_WAIT.equals(usrCertListPo.getCertStat())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0035);
                }
            } else {
                if (DataBaseConstants_USR.CERT_STAT_YES.equals(usrCertListPo.getCertStat())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0036, certNo);
                }
            }
        } else {
            /** 用户实名认证申请历史信息 */
            UsrCertListHisPo usrCertListHisPo = new UsrCertListHisPo();
            usrCertListHisPo.setCertNo(certNo);
            usrCertListHisPo.setCertType(certType);
            usrCertListHisPo.setCertName(certName);
            usrCertListHisPo.setApproveType(approveType);
            usrCertListHisPo.addOrder(certApplyTimeDesc);

            List<UsrCertListHisPo> listUsrCertListHisPo = daoService.selectList(usrCertListHisPo);
            if (listUsrCertListHisPo != null && listUsrCertListHisPo.size() > 0) {
                usrCertListHisPo = listUsrCertListHisPo.get(0);
                if (userId.equals(usrCertListHisPo.getUserId())) {
                    if (DataBaseConstants_USR.CERT_STAT_YES.equals(usrCertListHisPo.getCertStat())) {
                         ExInfo.throwDipperEx(AppCodeDict.BISUSR0034);
                    } else if (DataBaseConstants_USR.CERT_STAT_WAIT.equals(usrCertListHisPo.getCertStat())) {
                        ExInfo.throwDipperEx(AppCodeDict.BISUSR0035);
                    }
                } else {
                    if (DataBaseConstants_USR.CERT_STAT_YES.equals(usrCertListHisPo.getCertStat())) {
                         ExInfo.throwDipperEx(AppCodeDict.BISUSR0036, certNo);
                    }
                }
            } else {
                /** 将用户信息添加到用户基本信息表 */
                UsrBaseInfoPo usrBaseInfoSelPo = new UsrBaseInfoPo();
                usrBaseInfoSelPo.setCertNo(certNo);
                usrBaseInfoSelPo.setUserId(userId);
                usrBaseInfoSelPo = daoService.selectOne(usrBaseInfoSelPo);
                if (null != usrBaseInfoSelPo) {
                     ExInfo.throwDipperEx(AppCodeDict.BISUSR0036, certNo);
                }
            }
        }
        return certUniqueCheckDto;
    }
}
