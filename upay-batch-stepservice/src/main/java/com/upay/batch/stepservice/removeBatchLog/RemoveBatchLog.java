/**
 * 
 */
package com.upay.batch.stepservice.removeBatchLog;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.pactera.dipper.po.batch.BatchJobExecutionPo;
import com.pactera.dipper.po.batch.BatchJobgrpExecutionPo;
import com.pactera.dipper.po.batch.BatchObjectExecutionPo;
import com.pactera.dipper.po.batch.BatchStepExecutionPo;
import com.pactera.dipper.po.gnr.GnrParmPo;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;

/**
 * 删除配置天数以前的日志信息
 * 
 * @author lb
 * 
 */
public class RemoveBatchLog extends AbstractStepExecutor<Object, Object> {
	private final static Logger logger = LoggerFactory
			.getLogger(RemoveBatchLog.class);
	@Resource
	private IDaoService daoService;
	

	@Override
	public void execute(BatchParams batchParams, int index, Object data,
			Object object) throws BatchException {
		
		GnrParmPo parmPo=new GnrParmPo();
		parmPo.setParmId("CLEAR_BATCH_LOG_DAYS");
		parmPo=daoService.selectOne(parmPo);
		if(null!=parmPo){
			String parmValue = parmPo.getParmValue();
			if(StringUtils.isNotBlank(parmValue)){
				int days=Integer.valueOf(parmValue);
				if(days>0){
					HashMap<String, Object> parmMap=new HashMap<String,Object>();
					Date toDay=new Date();
					Date deleteStopDate = DateUtil.add(toDay, Calendar.DAY_OF_MONTH, days);
					String deleteDate=DateUtil.format(deleteStopDate, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
					parmMap.put("deleteDate", deleteDate);
					logger.info("删除  " +days+"  天以前的批量日志  日期为："+deleteDate);
					int delete = daoService.delete(BatchJobExecutionPo.class.getName()+".deleteBatchJobLog", parmMap);
					
					delete = daoService.delete(BatchJobgrpExecutionPo.class.getName()+".deleteBatchJobLog", parmMap);
					delete = daoService.delete(BatchObjectExecutionPo.class.getName()+".deleteBatchJobLog", parmMap);
					delete = daoService.delete(BatchStepExecutionPo.class.getName()+".deleteBatchJobLog", parmMap);
				}
			}
		}
	}
	
	public static void main(String [] args){
		
	}
}
