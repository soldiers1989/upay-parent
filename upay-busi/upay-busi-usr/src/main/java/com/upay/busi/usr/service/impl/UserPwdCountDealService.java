package com.upay.busi.usr.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserPwdCountDealDto;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrPwdListPo;
import com.upay.dao.po.usr.UsrPwdlockBookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 用户密码错误次数统计： 1.临时锁定解锁判定 ； 2.判定输入密码是否正确，正确则清空相应数据并更改用户状态；
 * 3.判定用户连续日累计错误次数是否大于系统参数的日连续累计错误次数，如果不大于则更新密码错误次数相应数据
 * 4.如果用户连续日累计错误次数大于系统参数表中的日连续累计错误次数，从系统参数表中取出日累计错误次数参数与用户改日密码输入错误总次数比较；
 * 如果前者小于大于后者，则更新状态为临时锁定并返还给用户临时锁定时间，反之则更新为长期锁定
 * 
 * @author lihe
 * 
 */
public class UserPwdCountDealService extends AbstractDipperHandler<UserPwdCountDealDto> {
    @Resource
    IDaoService daoService;


    @Override
    public UserPwdCountDealDto execute(UserPwdCountDealDto userPwdCountDealDto, Message arg1)
            throws Exception {
        // TODO Auto-generated method stub
        Date sysTime = SysInfoContext.getSysTime(); // 系统时间
        String userId = userPwdCountDealDto.getUserId(); // 用户 ID
        String pwdCheckFlag = userPwdCountDealDto.getPwdCheckFlag();// 错误密码统计标识
                                                                    // Y 需要统计
                                                                    // N不需要统计
        // 获取用户信息
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        // 用户信息非空判定
        if (null == usrRegInfoPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "用户信息");
        } else { // 如果密码目前为临时锁定状态，当临时锁定时间到期时，解锁用户密码
            expireUnlockTemporaryPwd(userId, sysTime, usrRegInfoPo);
        }

