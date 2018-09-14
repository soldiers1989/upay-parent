package com.upay.batch.stepservice.migration;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.util.DateUtil;
import com.upay.dao.IDaoService;
import com.upay.dao.po.pay.PayOrderListHisPo;
import com.upay.dao.po.pay.PayOrderListPo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @description 批量备份   订单表到历史订单表
 */
public class BackupPayOrderList extends AbstractStepExecutor<PayOrderListHisPo, PayOrderListPo> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private IDaoService daoService;

	@Override
	public void execute(BatchParams batchParams, int index,
			PayOrderListPo data, PayOrderListHisPo object)
			throws BatchException {
		GnrParmPo parm=new GnrParmPo();
		parm.setParmId("MOVE_DATA_DAYS");
		parm=daoService.selectOne(parm);
		if(parm!=null){
			String moveDays=parm.getParmValue();
			Date transDate = batchParams.getTranDate();
			Date backupDate = DateUtil.add(transDate, Calendar.DAY_OF_MONTH, -Integer.valueOf(moveDays));
			String backupDateStr=DateUtil.format(backupDate, DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD);
			logger.info("备份"+backupDateStr+"天之前的订单信息数据");
			HashMap<Object, String> parmMap=new HashMap<Object,String>();
//			parmMap.put("orderNo", orderNo);
//			parmMap.put("startDate", orderNo);
			parmMap.put("endDate", backupDateStr);
			int insertSelect = daoService.insertSelect(PayOrderListHisPo.class.getName().concat(".dataMove")+"", parmMap);
			if(insertSelect>0){
				logger.info("备份"+backupDateStr+"之前的订单信息数据      成功!");
				
				logger.info("删除"+backupDateStr+"之前的订单信息数据      成功!");
				int delete = daoService.delete(PayOrderListPo.class.getName().concat(".deleteDataByDate")+"", parmMap);
				logger.info("删除"+backupDateStr+"之前的订单信息数据      "+delete);
			}
		}else{
			logger.info("未配置数据转移配置");
		}
	}
	
	
//    private int counter = 1;
//
//    @Override
//    public int getTotalResult(BatchParams batchParams, PayOrderListHisPo object) throws BatchException {
//        logger.debug("订单记录备份开始", BackupPayOrderList.class);
//        Date startDate = null;
//        Date endDate = null;
//        if (batchParams.getParameter().containsKey("startDate") && batchParams.getParameter().containsKey("endDate")) {
//            startDate = (Date) batchParams.getParameter().get("startDate");
//            endDate = (Date) batchParams.getParameter().get("endDate");
//        }
//        Integer count = null;
//        if (startDate != null && endDate != null) {
//            Map<String, Object> paramMap = new HashMap<String, Object>(2);
//            // 查询的开始时间
//            paramMap.put("orderStartDate", startDate);
//            // 查询的结束时间
//            paramMap.put("orderEndDate", endDate);
//            batchParams.getParameter().put("startDate", startDate);
//            batchParams.getParameter().put("endDate", endDate);
//            count = daoService.count(PayOrderListPo.class.getName() + ".selectQueryResultByTs3", paramMap);
//        }
//        logger.debug("订单记录备份开始，有{}订单记录需要处理", new Object[]{count});
//        return count;
//    }
//
//    @Override
//    public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset, int pageSize, PayOrderListHisPo object) throws BatchException {
//        logger.debug("订单记录备份开始，开始订单查询记录", BackupPayOrderList.class);
//        Map<String, Object> paramMap = new HashMap<>(2);
//        // 查询的开始时间
//        Date startDate = (Date) batchParams.getParameter().get("startDate");
//        // 查询的结束时间
//        Date endDate = (Date) batchParams.getParameter().get("endDate");
//        paramMap.put("orderStartDate", startDate);
//        paramMap.put("orderEndDate", endDate);
//        logger.debug("订单记录备份开始，结束订单查询记录", BackupPayOrderList.class);
//        return daoService.selectList(PayOrderListPo.class.getName() + ".selectQueryResultByTs3", paramMap);
//    }
//
//    @Override
//    public void execute(BatchParams batchParams, int index, PayOrderListPo data, PayOrderListHisPo object) throws BatchException {
//        logger.debug("订单记录备份开始，插入历史订单表开始", BackupPayOrderList.class);
//        PayOrderListHisPo payOrderListHisPo = new PayOrderListHisPo();
//        BeanUtils.copyProperties(data, payOrderListHisPo);
//        daoService.insert(payOrderListHisPo);
//        logger.debug("订单记录备份开始，插入历史订单表开始，插入第{}条记录", counter++);
//        logger.debug("订单记录备份结束，插入历史订单表结束", BackupPayOrderList.class);
//        //删除订单表数据
//        logger.debug("删除订单记录开始");
//        daoService.delete(data);
//        logger.debug("删除订单记录结束，共删除和迁移{}条记录", counter);
//    }


}
