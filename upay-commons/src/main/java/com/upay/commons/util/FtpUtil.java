package com.upay.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.presys.cp.ftp.client.bean.RequestParam;
import com.pactera.dipper.presys.cp.ftp.client.service.FTPClientFactory;
import com.pactera.dipper.presys.cp.ftp.client.service.FTPClientService;


/**
 * FTP工具类
 * 
 * @author jian.liu
 * 
 */
public class FtpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    private static List<File> files = new ArrayList<File>();
    // 核心ftp端口信息
    private String hostid = "";
    private int port;
    private String username = "";
    private String password = "";
    private String encoding = "";
    private String remoteroot = "";
    private boolean localactive = false;
    private String fileroot = "";
    private boolean encrypt = false;


    public FtpUtil(String servename) {
        ColumnFTP columnFTP = readFtpXmlToList(servename);
        hostid = columnFTP.getFtpserveid();
        port = columnFTP.getPort();
        username = columnFTP.getUsername();
        password = columnFTP.getPassword();
        encoding = columnFTP.getEncoding();
        remoteroot = columnFTP.getRemoteroot();
        localactive = columnFTP.getLocalactive();
        fileroot = columnFTP.getFileroot();
        encrypt = columnFTP.getEncrypt();
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // String hostid = "16.1.4.41";
        // int port = 21;
        // String username = "ylsj";
        // String password = "ylsj";
        // String remotePath = "/home/ylsj/pub/15142080/20130606";
        // String localPath = "D:\\home\\iccard\\files";
    }


    public static ColumnFTP readFtpXmlToList(String servename) {

        DataFTP data =
                FTPTemplateContext.getInstance().getConfig(TemplateTypeFTP.READ, ColumnFTP.class.getName());
        ColumnFTP columnFTP = null;
        int ftpServeSize = data.getColumnList().size();
        for (int i = 0; i < ftpServeSize; i++) {
            columnFTP = data.getColumnList().get(i);
            if (columnFTP.getServename().equals(servename)) {
                break;
            }

        }
        return columnFTP;
    }


    /**
     * 从FTP服务器下载文件
     * 
     * @param ftpType
     *            FTP服务器上的类型
     * @return List
     */
    public static List getFileNames(String ftpType, FtpUtil ftpUtil) {
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        List fileNamelist = new ArrayList<String>();
        String remotePath = "";
        try {
            // The integer value of the reply code of the last FTP reply
            int reply;
            if ("CORE".equals(ftpType)) {
                // 连接FTP服务器
                ftp.connect(ftpUtil.hostid, ftpUtil.port);
                // 登陆
                ftp.login(ftpUtil.username, ftpUtil.password);
                // 默认路径
                remotePath = ftpUtil.remoteroot;
            } else {
                // 连接FTP服务器
                ftp.connect(ftpUtil.hostid, ftpUtil.port);
                // 登陆
                ftp.login(ftpUtil.username, ftpUtil.password);
                // 默认路径
                remotePath = ftpUtil.remoteroot;
            }
            reply = ftp.getReplyCode();
            // 判断是否响应成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return null;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            // 遍历目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                fileNamelist.add(ff.getName());
               // System.out.println("name=" + ff.getName());

            }
            ftp.logout();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNamelist;
    }


    public static List<File> getFileList() {
        return files;
    }


    /**
     * 从FTP服务器下载文件
     * 
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public static boolean downFile(String remotePath, String localPath, FtpUtil ftpUtil) {
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        try {
            // The integer value of the reply code of the last FTP reply
            int reply;
            // 连接FTP服务器
            ftp.connect(ftpUtil.hostid, ftpUtil.port);
            // 登陆
            ftp.login(ftpUtil.username, ftpUtil.password);
            reply = ftp.getReplyCode();
            // 判断是否响应成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return flag;
            }

            // 遍历目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                File localFile = new File(localPath + "/" + ff.getName());
                OutputStream is = new FileOutputStream(localFile);
                ftp.retrieveFile(ff.getName(), is);
                files.add(localFile);
                is.close();
            }
            ftp.logout();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
   
    /**
     *  Description: FTP服务器上传文件
     *  
     * @param url  ftp 服务器地址
     * @param port ftp 服务器端口
     * @param username ftp 服务器登录用户名
     * @param password ftp 服务器登录密码
     * @param remotePath ftp 服务器目录
     * @param fileStream 上传的流
     * @param remoteFileName 上传到服务器上的文件名
     * @return
     */
    public static boolean upFile(String url, int port, String username, String password, String remotePath,InputStream fileStream,String remoteFileName) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
