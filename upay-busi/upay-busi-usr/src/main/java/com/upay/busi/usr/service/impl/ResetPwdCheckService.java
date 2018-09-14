package com.upay.busi.usr.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.usr.service.dto.ResetPwdCheckDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.usr.UsrResetTradepwdApplyPo;


/**
 * 用户重置支付密码验证
 * 
 * @author liyulong
 * 
 */
public class ResetPwdCheckService extends AbstractDipperHandler<ResetPwdCheckDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public ResetPwdCheckDto execute(ResetPwdCheckDto dto, Message arg1) throws Exception {

        String userId = dto.getUserId();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户ID");
        }

        UsrResetTradepwdApplyPo usrResetTradepwdApplyPo = new UsrResetTradepwdApplyPo();
        usrResetTradepwdApplyPo.setUserId(userId);
        usrResetTradepwdApplyPo.setApproveStat(DataBaseConstants_USR.APPROVE_STAT_WAIT);
        // 查询用户是否有待审批的重置密码申请
        usrResetTradepwdApplyPo = daoService.selectOne(usrResetTradepwdApplyPo);
        if (null != usrResetTradepwdApplyPo) {
            ExInfo.throwDipperEx(AppCodeDict.BISUSR0028);
        }
        return dto;
    }
}
