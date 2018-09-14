package com.upay.commons.util;

import com.upay.commons.constants.AlipayConstants;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 加签验签工具类型。
 * @author zhangjianfeng
 * @since 2017/07/18 11:08
 */
public class SignVerifyUtils {

    /** 日志记录。 */
    private static final Logger LOGGER = LoggerFactory.getLogger(SignVerifyUtils.class);

    /** key store type : JKS */
    public static final String KEY_STORE_TYPE_JKS = "JKS";

    /** key store type : PKCS8 */
    public static final String KEY_STORE_TYPE_PKCS8 = "PKCS8";

    /** key store type : PKCS12 */
    public static final String KEY_STORE_TYPE_PKCS12 = "PKCS12";

    /**
     * 获取私钥
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromPKCS8(String signPrivateKeyPath, String algorithm) {
        if (StringUtils.isBlank(signPrivateKeyPath) || StringUtils.isBlank(algorithm)) {
            throw new IllegalArgumentException("签名私钥未配置");
        }
        PrivateKey signPrivateKey = null;
        FileInputStream fis = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

            fis = new FileInputStream(signPrivateKeyPath);
            byte[] bytes = new byte[1024];
            byte[] encodedKey = new byte[0];
            int readLen = 0;
            while ((readLen = fis.read(bytes)) != -1) {
                encodedKey = ArrayUtils.addAll(encodedKey, ArrayUtils.subarray(bytes, 0, readLen));
            }

            encodedKey = Base64.decodeBase64(encodedKey);
            signPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (Exception e) {
            LOGGER.error("加载签名私钥异常", e);
            throw new IllegalArgumentException("加载签名私钥异常", e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("关闭签名私钥文件流异常", e);
                }
            }
        }
        return signPrivateKey;
    }

    /**
     * 获取私钥
     * @throws Exception
     */
    public static PrivateKey getPrivateKeyFromPKCS8String(String base64KeyString, String algorithm) {
        if (StringUtils.isBlank(base64KeyString) || StringUtils.isBlank(algorithm)) {
            throw new IllegalArgumentException("签名私钥未配置");
        }
        PrivateKey signPrivateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = Base64.decodeBase64(base64KeyString.getBytes());
            signPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (Exception e) {
            LOGGER.error("加载签名私钥异常", e);
            throw new IllegalArgumentException("加载签名私钥异常", e);
        }
        return signPrivateKey;
    }

