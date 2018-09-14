package com.upay.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upay.commons.constants.FtpParmConstants;


/**
 * zip文件压缩解压
 * 
 * @author zacks
 * 
 */
public class ZipUtil {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);
    private static final int BUFFER = 1024 * 10;


    /**
     * 将指定目录压缩到和该目录同名的zip文件，自定义压缩路径
     * 
     * @param sourceFilePath
     *            目标文件路径
     * @param zipFilePath
     *            指定zip文件路径
     * @return
     */
    public static boolean zip(String sourceFilePath, String zipFilePath) {
        boolean result = false;
        if (StringUtils.isBlank(sourceFilePath)) {
            logger.info("源文件路径不可为空!");
            return result;
        }
        if (StringUtils.isBlank(zipFilePath)) {
            logger.info("压缩文件路径不可为空!");
            return result;
        }
        if (sourceFilePath.equals(zipFilePath)) {
            logger.info("源文件路径不能与压缩文件路径相同!");
            return result;
        }
        File source = new File(sourceFilePath);
        if (!source.exists()) {
            logger.info("源文件路径[{}]不存在!", sourceFilePath);
            return result;
        }
        if (!source.isDirectory()) {
            logger.info("源文件路径[{}]错误!", sourceFilePath);
            return result;
        }
        File zipFile =
                new File(zipFilePath + FtpParmConstants.SEPARATOR + source.getName()
                        + FtpParmConstants.SUFFIX);
        if (zipFile.exists()) {
            // logger.info("压缩文件[{}]已存在,请检查!", zipFile.getName());
            // return result;
            zipFile.delete();
        } else {
            if (!zipFile.getParentFile().exists()) {
                if (!zipFile.getParentFile().mkdirs()) {
                    logger.info("不能创建压缩文件[{}] ", zipFile.getName());
                    return result;
                }
            }
        }
        logger.info("********************文件开始压缩********************");
        FileOutputStream dest = null;
        ZipOutputStream out = null;
        try {
            dest = new FileOutputStream(zipFile);
            CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
            out = new ZipOutputStream(new BufferedOutputStream(checksum));
            out.setMethod(ZipOutputStream.DEFLATED);
            compress(source, out, source.getName());
            result = true;
        } catch (FileNotFoundException e) {
            logger.error("[{}]", e);
        } finally {
            if (out != null) {
                try {
                    out.closeEntry();
                } catch (IOException e) {
                    logger.error("[{}]", e);
                }
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("[{}]", e);
                }
            }
        }
        if (result) {
            logger.info("********************文件压缩完成********************");
        } else {
            logger.info("********************文件压缩失败********************");
        }
        return result;
    }


    private static void compress(File file, ZipOutputStream out, String mainFileName) {
        if (file.isFile()) {
            FileInputStream fi = null;
            BufferedInputStream origin = null;
            try {
                fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                int index = file.getAbsolutePath().indexOf(mainFileName);
                String entryName = file.getAbsolutePath().substring(index);
                logger.info(entryName);
                ZipEntry entry = new ZipEntry(entryName);
                out.putNextEntry(entry);
                byte[] data = new byte[BUFFER];
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            } catch (FileNotFoundException e) {
                logger.error("[{}]", e);
            } catch (IOException e) {
                logger.error("[{}]", e);
            } finally {
                if (origin != null) {
                    try {
                        origin.close();
                    } catch (IOException e) {
                        logger.error("[{}]", e);
                    }
                }
            }
        } else if (file.isDirectory()) {
            File[] fs = file.listFiles();
            if (fs != null && fs.length > 0) {
                for (File f : fs) {
                    compress(f, out, mainFileName);
                }
            }
        }
    }


    /**
     * 解压到指定路径下
     * 
     * @param zipFile
     * @param descDir
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static void unzip(File zipFile, String descDir) throws Exception {

        if (!File.separator.equals(descDir.charAt(descDir.length() - 1))) {
            descDir += File.separator;
        }
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        logger.info("********************文件开始解压********************");
        try {
            zip = new ZipFile(zipFile);
            for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                String outPath = (descDir + zipEntryName).replaceAll("\\*", File.separator);
                // 判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                // 输出文件路径信息
                logger.info(outPath);

                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
        } catch (Exception e) {
            logger.error("解压失败:[{}]", e);
            throw e;
        } finally {
            if (zip != null) {
                try {
                    zip.close();
                    zip = null;
                } catch (Exception e) {
                    logger.error("解压失败:[{}]", e);
                    throw e;
                }
            }
        }
        logger.info("********************文件解压完毕********************");
    }
    /** 
     * 解压文件到指定目录 
     * 解压后的文件名，和之前一致 
     * @param zipFile   待解压的zip文件 
     * @param descDir   指定目录 
     */  
    @SuppressWarnings("rawtypes")  
    public static void unZipFiles(File zipFile, String descDir) throws IOException {  
          
        ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));//解决中文文件夹乱码  
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));  
          
        File pathFile = new File(descDir+name);  
        if (!pathFile.exists()) {  
            pathFile.mkdirs();  
        }  
          
        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {  
            ZipEntry entry = (ZipEntry) entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir + name +"/"+ zipEntryName).replaceAll("\\*", "/");  
              
            // 判断路径是否存在,不存在则创建文件路径  
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
            if (new File(outPath).isDirectory()) {  
                continue;  
            }  
            // 输出文件路径信息  
//          System.out.println(outPath);  
  
            FileOutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while ((len = in.read(buf1)) > 0) {  
                out.write(buf1, 0, len);  
            }  
            in.close();  
            out.close();  
        }  
        logger.info("******************解压完毕********************");  
        return;  
    }  

    public static void main(String[] ss) throws Exception {
        // String path = unzip(new
        // File("/zipTest/142510160120003_20150604.zip"), "/zipTest");
        // logger.info("==============================解压完成" + path);
    	unZipFiles(new File("D:/alipaychk/2016090900468878_20171207.zip"), "D:/alipaychk/");  
//        zip("/zipTest/aaaaaa", "/zipTest/");

        // logger.info("==============================解压完成" + path);
    }
}