package com.upay.gateway.client.atalipay.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.bean.Fault;
import com.upay.commons.util.DateUtil;


/**
 * 交易撤销
 *
 * 
 */
public class DefaultAlipayClientHandler extends AbstractAlipayClientDipperHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultAlipayClientHandler.class);


    @Override
    public boolean isFailureThrow() {
        return true;// 失败抛异常
    }


    @Override
    public boolean isErrorThrow() {
        return true;// 超时抛异常
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
    	target.putAll(source);
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
    	target.putAll(source);
    }


    @Override
    protected void doErrorHandle(Map<String, Object> source, Fault fault) {
    }


    @Override
    protected void setInitParam(Map<String, Object> init) {

    }
}
