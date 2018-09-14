package com.upay.gateway.client.atalipay.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;


/**
 * 交易关闭
 *
 * 
 */
public class UC_AlipayCloseHandler extends DefaultAlipayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(UC_AlipayCloseHandler.class);


    @Override
    protected void setInitParam(Map<String, Object> body) {
    	body.put("_TRAN_CODE", "close");
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
