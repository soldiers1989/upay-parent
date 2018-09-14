/**
 * 
 */
package com.upay.batch.stepservice.schedule;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.batch.dto.BatchParams;
import com.pactera.dipper.batch.exception.BatchException;
import com.pactera.dipper.batch.service.AbstractStepExecutor;
import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.IDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.core.bean.Store;
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.core.utils.Constants;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.ISequenceService;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.acc.AccVbookPo;
import com.upay.dao.po.mer.MerAcctInfoPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * @author shang
 * 2016年9月18日
 */
public class SynchRefundMerStlAmt extends AbstractStepExecutor<Object, PayOrderListPo> {
    private static final Logger log=LoggerFactory.getLogger(SynchRefundMerStlAmt.class);
    private SimpleDateFormat SIM_YMD=new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat SIM_HMS=new SimpleDateFormat("HHmmss");

    @Resource
    IDaoService daoService;
    @Resource(name = "coreCliDipperHandler")
    IDipperHandler<Message> core;
    @Resource
    private ISequenceService seqService;
    /**
     * 
     */
    @Override
    public void execute(BatchParams batchParams, int index, PayOrderListPo order, Object object)
            throws BatchException {
        PayFlowListPo flow=new PayFlowListPo();
        flow.setOrderNo(order.getOrderNo());
        flow.setTransStat(DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        List<PayFlowListPo> flowList=daoService.selectList(flow);
        if(flowList == null || flowList.size()!=1){
            ExInfo.throwDipperEx("请检查订单[{}]，需要回退商户金额，但是存在多比成功退款流水", order.getOrderNo());
        }
        MerAcctInfoPo merAcc=new MerAcctInfoPo();
        merAcc.setMerNo(order.getMerNo());
        merAcc=daoService.selectOne(merAcc);
        if(merAcc==null){
            ExInfo.throwDipperEx("商户[{}]结算账户不存在", order.getMerNo());
        }
        PayRouteInfoPo route=new PayRouteInfoPo();
        route.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
        route=daoService.selectOne(route);
        if(route==null){
            ExInfo.throwDipperEx("资金通道[{}]信息不存在", DataBaseConstants_PAY.ROUTE_CODE_HOST);
        }
        boolean addMer=merAcc.getStlAcctNo().equals(flowList.get(0).getPayerAcctNo())&&route.getClrAcctNo().equals(flowList.get(0).getPayeeAcctNo());
        boolean subMer=merAcc.getStlAcctNo().equals(flowList.get(0).getPayeeAcctNo())&&route.getClrAcctNo().equals(flowList.get(0).getPayerAcctNo());
        if((!addMer)&&(!subMer)){
            ExInfo.throwDipperEx("订单[{}]不需要回退商户结算账户余额", order.getOrderNo());
        }
        BigDecimal big=new BigDecimal(10);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("amount", flowList.get(0).getTransAmt().multiply(big).toString());
        boolean isAcc=false;
        String addOrSub=null;
        int num=0;
        if(addMer){
            addOrSub= CommonBaseConstans_PAY.ACC_AMT_ADD;
            if(DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(merAcc.getStlAcctType())){
                isAcc=true;
                map.put("bankCardNo",route.getClrAcctNo());
                map.put("setAccount",route.getTransAcctNo());
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
            }else if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(merAcc.getStlAcctType())){
                map.put("bankCardNo",merAcc.getStlAcctNo());
                map.put("setAccount",route.getClrAcctNo());
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_CASH);
            }else if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(merAcc.getStlAcctType())){
                map.put("bankCardNo",route.getClrAcctNo());
                map.put("setAccount",merAcc.getStlAcctNo());
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
            }else{
                ExInfo.throwDipperEx("商户[{}]结算账户，账户类型错误", order.getMerNo());
            }
        }else{
            addOrSub= CommonBaseConstans_PAY.ACC_AMT_SUB;
            if(DataBaseConstants_PAY.ACCT_TYPE_EPAY.equals(merAcc.getStlAcctType())){
                isAcc=true;
                map.put("bankCardNo",route.getTransAcctNo());
                map.put("setAccount",route.getClrAcctNo());
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
            }else if(DataBaseConstants_PAY.ACCT_TYPE_DEBIT_CARD.equals(merAcc.getStlAcctType())){
                map.put("bankCardNo",merAcc.getStlAcctNo());
                map.put("setAccount",route.getClrAcctNo());
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_PAY);
            }else if(DataBaseConstants_PAY.ACCT_TYPE_INTERNAL_ACCT.equals(merAcc.getStlAcctType())){
                map.put("bankCardNo",merAcc.getStlAcctNo());
                map.put("setAccount",route.getClrAcctNo());
                map.put("trantype", CommonConstants_GNR.CORE_BANK_PAY_TYPE_ITI);
            }else{
                ExInfo.throwDipperEx("商户[{}]结算账户，账户类型错误", order.getMerNo());
            }
        }
        String seq=seqService.generatePayFlowSeq();
        map.put("flowSeq", seq);
        if(isAcc){
            Map<String,Object> updateAcc=new HashMap<String, Object>();
            updateAcc.put("updateAmt", flowList.get(0).getTransAmt().toString());
            updateAcc.put("addOrSub", addOrSub);
            updateAcc.put("accNo", merAcc.getStlAcctNo());
            num=daoService.update(AccVbookPo.class.getName().concat(".updateAccountAmtSub"),updateAcc);
        }
        try {
            Map<String,Object> mm=this.queryCoreBank(map);
            log.info("回退商户结算账户金额核心返回报文:[{}]",mm.toString());
            if(mm!=null&&CommonConstants_GNR.T_PAY_BANK_HT_SUCCESS.equals(mm.get("respCode"))){
                PayFlowListPo paramFlow=new PayFlowListPo();
                paramFlow.setRemark1(seq);
                PayFlowListPo whereFlow=new PayFlowListPo();
                whereFlow.setTransSubSeq(flowList.get(0).getTransSubSeq());
                daoService.update(paramFlow, whereFlow);
            }else{
                log.error("商户[{}]结算账户，核心接口回退失败！！！",merAcc.getMerPlatNo());
                if(isAcc&&num==1){
                    if(addMer){
                        addOrSub= CommonBaseConstans_PAY.ACC_AMT_SUB;
                    }else{
                        addOrSub= CommonBaseConstans_PAY.ACC_AMT_ADD;
                    }
                    Map<String,Object> updateAcc=new HashMap<String, Object>();
                    updateAcc.put("updateAmt", flowList.get(0).getTransAmt().toString());
                    updateAcc.put("addOrSub", addOrSub);
                    updateAcc.put("accNo", merAcc.getStlAcctNo());
                    int n=daoService.update(AccVbookPo.class.getName().concat(".updateAccountAmtSub"),updateAcc);
                    if(n!=1){
                        log.error("商户[{}]结算账户，回退失败，更新余额回退失败",merAcc.getMerPlatNo());
                    }
                }
            }
        } catch (Exception e) {
            if(isAcc&&num==1){
                if(addMer){
                    addOrSub= CommonBaseConstans_PAY.ACC_AMT_SUB;
                }else{
                    addOrSub= CommonBaseConstans_PAY.ACC_AMT_ADD;
                }
                Map<String,Object> updateAcc=new HashMap<String, Object>();
                updateAcc.put("updateAmt", flowList.get(0).getTransAmt().toString());
                updateAcc.put("addOrSub", addOrSub);
                updateAcc.put("accNo", merAcc.getStlAcctNo());
                num=daoService.update(AccVbookPo.class.getName().concat(".updateAccountAmtSub"),updateAcc);
            }
            log.error(e.toString());
            throw new BatchException(e.toString());
        }
    }

    /**
     * 获取需要回退商户结算账户余额的订单
     */
    @Override
    public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        List<String> listPay=new ArrayList<String>();
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        List<String> listRef=new ArrayList<String>();
        listRef.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING);
        listRef.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderStatListPay", listPay);
        map.put("orderStatListRefund", listRef);
        map.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        map.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
        map.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        List<PayOrderListPo> orderList=daoService.selectList(PayOrderListPo.class.getName().concat(".selectRefundMerStlData"),map);
        return orderList;
    }

    /**
     * 获取需要回退商户结算账户余额的订单数
     */
    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
        List<String> listPay=new ArrayList<String>();
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y);
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP);
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFPRO);
        listPay.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFALL);
        List<String> listRef=new ArrayList<String>();
        listRef.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFING);
        listRef.add(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFFAIL);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderStatListPay", listPay);
        map.put("orderStatListRefund", listRef);
        map.put("transStat", DataBaseConstants_PAY.T_PAY_TX_SUCCESS);
        map.put("routeCode", DataBaseConstants_PAY.ROUTE_CODE_HOST);
        map.put("transType", CommonConstants_GNR.SYS_TRANS_TYPE_PAY);
        int num=daoService.selectOne(PayOrderListPo.class.getName().concat(".selectRefundMerStlNum"),map);
        return num;
    }
    /**
     * 访问核心
     * @param pay
     * @throws Exception
     */
    public Map<String,Object> queryCoreBank(Map<String,Object> pay) throws Exception{
        Date now=new Date();
        Date date=SysInfoContext.getSysDate()==null?now:SysInfoContext.getSysDate();
        Map<String,Object> bodyMap= new HashMap<String,Object>();
        bodyMap.put("tranCode", CommonConstants_GNR.CORE_BANK_PAY_CODE_08001);//交易代码
        bodyMap.put("channelId", "74");
        bodyMap.put("machineDate", SIM_YMD.format(date));//交易日期
        bodyMap.put("machineTime", SIM_HMS.format(now));//交易时间
        bodyMap.put("bizDate", SIM_YMD.format(date));//业务日期
        bodyMap.put("bizSerialNo", pay.get("flowSeq"));//业务流水号
        bodyMap.put("bankCardNo",pay.get("bankCardNo"));
        bodyMap.put("currency", DataBaseConstants_PAY.T_CORE_CCY_CNY);
        bodyMap.put("charge", "");
        bodyMap.put("setAccount", pay.get("setAccount"));
        bodyMap.put("orgBizDate", "");
        bodyMap.put("orgBizSerialNo", "");
        bodyMap.put("cvv2", "");
        bodyMap.put("validate", "");
        bodyMap.put("trantype", pay.get("trantype"));
        bodyMap.put("amount", pay.get("amount"));
        Message message =this.getMessage(bodyMap);
        message = core.handle(message);
        Map<String,Object> result=null;
        if(message.getTarget().getBodys()!=null&&message.getTarget().getBodys() instanceof Map){            
            result=(Map<String, Object>) message.getTarget().getBodys();
        }
//        if(result!=null&&result.get("bkSerialNo")!=null){
//            return result.get("bkSerialNo").toString();
//        }
        return result;
    }
    public Message getMessage(Map<String,Object> body){
        Message message =
                MessageFactory.create(IdGenerateFactory.generateId(), MessageFactory.createSimpleMessage(
                        new HashMap<String,Object>(), body), com.pactera.dipper.core.factory.MessageFactory.createSimpleMessage(
                    new HashMap<String, Object>(), body), FaultFactory.create(
                    Constants.ResponseCode.SUCCESS, "success"), new LinkedList<Store>());
        return message;
    }
}
