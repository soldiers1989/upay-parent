package com.ups.api.internal.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 签名工具
 * @author Guo
 *
 */
public class MD5Utils {
	/**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] md5(byte[] data) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			return messageDigest.digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
    	
    }
    
    public static byte[] md5(String data, String charset) throws UnsupportedEncodingException {
    	return md5(data.getBytes(charset));
    }

    public static String md5Hex(String data, String charset) throws UnsupportedEncodingException {
    	return encodeHexString(md5(data, charset));
    }
    
    private static String encodeHexString(byte[] data) {
        return new String(encodeHex(data, false));
    }
    
    private static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }
    
    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 获取签名原始串(升序，去除空值、sign)
     * @param body
     * @param keyName
     * @param keyVal
     * @return
     */
    public static String getSignSource(Map<String, String> body, String keyName,
            String keyVal) {
        if (StringUtils.isEmpty(keyVal)) {
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
        treeMap.putAll(body);

        Iterator<Entry<String, String>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            if (entry.getValue() instanceof String) {
            	String key = entry.getKey();
            	String value = entry.getValue();
                if (!StringUtils.isEmpty(value) && !"sign".equals(key)&& !"signType".equals(key)) {
                    output.append(key).append("=").append(value)
                        .append("&");
                }
            } else {
                throw new IllegalArgumentException("摘要值不为字符串类型，请检查！");
            }

        }
        output.append(keyName).append("=").append(keyVal);

        return output.toString();
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException{
        // System.out.println(getMd5String(""));
         System.out.println(md5Hex("good", "UTF-8"));
     }
     

}
