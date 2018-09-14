package com.upay.gateway.client.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upay.commons.util.DateUtil;



/**
 * 短信服务初始化操作
 * 
 * @author 车静
 * 
 */
public class InitialSmsService {

    private static final Logger log = LoggerFactory.getLogger(InitialSmsService.class);


    /**
     * 启动短信服务 初始化基础参数
     * 
     * @throws Exception
     */
    public static void init() throws Exception {

        InitialSmsService obj = new InitialSmsService();
        FileInputStream inStream = null;
        try {
            // 配置文件读取
            URL url = InitialSmsService.class.getClassLoader().getResource("config.properties");
            inStream = new FileInputStream(new File(url.toURI()));
            Properties props = new Properties();
            props.load(inStream);
            if (!props.isEmpty()) {
                if (props.get("CONNECTTIMEOUT") != null && !"".equals(props.get("CONNECTTIMEOUT"))) {
                    IMSLongConnectionAPIIEventAdapter.CONNECTTIMEOUT =
                            Integer.parseInt((String) props.get("CONNECTTIMEOUT"));
                }
                if (props.get("SOTIMEOUT") != null && !"".equals(props.get("SOTIMEOUT"))) {
                    IMSLongConnectionAPIIEventAdapter.SOTIMEOUT =
                            Integer.parseInt((String) props.get("SOTIMEOUT"));
                }
                if (props.get("SMSURL") != null && !"".equals(props.get("SMSURL"))) {
                    IMSLongConnectionAPIIEventAdapter.SMSURL = (String) props.get("SMSURL");
                }
                if (props.get("CHARSET") != null && !"".equals(props.get("CHARSET"))) {
                    IMSLongConnectionAPIIEventAdapter.CHARSET = (String) props.get("CHARSET");
                }
            } else {
                log.debug(DateUtil.format(new Date(), "yyyyMMddHHmmss") + "使用默认短信平台配置[{}]",
                    "197.3.11.76:8504");
            }

        } catch (Exception e) {
            // 如果报错 配置文件不整合
            log.error(DateUtil.format(new Date(), "yyyyMMddHHmmss")
                    + "因配置文件config.properties故障，短信服务启动失败...[{}]", e);
            throw e;
        } finally {
            if (null != inStream) {
                inStream.close();
            }
        }
    }
}
