package com.upay.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * @author we
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

    public final static String toMD5(String s) {
        try {
            byte[] btInput = s.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            return encodeHexString(md);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMd5String(String str){
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }
    
    /**
     * 获取签名原始串(升序，去除空值、sign)
     * @param body
     * @param keyName
     * @param keyVal
     * @return
     */
    public static String getSignSource(Map<String, Object> body, String keyName,
            String keyVal) {
        if (StringUtils.isBlank(keyVal)) {
            throw new IllegalArgumentException("秘钥不能为空，请检查！");
        }
        if (body == null || body.size() == 0) {
            throw new IllegalArgumentException("传入参数不能为空，请检查！");
        }

        StringBuffer output = new StringBuffer();
        Map<String, Object> treeMap = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String key1, String key2) {
                return key1.compareTo(key2);
            }
        });
        treeMap.putAll(body);

        Iterator<Entry<String, Object>> it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (entry.getValue() instanceof String) {
            	String key = entry.getKey();
            	String value = (String) entry.getValue();
                if (StringUtils.isNotBlank(value)) {
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
         System.out.println(toMD5("good"));
         System.out.println(md5Hex("good", "UTF-8"));
         System.out.println(getMd5String("good"));
     }
     

}
