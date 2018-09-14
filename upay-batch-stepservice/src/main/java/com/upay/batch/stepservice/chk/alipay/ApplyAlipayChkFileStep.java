package com.upay.batch.stepservice.chk.alipay;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.ZipUtil;
import com.upay.dao.po.chk.ChkInfoPo;


public class ApplyAlipayChkFileStep extends AbstractStepExecutor<Object, Object> {

    private final static Logger logger = LoggerFactory.getLogger(ApplyAlipayChkFileStep.class);

    private IDipperHandler<Message> alipayDownloadHandler;
    private String chkFilePath;
    private String appId;

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

        // 第一步 调用支付宝的接口获取对账的文件名
        Message msg = MessageFactory.create(IdGenerateFactory.generateId(), "alipaycli", "XML", "UTF-8",
            MessageFactory.createSimpleMessage(new HashMap<String, Object>(), new HashMap<String, Object>()),
            FaultFactory.create("000000", ""));
		Map<String, Object> body = (Map<String, Object>) msg.getTarget().getBodys();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = batchParams.getPreDate();
        // 对账单下载
        body.put("billType", "trade");
        body.put("billDate",dateFormat.format(date));
        try {
            Message handle = alipayDownloadHandler.handle(msg);
            
            if (!handle.getFault().getCode().equals(CommonConstants_GNR.TRANS_FAULT_SUCCESS)) {
            	logger.error("-------------调用支付宝对账文件下载接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                        handle.getFault().getCode(), handle.getFault().getMsg());
            	throw new BatchException("-------------调用支付宝对账文件下载服务返回异常!!!");
            }
            
            Map<String, Object> bodys = (Map<String, Object>) handle.getTarget().getBodys();
            
            byte[] b = (byte[]) bodys.get("data");
            if(b !=null && b.length == 0){
            	throw new BatchException("支付宝返回对账文件异常，未获取到数据");
            }
            String chkDate = new SimpleDateFormat("yyyyMMdd").format(date);
            String fileName = appId+"_"+chkDate+".zip";
            
            FileOutputStream out = new FileOutputStream(new File(chkFilePath.concat(fileName)));
            out.write(b);
            out.flush();
            out.close();
            ZipUtil.unZipFiles(new File(chkFilePath.concat(fileName)), "");
            
            ChkInfoPo chkInfo = new ChkInfoPo();
            chkInfo.setBenchmarkFlag(DataBaseConstants_BATCH.T_CHK_BENCHMARK_FLAG_THIRD);
            chkInfo.setChkDate(batchParams.getTranDate());
            chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_ALIPAY);// 通道代码--支付宝
            chkInfo.setChkTime(new Date());
            chkInfo.setBatchNo(batchParams.getBatchNo());
            chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
            chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);// 未对账
            chkInfo.setFileType(DataBaseConstants_BATCH.T_CHK_FILE_TYPE_DOWNLOAD);// 下载
            chkInfo.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_REQUEST_SEND);// 0：文件请求发送
            chkInfo.setChkFile(getChkFileName(chkFilePath.concat(fileName.replace(".zip", ""))));
            chkInfo.setOrgType(DataBaseConstants_BATCH.UNIT_TYPE_002);

            daoService.insert(chkInfo);
            
        } catch (Exception e) {
            logger.error("批量==============获取支付宝对账文件出现错误", e);
            e.printStackTrace();
            throw new BatchException("未取得日期为:" + dateFormat.format(date) + "的对账文件");
        }
    }


	public void setAlipayDownloadHandler(
			IDipperHandler<Message> alipayDownloadHandler) {
		this.alipayDownloadHandler = alipayDownloadHandler;
	}


	public void setChkFilePath(String chkFilePath) {
		this.chkFilePath = chkFilePath;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getChkFileName(String path){
		 File files = new File(path);
		 File[] listFiles = files.listFiles();
		 String name = null;
		 
		 for (File file : listFiles) {
			 if(file.getName().indexOf("DETAILS") != -1){
				name = file.getName();
			 }
		 }
		 return name;
	}


}
