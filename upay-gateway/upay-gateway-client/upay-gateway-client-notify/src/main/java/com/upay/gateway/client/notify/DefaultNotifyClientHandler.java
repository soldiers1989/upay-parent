package com.upay.gateway.client.notify;

import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.presys.cp.client.AbstractClientDipperHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 支付成功后通知商户通讯客户端
 * Created by Guo on 2016/10/21.
 */
public class DefaultNotifyClientHandler extends AbstractClientDipperHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultNotifyClientHandler.class);

    @Override
    protected void doErrorHandle(Map<String, Object> target, Fault fault) {
        LOG.info("支付通知失败，错误码：{}，错误信息：{}", fault.getOutCode(), fault.getOutMsg());

    }

    @Override
    protected void setInitParam(Map<String, Object> init) {

    }

    @Override
    protected void doSuccessHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        LOG.info("支付通知成功");
        target.putAll(source);
    }

    @Override
    protected void doFailureHandle(Map<String, Object> source, Map<String, Object> target, Fault fault) {
        LOG.info("支付通知失败，错误码：{}，错误信息：{}", fault.getOutCode(), fault.getOutMsg());
        target.putAll(source);
    }
}
