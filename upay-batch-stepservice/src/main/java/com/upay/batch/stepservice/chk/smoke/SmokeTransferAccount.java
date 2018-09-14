/**
 * 
 */
package com.upay.batch.stepservice.chk.smoke;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.page.QueryResult;
import com.upay.batch.stepservice.schedule.BatchCommon;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.SmokeStlDetailPo;

/**
 * 针对未作处理的数据进行转账
 * 
 * @author lb
 * 
 */
public class SmokeTransferAccount extends
		AbstractStepExecutor<Object, SmokeStlDetailPo> {
	private final static Logger logger = LoggerFactory
			.getLogger(SmokeTransferAccount.class);

	private BatchCommon batchCommon;
	@Resource
	IDaoService daoService;

	@Override
	public int getTotalResult(BatchParams batchParams, Object object)
			throws BatchException {
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		map.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		int queryResult = (int) daoService.selectQueryResult(SmokeStlDetailPo.class.getName() + ".selectSmokeStlDetailByDate",map,0,0).getTotalrecord();
		logger.info(queryResult+"=======================");
		return queryResult;
		
//		SmokeStlPo smokeStlPo = new SmokeStlPo();
//		smokeStlPo.setMerDate(DateUtil.parse("2017-02-22 00:00:00", DataBaseConstans_ACC.DATE_FORMAT_YYYYMMDDHHmmSS));
//		List<SmokeStlPo> smokeStlList = daoService.selectList(smokeStlPo,0, 0);
//		return (int)smokeStlList.size();
	}

	@Override
	public List<SmokeStlDetailPo> getDataList(BatchParams batchParams, int offset,
			int pageSize, Object object) throws BatchException {
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("stlStartDate", DateUtil.format(batchParams.getTranDate(), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		map.put("stlEndDate", DateUtil.format(DateUtil.add(batchParams.getTranDate(), Calendar.DAY_OF_MONTH, 1), DataBaseConstans_ACC.DATE_FORMAT_YYYY_MM_DD));
		QueryResult<SmokeStlDetailPo> queryList = daoService.selectQueryResult(SmokeStlDetailPo.class.getName() + ".selectSmokeStlDetailByDate",map,offset,pageSize);
		logger.info("======================"+queryList.getResultlist().size());
		return queryList.getResultlist();
	}

	@Override
	public void execute(BatchParams batchParams, int index, SmokeStlDetailPo data,
			Object object) throws BatchException {
		String payerMerNo = data.getPayerMerNo();//付款方商户号
		MerAcctInfoPo merAcctInfoPo=new MerAcctInfoPo();
		merAcctInfoPo.setMerNo(payerMerNo);
		merAcctInfoPo = daoService.selectOne(merAcctInfoPo);
		if(merAcctInfoPo==null){
			logger.info("商户:"+payerMerNo+" 商户账务信息表信息未配置!");
			throw new BatchException("商户:"+payerMerNo+" 商户账务信息表信息未配置!");
		}else{
			String stlAcctType = merAcctInfoPo.getStlAcctType();
			logger.info("烟草清算转账开始======================");
			BigDecimal transAmt = data.getTransAmt();//转账金额
			String payeeAcctNo = data.getPayeeAcctNo();//收款账号
			
			// 为本次商户转账扣款  从资金池账户口  扣款到了 核心待清算账户
			Map<String, Object> parmMap=new HashMap<String, Object>();
//			
			
			parmMap.put("transAmt", transAmt);//转账金额
//			if(DataBaseConstans_ACC.ACCT_TYPE_ELECT_ACCT.equals(stlAcctType)){//如果是虚拟账户到卡需要传入商户号
				parmMap.put("merNo", payerMerNo);
				parmMap.put("merName", merAcctInfoPo.getMerName());
				parmMap.put("merSeq", data.getMerSeq());
				
//			}
			
			parmMap.put("payerAccNo", merAcctInfoPo.getStlAcctNo());//付款账户    为商户结算账户
			parmMap.put("payeeAccNo", payeeAcctNo);//收款账号    烟草二清中的收款账号
			
			
			parmMap.put("payeeName", data.getPayeeName());//收款人姓名
			parmMap.put("payerName", data.getPayerName());//付款人姓名
			parmMap.put("stlAcctType", stlAcctType);//
			parmMap.put("payeeCardType", data.getPayeeCardType());//收款账户卡类型
			parmMap.put("bankId", data.getRemark2());//bankId 清算文件中，存入备用字段2中
			
			checkInputParm(stlAcctType, payerMerNo, merAcctInfoPo.getStlAcctNo(), payeeAcctNo);
			
			Message postAcc1006 = batchCommon.postAcc1006(parmMap);
			HashMap<String,Object> bodys = (HashMap<String,Object>)postAcc1006.getTarget().getBodys();
			
			String code = postAcc1006.getFault().getCode();
			String msg = postAcc1006.getFault().getMsg();
			logger.debug("调用转账接口：返回代码="+code+"      返回信息="+msg);
			SmokeStlDetailPo whereSmoke=new SmokeStlDetailPo();
			whereSmoke.setId(data.getId());
			
			SmokeStlDetailPo updateSmoke=new SmokeStlDetailPo();
			updateSmoke.setUpayDate(batchParams.getTranDate());
			if(null!=bodys){
				String upaySeq=(String)bodys.get("transSubSeq");
				updateSmoke.setUpaySeq(upaySeq);
				if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(code)&&null!=upaySeq){
					//根据平台交易流水号查询转账流水是状态
					PayFlowListPo list=new PayFlowListPo();
					list.setTransSubSeq(upaySeq);
					list=daoService.selectOne(list);
					if(null!=list){
						//通过第三方通道流水状态判断流水状态
						String routeTransStat = list.getRouteTransStat();
					    
						if(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS.equals(routeTransStat)){//成功
							updateSmoke.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_SUCCESS);
							updateSmoke.setRemark1("转账成功");
							logger.info("烟草清算转账  收款账号: "+payeeAcctNo+" 金额:"+transAmt+" 转账成功!  批次号:"+batchParams.getBatchNo());
						}else if(CommonConstants_GNR.OUT_PAY_STAT_ING.equals(routeTransStat)){//处理中
							updateSmoke.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_PROCESSING);
							updateSmoke.setRemark1("[流水号："+upaySeq+"   转账处理中]");
							logger.info("烟草清算转账  收款账号: "+payeeAcctNo+" 金额:"+transAmt+" 转账处理中!  批次号:"+batchParams.getBatchNo());
						}else{//失败
							updateSmoke.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_FAIL);
							updateSmoke.setRemark1(msg);
							logger.info("烟草清算转账  收款账号: "+payeeAcctNo+" 金额:"+transAmt+" 转账失败!  批次号:"+batchParams.getBatchNo());
						}
					}else{
						updateSmoke.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_FAIL);
						updateSmoke.setRemark1(msg);
						logger.info("烟草清算转账  收款账号: "+payeeAcctNo+" 金额:"+transAmt+" 转账失败!  批次号:"+batchParams.getBatchNo());
					}
				}else{
					updateSmoke.setResult(DataBaseConstants_PAY.SMOKE_TRANSFER_FAIL);
					updateSmoke.setRemark1(msg);
					logger.info("烟草清算转账  收款账号: "+payeeAcctNo+" 金额:"+transAmt+" 转账失败!  批次号:"+batchParams.getBatchNo());
				}
				daoService.update(updateSmoke,whereSmoke);
			}
			logger.info("烟草清算转账结束======================");
		}
	}

	public void setBatchCommon(BatchCommon batchCommon) {
		this.batchCommon = batchCommon;
	}
	
	private void checkInputParm(String stlAcctType,String merNo,String payerAcctNo,String payeeAcctNo){
		if(DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(stlAcctType)){
			if(StringUtils.isBlank(merNo)){
				throw new BatchException("烟草批量转账付款商户号不能为空");
			}
		}else{
			if(StringUtils.isBlank(payerAcctNo)){
				throw new BatchException("烟草批量转账付款方账号不能为空");
			}
		}
		
		if(StringUtils.isBlank(payeeAcctNo)){
			throw new BatchException("烟草批量转账收款方账号不能为空");
		}
	}
}
