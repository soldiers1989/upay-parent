package com.upay.busi.fee.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.fee.service.dto.CalculateFeeDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.fee.FeeAssDetailPo;
import com.upay.dao.po.fee.FeeAssPo;
import com.upay.dao.po.fee.FeeDetailPo;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;
import com.upay.dao.po.fee.FeeStandAreaPo;
import com.upay.dao.po.pay.PayOrderListPo;


/**
 * 手续费明细计算及分润明细计算
 * 
 * @author zhangjianfeng
 * @since 2016/11/24 21:09
 */
public class CalculateFeeService extends AbstractDipperHandler<CalculateFeeDto> {

    private static Logger logger = LoggerFactory.getLogger(CalculateFeeService.class);

    /** 0-不免费 */
    private static final String FEE_NOT_FREE = "0";
    /** 1-周期内免费 */
    private static final String FEE_CYCLE_FREE = "1";
    /** 2-周期内次数免费 */
    private static final String FEE_CYCLE_COUNT_FREE = "2";
    /** 3-全免费 */
    private static final String FEE_ALL_FREE = "3";

    @Resource
    private IDaoService daoService;


    @Override
    public CalculateFeeDto execute(CalculateFeeDto calculateFeeDto, Message message) throws Exception {

        // 交易码非空检查
        String transCode = calculateFeeDto.getTransCode();
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易码");
        }

