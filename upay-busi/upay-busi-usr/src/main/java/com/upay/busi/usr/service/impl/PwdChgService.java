package com.upay.busi.usr.service.impl;

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
import com.upay.busi.usr.service.dto.PwdChgDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.AppPwdUtil;
//import com.upay.commons.http.HttpGetClient;
import com.upay.dao.po.usr.UsrRegInfoPo;
import com.upay.commons.encryptor.UnionAPI;
/**
 * 用户密码修改
 * 
 * @author liyulong
 * 
 */
public class PwdChgService extends AbstractDipperHandler<PwdChgDto> {

	@Resource
	private IDaoService daoService;
	@Resource
	IDipperCached idipperCached;
	@Resource
	UnionAPI unionAPI;
	private static final Logger logger = LoggerFactory
			.getLogger(PwdChgService.class);

	@Override
	public PwdChgDto execute(PwdChgDto dto, Message msg) throws Exception {
		// 将输入密码加密 以便与数据库密码 比较
		boolean encryptorFlag = false;
		GnrParmPo gnrParm=new GnrParmPo();
		gnrParm.setParmId(DataBaseConstants_USR.ENCRYPTOR_FLAG);
		gnrParm=daoService.selectOne(gnrParm);
		if(null!=gnrParm){
			encryptorFlag=Boolean.valueOf(gnrParm.getParmValue());
		}
				
		String aesKey = dto.getAesKey();
		String userId = dto.getUserId();// 用户ID
		String pwdFlag = dto.getPwdFlag();// 密码类型
		String chnlId = dto.getChnlId();
		if (StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户id");
		}
		if (StringUtils.isBlank(pwdFlag)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "密码类型");
		}
		if (StringUtils.isBlank(chnlId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "渠道id");
		}
		UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
		UsrRegInfoPo usrRegInfoUpdPo = new UsrRegInfoPo();
		UsrRegInfoPo usrRegInfoWherePo = new UsrRegInfoPo();
		// TODO 密码控件下来之后 做密码处理
		String newLoginPwd = dto.getNewLoginPwd();// 新登录密码
		String newTradePwd = dto.getNewTradePwd();// 新支付密码
		
		//密码控件开关 
		if(encryptorFlag){
			unionAPI.getShortConnection(idipperCached,getEncryptorLocalBank());// 加密工具
		}
		if (DataBaseConstants_USR.PWD_FLAG_LOGIN_PWD.equals(pwdFlag)) {
			if (StringUtils.isBlank(newLoginPwd)) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "新登录密码");
			}
			usrRegInfoPo.setUserId(userId);
			usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
			usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
			if (usrRegInfoPo == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId);
			}
			logger.debug("前端传入新的登录密码：" + newLoginPwd);
			if(encryptorFlag){
				try {
					// 第一次加密
					if (CommonConstants_GNR.CHNL_ID_WEB.equals(dto.getChnlId())) {
						// 微通PC端解密
						newLoginPwd = AppPwdUtil.decryptToSM2(aesKey, newLoginPwd,
								dto.getMiType());
					} else {
						// 微通App端解密
						newLoginPwd = AppPwdUtil.decrypt(idipperCached,
								newLoginPwd, aesKey);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
							"新登录密码威通解密失败,请检查!");
				}
	
				logger.debug("微通解密后新的登录密码：" + newLoginPwd);
				if (StringUtils.isBlank(newLoginPwd)) {
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "微通解密后的登录密码为空!");
				}
				try {
					// 第二次加密
					newLoginPwd = unionAPI.encryptWT(newLoginPwd);
					newLoginPwd = unionAPI.encrypt(newLoginPwd);
				} catch (Exception e) {
					e.printStackTrace();
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
							"新登录密码核心解密失败,请检查!");
				}
				if (StringUtils.isBlank(newLoginPwd)) {
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "新登录密码核心加密后为空!");
				}
			}

			if (newLoginPwd.equals(usrRegInfoPo.getTradePwd())) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0046);
			}

			// Date date = new Date();
			usrRegInfoUpdPo.setLastUpdateTime(dto.getSysTime());
			usrRegInfoUpdPo.setLoginPwd(newLoginPwd);
			usrRegInfoUpdPo.setLastLogpwdModifytime(dto.getSysTime());
			usrRegInfoWherePo.setUserId(userId);
			daoService.update(usrRegInfoUpdPo, usrRegInfoWherePo);

		} else if (DataBaseConstants_USR.PWD_FLAG_TRADE_PWD.equals(pwdFlag)) {
			if (StringUtils.isBlank(newTradePwd)) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "新支付密码");
			}
			usrRegInfoPo.setUserId(userId);
			usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
			usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
			if (usrRegInfoPo == null) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId);
			}
			logger.debug("前端传入新的交易密码：" + newLoginPwd);
			if(encryptorFlag){
				try {
					// 第一次加密
					if (CommonConstants_GNR.CHNL_ID_WEB.equals(dto.getChnlId())) {
						// 微通PC端解密
						newTradePwd = AppPwdUtil.decryptToSM2(aesKey, newTradePwd,
								dto.getMiType());
					} else {
						// 微通App端解密
						newTradePwd = AppPwdUtil.decrypt(idipperCached,
								newTradePwd, aesKey);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
							"新支付密码威通解密失败,请检查!");
				}
				logger.debug("微通解密后的交易密码：" + newLoginPwd);
				if (StringUtils.isBlank(newTradePwd)) {
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "微通解密后的交易密码为空!");
				}
				try {
					// 第二次加密
					newTradePwd = unionAPI.encryptWT(newTradePwd);
					newTradePwd = unionAPI.encrypt(newTradePwd);
				} catch (Exception e) {
					e.printStackTrace();
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "新支付密码核心解密失败,请检查!");
				}
				if (StringUtils.isBlank(newTradePwd)) {
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "新支付密码核心加密后为空!");
				}
			}
			if (newTradePwd.equals(usrRegInfoPo.getLoginPwd())) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0046);
			}

			// Date date = new Date();
			usrRegInfoUpdPo.setLastUpdateTime(dto.getSysTime());
			usrRegInfoUpdPo.setTradePwd(newTradePwd);
			usrRegInfoUpdPo.setLastTradepwdModifytime(dto.getSysTime());
			usrRegInfoWherePo.setUserId(userId);
			daoService.update(usrRegInfoUpdPo, usrRegInfoWherePo);
		}

		logger.debug("-----------------------------end");
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
