package com.upay.busi.pay.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.DailyCumulativeDto;
import com.upay.commons.constants.CommonBaseConstans_PAY;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerTransTemplatePo;
import com.upay.dao.po.pay.PayOrderListPo;
/**
 * 修改商户交易日累计
 * 
 * @author yhy 
 * 
 */
public class DailyCumulativeService extends AbstractDipperHandler<DailyCumulativeDto> {
	@Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(DailyCumulativeService.class);


	@Override
	public DailyCumulativeDto execute(DailyCumulativeDto dto, Message message)
			throws Exception {
		String merNo = dto.getMerNo();
		BigDecimal transAmt = dto.getTransAmt();
		String refundDailyFlag = dto.getRefundDailyFlag();
		String orderStat=dto.getOrderStat();
		if(StringUtils.isBlank(merNo)){
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户号");
		}
		if(transAmt == null){
			ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "交易金额");
		}
		MerTransTemplatePo merTransTemplate = new MerTransTemplatePo();
		merTransTemplate.setMerNo(merNo);
		merTransTemplate = daoService.selectOne(merTransTemplate);
		if(null!=merTransTemplate){
			//如果refundFlag 退款标志 为空，则为支付交易，增加日累计金额；否则为退款交易，减去日累计金额
			if(StringUtils.isBlank(refundDailyFlag)){
				if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_Y.equals(orderStat) || 
						DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_CP.equals(orderStat) || 
						DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_WCP.equals(orderStat)){
					
					merTransTemplate.setDailyAcmlativeAmt(merTransTemplate.getDailyAcmlativeAmt().add(transAmt));
					daoService.update(merTransTemplate);
				}
			}else{
				 if(DataBaseConstants_PAY.T_PAY_ORDER_LIST_ORDER_STAT_REFSECSUC.equals(orderStat)){
					 merTransTemplate.setDailyAcmlativeAmt(merTransTemplate.getDailyAcmlativeAmt().subtract(transAmt));
					 daoService.update(merTransTemplate);
			     }
			}
		}
		logger.info("商户"+merNo+"当前日累计限额(修改后)为："+(merTransTemplate!=null?merTransTemplate.getDailyAcmlativeAmt():"  商户为空"));
		
		
		return dto;
	}
}
