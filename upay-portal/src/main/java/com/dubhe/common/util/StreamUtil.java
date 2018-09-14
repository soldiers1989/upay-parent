package com.dubhe.common.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


/**
 * @author Hing<xingguang.ren@pactera.com>
 * @since 2014年4月19日
 */
public class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);
    private static final String CHARSET = "UTF-8";
    /**
     * 上行报文压缩
     */
    private static boolean GZIP_UP = false;

    /**
     * 下行报文压缩
     */
    private static boolean GZIP_DOWN = false;

    public static String readStream(HttpServletRequest request) throws Exception {
        InputStream is = null;
        try {
            is = request.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            byte[] reqBytes = null;
            while ((len = is.read(bytes)) != -1) {
                reqBytes = ArrayUtils.addAll(reqBytes, ArrayUtils.subarray(bytes, 0, len));
            }
            is.close();
            String receive = new String(reqBytes, CHARSET);
            logger.info("receive:{}", "\n" + JSON.toJSONString(JSON.parse(receive),true));
            return receive;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != is) {
                is.close();
            }
        }
    }

    public static byte[] readStream(HttpServletRequest request, String channel, boolean isGzipUp) throws Exception {
        InputStream is = null;
        try {
            is = request.getInputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            byte[] reqBytes = null;
            while ((len = is.read(bytes)) != -1) {
                reqBytes = ArrayUtils.addAll(reqBytes, ArrayUtils.subarray(bytes, 0, len));
            }
            is.close();

            // 解压报文
            if (isGzipUp) {
                reqBytes = GzipUtil.decode(reqBytes);
            }

            logger.info("{} receive:{}", channel, new String(reqBytes, CHARSET));

            return reqBytes;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != is) {
                is.close();
            }
        }
    }
    
    public static void writeStream(HttpServletResponse response, byte[] bytes, String channel)
            throws Exception {
        writeStream(response, bytes, channel, "text/xml; charset=".concat(CHARSET));
    }


    public static void writeJson(HttpServletResponse response, String json, String channel, String charset)
            throws Exception {
        writeStream(response, json.getBytes(), channel, "application/json; charset=".concat(charset));
    }
    
    public static void writeByteStream(HttpServletResponse response, byte[] rspBytes) throws Exception {
        response.setContentType("application/json; charset=".concat(CHARSET));
        String jsonstr=new String(rspBytes, CHARSET);
        logger.info("response:{}", "\n" + jsonstr);
        writeStream(response, jsonstr);
    }

    public static void writeStream(HttpServletResponse response, byte[] bytes, String channel,
            String contentType) throws Exception {
        response.setContentType(contentType);
        logger.info("{} response:{}", channel, new String(bytes, CHARSET));

        OutputStream os = null;
        try {
            if (GZIP_DOWN) {
                response.setHeader("Content-Encoding", "gzip");
                bytes = GzipUtil.encode(bytes);

            }
            os = response.getOutputStream();
            os.write(bytes);
            os.close();
            return;
        } catch (Exception e) {
            throw e;
        } finally {
            if (os != null) {
                os.close();
            }
        }

    }

    public static void writeStream(HttpServletResponse response, Object object) throws Exception {
        response.setContentType("application/json; charset=".concat(CHARSET));
        String jsonstr = replace(JSON.toJSONString(object));
        logger.info("response:{}", "\n" + JSON.toJSONString(object,true));
        writeStream(response, jsonstr);
    }

    public static void writeStream(HttpServletResponse response, String jsonstr) throws Exception {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(jsonstr);
            pw.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != pw) {
                pw.close();
            }
        }
    }
    
    private static String replace(String s) {
		int i = 0;

		StringBuffer sb = new StringBuffer();
		char[] ss = s.toCharArray();
		for (char ss1 : ss) {
			if (i > 1) {
				if (ss1 == ',' && ss[i - 1] != '"' && ss[i + 1] == '"'&&ss[i-1]!='}'&&ss[i-1]!=']')
					sb.append('"');
			}
			if(i<ss.length-1)
				if (ss1 == '}' &&ss[i-1]!='"'&&ss[i-1]!='{'&&ss[i-1]!=']')
					sb.append('"');

			sb.append(ss1);
			if (i > 1) {
				if (ss1 == ':' && ss[i + 1] != '"' && ss[i + 1] != '['&& ss[i + 1] != '{' && ss[i - 1] == '"')
					sb.append('"');
			}
			i++;

		}
		return sb.toString();
	}
    
}
