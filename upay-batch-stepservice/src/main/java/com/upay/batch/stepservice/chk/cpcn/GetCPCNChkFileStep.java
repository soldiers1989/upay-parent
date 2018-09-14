package com.upay.batch.stepservice.chk.cpcn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;

/**
 * 获取中金对账明细,并解析入库 调用中金1810接口，查询中金交易明细 第二步
 * 
 * @author Pactera
 * 
 */
public class GetCPCNChkFileStep extends
		AbstractStepExecutor<ChkInfoPo, HashMap> {

	private final static Logger logger = LoggerFactory
			.getLogger(GetCPCNChkFileStep.class);
	private IDipperHandler<Message> cpcnChkFileHandler;

	/**
	 * 中金节假日不参与对账 非节假日，查询对账信息（包括节假日的对账信息）
	 * 
	 * @param batchParams
	 * @return
	 * @throws BatchException
	 */
	@Override
	public List<ChkInfoPo> getObjectList(BatchParams batchParams)
			throws BatchException {
		// 中金节假日也参于对账
		// if(!PlatFormHolidayContext.isWorkDay(batchParams.getTranDate(), "0"))
		// {
		// return null;
		// }

		// 查询未对账信息;
		ChkInfoPo chkInfo = new ChkInfoPo();
		chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);
		chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
		chkInfo.setChkDate(batchParams.getTranDate());
		List<ChkInfoPo> listChkInfo = daoService.selectList(chkInfo);
		logger.debug("获取当日中金对账记录===============================");
		return listChkInfo;
	}

	@SuppressWarnings({ "rawtypes", "null", "unchecked" })
	@Override
	public List<HashMap> getDataList(BatchParams batchParams, int offset,
			int pageSize, ChkInfoPo object) throws BatchException {
		logger.info("查询中金交易订单开始===========================");

		String channel = "zjpaycli";
		final String charsetName = "GBK";

		// 查检当前日期是否已经导入过数据 如果己导入做删除操作
		ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
		chkThirdDetailPo.setChkDate(batchParams.getTranDate());
		chkThirdDetailPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
		List<ChkThirdDetailPo> chkThirdDetailList = daoService
				.selectList(chkThirdDetailPo);
		if (null != chkThirdDetailList && chkThirdDetailList.size() > 0) {
			logger.info(DateUtil.format(batchParams.getTranDate(),
					DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD)
					+ "中金对账明细数据己存在,删除再重新导入");
			for (ChkThirdDetailPo detailPo : chkThirdDetailList) {
				daoService.delete(detailPo);
			}
		}

		logger.info("-----批次号：[{}]---Step[{}]---获取中金支付对账明细数据流水数据并入库开始--------",
				batchParams.getBatchNo(), "GetCPCNChkFileStep");

		// 调用中金的接口获取对账的明细信息
		Message m = MessageFactory.create(IdGenerateFactory.generateId(),
				channel, "XML", charsetName, MessageFactory
						.createSimpleMessage(new HashMap<String, Object>(),
								new HashMap<String, Object>()), FaultFactory
						.create(Constants.ResponseCode.SUCCESS, ""));

		Map<String, Object> body = (Map<String, Object>) m.getTarget()
				.getBodys();

		// 获取前一天日期
		Date preDate = DateUtil.getPreDate(object.getChkDate());

		// 中金报文头header
		// body.put("_TRAN_CODE", "1810");
		body.put("institutionID", CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID);// 002367

		// 中金报文体body
		body.put("searchDate", DateUtil.format(preDate,
				DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD));// 内部交易代码，获取模板用
		List<HashMap> acountBill = null;
		List<HashMap> acountBill2 = null;
		List<HashMap> acountBill3 = null;
		try {
			// 机构号002367的中金数据
			Message handle = cpcnChkFileHandler.handle(m);
			Map<String, Object> bodys = (Map<String, Object>) handle
					.getTarget().getBodys();
			acountBill = (List<HashMap>) bodys.get("acountBill");

			if (Constants.ResponseCode.SUCCESS.equals(handle.getFault()
					.getCode())) {

				object.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC); // 文件下载成功
				// 修改批次号，因节假日生成的对账信息（节假日不对账）中批次号不为实际对账批次号
				object.setBatchNo(batchParams.getBatchNo());
				daoService.update(object);
				logger.info("机构号:"
						+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID
						+ "        获取日期为:[{}]的中金对账明细成功，交易记录数为:[{}]",
						new Object[] { DateUtil.format(preDate, "yyyyMMdd"),
								(acountBill == null ? 0 : acountBill.size()) });

			} else {
				object.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_FAIL); // 文件下载失败
				daoService.update(object);
				logger.info("机构号:"
						+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID
						+ "        取得日期为:[{}]的中金对账明细失败",
						DateUtil.format(preDate, "yyyyMMdd"));
				throw new BatchException("机构号:"
						+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID
						+ "        取得日期为:"
						+ DateUtil.format(preDate, "yyyyMMdd") + "的中金对账明细失败");
			}

			// 机构号002366的中金数据
			body.put("institutionID",
					CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK);// 002366
			handle = cpcnChkFileHandler.handle(m);
			bodys = (Map<String, Object>) handle.getTarget().getBodys();
			acountBill2 = (List<HashMap>) bodys.get("acountBill");

			if (Constants.ResponseCode.SUCCESS.equals(handle.getFault()
					.getCode())) {
				object.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC); // 文件下载成功
				// 修改批次号，因节假日生成的对账信息（节假日不对账）中批次号不为实际对账批次号
				object.setBatchNo(batchParams.getBatchNo());
				daoService.update(object);
				logger.info(
						"机构号:"
								+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK
								+ "        获取日期为:[{}]的中金对账明细成功，交易记录数为:[{}]",
						new Object[] { DateUtil.format(preDate, "yyyyMMdd"),
								(acountBill2 == null ? 0 : acountBill2.size()) });

			} else {
				object.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_FAIL); // 文件下载失败
				daoService.update(object);
				logger.info(
						"机构号:"
								+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK
								+ "        取得日期为:[{}]的中金对账明细失败",
						DateUtil.format(preDate, "yyyyMMdd"));
				throw new BatchException(
						"机构号:"
								+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ONLINE_BANK
								+ "        取得日期为:"
								+ DateUtil.format(preDate, "yyyyMMdd")
								+ "的中金对账明细失败");
			}

			// 机构号002368的中金数据 单笔代扣（2011接口）的交易
			body.put(
					"institutionID",
					CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION);// 002368
			handle = cpcnChkFileHandler.handle(m);
			bodys = (Map<String, Object>) handle.getTarget().getBodys();
			acountBill3 = (List<HashMap>) bodys.get("acountBill");

			if (Constants.ResponseCode.SUCCESS.equals(handle.getFault()
					.getCode())) {
				object.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC); // 文件下载成功
				// 修改批次号，因节假日生成的对账信息（节假日不对账）中批次号不为实际对账批次号
				object.setBatchNo(batchParams.getBatchNo());
				daoService.update(object);
				logger.info(
						"机构号:"
								+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION
								+ "        获取日期为:[{}]的中金对账明细成功，交易记录数为:[{}]",
						new Object[] { DateUtil.format(preDate, "yyyyMMdd"),
								(acountBill3 == null ? 0 : acountBill3.size()) });

			} else {
				object.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_FAIL); // 文件下载失败
				daoService.update(object);
				logger.info(
						"机构号:"
								+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION
								+ "        取得日期为:[{}]的中金对账明细失败",
						DateUtil.format(preDate, "yyyyMMdd"));
				throw new BatchException(
						"机构号:"
								+ CmparmConstants.GATEWAY_ZJPAY_INSTITUTION_ID_ENTERPRISE_FUND_COLLECTION
								+ "        取得日期为:"
								+ DateUtil.format(preDate, "yyyyMMdd")
								+ "的中金对账明细失败");
			}
		} catch (Exception e) {
			logger.error("批量==============获取核心对账文件出现错误", e);
			e.printStackTrace();
			throw new BatchException("批量==============获取核心对账文件出现错误      "
					+ e.getMessage());
		}
		if (null == acountBill) {
			acountBill = new ArrayList<HashMap>();
		}
		if (null == acountBill2) {
			acountBill2 = new ArrayList<HashMap>();
			;
		}
		if (null == acountBill3) {
			acountBill3 = new ArrayList<HashMap>();
			;
		}
		acountBill.addAll(acountBill2);
		acountBill.addAll(acountBill3);
		logger.info("获取中金交易订单共==== " + acountBill.size() + " ====笔。");
		return acountBill;
	}

	/**
	 * 登记中金交易明细到第三方对账明细表
	 * 
	 * @param batchParams
	 * @param index
	 * @param data
	 * @param object
	 * @throws BatchException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(BatchParams batchParams, int index, HashMap data,
			ChkInfoPo object) throws BatchException {
		if (null != data && data.size() > 0) {
			String chkSeq = (String) data.get("txSn");
			this.insertThird(data, chkSeq, batchParams, object);
		}
	}

	@Override
	public void updateObject(BatchParams batchParams, ChkInfoPo object) {
		logger.info("-----批次号：[{}]------获取中金支付对账明细数据流水数据并入库结束--------",
				batchParams.getBatchNo(), "GetCPCNChkFileStep");
	}

	/**
	 * 登记第三方表
	 * 
	 * @param data
	 *            中金支付数据
	 * @param chkSeq
	 *            中金流水号
	 * @param object
	 *            为对账批次信息
	 */

	@SuppressWarnings("rawtypes")
	public void insertThird(HashMap data, String chkSeq,
			BatchParams batchParams, ChkInfoPo object) {
		logger.info("登记中金交易明细[{}]开始...", chkSeq);

		if (data == null) {
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "对账批次信息");
		}
		ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();

		chkThirdDetailPo.setThirdSeq(chkSeq);
		ChkThirdDetailPo chkNo = this.daoService.selectOne(chkThirdDetailPo);
		if (chkNo == null) {

			logger.debug("=========" + data.get("txSn") + "          "
					+ data.get("txType") + "         "
					+ data.get("institutionAmount") + "           "
					+ data.get("txAmount") + "       "
					+ data.get("bankNotificationTime"));
			chkThirdDetailPo.setChkBatchNo(batchParams.getBatchNo());// 对账批次号
			chkThirdDetailPo.setChkDate(batchParams.getTranDate());// 对帐日期

			// 平台交易日期
			chkThirdDetailPo.setChnlDate(DateUtil.getPreDate(object
					.getChkDate()));
			// 平台交易流水号
			chkThirdDetailPo.setChnlSeq((String) data.get("txSn"));
			// 备注，中金交易码
			chkThirdDetailPo.setRemark((String) data.get("txType"));
			// 第三方机构，通道代码
			chkThirdDetailPo.setOrgCode(object.getOrgCode());
			String txAmtount = (String) data.get("txAmount");
			BigDecimal transAmt = BigDecimal.ZERO;
			if (StringUtils.isNotBlank(txAmtount)) {
				transAmt = new BigDecimal(txAmtount).divide(new BigDecimal("100"));
			}
			String institutionAmount = (String) data.get("institutionAmount");
			BigDecimal institutionAmt = BigDecimal.ZERO;
			if (StringUtils.isNotBlank(institutionAmount)) {
				institutionAmt = new BigDecimal(institutionAmount).divide(new BigDecimal("100"));
			}
			
			String institutionFee = (String) data.get("institutionFee");
			BigDecimal feeAmt = BigDecimal.ZERO;
			if (StringUtils.isNotBlank(institutionFee)) {
				feeAmt = new BigDecimal(institutionFee).divide(new BigDecimal("100"));
			}
			chkThirdDetailPo.setTransAmt(transAmt);// 交易金额
			chkThirdDetailPo.setFeeAmt(feeAmt);// 支付平台应付手续费=交易金额-支付平台应收的金额

			chkThirdDetailPo.setCcy(DataBaseConstants_PAY.T_CCY_CNY);// 币种
			chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);// 对账状态
																			// 0：未对账
			String bankNotificationTime = (String) data
					.get("bankNotificationTime");
			if (StringUtils.isNotBlank(bankNotificationTime)) {
				chkThirdDetailPo.setThirdDate(DateUtil.parse(bankNotificationTime, "yyyyMMddHHmmss"));// 第三方交易日期
			}
			chkThirdDetailPo.setThirdSeq(chkSeq);// 第三方交易流水号
			// chkThirdDetailPo.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);//
			// 交易状态，默认交易成功
			chkThirdDetailPo.setTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);// 交易状态，默认交易成功
			daoService.insert(chkThirdDetailPo);
			logger.info("流水号" + (String) data.get("txSn") + "  交易金额:"+ txAmtount + "    机构应收的金额:" + institutionAmount
					+ "     支付平台应收的金额:"+ (String) data.get("paymentAmount") + "    服务费:"+(String) data.get("institutionFee"));
			logger.info("登记中金交易明细[{}]结束，商户手续费[{}]", new Object[] { chkSeq,
					chkThirdDetailPo.getFeeAmt() });
		}
	}

	public IDipperHandler<Message> getCpcnChkFileHandler() {
		return cpcnChkFileHandler;
	}

	public void setCpcnChkFileHandler(IDipperHandler<Message> cpcnChkFileHandler) {
		this.cpcnChkFileHandler = cpcnChkFileHandler;
	}

	// public static void main(String [] args){
	// List<HashMap> acountBill = new ArrayList<HashMap>();
	// List<HashMap> acountBill2 = new ArrayList<HashMap>();
	// acountBill.addAll(acountBill2);
	// System.out.println(acountBill.size());
	// }

}
