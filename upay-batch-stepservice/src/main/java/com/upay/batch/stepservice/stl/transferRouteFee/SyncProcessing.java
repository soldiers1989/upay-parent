package com.upay.batch.stepservice.stl.transferRouteFee;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.upay.dao.po.chk.StlRouteFeeBookPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;



/**
 * 同步昨日分润转账处理中的情况
 * Created by LB on 2017/8/7.
 */
public class SyncProcessing extends AbstractStepExecutor<Object, StlRouteFeeBookPo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncProcessing.class);

    @Resource
	IDaoService daoService;

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
		LOGGER.debug("检查昨日处理中的分润转账流水====================开始======================");
		//查询烟草上送的处理中的转账明细
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("transDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		int queryResult = (int) daoService.selectQueryResult(StlRouteFeeBookPo.class.getName() + ".syncProcessing",map,0,0).getTotalrecord();
		LOGGER.info("交易处理中的分润转账====================共==="+queryResult+" 条");
		return queryResult;
	}

	@Override
	public List<StlRouteFeeBookPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		//查询烟草上送的处理中的转账明细
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("transDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		QueryResult<StlRouteFeeBookPo> queryList = daoService.selectQueryResult(StlRouteFeeBookPo.class.getName() + ".syncProcessing",map,offset,pageSize);
		LOGGER.info("======================"+queryList.getResultlist().size());
		return queryList.getResultlist();
	}

	@Override
	public void execute(BatchParams batchParams, int index, StlRouteFeeBookPo data,
			Object object) throws BatchException {
		LOGGER.debug("分润转账处理中的订单:"+data.getUpayOrderNo());
		if(StringUtils.isNotBlank(data.getUpayOrderNo())){
			PayOrderListPo list=new PayOrderListPo();
			list.setOrderNo(data.getUpayOrderNo());
			list=daoService.selectOne(list);
			
			StlRouteFeeBookPo setStlRouteFee=new StlRouteFeeBookPo();
			StlRouteFeeBookPo whereStlRouteFee=new StlRouteFeeBookPo();
			whereStlRouteFee.setId(data.getId());;
			
			if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(list.getOrderStat())){
				//如果流水状态和第三方交易状态都为成功，则将转账明细状态设置为成功
				setStlRouteFee.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_SUCCESS);
				setStlRouteFee.setUpayDate(list.getOrderDate());
				setStlRouteFee.setRemark1("转账成功");
				daoService.update(setStlRouteFee, whereStlRouteFee);
			}else if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_FAIL.equals(list.getOrderStat())){
				//否则设置为失败   失败后，后面在SmokeTransferAccount 类中会重发起新的转账交易
				setStlRouteFee.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_FAIL);
				setStlRouteFee.setUpayDate(list.getOrderDate());
				setStlRouteFee.setRemark1("转账失败");
				daoService.update(setStlRouteFee, whereStlRouteFee);
			}
		}else{
			throw new BatchException(data.getUpayOrderNo()+"   分润转账状态为处理中,但支付平台订单为空,请检查!!!");
		}
		
		LOGGER.debug("检查昨日处理中的分润转账处理中的流水====================结束======================");
	}
}
