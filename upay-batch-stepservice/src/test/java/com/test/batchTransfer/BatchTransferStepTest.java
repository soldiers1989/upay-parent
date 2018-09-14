package com.test.batchTransfer;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.upay.batch.stepservice.transfer.BatchTransferStep;
import com.upay.batch.stepservice.transfer.DownloadTransferFileStep;
import com.upay.batch.stepservice.transfer.GetTransferDataStep;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.pay.PayFlowDetailPo;
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
public class BatchTransferStepTest {
    @Resource(name="BatchTransferStep")
    BatchTransferStep batchTransferStep;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setBatchNo("2012257845220232");
    	batchParams.setTranDate(DateUtil.parse("2017-09-22", "yyyy-MM-dd"));
		int totalResult = batchTransferStep.getTotalResult(batchParams, null);
		List<PayFlowDetailPo> dataList = batchTransferStep.getDataList(batchParams, 0, 100, null);
		for(PayFlowDetailPo map:dataList){
			batchTransferStep.execute(batchParams, 0, map, null);
		}
    }
}
