package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DealStlChkErrDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 处理商户结算产生的差错
 * 
 * @author yhy 20170516
 * 
 */
public class DealStlChkErrService extends AbstractDipperHandler<DealStlChkErrDto> {
	
	@Resource
	private IDaoService daoService;
	private static final Logger logger = LoggerFactory.getLogger(DealStlChkErrService.class);
	@Resource
	private ISequenceService seqService;

	@Override
	public DealStlChkErrDto execute(DealStlChkErrDto dto,Message message) throws Exception {
		String dealType = dto.getDealType();
		String errFlowSeq = dto.getErrFlowSeq();
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMdd");
		//差错流水
		PayFlowListPo errFlowList = new PayFlowListPo();
		errFlowList.setTransSubSeq(errFlowSeq);
		errFlowList = daoService.selectOne(errFlowList);
		
		if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_ADD.equals(dealType)){
			String isAdd = "Y";
			logger.info("核心补账处理开始!流水号："+errFlowSeq);
			
			String paySeq = seqService.generatePayFlowSeq();
			insertPayFlowList(paySeq,errFlowList,isAdd);
			dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
			
			dto.setPayErAcctNo(errFlowList.getPayerAcctNo());
			dto.setPayEeAcctNo(errFlowList.getPayeeAcctNo());
			dto.setIsReqCore("Y");
			dto.setCoreSubSeq(paySeq);
			dto.setTranDate(SysInfoContext.getSysDate());
			dto.setTranType(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
			dto.setTranAmt(MoneyUtil.transferY2F(errFlowList.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
			
		}else if(DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_RUS.equals(dealType) || DataBaseConstants_PAY.T_PMT_DEELERR_WAY_WeChat_RUS.equals(dealType)){
			String isAdd = "N";
			logger.info("核心冲账处理开始!流水号："+errFlowSeq);
			dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08002);
			if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(errFlowList.getPayeeAcctType())){
				dto.setBankCardNo(errFlowList.getPayeeAcctNo());
				dto.setSetAccount(errFlowList.getPayerAcctNo());
			}else{
				dto.setBankCardNo(errFlowList.getPayerAcctNo());
				dto.setSetAccount(errFlowList.getPayeeAcctNo());
			}
			dto.setAmount(MoneyUtil.transferY2F(errFlowList.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
			dto.setOrgBizDate(simp.format(errFlowList.getSysDate()));
			dto.setOrgBizSerialNo(errFlowList.getTransSubSeq());
			String paySeq = seqService.generatePayFlowSeq();
			insertPayFlowList(paySeq,errFlowList,isAdd);
			dto.setIsReqCore("Y");
			dto.setCoreSubSeq(paySeq);
			dto.setTranDate(SysInfoContext.getSysDate());
		}
		
		SimpleDateFormat sim = new SimpleDateFormat(
				CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
		String time = sim.format(new Date());
		dto.setMachineDate(time.substring(0, 8));
		dto.setMachineTime(time.substring(8));
		dto.setBizDate(sim.format(SysInfoContext.getSysDate()).substring(0, 8));
		dto.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		return dto;
		
	}
	
	
	private void insertPayFlowList(String paySeq,PayFlowListPo corePayFlow, String isAdd) {

		Date now = new Date();
		Date date = SysInfoContext.getSysDate();

		PayFlowListPo pay = new PayFlowListPo();
		pay.setOrderNo(corePayFlow.getOrderNo());
		pay.setSrFlag("1");
		pay.setOrderDes(corePayFlow.getOrderDes());
		pay.setSecMerNo(corePayFlow.getSecMerNo());
		pay.setSysDate(date);
		pay.setTransSubSeq(paySeq);
		pay.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		pay.setMerNo(corePayFlow.getMerNo());
		if ("N".equals(isAdd)) {
			pay.setPayerAcctNo(corePayFlow.getPayeeAcctNo());
			pay.setPayeeAcctNo(corePayFlow.getPayerAcctNo());
			pay.setPayerAcctType(corePayFlow.getPayeeAcctType());
			pay.setPayeeAcctType(corePayFlow.getPayerAcctType());

		} else {
			pay.setPayerAcctNo(corePayFlow.getPayerAcctNo());
			pay.setPayeeAcctNo(corePayFlow.getPayeeAcctNo());
			pay.setPayerAcctType(corePayFlow.getPayerAcctType());
			pay.setPayeeAcctType(corePayFlow.getPayeeAcctType());
		}
		pay.setPayUserId(corePayFlow.getPayUserId());
		pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
		pay.setTransAmt(corePayFlow.getTransAmt());
		pay.setFeeAmt(corePayFlow.getFeeAmt());
		pay.setTransTime(now);
		pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
		pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
		pay.setLastUpdateTime(now);
		pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);

		HashMap<String, Object> parmMap = new HashMap<>();
		parmMap.put("orderNo", corePayFlow.getOrderNo());
		Integer maxSeqNo = (Integer) daoService.selectOne(
				PayFlowListPo.class.getName() + ".findMaxSeqNo", parmMap);
		if (null == maxSeqNo || 0 == maxSeqNo) {
			maxSeqNo = 1;
		} else {
			maxSeqNo = maxSeqNo + 1;
		}
		pay.setSeqNo(maxSeqNo);

		daoService.insert(pay);
	}
}
