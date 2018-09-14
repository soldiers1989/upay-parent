/**
 * 
 */
package com.upay.batch.stepservice.chk.smoke;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;

/**
 * 针对烟草当日上送的昨日处理中的转账数据作转账状态  作明确的处理
 * 
 * @author lb
 * 
 */
public class QueryProcessingTransfer extends AbstractStepExecutor<Object, SmokeStlDetailPo> {
	private final static Logger logger = LoggerFactory.getLogger(QueryProcessingTransfer.class);
	@Resource
	IDaoService daoService;

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
		logger.debug("检查昨日处理中的烟草转账流水====================开始======================");
		//查询烟草上送的处理中的转账明细
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("stlStartDate", DateUtil.format(batchParams.getTranDate(),DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		map.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		int queryResult = (int) daoService.selectQueryResult(SmokeStlDetailPo.class.getName() + ".selectProcessingDetailByDate",map,0,0).getTotalrecord();
		logger.info("交易处理中的烟草转账====================共==="+queryResult+" 条");
		return queryResult;
	}

	@Override
	public List<SmokeStlDetailPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		//查询烟草上送的处理中的转账明细
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("stlStartDate", DateUtil.format(batchParams.getTranDate(),DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		map.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		QueryResult<SmokeStlDetailPo> queryList = daoService.selectQueryResult(SmokeStlDetailPo.class.getName() + ".selectProcessingDetailByDate",map,offset,pageSize);
		logger.info("======================"+queryList.getResultlist().size());
		return queryList.getResultlist();
	}

	@Override
	public void execute(BatchParams batchParams, int index, SmokeStlDetailPo data,
			Object object) throws BatchException {
		if(null!=data.getUpaySeq()){
			PayFlowListPo list=new PayFlowListPo();
			list.setTransSubSeq(data.getUpaySeq());
			list=daoService.selectOne(list);
			
			SmokeStlDetailPo setStlDetail=new SmokeStlDetailPo();
			SmokeStlDetailPo whereStlDetail=new SmokeStlDetailPo();
			whereStlDetail.setId(data.getId());;
			
			if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(list.getTransStat())&&
					CommonConstants_GNR.OUT_PAY_STAT_SUCCESS.equals(list.getRouteTransStat())){
				//如果流水状态和第三方交易状态都为成功，则将转账明细状态设置为成功
				setStlDetail.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_SUCCESS);
				setStlDetail.setUpayDate(list.getRouteDate());
				setStlDetail.setRemark1("转账成功");
				daoService.update(setStlDetail, whereStlDetail);
			}else if(DataBaseConstants_PAY.T_PAY_TX_FAL.equals(list.getTransStat())||
					CommonConstants_GNR.OUT_PAY_STAT_FAIL.equals(list.getRouteTransStat())){
				//否则设置为失败   失败后，后面在SmokeTransferAccount 类中会重发起新的转账交易
				setStlDetail.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_FAIL);
				setStlDetail.setUpayDate(list.getRouteDate());
				setStlDetail.setRemark1("转账失败");
				daoService.update(setStlDetail, whereStlDetail);
			}
		}else{
			logger.error("烟草上送明细转账状态为处理中,但支付平台流水为空,请检查!!!");
			throw new BatchException("烟草上送明细转账状态为处理中,但支付平台流水为空,请检查!!!");
		}
		
		logger.debug("检查昨日处理中的烟草转账流水====================结束======================");
	}
}
