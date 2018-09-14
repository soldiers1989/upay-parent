package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckInFeeFlowListServiceDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * 登记核心流水，冲手续费
 *
 * @author yhy 20170516
 * 
 */
public class CheckInFeeFlowListService extends AbstractDipperHandler<CheckInFeeFlowListServiceDto> {
    @Resource
    private IDaoService daoService;
    @Resource
    private ISequenceService seqService;
    
    private static final Logger logger = LoggerFactory.getLogger(CheckInFeeFlowListService.class);


	@Override
	public CheckInFeeFlowListServiceDto execute(CheckInFeeFlowListServiceDto dto, Message message)
			throws Exception {
		String dealType = dto.getDealType();
		String errFlowSeq = dto.getErrFlowSeq();
		String isAddFee = dto.getIsAddFee();//是否补手续费  Y-补 N-冲
		if (StringUtils.isBlank(errFlowSeq)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "平台流水号");
        }
		if (StringUtils.isBlank(dealType)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错处理方式");
        }
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMdd");
		//核心通道信息
		PayRouteInfoPo payRouteInfo = new PayRouteInfoPo();
		payRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		payRouteInfo = daoService.selectOne(payRouteInfo);
		//差错流水信息
		PayFlowListPo payFlowList = new PayFlowListPo();
		payFlowList.setTransSubSeq(errFlowSeq);
		payFlowList = daoService.selectOne(payFlowList);
		
		PayFlowListPo feeFlowList = new PayFlowListPo();
		feeFlowList.setOrderNo(payFlowList.getOrderNo());
		feeFlowList.setPayerAcctNo(payRouteInfo.getTransAcctNo());
		feeFlowList.setPayeeAcctNo(payRouteInfo.getClrAcctNo());
		feeFlowList.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
		feeFlowList = daoService.selectOne(feeFlowList);
		
		//差错订单信息 
		PayOrderListPo payOrder = new PayOrderListPo();
		payOrder.setOrderNo(payFlowList.getOrderNo());
		payOrder = daoService.selectOne(payOrder);
		String paySeq = seqService.generatePayFlowSeq();
		if("Y".equals(isAddFee)){//是否补手续费  Y-补 N-冲
			dto.setCoreFeeCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
			dto.setFeePayErAcctNo(payRouteInfo.getTransAcctNo());
			dto.setFeePayEeAcctNo(payRouteInfo.getClrAcctNo());
			dto.setTranFee(MoneyUtil.transferY2F(payOrder.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
			dto.setFeePaySeq(paySeq);
		}else{
			dto.setBizSerialNo(paySeq);
			dto.setCoreFeeCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08002);
			dto.setBankCardNo(feeFlowList.getPayerAcctNo());
			dto.setSetAccount(feeFlowList.getPayeeAcctNo());
			dto.setOrgBizDate(simp.format(feeFlowList.getSysDate()));
			dto.setOrgBizSerialNo(feeFlowList.getTransSubSeq());
			dto.setAmount(MoneyUtil.transferY2F(feeFlowList.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
			dto.setFeePaySeq(paySeq);
		}
		//登记核心流水，冲手续费
		insertPayFlowList(payOrder,paySeq,payRouteInfo,isAddFee);
		
		SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
        String time = sim.format(new Date());
        dto.setMachineDate(time.substring(0, 8));
        dto.setMachineTime(time.substring(8));
        dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0, 8));
		dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		return dto;
	}
	
	private void insertPayFlowList(PayOrderListPo payOrder, String paySeq, PayRouteInfoPo payRouteInfo, String isAddFee){
		
        Date now = new Date();
        Date date = SysInfoContext.getSysDate();
       
        PayFlowListPo pay = new PayFlowListPo();
        pay.setOrderNo(payOrder.getOrderNo());
        pay.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);
        pay.setOrderDes(payOrder.getOrderDes());
        pay.setSecMerNo(payOrder.getSecMerNo());
        pay.setSysDate(date);
        pay.setTransSubSeq(paySeq);
        if("Y".equals(isAddFee)){
        	pay.setPayerAcctNo(payRouteInfo.getTransAcctNo());
        	pay.setPayeeAcctNo(payRouteInfo.getClrAcctNo());
        }else{
        	pay.setPayerAcctNo(payRouteInfo.getClrAcctNo());
        	pay.setPayeeAcctNo(payRouteInfo.getTransAcctNo());
        	
        }
        
        HashMap<String, Object> parmMap = new HashMap<>();
		parmMap.put("orderNo", payOrder.getOrderNo());
		Integer maxSeqNo = (Integer) daoService.selectOne(
				PayFlowListPo.class.getName() + ".findMaxSeqNo", parmMap);
		if (null == maxSeqNo || 0 == maxSeqNo) {
			maxSeqNo = 1;
		} else {
			maxSeqNo = maxSeqNo + 1;
		}
		pay.setSeqNo(maxSeqNo);
		
        pay.setPayerAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        pay.setPayeeAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        pay.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        pay.setMerNo(payOrder.getMerNo());
        pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
        pay.setTransAmt(payOrder.getFeeAmt());
        pay.setTransTime(now);
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
        pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        pay.setLastUpdateTime(now);
        pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        daoService.insert(pay);
	}
}
