package com.upay.busi.usr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.busi.usr.service.dto.ResetMobileQueryDto;
import com.upay.commons.constants.DataBaseConstants_USR;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.usr.UsrChgMobileApplyPo;


/**
 * 重置用户手机号申请查询
 * 
 * @author liu
 * 
 */

public class ResetMobileQueryService extends AbstractDipperHandler<ResetMobileQueryDto> {

    private static final Logger LOG = LoggerFactory.getLogger(ResetMobileQueryService.class);

    @Resource
    private IDaoService daoService;


    @Override
    public ResetMobileQueryDto execute(ResetMobileQueryDto resetMobileQueryDto, Message msg) throws Exception {

        String userId = resetMobileQueryDto.getUserId();

        UsrChgMobileApplyPo usrChgMobileApplyPo = new UsrChgMobileApplyPo();

        // if (usrChgMobileApplyPo.getAuthApplyNo() == null) {
        // ExInfo.throwDipperEx(AppCodeDict.BISUSR0043, "手机号");
        // //未提交过[{}]变更申请，请确认。
        // }

        usrChgMobileApplyPo.setUserId(userId);
        usrChgMobileApplyPo.addOrder(Order.desc("applyTime"));

        String approveTime = null;
        String rejectReason = null;
        // 获取用户手机变更表中的最近的一条信息
        List<UsrChgMobileApplyPo> list = daoService.selectList(usrChgMobileApplyPo);
        if (null != list && list.size() > 0) {
            UsrChgMobileApplyPo usrChgMobileApplyOnePo = new UsrChgMobileApplyPo();
            usrChgMobileApplyOnePo = list.get(0);
            String applyTime = DateUtil.format(usrChgMobileApplyOnePo.getApplyTime(), "yyyy-MM-dd");
            String approveStat = usrChgMobileApplyOnePo.getApproveStat();
            String authApplyNo = usrChgMobileApplyOnePo.getAuthApplyNo();
            resetMobileQueryDto.setApplyTime(applyTime);
            resetMobileQueryDto.setApproveStat(approveStat);
            resetMobileQueryDto.setAuthApplyNo(authApplyNo);
            if (!DataBaseConstants_USR.APPROVE_STAT_YES.equals(approveStat)) {
                if (usrChgMobileApplyOnePo.getApproveTime() != null) {
                    approveTime = DateUtil.format(usrChgMobileApplyOnePo.getApproveTime(), "yyyy-MM-dd");
                    resetMobileQueryDto.setApproveTime(approveTime);
                }
            }
            if (DataBaseConstants_USR.APPROVE_STAT_NO.equals(approveStat)) {
                if (usrChgMobileApplyOnePo.getApproveTime() != null) {
                    approveTime = DateUtil.format(usrChgMobileApplyOnePo.getApproveTime(), "yyyy-MM-dd");
                    resetMobileQueryDto.setApproveTime(approveTime);
                }
                rejectReason = usrChgMobileApplyOnePo.getRejectReason();
                resetMobileQueryDto.setRejectReason(rejectReason);
            }
        }

        return resetMobileQueryDto;
    }

}
