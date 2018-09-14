/**
 * 
 */
package com.upay.batch.stepservice.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.pactera.dipper.core.factory.FaultFactory;
import com.pactera.dipper.core.factory.IdGenerateFactory;
import com.pactera.dipper.core.factory.MessageFactory;
import com.pactera.dipper.dao.service.IDaoService;
import com.pactera.dipper.po.Order;
import com.upay.commons.constants.CommonConstants_GNR;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.SysInfoContext;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 * @author shang
 * 2016年9月18日
 */
public class CheckOrderTimeOut extends AbstractStepExecutor<Object, PayOrderListPo> {
    private static final Logger log=LoggerFactory.getLogger(CheckOrderTimeOut.class);

    @Resource
    IDaoService daoService;
    /**
     * 把超时的订单状态更改为超时
     */
    @Override
    public void execute(BatchParams batchParams, int index, PayOrderListPo order, Object object)
            throws BatchException {
        Date now=new Date();
        Date createTime=order.getOrderTime();
        boolean checkMerDate=(order.getOuterOrderEndDate()==null?false:order.getOuterOrderEndDate().compareTo(now)>=0?false:true)||(((int) ((now.getTime() - createTime.getTime()) / 1000L / 60L) - order.getOrderLmtTime()) >= 0);
        if (checkMerDate) {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("orderNo", order.getOrderNo());
            map.put("orderStatTo", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_TOC);
            map.put("orderStatWhere", DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);
            int num=daoService.update(PayOrderListPo.class.getName().concat(".updateOrderStatToTimeOut"), map);
            if(num>0){                
                //微信公众号支付，微信二维码支付 TODO
                if(CommonConstants_GNR.TRANS_TYPE_WEIXIN_PAY.equals(order.getTransCode())){
                    PayFlowListPo pay=new PayFlowListPo();
                    pay.setOrderNo(order.getOrderNo());
                    pay.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
                    List<PayFlowListPo> payList=daoService.selectList(pay);
                    if(payList!=null&&payList.size()>0){
                        for(PayFlowListPo p:payList){                            
                            if(!DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(p.getTransStat())){ 
                                this.closeWeiXinOrder(p.getTransSubSeq(), order.getMerNo());
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 分页获取数据
     */
    @Override
    public List<PayOrderListPo> getDataList(BatchParams batchParams, int offset, int pageSize, Object object)
            throws BatchException {
        PayOrderListPo order=new PayOrderListPo();
        order.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);
        order.addOrder(Order.asc("orderTime"));
        return daoService.selectList(PayOrderListPo.class.getName().concat(".getOrderListByStat"), order, offset, pageSize);
    }

    /**
     * 获取所有待支付订单
     */
    @Override
    public int getTotalResult(BatchParams batchParams, Object object) throws BatchException {
        PayOrderListPo order=new PayOrderListPo();
        order.setOrderStat(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_N);
        int count = daoService.count(PayOrderListPo.class.getName().concat(".getOrderByStat"), order);
        log.debug(AppCodeDict.BISPAY0032,count);
        return count;
    }
    
    
    /**
     * 微信关闭订单
     */
    public void closeWeiXinOrder(String payFlowNo,String merNo){
        String channel="weixincli";
        PayRouteInfoPo route=new PayRouteInfoPo();
        route.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_CNAPSBEPS);
        route=daoService.selectOne(route);
        if(route==null){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0028);
        }
        Map<String,Object> body=new HashMap<String,Object>();
        
        Message m =
                MessageFactory.create(IdGenerateFactory.generateId(), channel, "XML", "UTF-8",
                    MessageFactory.createSimpleMessage(new HashMap<String, Object>(),
                        new HashMap<String, Object>()), FaultFactory.create("000000", ""));
        
        body.put("tranCode", "CLOSEORDER");
        body.put("appId", route.getAppId());
        body.put("mchId", route.getOrgNo());
        body.put("subMchId", merNo); 
        body.put("outTradeNo",payFlowNo);
    }
    
}
