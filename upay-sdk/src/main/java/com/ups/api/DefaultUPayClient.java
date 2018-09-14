package com.ups.api;

import com.ups.api.internal.util.MD5Utils;
import com.ups.api.internal.util.WebUtils;
import com.ups.api.internal.util.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求客户端默认实现
 *
 * Created by Guo on 2016/12/11.
 */
public class DefaultUPayClient implements UPayClient {
    private String serverUrl;
    private String privateKey;

    private String charset;

    private int    connectTimeout = 3000;
    private int    readTimeout    = 15000;
    private static final String KEY_NAME = "key";
    private static final String SIGN_NAME = "sign";

    /**
     * 构造方法
     * @param serverUrl 服务端请求地址
     * @param privateKey 签名密钥
     * @param charset 编码（默认UTF-8）
     */
    public DefaultUPayClient(String serverUrl, String privateKey, String charset) {
        this.serverUrl = serverUrl;
        this.privateKey = privateKey;
        this.charset = charset;
    }

    /**
     * 后台请求
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> execute(Map<String, String> request) throws Exception {
        String signSource = MD5Utils.getSignSource(request, KEY_NAME, privateKey);
        String sign = MD5Utils.md5Hex(signSource, charset);
        request.put(SIGN_NAME, sign);
        String rsp = "";
        try {
            rsp = WebUtils.doPost(serverUrl, request, charset, connectTimeout, readTimeout);
        } catch (IOException e) {
            throw e;
        }
        System.out.println(rsp);
        //解析
        Element root = XmlUtils.getRootElementFromString(rsp);
        
        NodeList nl = root.getChildNodes();
        Map<String, String> rspMap = new HashMap<>();
        for (int i = 0; i < nl.getLength(); i ++) {
            Node node = nl.item(i);
            String key = node.getNodeName();
            String value = node.getTextContent();
            rspMap.put(key, value);
//            System.out.println("key====="+key+"          value======"+value);
        }

        
        //验签
        String rspSignSource = MD5Utils.getSignSource(rspMap, KEY_NAME, privateKey);
        
        
        System.out.println(rspSignSource+"  ==============rspSignSource");
        
        String cSign = MD5Utils.md5Hex(rspSignSource, charset);
//        String rspSign = XmlUtils.getElementValue(root, "sign");
        String rspSign = rspMap.get("sign");
        
        System.out.println(cSign+"  ==============cSign");
        System.out.println(rspSign+"  ==============rspSign");
        if (!cSign.equals(rspSign)) {
            throw new Exception("验签失败!");
        }
        System.out.println(rspMap);
        return rspMap;
    }

    /**
     * 前台请求（页面跳转到收银台）
     * @param request
     * @throws Exception
     */
    @Override
    public String pageExecute(Map<String, String> request) throws Exception {
        String signSource = MD5Utils.getSignSource(request, KEY_NAME, privateKey);
        System.out.println("signSource::::"+signSource);
        String sign = MD5Utils.md5Hex(signSource, charset);
        System.out.println("sign::::"+signSource);
        request.put(SIGN_NAME, sign);
        String rsp = "";
        try {
            rsp = WebUtils.doPost(serverUrl, request, charset, connectTimeout, readTimeout);
        } catch (IOException e) {
            throw e;
        }
        return rsp;
    }

}
