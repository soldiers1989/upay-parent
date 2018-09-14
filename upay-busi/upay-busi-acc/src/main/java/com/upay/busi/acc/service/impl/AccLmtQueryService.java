package com.upay.busi.acc.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.acc.service.dto.AccLmtQueryDto;
import com.upay.commons.constants.CommunicationConstants;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.po.acc.AccLmtCountPo;
import com.upay.dao.po.acc.AccSysLmtBookPo;
import com.upay.dao.po.acc.AccVbookPo;


/**
 * 账户限额查询
 * 
 * @author liyulong
 * 
 */
public class AccLmtQueryService extends AbstractDipperHandler<AccLmtQueryDto> {

    private static final String DAYSUMAMTLMTREMAIN = "daySumAmtLimitRemain";
    private static final String MONSUMAMTLMTREMAIN = "monSumAmtLimitRemain";
    private static final String YEARSUMAMTLMTREMAIN = "yearSumAmtLimitRemain";
    private static final String DAYSUMTIMESLMTREMAIN = "daySumTimesLimitRemain";
    private static final String MONSUMTIMESLMTREMAIN = "monSumTimesLimitRemain";
    private static final String YEARSUMTIMESLMTREMAIN = "yearSumTimesLimitRemain";
    @Resource
    private IDaoService daoService;


