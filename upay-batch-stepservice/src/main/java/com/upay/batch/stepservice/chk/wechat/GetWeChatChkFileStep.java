package com.upay.batch.stepservice.chk.wechat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.upay.batch.stepservice.chk.wechat.bean.ChkWechatBean;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.FileUtil;
import com.upay.dao.po.chk.ChkHostFilePo;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;


/**
 * 获取微信对账明细文件,并解析入库
 *
 * @author Pactera
 *
 */
public class GetWeChatChkFileStep extends AbstractStepExecutor<ChkInfoPo, ChkWechatBean> {

    private final static Logger logger = LoggerFactory.getLogger(GetWeChatChkFileStep.class);

    private String weChatChkFilePath;

    private int num=3;


    /**
     *
     * @param batchParams
     * @return
     * @throws BatchException
     */
    @Override
    public List<ChkInfoPo> getObjectList(BatchParams batchParams) throws BatchException {


        //判断是否是指定日期执行
        if(batchParams.getParameter().containsKey("tranDate")){
            Date tranDate = (Date)batchParams.getParameter().get("tranDate");
            Date preDate = (Date)batchParams.getParameter().get("preDate");
            batchParams.setTranDate(tranDate);
            batchParams.setPreDate(preDate);
        }

        num=3;
        // 查询未对账信息
        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);
        chkInfo.setBatchNo(batchParams.getBatchNo());
        chkInfo.setChkDate(batchParams.getTranDate());
        chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
        List<ChkInfoPo> listChkInfo = daoService.selectList(chkInfo);
        if(listChkInfo!=null&&listChkInfo.size()>0){
            num=(int) FileUtil.getFileRowCount(weChatChkFilePath + listChkInfo.get(0).getChkFile(), ChkHostFilePo.class);
        }
        return listChkInfo;
    }


    @SuppressWarnings({ "rawtypes", "null", "unchecked" })
    @Override
    public List<ChkWechatBean> getDataList(BatchParams batchParams, int offset, int pageSize,
                                           ChkInfoPo object) throws BatchException {
        if(batchParams.isContinue()){
            ChkThirdDetailPo third=new ChkThirdDetailPo();
            third.setChkDate(batchParams.getTranDate());
            third.setChkBatchNo(object.getBatchNo());
            daoService.delete(third);
        }
        logger.info("微信获取文件信息：batchNo:[{}],rountCode:[core],fileName[{}]", object.getBatchNo(),
                weChatChkFilePath + object.getChkFile());
//        String chkPath = (String) batchParams.getParameter().get("chkPath");
        List<ChkWechatBean> list =
                FileUtil.readFileToList(weChatChkFilePath + object.getChkFile(), ChkWechatBean.class, 0, num);
        if(list!=null && list.size()>0){
            ChkInfoPo chk=new ChkInfoPo();
            chk.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_DOWNLOAD_SUC);
            ChkInfoPo chkw=new ChkInfoPo();
            chkw.setBatchNo(batchParams.getBatchNo());
            chkw.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_REQUEST_SEND);
            daoService.update(chk,chkw);
        }
        logger.info("文件记录总数[{}]", list != null ? list.size() : 0);
        logger.info("文件入库 start...");
        return list;
    }


    /**
     * 登记中金交易明细到第三方对账明细表
     *
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(BatchParams batchParams, int index, ChkWechatBean data, ChkInfoPo object)
            throws BatchException {
        String chkSeq = (String) data.getWcOrderNo();
        try {
            this.insertThird(data, chkSeq, batchParams, object);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new BatchException("-----批次号：[{}]------微信对账文件解析并导入数据库异常--------");
        }
    }


    @Override
    public void updateObject(BatchParams batchParams, ChkInfoPo object) {
        logger.info("-----批次号：[{}]------获取中金支付对账明细数据流水数据并入库结束--------", object.getBatchNo(),
                "GetCPCNChkFileStep");
    }


    /**
     * 登记第三方表
     *
     * @param data
     *            中金支付数据
     * @param chkSeq
     *            中金流水号
     * @param object
     *            为对账批次信息
     * @throws ParseException
     */