        if (!DataBaseConstants_USR.PWD_ERROR_COUNT_N.equals(pwdCheckFlag)) {
            // 获取用户密码每日统计管理表相关信息
            UsrPwdListPo usrPwdListPo = new UsrPwdListPo();
            usrPwdListPo.setUserId(userId);
            usrPwdListPo.setCountDate(SysInfoContext.getSysDate());
            usrPwdListPo = daoService.selectOne(usrPwdListPo);

            if (usrPwdListPo == null) {
                UsrPwdListPo usrPwdListAddPo = new UsrPwdListPo();
                usrPwdListAddPo.setUserId(userId);
                usrPwdListAddPo.setCountDate(SysInfoContext.getSysDate());
                // 当日登录密码连续错误次数，设置为1
                usrPwdListAddPo.setLogDayErr(1);
                // 登录密码总错误次数
                usrPwdListAddPo.setLogTotErr(1);
                // 当日交易密码连续错误次数，设为0
                usrPwdListAddPo.setTradeDayErr(0);
                // 交易密码总错误次数
                usrPwdListAddPo.setTradeTotErr(0);
                // 最后一次登录密码错日期
                usrPwdListAddPo.setLastLogpwdErrtime(sysTime);
                daoService.insert(usrPwdListAddPo);
            } else {
                // 如果不为空则更新记录
                int newLogDayErr = 0;// 当日登录密码连续错误次数
                int newLogTotErr = 0;// 登录密码连续累计错误总次数
                // 当日登录密码连续错误次数 + 1
                int logDayErr = usrPwdListPo.getLogDayErr();
                newLogDayErr = logDayErr + 1;
                // 获取登录密码错误总次数 + 1
                int logTotErr = usrPwdListPo.getLogTotErr();
                newLogTotErr = logTotErr + 1;
                // 查询系统参数表，获取到用户日登录密码连续错误次数锁定参数 3 次
                int logDayMaxErr =
                        new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.LOG_DAY_MAX_ERR).toString())
                            .intValue();
                int logTotMaxErr =
                        new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.LOG_TOT_MAX_ERR).toString())
                            .intValue();
                // 当每天连续密码错误次数小于3，并且每天总密码错误次数小于6 则 直接更新 密码错误统计表
                if ((newLogDayErr < logDayMaxErr) && (newLogTotErr < logTotMaxErr)) {
                    UsrPwdListPo usrPwdListUpPo = new UsrPwdListPo();
                    UsrPwdListPo usrPwdListWherePo = new UsrPwdListPo();
                    usrPwdListUpPo.setLogDayErr(newLogDayErr);
                    usrPwdListUpPo.setLogTotErr(newLogTotErr);
                    usrPwdListUpPo.setLastLogpwdErrtime(SysInfoContext.getSysTime());
                    usrPwdListWherePo.setUserId(userId);
                    usrPwdListWherePo.setCountDate(SysInfoContext.getSysDate());
                    daoService.update(usrPwdListUpPo, usrPwdListWherePo);
                }
                // 如果每天连续错误次数等于3，并且总错误次数小于6
                // 更新注册信息表用户状态为锁定；
                // 将一条临时锁定信息插入密码锁定表；
                if ((newLogDayErr == logDayMaxErr) && (newLogTotErr < logTotMaxErr)) {
                    // 更新注册信息表用户状态为锁定；
                    String userLockFlag = usrRegInfoPo.getUserLockFlag();
                    userLockFlag = DataBaseConstants_USR.USER_LOCK_FLAG_LOCK + userLockFlag.substring(1);
                    UsrRegInfoPo usrRegInfoUpdPo = new UsrRegInfoPo();
                    usrRegInfoUpdPo.setUserLockFlag(userLockFlag);
                    usrRegInfoUpdPo.setLastUpdateTime(sysTime);
                    UsrRegInfoPo usrRegInfoUpdWherePo = new UsrRegInfoPo();
                    usrRegInfoUpdWherePo.setUserId(userId);
                    daoService.update(usrRegInfoUpdPo, usrRegInfoUpdWherePo);
                    // 将一条临时锁定信息插入密码锁定表
                    UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
                    usrPwdlockBookPo.setLockWay(DataBaseConstants_USR.LOCK_WAY_DAY_ERR);
                    usrPwdlockBookPo.setLockMode(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK);
                    usrPwdlockBookPo.setLockTime(sysTime);
                    usrPwdlockBookPo.setUserId(userId);
                    // 获取锁定时效
                    int lockHour =
                            new BigDecimal(ParmsContext.getParmByKey(CmparmConstants.LOG_TEMP_HOUR)
                                .toString()).intValue();
                    usrPwdlockBookPo.setPartLockHour(lockHour);
                    usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
                    usrPwdlockBookPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
                    daoService.insert(usrPwdlockBookPo);
                    // 更新用户密码每日统计管理表
                    UsrPwdListPo usrPwdListUpdPo = new UsrPwdListPo();
                    UsrPwdListPo usrPwdListWherePo = new UsrPwdListPo();
                    usrPwdListUpdPo.setLogDayErr(0);
                    usrPwdListUpdPo.setLogTotErr(newLogTotErr);
                    usrPwdListUpdPo.setLastLogpwdErrtime(SysInfoContext.getSysTime());
                    usrPwdListWherePo.setUserId(userId);
                    usrPwdListWherePo.setCountDate(SysInfoContext.getSysDate());
                    daoService.update(usrPwdListUpdPo, usrPwdListWherePo);
                }
                // 当每日密码错误次数等于3，并且每日总错误次数等于6
                // 新增密码锁定登记簿为长期锁定
                // 更新密码统计登记簿每日密码连续错误为0，总错误次数为0
                if ((newLogDayErr == logDayMaxErr) && (newLogTotErr == logTotMaxErr)) {
                    // 新增密码锁定登记簿
                    UsrPwdlockBookPo usrPwdlockBookUpPo = new UsrPwdlockBookPo();
                    usrPwdlockBookUpPo.setUserId(userId);
                    usrPwdlockBookUpPo.setLockWay(DataBaseConstants_USR.LOCK_WAY_TOT_ERR);
                    usrPwdlockBookUpPo.setLockMode(DataBaseConstants_USR.LOCK_MODE_UNTEMPORARY_LOCK);
                    usrPwdlockBookUpPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
                    usrPwdlockBookUpPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
                    usrPwdlockBookUpPo.setLockTime(sysTime);
                    daoService.insert(usrPwdlockBookUpPo);
                    // 更新密码错误登记薄
                    UsrPwdListPo usrPwdListUpdPo = new UsrPwdListPo();
                    UsrPwdListPo usrPwdListWherePo = new UsrPwdListPo();
                    usrPwdListUpdPo.setLogDayErr(0);
                    usrPwdListUpdPo.setLogTotErr(0);
                    usrPwdListUpdPo.setLastLogpwdErrtime(SysInfoContext.getSysTime());
                    usrPwdListWherePo.setUserId(userId);
                    usrPwdListWherePo.setCountDate(SysInfoContext.getSysDate());
                    daoService.update(usrPwdListUpdPo, usrPwdListWherePo);
                    // 更新注册信息表用户状态为锁定；
                    String userLockFlag = usrRegInfoPo.getUserLockFlag();
                    userLockFlag = DataBaseConstants_USR.USER_LOCK_FLAG_LOCK + userLockFlag.substring(1);
                    UsrRegInfoPo usrRegInfoUpdPo = new UsrRegInfoPo();
                    usrRegInfoUpdPo.setUserLockFlag(userLockFlag);
                    usrRegInfoUpdPo.setLastUpdateTime(sysTime);
                    UsrRegInfoPo usrRegInfoUpdWherePo = new UsrRegInfoPo();
                    usrRegInfoUpdWherePo.setUserId(userId);
                    daoService.update(usrRegInfoUpdPo, usrRegInfoUpdWherePo);

                }
            }
        }
        return userPwdCountDealDto;
    }


    /**
     * 如果密码目前为临时锁定状态，当临时锁定时间到期时，解锁用户密码
     * 
     * @param userId
     * @param sysTime
     * @param usrRegInfo
     */

    private void expireUnlockTemporaryPwd(String userId, Date sysTime, UsrRegInfoPo usrRegInfo) { // 获取用户密码锁定登记簿相关信息
        String userLockFlag = usrRegInfo.getUserLockFlag();
        if (StringUtils.isBlank(userLockFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户锁定标识");
        }
        if (StringUtils.isBlank(StringUtils.substring(usrRegInfo.getUserLockFlag(), 0, 1))) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "登录密码锁定标识");
        }
        UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
        // 状态为锁定
        usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
        usrPwdlockBookPo.setUserId(userId);
        usrPwdlockBookPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
        usrPwdlockBookPo = daoService.selectOne(usrPwdlockBookPo);
        if (null != usrPwdlockBookPo) {
            // 获取锁定时效
            String lockMode = usrPwdlockBookPo.getLockMode();
            // 当为临时锁定时进行解锁
            if (lockMode.equals(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK)) {
                Date lockTime = usrPwdlockBookPo.getLockTime(); // 锁定时间
                int partLockHour = usrPwdlockBookPo.getPartLockHour();// 临时锁定持续时间(小时)
                // 获取临时锁定解锁时间
                lockTime = DateUtil.add(lockTime, Calendar.HOUR, partLockHour);
                if (sysTime.compareTo(lockTime) >= 0) { // 更新用户密码锁定登记簿状态-----解锁
                    usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_UNLOCK);
                    usrPwdlockBookPo.setUnlockTime(sysTime);
                    usrPwdlockBookPo.setUnlockOper("system");
                    usrPwdlockBookPo.setUnlockReason("到期自动解锁");
                    UsrPwdlockBookPo usrPwdlockBookPo2 = new UsrPwdlockBookPo();
                    usrPwdlockBookPo2.setUserId(usrPwdlockBookPo.getUserId());
                    usrPwdlockBookPo2.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
                    usrPwdlockBookPo2.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
                    usrPwdlockBookPo2.setLockWay(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK);
                    daoService.update(usrPwdlockBookPo, usrPwdlockBookPo2); // 更新用户注册信息表密码相关状态
                    userLockFlag = DataBaseConstants_USR.PWD_LOCK_NO.concat(userLockFlag.substring(1));
                    usrRegInfo.setUserLockFlag(userLockFlag);
                    usrRegInfo.setLastUpdateTime(sysTime);
                    UsrRegInfoPo usrPo = new UsrRegInfoPo();
                    usrPo.setUserId(usrPwdlockBookPo.getUserId());
                    daoService.update(usrRegInfo, usrPo);
                }
            }
        }
    }
}
