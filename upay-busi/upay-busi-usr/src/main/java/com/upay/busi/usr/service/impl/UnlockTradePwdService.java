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
import com.upay.busi.usr.service.dto.UnlockTradePwdDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrPwdListPo;
import com.upay.dao.po.usr.UsrPwdlockBookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 对用户交易密码进行解锁
 * 
 * @author liyulong
 * 
 */
public class UnlockTradePwdService extends AbstractDipperHandler<UnlockTradePwdDto> {

    @Resource
    private IDaoService daoService;

    private static final Logger logger = LoggerFactory.getLogger(UnlockTradePwdService.class);


    @Override
    public UnlockTradePwdDto execute(UnlockTradePwdDto unlockTransPwdDto, Message msg) throws Exception {

        String userId = unlockTransPwdDto.getUserId();

        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户id");
        }

        Date sysTime = SysInfoContext.getSysTime(); // 系统时间
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo.setUserStat(DataBaseConstants_USR.USER_STAT_NORMAL);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        if (usrRegInfoPo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0005, userId);
        }

        String userLockFlag = usrRegInfoPo.getUserLockFlag();
        if (StringUtils.isBlank(userLockFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户锁定标识不能为空");
        }
        if (StringUtils.isBlank(StringUtils.substring(usrRegInfoPo.getUserLockFlag(), 1, 2))) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易密码锁定标识不能为空");
        }
        if (DataBaseConstants_USR.PWD_LOCK_YES.equals(userLockFlag.substring(1, 2))) {

            UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
            usrPwdlockBookPo.setUserId(userId);
            usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
            usrPwdlockBookPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_TRADE_PWD_LOCK);
            usrPwdlockBookPo = daoService.selectOne(usrPwdlockBookPo);
            if (usrPwdlockBookPo != null) {
                if (DataBaseConstants_USR.LOCK_WAY_ADMIN.equals(usrPwdlockBookPo.getLockWay())) {
                    ExInfo.throwDipperEx(AppCodeDict.BISUSR0024);
                } else {
                    usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_UNLOCK);
                    usrPwdlockBookPo.setUnlockTime(sysTime);
                    usrPwdlockBookPo.setUnlockOper("用户");
                    usrPwdlockBookPo.setUnlockReason("用户重置支付密码解锁");

                    UsrPwdlockBookPo usrPwdlockBookPo2 = new UsrPwdlockBookPo();
                    usrPwdlockBookPo2.setUserId(userId);
                    usrPwdlockBookPo2.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
                    usrPwdlockBookPo2.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_TRADE_PWD_LOCK);
                    daoService.update(usrPwdlockBookPo, usrPwdlockBookPo2);

                    UsrPwdListPo usrPwdListPoWhere = new UsrPwdListPo();
                    usrPwdListPoWhere.setCountDate(SysInfoContext.getSysDate());
                    usrPwdListPoWhere.setUserId(userId);
                    UsrPwdListPo usrPwdListPoUpd = new UsrPwdListPo();
                    usrPwdListPoUpd.setTradeDayErr(0);
                    usrPwdListPoUpd.setTradeTotErr(0);
                    daoService.update(usrPwdListPoUpd, usrPwdListPoWhere);
                    userLockFlag =
                            userLockFlag.substring(0, 1).concat(DataBaseConstants_USR.PWD_LOCK_NO)
                                .concat(userLockFlag.substring(2, userLockFlag.length()));
                    usrRegInfoPo.setUserLockFlag(userLockFlag);
                    usrRegInfoPo.setLastUpdateTime(sysTime);
                    UsrRegInfoPo usrRegInfoPo2 = new UsrRegInfoPo();
                    usrRegInfoPo2.setUserId(userId);
                    daoService.update(usrRegInfoPo, usrRegInfoPo2);

                }
            } else {
                ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "锁定记录");
            }

        }
        logger.debug("---------------------------------------end");
        return unlockTransPwdDto;

    }

}
