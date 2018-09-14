/**
 *
 */
package com.upay.gateway.client.acp.service;

import com.pactera.dipper.core.bean.Fault;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 银联之银联冲正
 * 冲正必须与原始消费在同一天（准确讲是昨日23:00至本日23:00之间）。
 * 冲正交易，仅用于超时无应答等异常场景，只有发生支付系统超时或者支付结果未知时可调用冲正，
 * 其他正常支付的订单如果需要实现相通功能，请调用消费撤销或者退货。
 *
 * @author liudan
 */
public class UnionPayWashedDipperHandler extends AbstractAcpClientDipperHandler {
    

    // 超时不抛异常
    @Override
    public boolean isErrorThrow() {
        return false;
    }


    // 失败抛异常
    @Override
    public boolean isFailureThrow() {
        return true;
    }


    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {

    }


    @Override
    protected void setInitParam(Map<String, Object> init) {
		LOG.info("=======银联二维码【冲正】（UnionPayWashedDipperHandler）============开始");
    	init.put("tranCode","05");
    	init.put("reqType", "0320000903");

    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        target.putAll(source);
		LOG.info("=======银联二维码【冲正】（UnionPayWashedDipperHandler）============结束");
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {

    }

 

}
