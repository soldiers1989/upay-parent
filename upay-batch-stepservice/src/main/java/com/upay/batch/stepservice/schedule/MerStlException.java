package com.upay.batch.stepservice.schedule;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.batch.stepservice.stl.mer.MerStlStep;
import com.upay.commons.constants.CmparmConstants;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.ParmsContext;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayFlowListHisPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * 商户结算异常处理
 * 主要针对结算到他行卡时，调用中金返回处理中的情况
 * 
 * @author yhy
 * 
 */
public class MerStlException extends AbstractStepExecutor<Object, StlBookPo> {
    @Resource
    private IDaoService daoService;
    @Resource(name = "SA_ZJPAY_Pay4532Handler")
    private IDipperHandler<Message> zj4532;
    @Resource(name = "SA_ZJPAY_Pay2020Handler")
    private IDipperHandler<Message> zj2020;
    @Resource
    private ISequenceService sequenceService;
    @Resource(name = "coreCliDipperHandler")
    private IDipperHandler<Message> coreCliDipperHandler;
    Message msg;
    Message m;
    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
    	StlBookPo stlBookPo = new StlBookPo();
    	//查询结算状态为 入账异常——2
    	stlBookPo.setMerFlag(DataBaseConstants_BATCH.MER_FLAG_SPECIAL_BUSINESS);
    	stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
    	List<StlBookPo> list = daoService.selectList(stlBookPo);
		return list.size();
    	
    }


    @Override
    public List<StlBookPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
    	StlBookPo stlBookPo = new StlBookPo();
    	//查询结算状态为 入账异常——2
    	stlBookPo.setMerFlag(DataBaseConstants_BATCH.MER_FLAG_SPECIAL_BUSINESS);
    	stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
    	List<StlBookPo> list = daoService.selectList(stlBookPo);
		return list;
    }


    @Override
    public void execute(BatchParams batchParams, int index, StlBookPo stlBookPo, Object object)
            throws BatchException {
    	//核心资金通道信息
    	PayRouteInfoPo routeInfo = new PayRouteInfoPo();
        routeInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);// 平台待清算账户获取
        routeInfo = daoService.selectOne(routeInfo);
        //中金资金通道信息
        PayRouteInfoPo zjRouteInfo = new PayRouteInfoPo();
    	zjRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);//中金
    	zjRouteInfo = daoService.selectOne(zjRouteInfo);
    	//商户结算账户信息
    	MerAcctInfoPo plateClearAcct = new MerAcctInfoPo();
        plateClearAcct.setMerNo(stlBookPo.getMerNo());
        plateClearAcct = daoService.selectOne(plateClearAcct);
    	
        //初始化接口参数
    	Map<String, Object> coreMap = initReqCore();
    	
    	//结算时，当中金返回处理中时，会将流水存入remark备用字段中
    	String remark = stlBookPo.getRemark();
    	PayFlowListPo payFlowListPo = new PayFlowListPo();
    	payFlowListPo.setTransSubSeq(remark);
    	PayFlowListPo flow = daoService.selectOne(payFlowListPo);
    	if(flow == null){
    		logger.error("-------------流水号[{}]不存在",remark);
    		return;
    	}
    	
    	BigDecimal zero =new BigDecimal("0");
    	//商户应付金额
    	BigDecimal allPayAmt = stlBookPo.getPayAmt()==null?zero:(stlBookPo.getPayAmt().add(stlBookPo.getPayFee()==null?zero:stlBookPo.getPayFee()));
        //商户应收金额
    	BigDecimal allRevAmt = stlBookPo.getRevAmt()==null?zero:stlBookPo.getRevAmt().add(stlBookPo.getRevFee()==null?zero:stlBookPo.getRevFee());
    	String addOrSub = null;
    	
    	if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(flow.getRouteCode())){
    		//如果应付小于应收，则调用中金代付接口查询；反之，调用中金代收接口查询
        	Message handle = null;
        	Map<String, Object> bodys = (Map<String, Object>) m.getTarget().getBodys();
        	bodys.put("txSN", flow.getRouteSeq());
        	
        	try {
    	    	if(allPayAmt.compareTo(allRevAmt)<0){
    				handle = zj4532.handle(m);
    	    	}else if(allPayAmt.compareTo(allRevAmt)>0){
    	    		handle = zj2020.handle(m);
    	    	}
        	} catch (Exception e) {
        		logger.error("调用中金查询接口异常");
        		logger.error(e.toString());
        	}
    	    if (!handle.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
    	    	logger.error("-------------调用中金接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                          handle.getFault().getCode(), handle.getFault().getMsg());
    	    	return;
    	    }
    	    Map<String, Object> zjBody = (Map<String, Object>)handle.getTarget().getBodys();
    		String status = (String) zjBody.get("status");
    		Date sysDate = null ;
    		//中金代付接口不会返回通道处理日期
    		if (allPayAmt.compareTo(allRevAmt) < 0) {
    			sysDate = SysInfoContext.getSysDate();
            } else if (allPayAmt.compareTo(allRevAmt) > 0) {
            	String bankTxTime = (String)zjBody.get("bankTxTime");
            	if(StringUtils.isNotBlank(bankTxTime)){
            		try {
    					sysDate = new SimpleDateFormat("yyyyMMdd").parse(bankTxTime.substring(0, 8));
    				} catch (ParseException e) {
    					e.printStackTrace();
    				}
            	}
            }
    		if(status == null){
    			logger.error("中金接口返回异常");
    			return;
    		}
    		//如果为正在处理，直接返回不做处理
    		if(CommonConstants_GNR.ZJ_C_PAY_STAT_ING.equals(status)){
    			return;
            }
    		//如果为处理失败，则将结算状态修改为0初始状态，中金流水修改为失败状态，留待手动执行结算批量或者第二天定时执行结算批量
            if(CommonConstants_GNR.ZJ_C_PAY_STAT_FAIL.equals(status)){
            	stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_CHECK_IN);
                daoService.update(stlBookPo);
                flow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                flow.setRouteDate(sysDate);
                flow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                daoService.update(flow);
                return;
            }
            //处理成功，修改中金流水
            if(CommonConstants_GNR.ZJ_C_PAY_STAT_SUCCESS.equals(status)){
            	flow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
            	flow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
            	flow.setRouteDate(sysDate);
            	flow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
            	daoService.update(flow);
            }
    	}else if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(flow.getRouteCode())){
    		
    		SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMD);
    		String querySeq=sequenceService.generatePayFlowSeq();
    		coreMap.put("tranCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_08003);
    		coreMap.put("bizSerialNo", querySeq);
    		coreMap.put("orgBizDate", sim.format(flow.getSysDate()));//原正交易业务日期
    		coreMap.put("orgBizSerialNo", "1111111");//原正交易流水号
    		//coreMap.put("orgBizSerialNo", flow.getTransSubSeq());//原正交易流水号
    		Message coreHandle = null;
    		Map<String,Object> result=null;
			try {
				coreHandle = coreCliDipperHandler.handle(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//失败处理
			if (!coreHandle.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
                logger.error("-------------调用核心交易查询接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                		coreHandle.getFault().getCode(), coreHandle.getFault().getMsg());
                return;
             }
			
			if(coreHandle.getTarget().getBodys()!=null&&coreHandle.getTarget().getBodys() instanceof Map){            
	            result=(Map<String, Object>) coreHandle.getTarget().getBodys();
	        }
			String status = result.get("status").toString();
			if(CommonConstants_GNR.CORE_BANK_PAY_STAT_SUCCESS.equals(status)){
				//如果交易成功，更新流水状态、清算状态
				flow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
				flow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
				flow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
				daoService.update(flow);
				stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);// 状态：1完成入账
	    		daoService.update(stlBookPo);
	    		logger.info("该商户已经完成清算！");
	    		return;
            }
            if(CommonConstants_GNR.CORE_BANK_PAY_STAT_NO.equals(status)){
            	flow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
            	flow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
				daoService.update(flow);
            }
    	}
    	
    	
    	String seq=sequenceService.generatePayFlowSeq();
    	String transAmt = MoneyUtil.transferY2F(flow.getTransAmt().multiply(new BigDecimal(100)), 0).toString();
    	GnrParmPo parmPo=new GnrParmPo();
    	parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
    	parmPo=daoService.selectOne(parmPo);
    	if(null==parmPo){
    		logger.error("-------------中金备用金账户参数未配置!!!");
    	}
    	
    	MerStlStep merStlStep = new MerStlStep();
    	//登记核心记账流水
    	PayFlowListPo payFlow = new PayFlowListPo();
    	payFlow.setOrderNo(flow.getOrderNo());
    	payFlow.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
    	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
    	payFlow = daoService.selectOne(payFlow);
    	coreMap.put("tranCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);
    	if(payFlow == null ){
    		if(allPayAmt.compareTo(allRevAmt)<0){
	    		coreMap.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
	    		coreMap.put("bankCardNo", routeInfo.getClrAcctNo());
	    		coreMap.put("setAccount", zjRouteInfo.getTransAcctNo());
	    		addOrSub=CommonBaseConstans_PAY.ACC_AMT_ADD;
	    	}else if(allPayAmt.compareTo(allRevAmt)>0){
	    		coreMap.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
	    		coreMap.put("bankCardNo", zjRouteInfo.getTransAcctNo());
	    		coreMap.put("setAccount", routeInfo.getClrAcctNo());
	    		addOrSub=CommonBaseConstans_PAY.ACC_AMT_SUB;
	    	}
    		Map<String, Object> initPara = merStlStep.initPara(seq, transAmt, flow.getOrderNo(), addOrSub, routeInfo, zjRouteInfo, plateClearAcct, DataBaseConstants_PAY.ROUTE_CODE_HOST, true,parmPo);
    		payFlow = insertFlowList(initPara, batchParams);
    		daoService.insert(payFlow);
    	}else{
    		stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);// 状态：1完成入账
    		daoService.update(stlBookPo);
    		logger.info("该商户已经完成清算！");
    		return;
    	}
        try {
        	coreMap.put("bizSerialNo", seq);
        	coreMap.put("amount", transAmt);
			Message coreHandle = coreCliDipperHandler.handle(msg);
			//失败处理
			if (!coreHandle.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
				payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
             	daoService.update(payFlow);
                logger.error("-------------调用核心记账接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                		coreHandle.getFault().getCode(), coreHandle.getFault().getMsg());
                return;
             }
			//核心记账
			Map<String, Object> resp = (Map<String, Object>)coreHandle.getTarget().getBodys();
			String bkDateStr = (String)resp.get("bkDate");
            Date bkDate = new SimpleDateFormat("yyyyMMdd").parse(bkDateStr);
            String bkSeq =  (String)resp.get("bkSerialNo");
            if(CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(resp.get("respCode"))){
            	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
            	payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
            	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
            	payFlow.setRouteDate(bkDate);
            	payFlow.setRouteSeq(bkSeq);
            	daoService.update(payFlow);
            	
            }else{
            	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
            	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
            	payFlow.setRouteDate(bkDate);
            	payFlow.setRouteSeq(bkSeq);
            	daoService.update(payFlow);
            	return;
            }
		} catch (Exception e) {
			logger.error("-------------调用核心记账接口服务异常!!!");
			logger.error(e.toString());
			return;
		}
        stlBookPo.setRemark(payFlow.getOrderNo());
        stlBookPo.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);// 状态：1完成入账
        daoService.update(stlBookPo);
        logger.info("-------------商户[{}]扣款成功，更新记录状态...",stlBookPo.getMerNo());
        logger.info("-------------商户[{}]结算成功！！！",stlBookPo.getMerNo());
    }
     
    public Map<String, Object> initReqCore(){
    	m = MessageFactory.create(IdGenerateFactory.generateId(), "zjpaycli", "XML", "GBK",
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                        ""));
    	
    	msg = MessageFactory.create(IdGenerateFactory.generateId(), "corecli", "XML", "UTF-8",
                MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                    new HashMap<String, Object>()),
                FaultFactory.create(Constants.ResponseCode.SUCCESS, "交易成功"));
    	Map<String, Object> coreMap = (Map<String, Object>) msg.getTarget().getBodys();
    	
    	SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
        String time = sim.format(new Date());
        coreMap.put("channelId", "74");
    	coreMap.put("machineTime", time.substring(8));
    	coreMap.put("machineDate", time.substring(0, 8));
    	coreMap.put("bizDate", sim.format(SysInfoContext.getSysDate()).substring(0, 8));
    	return coreMap;
    }
    //登记流水
    public PayFlowListPo insertFlowList(Map<String,Object> map,BatchParams batchParams){
    	Date now = new Date();
    	PayFlowListPo pay = new PayFlowListPo();
        pay.setOrderNo((String)map.get("orderNo"));
        //差错处理以及订单状态同步判断的依据，勿动
        pay.setOrderDes("商户结算流水");
        pay.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);
        pay.setSysDate(batchParams.getTranDate());
        pay.setTransSubSeq((String)map.get("tranSeq"));
        pay.setPayerAcctNo((String)map.get("payerAcctNo"));
        pay.setRouteCode((String)map.get("routeCode"));
        pay.setPayerAcctType((String)map.get("payerAcctType"));
        pay.setPayerName((String)map.get("payerAcctName"));
        pay.setPayeeAcctNo((String)map.get("payeeAcctNo"));
        pay.setPayeeAcctType((String)map.get("payeeAcctType"));
        pay.setPayeeName((String)map.get("payeeAcctName"));
        pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
        pay.setTransAmt(new BigDecimal((String)map.get("amount")).divide(new BigDecimal("100")));
        pay.setTransTime(now);
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
        pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        pay.setLastUpdateTime(now);
        pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        HashMap<String, Object> parmMap = new HashMap<>();
		parmMap.put("orderNo", pay.getOrderNo());
		Integer maxSeqNo = (Integer) daoService.selectOne(
				PayFlowListPo.class.getName() + ".findMaxSeqNo", parmMap);
		if (null == maxSeqNo || 0 == maxSeqNo) {
			maxSeqNo = 1;
		} else {
			maxSeqNo = maxSeqNo + 1;
		}
		pay.setSeqNo(maxSeqNo);
        return pay;
    }
}
