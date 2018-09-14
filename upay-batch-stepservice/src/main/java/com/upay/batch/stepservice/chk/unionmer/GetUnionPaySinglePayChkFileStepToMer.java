package com.upay.batch.stepservice.chk.unionmer;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.batch.stepservice.chk.unionmer.bean.ChkUnionFlowBeanMer;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 银联对账文件解析
 *
 * @author Pactera
 */
public class GetUnionPaySinglePayChkFileStepToMer extends
        AbstractStepExecutor<ChkInfoPo, ChkUnionFlowBeanMer> {

    private static final Logger log = LoggerFactory
            .getLogger(GetUnionPaySinglePayChkFileStepToMer.class);


    @Override
    public List<ChkInfoPo> getObjectList(BatchParams batchParams)
            throws BatchException {
        List<ChkInfoPo> chkInfoList = new ArrayList<ChkInfoPo>();
        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);//通道代码,银联-0002
        chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);//对账状态 ： 3：未对账
        chkInfo.setFileType(DataBaseConstants_BATCH.T_CHK_FILE_TYPE_DOWNLOAD);// 下载
        chkInfo.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC);// 下载成功
        Date chkDate = DateUtil.getYMD(batchParams.getPreDate()).getTime();
        chkInfo.setChkDate(chkDate);//对账日期  需要核对的账务日期
        chkInfoList = daoService.selectList(chkInfo);
        String outPutZipDirectory = (String) batchParams.getParameter().get("outPutZipDirectory");
        log.info("------UnionPay pay chkPath:[{}]", outPutZipDirectory);
        return chkInfoList;
    }

    @Override
    public int getTotalResult(BatchParams batchParams, ChkInfoPo chkInfo)
            throws BatchException {
        Date chkDate = DateUtil.getYMD(batchParams.getPreDate()).getTime();
        //续跑
        if(batchParams.isContinue()){
            ChkThirdDetailPo third=new ChkThirdDetailPo();
            third.setChkDate(chkDate);
            third.setChkBatchNo(chkInfo.getBatchNo());
            third.setOrgCode(chkInfo.getOrgCode());
            daoService.delete(third);
        }
        chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);//通道代码,银联-0002
        chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);//对账状态 ： 3：未对账
        chkInfo.setFileType(DataBaseConstants_BATCH.T_CHK_FILE_TYPE_DOWNLOAD);// 下载
        chkInfo.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC);// 下载成功

        chkInfo.setChkDate(chkDate);//对账日期  需要核对的账务日期
        chkInfo = this.daoService.selectOne(chkInfo);
        int totalResult = 0;
        if (chkInfo != null) {
            String outPutZipDirectory = (String) batchParams.getParameter().get("outPutZipDirectory");
            outPutZipDirectory += File.separator + chkInfo.getChkFile();
            batchParams.getParameter().put("chkFileName", outPutZipDirectory);
            totalResult += new Long(FileUtil.getFileRowCount(outPutZipDirectory, ChkUnionFlowBeanMer.class)).intValue();
            //用于读取文件数据   读取多少行
            batchParams.getParameter().put("totalResult", totalResult);
        }

        return totalResult;
    }

    @Override
    public List<ChkUnionFlowBeanMer> getDataList(BatchParams batchParams,
                                                 int offset, int pageSize, ChkInfoPo chkInfo) throws BatchException {

        int totalResult =(int)batchParams.getParameter().get("totalResult");

        String chkFileName = (String) batchParams.getParameter().get(
                "chkFileName");
        List<ChkUnionFlowBeanMer> dataList = new ArrayList<>();
        dataList.addAll(FileUtil.readFileToList(chkFileName,
                ChkUnionFlowBeanMer.class, 0, totalResult));
        log.debug("----------dataList.size[{}]", dataList.size());
        log.info("文件记录总数[{}]", dataList != null ? dataList.size() : 0);
        return dataList;
    }

    @Override

    @SuppressWarnings("all")
    public void execute(BatchParams batchParams, int index, ChkUnionFlowBeanMer bankInfo, ChkInfoPo chkInfo)
            throws BatchException {

        //判断是否是指定日期执行
  /*      if (batchParams.getParameter().containsKey("tranDate")) {
            Date tranDate = (Date) batchParams.getParameter().get("tranDate");
            Date preDate = (Date) batchParams.getParameter().get("preDate");
            batchParams.setTranDate(tranDate);
            batchParams.setPreDate(preDate);


        }*/


        log.info("execute==============================begin");
        Date fdate = null;
        String dateString = DateUtil.format(batchParams.getTranDate(), "yyyyMMddHHmmss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dat = new SimpleDateFormat("yyyyMMdd");

        try {
            Date dt = sdf.parse(dateString);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(dt);
            rightNow.add(Calendar.DAY_OF_YEAR, -1);// 日期减一天
            dateString = sdf.format(rightNow.getTime());
            fdate = dat.parse(dateString.substring(0, 8));

            //String chkSeq = bankInfo.getAcqinsCode() + "   " + bankInfo.getHairinsCode() + "   " + bankInfo.getTraceNo() + bankInfo.getTxnTime();
           // String chkSeq = bankInfo.getQueryId();

            insertThird(bankInfo, fdate, null, batchParams);

        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            log.info("", e);
            e.printStackTrace();
            throw new BatchException("银联代付对账文件插入数据库失败。");
        }
        log.info("execute==============================end");
    }

    /**
     * 登记第三方表
     *
     * @param data
     * @param
     * @param chkSeq
     * @throws ParseException
     */

    public void insertThird(ChkUnionFlowBeanMer data, Date fdate, String chkSeq,
                            BatchParams batchParams) throws ParseException {
        log.info("登记第三方表 start...");

        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
        SimpleDateFormat chnlDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        fdate = sdf.parse(dateformat.format(batchParams.getTranDate())
                + getCurrentYear() + data.getTxnTime());

        String chnlDate = chnlDateFormat.format(sdf.parse(dateformat
                .format(batchParams.getPreDate())
                + getCurrentYear()
                + data.getTxnTime()));

        chkThirdDetailPo.setChnlSeq(data.getQueryId());
        chkThirdDetailPo.setOrderNo(data.getOrderId());
        ChkThirdDetailPo chkNo = this.daoService.selectOne(chkThirdDetailPo);
        if (chkNo == null) {

            if (data.getAcqinsCode() != null) {
                // 05591751代付
                chkThirdDetailPo.setMerId(data.getMerId());
                chkThirdDetailPo.setChnlDate(chnlDateFormat.parse(chnlDate));
                chkThirdDetailPo.setChkBatchNo(batchParams.getBatchNo());
                Date chkDate = DateUtil.getYMD(batchParams.getPreDate()).getTime();
                chkThirdDetailPo.setChkDate(chkDate);
                chkThirdDetailPo
                        .setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS);
                BigDecimal txnAmt = new BigDecimal(data.getTxnAmt());
                chkThirdDetailPo.setTransAmt(txnAmt
                        .divide(new BigDecimal("100")));// 交易金额
                BigDecimal stmtAmtno = new BigDecimal(
                        data.getMerFee());
                chkThirdDetailPo.setFeeAmt(stmtAmtno.divide(new BigDecimal(
                        "100")));// 收单机构应付手续费
                chkThirdDetailPo.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
                chkThirdDetailPo
                        .setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
               // chkThirdDetailPo.setThirdDate(fdate);
                chkThirdDetailPo.setThirdDate(DateUtil.getPreDate(batchParams.getTranDate()));// 第三方交易日期
                chkThirdDetailPo.setThirdSeq(data.getTraceNo().trim());
                chkThirdDetailPo
                        .setTransStat(DataBaseConstants_PAY.T_PAY_CORE_TX_SUCCESS);
                chkThirdDetailPo.setPayeeAcct(data.getPayCardNo().trim());
                chkThirdDetailPo.setChnlTime(new Date());
                daoService.insert(chkThirdDetailPo);
            }
            log.info("登记第三方表 end...");
        }
    }


    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
}
