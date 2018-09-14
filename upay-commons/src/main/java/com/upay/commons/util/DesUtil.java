package com.upay.commons.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;


@SuppressWarnings("restriction")
public class DesUtil {

    private static final BASE64Decoder decoder = new BASE64Decoder();


    public static void main(String[] args) throws Exception {
        String password = "cmbtest1";
        String content =
                "/qigtuR2GX2aR5z+z329BCDH8q7cpRdOe9fMJBW7Emob9hG6xurMDuLqxv3jg8DR2oEfYBPazsJq1y3j2qFA2EFxFrlxgGdUDxUItWTfWkKlDBz1gMZbQFriRFRUrTE/wfu3rK2UByR92sZWeiD5jvD5lxNztrOFCP6kF9RKnvp5v56zKIIRlWFiSFdtUB+SfBpwDvR4Ryf5EjyjlciGSGW8gTgGicxOKxjbTEPpTekwIegXGwVgim/UGsnpb3JWydyR6DZ3ecSrOj/fnQ9C7fU/fDgDiO+CdGgCT9vDlTzPIzhtxR2jUqrmz8VadziM9gbnjk9ne6so6fzsbet5g3lDGZjJHgUJIlVP0sunefloMrH+NHU8lzHjgiYTjAFtdm6b2PWbiUhZchIomPg1upsj3G2d0HVjySl+cKA+TJp1j5yI4t11OWB/BHK7KTjhsWfrlnV/z5oRC2F93umONJ8X5Xg2TNuzKdJQ393OFiju1nFElGr7N7P9+RuY29H6bXyzZrcndCx1QV08w6RcuP6zGK8tn8r40WF2px12A69GQbixPjNQ2k0423oIey+ZQ/WjMH5EThuZTjNDXDpqoZrryW9W7I1oQ4FnnRLA+M/L5n4jgMV+bcYMpPRwkJ9FK5ow3jkv63PRS/UEAwwYcdVkKUoRVuDpaQWwFiPN4bopiS0DdeYS2JUxo9FJ0P57HXTpmODVsu7RWaizTDh77u6TLatcOGUbk6LpohpQ+sYFEolyTVfS+m7/AFyDGL0GLZeXREWbFIOctD4arU/u0vbC2GDc1aYLvgWQNHueZCwokHPIDyoAeQ==";
        //System.out.println(decrypt(content, password));
    }


    /**
     * 解密对外接口
     * 
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String password) throws Exception {
        byte[] result = decoder.decodeBuffer(content);
        byte[] decryResult = decrypt_(result, password);
        return new String(decryResult, "utf-8");
    }


    /**
     * 加密
     * 
     * @param datasource
     *            byte[]
     * @param password
     *            String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密
     * 
     * @param src
     *            byte[]
     * @param password
     *            String
     * @return byte[]
     * @throws Exception
     */
    private static byte[] decrypt_(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        // Cipher cipher = Cipher.getInstance("DES");
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

}
