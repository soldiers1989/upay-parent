package com.upay.commons.http;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.exception.DipperException;
import com.pactera.dipper.exception.SysErrCode;
import com.pactera.dipper.utils.net.ExceptionUtils;
import com.pactera.dipper.utils.xml.XMLFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Guo on 16/7/27.
 * HTTP客户端(post方式)
 */
public class HttpPostClient {
    private final static Logger LOG = LoggerFactory.getLogger(HttpPostClient.class);

    private static final int OK = 200;

    /**
     * 目标地址
     */
    private String uri;
    /**
     * 连接超时时间
     */
    private int connectTimeout;
    /**
     * 读超时时间
     */
    private int soTimeout;

    public HttpPostClient(String uri, int connectTimeout, int soTimeout) {
        this.uri = uri;
        this.connectTimeout = connectTimeout;
        this.soTimeout = soTimeout;
    }

    public HttpPostClient(String uri) {
        this(uri, 10000, 30000);
    }

    public Message handle(Message m) throws Exception {
        LOG.debug("message={}", m.getChannel());

        LOG.debug("client[{}] uri[{}]", m.getChannel(), uri);

        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = createHttpclient();
            HttpPost httppost = new HttpPost(uri);

            byte[] bytes = setEntity(httppost, m);
            setHeader(httppost, m);

            RequestConfig reqCfg =
                    RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(soTimeout)
                            .build();
            httppost.setConfig(reqCfg);

            printRequestInfo(m, httppost, bytes);

            response = httpclient.execute(httppost);

            LOG.debug("client[{}] statusLine[{}]", m.getChannel(), response.getStatusLine());

            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if (OK == statusCode) {
                receiveHandle(resEntity, m);
            } else {
                receiveHandleNoOk(resEntity, m, response.getStatusLine());
                throw new DipperException(FaultFactory.create(String.valueOf(statusCode), ""));
            }
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            LOG.error("channel[{}] Exception", m.getChannel(), e);
            m.getFault().setCodeAll(SysErrCode.SYS003);
            m.getFault().setMsgAll(
                    ExceptionUtils.convertCN(e, m.getChannel(),
                            "渠道[".concat(m.getChannel()).concat("]").concat(SysErrCode.SYS003_MSG)));

        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                if (null != httpclient) {
                    httpclient.close();
                }
            } catch (IOException e) {
                LOG.error("", e);
            }

        }

        return m;
    }

    private void printRequestInfo(Message m, HttpPost httppost, byte[] bytes) throws Exception {
        LOG.info("client[{}] executing[{}] request:[{}]", m.getChannel(), httppost.getRequestLine(),
                new String(bytes, m.getCharset()));
    }

    private void receiveHandle(HttpEntity resEntity, Message m) throws Exception {
        byte[] bytes = EntityUtils.toByteArray(resEntity);
        LOG.info("client[{}] receive:[{}]", m.getChannel(), new String(bytes, m.getCharset()));
        LOG.debug("client[{}] receiveInfo format:[{}]", m.getChannel(),
                XMLFormatUtils.formatXML(new String(bytes, m.getCharset()), m.getCharset()));

        m.getTarget().setBody(bytes);
    }

    private void receiveHandleNoOk(HttpEntity resEntity, Message m, StatusLine statusLine) throws Exception {
        byte[] bytes = EntityUtils.toByteArray(resEntity);
        LOG.error("client[{}] [{}] receive:[{}]", m.getChannel(), statusLine,
                new String(bytes, m.getCharset()));
        LOG.debug("client[{}] [{}] receiveInfo format:[{}]", m.getChannel(), statusLine,
                XMLFormatUtils.formatXML(new String(bytes, m.getCharset()), m.getCharset()));

    }

    private CloseableHttpClient createHttpclient() throws Exception {
        return HttpClients.createDefault();
//        return createSSLInsecureClient();//测试时可使用此方法绕过SSL验证
    }

    protected byte[] setEntity(HttpPost httppost, Message m) {
        byte[] bytes = (byte[]) m.getTarget().getBodys();
        ByteArrayEntity reqentity = new ByteArrayEntity(bytes);
//        reqentity.setChunked(true);//消息是否分块发送
        httppost.setEntity(reqentity);
        return bytes;
    }

    protected void setHeader(HttpPost httppost, Message m) {
        httppost.setHeader("Content-Type", "text/xml;charset=".concat(m.getCharset()));
    }

    /**
     * 绕过SSL验证-测试时使用
     * @return CloseableHttpClient
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient()
            throws GeneralSecurityException {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] chain,
                                             String authType) throws CertificateException {
                        return true;
                    }
                }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext, new X509HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

            @Override
            public void verify(String host, SSLSocket ssl)
                    throws IOException {
            }

            @Override
            public void verify(String host, X509Certificate cert)
                    throws SSLException {
            }

            @Override
            public void verify(String host, String[] cns,
                               String[] subjectAlts) throws SSLException {
            }

        });
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}
