package com.upay.commons.util;

import java.util.Random;


/**
 * 随机数
 * 
 * @author freeplato
 * 
 */
public class MathUtil {

    /**
     * 获取随机数(纯数字，用于短信验证码)
     * 
     * @param max
     * @return
     */
    public static String randomNum(int max) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < max; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();

    }


    /**
     * 获取随机数(数字加字幕组合，用于图形验证码)
     * 
     * @param max
     * @return
     */
    public static String randomCode(int max) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < max; i++) {
            boolean flag = true;
            if (random.nextInt() % 2 == 0) {
                flag = false;
            }
            if (flag) {
                sb.append(random.nextInt(10));
            } else {
                char randomWord = 'a';
                if (random.nextInt() % 2 == 0) {
                    randomWord = 'A';
                }
                sb.append((char) (randomWord + (int) (Math.random() * 26)));
            }
        }

        return sb.toString();

    }

}