//            ftp.connect(url);
            ftp.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
           ftp.login(username, password);// 登录
           ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
           ftp.setControlEncoding("UTF-8");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			boolean  storeFile= ftp.storeFile(remoteFileName, fileStream);
			logger.debug("上传文件到FTP::::::::::::"+storeFile);
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * Description: 从FTP服务器下载文件
     * 
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public static boolean downFile(String url, int port, String username, String password, String remotePath,
            String fileName, String localPath) {
    	System.out.println(url);
    	System.out.println(port);
    	System.out.println(username);
    	System.out.println(password);
    	System.out.println(remotePath);
    	System.out.println(fileName);
    	System.out.println(localPath);
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
//            ftp.connect(url);
            ftp.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
           ftp.login(username, password);// 登录
           ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
           ftp.setControlEncoding("UTF-8");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            
//            FTPFile[] fs = ftp.listFiles();
            File localFile = new File(localPath + "/" + fileName);
            OutputStream is = new FileOutputStream(localFile);
            success = ftp.retrieveFile(fileName, is);
            if(success){
            	logger.debug("下载烟草转账文件成功");
            }else{
            	logger.debug("下载烟草转账文件失败");
            }
            is.flush();
            is.close();

            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }


    /**
     * 文件上传下载
     * 
     * @param fileName
     *            文件名
     * @param flag
     *            true 上传/false 下载
     * @param path
     *            本地路径
     * @return
     * @throws Exception
     */
    public boolean ftpFile(String fileName, boolean flag, String path) throws Exception {

        RequestParam reqParam = new RequestParam();
        reqParam.setHost(hostid);
        reqParam.setPort(port);
        reqParam.setUsername(username);
        reqParam.setPassword(password);
        reqParam.setEncoding(encoding);
        reqParam.setLocalActive(localactive);
        reqParam.setRootDir(remoteroot + fileroot);
        reqParam.setRemoteFileName(fileName);
        reqParam.setLocalFileName(path + fileName);
        reqParam.setEncrypt(encrypt);
        FTPClientService ftpClientService = FTPClientFactory.createSFtpClientService();
        ftpClientService.setParam(reqParam);
        boolean bool = false;
        try {

            if (flag) {
                bool = ftpClientService.uploadFile();
            } else {
                bool = ftpClientService.downloadFile();
            }
        } catch (Exception e) {
            // TODO: handle exception
            bool = false;
            logger.error("链接FTP服务器异常");
        }
        return bool;
    }


    /**
     * 文件上传下载
     * 
     * @param fileName
     *            文件名
     * @param flag
     *            true 上传/false 下载
     * @param path
     *            本地路径
     * @param filepatch
     *            服务器的文件子路径
     * @return
     * @throws Exception
     */
    public boolean ftpFile(String fileName, boolean flag, String path, String filepatch) throws Exception {

        RequestParam reqParam = new RequestParam();
        reqParam.setHost(hostid);
        reqParam.setPort(port);
        reqParam.setUsername(username);
        reqParam.setPassword(password);
        reqParam.setEncoding(encoding);
        reqParam.setLocalActive(localactive);
        reqParam.setRootDir(remoteroot + filepatch);
        reqParam.setRemoteFileName(fileName);
        reqParam.setLocalFileName(path + fileName);
        reqParam.setEncrypt(encrypt);
        FTPClientService ftpClientService = FTPClientFactory.createSFtpClientService();
        ftpClientService.setParam(reqParam);
        boolean bool = false;
        try {

            if (flag) {
                bool = ftpClientService.uploadFile();
            } else {
                bool = ftpClientService.downloadFile();
            }
        } catch (Exception e) {
            // TODO: handle exception
            bool = false;
            logger.error("链接FTP服务器异常");
        }
        return bool;
    }
}
