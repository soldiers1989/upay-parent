package com.upay.busi.usr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import ocx.AESWithJCE;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.usr.service.dto.ResetPwdRegiserDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.AppPwdUtil;
//import com.upay.commons.http.HttpGetClient;
import com.upay.dao.po.usr.UsrBaseInfoPo;
import com.upay.dao.po.usr.UsrResetTradepwdApplyPo;
import com.upay.commons.encryptor.UnionAPI;
/**
 * 支付密码重置登记
 * 
 * @author liyulong
 * 
 */
public class ResetPwdRegiserService extends
		AbstractDipperHandler<ResetPwdRegiserDto> {
	private static final Logger log = LoggerFactory
			.getLogger(ResetPwdRegiserService.class);
	@Resource
	private IDaoService daoService;
	@Resource
	IDipperCached idipperCached;
	@Resource
	UnionAPI unionAPI;

	@Override
	public ResetPwdRegiserDto execute(ResetPwdRegiserDto dto, Message msg)
			throws Exception {
		// 将输入密码加密 以便与数据库密码 比较
		boolean encryptorFlag = false;
		GnrParmPo gnrParm=new GnrParmPo();
		gnrParm.setParmId(DataBaseConstants_USR.ENCRYPTOR_FLAG);
		gnrParm=daoService.selectOne(gnrParm);
		if(null!=gnrParm){
			encryptorFlag=Boolean.valueOf(gnrParm.getParmValue());
		}
		/** 用户号 */
		String userId = dto.getUserId();
		/** 证件号码 */
		String certNo = StringUtils.upperCase(dto.getCertNo());
		/** 证件类型 */
		String certType = dto.getCertType();
		/** 证件姓名 */
		String certName = dto.getCertName();
		/** 证件正面照 */
		String certFront = dto.getCertFront();
		/** 证件反面照 */
		String certBack = dto.getCertBack();
		/** 用户手持证件照 */
		String certPerson = dto.getCertPerson();
		/** 交易密码 */
		String tradePwd = dto.getTradePwd();
		/** 渠道号 */
		String chnlId = dto.getChnlId();
		/** 获取当前时间 */
		Date nowDate = dto.getSysTime();

		// String aesKey = dto.getAesKey();
		// 验证用户基本信息是否正确
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(certType)
				|| StringUtils.isBlank(certNo) || StringUtils.isBlank(certName)) {
			ExInfo.throwDipperEx("用户个人信息查询参数", AppCodeDict.BISUSR0006);
		}
		if (StringUtils.isBlank(tradePwd)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "交易密码");
		}
		UsrBaseInfoPo usrBaseInfoPo = new UsrBaseInfoPo();
		usrBaseInfoPo.setUserId(userId);
		usrBaseInfoPo.setCertType(certType);
		usrBaseInfoPo.setCertNo(certNo);
		usrBaseInfoPo.setCertName(certName);
		usrBaseInfoPo = daoService.selectOne(usrBaseInfoPo);
		if (null == usrBaseInfoPo) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0027);
		}
		/** 用户重置交易密码申请信息 */
		UsrResetTradepwdApplyPo usrResetTradepwdApplyPo = new UsrResetTradepwdApplyPo();
		usrResetTradepwdApplyPo.setUserId(userId);
		/** 默认审批状态为0:待审批 */
		usrResetTradepwdApplyPo
				.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_WAIT);
		usrResetTradepwdApplyPo = daoService.selectOne(usrResetTradepwdApplyPo);
		if (usrResetTradepwdApplyPo != null) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0028);
		}
		if (StringUtils.isBlank(certFront)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "身份证正面照");
		}
		if (StringUtils.isBlank(certBack)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "身份证反面照");
		}
		if (StringUtils.isBlank(certPerson)) {
			ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "身份证本人手持照");
		}
		UsrResetTradepwdApplyPo usrResetTradepwdApplyInsertPo = new UsrResetTradepwdApplyPo();
		usrResetTradepwdApplyInsertPo.setUserId(userId);
		usrResetTradepwdApplyInsertPo.setCertNo(certNo);
		usrResetTradepwdApplyInsertPo.setCertName(certName);
		usrResetTradepwdApplyInsertPo.setCertType(certType);
		usrResetTradepwdApplyInsertPo.setCertFront(certFront);
		usrResetTradepwdApplyInsertPo.setCertBack(certBack);
		usrResetTradepwdApplyInsertPo.setCertPerson(certPerson);
		// tradePwd = SecurityUtil.getTransPwd(aesKey, tradePwd, userId,
		// chnlId);
		if(encryptorFlag){
			unionAPI.getShortConnection(idipperCached,getEncryptorLocalBank());// 加密工具
	
			String aesKey = dto.getAesKey();
			log.debug("前端传入的交易密码是：" + tradePwd);
			try {
				// 第一次加密
				if (CommonConstants_GNR.CHNL_ID_WEB.equals(dto.getChnlId())) {
					// 微通PC端解密
					tradePwd = AppPwdUtil.decryptToSM2(aesKey, tradePwd,
							dto.getMiType());
				} else {
					// 微通App端解密
					tradePwd = AppPwdUtil.decrypt(idipperCached, tradePwd, aesKey);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "支付密码威通解密失败,请检查!");
			}
	
			log.debug("微通解密后的交易密码是：" + tradePwd);
			try {
				// 第二次加密
				tradePwd = unionAPI.encryptWT(tradePwd);
				tradePwd = unionAPI.encrypt(tradePwd);
			} catch (Exception e) {
				e.printStackTrace();
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "支付密码核心解密失败,请检查!");
			}
		}
		usrResetTradepwdApplyInsertPo.setNewTradePwd(tradePwd);
		usrResetTradepwdApplyInsertPo.setApplyTime(nowDate);
		usrResetTradepwdApplyInsertPo.setApplyChnlId(chnlId);
		usrResetTradepwdApplyInsertPo
				.setWfStatus(DataBaseConstants_USR.WF_STATUS_1);
		usrResetTradepwdApplyInsertPo
				.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_WAIT);
		daoService.insert(usrResetTradepwdApplyInsertPo);
		return dto;
	}
	
	private String getEncryptorLocalBank(){
		String encryptorLocalBank = idipperCached.get("ENCRYPTOR_LOCAL_BANK");
		if(StringUtils.isNotBlank(encryptorLocalBank)){
			return encryptorLocalBank;
		}
		GnrParmPo parm=new GnrParmPo();
		parm.setParmId("ENCRYPTOR_LOCAL_BANK");
		parm = daoService.selectOne(parm);
		if(parm!=null){
			encryptorLocalBank=parm.getParmValue();
			idipperCached.add("ENCRYPTOR_LOCAL_BANK", encryptorLocalBank);
		}
		return encryptorLocalBank;
	}
}
