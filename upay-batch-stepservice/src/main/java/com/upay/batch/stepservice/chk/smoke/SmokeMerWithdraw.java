/**
 * 
 */
package com.upay.batch.stepservice.chk.smoke;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.Message;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.SmokeStlPo;

/**
 * 商户提现到待清算账户为转账做准备
 * 
 * @author lb
 * 
 */
public class SmokeMerWithdraw extends AbstractStepExecutor<Object, Object> {
	private final static Logger logger = LoggerFactory
			.getLogger(SmokeMerWithdraw.class);
//	@Resource
//	private IDaoService daoService;
	private BatchCommon batchCommon;


	@Override
	public void execute(BatchParams batchParams, int index, Object data,
			Object object) throws BatchException {
		String transDate = DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
		logger.info("商户提现开始 。。。。。。。。。。");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("stlStartDate",transDate);
		map.put("stlEndDate", DateUtil.format(DateUtil.add(
				batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1),DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		List<SmokeStlPo> queryList = daoService.selectList(
				SmokeStlPo.class.getName() + ".selectSmokeStlByTransDate", map);

		if (null != queryList && queryList.size() > 0) {
			// 为本次商户转账扣款 从资金池账户口 扣款到了 核心待清算账户
			for (SmokeStlPo smokeStlPo : queryList) {
				String payerMerNo = smokeStlPo.getMerNo();
				BigDecimal amt = smokeStlPo.getTransAmt();
				logger.info("一级商户批量转账:::::商户号=" + payerMerNo+ ":::::::::::::::::" + amt);

				Map<String, Object> parmMap = new HashMap<String, Object>();
				parmMap.put("transAmt", amt);
				parmMap.put("transCode", "SI_ACC1006");
				parmMap.put("merNo", payerMerNo);
				parmMap.put("operatorType", "0");
				Message message = batchCommon.postAcc1006(parmMap);
				String code = message.getFault().getCode();
				if (!CommonConstants_GNR.SERVICE_STAT.equals(code)) {// 成功
					new BatchException("一级商户提现到待清算账户失败。商户号=" + payerMerNo
							+ ":::::::::::::::::" + amt);
				}
			}
		} else {
			logger.info("日期::" + transDate + ":::::没有需要转账的烟草商户");
		}
		logger.info("商户提现结束 。。。。。。。。。。");
	}

	public void setBatchCommon(BatchCommon batchCommon) {
		this.batchCommon = batchCommon;
	}

}
