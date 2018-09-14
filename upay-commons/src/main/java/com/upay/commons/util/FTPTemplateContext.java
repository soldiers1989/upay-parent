package com.upay.commons.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 数据文件模板缓存 含分隔符的文件必须有换行符 没有换行符或者没有分隔符，必须设置字段的长度（必须是定长文件）
 * 
 * @author liujian
 * @since 2014年8月05日
 */
public class FTPTemplateContext {
    private static final Logger logger = LoggerFactory.getLogger(FTPTemplateContext.class);
    private static FTPTemplateContext instance;

    private Map<String, DataFTP> configurations = new ConcurrentHashMap<String, DataFTP>();

    private final String fileDir = "templates/ftp";


    private FTPTemplateContext() {
    }


    public static FTPTemplateContext getInstance() {
        if (null == instance) {
            synchronized (FTPTemplateContext.class) {
                if (null == instance) {
                    instance = new FTPTemplateContext();
                    try {
                        instance.loadTemplateFileDir();
                    } catch (Exception e) {
                        throw new RuntimeException("初始化加载文件失败", e);
                    }
                }
            }
        }
        return instance;
    }


    /**
     * 
     * @param templateType
     * @param key
     *            文件内容对应的类的className
     * @return
     */
    public DataFTP getConfig(TemplateTypeFTP templateType, String key) {
        DataFTP data = this.configurations.get(templateType + key);
        if (data == null) {
            throw new RuntimeException("配置模板" + templateType + "[" + key + "]不存在");
        }
        return data;
    }


    private void loadTemplateFileDir() throws Exception {
        configurations.clear();
        List<String> subFiles = getResourceFile(fileDir, ".xml");
        for (String subFile : subFiles) {
            DataFTP data = parseXML(DataFTP.class, subFile);

            String clazz = data.getClazz();
            this.configurations.put(data.getTemplateType() + clazz, data);

        }
    }


    private List<String> getResourceFile(String path, final String extensions) {
        ClassLoader classLoader = getClass().getClassLoader();
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


    private <M> M parseXML(Class<M> className, String xmlPath) {
        try {
            JAXBContext context = JAXBContext.newInstance(className);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ClassLoader loader = getClass().getClassLoader();
            InputStream is = loader.getResourceAsStream(xmlPath);
            @SuppressWarnings("unchecked")
            M m = (M) unmarshaller.unmarshal(is);

            return m;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}


/** 模板文件类型定义 */
enum TemplateTypeFTP {
    READ,
    WRITE
};


@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
class DataFTP {

    @XmlAttribute(name = "templateType")
    private TemplateTypeFTP templateType;


    public TemplateTypeFTP getTemplateType() {
        return templateType;
    }


    public void setTemplateType(TemplateTypeFTP templateType) {
        this.templateType = templateType;
    }

    /** 对应到class类 */
    @XmlAttribute(name = "clazz")
    private String clazz;

    @XmlElement(name = "column")
    private List<ColumnFTP> columnList;


    public List<ColumnFTP> getColumnList() {
        return columnList;
    }


    public void setColumnList(List<ColumnFTP> columnList) {
        this.columnList = columnList;
    }


    public String getClazz() {
        return clazz;
    }


    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}


@XmlRootElement(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
class ColumnFTP {

    /** FTP 服务器地址 */
    @XmlAttribute(name = "ftpserveid")
    private String ftpserveid;
    /** 端口 */
    @XmlAttribute(name = "port")
    private int port;

    /** 登录名称 */
    @XmlAttribute(name = "username")
    private String username;
    /** 登录密码 */
    @XmlAttribute(name = "password")
    private String password;

    /** 字符集 */
    @XmlAttribute(name = "encoding")
    private String encoding;

    /**  */
    @XmlAttribute(name = "localactive")
    private boolean localactive;

    /** 公共目录 */
    @XmlAttribute(name = "remoteroot")
    private String remoteroot;

    /** 私有文件目录 */
    @XmlAttribute(name = "fileroot")
    private String fileroot;
    /** FTP 内部识别名称 */
    @XmlAttribute(name = "servename")
    private String servename;
    /** FTP 是否加密 */
    @XmlAttribute(name = "encrypt")
    private boolean encrypt;


    public boolean getEncrypt() {
        return encrypt;
    }


    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }


    public String getServename() {
        return servename;
    }


    public void setServename(String servename) {
        this.servename = servename;
    }


    public String getFtpserveid() {
        return ftpserveid;
    }


    public void setFtpserveid(String ftpserveid) {
        this.ftpserveid = ftpserveid;
    }


    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEncoding() {
        return encoding;
    }


    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public boolean getLocalactive() {
        return localactive;
    }


    public void setLocalactive(boolean localactive) {
        this.localactive = localactive;
    }


    public String getRemoteroot() {
        return remoteroot;
    }


    public void setRemoteroot(String remoteroot) {
        this.remoteroot = remoteroot;
    }


    public String getFileroot() {
        return fileroot;
    }


    public void setFileroot(String fileroot) {
        this.fileroot = fileroot;
    }

    /** 值转换 tranSrc 和 tranDes 转换成map */
    private Map<String, String> transMap = new HashMap<String, String>();


    /** 根据源值获取转换后的值 */
    public String trans(String source) {
        if (source != null) {
            String dest = transMap.get(source);
            if (dest == null)
                throw new RuntimeException("trans error:source [" + source + "] has no dest ");
            return dest;
        }
        return null;
    }


    public void addTrans(String source, String dest) {
        this.transMap.put(source, dest);
    }


    /** 判断是否需要转换数据 */
    public boolean isNeedTrans() {
        return transMap.size() > 0;
    }
}
