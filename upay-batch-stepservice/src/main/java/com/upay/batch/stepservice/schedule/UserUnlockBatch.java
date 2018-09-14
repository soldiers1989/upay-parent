package com.upay.batch.stepservice.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.util.DateUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.usr.UsrPwdlockBookPo;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * 用户登录密码临时锁定解锁
 * 
 * @author liyulong
 * 
 */
public class UserUnlockBatch extends AbstractStepExecutor<Object, UsrPwdlockBookPo> {
    @Resource
    private IDaoService daoService;


    // @Override
    // public List<Object> getObjectList(BatchParams batchParams) throws
    // BatchException {
    // // TODO Auto-generated method stub
    // return super.getObjectList(batchParams);
    // }
    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
        UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
        // 查询锁定条数
        usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
        usrPwdlockBookPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
        usrPwdlockBookPo.setLockWay(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK);
        return daoService.selectList(usrPwdlockBookPo).size();
    }


    @Override
    public List<UsrPwdlockBookPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        UsrPwdlockBookPo usrPwdlockBookPo = new UsrPwdlockBookPo();
        // 查询锁定记录
        usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
        usrPwdlockBookPo.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
        usrPwdlockBookPo.setLockWay(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK);
        List<UsrPwdlockBookPo> selectList = daoService.selectList(usrPwdlockBookPo, offset, pageSize);
        return selectList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, UsrPwdlockBookPo usrPwdlockBookPo, Object object)
            throws BatchException {
        // 如果锁定时间过去，解锁
        Date sysTime = SysInfoContext.getSysTime(); // 系统时间
        if (null != usrPwdlockBookPo && StringUtils.isNotBlank(usrPwdlockBookPo.getUserId())) {
            String userId = usrPwdlockBookPo.getUserId();
            UsrRegInfoPo usrRegInfo = new UsrRegInfoPo();
            usrRegInfo.setUserId(userId);
            usrRegInfo = daoService.selectOne(usrRegInfo);
            if (usrRegInfo != null) {
                String userLockFlag = usrRegInfo.getUserLockFlag();
                if (StringUtils.isBlank(userLockFlag)) {
                    throw new BatchException("用户锁定标识不能为空");
                }
                if (StringUtils.isBlank(StringUtils.substring(usrRegInfo.getUserLockFlag(), 0, 1))) {
                    throw new BatchException("登录密码锁定标识不能为空 ");
                }
                // 获取锁定时效
                String lockMode = usrPwdlockBookPo.getLockMode();
                // 当为临时锁定时进行解锁
                if (lockMode.equals(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK)) {
                    Date lockTime = usrPwdlockBookPo.getLockTime(); // 锁定时间
                    int partLockHour = usrPwdlockBookPo.getPartLockHour();// 临时锁定持续时间(小时)
                    // 获取临时锁定解锁时间
                    lockTime = DateUtil.add(lockTime, Calendar.HOUR, partLockHour);
                    if (sysTime.compareTo(lockTime) >= 0) { // 更新用户密码锁定登记簿状态-----解锁
                        logger.debug("--------------------------------开始解锁");
                        usrPwdlockBookPo.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_UNLOCK);
                        usrPwdlockBookPo.setUnlockTime(sysTime);
                        usrPwdlockBookPo.setUnlockOper("system");
                        usrPwdlockBookPo.setUnlockReason("到期自动解锁");
                        UsrPwdlockBookPo usrPwdlockBookPo2 = new UsrPwdlockBookPo();
                        usrPwdlockBookPo2.setUserId(usrPwdlockBookPo.getUserId());
                        usrPwdlockBookPo2.setLockFlag(DataBaseConstants_USR.LOCK_FLAG_LOCK);
                        usrPwdlockBookPo2.setLockFunction(DataBaseConstants_USR.LOCK_FUNCTION_LOGIN_PWD_LOCK);
                        usrPwdlockBookPo2.setLockWay(DataBaseConstants_USR.LOCK_MODE_TEMPORARY_LOCK);
                        logger.debug("--------------------------------开始更新密码锁定登记簿");
                        daoService.update(usrPwdlockBookPo, usrPwdlockBookPo2); // 更新用户注册信息表密码相关状态
                        logger.debug("--------------------------------完成更新密码锁定登记簿");
                        userLockFlag = DataBaseConstants_USR.PWD_LOCK_NO.concat(userLockFlag.substring(1));
                        usrRegInfo.setUserLockFlag(userLockFlag);
                        usrRegInfo.setLastUpdateTime(sysTime);
                        UsrRegInfoPo usrPo = new UsrRegInfoPo();
                        usrPo.setUserId(usrPwdlockBookPo.getUserId());
                        logger.debug("--------------------------------开始更新用户注册信息表");
                        daoService.update(usrRegInfo, usrPo);
                        logger.debug("--------------------------------完成更新用户注册信息表");
                    } else {
                        logger.debug("锁定时效还未过");
                    }
                }
            }
        } else {
            logger.debug("暂无锁定记录");
        }
    }
}
