package com.upay.gateway.client.weixin;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.Message;
import com.pactera.dipper.presys.cp.http.handler.AbstractHttpClientCommHandler;
import com.pactera.dipper.utils.xml.XMLFormatUtils;


/**
 * ESB通讯
 * 
 * @author Hing
 * @since 2014年11月5日
 */
public class WeixinHttpsClientCommHandler extends AbstractHttpClientCommHandler {
    private final static Logger LOG = LoggerFactory.getLogger(WeixinHttpsClientCommHandler.class);

    private KeyStore trustStore;

    private String certPath;

    private String mchId;


    @Override
    protected void receiveHandle(HttpEntity resEntity, Message m) throws Exception {
        byte[] bytes = EntityUtils.toByteArray(resEntity);
        LOG.info("client[{}] receive:[{}]", m.getChannel(), new String(bytes, m.getCharset()));
        LOG.debug("client[{}] receiveInfo format:[{}]", m.getChannel(),
            XMLFormatUtils.formatXML(new String(bytes, m.getCharset()), m.getCharset()));

        m.getTarget().setBody(bytes);
    }


    protected void receiveHandleNoOk(HttpEntity resEntity, Message m, StatusLine statusLine) throws Exception {
        byte[] bytes = EntityUtils.toByteArray(resEntity);
        LOG.error("client[{}] [{}] receive:[{}]", m.getChannel(), statusLine,
            new String(bytes, m.getCharset()));
        LOG.debug("client[{}] [{}] receiveInfo format:[{}]", m.getChannel(), statusLine,
            XMLFormatUtils.formatXML(new String(bytes, m.getCharset()), m.getCharset()));

    }


    @Override
    protected byte[] setEntity(HttpPost httppost, Message m) {
        byte[] bytes = (byte[]) m.getTarget().getBodys();
        ByteArrayEntity reqentity = new ByteArrayEntity(bytes);
        reqentity.setChunked(true);
        httppost.setEntity(reqentity);
        return bytes;
    }


    @Override
    protected void setHeader(HttpPost httppost, Message m) {
        httppost.setHeader("Content-Type", "text/xml;charset=".concat(m.getCharset()));
    }


    @Override
    protected CloseableHttpClient createHttpclient(String uri) throws Exception {

        CloseableHttpClient chc = null;
        if (uri.indexOf(HTTPS) >= 0) {
            SSLContext sslcontext =
                    SSLContexts.custom().loadKeyMaterial(trustStore, mchId.toCharArray()).build();
            // 指定TLS版本
            SSLConnectionSocketFactory sslsf =
                    new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            chc = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } else {
            chc = HttpClients.createDefault();
        }
        return chc;
    }


    public void loadCert() throws Exception {
        //指定读取证书格式为PKCS12
        trustStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(certPath));
        try {
            //指定PKCS12的密码(商户ID)
            trustStore.load(instream, mchId.toCharArray());
        } finally {
            instream.close();
        }
    }

//    public static void main(String[] args) throws Exception {
//        //指定读取证书格式为PKCS12
//        KeyStore trustStore = KeyStore.getInstance("PKCS12");
//        //读取本机存放的PKCS12证书文件
//        FileInputStream fis = new FileInputStream(new File("/Users/Guo/IdeaProjects/upay/apiclient_cert.p12"));
//        try {
//            //指定PKCS12的密码(商户ID)
//            trustStore.load(fis, "1900008731".toCharArray());
//        } finally {
//            fis.close();
//        }
//    }

    public String getCertPath() {
        return certPath;
    }


    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }


    public String getMchId() {
        return mchId;
    }


    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

}
