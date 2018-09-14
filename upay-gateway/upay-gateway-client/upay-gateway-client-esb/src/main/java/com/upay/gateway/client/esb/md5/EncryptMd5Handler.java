/**
 * 
 */
package com.upay.gateway.client.esb.md5;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;


/**
 * md5摘要处理,打包之前
 * 
 * @author zacks
 * @since 2014年11月4日
 */
public class EncryptMd5Handler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptMd5Handler.class);

    private String md5Key;// 核心秘钥

    private Map<String, String> packKeyMapping;


    public Message handle(Message m) throws Exception {
        Message message = m;

        Map<String, Object> bodys = (Map<String, Object>) m.getTarget().getBodys();

        LOG.info("bodys before encript:[{}]", bodys);

        encryptParamsWithMd5(bodys);

        LOG.info("bodys after encript:[{}]", bodys);

        return message;
    }


    /**
     * md5摘要
     * 
     * @param requestMap
     * @return
     */
    private void encryptParamsWithMd5(Map<String, Object> bodys) throws Exception {
        if (packKeyMapping.get(bodys.get("tranCode")) == null)
            return;
        String[] md5Culumns = packKeyMapping.get(bodys.get("tranCode")).split("\\|");

        for (String md5Column : md5Culumns) {
            String[] keyMapping = md5Column.split("\\-");
            if (keyMapping.length != 2)
                throw new IllegalArgumentException("配置不合法，请配置与[" + keyMapping[0] + "]对应的摘要字段名称!");
            Object value = bodys.get(keyMapping[0]);
            if (value instanceof String) {
                bodys.put(keyMapping[1], DigestUtils.md5Hex((String) value) + md5Key);
            } else {
                throw new IllegalArgumentException("摘要字段[" + keyMapping[0] + "]不是String类型,请检查!");
            }

        }
    }


    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }


    public void setPackKeyMapping(Map<String, String> packKeyMapping) {
        this.packKeyMapping = packKeyMapping;
    }

}
