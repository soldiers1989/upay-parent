package com.upay.gateway.client.acpmer.service;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.UnionUtils;
import com.upay.gateway.client.acpmer.util.AcpToolUtil;

import java.util.Map;

/**
 * 银联无跳转支付授权删除
 * 
 * @author hry
 * 
 */
public class UnionPayTokenDeleteClientDipperHandler extends AbstractAcpClientDipperHandler {
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";

	private String trId;

	public String getTrId() {
		return trId;
	}

	public void setTrId(String trId) {
		this.trId = trId;
	}


		@Override
		protected void setInitParam(Map<String, Object> init) {
			init.put("tranCode", "74");
			init.put("txnSubType", "01"); // 交易子类型 00-默认开通
			init.put("bizType", "000902"); // 业务类型 token支付
			init.put("channelType", "07"); // 渠道类型07-PC
			init.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			init.put("orderId", AcpToolUtil.getOrderId()); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			init.put("txnTime", AcpToolUtil.getCurrentTime()); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			init.put("tokenPayData", "{token="+init.get("token")+"&trId="+trId+"}");
		}

		@Override
		protected void doErrorHandle(Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.TIME_OUT);
			target.putAll(target);
			// 超时

			/*
			 * target.put("orderStat", "2"); target.put("transStat", "8");
			 * target.put("routTransStat", "8"); target.put("errCode",
			 * fault.getOutCode()); target.put("errMsg", fault.getOutMsg());
			 * target.put("transSyncFlag", "2");
			 */// 如果出现超时的问题，则把同异步标识设置为异步（2），终止流程，在finally中更新订单，流水状态
			ExInfo.throwDipperEx(fault);		}

		@Override
		protected void doSuccessHandle(Map<String, Object> source,
				Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.SUCESS);
			target.putAll(source);
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
            target.putAll(source);
			UnionUtils.setRespCode(target,fault);
		}

}
