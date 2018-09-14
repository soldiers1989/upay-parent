/**
 * 
 */
package com.upay.gateway.client.acp.service;

import java.util.Date;
import java.util.Map;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;


/**
 * 银联交易状态查询
 * 
 * @author zhanggr
 * 
 */
public class UnionPayTranStatQuery extends AbstractAcpClientDipperHandler {
	private String temptxnAmt;
	private String unionOrder;
	@Override
	protected void setInitParam(Map<String, Object> init) {
		LOG.info("=======银联二维码【交易状态查询】（UnionPayTranStatQuery）============开始");
		temptxnAmt=(String) init.get("txnAmt");
		unionOrder=(String) init.get("orderNo");
		init.put("tranCode", "00");
		init.put("orderNo", init.get("transSubSeq"));
//		init.put("reqType", "0540000903");主扫
//		init.put("reqType", "0350000903");被扫
	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		target.put("coreStat", CoreStat.TIME_OUT);
		// 超时

		/*
		 * target.put("orderStat", "2"); target.put("transStat", "8");
		 * target.put("routTransStat", "8"); target.put("errCode",
		 * fault.getOutCode()); target.put("errMsg", fault.getOutMsg());
		 * target.put("transSyncFlag", "2");
		 */// 如果出现超时的问题，则把同异步标识设置为异步（2），终止流程，在finally中更新订单，流水状态
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		source.put("txnAmt", temptxnAmt);
		source.put("orderNo", unionOrder);
		target.putAll(source);
		LOG.info("=======银联二维码【交易状态查询】（UnionPayTranStatQuery）============结束");
		
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.putAll(source);
	}

	@Override
	public boolean isErrorThrow() {
		return true;// 超时抛异常
	}

	@Override
	public boolean isFailureThrow() {
		// return false;
		return true;// 失败抛异常
	}

    
}
