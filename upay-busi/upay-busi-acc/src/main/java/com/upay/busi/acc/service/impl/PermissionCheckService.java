package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.PermissionCheckDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.commons.util.ToolUtil;
import com.upay.dao.po.acc.AccPermissionPo;
import com.upay.dao.po.acc.AccStpBookPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 账户交易权限检查
 * 
 * @author: xuxin
 * @CreateDate:2016年8月23日
 * 
 */
public class PermissionCheckService extends AbstractDipperHandler<PermissionCheckDto> {

    private final static Logger log = LoggerFactory.getLogger(BindBookService.class);

    @Resource
    private IDaoService daoService;

    public PermissionCheckDto execute(PermissionCheckDto permissionCheckDto, Message msg) throws Exception {
        // 获取账户状态检查所需要字段
        log.info("<-------check  account permission-------> ");
        String userId = permissionCheckDto.getUserId();// 用户号
        String acctType = permissionCheckDto.getAcctType();// 账户类型
        String transCode = permissionCheckDto.getTransCode();// 交易代码
        BigDecimal transAmt = permissionCheckDto.getTransAmt();// 交易金额
        if (StringUtils.isBlank(acctType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0001, "账户类型");
        }
        if (DataBaseConstans_ACC.ACCT_TYPE_ELECT_ACCT.equals(acctType)) {
            // 根据账户类型，渠道权限，交易代码来查询账户交易权限表
            AccPermissionPo accPermissionPo = new AccPermissionPo();
            accPermissionPo.setAcctType(acctType);
            accPermissionPo.setTransCode(transCode);
            accPermissionPo.setPrmStat(DataBaseConstans_ACC.PRM_STAT_OPENING);
            accPermissionPo = daoService.selectOne(accPermissionPo);
            if (accPermissionPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0020, "该交易的账户交易权限表配置");
            }
            String suitEacctStat = ToolUtil.splitStat(accPermissionPo.getSuitVacctStat()); // 账户状态
            String suitStpStat = ToolUtil.splitStat(accPermissionPo.getSuitStpStat());    // 止付状态
            String suitFrzStat = ToolUtil.splitStat(accPermissionPo.getSuitFrzStat());   // 冻结状态
            String suitChnlId = ToolUtil.splitStat(accPermissionPo.getSuitChnlId());    // 渠道权限

            // 根据用户号及账号判断此用户状态
            AccVbookPo accVbookPo = new AccVbookPo();
            accVbookPo.setUserId(userId);
            accVbookPo = daoService.selectOne(accVbookPo);
            if (accVbookPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0017,"虚拟账户");
               // ExInfo.throwDipperEx(AppCodeDict.BISACC0029);
            }
            BigDecimal acctBal = accVbookPo.getAcctBal();// 电子账户余额
            BigDecimal frzBal = accVbookPo.getFrzBal();// 冻结金额
            BigDecimal stpAmt = BigDecimal.valueOf(0);// 止付金额
            BigDecimal remainAcctBal =
                    MoneyUtil.subtract(MoneyUtil.subtract(acctBal, frzBal, null), stpAmt, null);// 可用电子账户余额
            String eacctStat = accVbookPo.getVacctStat();
            String stpStat = accVbookPo.getStopStat();
            String frzStat = accVbookPo.getFrzStat();

            if (!suitChnlId.contains(permissionCheckDto.getChnlId())) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0028, "渠道");
            }
            if (!suitEacctStat.contains(eacctStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0027, "账户状态");
            }
            if (!suitStpStat.contains(stpStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0027, "账户止付状态");
            }
            if (!suitFrzStat.contains(frzStat)) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0027, "账户冻结状态");
            }

            /* 账户止付状态,部分止付-3 */
            if (DataBaseConstans_ACC.STP_TYPE_PART_FROZEN.equals(stpStat)) {
                AccStpBookPo accStpBookPo = new AccStpBookPo();
                accStpBookPo.setUserId(permissionCheckDto.getUserId());
                accStpBookPo.setAcctType(acctType);
                accStpBookPo.setStpStat(DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_NO);
                accStpBookPo.setStpMode(DataBaseConstans_ACC.STP_MODE_SNAP);
                accStpBookPo = daoService.selectOne(accStpBookPo);
                if (accStpBookPo != null) {
                    /* 止付方式为1：临时止付 */
                    Integer partStpTime = accStpBookPo.getPartStpTime();
                    Date stpTime = (Date) accStpBookPo.getStpTime();
                    if (DateUtil.add(stpTime, Calendar.HOUR, Integer.parseInt(partStpTime.toString()))
                        .compareTo(permissionCheckDto.getSysTime()) <= 0) {
                        accStpBookPo.setStpStat(DataBaseConstans_ACC.STP_TYPE_NOMAL);
                        /** 虚拟账户信息表 止付类型 0-正常 */
                        accStpBookPo.setPayAmt(stpAmt);
                        accStpBookPo.setUnstpTime(permissionCheckDto.getSysTime());
                        /** 虚拟账户信息表 解付原因:临时止付时间到期 */
                        accStpBookPo.setUnstpReason(DataBaseConstans_ACC.STP_UNSTP_REASON);
                        AccStpBookPo whereAccStpBookPo = new AccStpBookPo();
                        whereAccStpBookPo.setId(accStpBookPo.getId());
                        daoService.update(accStpBookPo,whereAccStpBookPo);/* 更新电子账户登记簿止付状态 */
                        accVbookPo.setStopStat(DataBaseConstans_ACC.STP_TYPE_NOMAL);
                        accVbookPo.setLastChgTime(permissionCheckDto.getSysTime());


                        AccVbookPo whereAccVbookPo = new AccVbookPo();
                        whereAccVbookPo.setId(accVbookPo.getId());
                        daoService.update(accVbookPo,whereAccVbookPo);
                    }
                }
            }
            if (DataBaseConstans_ACC.AMT_FLAG_YES.equals(permissionCheckDto.getAmtFlag())) {
                /* 如果是金融交易，能得到借贷记标识 */
                String dcFlag = permissionCheckDto.getDcFlag();
                if (!StringUtils.isBlank(dcFlag)) {
                    if (null == transAmt || BigDecimal.ZERO.compareTo(transAmt) >= 0) {
                        ExInfo.throwDipperEx(AppCodeDict.BISACC0026, "交易金额为空或者输入金额为0");
                    }
                    /** 借贷标识,借-1 */
                    if (DataBaseConstans_ACC.DC_FLAG_DEBIT.equals(dcFlag)) {
                        if (remainAcctBal.compareTo(BigDecimal.ZERO) < 0) {
                            ExInfo.throwDipperEx(AppCodeDict.BISACC0026, "可用电子账户余额处于负债状态");
                        }
                        if ((MoneyUtil.subtract(remainAcctBal, transAmt, null)).compareTo(BigDecimal.ZERO) < 0) {
                            ExInfo.throwDipperEx(AppCodeDict.BISACC0026, "可用电子账户余额小于交易金额");
                        }
                    }
                }
            }
        }
        return permissionCheckDto;
    }
}
