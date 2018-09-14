package com.dubhe.common.handler.mp;

import static com.upay.commons.dict.AppCodeDict.GWSPAY2403;
import static com.upay.commons.dict.AppCodeDict.GWSPAY2404;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dubhe.common.util.MD5Utils;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperCached;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.utils.Constants.Channel;
import com.pactera.dipper.core.utils.PacketUtil;
import com.pactera.dipper.presys.mp.agilexml.configuration.ConfigurationRegistry;
import com.pactera.dipper.presys.mp.agilexml.model.ConfigDef;
import com.pactera.dipper.presys.mp.agilexml.model.MappingDef;
import com.pactera.dipper.presys.mp.agilexml.model.PreviewDef;
import com.pactera.dipper.presys.mp.agilexml.utils.Constants;
import com.pactera.dipper.utils.common.TypeUtils;
import com.upay.commons.dict.AppCodeDict;


public class DeepLinkUnpackHandler implements IDipperHandler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(DeepLinkUnpackHandler.class);

    private static final String MP_BUNDLE = "deeplink";
    private static final String KEY_NAME = "key";
    private static final String SIGN_NAME = "sign";

    @Resource(name = "DIPPER_REDIS_CLIENT")
    private IDipperCached cacheClient;
    


    @Override
    public Message handle(Message m) throws Exception {
        Object payload = m.getTarget().getBodys();
        String charset = m.getCharset();
        String channelId = m.getChannel();
        LOG.debug("{} [channel={}] unpack start!!", MP_BUNDLE, channelId);

        byte[] payloadByte = null;
        if (payload instanceof byte[]) {
            payloadByte = (byte[]) payload;
        } else {
            throw new IllegalArgumentException(MP_BUNDLE.concat(" [channel=" + channelId
                    + "] unpack payload type error!!"));
        }

        Map<String, Object> temp = new HashMap<>();

        // 验签
        String payloadStr = new String(payloadByte, charset);
        String[] payloadArr = payloadStr.split("&");
        Arrays.sort(payloadByte);// 排序

        StringBuilder signStr = new StringBuilder();
        for (String keyVal : payloadArr) {
            int index = keyVal.indexOf("=");
            temp.put(keyVal.substring(0, index), keyVal.substring(index + 1));
            // value为空的不参与签名运算
            if (StringUtils.isNotBlank(keyVal.substring(index + 1, keyVal.length()))
                    && SIGN_NAME.equals(keyVal.substring(0, index))) {
                signStr.append(keyVal).append("&");
            }
        }
        // 获取商户密钥,根据商户号从redis里取
        String merNo = "";// 商户号
        String targetSign = "";
        boolean isDataSign = false;

        String tranCode = "";

        boolean bool = false;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, ConfigDef> configDefMap =
                ConfigurationRegistry.getInstance().getConfigDefMap().get(channelId);
        for (PreviewDef previewDef : configDefMap.get(Constants.PREVIEW).getPreviewsDef().getPreviewDefs()) {
            if (channelId.equals(previewDef.getId())) {
                if (StringUtils.isNotBlank((String) temp.get(previewDef.getTrancodename()))) {
                    tranCode = (String) temp.get(previewDef.getTrancodename());
                    PacketUtil.setValueOfTargetHeaders(m, Channel._TRAN_CODE_NAME, tranCode);// 交易码
                } else {
                    tranCode = PacketUtil.getTranCodeFromTargetHeaders(m, Channel._TRAN_CODE_NAME);
                }
                isDataSign = previewDef.isDataSign();// 是否验签
                // 取出商户号
                merNo = (String) temp.get(previewDef.getXpath());
                targetSign = (String) temp.get(previewDef.getDataSignName());// 报文中的签名串
                LOG.debug("channel[{}], trancode[{}]", channelId, tranCode);
                bool = true;
                break;
            }
        }

        if (!bool) {
            throw new IllegalArgumentException("[channel=" + channelId + "] preview config error!!");
        }

        // 验签
        if (isDataSign) {
            if (StringUtils.isBlank(merNo)) {
                ExInfo.throwDipperEx(GWSPAY2404, "merNo");
                // throw new IllegalArgumentException("merNo不能为空!!");
            }

            if (StringUtils.isBlank(targetSign)) {
                ExInfo.throwDipperEx(GWSPAY2404, SIGN_NAME);
                // throw new IllegalArgumentException(SIGN_NAME + "不能为空!!");
            }
            // 从redis取出商户的密钥
            String merKey = cacheClient.get(merNo);

            signStr.append(KEY_NAME).append("=").append(merKey);
            String sign = MD5Utils.md5Hex(signStr.toString(), charset);
            if (!targetSign.equals(sign)) {
                ExInfo.throwDipperEx(GWSPAY2403);
                // throw new IllegalArgumentException("验证签名不通过");
            }
        }

        ConfigDef headConfigDef = configDefMap.get(Constants.HEAD);
        if (headConfigDef == null) {
            throw new IllegalArgumentException("请检查" + Constants.HEAD + "对应的配置文件!!");
        }

        ConfigDef configDef = configDefMap.get(tranCode);
        if (configDef == null) {
            throw new IllegalArgumentException("请检查" + tranCode + "对应的配置文件!!");
        }

        List<MappingDef> mappingDefs = new ArrayList<MappingDef>();
        String head = configDef.getMappingsDef().getHead();
        if (StringUtils.isNotBlank(head)) {
            LOG.debug("use head is {}.xml", head);
            ConfigDef headDef = configDefMap.get(head);
            mappingDefs.addAll(headDef.getMappingsDef().getMappingDefs());
        } else {
            LOG.debug("use head is head.xml");
            mappingDefs.addAll(headConfigDef.getMappingsDef().getMappingDefs());
        }

        if (configDef.getMappingsDef().getMappingDefs() != null) {
            mappingDefs.addAll(configDef.getMappingsDef().getMappingDefs());
        }

        for (MappingDef mappingDef : mappingDefs) {
            LOG.debug(
                "xpath={}, field={}, type={}, attr={}",
                new Object[] { mappingDef.getXpath(), mappingDef.getField(), mappingDef.getType(),
                              mappingDef.getAttr() });
            setText(temp, resultMap, mappingDef.getXpath(), mappingDef.getField(), mappingDef.getType(),
                mappingDef.getMust(), mappingDef.isE());
        }

        print(resultMap);
        m.getTarget().setBody(resultMap);
        return m;
    }


    private void setText(Map<String, Object> temp, Map<String, Object> resultMap, String xPath, String field,
            String type, String must, boolean e) {
        String obj = (String) temp.get(xPath);
        if (!isMust(obj, must)) {
            return;
        }
        if (StringUtils.isBlank(must) && (obj == null || "".equals(obj))) {
            ExInfo.throwDipperEx(AppCodeDict.GWSPAY2404, xPath);// 参数为空异常
        }
        if (e && StringUtils.isNotBlank(obj)) {
            LOG.debug("{} convert before[{}]", field, obj);
            obj = new BigDecimal(obj).toPlainString();
            LOG.debug("{} convert after[{}]", field, obj);
        }

        if (StringUtils.isNotBlank(type)) {
            try {
                resultMap.put(field, TypeUtils.getValue(type, "", obj));
                return;
            } catch (Exception e1) {
                LOG.error("", e1);
                throw new RuntimeException("field[" + field + "] type convert fail !!");
            }
        }
        resultMap.put(field, obj);
    }


    private boolean isMust(Object obj, String must) {
        if (StringUtils.isNotBlank(must) && "NOT".equals(must.toUpperCase())
                && (obj == null || "".equals(obj))) {
            return false;
        }
        return true;
    }


    private void print(Map<String, Object> resultMap) {
        LOG.debug("{} unpack after:{}", MP_BUNDLE, resultMap.toString());
        LOG.debug("{} unpack end!!", MP_BUNDLE);
    }

}
