package com.upay.gateway.client.alipay.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;


/**
 * 商户信息修改
 *
 * 
 */
public class AlipaySubMchUpdateMerHandler extends DefaultAlipayClientHandler {

    private static final Logger log = LoggerFactory.getLogger(AlipaySubMchUpdateMerHandler.class);


    @Override
    protected void setInitParam(Map<String, Object> body) {
    	body.put("_TRAN_CODE", "subMchUpdate");
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
    	super.doErrorHandle(target, fault);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
    	super.doFailureHandle(source, target, fault);
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
    	super.doSuccessHandle(source, target, fault);
    }
    
    @Override
    public boolean isFailureThrow() {
        return true;// 失败抛异常
    }


    @Override
    public boolean isErrorThrow() {
        return true;// 超时抛异常
    }
}
