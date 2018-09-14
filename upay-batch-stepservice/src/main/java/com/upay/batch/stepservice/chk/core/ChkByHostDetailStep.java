package com.upay.batch.stepservice.chk.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * 以核心数据为主对账
 * 
 * @author 张立朋
 * @version v1.0
 * @CreateDate: 2016-11-28
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:yyyy-mm-dd
 * @UpdateRemark:修改具体的内容；
 */

public class ChkByHostDetailStep extends
		AbstractStepExecutor<Object, ChkHostDetailPo> {
	private final static Logger logger = LoggerFactory
			.getLogger(ChkByHostDetailStep.class);

	@Override
	public List<ChkHostDetailPo> getDataList(BatchParams batchParams,
			int offset, int pageSize, Object object) throws BatchException {
		String batchNo = batchParams.getBatchNo();
		logger.info(
				"-----批次号：{}---获取核心对账明细表(T_CHK_HOST_DETAIL)数据流水数据结束--------",
				batchParams.getBatchNo());
		// 获取核心对账表(T_Chk_Host_Detail)数据
		ChkHostDetailPo chkHostDetailPo = new ChkHostDetailPo();
		chkHostDetailPo.setChkActNo(batchNo);
		chkHostDetailPo.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
		List<ChkHostDetailPo> chkHostDetailPoList = new ArrayList<ChkHostDetailPo>();
		chkHostDetailPoList = daoService.selectList(chkHostDetailPo);
		logger.info(
				"-----批次号：{}---获取核心对账明细表(T_CHK_HOST_DETAIL)数据流水数据结束--------",
				batchParams.getBatchNo());
		return chkHostDetailPoList;
	}

	@Override
	public void execute(BatchParams batchParams, int index,
			ChkHostDetailPo data, Object object) throws BatchException {
		logger.info("-----执行批量操作第" + index + "次--------");
		// 获取源表数据
		ChkHostDetailPo chkHostDetailPo = (ChkHostDetailPo) data;
		// 开始进行数据勾兑:根据平台流水号、资金通道代码、平台交易日期查询交易流水
		PayFlowListPo payFlowListPo = queryPmtFlowListInfo(
				chkHostDetailPo.getPlatSeq(), chkHostDetailPo.getOrgCode(),
				chkHostDetailPo.getPlatDate());
		if (payFlowListPo == null) {
			// 核心多
			logger.info("核心多");
			// 插入差错信息表（T_CHK_ERR_LIST）

			insertChkErrListInfo(chkHostDetailPo, payFlowListPo, batchParams,
					DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
			logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
			updateChkHostDetailInfo(chkHostDetailPo,
					DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
			logger.info("更新核心对账明细表(T_CHK_HOST_DETAIL)結束");
		} else {
			// 流水同时存在
			// 支付流水存在

			if (payFlowListPo.getTransStat().equals(
					DataBaseConstants_PAY.T_PAY_TX_SUCCESS)
					&& chkHostDetailPo.getTranStat().equals(
							CommonConstants_GNR.OUT_PAY_STAT_SUCCESS)) { //
				// 记账状态相同,且都为成功
				if (chkHostDetailPo.getTransAmt().compareTo(
								payFlowListPo.getTransAmt()) != 0) {
					// 金额不符
					logger.info("对账状态相符，金额不符");
					// 插入差错信息表（T_CHK_ERR_LIST）
					// 插入差错信息表（T_CHK_ERR_LIST）
					insertChkErrListInfo(chkHostDetailPo, payFlowListPo,
							batchParams,
							DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
					logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
					updateChkHostDetailInfo(chkHostDetailPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
					logger.info("更新核心对账明细表(T_CHK_HOST_DETAIL)結束");
					updatePayFlowList(payFlowListPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_NOT_BALANCED);
					logger.info("更新平台支付流水表(T_PAY_FLWO_LIST)結束");
				} else {
					logger.info("对账成功");
					updateChkHostDetailInfo(chkHostDetailPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
					updatePayFlowList(payFlowListPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_SUCCESS);
					logger.info("更新平台支付流水表(T_PAY_FLWO_LIST)和核心对账明细表(T_CHK_HOST_DETAIL)結束");
				}
			} else {
				// 状态不同,核心成功,平台失败
				if (chkHostDetailPo.getTranStat().equals(
						CommonConstants_GNR.OUT_PAY_STAT_SUCCESS)) {
					logger.info("核心成功，本系统失败/非终态");
					// 插入差错信息表（T_CHK_ERR_LIST）
					insertChkErrListInfo(chkHostDetailPo, payFlowListPo,
							batchParams,
							DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
					logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
					updateChkHostDetailInfo(chkHostDetailPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
					logger.info("更新核心对账明细表(T_CHK_HOST_DETAIL)結束");
					updatePayFlowList(payFlowListPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_LESS);
					logger.info("更新平台流水表(T_PAY_FLWO_LIST)結束");
				} else {
					logger.info("核心失败，本系统成功");
					// 插入差错信息表（T_CHK_ERR_LIST）
					insertChkErrListInfo(chkHostDetailPo, payFlowListPo,
							batchParams,
							DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
					logger.info("插入差错信息表（T_CHK_ERR_LIST）結束");
					updateChkHostDetailInfo(chkHostDetailPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
					logger.info("更新核心对账明细表(T_CHK_HOST_DETAIL)結束");
					updatePayFlowList(payFlowListPo,
							DataBaseConstants_BATCH.T_CHK_FLAG_MORE);
					logger.info("更新平台流水表(T_PAY_FLWO_LIST)結束");
				}

			}
		}
	}

	/***
	 * *更新支付平台流水表(T_PAY_FLOW_LIST)
	 * 
	 * @param chkDetailPo
	 * @param chkStat
	 */
	public void updatePayFlowList(PayFlowListPo payFlowListPo, String chkStat) {
		payFlowListPo.setChkFlag(chkStat);// 对账标志
		daoService.update(payFlowListPo);
	}

	/***
	 * *查询支付流水表(T_PMT_FLOW_LIST)
	 * 
	 * @param chkSeq
	 * @param routeCode
	 * @param chkDate
	 */
	public PayFlowListPo queryPmtFlowListInfo(String chkSeq, String routeCode,
			Date chkDate) {
		PayFlowListPo pmtFlowListPo = new PayFlowListPo();
		pmtFlowListPo.setRouteDate(chkDate);
		pmtFlowListPo.setTransSubSeq(chkSeq);
		pmtFlowListPo.setRouteCode(routeCode);
		pmtFlowListPo = daoService.selectOne(pmtFlowListPo);
		return pmtFlowListPo;
	}

	/***
	 * *更新核心对账明细表(T_CHK_HOST_DETAIL)
	 * 
	 * @param chkThirdDetailPo
	 * @param chkStat
	 */
	public void updateChkHostDetailInfo(ChkHostDetailPo chkHostDetailPo,
			String chkStat) {
		chkHostDetailPo.setChkFlag(chkStat);// 对账标志
		daoService.update(chkHostDetailPo);

	}

	/***
	 * *插入差错信息表（T_CHK_ERR_LIST）
	 * 
	 * @param batchParams
	 * 
	 * @param ChkDetailPo
	 * @param ChkErrDealOpinionPo
	 */
	public void insertChkErrListInfo(ChkHostDetailPo chkHostDetailPo,
			PayFlowListPo payFlowListPo, BatchParams batchParams, String errStat) {

		ChkErrListPo chkErrListPo = new ChkErrListPo();
		// chkErrListPo.setOrgCode(chkHostDetailPo.getOrgCode());//第三方机构代码

		chkErrListPo.setTransDate(chkHostDetailPo.getPlatDate());
		chkErrListPo.setTransTime(chkHostDetailPo.getPlatDate());
		chkErrListPo.setSysSeq(chkHostDetailPo.getPlatSeq());// 平台流水号
		chkErrListPo.setHostSeq(chkHostDetailPo.getChnlSeq());// 核心流水号
		chkErrListPo.setBatchNo(chkHostDetailPo.getChkActNo());
		// chkErrListPo.setOrgCode(routeCode);//除核心外的资金通道代码
		chkErrListPo.setHostChkBatchNo(chkHostDetailPo.getChkBatchNo());
		chkErrListPo.setHostChkDate(chkHostDetailPo.getChkDate());
		// chkErrListPo.setThirdChkBatchNo(value);// 除核心外的资金通道对账批次号
		// chkErrListPo.setThirdChkDate(value);// 除核心外的资金通道对账日期
		chkErrListPo.setCurrNo(DataBaseConstants_PAY.T_CORE_CCY_CNY);
		chkErrListPo.setPayAcct(chkHostDetailPo.getAcctNo());
		chkErrListPo.setPayeeAcct(chkHostDetailPo.getOthAcctNo());
		chkErrListPo.setTransAmt(chkHostDetailPo.getTransAmt());
		chkErrListPo.setFeeAmt(chkHostDetailPo.getFeeAmt());// 需要新增字段
		chkErrListPo.setHostErrStat(errStat);
		chkErrListPo.setErrStat(DataBaseConstants_PAY.T_PMT_DEELERR_NOT);

		if (payFlowListPo != null) {
			// 获取订单信息
			PayOrderListPo payOrderListPo = new PayOrderListPo();
			payOrderListPo.setOrderNo(payFlowListPo.getOrderNo());
			payOrderListPo = daoService.selectOne(payOrderListPo);

			if (payOrderListPo != null) {
				chkErrListPo.setTransCode(payOrderListPo.getTransCode());
				chkErrListPo.setChnlId(payOrderListPo.getChnlId());
				chkErrListPo.setChnlSeq(payOrderListPo.getMerSeq());// 商户流水号
				chkErrListPo.setOrderNo(payOrderListPo.getOuterOrderNo());// 商户订单号
			}

			// 保存支付流水相关信息
			chkErrListPo.setTransDate(payFlowListPo.getSysDate());
			chkErrListPo.setTransTime(payFlowListPo.getTransTime());
			chkErrListPo.setSysSeq(payFlowListPo.getTransSubSeq());
		}

		daoService.insert(chkErrListPo);//

	}

}
