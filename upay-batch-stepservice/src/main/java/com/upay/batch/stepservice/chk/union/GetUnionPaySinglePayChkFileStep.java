package com.upay.batch.stepservice.chk.union;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.utils.common.UUIDGenerator;
import com.upay.batch.stepservice.chk.union.bean.ChkUnionFlowBean;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 银联对账文件解析
 * 
 * @author Pactera
 */
public class GetUnionPaySinglePayChkFileStep extends
		AbstractStepExecutor<ChkInfoPo, ChkUnionFlowBean> {

	private static final Logger log = LoggerFactory
			.getLogger(GetUnionPaySinglePayChkFileStep.class);
	private String unionpayChkPath;
	private String pattern;
	//代收商户号
	private String acpReceiveMerId;
	//代付商户号
	private String acpPayMerId;

	public String getAcpReceiveMerId() {
		return acpReceiveMerId;
	}

	public void setAcpReceiveMerId(String acpReceiveMerId) {
		this.acpReceiveMerId = acpReceiveMerId;
	}

	public String getAcpPayMerId() {
		return acpPayMerId;
	}

	public void setAcpPayMerId(String acpPayMerId) {
		this.acpPayMerId = acpPayMerId;
	}

	@Override
	public List<ChkInfoPo> getObjectList(BatchParams batchParams)
			throws BatchException {
		// 判断是否是指定日期执行
		if (batchParams.getParameter().containsKey("tranDate")) {
			Date tranDate = (Date) batchParams.getParameter().get("tranDate");
			Date preDate = (Date) batchParams.getParameter().get("preDate");
			batchParams.setTranDate(tranDate);
			batchParams.setPreDate(preDate);
		}

		insertChkInfo(batchParams, UUIDGenerator.randomUUID());
		List<ChkInfoPo> chkInfoList = new ArrayList<ChkInfoPo>();
		ChkInfoPo chkInfo = new ChkInfoPo();
		chkInfo.setBatchNo(batchParams.getBatchNo());
		chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
		chkInfoList = daoService.selectList(chkInfo);

		// 生产放开
		// String chkPath =
		// unionpayChkPath + DateUtil.format(batchParams.getPreDate(),
		// "yyyyMMdd") + File.separator;

		return chkInfoList;
	}

	@Override
	public int getTotalResult(BatchParams batchParams, ChkInfoPo chkInfo)
			throws BatchException {

		// 测试用
		String tmpUnionpayChkPath = unionpayChkPath + File.separator
				+ DateUtil.format(batchParams.getPreDate(), "yyyyMMdd")
				+ File.separator;
		log.info("------UnionPay pay chkPath:[{}]", unionpayChkPath);
		// String chkFileName = (String)
		// batchParams.getParameter().get("chkFileName");
		chkInfo = new ChkInfoPo();
		chkInfo.setBatchNo(batchParams.getBatchNo());
		chkInfo = this.daoService.selectOne(chkInfo);

		String chkFile = FileUtil.findFiles(tmpUnionpayChkPath, pattern);
		if (chkFile.equals("false")) {
			throw new BatchException("查找无银联对账文件" + tmpUnionpayChkPath);
		}
		// 设置文件证书完整路径 用于读取文件
		batchParams.getParameter().put("chkFileName", chkFile);
		String[] tempfile = chkFile.split(",");
		int totalResult = 0;
		// 获取银联对账文件根目录
		// String outPutDirectory = (String)
		// batchParams.getParameter().get("outPutZipDirectory");
		for (int i = 0; i < tempfile.length; i++) {
			// //获取银联对账文件绝对路径
			// outPutDirectory = outPutDirectory +
			// File.separator+chkInfo.getChkFile();
			// 取数据文件最大记录数
			totalResult = totalResult
					+ new Long(FileUtil.getFileRowCount(chkFile,
							ChkUnionFlowBean.class)).intValue();
		}
		return totalResult;
	}

	@Override
	public List<ChkUnionFlowBean> getDataList(BatchParams batchParams,
			int offset, int pageSize, ChkInfoPo chkInfo) throws BatchException {
		String chkFileName = (String) batchParams.getParameter().get(
				"chkFileName");
		String[] tempfile = chkFileName.split(",");
		List<ChkUnionFlowBean> dataList = new ArrayList<ChkUnionFlowBean>();
		for (int i = 0; i < tempfile.length; i++) {
			dataList = FileUtil.readFileToList(tempfile[i],
					ChkUnionFlowBean.class, offset, pageSize);
			log.debug("----------dataList.size[{}]", dataList.size());
			Iterator<ChkUnionFlowBean> cufit = dataList.iterator();
			int flag = 0;
			//04开头 主扫标识、94开头 被扫标识
			while (cufit.hasNext()) {
				ChkUnionFlowBean cfb = cufit.next();
				flag = cfb.getServiceInputType().substring(0, 2).indexOf("04");
				if (flag < 0) {
					flag = cfb.getServiceInputType().substring(0, 2).indexOf("94");
				}
				//使用商户区分是代收的  对账文件
				//todo:代付的对账文件
//				if (flag < 0||!cfb.getRecipientIDCode().equals("524530160110002")) {
//					cufit.remove();
//				}
				//筛选代收和代付 和 银联二维码的对账文件入库
				if (flag < 0 ||
					!cfb.getRecipientIDCode().equals(acpReceiveMerId)||
					!cfb.getRecipientIDCode().equals(acpPayMerId)) {
					cufit.remove();
				}

			}
			log.debug("----------dataList.size[{}]", dataList.size());
			// 分批取数据文件记录

			log.info("文件记录总数[{}]", dataList != null ? dataList.size() : 0);
		}
		return dataList;
	}

	@Override
	@SuppressWarnings("all")
	public void execute(BatchParams batchParams, int index,
			ChkUnionFlowBean bankInfo, ChkInfoPo chkInfo) throws BatchException {

		Date now = DateUtil.getPreDate(batchParams.getTranDate());
		String tempstr = DateUtil.format(now, "yyMMdd").substring(2,
				DateUtil.format(now, "yyMMdd").length());
		bankInfo.setTraceTime(tempstr
				+ bankInfo.getTraceTime().substring(4,
						bankInfo.getTraceTime().length()));

		log.info("execute==============================begin");
		// String falg = (String)
		// DipperParm.getParmByKey(DataBaseConstants_PAY.T_FALG);
		// String dateString = "";
		Date fdate = null;
		// if (!falg.equals("") && falg.equals("1")) {
		// dateString = DateUtil.format(new Date(), "yyyyMMddHHmmss");
		// } else {
		// dateString = DateUtil.format(SysInfoContext.getSysDate(),
		// "yyyyMMddHHmmss");
		// }
		String dateString = DateUtil.format(batchParams.getTranDate(),
				"yyyyMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dat = new SimpleDateFormat("yyyyMMdd");

		try {
			Date dt = sdf.parse(dateString);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(dt);
			rightNow.add(Calendar.DAY_OF_YEAR, -1);// 日期减一天
			dateString = sdf.format(rightNow.getTime());
			fdate = dat.parse(dateString.substring(0, 8));

			String chkSeq = bankInfo.getAcqinsCode() + "   "
					+ bankInfo.getHairinsCode() + "   " + bankInfo.getTraceNo()
					+ bankInfo.getTraceTime();
			String orderNo = bankInfo.getOrderNo();
			insertThird(bankInfo, fdate, orderNo, batchParams, chkSeq);

		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			log.info("", e);
			e.printStackTrace();
			throw new BatchException("银联代付对账文件插入数据库失败。");
		}
		log.info("execute==============================end");
	}

	/**
	 * 登记第三方表
	 * 
	 * @param data
	 * @param
	 * @param chkSeq
	 * @throws ParseException
	 */

	public void insertThird(ChkUnionFlowBean data, Date fdate, String orderNo,
			BatchParams batchParams, String chkSeq) throws ParseException {
		log.info("登记第三方表 start...");

		ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
		SimpleDateFormat chnlDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		fdate = sdf.parse(dateformat.format(batchParams.getTranDate())
				+ data.getTraceTime());

		String chnlDate = chnlDateFormat.format(sdf.parse(dateformat
				.format(batchParams.getPreDate()) + data.getTraceTime()));

		chkThirdDetailPo.setChnlSeq(orderNo);
		chkThirdDetailPo.setThirdSeq(chkSeq);
		ChkThirdDetailPo chkNo = this.daoService.selectOne(chkThirdDetailPo);
		if (chkNo == null) {

			if (data.getAcqinsCode() != null) {
				// 05591751代付
				chkThirdDetailPo.setMerId(data.getRecipientIDCode());
				chkThirdDetailPo.setChnlDate(chnlDateFormat.parse(chnlDate));
				chkThirdDetailPo.setChkBatchNo(batchParams.getBatchNo());
				chkThirdDetailPo.setChkDate(batchParams.getTranDate());
				chkThirdDetailPo
						.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
				BigDecimal txnAmt = new BigDecimal(data.getTxnAmt());
				chkThirdDetailPo.setTransAmt(txnAmt
						.divide(new BigDecimal("100")));// 交易金额
				BigDecimal stmtAmtno = new BigDecimal(
						data.getCardholderTransactionFee());
				chkThirdDetailPo.setFeeAmt(stmtAmtno.divide(new BigDecimal(
						"100")));// 收单机构应付手续费
				chkThirdDetailPo.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
				chkThirdDetailPo
						.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
			//	chkThirdDetailPo.setThirdDate(fdate);
				chkThirdDetailPo.setThirdDate(DateUtil.getPreDate(batchParams.getTranDate()));// 第三方交易日期
				chkThirdDetailPo
						.setTransStat(DataBaseConstants_PAY.T_PAY_CORE_TX_SUCCESS);
				chkThirdDetailPo.setPayeeAcct(data.getPayCardNo().trim());
				// chkThirdDetailPo.setRouteTransCode("12");
				daoService.insert(chkThirdDetailPo);
			}
			log.info("登记第三方表 end...");
		}
	}

	public String getUnionpayChkPath() {
		return unionpayChkPath;
	}

	public void setUnionpayChkPath(String unionpayChkPath) {
		this.unionpayChkPath = unionpayChkPath;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public static String getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		return sdf.format(date);
	}

	/**
	 * 插入对账文件的文件名
	 * 
	 * @param batchParams
	 * @param fileName
	 */
	public void insertChkInfo(BatchParams batchParams, String fileName) {
		ChkInfoPo chkInfo = new ChkInfoPo();
		chkInfo.setBatchNo(batchParams.getBatchNo());
		chkInfo.setChkFile(fileName);
		chkInfo = this.daoService.selectOne(chkInfo);
		if (chkInfo == null) {
			chkInfo = new ChkInfoPo();
			chkInfo.setBatchNo(batchParams.getBatchNo());
			chkInfo.setChkFile(fileName);
			chkInfo.setChkDate(batchParams.getTranDate());
			chkInfo.setBenchmarkFlag("03");
			chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
			chkInfo.setOrgType("002");// 资金通道
			chkInfo.setChkTime(new Date());
			chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
			chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);
			chkInfo.setFileType("0");// 下载
			chkInfo.setFileDownStat("3");// 下载成功
			daoService.insert(chkInfo);
		}
		log.info(
				"-------------DownloadUnionPayChkFileStep insert ChkInfo fileName[{}]",
				fileName);

	}
}
