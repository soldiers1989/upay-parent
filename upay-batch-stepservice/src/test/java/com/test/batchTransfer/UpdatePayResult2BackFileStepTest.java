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
import com.upay.batch.stepservice.transfer.DownloadTransferFileStep;
import com.upay.batch.stepservice.transfer.GenBackFileStep;
import com.upay.batch.stepservice.transfer.GetTransferDataStep;
import com.upay.batch.stepservice.transfer.UpdatePayResult2BackFileStep;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.gnr.FileInfoPo;
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
public class UpdatePayResult2BackFileStepTest {
    @Resource(name="UpdatePayResult2BackFileStep")
    UpdatePayResult2BackFileStep updatePayResult2BackFileStep;
    
    @Test
    public void testExecute() throws Exception {
    	BatchParams batchParams=new BatchParams();
    	batchParams.setBatchNo("2012257845220232");
    	batchParams.setTranDate(DateUtil.parse("2018-05-27", "yyyy-MM-dd"));
    	List<FileInfoPo> dataList = updatePayResult2BackFileStep.getObjectList(batchParams);
    	for(FileInfoPo info:dataList){
    		updatePayResult2BackFileStep.execute(batchParams, 0, null,info);
    		updatePayResult2BackFileStep.updateObject(batchParams, info);
    	}
    }
}