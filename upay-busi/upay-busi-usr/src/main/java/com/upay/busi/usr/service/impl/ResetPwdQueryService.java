package com.upay.busi.usr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.usr.service.dto.ResetPwdQueryDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.usr.UsrResetTradepwdApplyPo;


/**
 * 交易密码重置申请结果查询
 * 
 * @author liyulong
 * 
 */
public class ResetPwdQueryService extends AbstractDipperHandler<ResetPwdQueryDto> {
    @Resource
    private IDaoService daoService;


    @Override
    public ResetPwdQueryDto execute(ResetPwdQueryDto dto, Message msg) throws Exception {
        /** 取出用户ID */
        String userId = dto.getUserId();
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户ID");
        }
        /** 用户重置密码申请表信息 */
        UsrResetTradepwdApplyPo usrResetTradepwdApplyPo = new UsrResetTradepwdApplyPo();

        usrResetTradepwdApplyPo.setUserId(userId);
        /** 将申请时间按降序排序 */
        Order applyTimeDesc = Order.desc("applyTime");
        usrResetTradepwdApplyPo.addOrder(applyTimeDesc);
        /** 申请时间降序排序后的查询 */
        List<UsrResetTradepwdApplyPo> list = daoService.selectList(usrResetTradepwdApplyPo);

        if (null != list && list.size() > 0) {
            /** 取最近的一条记录 */
            usrResetTradepwdApplyPo = list.get(0);
            dto.setApplyTime(DateUtil.format(usrResetTradepwdApplyPo.getApplyTime(), "yyyy-MM-dd"));
            dto.setApproveStat(usrResetTradepwdApplyPo.getApproveStat());
            dto.setRejectReason(usrResetTradepwdApplyPo.getRejectReason());
        }
        return dto;
    }
}