    @Override
    public AccLmtQueryDto execute(AccLmtQueryDto accLmtQueryDto, Message message) throws Exception {

        Date sysDate = accLmtQueryDto.getSysDate();
        String acctType = accLmtQueryDto.getAcctType();
        String chnlId = accLmtQueryDto.getChnlId();
        String transCode = accLmtQueryDto.getPendTransCode();
        String dcFlag = null;
        if (!DataBaseConstans_ACC.AMT_FLAG_YES.equals(accLmtQueryDto.getAmtFlag())) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0020, transCode);
        }
        dcFlag = accLmtQueryDto.getDcFlag();
        String userCertLevel = accLmtQueryDto.getUserCertLevel();
        String vAcctNo = accLmtQueryDto.getvAcctNo();
        String lmtType = accLmtQueryDto.getLmtType();
        String userId = accLmtQueryDto.getUserId();
        if (StringUtils.isBlank(userCertLevel)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "USER_CERT_LEVEL");
        }
        if (StringUtils.isBlank(userId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "用户Id");
        }
        if (StringUtils.isBlank(acctType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "ACCT_TYPE");
        } else {
            if (DataBaseConstans_ACC.ACCT_TYPE_VIRTUAL_ACCT.equals(acctType)) {
                if (StringUtils.isBlank(vAcctNo)) {
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "V_ACCT_NO");
                }
            }
        }
        if (StringUtils.isBlank(lmtType)) {
            lmtType = DataBaseConstans_ACC.LMP_TPYE_NORMAL;
        }

        // 根据条件获取系统限额
        // 查询借贷方向的系统限额
        boolean tcSysLmtFlag = false;
        boolean dcSysLmtFlag = false;
        AccSysLmtBookPo accSysLmtBookPo = new AccSysLmtBookPo();
        accSysLmtBookPo.setDcFlag(dcFlag);
        accSysLmtBookPo.setChnlId(chnlId);
        accSysLmtBookPo.setUserLevel(userCertLevel);
        accSysLmtBookPo.setLmtType(lmtType);
        accSysLmtBookPo.setAcctType(acctType);
        accSysLmtBookPo.setLmtStat(DataBaseConstans_ACC.LMT_STAT_ENABLE);
        accSysLmtBookPo = daoService.selectOne(accSysLmtBookPo);
        if (accSysLmtBookPo != null) {
            dcSysLmtFlag = true;
        }
        // 查询交易代码方向的系统限额
        AccSysLmtBookPo accSysLmtBookPoTc = new AccSysLmtBookPo();
        accSysLmtBookPoTc.setTransCode(transCode);
        accSysLmtBookPoTc.setChnlId(chnlId);
        accSysLmtBookPoTc.setUserLevel(userCertLevel);
        accSysLmtBookPoTc.setLmtType(lmtType);
        accSysLmtBookPoTc.setAcctType(acctType);
        accSysLmtBookPoTc.setLmtStat(DataBaseConstans_ACC.LMT_STAT_ENABLE);
        accSysLmtBookPoTc = daoService.selectOne(accSysLmtBookPoTc);
        if (accSysLmtBookPoTc != null) {
            tcSysLmtFlag = true;
        }
        if (!dcSysLmtFlag && !tcSysLmtFlag) {
            ExInfo.throwDipperEx(AppCodeDict.BISACC0021);
        } else if (tcSysLmtFlag) {
            accLmtQueryDto.setSingleAmtLimit(accSysLmtBookPoTc.getSingleAmtLimit()); // 单笔限额
            accLmtQueryDto.setDaySumAmtLimit(accSysLmtBookPoTc.getDaySumAmtLimit()); // 日累计系统限额
            accLmtQueryDto.setMonSumAmtLimit(accSysLmtBookPoTc.getMonSumAmtLimit()); // 月累计系统限额
            accLmtQueryDto.setYearSumAmtLimit(accSysLmtBookPoTc.getYearSumAmtLimit()); // 年累计系统限额
            accLmtQueryDto.setDaySumTimesLimit(accSysLmtBookPoTc.getDaySumTimesLimit()); // 日累计系统限额次数
            accLmtQueryDto.setMonSumTimesLimit(accSysLmtBookPoTc.getMonSumTimesLimit()); // 月累计系统限额次数
            accLmtQueryDto.setYearSumTimesLimit(accSysLmtBookPoTc.getYearSumTimesLimit()); // 年累计系统限额次数
        } else {
            accLmtQueryDto.setSingleAmtLimit(accSysLmtBookPo.getSingleAmtLimit()); // 单笔限额
            accLmtQueryDto.setDaySumAmtLimit(accSysLmtBookPo.getDaySumAmtLimit()); // 日累计系统限额
            accLmtQueryDto.setMonSumAmtLimit(accSysLmtBookPo.getMonSumAmtLimit()); // 月累计系统限额
            accLmtQueryDto.setYearSumAmtLimit(accSysLmtBookPo.getYearSumAmtLimit()); // 年累计系统限额
            accLmtQueryDto.setDaySumTimesLimit(accSysLmtBookPo.getDaySumTimesLimit()); // 日累计系统限额次数
            accLmtQueryDto.setMonSumTimesLimit(accSysLmtBookPo.getMonSumTimesLimit()); // 月累计系统限额次数
            accLmtQueryDto.setYearSumTimesLimit(accSysLmtBookPo.getYearSumTimesLimit()); // 年累计系统限额次数
        }

        // 获取账户当前可用余额，根据账户类型判断
        BigDecimal avlBal = BigDecimal.ZERO;
        if (DataBaseConstans_ACC.ACCT_TYPE_VIRTUAL_ACCT.equals(acctType)) {
            AccVbookPo accVbookPo = new AccVbookPo();
            accVbookPo.setVacctNo(vAcctNo);
            accVbookPo.setVacctStat(DataBaseConstans_ACC.ACCT_STAT_NORMAL);
            accVbookPo = daoService.selectOne(accVbookPo);
            if (accVbookPo == null) {
                ExInfo.throwDipperEx(AppCodeDict.BISACC0012, vAcctNo);
            }
            BigDecimal acctBal = accVbookPo.getAcctBal();
            BigDecimal frzBal = accVbookPo.getFrzBal();
            String stopStat = accVbookPo.getStopStat();
            avlBal = MoneyUtil.subtract(acctBal, frzBal, null);
            if (DataBaseConstans_ACC.ACC_V_BOOK_STOPSTAT_PART.equals(stopStat)) {
                // avlBal = MoneyUtil.subtract(avlBal, accVbookPo.getStpAmt(),
                // null);
            }
        }
        accLmtQueryDto.setAvlBal(avlBal);

        // 获取借贷方向的剩余限额
        Map<String, Object> mapDc = null;
        if (dcSysLmtFlag) {
            mapDc = getRemainLmt(accSysLmtBookPo, userId, sysDate);
        }
        // 获取交易代码的剩余限额
        Map<String, Object> mapTc = null;
        if (tcSysLmtFlag) {
            mapTc = getRemainLmt(accSysLmtBookPoTc, userId, sysDate);
        }
        accLmtQueryDto = getMinLmt(accLmtQueryDto, mapDc, mapTc);

        return accLmtQueryDto;
    }


    /**
     * 获取此账户借贷方向或交易类型的累计剩余限额
     * 
     * @param accSysLmtBookPo
     * @return
     */
    private Map<String, Object> getRemainLmt(AccSysLmtBookPo accSysLmtBookPo, String userId, Date sysDate) {
        Map<String, Object> mapRemain = new HashMap<String, Object>();
        BigDecimal daySumAmtLimit = accSysLmtBookPo.getDaySumAmtLimit();
        BigDecimal monSumAmtLimit = accSysLmtBookPo.getMonSumAmtLimit();
        BigDecimal yearSumAmtLimit = accSysLmtBookPo.getYearSumAmtLimit();
        int daySumTimesLimit = accSysLmtBookPo.getDaySumTimesLimit();
        int monSumTimesLimit = accSysLmtBookPo.getMonSumTimesLimit();
        int yearSumTimesLimit = accSysLmtBookPo.getYearSumTimesLimit();

        AccLmtCountPo accLmtCountPo = new AccLmtCountPo();
        accLmtCountPo.setUserId(userId);
        accLmtCountPo.setLmtAccountFlag(DataBaseConstans_ACC.T_ACC_LMT_COUNT_ACCOUNT_FLAG_ACCSYS);
        accLmtCountPo.setSysLmtId(accSysLmtBookPo.getSysLmtId());
        accLmtCountPo.setTransDate(sysDate);
        accLmtCountPo = daoService.selectOne(accLmtCountPo);
        if (accLmtCountPo == null) {
            // 查询离当前日期最近日期的一条系统限额记录
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userID", userId);
            map.put("sysLmtId", accSysLmtBookPo.getSysLmtId());
            map.put("transStartDate", DateUtil.getRealMonth(sysDate, CommunicationConstants.PRE_TIME));
            map.put("transEndDate", DateUtil.getRealMonth(sysDate, CommunicationConstants.POST_TIME));
            map.put("orderby_transDate", "transDate");
            List<AccLmtCountPo> listAccLmtCountPo =
                    daoService.selectList(AccLmtCountPo.class.getName() + ".selectRecentList", map);
            if (listAccLmtCountPo != null && listAccLmtCountPo.size() > 0) {
                AccLmtCountPo accLmtCountPoLast = listAccLmtCountPo.get(0);
                Date transDate = accLmtCountPoLast.getTransDate();
                BigDecimal daySumAmtLimitRecent = null;
                int daySumCountLimitRecent = 0;
                BigDecimal monSumAmtLimitRecent = accLmtCountPoLast.getMonSumAmtLimit();// 已累计限额（月）
                int monSumCountLimitRecent = accLmtCountPoLast.getMonSumCountLimit();// 已累计笔数（月）
                BigDecimal yearSumAmtLimitRecent = accLmtCountPoLast.getYearSumAmtLimit();// 已累计限额（年）
                int yearSumCountLimitRecent = accLmtCountPoLast.getYearSumCountLimit();// 已累计笔数（年）
                if (transDate.compareTo(sysDate) < 0) {
                    // 该用户首次进行交易
                    daySumAmtLimitRecent = BigDecimal.valueOf(0);// 已累计限额（日）
                    daySumCountLimitRecent = 0;// 已累计笔数（日）
                } else if (transDate.compareTo(sysDate) > 0) {
                    ExInfo.throwDipperEx(AppCodeDict.BISACC0022, "限额累计日期大于了当前系统日期");
                } else {// 第一条取出来的记录的日期等于当前系统日期
                    daySumAmtLimitRecent = accLmtCountPoLast.getDaySumAmtLimit();// 已累计限额（日）
                    daySumCountLimitRecent = accLmtCountPoLast.getDaySumCountLimit();// 已累计笔数（日）
                }

                BigDecimal daySumAmtLmtRemain =
                        MoneyUtil.subtract(daySumAmtLimit, daySumAmtLimitRecent, null);
                if (daySumAmtLmtRemain.signum() < 0) {
                    daySumAmtLmtRemain = BigDecimal.ZERO;
                }
                mapRemain.put(DAYSUMAMTLMTREMAIN, daySumAmtLmtRemain); // 日累计用户剩余限额
                int daySumTimesLmtRemain = daySumTimesLimit - daySumCountLimitRecent;
                mapRemain.put(DAYSUMTIMESLMTREMAIN, daySumTimesLmtRemain); // 日累计用户剩余限额次数

                BigDecimal monSumAmtLmtRemain =
                        MoneyUtil.subtract(monSumAmtLimit, monSumAmtLimitRecent, null);
                mapRemain.put(MONSUMAMTLMTREMAIN, monSumAmtLmtRemain); // 月累计用户剩余限额
                int monSumTimesLmtRemain = monSumTimesLimit - monSumCountLimitRecent;
                mapRemain.put(MONSUMTIMESLMTREMAIN, monSumTimesLmtRemain); // 月累计用户剩余限额次数

                BigDecimal yearSumAmtLmtRemain =
                        MoneyUtil.subtract(yearSumAmtLimit, yearSumAmtLimitRecent, null);
                mapRemain.put(YEARSUMAMTLMTREMAIN, yearSumAmtLmtRemain); // 年累计用户剩余限额
                int yearSumTimesLmtRemain = yearSumTimesLimit - yearSumCountLimitRecent;
                mapRemain.put(YEARSUMTIMESLMTREMAIN, yearSumTimesLmtRemain); // 年累计用户剩余限额次数

            } else {
                mapRemain.put(DAYSUMAMTLMTREMAIN, daySumAmtLimit); // 日累计用户剩余限额
                mapRemain.put(DAYSUMTIMESLMTREMAIN, daySumTimesLimit); // 日累计用户剩余限额次数
                mapRemain.put(MONSUMAMTLMTREMAIN, monSumAmtLimit); // 月累计用户剩余限额
                mapRemain.put(MONSUMTIMESLMTREMAIN, monSumTimesLimit); // 月累计用户剩余限额次数
                mapRemain.put(YEARSUMAMTLMTREMAIN, yearSumAmtLimit); // 年累计用户剩余限额
                mapRemain.put(YEARSUMTIMESLMTREMAIN, yearSumTimesLimit); // 年累计用户剩余限额次数
            }
        } else {
            BigDecimal daySumAmtRemainLmt = daySumAmtLimit.subtract(accLmtCountPo.getDaySumAmtLimit());
            if (daySumAmtRemainLmt.signum() < 0) {
                daySumAmtRemainLmt = BigDecimal.ZERO;
            }
            mapRemain.put(DAYSUMAMTLMTREMAIN, daySumAmtRemainLmt); // 日累计用户剩余限额
            int daySumTimesRemainLmt = daySumTimesLimit - accLmtCountPo.getDaySumCountLimit();
            mapRemain.put(DAYSUMTIMESLMTREMAIN, daySumTimesRemainLmt); // 日累计用户剩余限额次数
            BigDecimal monSumAmtRemainLmt = monSumAmtLimit.subtract(accLmtCountPo.getMonSumAmtLimit());
            mapRemain.put(MONSUMAMTLMTREMAIN, monSumAmtRemainLmt); // 月累计用户剩余限额
            int monSumTimesRemainLmt = monSumTimesLimit - accLmtCountPo.getMonSumCountLimit();
            mapRemain.put(MONSUMTIMESLMTREMAIN, monSumTimesRemainLmt); // 月累计用户剩余限额次数

            BigDecimal yearSumAmtRemainLmt = yearSumAmtLimit.subtract(accLmtCountPo.getYearSumAmtLimit());
            mapRemain.put(YEARSUMAMTLMTREMAIN, yearSumAmtRemainLmt); // 年累计用户剩余限额
            int yearSumTimesRemainLmt = yearSumTimesLimit - accLmtCountPo.getYearSumCountLimit();
            mapRemain.put(YEARSUMTIMESLMTREMAIN, yearSumTimesRemainLmt); // 年累计用户剩余限额次数

        }
        return mapRemain;
    }


    /**
     * 获取比较后的最小的剩余限额
     * 
     * @param accLmtQueryDto
     * @param mapDc
     *            借贷方向的剩余限额
     * @param mapTc
     *            交易代码的剩余限额
     * @return
     */
    private AccLmtQueryDto getMinLmt(AccLmtQueryDto accLmtQueryDto, Map<String, Object> mapDc,
            Map<String, Object> mapTc) {

        if (mapDc == null && mapTc != null) {
            accLmtQueryDto.setDaySumAmtRemainLmt((BigDecimal) mapTc.get(DAYSUMAMTLMTREMAIN));
            accLmtQueryDto.setMonSumAmtRemainLmt((BigDecimal) mapTc.get(MONSUMAMTLMTREMAIN));
            accLmtQueryDto.setYearSumAmtRemainLmt((BigDecimal) mapTc.get(YEARSUMAMTLMTREMAIN));
            accLmtQueryDto.setDaySumTimesRemainLmt((Integer) mapTc.get(DAYSUMTIMESLMTREMAIN));
            accLmtQueryDto.setMonSumTimesRemainLmt((Integer) mapTc.get(MONSUMTIMESLMTREMAIN));
            accLmtQueryDto.setYearSumTimesRemainLmt((Integer) mapTc.get(YEARSUMTIMESLMTREMAIN));
        } else if (mapTc == null && mapDc != null) {
            accLmtQueryDto.setDaySumAmtRemainLmt((BigDecimal) mapDc.get(DAYSUMAMTLMTREMAIN));
            accLmtQueryDto.setMonSumAmtRemainLmt((BigDecimal) mapDc.get(MONSUMAMTLMTREMAIN));
            accLmtQueryDto.setYearSumAmtRemainLmt((BigDecimal) mapDc.get(YEARSUMAMTLMTREMAIN));
            accLmtQueryDto.setDaySumTimesRemainLmt((Integer) mapDc.get(DAYSUMTIMESLMTREMAIN));
            accLmtQueryDto.setMonSumTimesRemainLmt((Integer) mapDc.get(MONSUMTIMESLMTREMAIN));
            accLmtQueryDto.setYearSumTimesRemainLmt((Integer) mapDc.get(YEARSUMTIMESLMTREMAIN));
        } else if (mapDc != null && mapTc != null) {
            accLmtQueryDto.setDaySumAmtRemainLmt(((BigDecimal) mapDc.get(DAYSUMAMTLMTREMAIN))
                .compareTo((BigDecimal) mapTc.get(DAYSUMAMTLMTREMAIN)) > 0 ? (BigDecimal) mapTc
                .get(DAYSUMAMTLMTREMAIN) : (BigDecimal) mapDc.get(DAYSUMAMTLMTREMAIN));
            accLmtQueryDto.setMonSumAmtRemainLmt(((BigDecimal) mapDc.get(MONSUMAMTLMTREMAIN))
                .compareTo((BigDecimal) mapTc.get(MONSUMAMTLMTREMAIN)) > 0 ? (BigDecimal) mapTc
                .get(MONSUMAMTLMTREMAIN) : (BigDecimal) mapDc.get(MONSUMAMTLMTREMAIN));
            accLmtQueryDto.setYearSumAmtRemainLmt(((BigDecimal) mapDc.get(YEARSUMAMTLMTREMAIN))
                .compareTo((BigDecimal) mapTc.get(YEARSUMAMTLMTREMAIN)) > 0 ? (BigDecimal) mapTc
                .get(YEARSUMAMTLMTREMAIN) : (BigDecimal) mapDc.get(YEARSUMAMTLMTREMAIN));
            accLmtQueryDto
                .setDaySumTimesRemainLmt(((Integer) mapDc.get(DAYSUMTIMESLMTREMAIN)) > ((Integer) mapTc
                    .get(DAYSUMTIMESLMTREMAIN)) ? (Integer) mapTc.get(DAYSUMTIMESLMTREMAIN) : (Integer) mapDc
                    .get(DAYSUMTIMESLMTREMAIN));
            accLmtQueryDto
                .setMonSumTimesRemainLmt(((Integer) mapDc.get(MONSUMTIMESLMTREMAIN)) > ((Integer) mapTc
                    .get(MONSUMTIMESLMTREMAIN)) ? (Integer) mapTc.get(MONSUMTIMESLMTREMAIN) : (Integer) mapDc
                    .get(MONSUMTIMESLMTREMAIN));
            accLmtQueryDto
                .setYearSumTimesRemainLmt(((Integer) mapDc.get(YEARSUMTIMESLMTREMAIN)) > ((Integer) mapTc
                    .get(YEARSUMTIMESLMTREMAIN)) ? (Integer) mapTc.get(YEARSUMTIMESLMTREMAIN)
                        : (Integer) mapDc.get(YEARSUMTIMESLMTREMAIN));

        }
        return accLmtQueryDto;
    }

}
