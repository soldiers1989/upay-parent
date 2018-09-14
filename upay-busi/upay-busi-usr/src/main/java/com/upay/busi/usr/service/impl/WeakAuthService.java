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
import com.upay.busi.usr.service.dto.WeakAuthDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.ValidateUtil;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrCertListHisPo;
import com.upay.dao.po.usr.UsrCertListPo;
import com.upay.dao.po.usr.UsrRegInfoPo;

/*
*Model：用户实名认证
*Description：用户实名认证
*Author:罗李
*Finished：xxxx年xx月xx日
*/
public class WeakAuthService extends AbstractDipperHandler<WeakAuthDto> {

	private final static Logger log = LoggerFactory.getLogger(WeakAuthService.class);
	@Resource
	private IDaoService daoService;

	@Override
	public WeakAuthDto execute(WeakAuthDto weakAuthDto, Message arg1) throws Exception {

		String userId = weakAuthDto.getUserId();
		String chnlId = weakAuthDto.getChnlId();
		String certName = StringUtils.trim(weakAuthDto.getCertName());
		String certType = weakAuthDto.getCertType();
		String certNo = StringUtils.trim(StringUtils.upperCase(weakAuthDto.getCertNo()));
		String certWeakWay = weakAuthDto.getCertWeakWay();
		String approveType = weakAuthDto.getApproveType();
		
		if (StringUtils.isBlank(certType)) {
			certType = DataBaseConstants_USR.CERT_TYPE_CERT;
		}
		if (StringUtils.isBlank(certWeakWay)) {
			ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "CERT_WEAK_WAY");
		}

		UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
		usrRegInfoPo.setUserId(userId);
		usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
		usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
		if (null == usrRegInfoPo) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "用户");
		}
		String userCertLevel = usrRegInfoPo.getUserCertLevel();
		// 判断用户 是否已通过弱实名认证等级
		if (DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH.equals(userCertLevel)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0034);
		}

		// 判断用户是否有待审批的弱实名认证 先查原表，查完以后没有数据 再查历史表，再没有数据才抛异常
		UsrCertListPo usrCertListCheckPo = new UsrCertListPo();
		usrCertListCheckPo.setUserId(userId);
		usrCertListCheckPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
		usrCertListCheckPo.setCertStat(DataBaseConstants_USR.CERT_STAT_WAIT);
		usrCertListCheckPo = daoService.selectOne(usrCertListCheckPo);
		if (null != usrCertListCheckPo) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0035);
		} else {
			// 判断用户是否有待审批的弱实名认证
			UsrCertListHisPo usrCertListHisCheckPo = new UsrCertListHisPo();
			usrCertListHisCheckPo.setUserId(userId);
			usrCertListHisCheckPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
			usrCertListHisCheckPo.setCertStat(DataBaseConstants_USR.CERT_STAT_WAIT);
			usrCertListHisCheckPo = daoService.selectOne(usrCertListHisCheckPo);
			if (null != usrCertListHisCheckPo) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0035);
			}
		}

		if (DataBaseConstants_USR.CERT_WEAK_WAY_CERT.equals(certWeakWay)) {// 核查身份信息
			// 修改用户认证等级为弱实名认证
			UsrRegInfoPo usrRegInfoUpdPo = new UsrRegInfoPo();
			usrRegInfoUpdPo.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);
			usrRegInfoUpdPo.setLastUpdateTime(weakAuthDto.getSysTime());
			UsrRegInfoPo usrRegInfoWherePo = new UsrRegInfoPo();
			usrRegInfoWherePo.setUserId(userId);

			daoService.update(usrRegInfoUpdPo, usrRegInfoWherePo);
			// 将用户信息添加到用户基本信息表
			UsrBaseInfoPo usrBaseInfoUpdPo = new UsrBaseInfoPo();
			usrBaseInfoUpdPo.setCertType(certType);
			usrBaseInfoUpdPo.setCertNo(certNo);
			usrBaseInfoUpdPo.setCertName(certName);
			usrBaseInfoUpdPo.setEcifCustNo(weakAuthDto.getEcifCustNo());
			usrBaseInfoUpdPo.setLastUpdateTime(weakAuthDto.getSysTime());

			if (DataBaseConstants_USR.CERT_TYPE_CERT.equals(certType)) {
				usrBaseInfoUpdPo = getCertInfo(usrBaseInfoUpdPo);
			}
			UsrBaseInfoPo usrBaseInfoWherePo = new UsrBaseInfoPo();
			usrBaseInfoWherePo.setUserId(userId);

			daoService.update(usrBaseInfoUpdPo, usrBaseInfoWherePo);
			// 用户弱实名认证信息添加到用户实名认证申请表
			UsrCertListPo usrCertListPo = new UsrCertListPo();
