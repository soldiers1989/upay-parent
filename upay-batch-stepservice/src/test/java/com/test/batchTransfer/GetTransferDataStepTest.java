package com.test.batchTransfer;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.upay.batch.stepservice.transfer.Transfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pactera.dipper.batch.dto.BatchParams;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodayRouteFee;
import com.upay.batch.stepservice.stl.transferRouteFee.SumTodaySubProfit;
import com.upay.batch.stepservice.stl.transferRouteFee.SyncProcessing;
import com.upay.batch.stepservice.transfer.DownloadTransferFileStep;
import com.upay.batch.stepservice.transfer.GetTransferDataStep;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * 
 */

/**
 * @author shang
 * 2017年1月12日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/**/*.xml")
public class GetTransferDataStepTest {
    @Resource(name="GetTransferDataStep")
    GetTransferDataStep getTransferDataStep;

	/**
	 * http://www.degraeve.com/images/lcsm.gif
	 * @throws Exception
	 */

	@Test
    public void testExecute() throws Exception {
    	/*BatchParams batchParams=new BatchParams();
    	batchParams.setBatchNo("20122578452202322");
    	batchParams.setTranDate(DateUtil.parse("2018-06-01", "yyyy-MM-dd"));
    	List<Map<String, Object>> objectList = getTransferDataStep.getObjectList(batchParams);
    	if(objectList!=null&&objectList.size()>0){
			for(Map<String, Object> object:objectList){
				int totalResult = getTransferDataStep.getTotalResult(batchParams, object);
			*//*	List<Map<String, Object>> dataList = getTransferDataStep.getDataList(batchParams, 0, 100, object);
				for(Map<String, Object> map:dataList){
					getTransferDataStep.execute(batchParams, 0, map, object);
				}*//*
				List<Transfer> dataList = getTransferDataStep.getDataList(batchParams, 0, 100, object);
				for (Transfer tf : dataList) {
					getTransferDataStep.execute(batchParams, 0, tf, object);
				}
				getTransferDataStep.updateObject(batchParams, object);
			}
    	}*/
    	
    }
}
