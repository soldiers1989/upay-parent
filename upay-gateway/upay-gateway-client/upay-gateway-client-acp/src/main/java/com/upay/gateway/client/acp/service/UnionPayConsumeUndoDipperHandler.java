
package com.upay.gateway.client.acp.service;

import com.pactera.dipper.core.bean.Fault;
import com.upay.gateway.client.acp.util.AcpToolUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 银联之消费撤销类
 * 撤销必须与原始消费在同一天（准确讲是前日23:00至本日23:00之间），且撤销必须是全金额撤销。。
 *
 * @author liudan
 */
public class UnionPayConsumeUndoDipperHandler extends AbstractAcpClientDipperHandler {
 

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
		LOG.info("=======银联二维码【消费撤销类】（UnionPayConsumeUndoDipperHandler）============开始");
      	init.put("tranCode","04");
    	init.put("orderNo", AcpToolUtil.getOrderId());
		init.put("orderTime", AcpToolUtil.getCurrentTime());
    }


    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        target.putAll(source);
		LOG.info("=======银联二维码【消费撤销类】（UnionPayConsumeUndoDipperHandler）============结束");
    }


    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {

    }


  
}
