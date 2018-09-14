package com.upay.gateway.client.atalipay.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;

/**
 * 
 * 条码支付
 * 
 * 
 */
public class UC_AlipayPayHandler extends DefaultAlipayClientHandler {

	private static final Logger log = LoggerFactory
			.getLogger(UC_AlipayPayHandler.class);

	@Override
	protected void setInitParam(Map<String, Object> body) {
		body.put("_TRAN_CODE", "pay");
	}

	@Override
	protected void doErrorHandle(Map<String, Object> target, Fault fault) {
		super.doErrorHandle(target, fault);
	}

	@Override
	protected void doFailureHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		super.doFailureHandle(source, target, fault);
	}

	@Override
	protected void doSuccessHandle(Map<String, Object> source,
			Map<String, Object> target, Fault fault) {
		super.doSuccessHandle(source, target, fault);
	}
	@Override
    public boolean isFailureThrow() {
        return true;// 失败抛异常
    }
}
