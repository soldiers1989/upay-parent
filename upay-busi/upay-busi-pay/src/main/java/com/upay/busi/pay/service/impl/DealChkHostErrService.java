package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DealChkHostErrServiceDto;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccBindBookPo;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 处理与核心对账产生的差错
 * 
 * @author yhy 20170516
 * 
 */
public class DealChkHostErrService extends
		AbstractDipperHandler<DealChkHostErrServiceDto> {
	@Resource
	private IDaoService daoService;
	private static final Logger logger = LoggerFactory
			.getLogger(DealChkHostErrService.class);
	@Resource
	private ISequenceService seqService;

	@Override
	public DealChkHostErrServiceDto execute(DealChkHostErrServiceDto dto,
			Message message) throws Exception {
		String dealType = dto.getDealType();
		String errFlowSeq = dto.getErrFlowSeq();
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMdd");
		if (StringUtils.isBlank(errFlowSeq)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "平台流水号");
		}
		if (StringUtils.isBlank(dealType)) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "差错处理方式");
		}
		// 差错的流水
		PayFlowListPo corePayFlow = new PayFlowListPo();
		corePayFlow.setTransSubSeq(errFlowSeq);
		corePayFlow = daoService.selectOne(corePayFlow);
		if (corePayFlow == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "差错对应的流水");
		}
		// 查询核心通道信息
		PayRouteInfoPo coreRouteInfo = new PayRouteInfoPo();
		coreRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		coreRouteInfo = daoService.selectOne(coreRouteInfo);
		// 查询订单信息
		PayOrderListPo orderList = new PayOrderListPo();
		orderList.setOrderNo(corePayFlow.getOrderNo());
		orderList = daoService.selectOne(orderList);

		// 核心补账处理，针对平台多、核心少的差错
		if (DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD.equals(dealType)) {
			logger.info("核心补账处理开始!流水号："+errFlowSeq);
			// 手续费判断
			if (orderList.getFeeAmt() != null
					&& orderList.getFeeAmt().compareTo(new BigDecimal("0")) > 0) {
				PayFlowListPo payFlowListPo = new PayFlowListPo();
				payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
				payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
				List<PayFlowListPo> list = daoService.selectList(payFlowListPo);
				//如果有两笔成功的核心流水，则说明手续费记账成功（排除隔日退款）
				if(list.size()  < 2){
					dto.setIsAddFee("Y");// 是否补手续费 Y-补 N-冲
				}
			}
			boolean flag = isDebit(corePayFlow, orderList, dealType, dto);
			if (flag) {
				// 贷记卡记账
				dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08011);
				if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(orderList.getTransType())){
					dto.setTranType(CommonConstants_GNR.CORE_BANK_PAY_TYPE_REFUND);
				}else{
					dto.setTranType(CommonConstants_GNR.CORE_BANK_PAY_TYPE_PAY);
				}
			} else {
				// 借记卡或者内部账户记账
				if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(orderList
						.getTransType())) {
					//如果是退款补账需要调用退款接口，tran_type为2
					if (DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD
							.equals(corePayFlow.getPayeeAcctType())) {
						dto.setTranType(CommonConstants_GNR.CORE_BANK_PAY_TYPE_REFUND);
						dto.setOrgBizDate(simp.format(corePayFlow
								.getRouteDate()));
						dto.setOrgBizSerialNo(corePayFlow.getTransSubSeq());
						dto.setPayErAcctNo(corePayFlow.getPayeeAcctNo());
						dto.setPayEeAcctNo(corePayFlow.getPayerAcctNo());
					}
				} else {
					dto.setPayErAcctNo(corePayFlow.getPayerAcctNo());
					dto.setPayEeAcctNo(corePayFlow.getPayeeAcctNo());
					dto.setTranType(CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
					
					//下面是针对网银充值的记账接口修改   或者收款方为二类户的情况下
					 if(CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(orderList.getChnlId())
							 ||DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(corePayFlow.getPayeeAcctType())){
						 	Map<String,Object> mapParam=new HashMap<String,Object>();
		                	mapParam.put("orderNo", orderList.getOrderNo());
		                	mapParam.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
	                    	// 目前网银用的是中金通道  所以只查询
	                    	PayFlowListPo flowPo = daoService.selectOne(PayFlowListPo.class.getName().concat(".findByOrderNoAndRouteCode"), mapParam);
	                    	dto.setThirdFlag(DataBaseConstants_PAY.THIRD_FLAG);
	                    	dto.setThirdAccount(flowPo.getPayerAcctNo());//第一条流水的付款方
	                    	dto.setAccountName(flowPo.getPayerName());//第一条流水的付款方姓名
	                    	dto.setMemoCode(DataBaseConstants_PAY.MEMO_CODE_TRANSFER);//备注是充值
	                 }
				}
				dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
			}

			String isAdd = "Y";
			String paySeq = seqService.generatePayFlowSeq();
			insertPayFlowList(orderList, paySeq, corePayFlow, isAdd);
			dto.setIsReqCore("Y");
			dto.setCoreSubSeq(paySeq);
			dto.setTranDate(SysInfoContext.getSysDate());
			dto.setTranAmt(MoneyUtil.transferY2F(corePayFlow.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
		} else if (DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_RUS
				.equals(dealType)) {// 核心冲账针对核心多，平台少
			logger.info("核心冲账处理开始!流水号："+errFlowSeq);
			String isAdd = "N";

			ChkHostDetailPo chkHostDetailPo = new ChkHostDetailPo();
			chkHostDetailPo.setPlatSeq(errFlowSeq);
			chkHostDetailPo = daoService.selectOne(chkHostDetailPo);

			if (chkHostDetailPo != null && !DataBaseConstants_BATCH.T_CHK_FLAG_LESS.equals(chkHostDetailPo
					.getChkFlag())) {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0109, new Object[] {
						"核心对账差错处理", "对方多", "核心冲账" });
			}

			if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(orderList.getTransType())){
				// 手续费判断
				if (orderList.getFeeAmt() != null
						&& orderList.getFeeAmt().compareTo(new BigDecimal("0")) > 0) {
					PayFlowListPo payFlowListPo = new PayFlowListPo();
					payFlowListPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
					payFlowListPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
					List<PayFlowListPo> list = daoService.selectList(payFlowListPo);
					//如果有两笔成功的核心流水，则说明手续费记账成功（排除隔日退款）
					if(list.size() == 2){
						dto.setIsAddFee("N");// 是否补手续费 Y-补 N-冲
					}
				}
			}
			boolean flag = isDebit(corePayFlow, orderList, dealType, dto);

			if (flag) {
				// 贷记卡冲正
				dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08013);
				if(DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(corePayFlow.getPayeeAcctType())){
					dto.setBankCardNo(corePayFlow.getPayeeAcctNo());
					dto.setSetAccount(corePayFlow.getPayerAcctNo());
				}else{
					dto.setBankCardNo(corePayFlow.getPayerAcctNo());
					dto.setSetAccount(corePayFlow.getPayeeAcctNo());
				}
				
			} else {
				// 借记卡或者内部账户冲正
				dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08002);
				if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(corePayFlow.getPayeeAcctType())){
					dto.setBankCardNo(corePayFlow.getPayeeAcctNo());
					dto.setSetAccount(corePayFlow.getPayerAcctNo());
				}else{
					dto.setBankCardNo(corePayFlow.getPayerAcctNo());
					dto.setSetAccount(corePayFlow.getPayeeAcctNo());
				}
				
			}
			dto.setAmount(MoneyUtil.transferY2F(corePayFlow.getTransAmt().multiply(new BigDecimal(100)), 0).toString());
			dto.setOrgBizDate(simp.format(corePayFlow.getSysDate()));
			dto.setOrgBizSerialNo(corePayFlow.getTransSubSeq());

			// 是否是本行贷记卡交易
			String paySeq = seqService.generatePayFlowSeq();
			insertPayFlowList(orderList, paySeq,corePayFlow, isAdd);
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

	private void insertPayFlowList(PayOrderListPo payOrderList, String paySeq,
			PayFlowListPo corePayFlow, String isAdd) {

		Date now = new Date();
		Date date = SysInfoContext.getSysDate();

		PayFlowListPo pay = new PayFlowListPo();
		pay.setOrderNo(payOrderList.getOrderNo());
		pay.setSrFlag("1");
		pay.setOrderDes(payOrderList.getOrderDes());
		pay.setSecMerNo(payOrderList.getSecMerNo());
		pay.setSysDate(date);
		pay.setTransSubSeq(paySeq);
		pay.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
		pay.setMerNo(payOrderList.getMerNo());
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
		pay.setTransAmt(payOrderList.getTransAmt());
		pay.setFeeAmt(payOrderList.getFeeAmt());
		pay.setTransTime(now);
		pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
		pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
		pay.setLastUpdateTime(now);
		pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);

		HashMap<String, Object> parmMap = new HashMap<>();
		parmMap.put("orderNo", payOrderList.getOrderNo());
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

	private boolean isDebit(PayFlowListPo corePayFlow,
			PayOrderListPo orderList, String dealType,
			DealChkHostErrServiceDto dto) {
		boolean isDebit = false;
		String debitNo = null;
		String debitTranType = null;
		SimpleDateFormat simp = new SimpleDateFormat("yyyyMMdd");
		if (CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(orderList
				.getTransType())) {
			if (DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(corePayFlow
					.getPayerAcctType())) {
				isDebit = true;
				debitNo = corePayFlow.getPayerAcctType();
				if (DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD
						.equals(dealType)) {
					debitTranType = DataBaseConstants_PAY.CORE_DEBIT_TRANS_TYPE_PAY;
					dto.setPayErAcctNo(corePayFlow.getPayerAcctNo());
					dto.setPayEeAcctNo(corePayFlow.getPayeeAcctNo());
				}
			}
		} else if (CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(orderList
				.getTransType())) {
			if (DataBaseConstants_PAY.ACCT_TYPE_CREDIT_CARD.equals(corePayFlow
					.getPayeeAcctType())) {
				isDebit = true;
				debitNo = corePayFlow.getPayeeAcctType();
				if (DataBaseConstants_PAY.T_PMT_DEELERR_WAY_HOST_ADD
						.equals(dealType)) {
					PayFlowListPo oriFlowCore = new PayFlowListPo();
					oriFlowCore
							.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
					oriFlowCore.setOrderNo(orderList.getOriOrderNo());
					oriFlowCore = daoService.selectOne(oriFlowCore);
					dto.setOrgBizSerialNo(oriFlowCore.getTransSubSeq());
					dto.setOrgBizDate(simp.format(oriFlowCore.getRouteDate()));
					debitTranType = DataBaseConstants_PAY.CORE_DEBIT_TRANS_TYPE_REFUND;
				}
			}
		}

		if (isDebit) {
			dto.setCoreTranCode(CommonConstants_GNR.CORE_BANK_PAY_CODE_08011);
			AccBindBookPo accBindBook = new AccBindBookPo();
			accBindBook.setBindAcctType(debitNo);
			accBindBook
					.setBindStat(DataBaseConstans_ACC.ACC_BIND_BOOK_BIND_STAT_BIND);
			List<AccBindBookPo> acctBindList = daoService
					.selectList(accBindBook);
			if (acctBindList.size() != 0) {
				dto.setCvv2(acctBindList.get(0).getCvn2());
				dto.setVaildDate(acctBindList.get(0).getValidDate());
				dto.setDebitTrantype(debitTranType);
			} else {
				ExInfo.throwDipperEx(AppCodeDict.BISPAY0117, debitNo);
			}
		}
		return isDebit;
	}
}
