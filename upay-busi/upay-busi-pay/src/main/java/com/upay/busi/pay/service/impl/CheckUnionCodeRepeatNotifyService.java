/**
 * 
 */
package com.upay.busi.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckUnionRepeatNotifyDto;
import com.upay.busi.pay.service.dto.GetClrtypeByRouteCodeDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayRouteInfoPo;

/**
 *银联二维码重复通知检查（银联单笔交易可能会发起多次通知）
 * 
 */
public class CheckUnionCodeRepeatNotifyService extends AbstractDipperHandler<CheckUnionRepeatNotifyDto> {

    
    @Resource
    IDaoService daoService;
    
    @Override
    public CheckUnionRepeatNotifyDto execute(CheckUnionRepeatNotifyDto dto, Message message) throws Exception {
    	
    	String routeCode = dto.getRouteCode();
    	if(StringUtils.isBlank(routeCode)){
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "资金通道");
        }
    	PayFlowListPo payFlowList = new PayFlowListPo();
    	
    	if(DataBaseConstants_PAY.ROUTE_CODE_CNAPSHVPS.equals(routeCode)){
    		//银联返回的平台流水号
    		String orderId = dto.getVoucherNum();
    		if(StringUtils.isBlank(orderId)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "银联返回流水号");
            }
    		payFlowList.setTransSubSeq(dto.getVoucherNum());
//    		String respCode = dto.getRespCode();
    		if(StringUtils.isBlank(routeCode)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "银联响应码");
            }
//    		if(!(DataBaseConstants_PAY.UNION_STAT_SUCC.equals(respCode) || DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode))){
//    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "银联处理");
//    		}
//    	    }else if(DataBaseConstants_PAY.ROUTE_CODE_UNIONPAY.equals(routeCode)){
//    		String zjEbankStat = dto.getZjEbankStat();
//    		String routeSeq = dto.getRouteSeq();
//    		if(StringUtils.isBlank(routeSeq)){
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "中金返回流水号");
//            }
//    		payFlowList.setRouteSeq(routeSeq);
//    		if(StringUtils.isBlank(zjEbankStat)){
//                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "网银通知响应码");
//            }
//    		if(!DataBaseConstants_PAY.ZJ_STAT_PAID.equals(zjEbankStat)){
//    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "网银支付");
//    		}
    	}
    	//是否为重复通知
    	boolean isRepeat = false;
    	
    	payFlowList = daoService.selectOne(payFlowList);
    	if(payFlowList!=null){
    		if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(payFlowList.getTransStat())){
    			PayFlowListPo payFlowListHost = new PayFlowListPo();
    			payFlowListHost.setOrderNo(payFlowList.getOrderNo());
    			payFlowListHost.setRouteCode(DataBaseConstants_PAY.ROUTE_CODE_HOST);
    			List<PayFlowListPo> flowList = daoService.selectList(payFlowListHost);
    			if(flowList.size() != 0){
    				for (PayFlowListPo payFlow : flowList) {
    					//成功或者失败 都不应该重复通知
    					if(DataBaseConstants_PAY.T_PAY_TX_SUCCESS.equals(payFlow.getTransStat())||DataBaseConstants_PAY.T_PAY_TX_FAL.equals(payFlow.getTransStat())){
    						isRepeat = true;
    						break;
    					}
    				}
    			}
    		}
    		
    	}
    	dto.setRepeatFlag(isRepeat);
    	return dto;
    }
   
}
