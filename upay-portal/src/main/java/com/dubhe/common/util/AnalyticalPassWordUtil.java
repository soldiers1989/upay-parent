package com.dubhe.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * 解析用户签名的密码读取工具类
 * 
 * @author: heyugui
 * @CreateDate:2015年10月28日
 * 
 * 
 * @UpdateUser:修改人名称
 * @UpdateDate:2015年10月28日
 * @UpdateRemark:修改具体的内容；
 * 
 */
public class AnalyticalPassWordUtil {
    /**
     * 获取解析签名所需密码
     * 
     * @return password
     */

    private final static Logger logger = LoggerFactory.getLogger(AnalyticalPassWordUtil.class);


    public static String findParsePassWord() throws Exception {

        logger.info("解析用户签名的密码读取工具类start");

        String passWord = "";
        Properties prop = new Properties();
        InputStream in = null;
        String filePath =
                AnalyticalPassWordUtil.class.getClassLoader().getResource("AnalyticalPassWord.properties")
                    .getPath();
        try {
            in = new BufferedInputStream(new FileInputStream(filePath)); // 读取属性文件AnalyticalPassWord.properties
            prop.load(in);
            passWord = prop.getProperty("password");
        } catch (Exception e) {
            logger.error("解析用户签名的密码读取工具类：" + e.getMessage());
            throw e;
        }
//        if (Strings.isNullOrEmpty(passWord)) {
//            logger.error("解析用户签名的密码读取工具类：读取密码为空");
//            throw new Exception("密码不能为空");
//        }

        logger.info("解析用户签名的密码读取工具类end");
        return passWord;
    }

    /*
     * public static void main(String[] args) { try {
     * System.out.println(findParsePassWord()); } catch (Exception e) {
     * e.printStackTrace(); } }
     */

}
