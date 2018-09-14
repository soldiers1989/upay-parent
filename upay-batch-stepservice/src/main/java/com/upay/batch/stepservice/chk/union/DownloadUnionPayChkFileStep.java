package com.upay.batch.stepservice.chk.union;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.ChkInfoPo;


/**
 * 下载银联对账文件
 * 
 * @author Pactera
 *
 */
public class DownloadUnionPayChkFileStep extends AbstractStepExecutor<Map<String, String>, Object> {

    private static final Logger log = LoggerFactory.getLogger(DownloadUnionPayChkFileStep.class);

    private String singlePayLocalPath;// 代付文件存放路径
    private String singleCollectionLocalPath;// 代收文件存放路径
    private String singlePayRemotePath;// 代付文件存放路径
    private String singleCollectionRemotePath;// 代收文件存放路径

    private String url;// FTP服务器地址
    private int port;// FTP服务器端口
    private String username;// FTP服务器用户名
    private String password;// FTP服务器登录密码
    // private String remotePath;// 远程服务器文件存放地址


    @Override
    public List<Map<String, String>> getObjectList(BatchParams batchParams) throws BatchException {
        // TODO Auto-generated method stub

        List<Map<String, String>> pathList = new ArrayList<Map<String, String>>();
        Map<String, String> singlePayMap = new HashMap<String, String>();
        Map<String, String> singleCollection = new HashMap<String, String>();

        singlePayMap.put("LocalPath", singlePayLocalPath);
        singlePayMap.put("RemotePath", singlePayRemotePath);
        singlePayMap.put("tranFileType", "pay");

        singleCollection.put("LocalPath", singleCollectionLocalPath);
        singleCollection.put("RemotePath", singleCollectionRemotePath);
        singleCollection.put("tranFileType", "collection");

        pathList.add(singlePayMap);
        pathList.add(singleCollection);
        log.info("-------------DownloadUnionPayChkFileStep download start object.Size[{}]", pathList.size());
        return pathList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, Object data, Map<String, String> object)
            throws BatchException {
        // TODO Auto-generated method stub
        log.debug("-------------DownloadUnionPayChkFileStep download start");

        String ftpFileName = null;
        StringBuffer localPath = new StringBuffer(object.get("LocalPath"));
        StringBuffer remotePath = new StringBuffer(object.get("RemotePath"));
        StringBuffer tranFileType = new StringBuffer(object.get("tranFileType"));

        // 本地文件目录
        localPath.append(DateUtil.format(batchParams.getPreDate(), "yyyyMMdd"));
        // localPath.append(File.separator);
        localPath.append("/");

        // 远程文件目录
        remotePath.append(DateUtil.format(batchParams.getPreDate(), "yyyyMMdd"));
        // remotePath.append(File.separator);
        remotePath.append("/");

        log.debug("-------------URL:[{}]", url);
        log.debug("------------Port:[{}]", port);
        log.debug("--------UserName:[{}]", username);
        log.debug("--------Password:[{}]", password);
        log.debug("-------LocalPath:[{}]", localPath.toString());
        log.debug("------RemotePath:[{}]", remotePath.toString());

        ftpFileName = this.downFile(url, port, username, password, remotePath.toString(),
            localPath.toString(), batchParams, ftpFileName, tranFileType.toString());

        log.info("-----ftpFileName:[{}]", ftpFileName);
        if (StringUtils.isNotBlank(ftpFileName)) {
            this.insertChkInfo(batchParams, ftpFileName);
        } else {
            new BatchException("-------------DownloadUnionPayChkFileStep file is not exists");
        }

        log.info("-------------DownloadUnionPayChkFileStep download end");

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
    public String downFile(String url, int port, String username, String password, String remotePath,
            String localPath, BatchParams batchParms, String ftpFileName, String tranFileType) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
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
            ftp.enterLocalPassiveMode();
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new BatchException("---------------银联文件FTP连接不通！！！reply[" + reply + "]");
            }
            boolean changeResult = ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            if (!changeResult) {
                throw new BatchException("---------------银联对账文件的路径及文件不存在！！！");
            }
            String[] fs = ftp.listNames();
            reply = ftp.getReplyCode();
            log.info("---------reply list names[{}]", reply);
            boolean isExist = false;
            String endWtih = null;
            for (String ff : fs) {

                if ("pay".equals(tranFileType)) {
                    endWtih = "ACOMN";
                } else {
                    endWtih = "ACOMA";
                }
                if (ff.startsWith("IND") && ff.endsWith(endWtih)) {
                    // 判断代收代付的文件是否存在，但未指定是哪一天的代收代付文件
                    if (ff.startsWith("IND") && ff.endsWith(endWtih))
                        // 代收文件，但未指定是哪一天的
                        isExist = true;
                    log.info("--------fileName:[{}]", ff);
                    log.info("下载到临时目录：" + url + localPath + ff);
                    ftpFileName = ff;
                    File localFilePath = new File(localPath);
                    if (!localFilePath.exists()) {
                        localFilePath.mkdirs();
                    }
                    File localFile = new File(localPath + ff);
                    if (localFile.exists()) {
                        localFile.delete();
                    }
                    OutputStream is = new FileOutputStream(localFile);
                    boolean exist = ftp.retrieveFile(ff, is);
                    if (!exist) {
                        throw new BatchException("-----------银联对账文件[{" + ff + "}]不存在！！！");
                    }
                    is.close();
                    // 登记对账明细
                    // this.insertChkInfo(batchParms, ff.getName());
                }
            }
            if (!isExist) {
                throw new BatchException("---------------银联对账文件" + endWtih + "不存在！！！");
            }
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BatchException("--------DownloadUnionPayChkFile has an excepion");
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    throw new BatchException(
                        "--------DownloadUnionPayChkFile ftp disconnect has an excepion");
                }
            }
        }
        return ftpFileName;
    }


    /**
     * 
     * @return
     */
    public void insertChkInfo(BatchParams batchParams, String fileName) {

        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        chkInfo.setChkFile(fileName);
        chkInfo = this.daoService.selectOne(chkInfo);
        if (chkInfo == null) {
            chkInfo = new ChkInfoPo();
            chkInfo.setBatchNo(batchParams.getBatchNo());
            chkInfo.setChkFile(fileName);
            chkInfo.setChkDate(batchParams.getTranDate());
            chkInfo.setBenchmarkFlag("03");
            chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
            chkInfo.setOrgType("002");// 资金通道
            chkInfo.setChkTime(new Date());
            chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
            chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);
            chkInfo.setFileType("0");// 下载
            chkInfo.setFileDownStat("3");// 下载成功
            daoService.insert(chkInfo);
        }
        log.info("-------------DownloadUnionPayChkFileStep insert ChkInfo fileName[{}]", fileName);

    }


    public String getSinglePayLocalPath() {
        return singlePayLocalPath;
    }


    public void setSinglePayLocalPath(String singlePayLocalPath) {
        this.singlePayLocalPath = singlePayLocalPath;
    }


    public String getSingleCollectionLocalPath() {
        return singleCollectionLocalPath;
    }


    public void setSingleCollectionLocalPath(String singleCollectionLocalPath) {
        this.singleCollectionLocalPath = singleCollectionLocalPath;
    }


    public String getSinglePayRemotePath() {
        return singlePayRemotePath;
    }


    public void setSinglePayRemotePath(String singlePayRemotePath) {
        this.singlePayRemotePath = singlePayRemotePath;
    }


    public String getSingleCollectionRemotePath() {
        return singleCollectionRemotePath;
    }


    public void setSingleCollectionRemotePath(String singleCollectionRemotePath) {
        this.singleCollectionRemotePath = singleCollectionRemotePath;
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
