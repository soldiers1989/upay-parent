package com.upay.batch.stepservice.chk.atalipay;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.batch.stepservice.chk.alipay.GetAlipayChkFileStep;
import com.upay.batch.stepservice.chk.alipay.bean.ChkAlipayBean;
import com.upay.batch.stepservice.chk.atalipay.bean.ChkAlipayBeanAT;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 获取支付宝对账明细文件,并解析入库
 */
public class GetAlipayChkFileStepAT extends AbstractStepExecutor<Object, ChkAlipayBeanAT> {

    private final static Logger logger = LoggerFactory.getLogger(GetAlipayChkFileStep.class);

    private String chkFilePath;
    private String appId;
    private int num;




    @SuppressWarnings({"rawtypes", "null", "unchecked"})
    @Override
    public List<ChkAlipayBeanAT> getDataList(BatchParams batchParams, int offset, int pageSize,
                                           Object object) throws BatchException {
        num = 9;
        if (batchParams.isContinue()) {
            ChkThirdDetailPo third = new ChkThirdDetailPo();
            third.setChkDate(batchParams.getTranDate());
            third.setChkBatchNo(batchParams.getBatchNo());
            daoService.delete(third);
        }
        //判断是否是指定日期执行
        if(batchParams.getParameter().containsKey("tranDate")){
            Date tranDate = (Date)batchParams.getParameter().get("tranDate");
            Date preDate = (Date)batchParams.getParameter().get("preDate");
            batchParams.setTranDate(tranDate);
            batchParams.setPreDate(preDate);
        }
        //生产
     /*   String tmpAliPayChkFilePath=chkFilePath+ File.separator+
                DateUtil.format(preDate, pattern)+File.separator+
                appId+"0516_"+DateUtil.format(preDate, pattern)+"_DETAILS.csv";*/
     //测试
        Date preDate = batchParams.getPreDate();
        String pattern = "yyyyMMdd";
        String separator = "/";
        String tmpAliPayChkFilePath=chkFilePath+ separator +
                                        DateUtil.format(preDate, pattern)+ separator +
                                       appId+"0156_"+DateUtil.format(preDate, pattern)+"_DETAILS.csv";
        num += (int) FileUtil.getFileRowCount(tmpAliPayChkFilePath, ChkAlipayBeanAT.class);
        List<ChkAlipayBeanAT> dataList = new ArrayList<>();
        dataList.addAll(FileUtil.readFileToList(tmpAliPayChkFilePath,
                ChkAlipayBeanAT.class, 0, num ));
        logger.debug("----------dataList.size[{}]", dataList.size());
        logger.info("文件记录总数[{}]", dataList != null ? dataList.size() : 0);
        logger.info("文件入库 start...");
        return dataList;
    }


    /**
     * 登记支付宝交易明细到第三方对账明细表
     *
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(BatchParams batchParams, int index, ChkAlipayBeanAT data, Object object)
            throws BatchException {
        if (data == null) {
            return;
        }
        try {
            this.insertThird(data, batchParams, object);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BatchException("-----批次号：[{}]------支付宝对账文件解析并导入数据库异常--------");
        }
    }


    /**
     * 登记第三方表
     *
     * @param data   支付宝支付数据
     * @param object 为对账批次信息
     * @throws ParseException
     */

    public void insertThird(ChkAlipayBeanAT data, BatchParams batchParams, Object object)
            throws ParseException {
        String tradeNo = null;
        if (StringUtils.isNotBlank(data.getRefundNo())) {
            tradeNo = data.getRefundNo();
        } else {
            tradeNo = data.getOutTradeNo();
        }
        logger.info("登记支付宝交易明细[{}]开始...", tradeNo);
        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();
        chkThirdDetailPo.setChnlSeq(tradeNo);
        ChkThirdDetailPo chkNo = this.daoService.selectOne(chkThirdDetailPo);
        if (chkNo == null) {
            chkThirdDetailPo.setFeeAmt(new BigDecimal(data.getServiceCharge()));
            chkThirdDetailPo.setTransAmt(new BigDecimal(data.getOrderAmt().replace("-", "")));// 交易金额
            chkThirdDetailPo.setTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);// 交易状态，默认交易成功
            chkThirdDetailPo.setChkBatchNo(batchParams.getBatchNo());// 对账批次号
            //  Date preDate = DateUtil.getYMD(batchParams.getPreDate()).getTime();
            chkThirdDetailPo.setChkDate(batchParams.getTranDate());// 对帐日期
            chkThirdDetailPo.setChnlDate(DateUtil.parse(DateUtil.format(data.getEndTime(), "yyyyMMdd"), "yyyyMMdd"));
//            chkThirdDetailPo.setRemark();
            // 第三方机构，通道代码
            // chkThirdDetailPo.setOrgCode(object.getOrgCode());
            chkThirdDetailPo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);
            chkThirdDetailPo.setCcy(DataBaseConstants_PAY.T_CCY_CNY);// 币种
            chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);// 对账状态
            chkThirdDetailPo.setThirdDate(DateUtil.getPreDate(batchParams.getTranDate()));// 第三方交易日期
            chkThirdDetailPo.setThirdSeq(data.getTradeNo());// 第三方交易流水号
            chkThirdDetailPo.setCancelFlag(DataBaseConstants_BATCH.T_CHK_CANCEL_FLAG_N);//撤销标志0，否，1是
            chkThirdDetailPo.setRevFlag(DataBaseConstants_BATCH.T_CHK_REV_FLAG_N);//冲正标识，0否，1是
            //添加商户号
            chkThirdDetailPo.setMerId(data.getMerchantId());
            daoService.insert(chkThirdDetailPo);
            logger.info("登记支付宝交易明细[{}]结束", tradeNo);
        } else {
            logger.info("支付宝交易明细[{}],已经登记", tradeNo);
        }
    }


    public void setChkFilePath(String chkFilePath) {
        this.chkFilePath = chkFilePath;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


}
