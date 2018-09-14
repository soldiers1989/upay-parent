package com.upay.busi.pay.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.ReportOrderDetailQueryDto;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.commons.util.SignVerifyUtils;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;

/**
 * Created hry 报表交易汇总查询
 */
public class ReportOrderCountService extends
		AbstractDipperHandler<ReportOrderDetailQueryDto> {
	private static final Logger LOG = LoggerFactory
			.getLogger(ReportOrderDetailQueryDto.class);

	@Resource
	private IDaoService daoService;

	@Value(value = "${esb.uploadFilePath}")
	public String uploadFilePath;

	@Override
	public ReportOrderDetailQueryDto execute(
			ReportOrderDetailQueryDto reportOrderDetailQueryDto, Message message)
			throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String fileName = "";
		fileName = fileName + "FILE" + reportOrderDetailQueryDto.getBranchId()
				+ reportOrderDetailQueryDto.getTlrNo() + ".txt";
		Map<String, Object> map = new HashMap<String, Object>();
		LOG.info("=====报表交易汇总查询开始====");
		if (StringUtils.isBlank(reportOrderDetailQueryDto.getMerNo())) {
			String accNumber = reportOrderDetailQueryDto.getAccNumber();
			if (StringUtils.isBlank(accNumber)) {
				ExInfo.throwDipperEx(AppCodeDict.BISACC0000,
						"商户号 和 收款方账号不能同时为空");
			} else {
				MerAcctInfoPo merAcctInfo = new MerAcctInfoPo();
				merAcctInfo.setStlAcctNo(accNumber);
				List<MerAcctInfoPo> merAcctList = daoService
						.selectList(merAcctInfo);
				if (merAcctList != null && merAcctList.size() > 0) {
					MerAcctInfoPo merAcctInfoPo = merAcctList.get(0);
					reportOrderDetailQueryDto
							.setMerNo(merAcctInfoPo.getMerNo());
				} else {
					ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "账号："
							+ accNumber + "  找不到对应的商户,请检查!");
				}
			}

		}

		if (StringUtils.isBlank(reportOrderDetailQueryDto.getBeginTime())) {
			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "开始时间");
		}
		if (StringUtils.isBlank(reportOrderDetailQueryDto.getEndTime())) {
			ExInfo.throwDipperEx(AppCodeDict.CIPSYS0001, "结束时间");
		}
		Date etime = DateUtil.parse(reportOrderDetailQueryDto.getEndTime(),
				"yyyyMMdd");
		Date btime = DateUtil.parse(reportOrderDetailQueryDto.getBeginTime(),
				"yyyyMMdd");
		if (etime.getTime() - btime.getTime() < 0) {
			ExInfo.throwDipperEx(AppCodeDict.BISMER0006, "开始时间", "结束时间");
		}
		map.put("merNo", reportOrderDetailQueryDto.getMerNo());
		map.put("beginTime", reportOrderDetailQueryDto.getBeginTime());
		map.put("endTime", reportOrderDetailQueryDto.getEndTime());
		String tempstr = DateUtil.format(new Date(), "yyyyMMddHHmmssSS");
		LOG.info("=====tempstr====" + tempstr);
		if (tempstr.length() == 16) {
			tempstr = tempstr + 0;
		}
		reportOrderDetailQueryDto.setTranDt(tempstr.substring(0, 8));
		reportOrderDetailQueryDto.setTranTm(tempstr.substring(8, 17));
		List<Map<String, Object>> resultmap = daoService.selectList(
				PayOrderListPo.class.getName() + ".selectOrderCount", map);
		List<Map<String, String>> relist = new ArrayList<Map<String, String>>();
		if (resultmap.size() > 0) {
			for (Map<String, Object> tempmap : resultmap) {
				Map<String, String> tempmaprp = new HashMap<String, String>();
				tempmaprp.put("transDate", tempmap.get("TRANSDATE").toString());
				tempmaprp.put("trades", tempmap.get("TRADES").toString());
				tempmaprp.put("amount",
						String.format("%.2f ", tempmap.get("AMOUNT")));
				tempmaprp.put("chinaAmount",
						MoneyUtil.toChar(tempmap.get("AMOUNT").toString()));
				relist.add(tempmaprp);
			}
			reportOrderDetailQueryDto.setOrderCountList(relist);
			Map<String, Object> countmap = daoService.selectOne(
					PayOrderListPo.class.getName() + ".countSelectOrderCount",
					map);
			reportOrderDetailQueryDto.setSumTrade(countmap.get("SUMTRADE")
					.toString());
			BigDecimal bd = new BigDecimal(countmap.get("SUMAMOUNT").toString());
			reportOrderDetailQueryDto.setSumAmount(bd);
			String str = FileUtil.ESBFtpPut(
					buildReportForTxt(reportOrderDetailQueryDto, fileName), "/"
							+ fileName,"");
			if (str != null) {
				reportOrderDetailQueryDto.setFilePath(str.split("@")[0]);
				Fault fault = message.getFault();
				reportOrderDetailQueryDto.setFileFlg("2");
				fault.setCode(CommonConstants_GNR.RSP_CODE_SUCCESS);
			} else {
				reportOrderDetailQueryDto.setFilePath("");
				Fault fault = message.getFault();
				fault.setCode("0000000001");
			}
			return reportOrderDetailQueryDto;
		} else {
			reportOrderDetailQueryDto.setFilePath(FileUtil.ESBFtpPut(
					buildReportForTxt(reportOrderDetailQueryDto, fileName),
					"/" + fileName,"").split("@")[0]);
			return reportOrderDetailQueryDto;
		}
	}

	private String buildReportForTxt(
			ReportOrderDetailQueryDto reportOrderDetailQueryDto, String fileName)
			throws Exception {
		int intflay = 1;// 计数器
		boolean firstPage = true; // 是否是第一页
		List<Map<String, String>> detailList = reportOrderDetailQueryDto
				.getOrderCountList();
		String uploadFileAllPath = uploadFilePath + fileName;
		File file = new File(uploadFileAllPath);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		BufferedWriter bw = null;
		OutputStreamWriter outStream = null;
		try {

			outStream = new OutputStreamWriter(new FileOutputStream(file),
					"GBK");
			bw = new BufferedWriter(outStream);
			// 拼表格上面的内容
			bw.write("                               {dz48}云南红塔银行卷烟销售电子结算业务汇总回单{dz24}");
			bw.write("\n");
			bw.write("\n");
			bw.write("\n");
			bw.write("  交易机构:"
					+ String.format("%1$-16s", reportOrderDetailQueryDto
							.getBranchId() == null ? ""
							: reportOrderDetailQueryDto.getBranchId()));
			bw.write(" 交易日期:"
					+ String.format("%1$-32s", reportOrderDetailQueryDto
							.getTranDt() == null ? ""
							: reportOrderDetailQueryDto.getTranDt()));
			bw.write(" 交易时间:"
					+ String.format("%1$-20s", DateUtil.format(new Date(),"HH:mm:ss")));
			bw.write("\n");
			bw.write("  交易代码:" + String.format("%1$-16s", "794007"));
			bw.write(" 交易名称:"
					+ SignVerifyUtils.chineseStitchingSpace("烟草结算回单打印(卷烟销售资金)",
							32));
			bw.write(" 交易流水:"
					+ String.format("%1$-20s", reportOrderDetailQueryDto
							.getCnsmSysSeqNo() == null ? ""
							: reportOrderDetailQueryDto.getCnsmSysSeqNo()));
			bw.write("\n");
			bw.write("\n");
			bw.write("\n");

			bw.write("  收款账号:"
					+ String.format("%1$-40s", reportOrderDetailQueryDto
							.getAccNumber() == null ? ""
							: reportOrderDetailQueryDto.getAccNumber()));
			bw.write("  付款账号:" +  "1010001300119000001");
			bw.write("\n");
			 bw.write("  收款户名:"
						+ SignVerifyUtils.chineseStitchingSpace( reportOrderDetailQueryDto.getAccName(), 40));
			bw.write("  付款户名:"+"统一平台支付待清算资金");
			bw.write("\n");
			bw.write("  收款开户行:"
					+ SignVerifyUtils.chineseStitchingSpace(reportOrderDetailQueryDto.getAccOpeningBank(),40));
			bw.write("付款开户行:"+"云南红塔银行股份有限公司");
			bw.write("\n");
			bw.write("  收款子账号:"
					+ String.format("%1$-40s", reportOrderDetailQueryDto
							.getShroffAccNumber() == null ? ""
							: reportOrderDetailQueryDto.getShroffAccNumber()));
			bw.write("付款子账号:"
					+ String.format("%1$-25s", reportOrderDetailQueryDto
							.getShroffPayNumber() == null ? " "
							: reportOrderDetailQueryDto.getShroffPayNumber()));
			bw.write("\n");
			if (StringUtils.isBlank(reportOrderDetailQueryDto
					.getShroffAccName())) {
				bw.write("  收款子户名:" + String.format("%-40s", ""));
			} else {
				bw.write("  收款子户名:"
						+ SignVerifyUtils.chineseStitchingSpace(
								reportOrderDetailQueryDto.getShroffAccName() == null ? ""
										: reportOrderDetailQueryDto
												.getShroffAccName(), 40));
			}
			bw.write("付款子户名:"
					+ String.format("%1$-25s", reportOrderDetailQueryDto
							.getShroffPayName() == null ? " "
							: reportOrderDetailQueryDto.getShroffPayName()));
			bw.write("\n");
			bw.write("\n");
			bw.write("\n");

			bw.write("  交易渠道:"
					+ SignVerifyUtils.chineseStitchingSpace("网上结算", 32));
			bw.write("\n");
			bw.write("  开始时间:" + reportOrderDetailQueryDto.getBeginTime());
			bw.write("\n");
			bw.write("  结束时间:" + reportOrderDetailQueryDto.getEndTime());

			// 拼表格
			bw.write("\n");
			bw.write("  ┌───────┬───────────┬────────────────┬────────────────┐");
			// bw.write("┌──────────────┬──────────────────────┬────────────────────────────────┬────────────────────────────────┐");
			bw.write("\n");
			bw.write("  │");
			bw.write("   交易日期   ");
			bw.write("│");
			bw.write("     笔数(笔)         ");
			bw.write("│");
			bw.write("       交易金额小写(元)         ");
			bw.write("│");
			bw.write("       交易金额大写(元)         ");
			bw.write("│");
			bw.write("\n");
			bw.write("  ├───────┼───────────┼────────────────┼────────────────┤");
			// bw.write("├──────────────┼──────────────────────┼────────────────────────────────┼────────────────────────────────┤");
			if (detailList != null) {
				for (int i = 0; i < detailList.size(); i++) {
					bw.write("\n");
					bw.write("  │");
					bw.write(String.format("%1$-14s",
							detailList.get(i).get("transDate")));
					bw.write("│");
					bw.write(String.format("%1$-22s",
							detailList.get(i).get("trades")));
					bw.write("│");
					bw.write(String.format("%1$-32s",
							detailList.get(i).get("amount")));
					bw.write("│");
					bw.write(SignVerifyUtils.chineseStitchingSpace(detailList
							.get(i).get("chinaAmount").toString(), 32));
					bw.write("│");
					bw.write("\n");
					bw.write("  ├───────┼───────────┼────────────────┼────────────────┤");
					// bw.write("├──────────────┼──────────────────────┼────────────────────────────────┼────────────────────────────────┤");
					if (firstPage && intflay == 21) {
						bw.write("{hy}");
						firstPage = false;
						intflay = 1;
					}
					if (!firstPage && intflay == 32) {
						bw.write("{hy}");
						intflay = 1;
					}

					if (i == detailList.size() - 1) {
						bw.write("\n");
						bw.write("  │");
						bw.write("汇总:         ");
						bw.write("│");
						bw.write(String.format("%1$-22s",
								reportOrderDetailQueryDto.getSumTrade()));
						bw.write("│");
						bw.write(String.format("%1$-32s",
								reportOrderDetailQueryDto.getSumAmount()));
						bw.write("│");
						bw.write(SignVerifyUtils.chineseStitchingSpace(
								MoneyUtil.toChar(reportOrderDetailQueryDto
										.getSumAmount().toString()), 32));
						bw.write("│");
						bw.write("\n");
						bw.write("  └───────┴───────────┴────────────────┴────────────────┘");
						// bw.write("└──────────────┴──────────────────────┴────────────────────────────────┴────────────────────────────────┘");
					}
					intflay++;
				}
			} else {
				bw.write("\n");
				// bw.write("├──────────────┼──────────────────────┼────────────────────────────────┼────────────────────────────────┤");
				bw.write("  ├───────┼───────────┼────────────────┼────────────────┤");
				bw.write("\n");
				bw.write("  │汇总:         ");
				bw.write("│");
				bw.write(String.format("%1$-22s", ""));
				bw.write("│");
				bw.write(String.format("%1$-32s", ""));
				bw.write(" │");
				bw.write(String.format("%1$-32s", ""));
				bw.write("│");
				bw.write("\n");
				bw.write("  └───────┴───────────┴────────────────┴────────────────┘");
				// bw.write("└──────────────┴──────────────────────┴────────────────────────────────┴────────────────────────────────┘");
			}

			// 拼表格下面的内容
			bw.write("\n");
			bw.write("\n");
			bw.write("\n");
			bw.write("  事后监督:                   ");
			bw.write(" 授权员:                      ");
			bw.write(" 复核员:                     ");
			bw.write(" 操作员:" + reportOrderDetailQueryDto.getTlrNo());

			bw.flush();// 刷新内存，将内存中的数据立刻写出。
			bw.close();
			return uploadFileAllPath;
		} catch (IOException e) {
			e.printStackTrace();
			bw.flush();// 刷新内存，将内存中的数据立刻写出。
			bw.close();
			return "";
		}

	}

}
