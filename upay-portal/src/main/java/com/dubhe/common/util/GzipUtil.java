
package com.dubhe.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;


/**
 * 压缩, 解压
 * 
 * @author Hing<xingguang.ren@pactera.com>
 * @since 2014年5月10日
 */
public class GzipUtil {

    /**
     * 压缩
     * 
     * @param bytes
     *            待压缩字节流
     * @return
     * @throws Exception
     */
    public static byte[] encode(byte[] bytes) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(baos);
            gos.write(bytes);
            baos.close();
            gos.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != baos) {
                baos.close();
            }
            if (null != gos) {
                gos.close();
            }
        }
        return baos.toByteArray();
    }


    /**
     * 解压
     * 
     * @param bytes
     *            待解压字节流
     * @return
     * @throws Exception
     */
    public static byte[] decode(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(bais);
            int bufferSize = 1024;
            byte[] b = new byte[bufferSize];
            int len = 0;
            while ((len = gis.read(b, 0, bufferSize)) != -1) {
                baos.write(b, 0, len);
            }
            bais.close();
            gis.close();
            baos.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != bais) {
                bais.close();
            }
            if (null != gis) {
                gis.close();
            }
            if (null != baos) {
                baos.close();
            }
        }
        return baos.toByteArray();
    }
    
    public static void main(String[] args) throws Exception {
    	String xml = "qZT0ShzL6wElGUUgtRlfd1OluXQYFgR5YQaCv8EPv7L6EIWVzWe4tRrJbVRWtWGGWHfWzVv+ZDQGirwBMasF9gj+fYllbyVirZAeh5ZNyy1bVdSGhH/mloOZeWARHN/HYAf50CrO9ogAcPkXR5stn8mIisvMvvgaAMalsUDME8zHDr4c9FO5+jNrh7J/ApLu2y0kpUk9FXE+foARbY3AWoI8m8WvykybnMmNWsc883SOma1FwXxDv5shUPXnWQ+UACCe+c9f0YbYhUHnkPKQF0iSV3TTKC+lrA6tAJZ8ecgGXD1A0xCi9wLVttPZmNIr07G5m6TxkC+fcTrbkwLxx8TnywasngEZUFRPZeVwMx4=";
//    	System.out.println(xml);
//    	System.out.println(xml.length());
//		byte[] encodeXml = encode(xml.getBytes());
//		String base64EncodeXml = Base64.encodeBase64String(encodeXml);
//		System.out.println(base64EncodeXml);
//		System.out.println(base64EncodeXml.length());
		byte[] decodexml = decode(Base64.decodeBase64(xml));
		String xmlreturn = new String(decodexml);
		System.out.println(xmlreturn);
	}
}
