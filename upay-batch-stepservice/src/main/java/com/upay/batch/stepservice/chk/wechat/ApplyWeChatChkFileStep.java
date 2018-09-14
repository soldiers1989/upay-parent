package com.upay.batch.stepservice.chk.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.server.DatadirCleanupManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayOrderListPo;


public class ApplyWeChatChkFileStep extends AbstractStepExecutor<Object, Object> {

    private final static Logger logger = LoggerFactory.getLogger(ApplyWeChatChkFileStep.class);

    //    @Resource(name = "weChatChkFileHandler")
    private IDipperHandler<Message> weChatChkFileHandler;
    @Resource
    private ISequenceService sequenceService;
    /**
     * 微信分配的公众号ID
     */
    private String weChatAppId;
    /**
     * 微信支付分配的商户号
     */
    private String weChatMckId;


    @Override
    public void execute(BatchParams batchParams, int index, Object data, Object object)
            throws BatchException {

        //判断是否是指定日期执行
        if(batchParams.getParameter().containsKey("tranDate")){
            Date tranDate = (Date)batchParams.getParameter().get("tranDate");
            Date preDate = (Date)batchParams.getParameter().get("preDate");
            batchParams.setTranDate(tranDate);
            batchParams.setPreDate(preDate);
        }

        // 到核心取对账的文件 该文件包括所有到核心记账的成功交易明细
        // 第一步 调用核心的接口获取对账的文件名
        Message msg = MessageFactory.create(IdGenerateFactory.generateId(), "weixincli", "XML", "UTF-8",
                MessageFactory.createSimpleMessage(new HashMap<String, Object>(), new HashMap<String, Object>()),
                FaultFactory.create("000000", ""));
        Map<String, Object> body = (Map<String, Object>) msg.getTarget().getBodys();
        Map<String, Object> head = (Map<String, Object>) msg.getTarget().getHeaders();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = batchParams.getPreDate();
        // xml报文头组装

        String sysDate = new SimpleDateFormat("yyyyMMdd").format((Date) batchParams.getTranDate());
        String sysTime = new SimpleDateFormat("HHmmss").format(new Date());

        // 对账单下载
        body.put("sysDate", sysDate);
        body.put("sysTime", sysTime);
        body.put("tranCode", "DOWNLOADBILL");
        body.put("appId", weChatAppId);// "wxdace645e0bc2c424"
        body.put("mchId", weChatMckId);// "1900008731"
        body.put("billDate",new SimpleDateFormat("yyyyMMdd").format(batchParams.getPreDate()) );
        body.put("billType", "ALL");
        head.put("billDate", body.get("billDate"));
        try {
            Message handle = weChatChkFileHandler.handle(msg);

            if (!handle.getFault().getCode().equals(CommonConstants_GNR.TRANS_FAULT_SUCCESS)) {
                if(DataBaseConstants_BATCH.T_CHK_WEIXIN_DOWNLOAD_NO_EXIST.equals(handle.getFault().getOutMsg())){
                    logger.error("-------------调用微信对账文件下载接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                            handle.getFault().getCode(), handle.getFault().getMsg());
                    return;
                }else{
                    logger.error("-------------调用微信对账文件下载接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                            handle.getFault().getCode(), handle.getFault().getMsg());
                    throw new BatchException("-------------调用微信对账文件下载服务返回异常!!!");
                }
            }

            Map<String, Object> bodys = (Map<String, Object>) handle.getTarget().getBodys();
            String ftpFileName = (String) bodys.get("fileName");
            if (StringUtils.isNotBlank(ftpFileName)) {
                ChkInfoPo chkInfo = new ChkInfoPo();

                chkInfo.setBenchmarkFlag(DataBaseConstants_BATCH.T_CHK_BENCHMARK_FLAG_THIRD);
                chkInfo.setChkDate(batchParams.getTranDate());
                chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);// 通道代码--微信
                chkInfo.setChkTime(new Date());
                chkInfo.setBatchNo(batchParams.getBatchNo());
                chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
                chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);// 未对账
                chkInfo.setFileType(DataBaseConstants_BATCH.T_CHK_FILE_TYPE_DOWNLOAD);// 下载
                chkInfo.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_REQUEST_SEND);// 0：文件请求发送
                chkInfo.setChkFile(ftpFileName);
                chkInfo.setOrgType(DataBaseConstants_BATCH.UNIT_TYPE_002);

                daoService.insert(chkInfo);
            } else {
                logger.error("未取得日期为:" + dateFormat.format(date) + "的对账文件");
                throw new BatchException("未取得日期为:" + dateFormat.format(date) + "的对账文件");
            }
        } catch (Exception e) {
            logger.error("批量==============获取微信对账文件出现错误", e);
            e.printStackTrace();
            throw new BatchException("未取得日期为:" + dateFormat.format(date) + "的对账文件");
        }
    }


    public IDipperHandler<Message> getWeChatChkFileHandler() {
        return weChatChkFileHandler;
    }


    public void setWeChatChkFileHandler(IDipperHandler<Message> weChatChkFileHandler) {
        this.weChatChkFileHandler = weChatChkFileHandler;
    }


    public String getWeChatAppId() {
        return weChatAppId;
    }


    public void setWeChatAppId(String weChatAppId) {
        this.weChatAppId = weChatAppId;
    }


    public String getWeChatMckId() {
        return weChatMckId;
    }


    public void setWeChatMckId(String weChatMckId) {
        this.weChatMckId = weChatMckId;
    }

}
