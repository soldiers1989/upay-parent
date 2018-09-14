package com.upay.gateway.client.acpmer.service;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.po.gnr.GnrSafInfoPo.CoreStat;
import com.upay.commons.util.UnionUtils;

import java.util.Map;

/**
 * 银联无跳转支付授权查询
 * 
 * @author hry
 * 
 */
public class UnionPayTokenQueryClientDipperHandler extends AbstractAcpClientDipperHandler{
	//默认配置的是UTF-8
	public static String encoding = "UTF-8";


		@Override
		protected void setInitParam(Map<String, Object> init) {
			init.put("tranCode", "78");
			init.put("txnSubType", "02"); // 交易子类型 00-默认开通
			init.put("bizType", "000902"); // 业务类型 token支付
			init.put("channelType", "07"); // 渠道类型07-PC
			init.put("accessType", "0"); // 接入类型，商户接入固定填0，不需修改
			init.put("orderId", init.get("orderId")); // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
			init.put("txnTime", init.get("txnTime")); // 订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效

		}

		@Override
		protected void doErrorHandle(Map<String, Object> target, Fault fault) {
			target.put("coreStat", CoreStat.TIME_OUT);
			target.putAll(target);
			ExInfo.throwDipperEx(fault);
			// 超时

			/*
			 * target.put("orderStat", "2"); target.put("transStat", "8");
			 * target.put("routTransStat", "8"); target.put("errCode",
			 * fault.getOutCode()); target.put("errMsg", fault.getOutMsg());
			 * target.put("transSyncFlag", "2");
			 */// 如果出现超时的问题，则把同异步标识设置为异步（2），终止流程，在finally中更新订单，流水状态
		}

		//此时成功 代表交易查询受理成功  不代表交易成功
		@Override
		protected void doSuccessHandle(Map<String, Object> source,
				Map<String, Object> target, Fault fault) {
			target.putAll(source);
			//需要根据  origRespCode 判断交易状态
			UnionUtils.setRespCode(target,fault);
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
			UnionUtils.setRespCode(target,fault);
		}

}
