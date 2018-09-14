package com.upay.commons.util;

import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Md5Util {
    private static final Logger LOG = LoggerFactory.getLogger(Md5Util.class);
    private static final String STR_KEY_NAME = "key";


    public static String toMD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 排序并拼接key-value字符串
     * 
     * @param body
     * @param columns
     * @param key
     * @param strKeyName
     * @return
     */
    public static String sortAndConvToStr(Map<String, Object> body, Map<String, String> columns, String key,
            String strKeyName) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("秘钥不能为空，请检查！");
        }
        if (body == null || body.size() == 0) {
            throw new IllegalArgumentException("传入参数不能为空，请检查！");
        }

        StringBuffer output = new StringBuffer();
        Map<String, String> treeMap = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                return key1.compareTo(key2);
            }
        });
        treeMap.putAll(columns);

        Iterator<Entry<String, String>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            Object value = body.get(entry.getValue());
            if (value != null) {
                if (value instanceof String) {
                    if (StringUtils.isNotBlank((String) value)) {
                        output.append(entry.getKey()).append("=").append((String) value).append("&");
                    }
                } else {
                    throw new IllegalArgumentException("摘要值不为字符串类型，请检查！");
                }
            }
        }
        output.append(strKeyName).append("=").append(key);
        LOG.debug("待签名字符串[{}]", output.toString());
        return output.toString();
    }


    /**
     * 摘要
     * 
     * @param body
     * @param columns
     *            要签名的key
     * @param key
     *            秘钥
     * @param strKeyName
     *            签名串秘钥key 默认"key"
     * @param upperFlag
     *            转大写标志
     */

    public static String md5(Map<String, Object> body, Map<String, String> columns, String key,
            String strKeyName, boolean upperFlag) {
        strKeyName = StringUtils.isBlank(strKeyName) ? STR_KEY_NAME : strKeyName;

        return upperFlag ? DigestUtils.md5Hex(sortAndConvToStr(body, columns, key, strKeyName)).toUpperCase()
                : DigestUtils.md5Hex(sortAndConvToStr(body, columns, key, strKeyName)).toLowerCase();

    }


    public static void main(String[] args) {
        // Map<String, Object> input = new HashMap<String, Object>();
        // input.put("df23", "df@23");
        // input.put("de23", "de@23");
        // input.put("df13", "@");
        // input.put("df24", "df@24");
        // input.put("df03", "df@03");
        // Map<String, String> input1 = new HashMap<String, String>();
        // input1.put("df23", "df_23");
        // input1.put("de23", "de_23");
        // input1.put("df13", "");
        // input1.put("df24", "df_24");
        // input1.put("df03", "df_03");
        // System.err.println(input);
        // System.err.println(md5(input, input1, "123456", "key", true));
        // System.err.println(md5(input, input1, "123456", null, false));

       // System.out.println(toMD5("shang"));
    }
}
