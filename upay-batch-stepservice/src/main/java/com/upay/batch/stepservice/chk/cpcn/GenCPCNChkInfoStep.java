package com.upay.batch.stepservice.chk.cpcn;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.dao.po.chk.ChkInfoPo;


/**
 * 生成中金对账信息 不论当日是否为节假日，都生成一条对账信息
 * 第一步
 * @author zhangjianfeng
 * @since 2017/01/09 21:20
 */
public class GenCPCNChkInfoStep extends AbstractStepExecutor<Object, HashMap> {
	private final static Logger logger = LoggerFactory.getLogger(GenCPCNChkInfoStep.class);
    /**
     * 生成并记录中金对账信息
     * 
     * @param batchParams
     * @param index
     * @param data
     * @param object
     * @throws BatchException
     */
    @Override
    public void execute(BatchParams batchParams, int index, HashMap data, Object object)
            throws BatchException {
    	logger.info("中金对账第一步    ---------------         开始");
    	
    	//判断是否是指定日期执行
    	if(batchParams.getParameter().containsKey("tranDate")){
    		Date tranDate = (Date)batchParams.getParameter().get("tranDate");
    		Date preDate = (Date)batchParams.getParameter().get("preDate");
    		batchParams.setTranDate(tranDate);
    		batchParams.setPreDate(preDate);
    	}
    	
    	// 登记对账批次信息
        ChkInfoPo chkInfo = new ChkInfoPo();
        chkInfo.setBatchNo(batchParams.getBatchNo());
        chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
        chkInfo.setChkDate(batchParams.getTranDate());
        chkInfo = daoService.selectOne(chkInfo);
        if (null == chkInfo) {
            chkInfo = new ChkInfoPo();
            chkInfo.setBatchNo(batchParams.getBatchNo());
            chkInfo.setBenchmarkFlag(DataBaseConstants_BATCH.T_CHK_BENCHMARK_FLAG_THIRD);
            chkInfo.setChkDate(batchParams.getTranDate());
            chkInfo.setOrgCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);// 中金代码
            chkInfo.setChkTime(new Date());
            chkInfo.setChkEntryClsCd(batchParams.getBatchNo());
            chkInfo.setChkStat(DataBaseConstants_BATCH.T_CHK_STAT_NO);// 未对账
            chkInfo.setFileType(DataBaseConstants_BATCH.T_CHK_FILE_TYPE_DOWNLOAD);// 下载
            chkInfo.setFileDownStat(DataBaseConstants_BATCH.T_CHK_FILE_DEAL_STAT_REQUEST_RECORD);
            // chkInfo.setChkFile(ftpFileName);
            chkInfo.setOrgType(DataBaseConstants_BATCH.UNIT_TYPE_002);
           // TODO   常量不能直接写字符串
            daoService.insert(chkInfo);
        }
        
        logger.info("中金对账第一步      ---------       结束");
    }
}
