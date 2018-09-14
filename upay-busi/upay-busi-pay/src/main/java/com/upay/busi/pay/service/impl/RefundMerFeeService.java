package com.upay.busi.pay.service.impl;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RefundMerFeeDto;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.fee.FeeDetailPo;
import com.upay.dao.po.pay.PayFlowListPo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退还商户手续费处理。
 * 原支付交易手续费已分润不再退还商户手续费；多次退货非首次退款不再退还商户手续费；多次退货首次退款时全额退还商户手续费；
 * 当日退货：修改原支付交易手续明细状态且手续明细不参与清算。
 * 隔日退货（手续费未分润）：修改原支付交易手续费明细状态且手续明细不参与分润；从手续费归集户中支付原支付交易手续费给商户；
 * 隔日退货（手续费已清算）：不再退还商户手续费。
 * @author zhangjianfeng
 * @since 2016/08/16
 */
public class RefundMerFeeService extends AbstractDipperHandler<RefundMerFeeDto> {

    private static final Logger LOG = LoggerFactory.getLogger(RefundMerFeeService.class);

    @Resource
    private IDaoService daoService;

    @Resource
    private ISequenceService sequenceService;

    @Override
    public RefundMerFeeDto execute(RefundMerFeeDto dto, Message message) throws Exception {

        BigDecimal hadRefundAmt = dto.getHadRefundAmt(); //已退款金额

        //原支付交易是否收取手续费处理
        String oriOrderNo = dto.getOriOrderNo();
        FeeDetailPo feeDetail = new FeeDetailPo();
        feeDetail.setOrderNo(oriOrderNo);
        feeDetail = daoService.selectOne(feeDetail);
        if(feeDetail == null || feeDetail.getFeeAmt().compareTo(BigDecimal.ZERO) == 0) {
            dto.setIsReturnFee("0");
            return dto;
        } else {
            dto.setFeeAmt(feeDetail.getFeeAmt());
            dto.setFeeId(feeDetail.getFeeId());
            dto.setFeeCharAmt(feeDetail.getFeeAmt().multiply(new BigDecimal("100")).toString());
        }

        //TODO 手续费是否已分润处理


        if(hadRefundAmt != null && hadRefundAmt.compareTo(BigDecimal.ZERO) > 0) { //非首次退款
            dto.setIsReturnFee("0");
        } else { //首次退款
            dto.setIsReturnFee("1");
            if("1".equals(dto.getIsNextDayRefund())) { //隔日退货，生成退还商户手续费支付流水
                PayFlowListPo refundFeePayFlow = new PayFlowListPo();
                refundFeePayFlow.setOrderNo(dto.getRefundOrderNo());
                refundFeePayFlow.setMerNo(dto.getMerNo());
                refundFeePayFlow.setSecMerNo(dto.getSecMerNo());
                refundFeePayFlow.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO); //往来标识
                refundFeePayFlow.setSysDate(dto.getSysDate()); //支付平台交易日期
                refundFeePayFlow.setTransSubSeq(sequenceService.generatePayFlowSeq()); //支付流水号
                refundFeePayFlow.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST); //通道代码
                refundFeePayFlow.setPayerAcctNo(""); //手续费归集户
                refundFeePayFlow.setPayerAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
                refundFeePayFlow.setPayeeAcctNo(dto.getMerVacctNo()); //商户支付平台虚拟账户
                refundFeePayFlow.setPayeeAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
                refundFeePayFlow.setCcy(dto.getOriCcy());
                refundFeePayFlow.setTransAmt(feeDetail.getFeeAmt());
                refundFeePayFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN); //初始状态
                Date currTime = new Date();
                refundFeePayFlow.setTransTime(currTime);
                refundFeePayFlow.setLastUpdateTime(currTime);
                refundFeePayFlow.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
                daoService.insert(refundFeePayFlow);

                //DTO中设置付款方信息
                dto.setRefundFeePayerAcctNo(refundFeePayFlow.getPayerAcctNo());
                dto.setRefundFeePayerAcctType(refundFeePayFlow.getPayerAcctType());
                dto.setRefundFeePayerBankName(refundFeePayFlow.getPayerBankName());
                dto.setRefundFeePayerBankNo(refundFeePayFlow.getPayerBankNo());
                dto.setRefundFeePayerName(refundFeePayFlow.getPayerName());
                dto.setRefundFeePayerOrgName(refundFeePayFlow.getPayerOrgName());
                //DTO中设置收款方信息
                dto.setRefundFeePayeeAcctNo(refundFeePayFlow.getPayeeAcctNo());
                dto.setRefundFeePayeeAcctType(refundFeePayFlow.getPayeeAcctType());
                dto.setRefundFeePayeeBankName(refundFeePayFlow.getPayeeBankName());
                dto.setRefundFeePayeeBankNo(refundFeePayFlow.getPayeeBankNo());
                dto.setRefundFeePayeeName(refundFeePayFlow.getPayeeName());
                dto.setRefundFeePayeeOrgName(refundFeePayFlow.getPayeeOrgName());
                //其它信息设置
                dto.setRefundFeeTransSubSeq(refundFeePayFlow.getTransSubSeq());
                dto.setRefundFeeFlowTransCharDate(DateUtil.format(refundFeePayFlow.getSysDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD));
            } else { //非隔日退货，修改支付交易手续费明细状态
                FeeDetailPo setFeeDetail = new FeeDetailPo();
                FeeDetailPo whereFeeDetail = new FeeDetailPo();
                //TODO 如何修改手续明细状态
                whereFeeDetail.setOrderNo(oriOrderNo);
                daoService.update(setFeeDetail, whereFeeDetail);
            }
        }
        return dto;
    }
}