//			usrCertListPo.setCertApplyTime(weakAuthDto.getSysTime());
			usrCertListPo.setCertApplyTime(new Date());
			usrCertListPo.setUserId(userId);
			usrCertListPo.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);
			usrCertListPo.setCertChnlId(chnlId);
			usrCertListPo.setCertType(certType);
			usrCertListPo.setCertNo(certNo);
			usrCertListPo.setCertName(certName);
			usrCertListPo.setCertWeakWay(certWeakWay);
			usrCertListPo.setCertStat(DataBaseConstants_USR.CERT_STAT_YES);
			usrCertListPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
			daoService.insert(usrCertListPo);
			
			weakAuthDto.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);
		} else if (DataBaseConstants_USR.CERT_WEAK_WAY_UPLOAD_CERT.equals(certWeakWay)) {// 上传身份证
			String certFront = weakAuthDto.getCertFront();
			String certBack = weakAuthDto.getCertBack();
			String certPerson = weakAuthDto.getCertPerson();
			if (StringUtils.isBlank(certFront)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "CERT_FRONT");
			}
			if (StringUtils.isBlank(certBack)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "CERT_BACK");
			}
			if (StringUtils.isBlank(certPerson)) {
				ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "CERT_PERSON");
			}
			// 用户弱实名认证申请信息添加到用户实名认证申请表
			UsrCertListPo usrCertListPo = new UsrCertListPo();
			usrCertListPo.setCertWeakWay(certWeakWay);
			usrCertListPo.setCertStat(DataBaseConstants_USR.CERT_STAT_WAIT);
			usrCertListPo.setCertFront(certFront);
			usrCertListPo.setCertBack(certBack);
			usrCertListPo.setCertPerson(certPerson);
			usrCertListPo.setCertApplyTime(weakAuthDto.getSysTime());
			usrCertListPo.setUserId(userId);
			usrCertListPo.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);
			usrCertListPo.setCertChnlId(chnlId);
			usrCertListPo.setCertType(certType);
			usrCertListPo.setCertNo(certNo);
			usrCertListPo.setCertName(certName);
			usrCertListPo.setApproveType(approveType);//为企业实名认证加的认证类型与注册信息表的注册类型一样
			daoService.insert(usrCertListPo);
			weakAuthDto.setUserCertLevel(DataBaseConstants_USR.USER_CERT_LEVEL_WEAK_AUTH);
		}

		return weakAuthDto;
	}

	/**
	 * 获取拆解身份证后的相关身份信息
	 * 
	 * @param usrBaseInfoUpdPo
	 * @return
	 */
	private UsrBaseInfoPo getCertInfo(UsrBaseInfoPo usrBaseInfoUpdPo) {
		String certNo = usrBaseInfoUpdPo.getCertNo();
		usrBaseInfoUpdPo.setBirthday(ValidateUtil.getBirthday(certNo));
		usrBaseInfoUpdPo.setSex(ValidateUtil.getSex(certNo));
		return usrBaseInfoUpdPo;
	}
}