    /**
     * 从JKS密钥库文件中获取私钥
     * @return
     */
    public static PrivateKey getPrivateKeyFromJKS(String signPrivateKeyPath, String keyStorePwd, String keyPwd) {
        if (StringUtils.isBlank(signPrivateKeyPath)) {
            throw new IllegalArgumentException("签名私钥未配置");
        }
        PrivateKey signPrivateKey = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(signPrivateKeyPath);
            KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE_JKS);
            ks.load(fis, keyStorePwd.toCharArray());
            Enumeration<String> alias = ks.aliases();
            signPrivateKey = (PrivateKey) ks.getKey(alias.nextElement(), keyPwd.toCharArray());
        } catch (Exception e) {
            LOGGER.error("加载签名私钥异常", e);
            throw new IllegalArgumentException("加载签名私钥异常", e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("关闭签名私钥文件流异常", e);
                }
            }
        }
        return signPrivateKey;
    }

    /**
     * 从PCKS12密钥库文件中获取私钥
     * @return
     */
    public static PrivateKey getPrivateKeyFromPKCS12(String signPrivateKeyPath, String keyStorePwd, String keyPwd) {
        if (StringUtils.isBlank(signPrivateKeyPath)) {
            throw new IllegalArgumentException("签名私钥未配置");
        }
        PrivateKey signPrivateKey = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(signPrivateKeyPath);
            KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE_PKCS12);
            ks.load(fis, keyStorePwd.toCharArray());
            Enumeration<String> alias = ks.aliases();
            signPrivateKey = (PrivateKey) ks.getKey(alias.nextElement(), keyPwd.toCharArray());
        } catch (Exception e) {
            LOGGER.error("加载签名私钥异常", e);
            throw new IllegalArgumentException("加载签名私钥异常", e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("关闭签名私钥文件流异常", e);
                }
            }
        }
        return signPrivateKey;
    }

    /**
     * 获取签名原始串
     * @param reqParams
     * @return
     */
    public static String getKeyValSignContent(Map<String, String> reqParams, Set<String> excludeKeys) {

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList(reqParams.keySet());
        Collections.sort(keys);

        boolean hasParam = false;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = reqParams.get(key);
            if (!excludeKeys.contains(key) && StringUtils.isNotBlank(value)) {
                if (hasParam) {
                    content.append("&");
                } else {
                    hasParam = true;
                }
                content.append(key).append("=").append(value);
            }
        }

        return content.toString();
    }

    /**
     * SHA256WithRSA 加签处理
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static byte[] rsa256Sign(String content, PrivateKey privateKey, String charset) {
        byte[] signed = null;
        try{
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(privateKey);
            if (StringUtils.isBlank(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            signed = signature.sign();
        } catch (Exception e) {
            LOGGER.error("SHA256WithRSA加签处理异常", e);
            throw new IllegalArgumentException("SHA256WithRSA加签处理异常", e);
        }
        return signed;
    }

    /**
     * SHA1WithRSA 加签处理
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws Exception
     */
    public static byte[] rsaSign(String content, PrivateKey privateKey, String charset) {
        byte[] signed = null;
        try{
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            if (StringUtils.isBlank(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            signed = signature.sign();

        } catch (Exception e) {
            LOGGER.error("SHA1WithRSA加签处理异常", e);
            throw new IllegalArgumentException("SHA1WithRSA加签处理异常", e);
        }
        return signed;
    }

    /**
     * 获取验签公钥
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromX509(String verifySignKeyPath, String algorithm) {
        if (StringUtils.isBlank(verifySignKeyPath) || StringUtils.isEmpty(algorithm)) {
            throw new IllegalArgumentException("验签公钥未配置");
        }
        PublicKey verifySignKey = null;
        FileInputStream fis = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            fis = new FileInputStream(verifySignKeyPath);
            byte[] bytes = new byte[1024];
            byte[] encodedKey = new byte[0];
            int readLen = 0;
            while ((readLen = fis.read(bytes)) != -1) {
                encodedKey = ArrayUtils.addAll(encodedKey, ArrayUtils.subarray(bytes, 0, readLen));
            }
            encodedKey = Base64.decodeBase64(encodedKey);
            verifySignKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (Exception e) {
            LOGGER.error("加载验签公钥异常", e);
            throw new IllegalArgumentException("加载验签公钥异常", e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("关闭验签公钥文件流异常", e);
                }
            }
        }
        return verifySignKey;
    }

    /**
     * 获取验签公钥
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromX509Cer(String verifySignKeyPath, String algorithm) {
        if (StringUtils.isBlank(verifySignKeyPath) || StringUtils.isEmpty(algorithm)) {
            throw new IllegalArgumentException("验签公钥未配置");
        }
        PublicKey verifySignKey = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(verifySignKeyPath);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate c = cf.generateCertificate(fis);
            verifySignKey = c.getPublicKey();
        } catch (Exception e) {
            LOGGER.error("加载验签公钥异常：", e);
            throw new IllegalArgumentException("加载验签公钥异常", e);
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.error("关闭验签公钥文件流异常", e);
                }
            }
        }
        return verifySignKey;
    }

    /**
     * 获取验签公钥
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKeyFromX509String(String base64CertString, String algorithm) {
        if (StringUtils.isBlank(base64CertString) || StringUtils.isEmpty(algorithm)) {
            throw new IllegalArgumentException("验签公钥未配置");
        }
        PublicKey verifySignKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = Base64.decodeBase64(base64CertString.getBytes());
            verifySignKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (Exception e) {
            LOGGER.error("加载验签公钥异常", e);
            throw new IllegalArgumentException("加载验签公钥异常", e);
        }
        return verifySignKey;
    }

    /**
     * SHA256WithRSA 验签处理
     * @param content 签名原始串
     * @param verifySignBytes 待验证签名串字节
     * @param publicKey 验签公钥
     * @param charset 字签集
     * @return
     * @throws Exception
     */
    public static boolean rsa256Verify(String content, byte[] verifySignBytes, PublicKey publicKey, String charset) throws Exception {
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initVerify(publicKey);
        if (StringUtils.isBlank(charset)) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset));
        }

        return signature.verify(verifySignBytes);
    }

    /**
     * SHA1WithRSA 验签处理
     * @param content 签名原始串
     * @param verifySignBytes 待验证签名串
     * @param publicKey 验签公钥
     * @param charset 字签集
     * @return
     * @throws Exception
     */
    public static boolean rsaVerify(String content, byte[] verifySignBytes, PublicKey publicKey, String charset) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(publicKey);
        if (StringUtils.isBlank(charset)) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset));
        }

        return signature.verify(verifySignBytes);
    }

    /**
     * 组建http请求参数设置
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params != null && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            boolean hasParam = false;
            Iterator it = entries.iterator();

            while (it.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) it.next();
                String name = entry.getKey();
                String value = entry.getValue();
                if (StringUtils.isNotBlank(value)) {
                    if (hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }

                    query.append(name).append("=").append(URLEncoder.encode(value, charset));
                }
            }

            return query.toString();
        } else {
            return null;
        }
    }


    /**
     * 组装XML报文中获取签名原始串。
     * @param xmlMessage xml 报文
     * @param excludeKeys 不参与签名的元素名称
     * @param signElLevel 用于签名的元素级，从根元素下的第一级元素算起，
     *                    也就是根元素下的第一级元素signElLevel为1。
     *                    如参与签名的元素包含子元素，则此元素不参与
     *                    签名。
     * @return
     */
    public static String getKeyValSignContentFormXMl(String xmlMessage, Set<String> excludeKeys, int signElLevel) {
        if (StringUtils.isBlank(xmlMessage)) {
            throw new IllegalArgumentException("XML报文格式错误！");
        }
        Map<String, String> xmlParams = null;
        try {
            Document document = DocumentHelper.parseText(xmlMessage);
            Element el = document.getRootElement();
            Element signElParentEl = el;
            for (int i = 0; i < (signElLevel - 1); i++) {
                List els = signElParentEl.elements();
                if (els == null || els.size() == 0) {
                    throw new IllegalArgumentException("XML报文用于签名的元素级不存在！");
                }
                signElParentEl = (Element) els.get(0);
            }
            Iterator it = signElParentEl.elementIterator();
            xmlParams = new HashMap<String, String>();
            while (it.hasNext()) {
                Element next = (Element) it.next();
                if (next.isTextOnly()) {
                    xmlParams.put(next.getName(), next.getText());
                }
            }
        } catch (Exception e) {
            LOGGER.error("组装XML报文中获取签名原始串异常", e);
            throw new IllegalArgumentException("组装XML报文中获取签名原始串异常", e);
        }
        return getKeyValSignContent(xmlParams, excludeKeys);
    }
    
    public static String chineseStitchingSpace(String str,int len) throws Exception{
    	if(org.springframework.util.StringUtils.isEmpty(str)){
    		return "";
    	}
		int strlen=getChineseLength(str,"UTF-8");
		if(strlen>=len){
			return str;
		}
		for(int i=0;i<len-strlen;i++){
			str=str + " ";
		}
		return str;
		
	}
	
    public static int getChineseLength( String name , String endcoding )
            throws Exception{
        int len = 0 ; //定义返回的字符串长度
        int j = 0 ;
        //按照指定编码得到byte[]
        byte [] b_name = name.getBytes( endcoding ) ;
        while ( true ){
            short tmpst = (short) ( b_name[ j ] & 0xF0 ) ;
            if ( tmpst >= 0xB0 ){
                if ( tmpst < 0xC0 ){
                    j += 2 ;
                    len += 2 ;
                }
                else if ( ( tmpst == 0xC0 ) || ( tmpst == 0xD0 ) ){
                    j += 2 ;
                    len += 2 ;
                }
                else if ( tmpst == 0xE0 ){
                    j += 3 ;
                    len += 2 ;
                }
                else if ( tmpst == 0xF0 ){
                    short tmpst0 = (short) ( ( (short) b_name[ j ] ) & 0x0F ) ;
                    if ( tmpst0 == 0 ){
                        j += 4 ;
                        len += 2 ;
                    }
                    else if ( ( tmpst0 > 0 ) && ( tmpst0 < 12 ) ){
                        j += 5 ;
                        len += 2 ;
                    }
                    else if ( tmpst0 > 11 ){
                        j += 6 ;
                        len += 2 ;
                    }
                }
            }
            else{
                j += 1 ;
                len += 1 ;
            }
            if ( j > b_name.length - 1 ){
                break ;
            }
        }
        return len ;
    }
	 

    public static void main(String[] args) {
        String xmlMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<EctData>\n" +
                "  <Message id=\"9b991068cddb5fc0\">\n" +
                "    <CSRes id=\"CPRes\">\n" +
                "      <resultMsg>快捷支付验签失败</resultMsg>\n" +
                "      <sign>ka3tYdtzxqs80mi3qkjX3EnimDknZRgRULOiB5XXtK5IJBYQwRIPT+MUF51qnYq1qkBN5amRNOt1Nm05SsIq1euboaTiGm9PhlIf+GoBXT8YegJmD4+P08Ng3yF0fbaU0+oljEfrkOpBIoZwJR6kuSlsp5wLjchl2ZhvE2cxD+ov9qflL8ezP+KBmd3KWlP8zcvMYN3pVqtJHYJHoaIQwcEjXOakE/W5wXxDWk7XEqofNPDg5FHckpGR4JA7j7oIv/S27luld1/ie/IDLPa50POTlQXZVuWyLjKzKlmq8i6JHI2M3GRPtIpzsDmDCQ8CpnEuEfqCvFVle1QsCaj/eg==</sign>\n" +
                "      <serialNo></serialNo>\n" +
                "      <payOrderId>1502787120858</payOrderId>\n" +
                "      <respDate>20170815 16:52:04</respDate>\n" +
                "      <resultCode>E230100001</resultCode>\n" +
                "      <account>6222060000000000</account>\n" +
                "      <merchantId>000010004</merchantId>\n" +
                "      <protocolId></protocolId>\n" +
                "      <retFlag>F</retFlag>\n" +
                "    </CSRes>\n" +
                "  </Message>\n" +
                "</EctData>";
        Set excludeKeys = new HashSet<String>(1);
        excludeKeys.add("sign");
        String singContent = getKeyValSignContentFormXMl(xmlMsg, excludeKeys, 3);
        System.out.println(singContent);
    }
}
