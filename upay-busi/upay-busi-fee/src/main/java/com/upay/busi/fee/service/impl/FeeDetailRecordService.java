package com.upay.busi.fee.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.fee.service.dto.FeeDetailRecordDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.fee.FeeDetailPo;


/**
 * 费用明细记录
 * 
 * @author liu
 * 
 */
public class FeeDetailRecordService extends AbstractDipperHandler<FeeDetailRecordDto> {

    @Resource
    private IDaoService daoService;
    private static final Logger LOG = LoggerFactory.getLogger(FeeDetailRecordService.class);


    @Override
    public FeeDetailRecordDto execute(FeeDetailRecordDto feeDetailRecordDto, Message msg) throws Exception {

        // 获取费用明细中的所有必输字段，并判断是否为空
        Date txnDate = feeDetailRecordDto.getTxnDate();
        Date txnTime = feeDetailRecordDto.getTxnTime();
        String orderNo = feeDetailRecordDto.getOrderNo();
        String acctNo = feeDetailRecordDto.getAcctNo();
        String acctType = feeDetailRecordDto.getAcctType();
        String chnlId = feeDetailRecordDto.getChnlId();
        String transCode = feeDetailRecordDto.getTransCode();
        String feeCode = feeDetailRecordDto.getFeeCode();
        String assCode = feeDetailRecordDto.getAssCode();
        BigDecimal txnAmt = feeDetailRecordDto.getTxnAmt();// 非必输
        BigDecimal feeAmt = feeDetailRecordDto.getFeeAmt();// 非必输
        String remarkCode = feeDetailRecordDto.getRemarkCode();// 非必输
        String txnStat = feeDetailRecordDto.getTxnStat();
        String freeFlag = feeDetailRecordDto.getFreeFlag();// 非必输
        String freeCycle = feeDetailRecordDto.getFreeCycle();// 非必输
        String getType = feeDetailRecordDto.getGetType();
        String memo = feeDetailRecordDto.getMemo();// 非必输

        if (txnDate.equals(null) || txnDate.equals("")) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易日期");
        }
        if (txnTime.equals(null) || txnTime.equals("")) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易时间");
        }
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易订单号");
        }
        if (StringUtils.isBlank(acctNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "支付账号");
        }
        if (StringUtils.isBlank(acctType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "账户类型");
        }
        if (StringUtils.isBlank(chnlId)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "渠道编号");
        }
        if (StringUtils.isBlank(transCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易代码");
        }
        if (StringUtils.isBlank(feeCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "收费代码");
        }
        if (StringUtils.isBlank(assCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "分润代码");
        }
        if (StringUtils.isBlank(txnStat)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "交易状态");
        }
        if (StringUtils.isBlank(getType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISFEE0001, "手续费收起方式");
        }

        FeeDetailPo feeDetailPo = new FeeDetailPo();
        feeDetailPo.setTxnDate(txnDate);
        feeDetailPo.setTxnTime(txnTime);
        feeDetailPo.setOrderNo(orderNo);
        feeDetailPo.setAcctNo(acctNo);
        feeDetailPo.setAcctType(acctType);
        feeDetailPo.setChnlId(chnlId);
        feeDetailPo.setTransCode(transCode);
        feeDetailPo.setFeeCode(feeCode);
        feeDetailPo.setAssCode(assCode);
        feeDetailPo.setTxnStat(txnStat);
        feeDetailPo.setGetType(getType);

        feeDetailPo.setTxnAmt(txnAmt);
        feeDetailPo.setFeeAmt(feeAmt);
        feeDetailPo.setRemarkCode(remarkCode);
        feeDetailPo.setFreeFlag(freeFlag);
        feeDetailPo.setMemo(memo);

        daoService.insert(feeDetailPo);

        return feeDetailRecordDto;
    }
}
