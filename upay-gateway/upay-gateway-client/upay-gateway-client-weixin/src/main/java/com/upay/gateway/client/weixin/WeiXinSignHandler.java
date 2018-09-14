/**
 * 
 */
package com.upay.gateway.client.weixin;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.Constants;
import com.upay.commons.util.Md5Util;


/**
 * @author hing
 * 
 */
public class WeiXinSignHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(WeiXinSignHandler.class);

    private String md5Key;
    private String keyName;
    private Map<String, Map<String, String>> columnsMap;


    @Override
    @SuppressWarnings("all")
    public Message handle(Message m) throws Exception {
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
        body.put("timeStamp", Long.toString(System.currentTimeMillis() / 1000));
        body.put("nonceStr", UUID.randomUUID().toString().replace("-", "").trim());
        body.put("signType", "MD5");
        Map<String, Object> header = m.getTarget().getHeaders();
        body.put(keyName, Md5Util.md5(body, columnsMap.get(StringUtils.isNotBlank((String) body
            .get("tranCode")) ? (String) body.get("tranCode") : (String) header
            .get(Constants.Channel._TRAN_CODE_NAME)), md5Key, "key", true));
        if(body.get("billDate")!=null){
            ProcessFileStreamHandler.THREAD.set(body.get("billDate").toString());
        }
        return m;
    }


    public String getMd5Key() {
        return md5Key;
    }


    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }


    public Map<String, Map<String, String>> getColumnsMap() {
        return columnsMap;
    }


    public void setColumnsMap(Map<String, Map<String, String>> columnsMap) {
        this.columnsMap = columnsMap;
    }


    public String getKeyName() {
        return keyName;
    }


    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

}
