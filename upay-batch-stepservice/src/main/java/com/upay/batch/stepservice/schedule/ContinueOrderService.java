/**
 * 
 */
package com.upay.batch.stepservice.schedule;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Fault;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.gnr.GnrParmPo;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstans_ACC;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.commons.util.DateUtil;
import com.upay.commons.util.MoneyUtil;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 补单
 * @author shang
 * 2016年10月10日
 */
public class ContinueOrderService extends AbstractStepExecutor<Object, PayOrderListPo>{

    private static final Logger log=LoggerFactory.getLogger(ContinueOrderService.class);
//    private static ArrayList<String> array=new ArrayList<String>();
    private static PayRouteInfoPo routeCore=null;
    private SimpleDateFormat SIM_HMS=new SimpleDateFormat("HHmmss");
    private SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
	private static final String USRESBCORE="USR_ESB_CORE";
    @Resource
    IDaoService daoService;
    @Resource
    private ISequenceService seqService;
    @Resource(name = "coreCliDipperHandler")
    IDipperHandler<Message> core;
    
//    @Override
//    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
//        Object obj=batchParams.getParameter().get("orderNo");
//        Set<String> set=new HashSet<String>();
//        if(obj!=null&&obj instanceof Set){
//            set=(Set<String>) obj;
//        }
//        array.addAll(set);
//        log.info("需要 补单的订单为：[{}]",set.toString());
//        return set.size();
//    }

    @Override
    public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        List<PayOrderListPo> orderList=new ArrayList<PayOrderListPo>();
        List<String> orderNoList=new ArrayList<String>();

