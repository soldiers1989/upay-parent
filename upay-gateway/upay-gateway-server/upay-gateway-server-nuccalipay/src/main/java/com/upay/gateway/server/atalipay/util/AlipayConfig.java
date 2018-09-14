package com.upay.gateway.server.atalipay.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.upay.gateway.server.atalipay.util.LoadCertUtil;


public class AlipayConfig {
    final static Logger log = LoggerFactory.getLogger(AlipayConfig.class);
    private static final String FILE_NAME = "atalipay.properties";
    public static Properties properties;
    public static String publicKey ;
    public static String privateKey;
    public static Map<String, AlipayClient> alipayClient = new HashMap<String, AlipayClient>();
    public static String url;
    public static String notifyUrl;


    public static void init() {
        alipayClient.clear();
        try {
            InputStream in = AlipayConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
            properties = new Properties();
            properties.load(in);
//            // 获取支付宝公钥
//            // 获取支付宝公钥结束
//            url = properties.getProperty("alipay.url");
//            notifyUrl = properties.getProperty("alipay.notifyUrl");
//            publicKey = properties.getProperty("alipay_public_key");
//            privateKey = properties.getProperty("private_key");
            
            url = properties.getProperty("atalipay.url");
            notifyUrl = properties.getProperty("atalipay.notifyUrl");
            
//            appId=properties.getProperty("atalipay.appId");
            String publicKeyPath = properties.getProperty("atpublicKey_path");
            
            String privateKeyPath = properties.getProperty("atprivateKey_path");
            String privateKeypassword=properties.getProperty("atprivateKey_password");
            
    		KeyStore keyStore = LoadCertUtil.loadKeyStore(privateKeyPath,privateKeypassword);
    		if (StringUtils.isNotBlank(privateKeyPath)) {
    			PrivateKey privateKeyObj = LoadCertUtil.getPriKey(keyStore,privateKeypassword);
    			privateKey=Base64.encodeBase64String(privateKeyObj.getEncoded());
    			privateKey=privateKey.trim();
    		
    		}
    		log.info("privateKey==========" + privateKey );
    		
    		
    		if (StringUtils.isNotBlank(publicKeyPath)) {
    			PublicKey publicKeyobj = LoadCertUtil.loadPubkey(publicKeyPath);
    			publicKey = Base64.encodeBase64String(publicKeyobj.getEncoded());
    			publicKey=publicKey.trim();
    		}
    		
    		log.info("publicKey==========" + publicKey );
            
        } catch (IOException e) {
            e.printStackTrace();
            log.error("配置文件" + FILE_NAME + "未找到");
        }
    }


    private AlipayClient createAlipayClient(String app_id) {
        AlipayClient client = new DefaultAlipayClient(getUrl(), app_id, privateKey, "json", "GBK", publicKey, AlipayConstants.SIGN_TYPE);
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

}
