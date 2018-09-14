package com.upay.batch.stepservice.stl.mer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.pactera.dipper.context.DipperParm;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonBaseConstants_USR;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.constants.DateBaseConstants_MER;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.CommonMethodUtils;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.PlatFormHolidayContext;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.mer.MerBaseInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;


/**
 * Created by dk on 2017/2/21.
 */
public class MerStlStep extends AbstractStepExecutor<StlBookPo, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MerStlStep.class);
    @Resource(name = "coreCliDipperHandler")
    private IDipperHandler<Message> coreCliDipperHandler;
    private PayRouteInfoPo payRouteInfoPo;
    @Resource
    private ISequenceService sequenceService;
    @Resource(name = "SA_ZJPAY_Pay4530Handler")
    private IDipperHandler<Message> zj4530;
    @Resource(name = "SA_ZJPAY_Pay2011Handler")
    private IDipperHandler<Message> zj2011;
    
    @Override
    public List<StlBookPo> getObjectList(BatchParams batchParams) throws BatchException {
    	
    	//判断是否是指定日期执行
    	if(batchParams.getParameter().containsKey("tranDate")){
    		Date tranDate = (Date)batchParams.getParameter().get("tranDate");
    		Date preDate = (Date)batchParams.getParameter().get("preDate");
    		batchParams.setTranDate(tranDate);
    		batchParams.setPreDate(preDate);
    	}
    	
        LOGGER.info("-------------商户结算开始");
        List<StlBookPo> stlBookPoList = new ArrayList<StlBookPo>();
        if (!PlatFormHolidayContext.isWorkDay(batchParams.getTranDate(), "0")) {
            LOGGER.info("-------------非工作日，商户不参与结算");
            return stlBookPoList;
        } else {
            Map<String, Object> sqlParam = new HashMap<String, Object>();
            // sqlParam.put("stlBatchNo", batchParams.getBatchNo());
            sqlParam.put("stat", DataBaseConstants_BATCH.STL_STAT_CHECK_IN);
            stlBookPoList = daoService.selectList(StlBookPo.class.getName() + ".firstMerStlQuery", sqlParam);
            LOGGER.info("-------------查询结算记录数[{}]", stlBookPoList.size());
            return stlBookPoList;
        }
    }


    @Override
    public void execute(BatchParams batchParams, int index, Object data, StlBookPo object)
            throws BatchException {
        String merNo = object.getMerNo();
        MerBaseInfoPo merBaseInfoPo = new MerBaseInfoPo();
        merBaseInfoPo.setMerNo(merNo);
        merBaseInfoPo.setMerState(DateBaseConstants_MER.MER_STAT_NORMAL);
        merBaseInfoPo = daoService.selectOne(merBaseInfoPo);

        PayRouteInfoPo routeInfo = new PayRouteInfoPo();
        routeInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);// 平台待清算账户获取
        routeInfo = daoService.selectOne(routeInfo);
        
        PayRouteInfoPo zjRouteInfo = new PayRouteInfoPo();
    	zjRouteInfo.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);//中金
    	zjRouteInfo = daoService.selectOne(zjRouteInfo);
    	
        if (null != merBaseInfoPo) {
            // 查询商户的结算账户
            MerAcctInfoPo plateClearAcct = new MerAcctInfoPo();
            plateClearAcct.setMerNo(merNo);
            plateClearAcct = daoService.selectOne(plateClearAcct);
            if(plateClearAcct==null){
                LOGGER.error("-------------商户[{}]无结算账户",merNo);
                //无商户结算账户不结算
                return;
            }
            LOGGER.info("查询商户的结算账户");
            // 第一步 调用核心的接口获取对账的文件名
            Message msg = MessageFactory.create(IdGenerateFactory.generateId(), "corecli", "XML", "UTF-8",
                MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                    new HashMap<String, Object>()),
                FaultFactory.create(Constants.ResponseCode.SUCCESS, "交易成功"));
            //中金代付接口
            Message m =
                    MessageFactory.create(IdGenerateFactory.generateId(), "zjpaycli", "XML", "GBK",
                        MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                            new HashMap<String, Object>()), FaultFactory.create(Constants.ResponseCode.SUCCESS,
                            ""));
            //核心接口参数
            Map<String, Object> body = (Map<String, Object>) msg.getTarget().getBodys();
            //中金接口参数
            Map<String, Object> zjMap = (Map<String, Object>) m.getTarget().getBodys();
            Date date = batchParams.getPreDate();
            // 中金备用金账户
            GnrParmPo parmPo = null;
            SimpleDateFormat sim = new SimpleDateFormat(CommonBaseConstants_USR.DATE_EXPRESSION_YMDHMS);
            String time = sim.format(new Date());
            body.put("tranCode", "08001");
            body.put("machineTime", time.substring(8));
            body.put("machineDate", time.substring(0, 8));
            body.put("bizDate", sim.format(batchParams.getTranDate()).substring(0, 8));

            // xml报文体组装
            BigDecimal zero =new BigDecimal("0");
            BigDecimal allPayAmt = object.getPayAmt()==null?zero:(object.getPayAmt().add(object.getPayFee()==null?zero:object.getPayFee()));
            BigDecimal allRevAmt = object.getRevAmt()==null?zero:object.getRevAmt().add(object.getRevFee()==null?zero:object.getRevFee());

            boolean isStl = true;
            boolean isAcc = false;
            boolean isZJ = false;
            logger.info("-------------商户应收总金额[{}],应付总金额[{}]，轧差后商户应付金额[{}]", allRevAmt, allPayAmt,allPayAmt.subtract(allRevAmt).toString());
            body.put("currency", DataBaseConstants_PAY.T_CORE_CCY_CNY);// 币种
            if(DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(plateClearAcct.getStlAcctType())){
                //更新账户余额
                isAcc=true;
                if(allPayAmt.compareTo(allRevAmt) < 0){
                    body.put("bankCardNo", routeInfo.getClrAcctNo());// 付款帐号
                    body.put("setAccount", routeInfo.getTransAcctNo());// 收款帐号
                }else{
                    body.put("bankCardNo",routeInfo.getTransAcctNo());// 付款帐号
                    body.put("setAccount",routeInfo.getClrAcctNo());// 收款帐号
                }
                //核心记账
                body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);// 4-内转内
            }else if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(plateClearAcct.getStlAcctType())){
                //核心记账
                body.put("bankCardNo",plateClearAcct.getStlAcctNo());// 付款帐号
                body.put("setAccount",routeInfo.getClrAcctNo());// 付款帐号
                if(allPayAmt.compareTo(allRevAmt) < 0){
                    body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_CASH);
                }else{
                    body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_PAY);                     
                }
            }else if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(plateClearAcct.getStlAcctType())){
                body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
                if(allPayAmt.compareTo(allRevAmt) < 0){
                	body.put("bankCardNo", routeInfo.getClrAcctNo());
                    body.put("setAccount", plateClearAcct.getStlAcctNo());
                }else{
                	body.put("bankCardNo", plateClearAcct.getStlAcctNo());                     
                    body.put("setAccount", routeInfo.getClrAcctNo());
                }
            }else if (DataBaseConstants_PAY.ACCT_TYPE_PUBLIC_ACCT.equals(plateClearAcct.getStlAcctType())){
            	 if(allPayAmt.compareTo(allRevAmt) < 0){
            		 body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_PUBLIC);
                 }else{
                	 body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_PUBLICTOIN);
                 }
            	body.put("bankCardNo",plateClearAcct.getStlAcctNo());
                body.put("setAccount",routeInfo.getClrAcctNo());
            }else if(DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(plateClearAcct.getStlAcctType())){
                body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
                if(allPayAmt.compareTo(allRevAmt) < 0){
                	body.put("bankCardNo", routeInfo.getClrAcctNo());
                    body.put("setAccount", plateClearAcct.getStlAcctNo());
                }else{
                	body.put("bankCardNo", plateClearAcct.getStlAcctNo());                     
                    body.put("setAccount", routeInfo.getClrAcctNo());
                }
            }else if (DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(plateClearAcct.getStlAcctType()) || DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(plateClearAcct.getStlAcctType())){
	        	//请求中金接口4530    
            	parmPo=new GnrParmPo();
            	parmPo.setParmId(DataBaseConstans_ACC.ZJ_ACCT_NO);
            	parmPo=daoService.selectOne(parmPo);
            	if(null==parmPo){
            		 throw new BatchException("-------------中金备用金账户参数未配置!!!");
            	}
            	if(DataBaseConstants_PAY.ACCT_TYPE_OTHERDEBIT_ACCT.equals(plateClearAcct.getStlAcctType())){
            		zjMap.put("accountType", "11");//11 个人账户  12企业账户
            	}else if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(plateClearAcct.getStlAcctType())){
            		zjMap.put("accountType", "12");//11 个人账户  12企业账户
            	}
            	//应收大于应付 调用中金代付接口
            	if(allPayAmt.compareTo(allRevAmt) < 0){
            		zjMap.put("vbindAcctNo", plateClearAcct.getStlAcctNo());
            		zjMap.put("bankId", plateClearAcct.getBankId());//银行机构编号
            		zjMap.put("paymentAccountName", parmPo.getParmName());
            		zjMap.put("paymentAccountNumber", parmPo.getParmValue());
            		zjMap.put("certName", plateClearAcct.getStlAcctName());
            		//调用核心接口准备参数
            		body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
            		body.put("bankCardNo", routeInfo.getClrAcctNo());
                    body.put("setAccount", zjRouteInfo.getTransAcctNo());
            	}else if(allPayAmt.compareTo(allRevAmt) > 0){//应收小于应付 调用中金代收接口
            		if(DataBaseConstants_PAY.ACCT_TYPE_OTHERPUBLIC_ACCT.equals(plateClearAcct.getStlAcctType())){
                		LOGGER.error("-------------暂不支持对公账户的代收交易!!!");
                		object.setRemark("暂不支持对公账户的代收交易");
                        daoService.update(object);
                		return;
                	}
            		zjMap.put("logoId", plateClearAcct.getBankId());//银行机构编号
            		zjMap.put("accountName", plateClearAcct.getStlAcctName());//账户名称
            		zjMap.put("accountNumber", plateClearAcct.getStlAcctNo());
            		zjMap.put("settlementFlag", "0001");//结算标识 默认0001
            		//调用核心接口准备参数
            		body.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
            		body.put("bankCardNo", zjRouteInfo.getTransAcctNo());
                    body.put("setAccount", routeInfo.getClrAcctNo());
            		
            	}
                isZJ = true;
                
            }else{
                LOGGER.error("-------------商户[{}]结算账户类型错误",merNo);
                object.setRemark("商户"+merNo+"结算账户类型错误，结算失败");
                daoService.update(object);
                return;
            }
            String addOrSub="";
            if (allPayAmt.compareTo(allRevAmt) > 0) {
                addOrSub=CommonBaseConstans_PAY.ACC_AMT_SUB;
                body.put("amount", allPayAmt.subtract(allRevAmt).toString());
            } else if (allPayAmt.compareTo(allRevAmt) < 0) {
                //核心记账
                addOrSub=CommonBaseConstans_PAY.ACC_AMT_ADD;
                body.put("amount", allRevAmt.subtract(allPayAmt).toString());
            } else if (allPayAmt.compareTo(allRevAmt) == 0) {
                isStl = false;
            }
            String orderNo = null;
            if(allPayAmt.compareTo(allRevAmt) != 0){
            	//商户结算并不生成订单，只需在流水中写入订单号，方便查询同一次结算的两笔流水（中金与核心流水）
            	orderNo = sequenceService.generateOrderNo("UPAY");
            	object.setRemark(orderNo);
            }
            //如果结算账户是虚拟账户，并且轧差后的金额不为0
            if(isAcc&&isStl){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("updateAmt", body.get("amount"));
                map.put("addOrSub", addOrSub);
                map.put("accNo", plateClearAcct.getStlAcctNo());
                int i=daoService.update(AccVbookPo.class.getName().concat(".updateAccountAmtSub"),map);
                if(i!=1){
                    LOGGER.error("-------------商户[{}]虚拟账户余额不足，结算失败",merNo);
                    object.setRemark("商户"+merNo+"虚拟账户余额不足，结算失败");
                    daoService.update(object);
                    return;
                }
            }
            
            //调用中金代收、代付成功后，修改为true
            boolean zjSuccess = false;
            //需要结算，并且需要调用中金接口
            if(isStl&&isZJ){
            	try {
            		String amount = new BigDecimal(body.get("amount").toString()).multiply(new BigDecimal(100)).toString();
            		String seq = CommonMethodUtils.getZJSubSeq();//中金流水号
            		String paltSeq=sequenceService.generatePayFlowSeq();//平台流水号
            		if(allPayAmt.compareTo(allRevAmt) < 0){
            			zjMap.put("routeSeq", seq);
            			zjMap.put("totalFee", amount);
            		}else{
            			zjMap.put("zjSeq", seq);
            			zjMap.put("amount", amount);
            		}
            		Map<String, Object> flowMap = initPara(paltSeq,amount,orderNo,addOrSub,routeInfo,zjRouteInfo,plateClearAcct,DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY,isZJ,parmPo);
            		PayFlowListPo payFlow = insertFlowList(flowMap, batchParams);
            		daoService.insert(payFlow);
            		Message handle = null;
            		if (allPayAmt.compareTo(allRevAmt) < 0) {
            			handle = zj4530.handle(m);
                    } else if (allPayAmt.compareTo(allRevAmt) > 0) {
                    	handle = zj2011.handle(m);
                    }
					 //交易流水参数
					if (!handle.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
                        logger.error("-------------调用中金代付接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                            handle.getFault().getCode(), handle.getFault().getMsg());
                        payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    	daoService.update(payFlow);
                    	
                    	//不设置结算状态为失败的原因是 为了第二日继续结算
                    	String msgInfo = handle.getFault().getMsg();
                    	if(StringUtils.isNotBlank(msgInfo)){
                    		object.setRemark("调用中金接口失败："+(msgInfo.length()>50?msgInfo.substring(0,50):msgInfo.substring(0,msgInfo.length()-1)));
                    	}else{
                    		object.setRemark("调用中金接口失败：");
                    	}
                		
//                    	object.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
                    	daoService.update(object);
                    	
                        //throw new BatchException("-------------调用中金代付接口服务返回异常!!!");
                        return;
                    }
					Map<String, Object> zjBody = (Map<String, Object>)handle.getTarget().getBodys();
					
					String status = (String) zjBody.get("status");
					String zjSeq = (String) zjBody.get("txSN");
					Date sysDate = null ;
					//中金代付接口不会返回通道处理日期
					if (allPayAmt.compareTo(allRevAmt) < 0) {
						sysDate = batchParams.getTranDate();
                    } else if (allPayAmt.compareTo(allRevAmt) > 0) {
                    	String bankTxTime = (String)zjBody.get("bankTxTime").toString().substring(0, 8);
                    	if(StringUtils.isNotBlank(bankTxTime)){
                    		sysDate = new SimpleDateFormat("yyyyMMdd").parse(bankTxTime);
                    	}
                    }
                    if(CommonConstants_GNR.ZJ_C_PAY_STAT_ING.equals(status)){
                    	//如果中金返回处理中，交由批量处理
                    	object.setRemark("中金处理中，流水号："+zjSeq);
                    	object.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
                    	daoService.update(object);
                    	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_ING);
                    	payFlow.setRouteSeq(zjSeq);
                    	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_PROING);
                    	daoService.update(payFlow);
                    	return;
                    }
                    if(CommonConstants_GNR.ZJ_C_PAY_STAT_FAIL.equals(status)){
                    	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    	payFlow.setRouteSeq(zjSeq);
                    	payFlow.setRouteDate(sysDate);
                    	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                    	daoService.update(payFlow);
                    	object.setRemark("中金支付失败，流水号:"+seq);
                    	daoService.update(object);
                    	logger.error("中金代付接口返回[支付失败]");
                    	return;
                    }
                    if(CommonConstants_GNR.ZJ_C_PAY_STAT_SUCCESS.equals(status)){
                    	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                    	payFlow.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
                    	payFlow.setRouteDate(sysDate);
                    	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                    	payFlow.setRouteSeq(zjSeq);
                    	daoService.update(payFlow);
                    	zjSuccess = true; 
                    }
				} catch (Exception e) {
					LOGGER.error("-------------调用中金代付接口服务异常!!!");
					LOGGER.error(e.toString());;
					return;
				}
            }
            //生成流水号
            String coreSeq = sequenceService.generatePayFlowSeq();
            try {
                if (isStl) {
                	body.put("bizSerialNo", coreSeq);
                	String amount = MoneyUtil.transferY2F(new BigDecimal(body.get("amount").toString()).multiply(new BigDecimal(100)), 0).toString();
                    body.put("amount",amount);
                    //交易流水参数
                    Map<String, Object> flowMap = initPara(coreSeq,amount,orderNo,addOrSub,routeInfo,zjRouteInfo,plateClearAcct,DataBaseConstants_PAY.ROUTE_CODE_HOST,isZJ,parmPo);
                    PayFlowListPo payFlow = insertFlowList(flowMap, batchParams);
                    daoService.insert(payFlow);
                    Message handle = coreCliDipperHandler.handle(msg);
                    if (!handle.getFault().getCode().equals(Constants.ResponseCode.SUCCESS)) {
                    	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                    	daoService.update(payFlow);
                        logger.error("-------------调用核心记账接口服务返回异常!!!，错误码[{}],错误信息[{}]",
                            handle.getFault().getCode(), handle.getFault().getMsg());
                        //如果中金成功，核心失败，则该笔结算即为异常，由异常批量处理
                        String errorMsg = handle.getFault().getMsg();
    					if(StringUtils.isNotBlank(errorMsg)){
    						if(errorMsg.length()>50){
    							errorMsg=errorMsg.substring(0,50);
    						}else{
    							errorMsg=errorMsg.substring(0,errorMsg.length()-1);
    						}
    					}
    					
                    	if(zjSuccess){
                    		object.setRemark("中金成功，核心失败，流水号为："+coreSeq+"  "+errorMsg);
                        	object.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
                        	daoService.update(object);
                    	}else{
        					//不设置结算状态为失败的原因是 为了第二日继续结算
                    		object.setRemark("结算核心失败："+errorMsg);
//                        	object.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
                        	daoService.update(object);
                    	}
                        return;
                    }
                    Map<String, Object> resp = (Map<String, Object>)handle.getTarget().getBodys();
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
                    	String coreMsg=(String)resp.get("respMsg");
                    	if(StringUtils.isNotBlank(coreMsg)){
                    		if(coreMsg.length()>50){
                    			coreMsg=coreMsg.substring(0,50);
                    		}else{
                    			coreMsg=coreMsg.substring(0,coreMsg.length()-1);
                    		}
                		}
                    	
                    	payFlow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_FAL);
                    	payFlow.setRouteTransStat(CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                    	payFlow.setRouteDate(bkDate);
                    	payFlow.setRouteSeq(bkSeq);
                    	daoService.update(payFlow);
                    	//如果中金成功，核心失败，则该笔结算即为异常，由异常批量处理
                    	if(zjSuccess){
                    		object.setRemark("中金成功，核心失败，流水号为："+coreSeq+"  "+coreMsg);
                        	object.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
                        	daoService.update(object);
                    	}else{
                    		//不设置结算状态为失败的原因是 为了第二日继续结算
                			object.setRemark("结算失败，核心流水："+coreSeq+" "+coreMsg);
                        	daoService.update(object);
                    	}
                    }
                } else {
                    logger.info("-------------商户应收总金额[{}],应付总金额[{}]，轧差后不需要清算", allRevAmt, allPayAmt);
                }
            } catch (Exception e) {
            	LOGGER.error("-------------调用核心记账接口服务异常!!!");
            	LOGGER.error(e.toString());
            	String errorMsg=e.getMessage();
            	if(StringUtils.isNotBlank(errorMsg)){
            		if(errorMsg.length()>50){
            			errorMsg=errorMsg.substring(0,50);
            		}else{
            			errorMsg=errorMsg.substring(0,errorMsg.length()-1);
            		}
        		}
            	//如果中金成功，核心失败，则该笔结算即为异常，由异常批量处理
            	if(zjSuccess){
            		object.setRemark("中金成功，核心失败，流水号为："+coreSeq+"  "+errorMsg);
                	object.setStat(DataBaseConstants_BATCH.STL_STAT_EXCEPTION);
                	daoService.update(object);
            	}else{
            		//不设置结算状态为失败的原因是 为了第二日继续结算
        			object.setRemark("结算失败，核心流水："+coreSeq+" "+errorMsg);
                	daoService.update(object);
            	}
                return;
//                throw new BatchException("-------------调用核心记账接口服务异常!!!");
            }

            object.setStat(DataBaseConstants_BATCH.STL_STAT_COMPLETED);// 状态：0：登记 // 状态：1完成入账
            LOGGER.info("-------------商户扣款成功，更新记录状态");
            daoService.update(object);

            LOGGER.debug("-------------UnionPayCollectedStlStep update stlBook success");
        }

    }


    public IDipperHandler<Message> getCoreCliDipperHandler() {
        return coreCliDipperHandler;
    }


    public void setCoreCliDipperHandler(IDipperHandler<Message> coreCliDipperHandler) {
        this.coreCliDipperHandler = coreCliDipperHandler;
    }


    public PayRouteInfoPo getPayRouteInfoPo() {
        return payRouteInfoPo;
    }


    public void setPayRouteInfoPo(PayRouteInfoPo payRouteInfoPo) {
        this.payRouteInfoPo = payRouteInfoPo;
    }

    public ISequenceService getSequenceService() {
        return sequenceService;
    }


    public void setSequenceService(ISequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }
    
    //准备流水数据
    public  Map<String, Object> initPara(String seq, String amount, String orderNo, String addOrSub, PayRouteInfoPo routeInfo, PayRouteInfoPo zjRouteInfo,MerAcctInfoPo plateClearAcct,String routeCode, boolean isZJ, GnrParmPo parmPo){
    	
    	Map<String, Object> flowMap = new HashMap<String, Object>();
        flowMap.put("tranSeq", seq);
        flowMap.put("amount", amount);
        flowMap.put("routeCode", routeCode);
        flowMap.put("orderNo", orderNo);
        if(CommonBaseConstans_PAY.ACC_AMT_ADD.equals(addOrSub)){
        	if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
        		flowMap.put("payerAcctNo", parmPo.getParmValue());
        		flowMap.put("payerAcctType", DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
        		flowMap.put("payerAcctName", parmPo.getParmName());
        		flowMap.put("payeeAcctNo",plateClearAcct.getStlAcctNo());
        		flowMap.put("payeeAcctName",plateClearAcct.getStlAcctName());
        		flowMap.put("payeeAcctType",plateClearAcct.getStlAcctType());
        	}else{
        		flowMap.put("payerAcctNo", routeInfo.getClrAcctNo());
        		flowMap.put("payerAcctType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        		flowMap.put("payerAcctName", "红塔银行");
        		if(isZJ){
        			flowMap.put("payeeAcctNo", zjRouteInfo.getTransAcctNo());
            		flowMap.put("payeeAcctType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
            		flowMap.put("payeeAcctName", "中金支付");
        		}else{
        			flowMap.put("payeeAcctNo", plateClearAcct.getStlAcctNo());
        			flowMap.put("payeeAcctType", plateClearAcct.getStlAcctType());
        			flowMap.put("payeeAcctName", plateClearAcct.getStlAcctName());
        		}
        	}
        }else if(CommonBaseConstans_PAY.ACC_AMT_SUB.equals(addOrSub)){
        	if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
        		flowMap.put("payerAcctNo",plateClearAcct.getStlAcctNo());
        		flowMap.put("payerAcctName",plateClearAcct.getStlAcctName());
        		flowMap.put("payerAcctType",plateClearAcct.getStlAcctType());
        		flowMap.put("payeeAcctNo", parmPo.getParmValue());
        		flowMap.put("payeeAcctType", DataBaseConstants_PAY.ACCT_TYPE_THIRD_ACCT);
        		flowMap.put("payeeAcctName", parmPo.getParmName());
        	}else{
        		if(isZJ){
        			flowMap.put("payerAcctNo", zjRouteInfo.getTransAcctNo());
            		flowMap.put("payerAcctType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
            		flowMap.put("payerAcctName", "中金支付");
        		}else{
        			flowMap.put("payerAcctNo", plateClearAcct.getStlAcctNo());
        			flowMap.put("payerAcctType", plateClearAcct.getStlAcctType());
        			flowMap.put("payerAcctName", plateClearAcct.getStlAcctName());
        		}
        		
        		flowMap.put("payeeAcctNo", routeInfo.getClrAcctNo());
        		flowMap.put("payeeAcctType", DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        		flowMap.put("payeeAcctName", "红塔银行");
        	}
        }
		return flowMap;
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
