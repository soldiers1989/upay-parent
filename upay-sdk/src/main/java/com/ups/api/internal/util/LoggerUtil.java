package com.ups.api.internal.util;

import com.ups.api.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * Created by Guo on 2016/12/11.
 */
public class LoggerUtil {
    private static final Log LOG = LogFactory.getLog("sdk.commumication.error");

    private static boolean   needEnableLogger = true;

    public static void setNeedEnableLogger(boolean needEnableLogger) {
        LoggerUtil.needEnableLogger = needEnableLogger;
    }

    /**
     * 通讯错误日志
     */
    public static void logCommError(Exception e, HttpURLConnection conn,
                                    String method, byte[] content) {
        if (!needEnableLogger) {
            return;
        }
        String contentString = null;
        try {
            contentString = new String(content, "UTF-8");
            logCommError(e, conn, method, contentString);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 通讯错误日志
     */
    public static void logCommError(Exception e, String url, String method,
                                    byte[] content) {
        if (!needEnableLogger) {
            return;
        }
        String contentString = null;
        try {
            contentString = new String(content, "UTF-8");
            logCommError(e, url, method, contentString);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 通讯错误日志
     */
    public static void logCommError(Exception e, HttpURLConnection conn,
                                    String method, Map<String, String> params) {
        if (!needEnableLogger) {
            return;
        }
        _logCommError(e, conn, null, method, params);
    }

    public static void logCommError(Exception e, String url, String method,
                                    Map<String, String> params) {
        if (!needEnableLogger) {
            return;
        }
        _logCommError(e, null, url, method, params);
    }

    /**
     * 通讯错误日志
     */
    private static void logCommError(Exception e, HttpURLConnection conn,
                                     String method, String content) {
        Map<String, String> params = parseParam(content);
        _logCommError(e, conn, null, method, params);
    }

    /**
     * 通讯错误日志
     */
    private static void logCommError(Exception e, String url, String method,
                                     String content) {
        Map<String, String> params = parseParam(content);
        _logCommError(e, null, url, method, params);
    }

    /**
     * 通讯错误日志
     */
    private static void _logCommError(Exception e, HttpURLConnection conn, String url,
                                      String method, Map<String, String> params) {
        DateFormat df = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(Constants.DATE_TIMEZONE));
        String sdkName = Constants.SDK_VERSION;
        String urlStr = null;
        String rspCode = "";
        if (conn != null) {
            try {
                urlStr = conn.getURL().toString();
                rspCode = "HTTP_ERROR_" + conn.getResponseCode();
            } catch (IOException ioe) {
            }
        } else {
            urlStr = url;
            rspCode = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(df.format(new Date()));// 时间
        sb.append("^_^");
        sb.append(method);// API
        sb.append("^_^");
        sb.append(sdkName);
        sb.append("^_^");
        sb.append(urlStr);// 请求URL
        sb.append("^_^");
        sb.append(rspCode);
        sb.append("^_^");
        sb.append((e.getMessage() + "").replaceAll("\r\n", " "));
        LOG.error(sb.toString());
    }

    private static Map<String, String> parseParam(String contentString) {
        Map<String, String> params = new HashMap<>();
        if (contentString == null || contentString.trim().equals("")) {
            return params;
        }
        String[] paramsArray = contentString.split("\\&");
        if (paramsArray != null) {
            for (String param : paramsArray) {
                String[] keyValue = param.split("=");
                if (keyValue != null && keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}
