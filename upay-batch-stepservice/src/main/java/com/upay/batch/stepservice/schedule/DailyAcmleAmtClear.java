package com.upay.batch.stepservice.schedule;


import java.math.BigDecimal;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.upay.dao.po.mer.MerTransTemplatePo;
import com.upay.dao.po.pay.PayFlowListPo;


/**
 * 商户日累计限额清零
 * 
 * @author yhy
 * @version v1.0
 * @CreateDate: 2018-03-08
 
 */
public class DailyAcmleAmtClear extends AbstractStepExecutor<Object, Object> {

    @Override
    public void execute(BatchParams batchParams, int index, Object data, Object object) throws BatchException {
    	
    	logger.info("商户日累计限额清零——>开始");
    	MerTransTemplatePo param = new MerTransTemplatePo();
    	param.setDailyAcmlativeAmt(new BigDecimal("0"));
    	MerTransTemplatePo where = new MerTransTemplatePo();
    	daoService.update(param, where);
      
    }


    @Override
    public void updateObject(BatchParams batchParams, Object object) {
    	
    }
}
