package com.dubhe.common.controller;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dubhe.common.util.AnalyticalPassWordUtil;
import com.dubhe.common.util.DesUtil;


@Controller
@RequestMapping("/redirect")
public class RedirectController extends AbstractController {

    private static Logger log = LoggerFactory.getLogger(RedirectController.class);


    // TODO Autowired
    // String password = "cmbtest1";// 秘钥

    @RequestMapping("user/parse")
    public String redirectForParseUserId(String url, String sResponseXml) {
        try {
            log.debug("******** " + "url:" + url + " *******");
            log.debug("******** " + "sResponseXml:" + sResponseXml + " **********");
            String userId = parseUserId(sResponseXml);
            if (StringUtils.isEmpty(url)) {
                throw new IOException("url connot be null!");
            } else {
                if (url.indexOf("?") >= 0) {
                    url = url + "&userid=" + userId;
                } else {
                    url = url + "?userid=" + userId;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        url = url.replace("\\", "");
        url = url + "&fail=0";
        log.debug("return:[" + "redirect:" + url + "]");
        return "redirect:" + url;
    }


    private String parseUserId(String sResponseXmlStr) throws Exception {
        if (StringUtils.isEmpty(sResponseXmlStr)) {// 前端数据包发送失败
            throw new IOException("sResponseXml connot be null!");
        }
        sResponseXmlStr = sResponseXmlStr.replace("&lt;", "<");
        sResponseXmlStr = sResponseXmlStr.replace("&gt;", ">");

        XMLConfiguration xmlCfg = new XMLConfiguration();
        sResponseXmlStr = sResponseXmlStr.replace("\\", "");
        xmlCfg.load(new StringReader(sResponseXmlStr));
        String responseUserID = "";
        String xmlResultType = xmlCfg.getString("Head.ResultType");

        if (xmlResultType.equals("Y")) {// 前端数据包发送成功
            String xmlBody = xmlCfg.getString("Body");// 拿到加密的body節點信息
            xmlBody = xmlBody.replace(" ", "");
            String xmlRes = DesUtil.decrypt(xmlBody, AnalyticalPassWordUtil.findParsePassWord()); // 将加密的body解密成xml明文
            XMLConfiguration userInfo = new XMLConfiguration();
            String xmlUtf8 = new String(xmlRes.getBytes(), "utf-8");
            userInfo.load(new StringReader(xmlUtf8));
            responseUserID = userInfo.getString("Body.NewUserID"); // 拿到解析后的NewUserID的值
        } else {
            throw new IOException("url connot be null!");
        }
        log.debug("返回值：" + responseUserID);
        return responseUserID;
    }
}
