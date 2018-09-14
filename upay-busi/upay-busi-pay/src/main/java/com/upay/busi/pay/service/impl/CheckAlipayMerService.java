package com.upay.busi.pay.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pactera.dipper.commons.exception.ExInfo;
import com.pactera.dipper.core.AbstractDipperHandler;
import com.pactera.dipper.core.Message;
import com.pactera.dipper.dao.service.IDaoService;
import com.upay.busi.pay.service.dto.CheckAlipayMerDto;
import com.upay.commons.dict.AppCodeDict;
import com.upay.dao.po.mer.MerBaseInfoPo;


/**
 * 校验商户支付宝支付是否开通
 * 
 * @author yhy
 * 
 */
public class CheckAlipayMerService extends AbstractDipperHandler<CheckAlipayMerDto> {

    @Resource
    private IDaoService daoService;
    private final static Logger log = LoggerFactory.getLogger(CheckAlipayMerService.class);


    @Override
    public CheckAlipayMerDto execute(CheckAlipayMerDto dto, Message msg) throws Exception {
    	String merNo = dto.getMerNo();
        if (StringUtils.isBlank(merNo)) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0004, "商户号");
        }
        MerBaseInfoPo merBaseInfo = new MerBaseInfoPo();
        merBaseInfo.setMerNo(merNo);
        merBaseInfo = daoService.selectOne(merBaseInfo);
        if (merBaseInfo == null) {
            ExInfo.throwDipperEx(AppCodeDict.BISGNR0020, "商户:" + merNo);
        }
        String merchantId = merBaseInfo.getAlipayMerchantId();
        if(StringUtils.isBlank(merchantId)){
        	 ExInfo.throwDipperEx(AppCodeDict.BISACC0000, "商户:" + merNo+"尚未开通支付宝支付");
        }
        dto.setMerName(merBaseInfo.getMerName());
        dto.setAliasName(merBaseInfo.getAliasName());
        dto.setMerchantId(merchantId);
        return dto;
    }
    
}
