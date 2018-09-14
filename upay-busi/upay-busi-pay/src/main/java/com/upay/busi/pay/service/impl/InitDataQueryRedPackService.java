package com.upay.busi.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.AdjustFlowListServiceDto;
import com.upay.busi.pay.service.dto.CheckErrTypeServiceDto;
import com.upay.busi.pay.service.dto.InitDataQueryRedPackDto;
import com.upay.commons.constants.DataBaseConstants_BATCH;
import com.upay.commons.constants.DataBaseConstants_PAY;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.chk.ChkErrListPo;
import com.upay.dao.po.chk.ChkHostDetailPo;
import com.upay.dao.po.chk.ChkThirdDetailPo;
import com.upay.dao.po.chk.StlBookPo;
import com.upay.dao.po.pay.PayFlowListHisPo;
import com.upay.dao.po.pay.PayFlowListPo;
import com.upay.dao.po.pay.PayOrderListPo;
import com.upay.dao.po.pay.PayRedpackListPo;


/**
 * 红包信息查询数据准备
 * 
 * @author yhy 2017908
 * 
 */
public class InitDataQueryRedPackService extends AbstractDipperHandler<InitDataQueryRedPackDto> {
    @Resource
    private IDaoService daoService;
    private static final Logger logger = LoggerFactory.getLogger(InitDataQueryRedPackService.class);


	@Override
	public InitDataQueryRedPackDto execute(InitDataQueryRedPackDto dto, Message message)
			throws Exception {
		String outerOrderNo = dto.getOuterOrderNo();
		if (StringUtils.isBlank(outerOrderNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISPAY0001, "商户订单号");
        }
		PayRedpackListPo payRedpackList = new PayRedpackListPo();
		payRedpackList.setOuterOrderNo(outerOrderNo);
		payRedpackList = daoService.selectOne(payRedpackList);
		if(payRedpackList == null){
	        ExInfo.throwDipperEx(AppCodeDict.BISPAY0003, "商户发送红包记录");
		}
		
		dto.setTransSubSeq(payRedpackList.getOrderNo());
		
		return dto;
	}
}
