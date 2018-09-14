package com.upay.busi.fee.service.impl;


import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.fee.service.dto.FeeGetBaseDto;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.fee.FeeGetPo;
import com.upay.dao.po.fee.FeeKindPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 根据资金通道获取手续费
 * Created by dk on 2017/8/4.
 */
public class GetFeeByRouteCodeService extends AbstractDipperHandler<FeeGetBaseDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger LOG = LoggerFactory.getLogger(GetFeeByRouteCodeService.class);

    @Override
    public FeeGetBaseDto execute(FeeGetBaseDto dto, Message msg) throws Exception {
        LOG.debug("根据资金通道代码获取手续费。。。。。。。");
        String routeCode = dto.getRouteCode();
        
        if (StringUtils.isBlank(routeCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道代码");
        }
        String transCode = dto.getTransCode();
//        if (StringUtils.isBlank(transCode)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易代码");
//        }
        
        BigDecimal transAmt = dto.getTransAmt();
        if (null == transAmt) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
        }
//        transCode = getTransCodeMethod(transCode, routeCode);
        FeeGetPo feeGetPo = new FeeGetPo();
        feeGetPo.setRouteCode(routeCode);
        feeGetPo.setTransCode(transCode);
//        String payType = dto.getPayType();
//        String accType = null;
//        if (null != payType) {
//            if (DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_PERSONAL.equals(payType)) {//个人网银
//                accType = DataBaseConstants_PAY.ACCT_TYPE_PERSONAL_ACCT;
//            } else if (DataBaseConstants_PAY.T_PAY_TYPE_ONLINE_BANK_COMPANY.equals(payType)) {//企业网银
//                accType = DataBaseConstants_PAY.ACCT_TYPE_ENTERPRISE_ACCT;
//            }
//        }
//        feeGetPo.setAcctType(accType);
        feeGetPo.setRouteFeeFlag(DataBaseConstants_FEE.ROUTE_FEE_YES); //通道手续费
        //获取交易码下的手续费收取方法集合
        feeGetPo = daoService.selectOne(feeGetPo);
        if (null != feeGetPo) {
            String feeCode = feeGetPo.getFeeCode();
            dto.setRouteFeeAmt(calculateFeeAmt(transAmt, feeCode));
        } else {
        	LOG.debug("还未设置交易代码["+transCode+"]资金通道["+routeCode+"]手续费！");
            dto.setRouteFeeAmt(new BigDecimal(0.00));
        }
        return dto;
    }

    /**
     * 计算手续费
     *
     * @param transAmt
     * @param feeCode
     * @return
     */
    private BigDecimal calculateFeeAmt(BigDecimal transAmt, String feeCode) {
        BigDecimal routeFeeAmt = new BigDecimal(0);
        FeeKindPo feeKindPo = new FeeKindPo();
        feeKindPo.setFeeCode(feeCode);
        feeKindPo = daoService.selectOne(feeKindPo);
        if (null == feeKindPo) {
            LOG.debug("该资金通道下还未设置手续费计算参数");
            return new BigDecimal(0);
        } else {
            String feeMode = feeKindPo.getFeeMode();
            if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_FIXED.equals(feeMode)) { //单笔固定
                routeFeeAmt = feeKindPo.getFixFee();
            } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_ALL.equals(feeMode)) { //单笔固定加交易金额比例
                BigDecimal rationFee = feeKindPo.getRationFee().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
                routeFeeAmt = feeKindPo.getFixFee().add(transAmt.multiply(rationFee));
            } else if (DataBaseConstants_PAY.T_FEE_KIND_FEE_MODE_PROPERTION.equals(feeMode)) { //按交易金额比例
                //先将取出的费率除100 在进行手续费计算
                BigDecimal rationFee = feeKindPo.getRationFee().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
                routeFeeAmt = transAmt.multiply(rationFee);
            }
        }
        //计算完毕后判断是否超过上下线
        if (null != feeKindPo.getLowFee()){
            int result = routeFeeAmt.compareTo(feeKindPo.getLowFee());
            if (result < 0) {
                routeFeeAmt = feeKindPo.getLowFee();
            }
        }
        if (null != feeKindPo.getHighFee()) {
            int result = routeFeeAmt.compareTo(feeKindPo.getHighFee());
            if (result > 0) {
                routeFeeAmt = feeKindPo.getHighFee();
            }
        }
        return routeFeeAmt;
    }

    /**
     * 将通道代码和交易代码成新的交易代码
     *
     * @param transCode
     * @param routeCode
     * @return
     */
    private String getTransCodeMethod(String transCode, String routeCode) {
        int transCodeLen = transCode.length();
        int routeCodeLen = routeCode.length();
        int leftLen = 10 - transCodeLen - routeCodeLen;
        String taken = "";
        for (int i = 0; i < leftLen; i++) {
            taken += "_";
        }
        transCode = routeCode + taken + transCode;
        return transCode;
    }
}
