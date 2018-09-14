package com.upay.busi.usr.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import com.upay.commons.encryptor.UnionAPI;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.busi.usr.service.dto.UserTrPwdCheckDto;
import com.upay.commons.constants.CacheConstants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.AppPwdUtil;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrPwdListPo;
import com.upay.dao.po.usr.UsrPwdlockBookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;

/**
 * 用户交易密码校验： 1.先判定密码是否锁定 2.锁定则分别判定是临时锁定还是永久锁定 ，确定后并进行相应的操作
 * 3.未锁定：当密码错误时判定是长期锁定还是临时锁定，长期锁定则提示用户为长期锁定， 临时锁定则返还给用户过多长时间后才能继续输入密码的提示信息
 * 4.每次用户密码输入正确后则 清空错误次数
 * 
 * @author lihe
 * 
 */

public class UserTrPwdCheckService extends
		AbstractDipperHandler<UserTrPwdCheckDto> {
	private static final Logger logger = LoggerFactory
			.getLogger(UserTrPwdCheckService.class);
	@Resource
	private IDaoService daoService;
	@Resource
	private IDipperCached cacheClient;

	@Resource
	UnionAPI unionAPI;

	@Override
	public UserTrPwdCheckDto execute(UserTrPwdCheckDto userTrPwdCheckDto,
			Message message) throws Exception {

		String userId = userTrPwdCheckDto.getUserId();// 获取用户ID
		String tradePwd = userTrPwdCheckDto.getTradePwd();// 获取密码
		Date sysTime = SysInfoContext.getSysTime();// 获取系统时间
		Date sysDate = SysInfoContext.getSysDate();// 获取当前日期年月日
		String repeatCheckFlag = userTrPwdCheckDto.getRepeatCheckFlag();
		String newTradePwd = userTrPwdCheckDto.getNewTradePwd();
		// 密码非空判定
		if (StringUtils.isBlank(tradePwd)) {
			ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "交易密码");
		}
		// 用户ID非 空判定
		if (StringUtils.isBlank(userId)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户ID");
		}

		// 用户信息查询
		UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
		usrRegInfoPo.setUserId(userId);
		usrRegInfoPo.setUserStat(DataBaseConstants_USR.USR_STAT_NORMAL);
		usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
		if (null == usrRegInfoPo) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "用户信息");
		}

		UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
		// 将输入密码加密 以便与数据库密码 比较
		boolean encryptorFlag = false;
		GnrParmPo gnrParm=new GnrParmPo();
		gnrParm.setParmId(DataBaseConstants_USR.ENCRYPTOR_FLAG);
		gnrParm=daoService.selectOne(gnrParm);
		if(null!=gnrParm){
			encryptorFlag=Boolean.valueOf(gnrParm.getParmValue());
		}
		logger.debug("密码开关标识"+encryptorFlag);
		if(encryptorFlag){//密码控件开关
			unionAPI.getShortConnection(cacheClient,getEncryptorLocalBank());// 加密工具
			logger.info("web in =======" + tradePwd);
			String aesKey = userTrPwdCheckDto.getAesKey();
			logger.debug("----------------------------交易密码  前端传入:" + tradePwd);
			try {
				// 交易密码第一次加密码
				if (CommonConstants_GNR.CHNL_ID_WEB.equals(userTrPwdCheckDto
						.getChnlId())) {
					// 微通PC端解密
					tradePwd = AppPwdUtil.decryptToSM2(aesKey, tradePwd,
							userTrPwdCheckDto.getMiType());
				} else {
					// 微通App端解密
					tradePwd = AppPwdUtil.decrypt(cacheClient, tradePwd, aesKey);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "支付密码威通解密失败,请检查!");
			}
	
			logger.info("微通解密后的交易密码 =======" + tradePwd);
			if (StringUtils.isBlank(tradePwd)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "支付密码加密控件加密后为空!");
			}
			try {
				// 交易密码第二次核心加密机加密
				tradePwd = unionAPI.encryptWT(tradePwd);
				tradePwd = unionAPI.encrypt(tradePwd);
			} catch (Exception e) {
				e.printStackTrace();
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "支付密码核心解密失败,请检查!");
			}
			if (StringUtils.isBlank(tradePwd)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "支付密码核心加密后为空!");
			}
			logger.info("core convert =======" + tradePwd);
			if (StringUtils.isNotBlank(newTradePwd)) {
				// 新的交易密码第一次核心加密机加密
				logger.debug("----------------------------新的交易密码  前端传入:"
						+ newTradePwd);
				try {
					if (CommonConstants_GNR.CHNL_ID_WEB.equals(userTrPwdCheckDto
							.getChnlId())) {
						// 微通PC端解密
						newTradePwd = AppPwdUtil.decryptToSM2(aesKey, newTradePwd,
								userTrPwdCheckDto.getMiType());
					} else {
						// 微通App端解密
						newTradePwd = AppPwdUtil.decrypt(cacheClient, newTradePwd,
								aesKey);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "新支付密码威通解密失败,请检查!");
				}
				logger.debug("----------------------------新的交易密码  微通解密后:"
						+ newTradePwd);
				try {
					// 新的交易密码第二次核心加密机加密
					newTradePwd = unionAPI.encryptWT(newTradePwd);
					newTradePwd = unionAPI.encrypt(newTradePwd);
				} catch (Exception e) {
					e.printStackTrace();
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "新支付密码核心解密失败,请检查!");
				}
			}
		}

		String userLockFlag = usrRegInfoPo.getUserLockFlag();
		if (StringUtils.isBlank(userLockFlag)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户锁定标识");
		}
		if (StringUtils.isBlank(StringUtils.substring(
				usrRegInfoPo.getUserLockFlag(), 1, 2))) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易密码锁定标识");
		}
		// 当用户状态为未锁定 （正常）
		if (DataBaseConstants_USR.USER_LOCK_FLAG_NORMAL.equals(StringUtils
				.substring(usrRegInfoPo.getUserLockFlag(), 1, 2))) {
			// 密码不正确
			if (!tradePwd.equals(usrRegInfoPo.getTradePwd())) {
				errtradePwdDeal(userId, sysDate, userTrPwdCheckDto);
			} else {
				if (StringUtils.isNotBlank(repeatCheckFlag)) {
					// 新密码非空判定
					if (StringUtils.isBlank(newTradePwd)) {
						ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "新密码");
					}
					if (repeatCheckFlag
							.equals(DataBaseConstants_USR.TRADE_REPEAT_CHECK)) {
						if (newTradePwd.equals(usrRegInfoPo.getTradePwd())) {
							ExInfo.throwDipperEx(AppCodeDict.BISUSR0020);
						}
					}
				}
				// 密码正确则清空密码错误统计表
				UsrPwdListPo usrPwdListPo = new UsrPwdListPo();
				usrPwdListPo.setUserId(userId);
				usrPwdListPo.setCountDate(SysInfoContext.getSysDate());
				usrPwdListPo = daoService.selectOne(usrPwdListPo);
				UsrPwdListPo usrPwdListUpdPo = new UsrPwdListPo();
				UsrPwdListPo usrPwdListWherePo = new UsrPwdListPo();
				if (usrPwdListPo != null) {
					// 清零用户登录密码总错误次数和日错误次数
					usrPwdListUpdPo.setTradeDayErr(0);
					usrPwdListUpdPo.setTradeTotErr(0);
					usrPwdListWherePo.setUserId(userId);
					usrPwdListWherePo.setCountDate(SysInfoContext.getSysDate());
					daoService.update(usrPwdListUpdPo, usrPwdListWherePo);
				}
			}

		} else {
			// 当用户已经被锁定时
			usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);// 密码状态锁定
			// 1：登录密码锁定 2：交易密码锁定3：手势密码锁定
			usrPwdlockBookPo
					.setLockFunction(DataBaseConstants_USR.PWD_FLAG_TRADE_PWD);
			usrPwdlockBookPo.setUserId(userId);
			usrPwdlockBookPo = daoService.selectOne(usrPwdlockBookPo);
			if (null != usrPwdlockBookPo) {
				// 锁定时效 1：临时锁定 2：非临时锁定
				String lockMode = usrPwdlockBookPo.getLockMode();
				if (!usrPwdlockBookPo.getLockWay().equals(
						DataBaseConstants_USR.LOCK_WAY_TOT_ERR)) {
					if (lockMode
							.equals(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK)) {
						// 获取锁定时间
						Date lockTime = usrPwdlockBookPo.getLockTime();
						// 获取临时锁定持续时间(小时)
						int partLockHour = usrPwdlockBookPo.getPartLockHour();
						// 计算得到解锁时间
						lockTime = DateUtil.add(lockTime, Calendar.HOUR,
								partLockHour);
						// 当系统时间小于锁定时间 清除密码错误次数缓存
						if (sysTime.compareTo(lockTime) < 0) {
							// 错误密码统计标识 Y 需要统计 N不需要统计
							Map<String, Object> body = (Map<String, Object>) message
									.getTarget().getBodys();
							body.put("traPwdCheckFlag",
									DataBaseConstants_USR.TRA_ERROR_COUNT_N);
							long times = DateUtil.betweenTimes(lockTime,
									sysTime);
							String lastTimesDesc = new StringBuffer(
									String.valueOf(times / 3600)).append("小时")
									.append(times / 60 - times / 3600 * 60)
									.append("分钟").append(times % 60)
									.append("秒").toString();
							clearTradePwdTimes(userTrPwdCheckDto);
							ExInfo.throwDipperEx(AppCodeDict.BISUSR0026,
									lastTimesDesc);
						} else { // 系统时间大于锁定时间 当密码正确 则清空缓存
							if (tradePwd.equals(usrRegInfoPo.getTradePwd())) {
								clearTradePwdTimes(userTrPwdCheckDto);
							} else { //
								// 密码错误进行密码错误处理
								errtradePwdDeal(userId, sysDate,
										userTrPwdCheckDto);
							}
						}
					}
				} else {
					// 错误密码统计标识 Y 需要统计 N不需要统计
					Map<String, Object> body = (Map<String, Object>) message
							.getTarget().getBodys();
					body.put("traPwdCheckFlag",
							DataBaseConstants_USR.TRA_ERROR_COUNT_N);

					ExInfo.throwDipperEx(AppCodeDict.BISUSR0015);
				}
			}
		}
		// 清空密码缓存
		clearTradePwdTimes(userTrPwdCheckDto);
		return userTrPwdCheckDto;
	}

	/**
	 * 交易密码错误处理方式
	 * 
	 * @param userId
	 * @param sysDate
	 * @param pwdCheckDto
	 */
	private void errtradePwdDeal(String userId, Date sysDate,
			UserTrPwdCheckDto userTrPwdCheckDto) {
		int tradeDayErr = 1; // 当日支付密码连续错误次数
		UsrPwdListPo usrPwdListPo = new UsrPwdListPo();
		usrPwdListPo.setUserId(userId);
		usrPwdListPo.setCountDate(SysInfoContext.getSysDate());
		usrPwdListPo = daoService.selectOne(usrPwdListPo);
		// 从系统参数表中取出每天最大允许密码错误次数 系统表3次
		int tradeDayMaxErr = new BigDecimal(ParmsContext.getParmByKey(
				CmparmConstants.TRADE_DAY_MAX_ERR).toString()).intValue();
		int tradetMaxErr = new BigDecimal(ParmsContext.getParmByKey(
				CmparmConstants.TRADE_TOT_MAX_ERR).toString()).intValue();
		if (null == usrPwdListPo) {
			if ((tradeDayMaxErr - tradeDayErr) > 0) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0016, "交易",
						(tradeDayMaxErr - tradeDayErr));
			}
		} else { // 如果 当日错误次数 小于 3 并且 总错误次数小于6 则抛出还有几次机会
			if (tradeDayMaxErr - (usrPwdListPo.getTradeDayErr() + tradeDayErr) > 0
					&& (usrPwdListPo.getTradeTotErr() + tradeDayErr) < tradetMaxErr) {
				ExInfo.throwDipperEx(
						AppCodeDict.BISUSR0016,
						"交易",
						(tradeDayMaxErr - (usrPwdListPo.getTradeDayErr() + tradeDayErr)));
			}
			// 如果当日次数等于3 总错误次数小于 6 则抛出临时锁定
			if (tradeDayMaxErr - (usrPwdListPo.getTradeDayErr() + tradeDayErr) == 0
					&& (usrPwdListPo.getTradeTotErr() + tradeDayErr) < tradetMaxErr) {
				ExInfo.throwDipperEx(
						AppCodeDict.BISUSR0021,
						"交易",
						new BigDecimal(ParmsContext.getParmByKey(
								CmparmConstants.TRADE_TEMP_HOUR).toString())
								.intValue() + "小时");
			}
			// 如果当日次数等于3 总错误次数等于6 则抛出永久锁定
			if (tradeDayMaxErr - (usrPwdListPo.getTradeDayErr() + tradeDayErr) == 0
					&& (usrPwdListPo.getTradeTotErr() + tradeDayErr) == 6) {
				ExInfo.throwDipperEx(AppCodeDict.BISUSR0015);
			}
		}
	}

	/**
	 * 当为交易密码时，如果校验通过，判断本次会话用户是否有输入登录密码的错误次数，如果有则清空缓存
	 * 
	 * @param userPwdCheckDto
	 */
	private void clearTradePwdTimes(UserTrPwdCheckDto userTrPwdCheckDto) {
		String cacheKey = userTrPwdCheckDto.getVerifyKey(); // 验证码KEY
		if (StringUtils.isBlank(cacheKey)) {
			String userId = userTrPwdCheckDto.getUserId();
			cacheKey = userId;
			if (!cacheClient.keyExists(CacheConstants.TRA_PWD_ERROR_TIMES
					.concat(cacheKey))) {
				cacheClient.set(
						CacheConstants.TRA_PWD_ERROR_TIMES.concat(cacheKey), 1);
			}
		}

		if (cacheClient.keyExists(CacheConstants.TRA_PWD_ERROR_TIMES
				.concat(cacheKey))) {
			cacheClient.delete(CacheConstants.TRA_PWD_ERROR_TIMES
					.concat(cacheKey));
		}
	}
	
	private String getEncryptorLocalBank(){
		String encryptorLocalBank = cacheClient.get("ENCRYPTOR_LOCAL_BANK");
		if(StringUtils.isNotBlank(encryptorLocalBank)){
			return encryptorLocalBank;
		}
		GnrParmPo parm=new GnrParmPo();
		parm.setParmId("ENCRYPTOR_LOCAL_BANK");
		parm = daoService.selectOne(parm);
		if(parm!=null){
			encryptorLocalBank=parm.getParmValue();
			cacheClient.add("ENCRYPTOR_LOCAL_BANK", encryptorLocalBank);
		}
		return encryptorLocalBank;
	}
}
