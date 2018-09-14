package com.upay.batch.stepservice.migration;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.po.batch.BatchJobExecutionPo;
import com.pactera.dipper.po.batch.BatchJobgrpExecutionPo;
import com.pactera.dipper.po.batch.BatchObjectExecutionPo;
import com.pactera.dipper.po.batch.BatchStepExecutionPo;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.dao.IDaoService;
import com.upay.dao.po.gnr.GnrOperateListPo;
import com.upay.dao.po.pay.PayFlowListHisPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListHisPo;
import com.upay.dao.po.pay.PayOrderListPo;

import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @Created with IDEA
 * @author:liudan
 * @Date:2018/3/21
 * @Time:9:40
 * @description 删除日志表
 */
public class RemoveBatchAndGnrOperateLog extends AbstractStepExecutor<PayFlowListHisPo, PayFlowListPo> {
	@Resource
	private IDaoService daoService;
	@Override
	public void execute(BatchParams batchParams, int index, PayFlowListPo data,
			PayFlowListHisPo object) throws BatchException {
		GnrParmPo parm=new GnrParmPo();
		parm.setParmId("CLEAR_BATCH_LOG_DAYS");
		parm=daoService.selectOne(parm);
		if(parm!=null){
			String moveDays=parm.getParmValue();
			logger.info("配置的是 删除"+moveDays+"天之前的批量日志 信息数据");
			Date transDate = batchParams.getTranDate();
			Date backupDate = DateUtil.add(transDate, Calendar.DAY_OF_MONTH, -Integer.valueOf(moveDays));
			String backupDateStr=DateUtil.format(backupDate, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
			logger.info("删除"+backupDateStr+"天之前的批量日志    信息数据");
			HashMap<Object, String> parmMap=new HashMap<Object,String>();
//			parmMap.put("orderNo", orderNo);
//			parmMap.put("startDate", orderNo);
			parmMap.put("endDate", backupDateStr);
			
			
			int delete = daoService.delete(GnrOperateListPo.class.getName().concat(".deleteDataByDate")+"", parmMap);
			logger.info("删除"+backupDateStr+"之前的GNR操作    信息数据      "+delete);
			
			
			delete = daoService.delete(BatchJobExecutionPo.class.getName().concat(".deleteDataByDate")+"", parmMap);
			logger.info("删除"+backupDateStr+"之前的批量组日志BatchJobExecution    信息数据      "+delete);
			
			delete = daoService.delete(BatchJobgrpExecutionPo.class.getName().concat(".deleteDataByDate")+"", parmMap);
			logger.info("删除"+backupDateStr+"之前的批量组日志BatchJobgrpExecution    信息数据      "+delete);
			
			delete = daoService.delete(BatchObjectExecutionPo.class.getName().concat(".deleteDataByDate")+"", parmMap);
			logger.info("删除"+backupDateStr+"之前的批量组日志BatchObjectExecutionPo   信息数据      "+delete);
			
			delete = daoService.delete(BatchStepExecutionPo.class.getName().concat(".deleteDataByDate")+"", parmMap);
			logger.info("删除"+backupDateStr+"之前的批量组日志BatchStepExecutionPo    信息数据      "+delete);
		}else{
			logger.info("未配置数据转移配置");
		}
	}

}
