package com.upay.gateway.client.weixinopenid.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 公众平台通用接口工具类
 * 
 * @author liuyq
 * @date 2013-08-09
 */
public class WeixinUtil {
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);


    /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST）
     * @param outputStr
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static Map<String, Object> httpRequest(String requestUrl, String requestMethod, String outputStr) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        StringBuffer buffer = new StringBuffer();
        String protocol = null;
        try {
            URL url = new URL(requestUrl);
            // 通讯协议
            protocol = url.getProtocol();
            // 通讯URL
            HttpURLConnection httpUrlConn = null;

            if ("http".equals(protocol)) { // https通讯处理
                httpUrlConn = (HttpURLConnection) url.openConnection();
            } else if ("https".equals(protocol)) { // http通讯处理
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new MyX509TrustManager() };
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url.openConnection();
                httpsUrlConn.setSSLSocketFactory(ssf);

                httpUrlConn = httpsUrlConn;
            } else {
                throw new RuntimeException(protocol + "is not support");
            }

            httpUrlConn.setConnectTimeout(5 * 10000);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("GBK"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains("scope")) {// 处理scope格式不规范问题
                    str = str.substring(0, str.indexOf("scope") - 2) + "}";
                }
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            log.debug("微信返回结果==" + buffer.toString());
            returnMap = getReturnInfo(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("{} request error:{}", protocol, e);
        }
        return returnMap;
    }


    /**
     * 校验AccessToken
     * 
     * @param openid
     *            用户的唯一标识
     * @param accessToken
     *            accessToken
     */
    public static Map<String, Object> checkAccessToken(String accessToken, String openid, String jumpUrl) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String url =
                "https://api.weixin.qq.com/sns/auth?" + "access_token=" + accessToken + "&" + "openid="
                        + openid;

        url = jumpUrl + "?" + url;

        returnMap = WeixinUtil.httpRequest(url, "GET", null);
        String openId = null;
        if (null != returnMap) {
            if (returnMap.containsKey("errcode")) {
                log.error("errcode:" + returnMap.get("errcode"));
                log.error("errmsg:" + returnMap.get("errmsg"));
            } else {
                try {
                    openId = (String) returnMap.get("openid");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMap;
    }


    /**
     * 刷新AccessToken
     * 
     * @param appId
     *            公众号的唯一标识
     * @param refreshToken
     *            Token
     */
    public static Map<String, Object> refreshAccessToken(String refreshToken, String appId, String jumpUrl) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String url =
                "https://api.weixin.qq.com/sns/oauth2/refresh_token?" + "appid=" + appId + "&"
                        + "grant_type=refresh_token" + "&" + "refresh_token=" + refreshToken;

        url = jumpUrl + "?" + url;

        returnMap = WeixinUtil.httpRequest(url, "GET", null);
        String openId = null;
        if (null != returnMap) {
            if (returnMap.containsKey("errcode")) {
                log.error("errcode:" + returnMap.get("errcode"));
                log.error("errmsg:" + returnMap.get("errmsg"));
            } else {
                try {
                    openId = (String) returnMap.get("openid");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMap;
    }


    /**
     * 获取商户openid
     * 
     * @param code
     * 
     * @param appId
     *            公众号的唯一标识
     * @param appSecret
     *            公众号的appsecret
     */
    public static Map<String, Object> getOpenId(String code, String appId, String appSecret, String jumpUrl) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String url =
                "https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + appId + "&" + "secret="
                        + appSecret + "&" + "code=" + code + "&" + "grant_type=authorization_code";
        url = jumpUrl + "?" + url;
        returnMap = WeixinUtil.httpRequest(url, "GET", null);
        String openId = null;
        if (null != returnMap) {
            if (returnMap.containsKey("errcode")) {
                log.error("errcode:" + returnMap.get("errcode"));
                log.error("errmsg:" + returnMap.get("errmsg"));
            } else {
                try {
                    openId = (String) returnMap.get("openid");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return returnMap;
    }


    /**
     * 获取商户支付请求code的url地址
     * 
     * @param appId
     *            商户公众号
     * @param redirectUri
     *            回调地址
     */
    public static String getCodeUrl(String appId, String redirectUri, String state) {
        Map<Object, Object> returnMap = new HashMap<Object, Object>();
        String url =
                "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + appId + "&"
                        + "redirect_uri=" + redirectUri + "&" + "response_type=code&scope=snsapi_base&state="
                        + state + "#wechat_redirect";

        return url;
    }


    public static Map<String, Object> getReturnInfo(String str) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String[] str1 = str.replace("{", "").replace("}", "").split(",");
        for (int i = 0; i < str1.length; i++) {
            String str2 = str1[i].toString();
            String key = str2.substring(0, str2.indexOf(":")).replace("\"", "");
            String value = str2.substring(str2.indexOf(":") + 1, str2.length()).replace("\"", "");
            returnMap.put(key, value);
        }
        return returnMap;

    }


    public static void main(String args[]) throws ParseException {

        /*
         * // String path = //
         * "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6cba2be5d7b26f70&redirect_uri=http://y.abic.cn/WXPK/indexpk/startPk&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect"
         * ; System.out.println("开始获取用户openid");
         * System.out.println("获取授权code信息"); // String path = //
         * "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6cba2be5d7b26f70&redirect_uri=http://y.abic.cn/WXPK/indexpk/startPk&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect"
         * ; String path =
         * "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx9f95a7b0bf66e29f&secret=10678c0524a8eb5dd6e291fbbe68ac3f&code=021fd981bc7791f650839fcf3fd42a3i&grant_type=authorization_code"
         * ; //
         * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type
         * =refresh_token&refresh_token=REFRESH_TOKEN
         * System.out.println("请求URL：" + path); httpRequest(path, "GET", "");
         */
        // String appId = "wx288339422065bc01";
        // String code = "001ye9hl00toTm1IwWfl0ndSgl0ye9hF";
        // String appSecret = "b01763152a0c8796d0ebc59042051686";
        // Map<String, Object> returnMap = getOpenId(code, appId, appSecret);

        // System.out.println(returnMap.get("openid"));
        // System.out.println(returnMap.get("errcode"));
        // https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx3950cc559a1244f4&secret=c3c0b9011961fa81b669f7e9fa4114a5&code=001zxZ8h0GrTJC1CUS9h0mUW8h0zxZ8o&grant_type=authorization_code
        // System.out.println("====================================");
        // checkAccessToken("MynF9NhEntnXPEssld-exdlYFUshgWJut3dYse0qcYvnPeOwbc_DnHJkFnzYhEgZ6t5L45Q7fyUYu6tLX1gcllf0e6NT3GqYTRHO1ecmoLE",
        // "oik1kv0cF_QoXcW7gft_ZApdJbPg");
        /*
         * refreshAccessToken(
         * "rMfY4LTBz6b_PQegrOuZvawLkhBqylVxPHx2qk75LeaKceg-w5rt7ouTTA8gndSl1vDnU1Jo4n14zPJ8P4Gem7ZLxk_1Jj0_f1XyFQqK5xs"
         * , appId);
         */
    }
}