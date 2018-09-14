package com.upay.gateway.client.alipay.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjianfeng
 * @since 2017/07/12 01:47
 */
public class TransSignConfig {

    /**
     * 交易签名参数配置
     * @return
     */
    public Map<String, Map<String, String>> getTransSignConfig() {
        return transSignConfig;
    }

    public void setTransSignConfig(Map<String, Map<String, String>> transSignConfig) {
        this.transSignConfig = transSignConfig;
    }

    Map<String, Map<String, String>> transSignConfig = new HashMap<String, Map<String, String>>();
}
