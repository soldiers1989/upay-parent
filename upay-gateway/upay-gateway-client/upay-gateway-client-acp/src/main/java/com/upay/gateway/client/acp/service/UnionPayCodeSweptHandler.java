/**
 * 
 */
package com.upay.gateway.client.acp.service;

import java.util.Map;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.gateway.client.acp.util.AcpToolUtil;

/**
 * 银联二维码被扫
 * 
 * @author hry
 * 
 */
public class UnionPayCodeSweptHandler extends AbstractAcpClientDipperHandler {
    private String merId;

	@Override
	protected void setInitParam(Map<String, Object> init) {
		LOG.info("=======银联二维码【被扫消费】（UnionPayCodeSweptHandler）============开始");
      	init.put("tranCode","02");
      	init.put("merId",init.get("qrMerId").toString());
		init.put("orderTime", AcpToolUtil.getCurrentTime());
		init.put("reqType", "0310000903");
		init.put("termId", "49000002");//商户或收单机构分配，与8583消费报文的F41等效。
		init.put("areaInfo", "1562900");
		init.put("merName", init.get("primaryMerName").toString());
		init.put("merCatCode", "5811");
		init.put("orderType", "10");//订单类型：10-消费（一般消费）  11-消费（限定非贷记帐户支付的消费，例如购买理财产品） 12-小微商户收款
		init.put("currencyCode", "156");
		init.put("invoiceSt", "1");
	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		target.put("coreStat", CoreStat.TIME_OUT);
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.putAll(source);
		LOG.info("=======银联二维码【被扫消费】（UnionPayCodeSweptHandler）============开始");
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		target.put("coreStat", CoreStat.FAULT);
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
	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

}
