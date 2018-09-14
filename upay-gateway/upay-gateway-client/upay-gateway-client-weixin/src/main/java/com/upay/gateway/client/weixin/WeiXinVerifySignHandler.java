/**
 * 
 */
package com.upay.gateway.client.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
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
public class WeiXinVerifySignHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(WeiXinVerifySignHandler.class);

    private String md5Key;
    private String keyName;
    private Map<String, Map<String, String>> columnsMap;


    @Override
    @SuppressWarnings("all")
    public Message handle(Message m) throws Exception {
        Map<String, Object> body = (Map<String, Object>) m.getTarget().getBodys();
        Map<String, Object> header = m.getTarget().getHeaders();
        XMLConfiguration xmlcfg =
                (XMLConfiguration) body.get(com.pactera.dipper.presys.mp.agilexml.utils.Constants.XML_CFG);
        String sign = "";
        if (xmlcfg == null) {
            sign =
                    Md5Util.md5(body, columnsMap.get((String) header.get(Constants.Channel._TRAN_CODE_NAME)),
                        md5Key, "key", true);

        } else {
            // 申请退款和查询退款验签
            HierarchicalConfiguration.Node cycleLists = xmlcfg.getRoot();
            List<ConfigurationNode> ConfigurationNodes = cycleLists.getChildren();
            Map<String, Object> bodys = new HashMap<String, Object>();
            Map<String, String> columns = new HashMap<String, String>();
            for (ConfigurationNode configurationNode : ConfigurationNodes) {
                if (!keyName.equals(configurationNode.getName()) && configurationNode.getValue() != null) {
                    bodys.put(configurationNode.getName(), configurationNode.getValue());
                    columns.put(configurationNode.getName(), configurationNode.getName());
                }
            }
            sign = Md5Util.md5(bodys, columns, md5Key, "key", true);
            xmlcfg.clear();
            body.remove(com.pactera.dipper.presys.mp.agilexml.utils.Constants.XML_CFG);
            LOG.debug("xmlcfg不为空,验签交易码[{}],message=[{}]",
                (String) header.get(Constants.Channel._TRAN_CODE_NAME), m);
        }
        if (!sign.equals((String) body.get(keyName))) {
            m.getFault().setCodeAll("FAIL");
            m.getFault().setMsgAll("验签错误");
            body.put("code", m.getFault().getCode());
            body.put("msg", m.getFault().getMsg());
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