        // 一级商户号非空检查
        String merNo = calculateFeeDto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "商户号");
        }

        // 交易金额检查
        BigDecimal transAmt = calculateFeeDto.getTransAmt();
        if (transAmt == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易金额");
        }
        if (transAmt.compareTo(BigDecimal.ZERO) < 0) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0047);
        }

        // 交易订单号非空检查
        if (StringUtils.isBlank(calculateFeeDto.getOrderNo())) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易订单号");
        }

        // 渠道标识非空检查
        if (StringUtils.isBlank(calculateFeeDto.getChnlId())) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "渠道标识");
        }

        // 交易类型非空检查
        if (StringUtils.isBlank(calculateFeeDto.getTransType())) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易类型");
        }

        // 系统交易日期
        FeeGetPo feeGet = getFeeGet(calculateFeeDto);

        if (feeGet != null) {
            // 手续明细生成
            BigDecimal feeAmt = generateFee(calculateFeeDto, feeGet);
            calculateFeeDto.setFeeAmt(feeAmt);
            calculateFeeDto.setFeeAmtChar(feeAmt.multiply(new BigDecimal("100"))
                .setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            // 修改订单手续
            updateOrderFee(calculateFeeDto, feeAmt);
            // 手续费分润明细生成
            if (feeAmt.compareTo(BigDecimal.ZERO) > 0) {
                generateFeeAss(calculateFeeDto, feeGet, feeAmt);
            }
            calculateFeeDto.setFeeGetType(feeGet.getGetType());
        } else { // 为null表示不收取手续费
            calculateFeeDto.setFeeAmt(BigDecimal.ZERO.setScale(2));
            calculateFeeDto.setFeeAmtChar("0");
        }

        return calculateFeeDto;
    }


    /**
     * 更新订单手续费
     * 
     * @param calculateFeeDto
     * @param feeAmt
     */
    private void updateOrderFee(CalculateFeeDto calculateFeeDto, BigDecimal feeAmt) {
        String orderNo = calculateFeeDto.getOrderNo();
        String transType = calculateFeeDto.getTransType();
        PayOrderListPo setOrder = new PayOrderListPo();
        if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)
                || CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)) { // 交易类型为支付则为商户手续费
            setOrder.setMerFeeAmt(feeAmt);
        } else {
            setOrder.setFeeAmt(feeAmt);
        }
        PayOrderListPo whereOrder = new PayOrderListPo();
        whereOrder.setOrderNo(orderNo);
        daoService.update(setOrder, whereOrder);
    }


    /**
     * 生成手续费分润明细
     * 
     * @param calculateFeeDto
     * @param feeGet
     */
    private void generateFeeAss(CalculateFeeDto calculateFeeDto, FeeGetPo feeGet, BigDecimal feeAmt) {
        // 分润代码
        String assCode = feeGet.getAssCode();
        // 查询手续费分润方法
        FeeAssPo feeAss = new FeeAssPo();
        feeAss.setAssCode(assCode);

        List<FeeAssPo> feeAssList = daoService.selectList(feeAss);
        if (feeAssList == null || feeAssList.size() == 0) {
            logger.error("手续费分润方法[{}]不存在", assCode);
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0017); // 没有配置分润计算规则
        }

        for (FeeAssPo fa : feeAssList) {
            // 分润类型
            String assType = fa.getAssType();
            BigDecimal assAmt = BigDecimal.ZERO.setScale(2);
            if (DataBaseConstants_PAY.T_FEE_ASS_ASS_TYPE_RATIO.equals(assType)) { // 按比例分润
                assAmt = feeAmt.multiply(fa.getAssRate().divide(new BigDecimal("100")));
            } else if (DataBaseConstants_PAY.T_FEE_ASS_ASS_TYPE_FIXED.equals(assType)) { // 按固定金额分润
                assAmt = fa.getFixAmt();
            }
            assAmt = assAmt.setScale(2, BigDecimal.ROUND_HALF_UP); // 分润金额，四舍五入

            // 保存分润明细
            FeeAssDetailPo feeAssDetail = new FeeAssDetailPo();
            feeAssDetail.setTxnDate(calculateFeeDto.getSysDate());
            feeAssDetail.setTxnTime(calculateFeeDto.getSysTime());
            feeAssDetail.setOrderNo(calculateFeeDto.getOrderNo());
            feeAssDetail.setAcctNo(calculateFeeDto.getAcctNo());
            feeAssDetail.setAcctType(calculateFeeDto.getAcctType());
            feeAssDetail.setChnlId(calculateFeeDto.getChnlId());
            feeAssDetail.setTransCode(calculateFeeDto.getTransCode());
            feeAssDetail.setAssCode(fa.getAssCode());
            feeAssDetail.setAssId(fa.getAssId());
            feeAssDetail.setAssRate(DataBaseConstants_PAY.T_FEE_ASS_ASS_TYPE_RATIO.equals(assType) ? fa
                .getAssRate() : null);
            feeAssDetail.setAssAmt(assAmt);
            if (DataBaseConstants_PAY.T_FEE_ASS_ASS_KIND_PUT.equals(fa.getAssKind())) { // 分润类型：0应收
                feeAssDetail.setDcFlag(DataBaseConstants_PAY.T_FEE_ASS_DC_FLAG_LOAN);
            } else if (DataBaseConstants_PAY.T_FEE_ASS_ASS_KIND_OUT.equals(fa.getAssKind())) { // 分润类型：1应付
                feeAssDetail.setDcFlag(DataBaseConstants_PAY.T_FEE_ASS_DC_FLAG_BORROW);
            }
            feeAssDetail.setTxnStat(calculateFeeDto.getOrderStat());

            daoService.insert(feeAssDetail);
        }
    }


    /**
     * 生成手续费
     * 
     * @param calculateFeeDto
     * @param feeGet
     * @return 手续费金额
     */
    private BigDecimal generateFee(CalculateFeeDto calculateFeeDto, FeeGetPo feeGet) throws Exception {

        BigDecimal feeAmt = BigDecimal.ZERO;
        // 免收处理
        String freeFlag = freeCycle(calculateFeeDto, feeGet);
        // 手续费处理
        if (FEE_ALL_FREE.equals(freeFlag) || FEE_CYCLE_FREE.equals(freeFlag)) { // 免收手续费
            feeAmt = BigDecimal.ZERO.setScale(2);
        } else if (FEE_CYCLE_COUNT_FREE.equals(freeFlag)) { // 免收周期内次数免收
            feeAmt = BigDecimal.ZERO.setScale(2);
            // TODO 免收周期次数增加一次

        } else if (FEE_NOT_FREE.equals(freeFlag)) { // 不免费
            String feeCode = feeGet.getFeeCode(); // 手续费代码
            FeeKindPo feeKind = new FeeKindPo();
            feeKind.setFeeCode(feeCode);
            feeKind = daoService.selectOne(feeKind);
            if (feeKind == null) {
                logger.error("手续费收取方法ID为[{}]对应的手续费计算方法[{}]未配置", new Object[] { feeGet.getFeeId(), feeCode });
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0016); // 没有配置手续费计算规则
            }
            // 计算手续费
            feeAmt = calculateFee(calculateFeeDto, feeKind);
            feeAmt = feeAmt.setScale(2, BigDecimal.ROUND_HALF_UP); // 手续费保留2位，四舍五入
            // 记录手续费明细
            FeeDetailPo feeDeatil = new FeeDetailPo();
            feeDeatil.setTxnDate(calculateFeeDto.getSysDate());
            feeDeatil.setTxnTime(calculateFeeDto.getSysTime());
            feeDeatil.setOrderNo(calculateFeeDto.getOrderNo());
            feeDeatil.setAcctNo(calculateFeeDto.getAcctNo());
            feeDeatil.setAcctType(calculateFeeDto.getAcctType());
            feeDeatil.setChnlId(calculateFeeDto.getChnlId());
            feeDeatil.setTransCode(calculateFeeDto.getTransCode());
            feeDeatil.setFeeCode(feeGet.getFeeCode());
            feeDeatil.setAssCode(feeGet.getAssCode());
            feeDeatil.setTxnAmt(calculateFeeDto.getTransAmt());
            feeDeatil.setFeeAmt(feeAmt);
            feeDeatil.setTxnStat(calculateFeeDto.getOrderStat());
            feeDeatil.setFreeFlag(DataBaseConstants_PAY.T_FEE_DETAIL_FREE_FLAG_NO);
            feeDeatil.setGetType(feeGet.getGetType() == null ? DataBaseConstants_FEE.FEE_GET_TYPE_OUTTER
                    : feeGet.getGetType());
            feeDeatil.setFeeId(feeGet.getFeeId());

            daoService.insert(feeDeatil);
        }

        return feeAmt;
    }


    /**
     * 计算手续费
     * 
     * @param calculateFeeDto
     * @param feeKind
     * @return
     */
    private BigDecimal calculateFee(CalculateFeeDto calculateFeeDto, FeeKindPo feeKind) throws Exception {
        // 交易金额
        BigDecimal transAmt = calculateFeeDto.getTransAmt();
        BigDecimal feeAmt = BigDecimal.ZERO.setScale(2);
        Date sysDate = calculateFeeDto.getSysDate();
        // 手续费收取方式
        String feeMode = feeKind.getFeeMode();
        if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED.equals(feeMode)) { // 单笔固定
            feeAmt = feeKind.getFixFee();
        } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL.equals(feeMode)) { // 单笔固定加按交易金额比例
            BigDecimal rationFee = transAmt.multiply(feeKind.getRationFee().divide(new BigDecimal("100")));
            feeAmt = feeKind.getFixFee().add(rationFee);
        } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION.equals(feeMode)) { // 按交易金额比例
            feeAmt = transAmt.multiply(feeKind.getRationFee().divide(new BigDecimal("100")));
        } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_SUBSECTION.equals(feeMode)) { // 分段配置
            Map<String, Object> feeAreaParamMap = new HashMap<String, Object>();
            feeAreaParamMap.put("feeCode", feeKind.getFeeCode());
            feeAreaParamMap.put("feeCollectDate", sysDate);
            // 排序处理
            List<Map<String, String>> orderByList = new ArrayList<Map<String, String>>();
            Map<String, String> orderByMap = new HashMap<String, String>();
            orderByMap.put("columnName", "AREA_LOW");
            orderByMap.put("sort", "asc");
            feeAreaParamMap.put("orderBy", orderByList);

            List<FeeStandAreaPo> feeStandAreaList =
                    daoService.selectList(FeeStandAreaPo.class.getName() + ".selectListExtra",
                        feeAreaParamMap);
            if (feeStandAreaList == null || feeStandAreaList.size() == 0) {
                logger.error("手续费计算方法收费代码[{}]对应的标准费率分段未配置", feeKind.getFeeCode());
                ExInfo.throwDipperEx(AppCodeDict.BISFEE0005, feeKind.getFeeCode()); // 收费代码[{}]对应的标准费率分段未配置
            }
            // 分段计算手续费
            for (FeeStandAreaPo feeStandArea : feeStandAreaList) {
                BigDecimal areaUp = feeStandArea.getAreaUp();
                BigDecimal areaLow = feeStandArea.getAreaLow();
                BigDecimal areaValue = feeStandArea.getAreaValue();
                BigDecimal areaTransAmt = BigDecimal.ZERO.setScale(2);
                boolean isCollectAreaFee = false; // 是否符合区间收费规则
                // 区间手续费，交易金额包括区间上限，不包括区间下限
                if (transAmt.compareTo(areaUp) >= 0) { // 交易金额 >= 区间上限
                    areaTransAmt = areaValue;
                    isCollectAreaFee = true;
                } else if (transAmt.compareTo(areaLow) > 0 && transAmt.compareTo(areaUp) < 0) { // 区间下限
                                                                                                // <
                                                                                                // 交易金额
                                                                                                // <
                                                                                                // 区间上限
                    areaTransAmt = transAmt.subtract(areaLow);
                    isCollectAreaFee = true;
                } else if (transAmt.compareTo(areaLow) <= 0) {
                    isCollectAreaFee = false;
                }

                // 区间手续费计算
                if (isCollectAreaFee) {
                    String areaFeeMode = feeStandArea.getFeeModelCode();
                    if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED.equals(areaFeeMode)) { // 单笔固定
                        feeAmt = feeAmt.add(feeStandArea.getFixFee());
                    } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL.equals(areaFeeMode)) { // 单笔固定加按交易金额比例
                        BigDecimal areaRationFee =
                                areaTransAmt.multiply(feeStandArea.getRationFee().divide(
                                    new BigDecimal("100")));
                        feeAmt = feeAmt.add(feeStandArea.getFixFee().add(areaRationFee));
                    } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION.equals(areaFeeMode)) { // 按交易金额比例
                        feeAmt =
                                feeAmt.add(areaTransAmt.multiply(feeStandArea.getRationFee().divide(
                                    new BigDecimal("100"))));
                    }
                }
            }
        }

        // 单笔固定加按交易金额比例、按交易金额比例、分段配置，手续费上限、下限处理
        if (!DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED.equals(feeMode)) {
            BigDecimal lowFee = feeKind.getLowFee();
            BigDecimal highFee = feeKind.getHighFee();
            if (lowFee != null && feeAmt.compareTo(lowFee) < 0) {
                feeAmt = lowFee;
            }
            if (highFee != null && feeAmt.compareTo(highFee) > 0) {
                feeAmt = highFee;
            }
        }
        return feeAmt;
    }


    /**
     * 免收周期处理
     * 
     * @param calculateFeeDto
     * @param feeGet
     * @return 免收标志 0-不免费；1-周期内免费；2-周期次数内免费；3-全免费；
     */
    private String freeCycle(CalculateFeeDto calculateFeeDto, FeeGetPo feeGet) throws Exception {
        String freeFlag = FEE_NOT_FREE;
        // 系统日期
        Date sysDate = calculateFeeDto.getSysDate();
        // 免收周期
        String freeCycle = feeGet.getFreeCycle();
        if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_NO.equals(freeCycle)) { // 不免费
            freeFlag = FEE_NOT_FREE;
        } else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_ALL.equals(freeCycle)) { // 全免
            freeFlag = FEE_ALL_FREE;
        } else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_DAY.equals(freeCycle)
                || DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_MONTH.equals(freeCycle)
                || DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_YEAR.equals(freeCycle)) { // 周期免收
            // 费用收取开始日期
            Date feeStartDate = feeGet.getStartDate();
            // 费用收取结束日期
            Date feeEndDate = feeGet.getEndDate();
            // 免收周期结束日期
            Date cycleEndDate = null;
            if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_DAY.equals(freeCycle)) {
                cycleEndDate = DateUtils.addDays(feeStartDate, 1);
            } else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_MONTH.equals(freeCycle)) {
                cycleEndDate = DateUtils.addMonths(feeStartDate, 1);
            } else if (DataBaseConstants_PAY.T_FEE_GET_FREE_CYCLE_YEAR.equals(freeCycle)) {
                cycleEndDate = DateUtils.addYears(feeStartDate, 1);
            }

            if (cycleEndDate.compareTo(sysDate) >= 0) { // 免收周期内
                Integer freeCount = feeGet.getFreeCount();
                if (freeCount != null) {
                    String feeId = feeGet.getFeeId();
                    // TODO 待需求明确后处理免收次数

                } else { // 周期内免收
                    freeFlag = FEE_CYCLE_FREE;
                }
            } else { // 免收周期外
                freeFlag = FEE_NOT_FREE;
            }
        } else {
            logger.error("费用方法ID为[{}]免收周期方式[{}]不支持",
                new Object[] { feeGet.getFeeId(), feeGet.getFreeCycle() });
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0016); // 没有配置手续费计算规则
        }
        return freeFlag;
    }


    /**
     * 获取手续费收取方法
     * 
     * @param calculateFeeDto
     * @return
     */
    private FeeGetPo getFeeGet(CalculateFeeDto calculateFeeDto) throws Exception {
        // 交易码
        String transCode = calculateFeeDto.getTransCode();
        // 一级商户号
        String merNo = calculateFeeDto.getMerNo();
        // 系统交易日期
        Date sysDate = calculateFeeDto.getSysDate();

        FeeGetPo feeGet = null;
        // 获取手续费收取方法，现只实现根据一级商户号查询手续费收取方法，后期修改为根据多字段
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("transCode", transCode);
        paramMap.put("merNo", merNo);
        paramMap.put("feeCollectDate", sysDate); // 手续费收取日期

        feeGet = daoService.selectOne(FeeGetPo.class.getName() + ".selectOneExtra", paramMap);

        return feeGet;
    }

}
