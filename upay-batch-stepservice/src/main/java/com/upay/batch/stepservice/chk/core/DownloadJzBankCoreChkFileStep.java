package com.upay.batch.stepservice.chk.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.dto.ExecutionType;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;


public class DownloadJzBankCoreChkFileStep extends AbstractStepExecutor<Map<String, String>, Object> {

    private static final Logger log = LoggerFactory.getLogger(DownloadJzBankCoreChkFileStep.class);

    private String coreLocalPath;// 核心文件本能地存放路径
    private String coreRemotePath;// 核心对账文件远程存放路径

    private String url;// FTP服务器地址
    private int port;// FTP服务器端口
    private String username;// FTP服务器用户名
    private String password;// FTP服务器登录密码


    @Override
    public List<Map<String, String>> getObjectList(BatchParams batchParams) throws BatchException {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> coreChkMap = new HashMap<String, String>();
        coreChkMap.put("LocalPath", coreLocalPath);
        coreChkMap.put("RemotePath", coreRemotePath);
        list.add(coreChkMap);
        return list;
    }


    @Override
    public void execute(BatchParams batchParams, int index, Object data, Map<String, String> object)
            throws BatchException {
    	
        log.info("-------------DownloadJzBankCoreChkFileSteps 下载开始");
        StringBuffer localPath = new StringBuffer(object.get("LocalPath"));
        StringBuffer remotePath = new StringBuffer(object.get("RemotePath"));

        // 本地文件目录
        localPath.append(DateUtil.format(batchParams.getPreDate(), "yyyyMMdd"));
        localPath.append(File.separator);

        // 远程文件目录
        ChkInfoPo chkinfoPo = new ChkInfoPo();
        chkinfoPo.setBatchNo(batchParams.getBatchNo());
        chkinfoPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        chkinfoPo = daoService.selectOne(chkinfoPo);

        if (null == chkinfoPo) {
            throw new BatchException("--------未登记对账文件信息！！！");
        }

        this.downFile(url, port, username, password, remotePath.toString(), localPath.toString(),
            chkinfoPo.getChkFile());
        
        File file = new File(localPath.toString()+chkinfoPo.getChkFile());
        if (!file.exists()) {
        	throw new BatchException("核心对账文件下载失败!");
        }
        
        log.info("-------------DownloadJzBankCoreChkFileSteps 下载完成");
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
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public boolean downFile(String url, int port, String username, String password, String remotePath,
            String localPath, String fileName) {
    	
        boolean success = false;
        FTPClient ftp = new FTPClient();
        OutputStream is = null;
        try {
            int reply;
            ftp.addProtocolCommandListener(new ProtocolCommandListener() {

                @Override
                public void protocolReplyReceived(ProtocolCommandEvent event) {
                    // TODO Auto-generated method stub
                    log.info("----------------receive" + event.getMessage() + "---replyCode:"
                            + event.getReplyCode());
                }


                @Override
                public void protocolCommandSent(ProtocolCommandEvent event) {
                    // TODO Auto-generated method stub
                    log.info("----------------send" + event.getCommand());
                }
            });
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
            /*ftp.enterLocalPassiveMode();*/
            boolean isExist = false;
            log.info("--------fileName:[{}]", fileName);
            log.info("下载到临时目录：" + localPath + fileName);
            File localFilePath = new File(localPath);
            if (!localFilePath.exists()) {
                localFilePath.mkdirs();
            }
            File localFile = new File(localPath + fileName);
            if (localFile.exists()) {
                localFile.delete();
            }
            is = new FileOutputStream(localFile);
            isExist = ftp.retrieveFile(fileName, is);

            if (!isExist) {
                throw new BatchException("-----------核心对账不存在！！！");
            }

            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BatchException("--------DownloadCoreChkFile has an excepion");
        } finally {
            IOUtils.closeQuietly(is);
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    throw new BatchException("--------DownloadCoreChkFile ftp disconnect has an excepion");
                }
            }
        }
        return success;
    }
   


    @Override
	public void updateObject(BatchParams batchParams, Map<String, String> object) {
    	log.info("文件下载 end...");
        String batchNo = batchParams.getBatchNo();
        ChkInfoPo chkInfoPo = new ChkInfoPo();
        chkInfoPo.setBatchNo(batchNo);
        ChkInfoPo updatePo = new ChkInfoPo();
        updatePo.setFileDownStat("3");
        daoService.update(updatePo, chkInfoPo);
		super.updateObject(batchParams, object);
	}


	public String getCoreLocalPath() {
        return coreLocalPath;
    }


    public void setCoreLocalPath(String coreLocalPath) {
        this.coreLocalPath = coreLocalPath;
    }


    public String getCoreRemotePath() {
        return coreRemotePath;
    }


    public void setCoreRemotePath(String coreRemotePath) {
        this.coreRemotePath = coreRemotePath;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public int getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = Integer.valueOf(port);
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

}
