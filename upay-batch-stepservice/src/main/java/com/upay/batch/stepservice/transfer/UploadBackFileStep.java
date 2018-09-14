package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.po.gnr.FileInfoPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * 批量转账 - 第六步 上传回盘文件。
 *
 * @author zhangjianfeng
 * @since 2017/02/20 13:05
 */
public class UploadBackFileStep extends AbstractStepExecutor<Object, FileInfoPo> {


    @Resource(name = "esbCliDipperHandler")
    private IDipperHandler<Message> esbCliDipperHandler;

    /**
     * 日志记录。
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadBackFileStep.class);

    /**
     * 转账文件远程服务器存入路径
     */
    private String remotePath;// 微信存放路径

    /**
     * FTP服务器地址
     */
    private String host;

    /**
     * FTP服务器端口
     */
    private int port;

    /**
     * FTP服务器用户名
     */
    private String username;

    /**
     * FTP服务器登录密码
     */
    private String password;



    /** 转账文件本地存入路径 */
    private String localPath;


    /**
     * 获取需要上传的微信回盘文件信息
     *
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<FileInfoPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object) throws BatchException {


        FileInfoPo fileInfo = new FileInfoPo();
        fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_BACK_FILE);
        fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_UPLOAD);
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_PROCESSED);

        List<FileInfoPo> winXinBackFileInfos = daoService.selectList(fileInfo);

        LOGGER.info("----- 批量转账 -- {}个文件回盘文件待上传", new Object[]{winXinBackFileInfos.size()});
        return winXinBackFileInfos;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * 上传微信回盘文件
     *
     * @param batchParams

     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index, FileInfoPo data, Object object) throws BatchException {
     //   String localFullPath = (String) batchParams.getParameter().get("localFullPath");
        // 当前交易日期转账文件存放目录
        String transDateStr = DateUtil.format(batchParams.getTranDate(), "yyyyMMdd");
        String localFullPath = localPath + transDateStr + File.separator;
        try {
//本地文件
            File file = new File(localFullPath + data.getFileName());

            // FTP上传后的文件名。
            String remoteFileName = file.getName();
            // FTP上传文件日期目录。
            String preDateStr = DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDD);
            String remoteFilePath = remotePath + preDateStr + File.separator;


            FileInputStream fis = null;

            //TODO 这里需不需要判断这个文件在本地是否存在  ！！！！？？？？？

            //去ESB取核心文件
            String esbFtpPut = FileUtil.ESBFtpPut(file.getAbsolutePath(), remoteFilePath + remoteFileName, "CBS");
            if (StringUtils.isNotBlank(esbFtpPut)) {
                // 更新回盘文件状态
                FileInfoPo setFileInfo = new FileInfoPo();
                setFileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_UPLOAD_SUC);

                FileInfoPo whereFileInfo = new FileInfoPo();
                whereFileInfo.setId(data.getId());

                daoService.update(setFileInfo, whereFileInfo);
                LOGGER.info("----- 批量转账 -- 上传回盘文件[{}]到远程目录[{}]成功",
                        new Object[]{data.getFileName(), remoteFilePath});

                //调ESB核心文件通通知接口
                //TODO
                esbCliDipperHandler.handle(Transfer.dkNotify(remoteFileName));
            } else {
                throw new BatchException("上传批量转账回盘文件[" + data.getFileName()
                        + "]到远程目录[" + remoteFilePath + "]失败");
            }
        } catch (Exception e) {

        }


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
