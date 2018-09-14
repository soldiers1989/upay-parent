package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.gnr.FileInfoPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;


/**
 * 批量转账 - 第一步
 * 下载为转账文件并记录文件信息。
 * 每日的清分文件在远程服务器上必须按日期存放，日期做为目录（格式：YYYYMMDD）。
 * @author xu
 */
public class DownloadTransferFileStep extends AbstractStepExecutor<Object, Object> {

    /** 日志logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadTransferFileStep.class);

    /** 平台序列 */
    @Resource
    private ISequenceService sequenceService;

    /** 转账文件本地存入路径 */
    private String localPath;

    /** 转账文件远程服务器存入路径 */
    private String remotePath;

    /** FTP服务器地址 */
    private String host;

    /** FTP服务器端口 */
    private int port;

    /** FTP服务器用户名 */
    private String username;

    /** FTP服务器登录密码 */
    private String password;


    /**
     * 扫描远程服务器目录，下载转账文件，记录文件信息
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index, Object data, Object object)
            throws BatchException {

        LOGGER.info("----- 批量转账 -- 转账文件下载并记录文件信息开始...");
        //判断是否是指定日期执行
        if (batchParams.getParameter().containsKey("tranDate")) {
            Date tranDate = (Date) batchParams.getParameter().get("tranDate");
            Date preDate = (Date) batchParams.getParameter().get("preDate");
            batchParams.setTranDate(tranDate);
            batchParams.setPreDate(preDate);
        }

        // 当前交易日期转账文件存放目录
        String transDateStr = DateUtil.format(batchParams.getTranDate(), "yyyyMMdd");
        String localFullPath = localPath + transDateStr + File.separator;

        //用于第三步  获取数据入库使用
     //   batchParams.getParameter().put("localFullPath", localFullPath);


        LOGGER.debug("localFullPath:" + localFullPath);
        // 远程服务器目录
        String remoteFullPath = remotePath + transDateStr + File.separator;
        LOGGER.debug("remoteFullPath:" + remoteFullPath);
        // 判断本地目录是否存在，如果不存在则创建
        File localPathFile = new File(localFullPath);
        if (!localPathFile.exists() || !localPathFile.isDirectory()) {
            boolean mkdirResult = localPathFile.mkdirs();
            // 创建目录失败
            if (!mkdirResult) {
                throw new BatchException("创建目录[" + localFullPath + "]失败");
            }
        }


        //查询需要下载的文件列表
        FileInfoPo fileInfo = new FileInfoPo();
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_INIT);//初始化状态文件信息
        fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_TRANSFER); // 接收代理人 转账文件
        fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_DOWNLOAD);
        List<FileInfoPo> fileList = daoService.selectList(fileInfo);
        if (null != fileList && fileList.size() > 0) {
            for (FileInfoPo filePo : fileList) {
                String fileName = filePo.getFileName();
                //fileName=fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
                //TODO 这里需不需要判断这个文件在本地是否存在  ！！！！？？？？？

                //去ESB取核心文件
                boolean esbFtpGet = FileUtil.ESBFtpGet("/" + fileName, localFullPath + fileName, "CBS");
                if (esbFtpGet) {
                    LOGGER.debug("去ESB 取转账文件：" + fileName + "  成功!!!!!!!!!!");
                    //更新文件信息状态为下载成功  和批次号 和最新的更新日期
                    FileInfoPo setFileInfo = new FileInfoPo();
                    FileInfoPo wehereFileInfo = new FileInfoPo();

                    setFileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_DOWNLOAD_SUC);//下载成功
                    setFileInfo.setBatchNo(batchParams.getBatchNo());
                    setFileInfo.setUpdateTime(new Date());
                    wehereFileInfo.setId(filePo.getId());

                    daoService.update(setFileInfo, wehereFileInfo);
                } else {
                    LOGGER.debug("去ESB 取转账文件：" + fileName + "  失败!!!!!!!!!!");
                }
            }
        }
    }
        


    public String getLocalPath() {
		return localPath;
	}



	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}



	public String getRemotePath() {
		return remotePath;
	}



	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}



	public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
}
