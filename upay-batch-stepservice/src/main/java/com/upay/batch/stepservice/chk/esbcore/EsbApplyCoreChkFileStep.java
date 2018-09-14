package com.upay.batch.stepservice.chk.esbcore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.FileUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.po.chk.ChkInfoPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


public class EsbApplyCoreChkFileStep extends AbstractStepExecutor<Object, Object> {

    private final static Logger logger = LoggerFactory.getLogger(EsbApplyCoreChkFileStep.class);

    private String coreLocalPath;// 核心文件本能地存放路径
    
    public String getCoreLocalPath() {
		return coreLocalPath;
	}


	public void setCoreLocalPath(String coreLocalPath) {
		this.coreLocalPath = coreLocalPath;
	}


	@Resource(name = "esbCliDipperHandler")
    private IDipperHandler<Message> esbCliDipperHandler;
    @Resource
    private ISequenceService sequenceService;


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
    	
        // TODO Auto-generated method stub
    	PayRouteInfoPo payRouteInfoPo = new PayRouteInfoPo();
    	payRouteInfoPo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
    	PayRouteInfoPo payRouteInfo = daoService.selectOne(payRouteInfoPo);

        // 到核心取对账的文件 该文件包括所有到核心记账的成功交易明细
        // 第一步 调用核心的接口获取对账的文件名
        Message msg = MessageFactory.create(IdGenerateFactory.generateId(), "corecli", "XML", "UTF-8",
            MessageFactory.createSimpleMessage(new HashMap<String, Object>(), new HashMap<String, Object>()),
            FaultFactory.create(Constants.ResponseCode.SUCCESS, "交易成功"));
        Map<String, Object> body = (Map<String, Object>) msg.getTarget().getBodys();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        Date date = batchParams.getPreDate();
        // xml报文头组装

        SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
        String time = sim.format(new Date());
        body.put("transCode", "801015");
        body.put("svcCd", "30150004");
        body.put("svcScn", "01");
//        body.put("machineTime", time.substring(8));
//        body.put("machineDate", time.substring(0, 8));
        body.put("machineDate", dateFormat.format(date));
        body.put("machineTime", timeFormat.format(date));
        body.put("fileFlg", "0");
//        
//        body.put("bizDate", sim.format(batchParams.getTranDate()).substring(0, 8));
//        body.put("bizSerialNo", sequenceService.generatePayFlowSeq());
        body.put("channelId","74");
        // xml报文体组装
        
        body.put("strtRcnclDt",dateFormat.format(batchParams.getPreDate()));
        body.put("endRcnclDt",dateFormat.format(batchParams.getPreDate()));
//        body.put("setAccount",payRouteInfo.getTransAcctNo()); 
//        body.put("bizType", "0");// 0-借记卡快捷支付 1-贷记卡快捷支付

        try {
            Message handle = esbCliDipperHandler.handle(msg);

            if (!handle.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
                logger.error("-------------调用核心对账文件下载接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                    handle.getFault().getCode(), handle.getFault().getMsg());
                throw new BatchException("-------------调用代收调用核心对账文件下载服务返回异常!!!");
            }

            Map<String, Object> bodys = (Map<String, Object>) handle.getTarget().getBodys();
            String ftpFileName = (String) bodys.get("filePath");
            
            if (StringUtils.isNotBlank(ftpFileName)) {
            	ChkInfoPo chkInfo = new ChkInfoPo();
            	
            	chkInfo.setBenchmarkFlag(DataBaseConstants_BATCH.T_CHK_BENCHMARK_FLAG_CORE);//TODO 对账数据基准标准确认
            	chkInfo.setChkDate(batchParams.getTranDate());
            	chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);// 核心通道代码
            	chkInfo.setOrgType(DataBaseConstants_BATCH.UNIT_TYPE_002);
            	chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
            	chkInfo.setBatchNo(batchParams.getBatchNo());
            	chkInfo.setChkTime(new Date());
            	chkInfo.setChkFile(ftpFileName);
            	chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);// 未对账
            	chkInfo.setFileType(DataBaseConstants_BATCH.T_CHK_FILE_TYPE_DOWNLOAD);// 下载
            	chkInfo.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_REQUEST_SEND);// 0：文件请求发送
                daoService.insert(chkInfo);
            } else {
                logger.error("未取得日期为:" + dateFormat.format(date) + "的对账文件");
                throw new BatchException("未取得日期为:" + dateFormat.format(date) + "的对账文件");
            }
            
            FileUtil.ESBFtpGet(ftpFileName, coreLocalPath,"CBS");
            logger.info("-------------DownloadJzBankCoreChkFileSteps 下载完成");
        } catch (Exception e) {
            logger.error("批量==============获取核心对账文件出现错误", e);
            e.printStackTrace();
            throw new BatchException("未取得日期为:" + dateFormat.format(date) + "的对账文件");
        }
    }


	public IDipperHandler<Message> getEsbCliDipperHandler() {
		return esbCliDipperHandler;
	}


	public void setEsbCliDipperHandler(IDipperHandler<Message> esbCliDipperHandler) {
		this.esbCliDipperHandler = esbCliDipperHandler;
	}


  

}
