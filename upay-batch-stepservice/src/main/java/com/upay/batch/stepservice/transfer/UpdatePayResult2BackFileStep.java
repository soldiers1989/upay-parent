package com.upay.batch.stepservice.transfer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.csv.CSVWriter;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.gnr.FileInfoPo;
import com.upay.dao.po.pay.PayFlowDetailPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

/**
 * 批量转账 - 第五步 更新支付结果到回盘文件中。
 *
 * @author zhangjianfeng
 * @since 2017/02/20 01:59
 */
public class UpdatePayResult2BackFileStep extends AbstractStepExecutor<FileInfoPo, Object> {

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * 日志记录。
     */

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UpdatePayResult2BackFileStep.class);


    /** 转账文件本地存入路径 */
    private String localPath;

    /**
     * 查询回盘文件信息
     *
     * @param batchParams
     * @return
     * @throws BatchException
     */
    @Override
    public List<FileInfoPo> getObjectList(BatchParams batchParams)
            throws BatchException {

        FileInfoPo fileInfo = new FileInfoPo();
        fileInfo.setTaskCode(CommonConstants_GNR.T_FILE_INFO_TASK_CODE_BACK_FILE);
        fileInfo.setFileType(CommonConstants_GNR.T_FILE_INFO_FILE_TYPE_UPLOAD);
        fileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_INIT);

        List<FileInfoPo> winXinBackFileInfos = daoService.selectList(fileInfo);

        LOGGER.info("----- 批量转账 -- {}个文件待更新支付结果到回盘文件",
                new Object[]{winXinBackFileInfos.size()});
        return winXinBackFileInfos;
    }

    /**
     * 更新回盘文件支付结果信息
     *
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index,
                        Object data, FileInfoPo object) throws BatchException {
        //String localFullPath = (String)batchParams.getParameter().get("localFullPath");
        // 当前交易日期转账文件存放目录
        String transDateStr = DateUtil.format(batchParams.getTranDate(), "yyyyMMdd");
        String localFullPath = localPath + transDateStr + File.separator;

        // 文件序列号。
        String fileSerino = object.getFileSerino();
        String backFileName = object.getFileName();
        String[] splitFileName = backFileName.split("_");
        for (String a : splitFileName) {
            LOGGER.info(a + "   ======== 回盘文件名");
        }
        Date merTransDate = DateUtil.parse(splitFileName[2], "yyyyMMdd");

        File file = new File(localFullPath+backFileName);
        CSVWriter writer = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            List<Transfer> dataList = Transfer.getDataList(file);
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, Transfer.charSet);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            writer = new CSVWriter(bufferedWriter, CSVWriter.DEFAULT_SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER);
            for (Transfer tf : dataList) {
                String merSeq = tf.getMerSeq();
                PayFlowDetailPo payFlowDetail = new PayFlowDetailPo();
                payFlowDetail.setMerTransDate(merTransDate);
                payFlowDetail.setMerTransSeq(merSeq);
                payFlowDetail.setTrfBatchNo(fileSerino); // 处理流水
                payFlowDetail = daoService.selectOne(payFlowDetail);
                if (null == payFlowDetail) {
                    LOGGER.info("----- 商户流水(MertransSeq)【[{}]】 不存在", merSeq);
                    throw new BatchException("商户流水(MertransSeq)【" + merSeq + "】 不存在");
                }
                if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(payFlowDetail.getTransStat())) {
                    tf.setResultDesc("交易成功");
                    tf.setRemark("交易成功");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N
                        .equals(payFlowDetail.getTransStat())) {
                    tf.setResultDesc("交易未处理");
                    tf.setRemark("交易未处理");
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL
                        .equals(payFlowDetail.getTransStat())) {
                    tf.setResultDesc(payFlowDetail.getRemark1());
                    tf.setRemark(payFlowDetail.getRemark1());
                } else if (DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_ING
                        .equals(payFlowDetail.getTransStat())) {
                    tf.setResultDesc("交易处理中");
                    tf.setRemark("交易处理中");
                }
                tf.setOrderNo(payFlowDetail.getOrderNo());
                tf.setOrderStat(payFlowDetail.getTransStat());
                String[] temp = tf.getDataForStringArray();
                // Map<String, Object> map = Transfer.beanToMap(tf, Transfer.class);
                //  List<Object> datas = new ArrayList<>(map.values());
                //   temp= datas.toArray(temp);
                writer.writeNext(temp);
                writer.flush();

            }


            //   writer.writeAll(dataLists);
            LOGGER.info("----- 批量转账 -- 更新回盘文件[{}]支付结果完成",
                    new Object[]{object.getFileName()});

        } catch (Exception e)

        {
            e.printStackTrace();
            LOGGER.error("----- 批量转账 -- 更新回盘文件["
                    + object.getFileName() + "]支付结果失败", e);
            throw new BatchException("批量转账更新回盘文件["
                    + object.getFileName() + "]支付结果失败");
        } finally {
            Transfer.closeWriter(writer, fileOutputStream,outputStreamWriter,bufferedWriter);
        }
    }

    /**
     * 更新微信回盘文件状态
     *
     * @param batchParams
     * @param object
     */
    @Override
    public void updateObject(BatchParams batchParams, FileInfoPo object) {
        // 更新回盘文件状态为已处理
        FileInfoPo setFileInfo = new FileInfoPo();
        setFileInfo.setFileStat(CommonConstants_GNR.T_FILE_INFO_FILE_STAT_PROCESSED);

        FileInfoPo whereFileInfo = new FileInfoPo();
        whereFileInfo.setId(object.getId());

        daoService.update(setFileInfo, whereFileInfo);
    }

}
