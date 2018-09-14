/**
 * 
 */
package com.upay.gateway.client.weixin;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.commons.constants.DataBaseConstants_BATCH;


/**
 * 处理对账文件流
 * 
 * @author zacks
 * 
 */
public class ProcessFileStreamHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessFileStreamHandler.class);
    public static final ThreadLocal<String> THREAD=new ThreadLocal<>();
    private String filePath;
    private String fileName;
    private static final String XML = "<xml>";
    private static final ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        };
    };

    private static final String SUCCESS_CONTENX =
            "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[交易成功]]></return_msg><fileName>";


    @Override
    @SuppressWarnings("all")
    public Message handle(Message m) throws Exception {
        byte[] body = (byte[]) m.getTarget().getBodys();
        String content = new String(body, m.getCharset());
        LOG.info("微信对账文件内容:::"+content);
        if (StringUtils.isNotBlank(content)) {
            if (content.indexOf(XML) >= 0) {
                // 请求失败，不作处理
                LOG.info("下载对账文件失败");
            } else {
                // 目录不存在，创建
                File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

//                Calendar today = Calendar.getInstance();
                // 获取前一天文件
//                today.add(Calendar.DAY_OF_MONTH, -1);
                // 文件名自动追加年月日
//                String realName = fileName + "-" + format.get().format(today.getTime()) + ".txt";
                String realName = fileName + "-" + THREAD.get() + ".txt";
                File file = new File(filePath + realName);
                LOG.info("微信对账全路径，文件名[{}]", filePath +realName);
                // 保存文件
                FileWriter fw = null;
                try {
                    // 重复获取，覆盖原有内容
                    fw = new FileWriter(file);
                    fw.write(content);
                    LOG.info("下载对账文件成功，文件名[{}]", filePath +realName);
                } catch (Exception e) {
                	e.printStackTrace();
                	LOG.error(e.getMessage());
                    throw new IllegalArgumentException("对账文件[" + realName + "]写入失败");
                } finally {
                    THREAD.remove();
                    if (fw != null) {
                        try {
                            fw.flush();
                            fw.close();
                        } catch (Exception e) {
                            LOG.error("", e);
                        }
                    }
                }
                String bodyString = SUCCESS_CONTENX + "<![CDATA[" + realName + "]]></fileName></xml>";
                // System.out.println("==============:"+bodyString);
                // 文件保存成功，将报文置为xml格式，以便解包
                m.getTarget().setBody(bodyString.getBytes(m.getCharset()));
            }
        }
        return m;
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
