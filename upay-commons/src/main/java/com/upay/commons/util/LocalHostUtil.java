package com.upay.commons.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用于获取本地IP并缓存
 * 
 * @author WUDUFENG
 * @since 2014年7月8日
 */
public class LocalHostUtil {
    private final static Logger logger = LoggerFactory.getLogger(LocalHostUtil.class);

    /** 本地IP地址 **/
    public static String LOCAL_IP_ADDR = "";

    static {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || !ni.isUp())
                    continue;
                logger.debug("DisplayName:" + ni.getDisplayName());
                String name = ni.getName();
                logger.debug("Name:" + name);
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress addr = ips.nextElement();
                    if (addr instanceof Inet6Address) {
                        continue;
                    }
                    String ip = addr.getHostAddress();
                    logger.debug("IP:" + ip);
                    if (ip.length() > 15 || ip.length() < 7 || ip.split("\\.").length != 4)
                        continue;
                    LOCAL_IP_ADDR = ip;
                }
            }
        } catch (Exception e) {
            logger.error("获取本地IP错误", e);
        }
        //防止本地IP为空白
        if(LOCAL_IP_ADDR == null || LOCAL_IP_ADDR.trim().equals("")){
        	LOCAL_IP_ADDR = "127.0.0.1";
        }
    }
}
