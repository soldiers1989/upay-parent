package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.UserStatCheckDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrRegInfoPo;


/**
 * @author lihe 用户状态检查 1.检查用户是否为正常状态 2.检查用户是否在黑名单中
 */
public class UserStatCheckService extends AbstractDipperHandler<UserStatCheckDto> {
    @Resource
    IDaoService daoService;


    @Override
    public UserStatCheckDto execute(UserStatCheckDto userStatCheckDto, Message arg1) throws Exception {
        // 用户ID
        String userId = userStatCheckDto.getUserId();
        // 用户ID非空判定
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户ID");
        }
        // 获取用户注册信息表信息
        UsrRegInfoPo usrRegInfoPo = new UsrRegInfoPo();
        usrRegInfoPo.setUserId(userId);
        usrRegInfoPo = daoService.selectOne(usrRegInfoPo);
        // 用户信息非空判定
        if (null == usrRegInfoPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "用户" + userId);
        } else {
            String userStat = usrRegInfoPo.getUserStat(); // 获取用户状态
            // 9 用户注销抛错
            if (DataBaseConstants_USR.USR_STAT_LOGOFF.equals(userStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0017);
            }
            // 1 用户待激活
            if (DataBaseConstants_USR.USER_STAT_UNACTIVATE.equals(userStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0018);
            }
            // 如果该用户状态为黑名单 则抛错 0：正常 1：黑名单
            String blacklistFlag = usrRegInfoPo.getBlacklistFlag();
            if (blacklistFlag.equals(DataBaseConstants_USR.BLACK_LIST_FLAG_YES)) {
                ExInfo.throwDipperEx(AppCodeDict.BISUSR0014);
            }
        }
        return userStatCheckDto;
    }
}
