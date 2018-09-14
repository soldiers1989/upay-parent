package com.upay.busi.usr.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UnlockLogPwdDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrPwdListPo;
import com.upay.dao.po.usr.UsrPwdlockBookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 对用户登陆密码进行解锁
 * 
 * @author liu
 * 
 */
public class UnlockLogPwdService extends AbstractDipperHandler<UnlockLogPwdDto> {

    @Resource
    private IDaoService daoService;


    @Override
    public UnlockLogPwdDto execute(UnlockLogPwdDto unlockLogPwdDto, Message msg) throws Exception {

        String userId = unlockLogPwdDto.getUserId();
        // 串在flow中，不会出现userId为空的状况
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0006, "用户ID");
        }
        Date sysTime = SysInfoContext.getSysTime(); // 系统时间
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (usrRegInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId); // 该用户不存在
        }

        String userLockFlag = usrRegInfoPo.getUserLockFlag();
        if (StringUtils.isBlank(userLockFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户锁定标识不能为空");
        }
        if (StringUtils.isBlank(StringUtils.substring(usrRegInfoPo.getUserLockFlag(), 0, 1))) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "登录密码锁定标识不能为空");
        }
        if (DataBaseConstants_USR.PWD_LOCK_YES.equals(userLockFlag.substring(0, 1))) {

            UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
            usrPwdlockBookPo.setUserId(userId);
            usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
            usrPwdlockBookPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
            usrPwdlockBookPo = daoService.selectOne(usrPwdlockBookPo);
            if (usrPwdlockBookPo != null) {// 锁定状态
                if (DataBaseConstants_USR.LOCK_WAY_ADMIN.equals(usrPwdlockBookPo.getLockWay())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0024); // 您已经被系统锁定,请联系客服
                } else {
                    usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_UNLOCK);
                    usrPwdlockBookPo.setUnlockTime(sysTime);
                    usrPwdlockBookPo.setUnlockOper("用户");
                    usrPwdlockBookPo.setUnlockReason("用户重置登录密码解锁");

                    UsrPwdlockBookPo usrPwdlockBookWhere = new UsrPwdlockBookPo();
                    usrPwdlockBookWhere.setUserId(userId);
                    usrPwdlockBookWhere.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
                    usrPwdlockBookWhere.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
                    daoService.update(usrPwdlockBookPo, usrPwdlockBookWhere);

                    UsrPwdListPo usrPwdListPoWhere = new UsrPwdListPo();
                    usrPwdListPoWhere.setCountDate(SysInfoContext.getSysDate());
                    usrPwdListPoWhere.setUserId(userId);
                    UsrPwdListPo usrPwdListPoUpd = new UsrPwdListPo();
                    usrPwdListPoUpd.setLogDayErr(0);// 当日登录密码连续错误次数
                    usrPwdListPoUpd.setLogTotErr(0);// 登录密码连续累计错误总次数
                    daoService.update(usrPwdListPoUpd, usrPwdListPoWhere);

                    userLockFlag = DataBaseConstants_USR.PWD_LOCK_NO.concat(userLockFlag.substring(1));
                    usrRegInfoPo.setUserLockFlag(userLockFlag);
                    usrRegInfoPo.setLastUpdateTime(sysTime);
                    UsrRegInfoPo usrRegInfoWhere = new UsrRegInfoPo();
                    usrRegInfoWhere.setUserId(userId);
                    daoService.update(usrRegInfoPo, usrRegInfoWhere);

                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0029, "锁定记录"); // {}不存在
            }

        }

        return unlockLogPwdDto;

    }

}
