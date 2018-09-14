package com.upay.commons.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


public class ToolUtil {

    /**
     * 判断是否为空
     * 
     * @param object
     * @return
     */
    public boolean checkIsNull(String object) {
        try {
            if (null == object || "".equals(object)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return true;
        }
    }


    /**
     * 判断是否为空
     * 
     * @author huaqiao
     * @param str
     * @return
     */
    public static String splitStat(String str) {
        StringBuffer temp = new StringBuffer();
        String[] chnlId = str.split("\\,");
        for (int i = 0; i < chnlId.length; i++) {
            if (i <= (chnlId.length - 2)) {
                temp.append(chnlId[i]);
                temp.append(",");
            } else {
                temp.append(chnlId[i]);
            }
        }
        return temp.toString();
    }


    /** 左补"0" */
    public static String leftPad(String str, String numStr, int len) {
        // numStr已超出长度如何处理？
        String formater = "%".concat(str).concat(len + "").concat("d");
        return String.format(formater, Long.valueOf(numStr));
    }


    /** 获取指定长度的随机整数 */
    public static String getRandomLong(int maxNumber, int length) {

        Random random = new Random();

        String verifyCode = leftPad("0", random.nextInt(maxNumber) + "", length);

        return verifyCode;
    }


    public static void main(String[] ss) {

        System.out.println(getRandomLong(1000000, 6));

    }


    /**
     * 去掉空值
     * 
     * @param inputMap
     * @return
     */
    public static <T> Map<String, T> removeBlank(Map<String, T> inputMap) {
        Map<String, T> outputMap = new HashMap<String, T>();
        outputMap.putAll(inputMap);
        Set<String> set1 = inputMap.keySet();
        Iterator<String> iterator1 = set1.iterator();
        while (iterator1.hasNext()) {
            String key0 = (String) iterator1.next();
            Object tmp = inputMap.get(key0);
            if (tmp == null || (StringUtils.equals(tmp.toString(), "null"))
                    || (StringUtils.isBlank(tmp.toString()))) {
                outputMap.remove(key0);
            }
        }
        return outputMap;
    }
}
