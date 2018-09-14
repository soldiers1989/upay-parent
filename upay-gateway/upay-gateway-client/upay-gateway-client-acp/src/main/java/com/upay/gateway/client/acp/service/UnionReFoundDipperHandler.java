/**
 *
 */
package com.upay.gateway.client.acp.service;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.gateway.client.acp.util.AcpToolUtil;

import java.util.Map;

/**
 * 银联之退货
 * 
 * @author liudan
 */
public class UnionReFoundDipperHandler extends AbstractAcpClientDipperHandler {
	private String tmpOrderNO;
	@Override
	protected void setInitParam(Map<String, Object> init) {
		LOG.info("=======银联二维码【退货】（UnionReFoundDipperHandler）============开始");
		tmpOrderNO=(String) init.get("orderNo");
		init.put("tranCode", "03");
		init.put("orderTime", AcpToolUtil.getCurrentTime());
	    init.put("voucherNum", init.get("oriRouteSeq"));
		init.put("currencyCode", "156");
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
		source.put("orderNo", tmpOrderNO);
		target.putAll(source);
		LOG.info("=======银联二维码【退货】（UnionReFoundDipperHandler）============结束");
		/*
		 * if ("1".equals(target.get("bankFlag"))) {//他行 target.put("routeDate",
		 * source.get("acpDate")); target.put("routeSeq", source.get("txId"));
		 * target.put("transStat", "8"); target.put("routTransStat", "8");
		 * target.put("orderStat", "2"); } else if
		 * ("0".equals(target.get("bankFlag"))) {//本行 target.put("routeDate",
		 * source.get("hostDate")); target.put("routeSeq",
		 * source.get("hostSerial")); target.put("transStat", "N");
		 * target.put("routTransStat", "N"); target.put("orderStat", "0"); }
		 */
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.put("coreStat", CoreStat.FAULT);
		/*
		 * target.put("transStat", "2"); target.put("routTransStat", "2");
		 */
		target.put("errCode", fault.getOutCode());
		target.put("errMsg", fault.getOutMsg());
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
