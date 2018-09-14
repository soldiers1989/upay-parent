package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.gnr.FileInfoPo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 批量转账 - 第四步 生成回盘文件信息。
 * 1. 对未生成回盘文件的清分文件且数据处理完成后的一段时间后生成回盘文件。
 * 1. 拷贝一份文件清分文件并命名为：原清分文件名 + "R" + 扩展名.xls。
 * 3. 在文件信息表中记录清分回盘文件信息。
 * @author zhangjianfeng
 * @since 2017/02/20 00:53
 */
public class GenBackFileStep extends AbstractStepExecutor<Object, FileInfoPo> {

    /** 日志记录 */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenBackFileStep.class);


    /** 转账文件本地存入路径 */
    private String localPath;

    /**
     * 查询未生成清分文件对应的回盘文件记录。
     *
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<FileInfoPo> getDataList(BatchParams batchParams,
                                        int offset, int pageSize, Object object)
            throws BatchException {
        // 查询转账文件信息
        FileInfoPo fileInfo=new FileInfoPo();
        fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_TRANSFER);
        fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_DOWNLOAD);
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_IN_DB);


        List<FileInfoPo> fileInfos = daoService.selectList(fileInfo);

        LOGGER.info("----- 批量转账 -- {}个文件待生成回盘文件",
                new Object[] {fileInfos.size()});
        return fileInfos;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * 登记微信回盘文件信息。

     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index,
                        FileInfoPo data, Object object) throws BatchException {
      //  String localFullPath = (String)batchParams.getParameter().get("localFullPath");

        // 当前交易日期转账文件存放目录
        String transDateStr = DateUtil.format(batchParams.getTranDate(), "yyyyMMdd");
        String localFullPath = localPath + transDateStr + File.separator;
        // 回盘文件名称
        int lastDotIdx =  data.getFileName().lastIndexOf(".");
       String backFileName = data.getFileName().substring(0, lastDotIdx) + "R"
                + data.getFileName().substring(lastDotIdx);
        FileInfoPo backFile = new FileInfoPo();
        // 续跑处理
        if (batchParams.isContinue()) {
            backFile.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_BACK_FILE);
            backFile.setFileName(backFileName);
            backFile.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_UPLOAD);
            backFile = daoService.selectOne(backFile);
            if (backFile != null) {
                return;
            }

            backFile = new FileInfoPo();
        }

        // 转账文件
        File transFile = new File(localFullPath+data.getFileName());

        // 生成回盘文件
        File weiXinBackFile = new File(localFullPath+backFileName);
        try{
            FileUtils.copyFile(transFile, weiXinBackFile);
        } catch (Exception e) {
            LOGGER.error("----- 批量转账 -- " + data.getFileName() + "文件生成回盘文件失败", e);
            throw new BatchException("批量转账[" + data.getFileName() + "]文件生成回盘文件失败");
        }

        // -- 登记回盘文件信息 --
        backFile.setRealTransDate(batchParams.getTranDate());
        backFile.setBatchNo(batchParams.getBatchNo());
        backFile.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_BACK_FILE);
        backFile.setFileName(backFileName);
        backFile.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_UPLOAD);
        backFile.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_INIT);
        backFile.setFileSerino(data.getFileSerino());
        Date nowTime = new Date();
        backFile.setCreateTime(nowTime);
        backFile.setUpdateTime(nowTime);
        daoService.insert(backFile);

        // -- 更新转账文件信息 --
        FileInfoPo setFileInfo = new FileInfoPo();
        setFileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_BACK_RECORD);
        setFileInfo.setUpdateTime(nowTime);

        FileInfoPo whereFileInfo = new FileInfoPo();
        whereFileInfo.setId(data.getId());
        daoService.update(setFileInfo, whereFileInfo);
    }

}
