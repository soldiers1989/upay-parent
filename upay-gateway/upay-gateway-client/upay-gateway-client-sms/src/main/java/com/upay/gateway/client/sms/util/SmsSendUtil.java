/**
 * 
 */
package com.upay.gateway.client.sms.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * 短信发送接口
 * 
 * @author zacks
 * 
 */
public class SmsSendUtil {

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor = null;

    private static final Logger log = LoggerFactory.getLogger(SmsSendUtil.class);


    /**
     * 发送mt短信
     * 
     * @return
     */
    public static void SendShortMessage(Map<String, List<String>> smsMap, ApplicationContext appCtx) {

        SendShortMessage(smsMap, appCtx, "VBank");

    }


    /**
     * 发送mt短信
     * 
     * @return
     */
    public static void SendShortMessage(Map<String, List<String>> smsMap, ApplicationContext appCtx,
            String userID) {

        Iterator<Map.Entry<String, List<String>>> it = smsMap.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entrySet = it.next();
            String mobile = entrySet.getKey();
            List<String> messageList = entrySet.getValue();
            for (int i = 0; i < messageList.size(); i++) {
                // 从本地的上下文管理对象中申请一个，上下文id
                int iContext = SmsContext.nextContextId();
                SmsBean sms = new SmsBean();
                sms.setMobilePhone(mobile);
                sms.setContent(messageList.get(i));

                // 暂定
                // sms.setUserId(userID);
                IMSLongConnectionAPIIEventAdapter.SMSCONTEXT.addCommand(iContext, sms);

                // if (null == threadPoolTaskExecutor) {
                synchronized (SmsSendUtil.class) {
                    if (null == threadPoolTaskExecutor) {
                        threadPoolTaskExecutor =
                                (ThreadPoolTaskExecutor) appCtx.getBean("threadPoolTaskExecutor");
                    }
                }
                // }

                threadPoolTaskExecutor.execute(new NewThread(sms, iContext));

                count++;
            }
        }
        log.info("短信发送总次数[{}]", count);

    }

    // public static void main(String[] args) throws ClientProtocolException,
    // IOException {
    // System.out.println(URLEncoder.encode(URLEncoder.encode("测试用")));
    // String uri =
    // IMSLongConnectionAPIIEventAdapter.SMSURL + "?MobilePhone=" +
    // "15800348143" + "&Content="
    // + "test";
    // System.out.println("uri : " + uri);
    // CloseableHttpClient httpclient = HttpClients.createDefault();
    // CloseableHttpResponse response = null;
    // HttpGet httpget = new HttpGet(uri);
    // httpget.setHeader("Content-Type",
    // "text/xml;charset=".concat(IMSLongConnectionAPIIEventAdapter.CHARSET));
    // RequestConfig reqCfg =
    // RequestConfig.custom().setConnectTimeout(IMSLongConnectionAPIIEventAdapter.CONNECTTIMEOUT)
    // .setSocketTimeout(IMSLongConnectionAPIIEventAdapter.SOTIMEOUT).build();
    //
    // httpget.setConfig(reqCfg);
    //
    // log.info("request:[{}]", httpget.getRequestLine());
    //
    // response = httpclient.execute(httpget);
    //
    // log.debug("request[{}] statusLine[{}]", httpget.getRequestLine(),
    // response.getStatusLine());
    //
    // HttpEntity resEntity = response.getEntity();
    //
    // int statusCode = response.getStatusLine().getStatusCode();
    // if (200 == statusCode) {
    // System.out.println("statusCode : " + statusCode);
    // } else {
    // System.out.println("statusCode : " + statusCode);
    // }
    //
    // }

}


class NewThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(NewThread.class);

    private SmsBean sms;
    private int iContext;


    public NewThread(SmsBean sms, int iContext2) {
        this.sms = sms;
        this.iContext = iContext2;
    }


    @Override
    public void run() {
        // 发送短消息
        String uriStr =
                IMSLongConnectionAPIIEventAdapter.SMSURL + "?MobilePhone=" + this.sms.getMobilePhone()
                        + "&Content=" + sms.getContent();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {

            URL url = new URL(uriStr);
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            log.debug("uri[{}]", uri);

            HttpGet httpget = new HttpGet(uri);
            httpget.setHeader("Content-Type",
                "text/xml;charset=".concat(IMSLongConnectionAPIIEventAdapter.CHARSET));
            RequestConfig reqCfg =
                    RequestConfig.custom()
                        .setConnectTimeout(IMSLongConnectionAPIIEventAdapter.CONNECTTIMEOUT)
                        .setSocketTimeout(IMSLongConnectionAPIIEventAdapter.SOTIMEOUT).build();

            httpget.setConfig(reqCfg);

            log.info("request:[{}]", httpget.getRequestLine());

            response = httpclient.execute(httpget);

            log.debug("request[{}] statusLine[{}]", httpget.getRequestLine(), response.getStatusLine());

            HttpEntity resEntity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                log.info("添加发送短信命令成功,电话号码：[{}], 发送信息是：[{}]", this.sms.getMobilePhone(), this.sms.getContent());
            } else {
                log.info("添加发送短信命令出现错误,电话号码：[{}], 发送信息是：[{}],错误代码是：[{}]", this.sms.getMobilePhone(),
                    this.sms.getContent(), statusCode);
                IMSLongConnectionAPIIEventAdapter.SMSCONTEXT.removeCommand(iContext);
            }
        } catch (ClientProtocolException e) {
            log.error("http连接异常 Exception[{}]", e);

        } catch (MalformedURLException e) {
            log.error("new URL([{}]) 失败  http连接异常 Exception[{}]", uriStr, e);
        } catch (IOException e) {
            log.error("URL 初始化失败 IOException[{}]", e);
        } catch (URISyntaxException e) {
            log.error("URL 初始化失败 URISyntaxException[{}]", e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("http response.close异常 Exception[{}]", e);
                }
            }
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    log.error("http httpclient.close异常 Exception[{}]", e);
                }
            }
        }
    }

}
