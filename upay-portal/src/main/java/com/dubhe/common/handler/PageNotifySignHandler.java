package com.dubhe.common.handler;

import com.dubhe.common.util.MD5Utils;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Guo on 16/9/2.
 * 前台通知签名
 */
public class PageNotifySignHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(PageNotifySignHandler.class);

    private static final String RETURN_URL = "returnUrl";

    @Resource
    private IDipperCached cacheClient;

    @SuppressWarnings("all")
    @Override
    public Message handle(Message message) throws Exception {
        Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();
        String returnUrl = (String) map.remove(RETURN_URL);
        String merNo = (String) map.get("merNo");
        String key = cacheClient.get(merNo);
        String signSource = MD5Utils.getSignSource(map, "key", key);
        String sign = MD5Utils.md5Hex(signSource, message.getCharset());
        map.put("sign", sign);
        map.put("returnUrl", returnUrl);
        LOG.info("前台通知签名成功");
        return message;
    }
}