        Map<String,Object> mapSe=new HashMap<String,Object>();
//        mapSe.put("orderNoList", orderPage);
        mapSe.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
        mapSe.put("chkFlag", DataBaseConstants_PAY.T_CHK_STAT_PRE);
        mapSe.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        mapSe.put("transStatx", DataBaseConstants_PAY.T_PAY_TX_PROING);
        mapSe.put("transStaty", DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
        mapSe.put("orderStat1", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        mapSe.put("orderStat2", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
        mapSe.put("orderStat3", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
        mapSe.put("orderStat4", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        mapSe.put("orderStat5", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        orderList=daoService.selectList(PayOrderListPo.class.getName().concat(".getOrderListByOrderNo"), mapSe);
        if(orderList!=null){            
            for(PayOrderListPo pay:orderList){
                orderNoList.add(pay.getOrderNo());
            }
        }else{
            orderList=new ArrayList<PayOrderListPo>();
        }
        //提现补单获取---本行卡提现需要补单数据获取(有手续费)
        mapSe=new HashMap<String,Object>();
        mapSe.put("transType",CommonConstants_GNR.SYS_TRANS_TYPE_CAS);
        mapSe.put("orderStatA",DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        mapSe.put("orderStatB",DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        mapSe.put("orderStatC",DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        mapSe.put("orderStatRef",DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC);
        mapSe.put("compareFlag",">");
        mapSe.put("transStatA",DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        mapSe.put("transStatB", DataBaseConstants_PAY.T_PAY_TX_PROING);
        mapSe.put("transStatC", DataBaseConstants_PAY.T_PAY_TX_UNKNOWN);
        mapSe.put("countStart", 0);
        mapSe.put("countEnd", 2);
        mapSe.put("chkFlag", DataBaseConstants_PAY.T_CHK_STAT_PRE);
        mapSe.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
        List<PayOrderListPo> ordList=daoService.selectList(PayOrderListPo.class.getName().concat(".getRechargeTrans"), mapSe);
        if(ordList != null && ordList.size()>0){
            for(PayOrderListPo pay:ordList){
                if(!orderNoList.contains(pay.getOrderNo())){
                    orderNoList.add(pay.getOrderNo());
                    orderList.add(pay);
                }
            }
        }
        //提现补单获取---他行卡提现需要补单数据获取(有手续费)
        mapSe.put("countEnd", 3);
        mapSe.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY);
        ordList=daoService.selectList(PayOrderListPo.class.getName().concat(".getRechargeTrans"), mapSe);
        if(ordList != null && ordList.size()>0){
            for(PayOrderListPo pay:ordList){
                if(!orderNoList.contains(pay.getOrderNo())){
                    orderNoList.add(pay.getOrderNo());
                    orderList.add(pay);
                }
            }
        }
        //提现补单获取---他行卡提现需要补单数据获取(无手续费)
        mapSe.put("compareFlag","=");
        mapSe.put("countEnd", 2);
        ordList=daoService.selectList(PayOrderListPo.class.getName().concat(".getRechargeTrans"), mapSe);
        if(ordList != null && ordList.size()>0){
            for(PayOrderListPo pay:ordList){
                if(!orderNoList.contains(pay.getOrderNo())){
                    orderNoList.add(pay.getOrderNo());
                    orderList.add(pay);
                }
            }
        }
        //转账补单获取，余额转余额，并且有手续费，并且
        mapSe=new HashMap<String,Object>();
        mapSe.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_TRA);
        mapSe.put("orderStat", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        mapSe.put("transStat",DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        ordList=daoService.selectList(PayOrderListPo.class.getName().concat(".getFailOrderBy04"), mapSe);
        if(ordList != null && ordList.size()>0){
            for(PayOrderListPo pay:ordList){
                if(!orderNoList.contains(pay.getOrderNo())){
                    orderNoList.add(pay.getOrderNo());
                    orderList.add(pay);
                }
            }
        }
//        mapSe.put("transType",CommonConstants_GNR.SYS_TRANS_TYPE_TRA);
//        mapSe.put("compareFlag",">");
//        mapSe.put("countEnd", 2);
//        mapSe.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
//        ordList=daoService.selectList(PayOrderListPo.class.getName().concat(".getRechargeTrans"), mapSe);
//        if(ordList != null && ordList.size()>0){
//            for(PayOrderListPo pay:ordList){
//                if(!orderNoList.contains(pay.getOrderNo())){
//                    orderNoList.add(pay.getOrderNo());
//                    orderList.add(pay);
//                }
//            }
//        }
        return orderList;
    }


    @Override
    public void execute(BatchParams batchParams, int index, PayOrderListPo order, Object object)
            throws BatchException {
    	log.debug("补单        订单号："+order.getOrderNo()+"     订单状态："+order.getOrderStat());
        try{
            //记录流水
            Map<String,String> map=new HashMap<String,String>();
            
            PayFlowListPo pay=null;
            List<PayFlowListPo> payList=null;
            if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(order.getTransType())){
                payList=this.getRouteCodeList(order);
            }else{                
                pay=this.getRouteCode(order);
            }
            if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(order.getTransType())){
                PayOrderListPo oriOrder=new PayOrderListPo();
                oriOrder.setOrderNo(order.getOriOrderNo());
                oriOrder=daoService.selectOne(oriOrder);
                if(oriOrder==null){
                    log.error("订单[{}]补单错误！！！！（订单不存在）",order.getOrderNo());
                }
                if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(oriOrder.getTransType())){
                    payList=this.getRouteCodeList(order);
                }else{
                    pay=this.getRouteCode(order);
                }
            }
            String routeCode=null; 
            if(payList!=null && payList.size()>0){
                routeCode=DataBaseConstants_PAY.ROUTE_CODE_HOST;
                for(PayFlowListPo pa:payList){
                    if(!DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(pa.getRouteCode())){
                        routeCode=pa.getRouteCode();
                    }
                }
            }else{
                routeCode=pay.getRouteCode();
            }
            if(StringUtils.isNotBlank(routeCode)){
                if(routeCore==null){
                    routeCore=this.getRouteInfo(DataBaseConstants_PAY.ROUTE_CODE_HOST);
                }
                PayRouteInfoPo routeOther=this.getRouteInfo(routeCode);
                String transType=order.getTransType();
                if(StringUtils.isBlank(routeOther.getTransAcctNo())){
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0031, routeCode);
                }
                if(StringUtils.isBlank(routeOther.getTransAcctNo())){
                    ExInfo.throwDipperEx(AppCodeDict.BISGNR0031, DataBaseConstants_PAY.ROUTE_CODE_HOST);
                }
                boolean feeR=false;
                boolean transP=false;
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
                //支付
//                if(DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){                    
//                    log.error("订单[{}]不需要补单!!!",order.getOrderNo());
//                    return;
//                }else{                    
//                }
                if(CommonConstants_GNR.SYS_TRANS_TYPE_PAY.equals(transType)){
                    map.put("payerAccNo", routeOther.getTransAcctNo());
                    map.put("payeeAccNo", routeCore.getClrAcctNo());
                    transP=true;
                    //充值
                }else if(CommonConstants_GNR.SYS_TRANS_TYPE_REC.equals(transType)){
                	transP=true;
                    map.put("payerAccNo", routeOther.getTransAcctNo());
                    map.put("payeeAccNo", routeCore.getTransAcctNo());
                    //判断手续费是否需要补单
                    if(order.getFeeAmt()!=null&&order.getFeeAmt().compareTo(BigDecimal.ZERO)>0){
                        feeR=true;
                    }
                    
                    //提现
                }else if(CommonConstants_GNR.SYS_TRANS_TYPE_CAS.equals(transType)){
                    if(payList==null||payList.size() == 0){
                        log.error("订单[{}]提现补单错误！！！！(无流水)",order.getOrderNo());
                        return;
                    }
                    //判断提现到他行卡还是本行卡
                    if(!DataBaseConstants_PAY.ROUTE_CODE_HOST.equals(routeCode)){
                    	
                        if(payList.size()==2){
                            if(order.getFeeAmt()!=null&&order.getFeeAmt().compareTo(BigDecimal.ZERO)>0){
                                //只补手续费  
                                map.put("orderDes", "提现");
                                feeR=true;
                            }else{
                                log.error("订单[{}]他行卡提现补单错误！！！！(订单手续费未记录)",order.getOrderNo());
                                return;
                            }
                        }else if(payList.size()==1){
                        	map.put("orderDes", "提现");
                        	transP=true;
                            if(order.getFeeAmt()!=null&&order.getFeeAmt().compareTo(BigDecimal.ZERO)>0){
                            	feeR=true;
                            }else{
                                feeR=false;
                            }
                            map.put("payerAccNo", routeCore.getTransAcctNo());
                            map.put("payeeAccNo", routeOther.getTransAcctNo());
                        }else{
                            log.error("订单[{}]提现补单错误！！！！(流水记录错误)",order.getOrderNo());
                            return;
                        }
                    }else{
                        if(order.getFeeAmt()!=null&&order.getFeeAmt().compareTo(BigDecimal.ZERO)>0){
                            //本行卡提现只补手续费
                            feeR=true;
                        }else{
                            log.error("订单[{}]本行卡提现补单错误！！！！(订单手续费未记录)",order.getOrderNo());
                            return;
                        }
                    }
                    //退款
                }else if(CommonConstants_GNR.SYS_TRANS_TYPE_REF.equals(transType)){
                	PayOrderListPo payOrderListPo = new PayOrderListPo();
                	payOrderListPo.setOrderNo(order.getOriOrderNo());
                	payOrderListPo = daoService.selectOne(payOrderListPo);
                	//如果是隔日退款则不进行补单处理，有隔日退款批量处理
                	if(order.getOrderDate().compareTo(payOrderListPo.getOrderDate()) > 0){
                		return;
                	}
                	transP=true;
                    map.put("payerAccNo", routeCore.getClrAcctNo());
                    map.put("payeeAccNo", routeOther.getTransAcctNo());
                    //转账---根据现有业务，暂时无补单情况
                }else if(CommonConstants_GNR.SYS_TRANS_TYPE_TRA.equals(transType)){
                	PayFlowListPo pp=new PayFlowListPo();
                	pp.setOrderNo(order.getOrderNo());
                	pp.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                	List<PayFlowListPo> successList = daoService.selectList(pp);
                	if(successList==null){
                		 log.error("转账订单[{}]的流水不存在！！！",order.getOrderNo());
                         return;
                	}
                    //平台转账，余额转余额
                	if(DataBaseConstans_ACC.TRANS_CODE_TRANSFER.equals(order.getTransCode())){
                		transP=true;
                		PayOrderListPo orderT=new PayOrderListPo();
                		orderT.setRemark1(order.getOrderNo());
                		orderT=daoService.selectOne(orderT);
                		if(orderT==null){
                			log.error("转账订单[{}]的收款方订单不存在！！",order.getOrderNo());
                            return;
                		}
                		
                		if(successList.size()==1){
                        	pp=successList.get(0);
                        	// 转账判断是手续费 还是平台流水
                        	if(DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(pp.getPayerAcctType()) 
                        			&& DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(pp.getPayeeAcctType())
                        			&& null!=order.getFeeAmt()){
                            	map.put("payerAccNo", routeCore.getTransAcctNo());
                            	map.put("payeeAccNo", routeCore.getClrAcctNo());
                                map.put("orderDes", "转账手续费补单");
                            	transP=false;
                            	feeR=true;
                            }
                		}else{
                			log.error("转账订单[{}]不需要补单",order.getOrderNo());
                            return;
                		}
                	//烟草二清转账
                	}else if(DataBaseConstans_ACC.TRANS_CODE_SMOKE_TRANSFER.equals(order.getTransCode())){
                		if(successList.size() == 1 && DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(successList.get(0).getRouteCode())){
                        	//烟草二级转账核心补单
                        	String merNo = order.getMerNo();
                        	MerAcctInfoPo merAcct=new MerAcctInfoPo();
                        	merAcct.setMerNo(merNo);
                        	merAcct=daoService.selectOne(merAcct);
                        	if(null!=merAcct){
                        		String merStlNo=merAcct.getStlAcctNo();
                        		map.put("payerAccNo", merStlNo);
                            	map.put("payeeAccNo", routeOther.getTransAcctNo());
                                map.put("orderDes", "烟草二级清算转账核心补单");
                            	transP=true;
                            	feeR=false;
                        	}
                        }else{
                        	log.error("订单[{}]不需要补单!!!",order.getOrderNo());
                            return;
                        }
                	}
                	//代收、代付
                }else if(CommonConstants_GNR.SYS_TRANS_TYPE_COLLECTION.equals(transType)||CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT.equals(transType)){
                	Map<String,Object> mapParam=new HashMap<String,Object>();
                	mapParam.put("orderNo", order.getOrderNo());
                	mapParam.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
                    List<PayFlowListPo> list = daoService.selectList(PayFlowListPo.class.getName().concat(".findFlowBySeqNoDesc"), mapParam);
	                    map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ANYTHING);
	                	map.put("payerAccNo", order.getPayerAcctNo());
	                	map.put("payerAcctType", order.getPayerAcctType());
	                	
	                    map.put("payeeAccNo", order.getPayeeAcctNo());
	                    String payeeAcctType = order.getPayeeAcctType();
	                    map.put("payeeAcctType", payeeAcctType);
	                    
	                    //如果是本行网银代扣时，核心记账接口08001接口添加了四个参数固需要添加下面代码    或者收款方为二类户的情况下
	                    if(CommonConstants_GNR.CHNL_ID_ONLINE_BANK.equals(order.getChnlId())
	                    		||DataBaseConstants_PAY.TWO_TYPES_OF_ACCOUNT.equals(payeeAcctType)){
	                    	// 目前网银用的是中金通道  所以只查询
	                    	PayFlowListPo flowPo = daoService.selectOne(PayFlowListPo.class.getName().concat(".findByOrderNoAndRouteCode"), mapParam);
	                    	map.put("thirdFlag", DataBaseConstants_PAY.THIRD_FLAG);
	                    	map.put("thirdAccount", flowPo.getPayerAcctNo());//第一条流水的付款方
	                        map.put("accountName", flowPo.getPayerName());//第一条流水的付款方姓名
	                        map.put("memoCode",DataBaseConstants_PAY.MEMO_CODE_TRANSFER);//备注是充值
	                    }
	                    transP=true;
                }else{
                    log.error("订单[{}]不需要补单!!!",order.getOrderNo());
                    return;
                }
//                map.put("routeCode", routeCode);
                if(transP){
                    //记录流水
                    PayFlowListPo payNew=this.addPayFlowList(map, order,order.getTransAmt());
                    map.put("transSubSeq", payNew.getTransSubSeq());
                    String transAmt = MoneyUtil.transferY2F(order.getTransAmt().multiply(new BigDecimal(100)), 0).toString();
                    map.put("transAmt", transAmt);
                	String flag=getAtParm(USRESBCORE);
    				Map<String,String> bodys=new HashMap<String,String>();
    				if(flag.equals("Y")){
    		               //访问核心记账
    				  bodys = this.coreBank801001(map);
    				}else{
    		               //访问核心记账
                      bodys = this.coreBank08001(map);
    				}
     
                    //修改流水状态
                    if(bodys!=null&&bodys.size()>0){
                        Map<String,Object> mapPay=new HashMap<String,Object>();
                        mapPay.put("whereStat", payNew.getTransStat());
                        mapPay.put("transSubSeq", payNew.getTransSubSeq());
                        if(CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(bodys.get("respCode"))){                        
                            mapPay.put("updateStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                            mapPay.put("routeSeq", bodys.get("bkSerialNo")==null?null:bodys.get("bkSerialNo").toString());
                            mapPay.put("routeTransStat", CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                            mapPay.put("routeDate",  bodys.get("bizDate")==null?null:SIM_YMD.parseObject(bodys.get("bizDate").toString()));
                            mapPay.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
                            
                            //更新订单的清算状态为待清算  如果订单原来就是清算成功，则不需要更新清算状态
                            if(!DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC.equals(order.getClearFlag())
                            		&&!DataBaseConstants_BATCH.T_CLEAR_FLAG_TWOSUC.equals(order.getClearFlag())){
                            	//
                                PayOrderListPo orderParam=new PayOrderListPo();
                                orderParam.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
                                PayOrderListPo orderWhere=new PayOrderListPo();
                                orderWhere.setOrderNo(order.getOrderNo());
                                orderWhere.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
                                int j=daoService.update(orderParam, orderWhere);
                                if(j!=1){
                                    log.error("订单[{}]记录的清算状态错误！！！！",order.getOrderNo());
                                }
                            }
                            
                        }else{
                            mapPay.put("updateStat", DataBaseConstants_PAY.T_PAY_TX_FAL);
                            mapPay.put("routeTransStat", CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        }
                        mapPay.put("beforeStat", DataBaseConstants_PAY.T_PAY_TX_BEGIN);
                        daoService.update(PayFlowListPo.class.getName().concat(".updateRouteInfoToPayFlow"), mapPay);
                    }else{
                    	log.error("订单[{}]记录的  补单调用核心接口失败！！！！",order.getOrderNo());
                    }
                }
                if(feeR){
                	map.put("payerAccNo", routeCore.getTransAcctNo());
                    map.put("payeeAccNo", routeCore.getClrAcctNo());
                    String transAmt = MoneyUtil.transferY2F(order.getFeeAmt().multiply(new BigDecimal(100)), 0).toString();
                	map.put("transAmt", transAmt);
                	if(null==map.get("orderDes")){
                		map.put("orderDes", "手续费");
                	}
                    PayFlowListPo payFeeNew=this.addPayFlowList(map, order,order.getFeeAmt());
                    map.put("transSubSeq", payFeeNew.getTransSubSeq());
                    //访问核心记账
                    map.put("setAccount", routeCore.getClrAcctNo());
                    Map<String,String> feeBodys=this.coreBank08001(map);
                    if(feeBodys!=null&&feeBodys.size()>0){
                        Map<String,Object> mapPay=new HashMap<String,Object>();
                        mapPay.put("whereStat", payFeeNew.getTransStat());
                        mapPay.put("transSubSeq", payFeeNew.getTransSubSeq());
                        if(CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(feeBodys.get("respCode"))){                        
                            mapPay.put("updateStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
                            mapPay.put("routeSeq", feeBodys.get("bkSerialNo")==null?null:feeBodys.get("bkSerialNo").toString());
                            mapPay.put("routeTransStat", CommonConstants_GNR.OUT_PAY_STAT_SUCCESS);
                            mapPay.put("routeDate",  feeBodys.get("bizDate")==null?null:SIM_YMD.parseObject(feeBodys.get("bizDate").toString()));
                            mapPay.put("clearFlag", DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC);
                        }else{
                            mapPay.put("updateStat", DataBaseConstants_PAY.T_PAY_TX_FAL);
                            mapPay.put("routeTransStat", CommonConstants_GNR.OUT_PAY_STAT_FAIL);
                        }
                        mapPay.put("beforeStat", DataBaseConstants_PAY.T_PAY_TX_BEGIN);
                        
                        
                        daoService.update(PayFlowListPo.class.getName().concat(".updateRouteInfoToPayFlow"), mapPay);
                        
                        //更新订单的清算状态为待清算  如果订单原来就是清算成功，则不需要更新清算状态
                        if(!DataBaseConstants_BATCH.T_CLEAR_FLAG_SUC.equals(order.getClearFlag())
                        		&&!DataBaseConstants_BATCH.T_CLEAR_FLAG_TWOSUC.equals(order.getClearFlag())){
	                        PayOrderListPo setOrderList=new PayOrderListPo();
	                        setOrderList.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_READY);
	                        setOrderList.setLastUpdateTime(new Date());
	                        
	                        PayOrderListPo whereOrderList=new PayOrderListPo();
	                        whereOrderList.setOrderNo(order.getOrderNo());;
	                        
	                        daoService.update(setOrderList,whereOrderList);
                        }
                    }
                }
            }else{
                log.error("交易流水的通道代码为空！");
                return;
            }
        }catch(Exception ex){
            log.error(ex.getMessage());
            return;
        }
    }
    /**
     * 获取资金通道信息
     * @param routeCode
     * @return
     */
    public PayRouteInfoPo getRouteInfo(String routeCode){
        PayRouteInfoPo route=new PayRouteInfoPo();
        route.setRouteCode(routeCode);
        route=daoService.selectOne(route);
        return route;
    }
    /**
     * 获取资交易流水
     * @param order
     * @return
     */
    public PayFlowListPo getRouteCode(PayOrderListPo order){
        PayFlowListPo pay=new PayFlowListPo();
        pay.setOrderNo(order.getOrderNo());
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        List<PayFlowListPo> payList=daoService.selectList(pay);
        if(payList!=null&&payList.size()==1){
            pay=payList.get(0);
            return pay;
        }else{
            log.error("补单错误！！！！");
        }
        return null;
    }
    /**
     * 获取资交易流水集合
     * @param order
     * @return
     */
    public List<PayFlowListPo> getRouteCodeList(PayOrderListPo order){
        PayFlowListPo pay=new PayFlowListPo();
        pay.setOrderNo(order.getOrderNo());
        pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        List<PayFlowListPo> payList=daoService.selectList(pay);
        if(payList!=null&&payList.size()>0){
            return payList;
        }
        return null;
    }

    /**
     * 记录交易流水
     * @param map
     * @param order
     */
    public PayFlowListPo addPayFlowList(Map<String,String> map,PayOrderListPo order,BigDecimal transAmt){
        Date now = new Date();
        Date date = SysInfoContext.getSysDate();
        String paySeq = seqService.generatePayFlowSeq();
        PayFlowListPo pay = new PayFlowListPo();
        
        HashMap<String, Object> parmMap=new HashMap<>();
        parmMap.put("orderNo", order.getOrderNo());
        Integer maxSeqNo = (Integer)daoService.selectOne(PayFlowListPo.class.getName()+".findMaxSeqNo",parmMap);
        if(null==maxSeqNo||0==maxSeqNo){
        	maxSeqNo=1;
        }else{
        	maxSeqNo=maxSeqNo+1;
        }
        pay.setSeqNo(maxSeqNo);
        pay.setOrderNo(order.getOrderNo());
        pay.setSrFlag(DataBaseConstants_PAY.SR_FLAG_NOSTRO);
        pay.setOrderDes(map.get("orderDes"));
        pay.setSecMerNo(order.getSecMerNo());
        pay.setSysDate(date);
        pay.setTransSubSeq(paySeq);
        pay.setPayerAcctNo(map.get("payerAccNo"));
        pay.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        pay.setMerNo(order.getMerNo());
//        pay.setPayUserId(map.get("payerUserId"));
//        pay.setPayerOrgName(map.get("payerOrgName"));
//        pay.setPayerBankNo(map.get("payerBankNo"));
//        pay.setPayerBankName(map.get("payerBankName"));
//        pay.setPayerName(map.get("payerName"));
        pay.setPayeeAcctNo(map.get("payeeAccNo"));
        pay.setRouteDate( DateUtil.getYMD(new Date()).getTime());
        if(CommonConstants_GNR.SYS_TRANS_TYPE_COLLECTION.equals(order.getTransType())){
        	pay.setPayerAcctType(map.get("payerAcctType"));
        	pay.setPayeeAcctType(map.get("payeeAcctType"));
        }else if(CommonConstants_GNR.SYS_TRANS_TYPE_PAYMENT.equals(order.getTransType())){
        	pay.setPayerAcctType(map.get("payerAcctType"));
        	pay.setPayeeAcctType(map.get("payeeAcctType"));
        }else{
        	pay.setPayerAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        	pay.setPayeeAcctType(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT);
        }
//        pay.setPayeeName(map.get("payeeName"));
//        pay.setPayeeOrgName(map.get("payeeOrgName"));
//        pay.setPayeeBankNo(map.get("payeeBankNo"));
//        pay.setPayeeBankName(map.get("payeeBankName"));
        pay.setClearFlag(DataBaseConstants_BATCH.T_CLEAR_FLAG_NO);
        pay.setCcy(DataBaseConstants_PAY.T_CCY_CNY);
        pay.setTransAmt(transAmt);
//        pay.setFeeAmt(order.getFeeAmt());
        pay.setTransTime(now);
        if(StringUtils.isNotBlank(map.get("transStat"))){
            pay.setTransStat(map.get("transStat"));
        }else{            
            pay.setTransStat(DataBaseConstants_PAY.T_PAY_TX_BEGIN);
        }
        pay.setChkFlag(DataBaseConstants_BATCH.T_CHK_FLAG_NO);
        pay.setLastUpdateTime(now);
        daoService.insert(pay);
        return pay;
    }
    /**
     * 更新流水状态
     * @param updateStat
     * @param whereStat
     * @param subSeq
     * @throws ParseException 
     */
    public void updatePayFlowListStat(Map<String,Object> map) throws ParseException{
        PayFlowListPo payParam=new PayFlowListPo();
//        payParam.setTransStat(map.get("updateStat"));
//        PayFlowListPo payWhere=new PayFlowListPo();
//        payWhere.setTransStat(map.get("whereStat"));
//        payWhere.setTransSubSeq(map.get("transSubSeq"));
//        payWhere.setRouteSeq(map.get("routeSeq"));
//        payWhere.setRouteTransStat(map.get("routeTransStat"));
//        payWhere.setRouteDate(map.get("routeDate")==null?null:CommonBaseConstants_USR.SIM_YMD.parse(map.get("routeDate")));
//        Map<String,Object> mapUp=new HashMap<String,Object>();
//        map.put("", );
//        daoService.update(payParam, payWhere);
    }
    /**
     * 
     * @return
     * @throws Exception 
     */
    public Map<String,String> coreBank08001(Map<String,String> map){
        Date now=new Date();
        Map<String,String> result=null;
        Date date=SysInfoContext.getSysDate()==null?now:SysInfoContext.getSysDate();
        Map<String,Object> bodyMap= new HashMap<String,Object>();
        bodyMap.put("tranCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);//交易代码
        bodyMap.put("machineDate", SIM_YMD.format(date));//交易日期
        bodyMap.put("machineTime", SIM_HMS.format(now));//交易时间
        bodyMap.put("bizDate", SIM_YMD.format(date));//业务日期
        bodyMap.put("bizSerialNo", map.get("transSubSeq"));//业务流水号
        bodyMap.put("channelId", "74");//渠道号
        bodyMap.put("bankCardNo", map.get("payerAccNo"));//银行卡号
        bodyMap.put("currency", DataBaseConstants_PAY.T_CORE_CCY_CNY);//货币代码
        bodyMap.put("trantype", map.get("trantype"));//交易类型  CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI
        bodyMap.put("amount", map.get("transAmt"));//交易金额
        bodyMap.put("charge", "");//手续费
        bodyMap.put("setAccount", map.get("payeeAccNo"));//内部账号
        bodyMap.put("cvv2", "");//信用卡cvv2
        bodyMap.put("validate", "");//信用卡有效日期
        
        //记账接口新增下面的字段
        bodyMap.put("thirdFlag", map.get("thirdFlag"));
        bodyMap.put("thirdAccount", map.get("thirdAccount"));//第一条流水的付款方
        bodyMap.put("accountName", map.get("accountName"));//第一条流水的付款方姓名
        bodyMap.put("memoCode", map.get("memoCode"));//备注是充值
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        new HashMap<String,Object>(), bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        try {
            message = core.handle(message);
            Fault fault = message.getFault();
            if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getCode())){
            	result=(Map<String, String>) message.getTarget().getBodys();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    
    
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    public Map<String,String> coreBank801001(Map<String,String> map){
        Date now=new Date();
        Map<String,String> result=null;
        Date date=SysInfoContext.getSysDate()==null?now:SysInfoContext.getSysDate();
        Map<String,Object> bodyMap= new HashMap<String,Object>();
        bodyMap.put("tranCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_801001);//交易代码
        bodyMap.put("svcCd", "30110001");
        bodyMap.put("svcScn", "15");
        bodyMap.put("machineDate", SIM_YMD.format(date));//交易日期
        bodyMap.put("machineTime", SIM_HMS.format(now));//交易时间
        bodyMap.put("bizDate", SIM_YMD.format(date));//业务日期
        bodyMap.put("bizSerialNo", map.get("transSubSeq"));//业务流水号
        bodyMap.put("channelId", "74");//渠道号
        bodyMap.put("fileFlg", "0");
        
        bodyMap.put("bankCardNo", map.get("payerAccNo"));//银行卡号
        bodyMap.put("ccy", "01");//货币代码
        bodyMap.put("acctNoDataSrcFlg", "2"); 
        bodyMap.put("amount", map.get("transAmt"));//交易金额
        bodyMap.put("setAccount", map.get("payeeAccNo"));//内部账号
        bodyMap.put("AcctNoSrc", "2"); 
        bodyMap.put("wthdwMd", "0"); 
       
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        new HashMap<String,Object>(), bodyMap), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), bodyMap), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        try {
            message = core.handle(message);
            Fault fault = message.getFault();
            if(CommonConstants_GNR.RSP_CODE_SUCCESS.equals(fault.getCode())){
            	result=(Map<String, String>) message.getTarget().getBodys();
            	result.put("bizDate", SIM_YMD.format(date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    
    
    private String getAtParm(String payRouteMethod) {
		GnrParmPo gnrParmPoPo = new GnrParmPo();
		if (StringUtils.isNotBlank(payRouteMethod)) {
			gnrParmPoPo.setParmId(payRouteMethod);
		} else {
			ExInfo.throwDipperEx(
					AppCodeDict.BISPAY0001,
					"请在pay_param中初始化 id为initPayMethod  的 payRouteMethod字段 值为 'WECHAT_USE_AT_ROUTE（微信是否使用银联AT通道）',  或者 'ALIPAY_USE_AT_ROUTE（支付宝是否使用银联AT通道）',    ");
		}
		gnrParmPoPo = daoService.selectOne(gnrParmPoPo);
		return gnrParmPoPo.getParmValue();
	}
    
}
