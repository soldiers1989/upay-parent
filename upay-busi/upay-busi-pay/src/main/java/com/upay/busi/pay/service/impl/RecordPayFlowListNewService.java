package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.RecordPayFlowListDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_FEE;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayFlowListPo;


/**
 * 记录支付流水 
 * 
 * @author shangqiankun
 * @version 创建时间：2016年8月6日 下午2:14:47
 */
public class RecordPayFlowListNewService extends AbstractDipperHandler<RecordPayFlowListDto> {


    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService seqService;


    @Override
    public RecordPayFlowListDto execute(RecordPayFlowListDto dto, Message message) throws Exception {
        String orderNo = dto.getOrderNo();
        String merNo = dto.getMerNo();
        String secMerNo = dto.getSecMerNo();
        String routeCode = dto.getRouteCode();
        BigDecimal feeAmt = dto.getFeeAmt()==null?new BigDecimal(0):dto.getFeeAmt();
        BigDecimal transAmt = dto.getTransAmt();
        
        if(DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(dto.getTransCode())){
        	//转账收款方如果手续费是内扣的情况，登记流水交易金额为减去手续费后的金额
        	if(!"Y".equals(dto.getIsPayee())&&DataBaseConstants_FEE.FEE_GET_TYPE_INNER.equals(dto.getGetType())){
        		transAmt=MoneyUtil.subtract(transAmt, feeAmt, RoundingMode.HALF_UP);
        	}
        }else if(DataBaseConstans_ACC.TRANS_CODE_WITHDRAW.equals(dto.getTransCode())){
        	//提现如果手续费是内扣的情况，登记交易金额为减去手续费后的金额，否则对账时会出差金额不符
        	if(!"Y".equals(dto.getIsFeeAmt())&&DataBaseConstants_FEE.FEE_GET_TYPE_INNER.equals(dto.getGetType())){
        		transAmt=MoneyUtil.subtract(transAmt, feeAmt, RoundingMode.HALF_UP);
        	}
        }
        
        
        String srFlag=dto.getSrFlag();
        String orderDes=dto.getOrderDes();
        String payerAccType=dto.getPayerAccType();
//        String payType=dto.getPayType();
        String	payerAccNo=dto.getPayerAccNo();
        String	payeeAccNo=dto.getPayeeAccNo();
        String payerUserId=dto.getPayerUserId();
        if (StringUtils.isBlank(srFlag)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "往来标识");
        }
        if (StringUtils.isBlank(orderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "订单号");
        }
        if (StringUtils.isBlank(routeCode)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道");
        }
        if (transAmt==null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "用户支付金额");
        }else if(transAmt.compareTo(BigDecimal.ZERO)<=0){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0002);
        }
//        if (StringUtils.isBlank(payType)) {
//            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "支付方式");
//        }
        HashMap<String, Object> parmMap=new HashMap<>();
        parmMap.put("orderNo", orderNo);
        Integer maxSeqNo = (Integer)daoService.selectOne(PayFlowListPo.class.getName()+".findMaxSeqNo",parmMap);
        if(null==maxSeqNo||0==maxSeqNo){
        	maxSeqNo=1;
        }else{
        	maxSeqNo=maxSeqNo+1;
        }

        Date now = new Date();
        Date date = SysInfoContext.getSysDate();
        String paySeq = dto.getTransSubSeq();
        PayFlowListPo pay = new PayFlowListPo();
        pay.setOrderNo(orderNo);
        pay.setSeqNo(maxSeqNo);
        pay.setSrFlag(srFlag);
        pay.setOrderDes(orderDes);
        pay.setSecMerNo(secMerNo);
        pay.setSysDate(date);
        pay.setTransSubSeq(paySeq);
        pay.setPayerAcctNo(payerAccNo);
        pay.setRouteCode(routeCode);
        pay.setMerNo(merNo);
        pay.setPayUserId(payerUserId);
        pay.setPayerAcctType(payerAccType);
        pay.setPayerOrgName(dto.getPayerOrgName());
        pay.setPayerBankNo(dto.getPayerBankNo());
        pay.setPayerBankName(dto.getPayerBankName());
        pay.setPayerName(dto.getPayerName());
        pay.setPayeeAcctNo(payeeAccNo);
        pay.setPayeeAcctType(dto.getPayeeAccType());
        pay.setPayeeName(dto.getPayeeName());
        pay.setPayeeOrgName(dto.getPayeeOrgName());
        pay.setPayeeBankNo(dto.getPayeeBankNo());
        pay.setPayeeBankName(dto.getPayeeBankName());
        pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
        pay.setTransAmt(transAmt);
        pay.setFeeAmt(feeAmt);
        //转账时收款方流水记录无手续费
  		if("1".equals(dto.getIsRegPayerOrderNo())){
  			pay.setFeeAmt(null);// 收款方订单流水为无客户手续费
  		}
        pay.setTransTime(now);
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
        pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        pay.setLastUpdateTime(now);
        pay.setOpenId(dto.getOpenId());
        pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
            pay.setRouteSeq(CommonMethodUtils.getZJSubSeq());
            dto.setRouteSeq(pay.getRouteSeq());
        }
        pay.setPromoterDeptNo(dto.getPromoterDeptNo());
        daoService.insert(pay);
        dto.setTransSubSeq(pay.getTransSubSeq());
        
        
        
        

        return dto;
    }
}
