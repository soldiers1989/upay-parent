package com.upay.gateway.client.notify.util;


import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据加签验签工具
 * Created by wangzhenxing on 2016/12/12.
 */
public class DataSignUtil {

	private static final Logger LOG = LoggerFactory.getLogger(DataSignUtil.class);
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        Map<String, Object> source = new HashMap<String, Object>();
//        source.put("transCode","resultNotify");
//        source.put("serviceVersion","1.0");
//        source.put("signType","MD5");
//        source.put("rspCode","0000000000");
//        source.put("rspMsg","sucess");
//        source.put("charset","utf-8");
//        System.out.println(unSignData(source,"key","1234567890123456",""));
//        StringBuilder sb = new StringBuilder();
//        sb.append("ssfa").append("&&");
//        System.out.println(sb.toString());
//    }

    /**
     * 对传进来的字符串进行加签操作
     *
     * @param param
     * @param key
     * @param merNo
     * @return
     */
    public static String signData(Map<String, Object> param, String key, String merNo) throws UnsupportedEncodingException {
        String dataSign = null;
        if (null == param || 0 == param.size()) {
            return null;
        }
        Set<String> keySet = param.keySet();
        List<String> list = new ArrayList<String>();
        for (String k : keySet) {
            if (!"sign".equals(k) && !"signType".equals(k)) {
                if (null != param.get(k) && !"".equals(param.get(k))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(k).append("=").append(param.get(k));
                    list.add(sb.toString());
                }
            }
        }
        String[] keyVals = new String[list.size()];
        list.toArray(keyVals);//将集合转换成字符数组
        Arrays.sort(keyVals);//将字符组排序
        StringBuilder sb = new StringBuilder();
        for (String keyVal : keyVals) {
            sb.append(keyVal).append("&");
        }
        String charSet = (String) param.get("charset");
        sb.append(key).append("=").append(merNo);
        LOG.debug("书序："+sb.toString());
        dataSign = MD5Utils.md5Hex(sb.toString(), charSet);
        return dataSign;
    }

    /**
     * 验签
     */
    public static boolean unSignData(Map<String, Object> source, String key, String merNoName, String oldDataSign) throws UnsupportedEncodingException {
        String dataSign = "";
        if (null == source || 0 == source.size()) {
            return false;
        }
        Set<String> keySet = source.keySet();
        List<String> list = new ArrayList<String>();
        for (String k : keySet) {
            if (!"sign".equals(k) && !"signType".equals(k)) {
                if (null != source.get(k) || !"".equals(source.get(k))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(k).append("=").append(source.get(k));
                    list.add(sb.toString());
                }
            }
        }
        String[] keyVals = new String[list.size()];
        list.toArray(keyVals);
        Arrays.sort(keyVals);
        StringBuilder sbBuff = new StringBuilder();
        for (String keyVal : keyVals) {
            sbBuff.append(keyVal).append("&");
        }
        sbBuff.append(key).append("=").append(merNoName);
        String charset = (String) source.get("charset");
        dataSign = MD5Utils.md5Hex(sbBuff.toString(), charset);
        if (dataSign.equals(oldDataSign)) {
            return true;
        }
        return false;
    }

}
