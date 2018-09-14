package com.upay.commons.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CfgFileReader {
    private final static Logger logger = LoggerFactory.getLogger(CfgFileReader.class);


    /**
     * xml配置文件转换成配置文件的实体对象
     * 
     * @param className
     *            需要转换的类
     * @param xmlPath
     *            配置文件在classpath下的相对路径
     * @return
     */
    public static <M> M parseXML(Class<M> className, String xmlPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(className);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ClassLoader loader = CfgFileReader.class.getClassLoader();
            InputStream is = loader.getResourceAsStream(xmlPath);
            @SuppressWarnings("unchecked")
            M m = (M) unmarshaller.unmarshal(is);

            return m;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取ClassPath目录下的资源文件
     * 
     * @param path
     *            目录（format-file）
     * @param filter
     *            文件后缀（.xml,.txt），可为空
     * @return path/filename 后续使用ClassLoader.getResourceAsSteam读取
     */
    public static List<String> getResourceFile(String path, final String extensions) {
        ClassLoader classLoader = CfgFileReader.class.getClassLoader();
        List<String> fileNameLists = new ArrayList<String>();

        try {
            Enumeration<URL> urls = classLoader.getResources(path);
            while (urls.hasMoreElements()) {
                URL temp = urls.nextElement();
                String protocol = temp.getProtocol();
                if ("jar".equals(protocol)) {
                    JarFile jarFile = null;
                    JarURLConnection connection = (JarURLConnection) temp.openConnection();
                    jarFile = connection.getJarFile();

                    for (Enumeration<JarEntry> en = jarFile.entries(); en.hasMoreElements();) {
                        JarEntry entry = en.nextElement();
                        final String entryName = entry.getName();
                        logger.debug("Current entry name is {}", entryName);
                        if (entryName.startsWith(path)) {
                            if ((extensions == null || extensions.trim().equals(""))
                                    || entryName.toUpperCase().endsWith(extensions.toUpperCase())) {
                                fileNameLists.add(entryName);
                            }
                        }
                    }
                } else if ("file".equals(protocol)) {
                    File file = new File(temp.getFile());
                    logger.debug(file.getAbsolutePath());
                    String[] files = file.list(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            if ((extensions == null || extensions.trim().equals(""))
                                    || name.toUpperCase().endsWith(extensions.toUpperCase()))
                                return true;
                            return false;
                        }
                    });
                    for (String f : files) {
                        fileNameLists.add(new StringBuilder(path).append(File.separatorChar).append(f)
                            .toString());
                    }
                } else {
                    throw new RuntimeException("Unknow Protocol [" + protocol + "]");
                }
            }
            return fileNameLists;
        } catch (Exception e) {
            throw new RuntimeException("获取资源文件出错", e);
        }
    }
}