//    @SuppressWarnings("rawtypes")
    public void insertThird(ChkWechatBean data, String chkSeq, BatchParams batchParams, ChkInfoPo object)
            throws ParseException {
        logger.info("登记微信交易明细[{}]开始...", chkSeq);

        if (data == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "对账批次信息");
        }
        ChkThirdDetailPo chkThirdDetailPo = new ChkThirdDetailPo();

        chkThirdDetailPo.setThirdSeq(chkSeq);
        ChkThirdDetailPo chkNo = this.daoService.selectOne(chkThirdDetailPo);
        if (chkNo == null) {
            if (null != data.getTxnStat()) {
                BigDecimal zero=new BigDecimal(0);
                BigDecimal feeAmt =data.getTxnFee()!=null&&data.getTxnFee().length()>1? new BigDecimal(data.getTxnFee().substring(1, data.getTxnFee().length()-1)):zero;
                chkThirdDetailPo.setFeeAmt(feeAmt);// 支付平台应付手续费=交易金额-支付平台应收的金额
                if(CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_SUCCESS.equals(data.getTxnStat().substring(1, data.getTxnStat().length()))){
                    chkThirdDetailPo.setChnlSeq(data.getOrderNo()!=null&&data.getOrderNo().length()>1?data.getOrderNo().substring(1,data.getOrderNo().length()):"");
                    BigDecimal transAmt =data.getTxnAmt()!=null&&data.getTxnAmt().length()>1?
                            new BigDecimal(data.getTxnAmt().substring(1, data.getTxnAmt().length())):zero;
                    chkThirdDetailPo.setTransAmt(transAmt);// 交易金额
                    if(CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_SUCCESS.equals(data.getTxnStat().substring(1, data.getTxnStat().length()))){
                        chkThirdDetailPo.setTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);// 交易状态，默认交易成功
                    }else{
                        chkThirdDetailPo.setTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);// 交易状态
                    }
                }
                if(CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_REFUND.equals(data.getTxnStat().substring(1, data.getTxnStat().length()))){
                    chkThirdDetailPo.setChnlSeq(data.getRefundOrderNo()!=null&&data.getRefundOrderNo().length()>1?data.getRefundOrderNo().substring(1,data.getRefundOrderNo().length()):"");
                    BigDecimal refundAmt =data.getRefundAmt()!=null&&data.getRefundAmt().length()>1?
                            new BigDecimal(data.getRefundAmt().substring(1, data.getRefundAmt().length())):zero;
                    chkThirdDetailPo.setTransAmt(refundAmt);// 交易金额
                    chkThirdDetailPo.setTransStat(data.getRefundStat().substring(1, data.getRefundStat().length()));// 交易状态，默认交易成功
                    if(CommonBaseConstans_PAY.WEIXIN_TRANS_STAT_SUCCESS.equals(data.getRefundStat().substring(1, data.getRefundStat().length()))){
                        chkThirdDetailPo.setTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);// 交易状态，默认交易成功
                    }else{
                        chkThirdDetailPo.setTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);// 交易状态
                    }
                }
                SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
                chkThirdDetailPo.setChkBatchNo(object.getBatchNo());// 对账批次号
                chkThirdDetailPo.setChkDate(object.getChkDate());// 对帐日期
                // 微信交易日期
                if(data.getTxnTime()!=null){
                    chkThirdDetailPo.setChnlDate(sim.parse(data.getTxnTime().substring(1, data.getTxnTime().length()-9)));
                }
                // 平台交易流水号
                // 备注，微信交易类型
                chkThirdDetailPo.setRemark(data.getTxnType()!=null&&data.getTxnType().length()>1?data.getTxnType().substring(1, data.getTxnType().length()):"");
                // 第三方机构，通道代码
                chkThirdDetailPo.setOrgCode(object.getOrgCode());
                chkThirdDetailPo.setCcy(DataBaseConstants_PAY.T_CCY_CNY);// 币种
                chkThirdDetailPo.setThirdStat(DataBaseConstants_BATCH.T_CHK_FLAG_NO);// 对账状态
                chkThirdDetailPo.setThirdDate(DateUtil.getPreDate(object.getChkDate()));// 第三方交易日期
                chkThirdDetailPo.setThirdSeq(data.getWcOrderNo()!=null&&data.getWcOrderNo().length()>1?data.getWcOrderNo().substring(1, data.getWcOrderNo().length()):"");// 第三方交易流水号
                chkThirdDetailPo.setCancelFlag(DataBaseConstants_BATCH.T_CHK_CANCEL_FLAG_N);//撤销标志0，否，1是
                chkThirdDetailPo.setRevFlag(DataBaseConstants_BATCH.T_CHK_REV_FLAG_N);//冲正标识，0否，1是
                //添加商户号
                chkThirdDetailPo.setMerId(data.getWcMerNo());
                daoService.insert(chkThirdDetailPo);
                logger.info("登记微信交易明细[{}]结束，商户手续费[{}]",
                        new Object[] { chkSeq, chkThirdDetailPo.getFeeAmt() });
            }
        }
    }


    public String getWeChatChkFilePath() {
        return weChatChkFilePath;
    }


    public void setWeChatChkFilePath(String weChatChkFilePath) {
        this.weChatChkFilePath = weChatChkFilePath;
    }

}
