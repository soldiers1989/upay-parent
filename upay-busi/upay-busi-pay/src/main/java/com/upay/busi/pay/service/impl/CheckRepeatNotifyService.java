/**
 * 
 */
package com.upay.busi.pay.service.impl;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckUnionRepeatNotifyDto;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.pay.PayFlowListPo;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 *银联重复通知检查（银联单笔交易可能会发起多次通知）  ---银联代收  无跳转 代付
 * 
 */
public class CheckRepeatNotifyService extends AbstractDipperHandler<CheckUnionRepeatNotifyDto> {

    
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
    		payFlowList.setTransSubSeq(dto.getOrderId());
    		String respCode = dto.getRespCode();
    		if(StringUtils.isBlank(routeCode)){
                ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "银联响应码");
            }
    		if(!(DataBaseConstants_PAY.UNION_STAT_SUCC.equals(respCode) || DataBaseConstants_PAY.UNION_STAT_LESS_SUCC.equals(respCode))){
    			ExInfo.throwDipperEx(AppCodeDict.BISPAY0112, "银联处理");
    		}
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
    			if(flowList!=null&&flowList.size()>0){
					isRepeat = true;
    			}
    		}
    		
    	}
    	dto.setRepeatFlag(isRepeat);
    	return dto;
    }
   
}
