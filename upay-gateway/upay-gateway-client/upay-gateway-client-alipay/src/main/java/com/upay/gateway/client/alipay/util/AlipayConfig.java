package com.upay.gateway.client.alipay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;


public class AlipayConfig {
    final static Logger log = LoggerFactory.getLogger(AlipayConfig.class);
    private static final String FILE_NAME = "alipay.properties";
    private Properties properties;
    private String publicKey ;
    private String privateKey;
    private Map<String, AlipayClient> alipayClient = new HashMap<String, AlipayClient>();
    private String url;
    private String notifyUrl;
    
    //当走专线的时候，下载对账单数据需要替换支付方返回的域名
    private String alipayFileAddr;
    private String alipayFileAddrReplace;

    public void init() {
        alipayClient.clear();
        try {
            InputStream in = AlipayConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
            properties = new Properties();
            properties.load(in);
            // 获取支付宝公钥
            // 获取支付宝公钥结束
            url = properties.getProperty("alipay.url");
            notifyUrl = properties.getProperty("alipay.notifyUrl");
            publicKey = properties.getProperty("alipay_public_key");
            privateKey = properties.getProperty("private_key");
            
          //当走专线的时候，下载对账单数据需要替换支付方返回的域名
            this.setAlipayFileAddr(properties.getProperty("alipay.file.addr"));
            this.setAlipayFileAddrReplace(properties.getProperty("alipay.file.addrReplace"));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("配置文件" + FILE_NAME + "未找到");
        }
    }


    private AlipayClient createAlipayClient(String app_id) {
        AlipayClient client = new DefaultAlipayClient(getUrl(), app_id, privateKey, "json", "GBK", publicKey, AlipayConstants.SIGN_TYPE);
        log.info("-----请求支付宝的IP----："+getUrl());
        return client;
    }


    public Properties getProperties() {
        return properties;
    }


    /**
     * 根据商户号获取支付客户端对象
     * 
     * @param app_id
     * @return
     */
    public AlipayClient getAlipayClient(String app_id) {
        AlipayClient client = alipayClient.get(app_id);
        if (null == client) {
            client = createAlipayClient(app_id);
            alipayClient.put(app_id, client);
        }
        return client;
    }


    /**
     * 获取支付宝请求地址
     * 
     * @return
     */
    public String getUrl() {
        return url;
    }



    /**
     * 支付结果通知地址
     * 
     * @return
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }


	public String getAlipayFileAddr() {
		return alipayFileAddr;
	}


	public void setAlipayFileAddr(String alipayFileAddr) {
		this.alipayFileAddr = alipayFileAddr;
	}


	public String getAlipayFileAddrReplace() {
		return alipayFileAddrReplace;
	}


	public void setAlipayFileAddrReplace(String alipayFileAddrReplace) {
		this.alipayFileAddrReplace = alipayFileAddrReplace;
	}

}
